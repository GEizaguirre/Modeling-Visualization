package cat.urv.miv.mivandroid3d;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;


public class MyOpenGLRenderer implements Renderer {

	private Context context;
	private Object3D sphere, monkey, pig, rock, gun;
	private Camera cam;
	private Light l1, l2, l3;
	private float gl_start;
	private FontAtlas camera_info;
	private FloatBuffer fog_color;
	private ByteBuffer auxiliary;
	private Vertex4 light_position;
	private GL10 gl;
	private SkyBox mySkyBox;
	private ParticleSystem PS;


	public MyOpenGLRenderer(Context context){
		this.context = context;
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {

		this.gl = gl;

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
		//gl.glEnable(GL10.GL_FOG);


		// Camera
		CameraManager.start(gl);

        sphere = new Object3D(context, R.raw.earth);
        monkey = new Object3D(context, R.raw.monkey);
        pig = new Object3D(context, R.raw.pig);
        rock = new Object3D(context, R.raw.rock_3);
        gun = new Object3D(context, R.raw.sierp);

        // Directional light
		l1 = new Light(gl, GL10.GL_LIGHT0);
		l1.enable();
		l1.setPosition(new float[] {1.0f, -1.0f, 0.0f, 0.0f});
		l1.setAmbientColor(new float[] {0.5f, 0.5f, 0.5f});
		l1.setDiffuseColor(new float[] {0.5f, 0.5f, 0.5f});

		// Positional light
		l2 = new Light(gl, GL10.GL_LIGHT1);
		l2.enable();
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

		// Set particle system
		PS = new ParticleSystem(gl, context, R.drawable.blood_particle);

		// Start functionality switcher
		StateManager.start(this, PS);
		if (!StateManager.dlight_enabled) l2.disable();
		if (!StateManager.fog_enabled) gl.glDisable(GL10.GL_FOG);
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

		manageState(gl);

        // Clears the screen and depth buffer.
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		gl.glLoadIdentity();

		// Camara set up
		CameraManager.look();

		// Skybox
		gl.glPushMatrix();
		gl.glScalef(10, 10, 10);
		if (StateManager.skybox_enabled) mySkyBox.drawSkybox(gl);
		gl.glPopMatrix();

		gl.glPushMatrix();
		// Draw models

		gl.glTranslatef(-1.5f,0,-7.0f);
		gl.glScalef(0.5f, 0.5f, 0.5f);
		sphere.draw(gl);
		gl.glPopMatrix();


		gl.glPushMatrix();

		gl.glTranslatef(1.5f,0,-7.0f);
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

		gl.glPushMatrix();
		gl.glTranslatef(0.5f,0,-9f);
		gl.glScalef(0.05f, 0.05f, 0.05f);
		//gl.glRotatef(90, 0, 1, 0);
		gl.glRotatef(90, 1, 0, 0);

		gun.draw(gl);
		gl.glPopMatrix();

		drawRockCorridor();

		if (StateManager.dlight_enabled) l2.setPosition(new float[] {light_position.get(0), light_position.get(1), light_position.get(2), light_position.get(3)});

		if (StateManager.ps_enabled) drawParticles(gl);

		drawHud(gl);

		scale_factor +=sum_value;
		if (scale_factor>1) sum_value = -sum_value;
		if (scale_factor<0.5) sum_value = -sum_value;

		current_y += y_sum;
		if (current_y>-0.1) y_sum=-y_sum;
		if (current_y<-7) y_sum=-y_sum;
		light_position.set(3, current_y);

	}

	public void manageState(GL10 gl) {
		if (StateManager.switch_fog) {
			if (StateManager.fog_enabled) gl.glEnable(GL10.GL_FOG);
			else gl.glDisable(GL10.GL_FOG);
			StateManager.switch_fog = false;
		}
		if (StateManager.switch_dlight) {
			if (StateManager.dlight_enabled) l2.enable();
			else l2.disable();
			StateManager.switch_dlight = false;
		}
	}

	public void drawRockCorridor(){
		gl.glPushMatrix();
		gl.glScalef(2, 2, 2);
		gl.glTranslatef(2f,0,-3.5f);
		rock.draw(gl);
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glScalef(2, 2, 2);
		gl.glTranslatef(2f,0,-1.5f);
		rock.draw(gl);
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glScalef(2, 2, 2);
		gl.glTranslatef(-2f,0,-3.5f);
		rock.draw(gl);
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glScalef(2, 2, 2);
		gl.glTranslatef(-2f,0,-1.5f);
		rock.draw(gl);
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glScalef(2, 2, 2);
		gl.glTranslatef(2f,0,0f);
		rock.draw(gl);
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glScalef(2, 2, 2);
		gl.glTranslatef(-2f,0,0f);
		rock.draw(gl);
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glScalef(2, 2, 2);
		gl.glTranslatef(2f,0,1.5f);
		rock.draw(gl);
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glScalef(2, 2, 2);
		gl.glTranslatef(-2f,0,1.5f);
		rock.draw(gl);
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glScalef(2, 2, 2);
		gl.glTranslatef(0f,0,3f);
		rock.draw(gl);
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glScalef(2, 2, 2);
		gl.glTranslatef(-2f,0,1.5f);
		rock.draw(gl);
		gl.glPopMatrix();

	}

	public void drawHud(GL10 gl){
		// Camera info
		gl.glPushMatrix();
		// Lightning must be provisionally disable for correct blending.
		gl.glDisable(GL10.GL_LIGHTING);
		gl.glLoadIdentity();
		gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		gl.glScalef(0.002f, 0.002f, 1f);
		gl.glTranslatef(-20f, 27f, -0.1f);
		camera_info.drawString("CAMERA NUM "+CameraManager.getCurrent_camera_number(), 1f, 1f);
		gl.glTranslatef(0f, -2f, 0f);
		camera_info.drawString("FOG "+(StateManager.fog_enabled?"SET":"UNSET"), 1f, 1f);
		gl.glTranslatef(0f, -2f, 0f);
		camera_info.drawString("MOBILE LIGHT "+(StateManager.dlight_enabled?"SET":"UNSET"), 1f, 1f);
		gl.glTranslatef(0f, -2f, 0f);
		camera_info.drawString("SKYBOX "+(StateManager.skybox_enabled?"SET":"UNSET"), 1f, 1f);
		gl.glTranslatef(0f, -2f, 0f);
		camera_info.drawString("PART SYSTEM "+(StateManager.ps_enabled?"SET":"UNSET"), 1f, 1f);
		gl.glEnable(GL10.GL_LIGHTING);
		gl.glPopMatrix();
	}

	public void drawParticles(GL10 gl){
		// Particles system info
		gl.glPushMatrix();
		gl.glDisable(GL10.GL_LIGHTING);
		gl.glTranslatef(-0.75f, -0.75f, 0);
		gl.glScalef(0.05f, 0.05f, 1);
		PS.update(gl);
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
