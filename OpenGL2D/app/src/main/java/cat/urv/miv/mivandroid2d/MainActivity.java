package cat.urv.miv.mivandroid2d;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyGLSurfaceView view = new MyGLSurfaceView(this);
        view.setRenderer(new MyOpenGLRenderer(this));
        setContentView(view);
    }
}