package cat.urv.miv.mivandroid3d;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.KeyEvent;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GLSurfaceView view = new GLSurfaceView(this);
        view.setRenderer(new MyOpenGLRenderer(this));
        setContentView(view);
    }

    public double last_modification = System.currentTimeMillis();

    @Override
    public boolean onKeyDown (int keyCode, KeyEvent event){
        double current_modification = System.currentTimeMillis();
        if (current_modification-last_modification>CameraManager.MOVE_FREQUENCY) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    CameraManager.moveLeft(CameraManager.MOVE_SPEED);
                    break;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    CameraManager.moveRight(CameraManager.MOVE_SPEED);
                    break;
                case KeyEvent.KEYCODE_DPAD_UP:
                    CameraManager.moveUp(CameraManager.MOVE_SPEED);
                    break;
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    CameraManager.moveDown(CameraManager.MOVE_SPEED);
                    break;
                case KeyEvent.KEYCODE_W:
                    CameraManager.moveForward(CameraManager.MOVE_SPEED);
                    break;
                case KeyEvent.KEYCODE_S:
                    CameraManager.moveBackward(CameraManager.MOVE_SPEED);
                    break;
                case KeyEvent.KEYCODE_Y:
                    CameraManager.yaw(CameraManager.ROTATE_ANGLE);
                    break;
                case KeyEvent.KEYCODE_R:
                    CameraManager.roll(CameraManager.ROTATE_ANGLE);
                    break;
                case KeyEvent.KEYCODE_P:
                    CameraManager.pitch(CameraManager.ROTATE_ANGLE);
                    break;
            }
            return true;
        }
        else return false;

    }
}