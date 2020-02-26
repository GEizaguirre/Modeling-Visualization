package cat.urv.miv.mivandroid3d;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

public class ParticleSystem {

    private Texture texture;
    private int MAX_PARTICLES = 1000;
    float	slowdown=2.0f;				// Slow Down Particles
    float	zoom=-3.0f;				// Used To Zoom Out

    List<ParticleData> particlelist = new ArrayList<ParticleData>(1000);

    public ParticleSystem(GL10 gl, Context context, int texture_id){
        this.texture = new Texture (gl, context, texture_id);
        for (int i=0;i<MAX_PARTICLES;i++)		//Create all the initial particles
        {
            particlelist.add(new ParticleData(i, texture));
        }
    }

    public void restart(){
        particlelist.clear();
        for (int i=0;i<MAX_PARTICLES;i++)		//Create all the initial particles
        {
            particlelist.add(new ParticleData(i, texture));
        }
    }

    public void update(GL10 gl){
        for(ParticleData pd: particlelist)
        {
            if (pd.active)							// If The Particle Is Active
            {
                pd.drawParticle(gl, zoom);
                pd.x+=pd.xi/(slowdown*1000);// Move On The X Axis By X Speed
                pd.y+=pd.yi/(slowdown*1000);// Move On The Y Axis By Y Speed
                pd.z+=pd.zi/(slowdown*1000);// Move On The Z Axis By Z Speed

                pd.xi+=pd.xg;			// Take Pull On X Axis Into Account
                pd.yi+=pd.yg;			// Take Pull On Y Axis Into Account
                pd.zi+=pd.zg;			// Take Pull On Z Axis Into Account
                pd.life-=pd.fade;		// Reduce Particles Life By 'Fade'

                if (pd.life<0.0f) pd.initParticle();	// If Particle Is Burned Out
            }
        }
    }
}
