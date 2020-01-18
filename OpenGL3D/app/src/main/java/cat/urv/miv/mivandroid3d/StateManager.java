package cat.urv.miv.mivandroid3d;

/*
 Global state manager for the HUD.
 */
public class StateManager {

    private static MyOpenGLRenderer glRenderer;
    private static ParticleSystem ps;
    static boolean fog_enabled;
    static boolean dlight_enabled;
    static boolean switch_fog;
    static boolean switch_dlight;
    static boolean skybox_enabled;
    static boolean ps_enabled;

    public static void start(MyOpenGLRenderer opengl, ParticleSystem ps1){
        glRenderer = opengl;
        ps = ps1;
        fog_enabled = false;
        dlight_enabled = false;
        switch_fog = false;
        switch_dlight = false;
        skybox_enabled = true;
        ps_enabled = false;
    }

    public static void switchFog(){
        fog_enabled = !fog_enabled;
        switch_fog = true;
    }

    public static void switchDLight(){
        dlight_enabled = !dlight_enabled;
        switch_dlight = true;
    }

    public static void switchSkybox(){
        skybox_enabled = !skybox_enabled;
    }

    public static void switchPS(){
        if (!ps_enabled) ps.restart();
        ps_enabled = !ps_enabled;
    }


}
