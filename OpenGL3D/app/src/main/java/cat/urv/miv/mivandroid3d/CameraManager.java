package cat.urv.miv.mivandroid3d;

import android.opengl.GLU;

import javax.microedition.khronos.opengles.GL10;

public class CameraManager {

    public static int MAX_NUM_CAMERAS = 1;
    public static int FIRST_CAMERA = 0;
    public static float MOVE_SPEED = 0.05f;
    public static float MOVE_FREQUENCY = 50f;
    public static float ROTATE_ANGLE = 2f;
    private static Camera[] cameras;
    private static Camera current_camera;
    private static boolean started_cameras = false;

    public static void start(GL10 gl) {
        cameras = new Camera [MAX_NUM_CAMERAS];
        for (int i=0; i<MAX_NUM_CAMERAS; i++) cameras[i] = new Camera(gl);
        current_camera = cameras[FIRST_CAMERA];
        started_cameras = true;
    }

    public static void moveLeft(float inc) {
        if (started_cameras)
            current_camera.moveLeft(inc);
    }

    public static void moveRight(float inc) {
        if (started_cameras)
            current_camera.moveRight(inc);
    }

    public static void moveUp(float inc) {
        if (started_cameras)
            current_camera.moveUp(inc);
    }

    public static void moveDown(float inc) {
        if (started_cameras)
            current_camera.moveDown(inc);
    }

    public static void moveForward(float inc) {
        if (started_cameras)
            current_camera.moveForward(inc);
    }

    public static void moveBackward(float inc) {
        if (started_cameras)
            current_camera.moveBackward(inc);
    }

    public static void yaw(float angle) {
        if (started_cameras)
            current_camera.yaw(angle);

    }

    public static void pitch(float angle) {
        if (started_cameras)
            current_camera.pitch(angle);
    }

    public static void roll(float angle) {
        if (started_cameras)
            current_camera.roll(angle);
    }


    public static void look()
    {
        current_camera.look();
    }
}
