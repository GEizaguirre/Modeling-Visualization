package cat.urv.miv.mivandroid2d;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

import android.content.Context;

public class MyOpenGLRenderer implements Renderer {

	private Square square1, square2, square3;
	private Texture texture1;
	private TextureAtlas tAtlas;
	private AnimationManager am1, am2;
	private int angle = 0;

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
		//square1.setColor(new float[]{0.0f, 1.0f, 0.0f, 0.0f,
		//							 0.0f, 0.0f, 0.0f, 0.0f,
		//                             1.0f, 0.0f, 0.0f, 0.0f,
		//							 0.0f, 0.0f, 1.0f,  0.0f});

		square2 = new Square();
		square3 = new Square();
		//square2.setColor(new float[]{0.0f, 0.0f, 1.0f,  0.0f, 1.0f, 0.0f, 0.0f, 0.0f,
		//		0.0f, 0.0f, 1.0f,  0.0f,1.0f, 0.0f, 0.0f, 0.0f});

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



		//System.out.println("Coords");
		//for (float f: tAtlas.getCoords("walk1")) System.out.print(" "+f);

		am1 = new AnimationManager(gl, context, R.drawable.caveman, R.raw.caveman);
		square2.setAnimation(am1.getAnimation("run"));

		//am2 = new AnimationManager(gl, context, R.drawable.mario, R.raw.mario);
		//square1.setAnimation(am2.getAnimation("walk"));



	}


	@Override
	public void onDrawFrame(GL10 gl) {
		
		// Clears the screen and depth buffer.
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);		
		
		gl.glLoadIdentity();	

		gl.glTranslatef(0.0f, 0.0f, -3.0f);

		// Green Square
		gl.glPushMatrix();
		int midAngle = angle % 200;
		if (midAngle > 100)
			midAngle = 200 - midAngle;
		//gl.glTranslatef(0.0f, 0.0f, midAngle * -0.1f);
		//gl.glColor4f(0.0f, 1.0f, 0.0f, 0.0f);
		//square1.update(System.currentTimeMillis());
		//square1.draw(gl);
		gl.glPopMatrix();

		//gl.glRotatef(angle, 0.0f, 1.0f, 0.0f);

		// Red Square
		gl.glPushMatrix();
		//gl.glRotatef(angle, 0.0f, 0.0f, 1.0f);
		//gl.glTranslatef(-2.0f, 0.0f, 0.0f);
		//gl.glColor4f(1.0f, 0.0f, 0.0f, 0.0f);
		square2.update(System.currentTimeMillis());
		square2.draw(gl);
		gl.glPopMatrix();

		/*
		// Blue Square
		gl.glPushMatrix();
		//gl.glRotatef(2.0f * angle, 0.0f, 0.0f, 1.0f);
		gl.glTranslatef( 1.0f, 0.0f, 0.0f);
		//gl.glColor4f(0.0f, 0.0f, 1.0f, 0.0f);
		square3.update(System.currentTimeMillis());
		square3.draw(gl);
		gl.glPopMatrix();*/
		
		angle += 5.0f;
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
		GLU.gluPerspective(gl, 60.0f, (float) width / (float) height, 0.1f, 100.0f);
		
		// Select the modelview matrix
		gl.glMatrixMode(GL10.GL_MODELVIEW);	
	}

}
