package cat.urv.miv.mivandroid3d;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

import static java.lang.Math.cos;
import static java.lang.Math.sin;


public class MyOpenGLRenderer implements Renderer {

	private Context context;
	private Object3D sphere, monkey;
	private Camera cam;
	private Light l1, l2, l3;
	private float gl_start;

	public MyOpenGLRenderer(Context context){
		this.context = context;
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {

		// Image Background color
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);

		//Enable Depth Testing
		gl.glEnable(GL10.GL_DEPTH_TEST);

		//Enable Smooth Shading
		gl.glShadeModel(GL10.GL_FLAT);

		//Enable Lights
		gl.glEnable(GL10.GL_LIGHTING);

		// Camera
		CameraManager.start(gl);

        sphere = new Object3D(context, R.raw.earth);
        monkey = new Object3D(context, R.raw.monkey);



        // Directional light
		l1 = new Light(gl, GL10.GL_LIGHT0);
		System.out.println("Setting light");
		l1.enable();
		l1.setPosition(new float[] {-1.0f, 1.0f, 2.0f, 0.0f});
		l1.setAmbientColor(new float[] {1.0f, 0.0f, 0.0f});
		l1.setDiffuseColor(new float[] {1.0f, 0.0f, 0.0f});

		// Positional light
		l2 = new Light(gl, GL10.GL_LIGHT1);
		System.out.println("Setting light");
		l2.enable();
		l2.setPosition(new float[] {1.5f, -0.5f, 2.0f, 0.0f});
		l2.setAmbientColor(new float[] {0.0f, 1.0f, 1.0f});
		l2.setDiffuseColor(new float[] {1.0f, 1.0f, 0.0f});

		gl_start = System.currentTimeMillis();
	}

	float scale_factor = 0.1f;
	float sum_value = 0.01f;

	public void onDrawFrame(GL10 gl) {


        // Clears the screen and depth buffer.
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);


		gl.glLoadIdentity();

		// Camara set up
		CameraManager.look();

		//gl.glPushMatrix();
		//Draw the sphere
		//gl.glScalef(0.5f, 0.5f, 1f);
		//gl.glTranslatef(-2,0,-6.0f);
		//sphere.draw(gl);
		//gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glScalef(scale_factor, scale_factor, 1);
		gl.glTranslatef(1,0,-4.5f);
		monkey.draw(gl);
		gl.glPopMatrix();

		scale_factor +=sum_value;
		if (scale_factor>1) sum_value = -sum_value;
		if (scale_factor<0.1) sum_value = -sum_value;

	}

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
