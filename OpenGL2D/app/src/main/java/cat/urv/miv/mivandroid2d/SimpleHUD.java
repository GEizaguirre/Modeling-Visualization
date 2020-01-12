package cat.urv.miv.mivandroid2d;

import android.content.Context;

import javax.microedition.khronos.opengles.GL10;

public class SimpleHUD {

    private Square info_square, framework_square, title_square;
    private Context context;
    private Texture title_texture;

    private float [] info_square_colors = new float[]{1.0f, 0.0f, 0.0f,  0.5f, 1.0f, 0.0f, 0.0f, 0.5f,
            1.0f, 0.0f, 0.0f,  0.5f,1.0f, 0.0f, 0.0f, 0.5f};
    private float [] framework_square_colors = new float[]{1.0f, 1.0f, 1.0f,  0.75f, 1.0f, 1.0f, 1.0f, 0.75f,
            1.0f, 1.0f, 1.0f,  0.75f,1.0f, 1.0f, 1.0f, 0.75f};

    public SimpleHUD (Context context, GL10 gl, int title_texture_id) {

        this.context = context;
        info_square = new Square();
        info_square.setColor(info_square_colors);
        framework_square = new Square();
        framework_square.setColor(framework_square_colors);
        title_square = new Square();
        title_texture = new Texture(gl, context, title_texture_id);
        title_square.setTexture(title_texture, new float[]{0.0f, 1.0f,
                0.0f, 0f,
                1, 0.0f,
                1f, 1f});
    }

    public void draw (GL10 gl){
        drawTitle(gl);
        drawGameInfo(gl);
    }

    public void drawTitle(GL10 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(-0.5f, 0.4f, 0);
        gl.glScalef(0.4f, 0.5f, 1);
        title_square.draw(gl);
        gl.glPopMatrix();

    }

    public void drawGameInfo (GL10 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(0.45f, 0.45f, 0);
        gl.glScalef(0.5f, 0.35f, 1);
        framework_square.draw(gl);
        gl.glScalef(0.9f, 0.9f, 1);
        info_square.draw(gl);
        gl.glPopMatrix();
    }


    public void drawGameStats(){

    }
}
