package cat.urv.miv.mivandroid2d;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import java.nio.IntBuffer;

import android.content.Context;
import android.view.MotionEvent;

import java.nio.IntBuffer;

import static javax.microedition.khronos.opengles.GL11.GL_VIEWPORT;

public class MyOpenGLRenderer implements Renderer {

    private Square square1, mario_square, caveman_square;
    private Texture texture1;
    private TextureAtlas tAtlas;
    private TileMap tm1, tm2, tm3, tm4, tm5, tm6;
    private AnimationManager mario_character, caveman_character;
    private int angle = 0;
    private int viewportWidth, viewportHeight;


    private Context context;

    public MyOpenGLRenderer(Context context){
        this.context = context;
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        // General openGL activation.
        gl.glEnable(GL10.GL_TEXTURE_2D);

        // Image Background color
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);

        //Create the objects
        square1 = new Square();

        mario_square = new Square();
        caveman_square = new Square();


		/*tAtlas = new TextureAtlas(gl, context, R.drawable.caveman, R.raw.caveman);
		texture1 = tAtlas.getTexture();
		square2.setTexture(texture1, tAtlas.getCoords("run5"));*/
        //for (float f: tAtlas.getCoords("run5")) System.out.println(f+" ");
		/*square2.setTexture(texture1, new float[] {
				0.07421f, 1f,//B
				0.07421f,0.4063f,//A
				0.1445f, 0.4063f,//D
				0.1445f, 1f//C
				});*/




        mario_character = new AnimationManager(gl, context, R.drawable.mario, R.raw.mario);
        mario_square.setAnimation(mario_character.getAnimation("walk"));

        caveman_character = new AnimationManager(gl, context, R.drawable.caveman, R.raw.caveman);
        caveman_square.setAnimation(caveman_character.getAnimation("run"));

        // TODO: implement a texture atlas (even if the rest works).
        tm1 = new TileMap (gl, context, R.drawable.background_tiles, R.raw.tilemap1, 300);
        /*tm2 = new TileMap (gl, context, R.drawable.background_tiles, R.raw.tilemap1, 200f);
        //tm3 = new TileMap (gl, context, R.drawable.background_tiles, R.raw.tilemap1, 150f);*/
        tm4 = new TileMap (gl, context, R.drawable.background_tiles, R.raw.tilemap2, 200f);
        tm5 = new TileMap (gl, context, R.drawable.background_tiles, R.raw.tilemap3, 60f);
        tm6 = new TileMap (gl, context, R.drawable.background_tiles, R.raw.tilemap4, 30f);



    }


    @Override
    public void onDrawFrame(GL10 gl) {

        // Clears the screen and depth buffer.
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        gl.glLoadIdentity();


        tm1.update(System.currentTimeMillis());
        tm1.draw(0f, -1f, 0.8f, 0.2f, 0.2f);
        tm4.update(System.currentTimeMillis());
        tm4.draw(0f, -1f, 0.47f, 0.13f, 0.13f);
        tm5.update(System.currentTimeMillis());
        tm5.draw(0f, -1f, -0.09f, 0.17f, 0.17f);


        tm6.update(System.currentTimeMillis());
        tm6.draw(0f, -1f, -0.09f, 0.17f, 0.17f);;

        // Red Square
        gl.glPushMatrix();

        drawCharacters(gl);

        gl.glPopMatrix();

    }

    float displacement = 0;

    public void drawCharacters(GL10 gl) {

        displacement += test_touches();
        gl.glTranslatef(-0.4f+displacement, -0.5f, 0.0f);
        gl.glScalef(-0.15f, 0.15f, 0.01f);
        caveman_square.update(System.currentTimeMillis());
        caveman_square.draw(gl);
        gl.glTranslatef(-2f-displacement, 0, 0);
        mario_square.update(System.currentTimeMillis());
        mario_square.draw(gl);

    }

    private float touch_counter = 0;
    private float lastupdate = System.nanoTime();
    private final double speed = 1E8;

    private float test_touches(){
        float t = System.nanoTime();
        //System.out.println(t);
        if ((t-lastupdate)>=speed) {
            lastupdate=t;
            float rt = touch_counter/100;
            touch_counter = 0;
            System.out.println("Got "+rt);
            return rt;
        }
        else return 0f;
    }

    public void incrementTouches(){

        touch_counter++;
        System.out.println("Touched! "+touch_counter+" touches");
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Define the Viewport
        gl.glViewport(0, 0, width, height);
        viewportWidth=width;
        viewportHeight=height;
        // Select the projection matrix
        gl.glMatrixMode(GL10.GL_PROJECTION);
        // Reset the projection matrix
        gl.glLoadIdentity();
        // Calculate the aspect ratio of the window


        // Select the modelview matrix
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        gl.glEnable( GL10.GL_BLEND );
    }

}
