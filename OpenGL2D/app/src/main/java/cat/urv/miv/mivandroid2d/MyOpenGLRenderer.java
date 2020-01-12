package cat.urv.miv.mivandroid2d;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.media.MediaPlayer;
import android.opengl.GLSurfaceView.Renderer;
import android.content.Context;


public class MyOpenGLRenderer implements Renderer {

    private final float speed_scalation = 2;
    private Square mario_square, caveman_square;
    private TileMap tm1, tm4, tm5, tm6;
    private AnimationManager mario_character, caveman_character;
    private SimpleHUD hud;
    private MediaPlayer music;


    private Context context;

    public MyOpenGLRenderer(Context context){
        this.context = context;
        music = MediaPlayer.create(context, R.raw.world_on_fire);
        music.start();
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // General openGL activation.
        gl.glEnable(GL10.GL_TEXTURE_2D);

        // Characters
        mario_square = new Square();
        caveman_square = new Square();
        mario_character = new AnimationManager(gl, context, R.drawable.mario, R.raw.mario);
        mario_square.setAnimation(mario_character.getAnimation("walk"));

        caveman_character = new AnimationManager(gl, context, R.drawable.caveman, R.raw.caveman);
        caveman_square.setAnimation(caveman_character.getAnimation("run"));
        caveman_square.getAnimation().activate_touches();

        // Background
        tm1 = new TileMap (gl, context, R.drawable.background_tiles, R.raw.tilemap1, 300/speed_scalation);
        tm4 = new TileMap (gl, context, R.drawable.background_tiles, R.raw.tilemap2, 200f/speed_scalation);
        tm5 = new TileMap (gl, context, R.drawable.background_tiles, R.raw.tilemap3, 150f/speed_scalation);
        tm6 = new TileMap (gl, context, R.drawable.background_tiles, R.raw.tilemap4, 30f/speed_scalation);

        hud = new SimpleHUD(context, gl, R.drawable.mario_title, R.raw.font_for_myv, R.drawable.font_for_myv);
    }


    @Override
    public void onDrawFrame(GL10 gl) {

        StateManager.update_touches();
        // Clears the screen and depth buffer.
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        gl.glLoadIdentity();

        // Background
        tm1.setDimensions(-1f, 0.8f, 0.2f, 0.2f);
        tm1.update(System.currentTimeMillis(), true);
        tm1.draw(0f);

        tm4.setDimensions( -1f, 0.47f, 0.13f, 0.13f);
        tm4.update(System.currentTimeMillis(), true);
        tm4.draw(0f);

        tm5.setDimensions( -1f, -0.09f, 0.17f, 0.17f);
        tm5.update(System.currentTimeMillis(), true);
        tm5.draw(0f);

        tm6.setDimensions( -1f, -0.09f, 0.17f, 0.17f);
        tm6.update(System.currentTimeMillis(), true);
        tm6.draw(0f);

        // Characters
        drawCharacters(gl);

        // HUD
        hud.draw(gl);
    }

    float displacement = 0;

    public void drawCharacters(GL10 gl) {
        gl.glPushMatrix();
        displacement = 0;
        gl.glTranslatef(-0.4f+displacement, -0.7f, 0.0f);
        gl.glScalef(-0.3f, 0.3f, 0.01f);

        caveman_square.update(System.currentTimeMillis());
        caveman_square.draw(gl);
        gl.glTranslatef(-2f-displacement, 0, 0);
        mario_square.update(System.currentTimeMillis());
        mario_square.draw(gl);
        gl.glPopMatrix();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Define the Viewport
        gl.glViewport(0, 0, width, height);
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
