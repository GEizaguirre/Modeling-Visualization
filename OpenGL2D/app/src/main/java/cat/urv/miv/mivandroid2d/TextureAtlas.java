package cat.urv.miv.mivandroid2d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import javax.microedition.khronos.opengles.GL10;

public class TextureAtlas {

    private HashMap<String, float[]> coordStructure;
    private GL10 gl;
    private Context context;
    private int image_id;
    private int text_id;

    public TextureAtlas (GL10 gl, Context context, int image_id, int text_id) {
        this.image_id=image_id;
        this.text_id = text_id;
        this.gl = gl;
        this.context = context;
        coordStructure = new HashMap();

        /* Get the size of the image.
         */
        //BitmapFactory.Options dim = new BitmapFactory.Options();
        //dim.inJustDecodeBounds = true;
        Bitmap mBitmap = BitmapFactory.decodeResource(context.getResources(), image_id);
        int height = mBitmap.getHeight();
        int width = mBitmap.getWidth();

        //
        // System.out.println(height + "x" + width);
        /* Read text file and create structure passing each images' coordinates
        to [0,1].
         */
        InputStream tFile = context.getResources().openRawResource(text_id);
        BufferedReader atlasData = new BufferedReader(new InputStreamReader(tFile));
        String line;
        try {
            while ((line = atlasData.readLine()) != null) {
                String [] parts = line.split("\\s+");
                if (parts.length == 6) {
                    float[] coordinates = {
                            Float.parseFloat(parts[2]) / width, 1 - Float.parseFloat(parts[3]) / height, //B
                            Float.parseFloat(parts[2]) / width, 1 - (Float.parseFloat(parts[5])+Float.parseFloat(parts[3])) / height, // A
                            (Float.parseFloat(parts[2]) + Float.parseFloat(parts[4]) - 1) / width, 1 - (Float.parseFloat(parts[5])+Float.parseFloat(parts[3])) / height, //D
                            (Float.parseFloat(parts[2]) + Float.parseFloat(parts[4]) - 1) / width, 1 - Float.parseFloat(parts[3]) / height //C
                    };

                    coordStructure.put(parts[0] + parts[1], coordinates);
                    /*System.out.println(parts[0]+parts[1]);
                    for (int i = 0; i<coordinates.length; i++){
                        System.out.println(coordinates[i]);
                    }*/
                }
            }
        }
        catch (IOException e){
            System.out.println("Error reading atlas txt file");
        }

    }

    public Texture getTexture() {
        return new Texture(gl, context, image_id);
    }

    public float[] getCoords ( String code ){

        return coordStructure.get(code);

    }
}
