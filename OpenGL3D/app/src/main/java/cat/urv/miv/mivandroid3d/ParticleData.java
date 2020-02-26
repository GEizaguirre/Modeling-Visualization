package cat.urv.miv.mivandroid3d;

import javax.microedition.khronos.opengles.GL10;

public class ParticleData {

	int id;
    boolean	active;					// Active (Yes/No)
    float	life;					// Particle Life
    float	fade;					// Fade Speed
    float	r;						// Red Value
    float	g;						// Green Value
    float	b;						// Blue Value
    float	x;						// X Position
    float	y;						// Y Position
    float	z;						// Z Position
    float	xi;						// X Direction
    float	yi;						// Y Direction
    float	zi;						// Z Direction
    float	xg;						// X Gravity
    float	yg;						// Y Gravity
    float	zg;						// Z Gravity
	private Texture texture;
	private Square square;

    float ParticleColors[][]=
    {
       {1.0f,0.5f,0.5f},{1.0f,0.75f,0.5f},{1.0f,1.0f,0.5f},{0.75f,1.0f,0.5f},
       {0.5f,1.0f,0.5f},{0.5f,1.0f,0.75f},{0.5f,1.0f,1.0f},{0.5f,0.75f,1.0f},
       {0.5f,0.5f,1.0f},{0.75f,0.5f,1.0f},{1.0f,0.5f,1.0f},{1.0f,0.5f,0.75f}
    };
    
    public ParticleData(int id, Texture texture)
    {    	
    	this.id = id;
        this.texture=texture;
        square = new Square();
        square.setTexture(texture, new float[] {0, 1, 0, 0, 1, 0, 1, 1});
    	initParticle();        
	}

	public void initParticle()
    {
        active 	= true;												// Make All The Particles Active
        life 	= 1.0f;												// Give All The Particles Full Life
        fade	= (float)(100 * Math.random())/1000.0f+0.003f;		// Random Fade Speed
        r		= ParticleColors[id%12][0];			// Select Red Rainbow Color
        g		= ParticleColors[id%12][1];			// Select Red Rainbow Color
        b		= ParticleColors[id%12][2];			// Select Red Rainbow Color
        xi		= (float)((50 * Math.random())-26.0f)*10.0f;		// Random Speed On X Axis
        yi		= (float)((50 * Math.random())-25.0f)*10.0f;		// Random Speed On Y Axis
        zi		= (float)((50 * Math.random())-25.0f)*10.0f;		// Random Speed On Z Axis
        xg		= 0.0f;												// Set Horizontal Pull To Zero
        yg		= -0.8f;											// Set Vertical Pull Downward
        zg		= 0.0f;												// Set Pull On Z Axis To Zero

    }

	public void drawParticle(GL10 gl, float zoom) {
		if(active){
			gl.glPushMatrix();;
            gl.glColor4f(1.0f, 1.0f, 1.0f, life);
			gl.glTranslatef(x, y, z+zoom);
			square.draw(gl);
			gl.glPopMatrix();


		}
	}
}
