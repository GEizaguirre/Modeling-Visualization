package cat.urv.miv.mivandroid2d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

//TODO: TileMap manager.
public class TileMap {

    static double DEFAULT_SPEED=100;

    private GL10 gl;
    private Context context;
    private int image_id;
    private int text_id;
    private int tileWidth;
    private int tileHeight;
    private int lineNumber;
    private int lineSize;
    private String name;
    private Square[][] tilemap;

    // Atributes for paralax
    private double speed;
    private float paralaxDisplacement=0;
    private double lastParalaxDisplacement;

    private int numFrames;
    private int currentFrame;
    private LOOP_TYPES loop;
    private long lastupdate;
    private Texture texture;

    public TileMap(GL10 gl, Context context, int image_id, int text_id, double speed){
        this.gl = gl;
        this.context = context;
        this.image_id = image_id;
        this.text_id = text_id;
        this.speed = speed;
        lastParalaxDisplacement=System.currentTimeMillis();
        // Read tilemap file.
        readTileMapData();


    }

    public void readTileMapData () {

        this.texture = new Texture(this.gl, this.context, this.image_id);
        /* Get the size of the image.
         */
        Bitmap mBitmap = BitmapFactory.decodeResource(context.getResources(), image_id);
        int height = mBitmap.getHeight();
        int width = mBitmap.getWidth();


        System.out.println("Image size: "+height + "x" + width);
        /* Read text file and create structure passing each images' coordinates
        to [0,1].
         */
        InputStream tFile = context.getResources().openRawResource(text_id);
        BufferedReader tilemapData = new BufferedReader(new InputStreamReader(tFile));
        String line;
        try {

            // Extract tile size.
            line = tilemapData.readLine();
            String[] parts = line.split("\\s+");
            tileWidth = Integer.parseInt(parts[0]);
            tileHeight = Integer.parseInt(parts[1]);
            //System.out.println("Tile size: "+tileWidth + "x" + tileHeight);
            int tilesPerRow = width/tileWidth;
            line = tilemapData.readLine();
            parts = line.split("\\s+");
            lineSize = Integer.parseInt(parts[0]);
            lineNumber = Integer.parseInt(parts[1]);
            //System.out.println("Format : "+lineNumber + " lines x " + lineSize+" elements");
            tilemap = new Square[lineNumber][lineSize];

            for (int i=0; i<lineNumber; i++){
                line = tilemapData.readLine();
                parts = line.split("\\s+");
                for (int j = 0; j<lineSize; j++) {
                    int number = Integer.parseInt(parts[j]);

                    // Get tile position
                    int row = number/tilesPerRow;
                    int column = number % tilesPerRow;
                    //System.out.print("Got tile number "+number+" with row "+row+" and column "+column);
                    tilemap[i][j] = new Square();
                    tilemap[i][j].setTexture( texture , new float [] { column*tileWidth/(float)width, ((row+1)*tileHeight-1)/(float)height,
                            column*tileWidth/(float)width, ((row)*tileHeight)/(float)height,
                            ((column+1)*tileWidth-1)/(float)width, ((row)*tileHeight)/(float)height,
                            ((column+1)*tileWidth-1)/(float)width, ((row+1)*tileHeight-1)/(float)height});
                    /*float u1=column*tileWidth/(float)width;
                    float v1=((row+1)*tileHeight-1)/(float)(height);
                    float u2=column*tileWidth/(float)width;
                    float v2=((row)*tileHeight)/(float)height;
                    float u3=((column+1)*tileWidth-1)/(float)width;
                    float v3=((row)*tileHeight)/(float)height;
                    float u4=((column+1)*tileWidth-1)/(float)width;
                    float v4=((row+1)*tileHeight-1)/(float)height;

                    System.out.println("\n\t Coordinates: ("+ u1 +", "+v1+") - ("+
                            u2 +", "+v2+") - ("+
                            u3 +"," + v3+") - ("+
                            u4 +", "+v4+")");*/
                }
            }
        } catch (java.io.IOException ioe) {
            System.out.println("ERROR :: Reading tilemap text file.");
        }
    }

    public void draw(float z, float displx, float disply){
        int viewport[] = new int[4];
        gl.glGetIntegerv(GL11.GL_VIEWPORT, viewport, 0);
        float dispTilex = tileWidth/(float)viewport[2];
        float dispTiley = tileWidth/(float)viewport[3];
        gl.glPushMatrix();
        gl.glTranslatef(displx+paralaxDisplacement,disply,z);
        for (int i=0; i<lineNumber; i++){
            gl.glPushMatrix();
            for (int j = 0; j<lineSize; j++){
                tilemap[i][j].draw(gl);
                gl.glTranslatef(2, 0, 0) ;
            }
            gl.glPopMatrix();
            gl.glTranslatef(0,-2,0);
        }
        gl.glPopMatrix();
    }

    public void update(double ctime){
        //System.out.println("ctime: "+ctime+" lastParalaxDisplacement: "+lastParalaxDisplacement+" speed:"+speed);
        //System.out.println("Difference: "+(ctime-lastParalaxDisplacement));
        if ((ctime-lastParalaxDisplacement)>speed) {
            paralaxDisplacement = paralaxDisplacement - 0.04f;
            lastParalaxDisplacement=ctime;
            //System.out.println("decreasing displacement");
        }
        if (paralaxDisplacement==-2*lineSize) paralaxDisplacement=0;
        //System.out.println("paralaxDisplacement: "+paralaxDisplacement);
    }

}