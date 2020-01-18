package cat.urv.miv.mivandroid3d;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import javax.microedition.khronos.opengles.GL11Ext;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static java.lang.Math.cos;
import static java.lang.Math.sin;


public class MyOpenGLRenderer implements Renderer {

	private Context context;
	private Object3D sphere, monkey, pig;
	private Camera cam;
	private Light l1, l2, l3;
	private float gl_start;
	private FontAtlas camera_info;
	private FloatBuffer fog_color;
	private ByteBuffer auxiliary;
	private Vertex4 light_position;
	private GL11 gl11;
	private SkyBox mySkyBox;


	public MyOpenGLRenderer(Context context){
		this.context = context;
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {

		// Image Background color
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);

		gl.glEnable(GL11.GL_TEXTURE_2D);

		//Enable Depth Testing
		gl.glEnable(GL10.GL_DEPTH_TEST);

		//Enable Smooth Shading
		gl.glShadeModel(GL10.GL_FLAT);

		//Enable Lights
		gl.glEnable(GL10.GL_LIGHTING);


		// Fog

		gl.glFogf(GL10.GL_FOG_MODE, GL10.GL_EXP);
		auxiliary = ByteBuffer.allocateDirect(4*4);
		auxiliary.order(ByteOrder.nativeOrder());
		fog_color = auxiliary.asFloatBuffer();
		fog_color.put(new float[] {0.0f, 0.0f, 0.0f, 1.0f});
		fog_color.position(0);
		gl.glFogfv(GL10.GL_FOG_COLOR, fog_color);
		gl.glFogf(GL10.GL_FOG_DENSITY, 0.3f);
		gl.glFogf(GL10.GL_FOG_START, 1f);
		gl.glFogf(GL10.GL_FOG_END, 100f);
		gl.glEnable(GL10.GL_FOG);


		// Camera
		CameraManager.start(gl);

        sphere = new Object3D(context, R.raw.earth);
        monkey = new Object3D(context, R.raw.monkey);
        pig = new Object3D(context, R.raw.pig);

        // Directional light
		l1 = new Light(gl, GL10.GL_LIGHT0);
		l1.enable();
		l1.setPosition(new float[] {1.0f, -1.0f, 0.0f, 0.0f});
		l1.setAmbientColor(new float[] {0.5f, 0.5f, 0.5f});
		l1.setDiffuseColor(new float[] {0.5f, 0.5f, 0.5f});

		// Positional light
		l2 = new Light(gl, GL10.GL_LIGHT1);
		//l2.enable();
		light_position = new Vertex4();
		light_position.set(0, 1.5f);
		light_position.set(1, 0.5f);
		light_position.set(2, 2.0f);
		light_position.set(3, -0.1f);
		l2.setPosition(new float[] {light_position.get(0), light_position.get(1), light_position.get(2), light_position.get(3)});
		l2.setAmbientColor(new float[] {0.0f, 1.0f, 1.0f});
		l2.setDiffuseColor(new float[] {1.0f, 1.0f, 0.0f});

		gl_start = System.currentTimeMillis();

		prepare_skybox((GL11) gl, context);

		// Set camera counter HUD
		camera_info = new FontAtlas(context, gl, R.raw.font_for_myv, R.drawable.font_for_myv);

	}

	public void prepare_skybox(GL11 gl, Context context){
		int[] skybox_textures = new int[6];
		skybox_textures[0] = R.drawable.posx;
		skybox_textures[1] = R.drawable.negx;
		skybox_textures[2] = R.drawable.posy;
		skybox_textures[3] = R.drawable.negy;
		skybox_textures[4] = R.drawable.posz;
		skybox_textures[5] = R.drawable.negz;
		mySkyBox = new SkyBox();
		mySkyBox.load_skybox(gl, context, skybox_textures);

	}

	float scale_factor = 0.5f;
	float sum_value = 0.002f;
	float current_y = -0.1f;
	float y_sum = -0.1f;


	public void onDrawFrame(GL10 gl) {


        // Clears the screen and depth buffer.
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);


		gl.glLoadIdentity();


		// Camara set up
		CameraManager.look();

		// Skybox
		gl.glPushMatrix();
		gl.glScalef(10, 10, 10);
		mySkyBox.drawSkybox(gl);
		gl.glPopMatrix();



		gl.glPushMatrix();
		//Draw the sphere

		gl.glTranslatef(-1.5f,0,-4.0f);
		gl.glScalef(0.5f, 0.5f, 0.5f);
		sphere.draw(gl);
		gl.glPopMatrix();

		gl.glPushMatrix();

		gl.glTranslatef(scale_factor,1,-4.5f);
		gl.glScalef(0.2f, 0.2f, 0.2f);
		gl.glRotatef(90, 0, 1, 0);
		pig.draw(gl);
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glScalef(scale_factor, scale_factor, 1);
		gl.glTranslatef(1,0,-3.5f);
		monkey.draw(gl);
		gl.glPopMatrix();

		l2.setPosition(new float[] {light_position.get(0), light_position.get(1), light_position.get(2), light_position.get(3)});

		drawHud(gl);

		scale_factor +=sum_value;
		if (scale_factor>1) sum_value = -sum_value;
		if (scale_factor<0.5) sum_value = -sum_value;

		current_y += y_sum;
		if (current_y>-0.1) y_sum=-y_sum;
		if (current_y<-7) y_sum=-y_sum;
		light_position.set(3, current_y);

	}

	public void drawHud(GL10 gl){
		// Camera info
		gl.glPushMatrix();
		gl.glDisable(GL10.GL_LIGHTING);
		gl.glLoadIdentity();
		gl.glTranslatef(-0.035f, -0.05f, -0.1f);
		camera_info.drawString("CAMERA:"+CameraManager.getCurrent_camera_number(), 0.002f, 0.002f);
		gl.glEnable(GL10.GL_LIGHTING);
		gl.glPopMatrix();
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
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glEnable( GL10.GL_BLEND );


	}

}
