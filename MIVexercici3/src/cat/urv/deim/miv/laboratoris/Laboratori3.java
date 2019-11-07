package cat.urv.deim.miv.laboratoris;

import cat.urv.deim.miv.Application;

public class Laboratori3 extends Application {

    public static final long serialVersionUID = 1L;

    private int angle;
    private boolean up=true;
    private float scaleCounter = 0;

    public Laboratori3() {
        super("Laboratori 3");
    }

    private void drawRectangle() {
        glBeginPolygon();
        glVertex3f(-0.5f, -0.5f, 0.0f);
        glVertex3f(-0.5f,  0.5f, 0.0f);
        glVertex3f( 0.5f,  0.5f, 0.0f);
        glVertex3f( 0.5f, -0.5f, 0.0f);
        glEndPolygon();
    }

    public void paint() {
    	//640x327
    	testMinimal();
    	//testDefault();
    	//testScale();
    	
    }

    public void testMinimal() {
    	int width = getPanelWidth();
		int height = getPanelHeight();
		glViewport(0, 0, width, height);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glBeginPolygon();
        glVertex3f(-0.5f, -0.5f, 0.0f);
        glVertex3f(-0.5f,  0.5f, 0.0f);
        glVertex3f( 0.5f,  0.5f, 0.0f);
        glVertex3f( 0.5f, -0.5f, 0.0f);
        glEndPolygon();
		
    }
    public void testDefault() {
    	int width = getPanelWidth();
		int height = getPanelHeight();
		float aspect = width / (float) height;
	
		glViewport(0, 0, width, height);
		
		setColor(1.0f, 0.0f, 0.0f);
	
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(60.0f, aspect, 0.1f, 1000.0f);
		
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glTranslatef(0.0f, 0.0f, -5.0f);
		
		glPushMatrix();
		int midAngle = angle % 200;
		if (midAngle > 100)
			midAngle = 200 - midAngle;
		glTranslatef(0.0f, 0.0f, midAngle * -0.1f);
		setColor(0.0f, 1.0f, 0.0f);
		drawRectangle();
		glPopMatrix();
		
		glRotatef(angle, 0.0f, 1.0f, 0.0f);

		glPushMatrix();
		glRotatef(angle, 0.0f, 0.0f, 1.0f);
		glTranslatef(-2.0f, 0.0f, 0.0f);
		setColor(1.0f, 0.0f, 0.0f);
		drawRectangle();
		glPopMatrix();
		
		glPushMatrix();
		glRotatef(2.0f * angle, 0.0f, 0.0f, 1.0f);
		glTranslatef( 1.0f, 0.0f, 0.0f);
		setColor(0.0f, 0.0f, 1.0f);
		drawRectangle();
		glPopMatrix();
		
		angle += 5.0f;
    }
    
    public void testScale () {
    	//640x327
        int width = getPanelWidth();
        int height = getPanelHeight();

        float aspect = width / (float) height;
        
        glViewport(0, 0, width, height);

        setColor(1.0f, 0.0f, 0.0f);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(30.0f, aspect, 0.1f, 1000.0f);

        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glTranslatef(0.0f, 0.0f, -3.0f);
        
        glPushMatrix();
		glTranslatef( -1.0f, 0.0f, 0.0f);
		glScalef(0.5f, 0.5f, 0f);
		glScalef(scaleCounter, scaleCounter, 0f);
		setColor(0.7f, 0.3f, 1.0f);
		drawRectangle();
		glPopMatrix();
		
		glPushMatrix();
		glTranslatef( 1.0f, 0.0f, 0.0f);
		glScalef(0.2f, 0.2f, 0f);
		if (up) glScalef(scaleCounter, scaleCounter*2, 0f);
		else glScalef(scaleCounter*2, scaleCounter, 0f);
		setColor(0.1f, 0.38f, 0.6f);
		drawRectangle();
		glPopMatrix();
        
        if (scaleCounter>1.5) up =false;
        if (scaleCounter<0) up = true;
        if (up) scaleCounter = scaleCounter+0.05f;
        else scaleCounter = scaleCounter-0.05f;
    }
    
    // Practica 3, implementa infraestructura de matrius OpenGL
    // Hint: Pots mirar l'API d'OpenGL

    // Inici codi de l'alumne

    public void defineGlMatrixMode(int model) {
        // TODO: has de ficar aqui el codi!
        switch(model) {
            case GL_MODELVIEW:
                MyOpenGL.setCurrentMatrixType(GL_MODELVIEW); break;
            case GL_PROJECTION:
                MyOpenGL.setCurrentMatrixType(GL_PROJECTION); break;
        }
    }

    public void defineGlViewport(int x, int y, int width, int height) {
        // TODO: has de ficar aqui el codi!
        if (width<0 || height<0)  {
            // TODO:GL_INVALID_VALUE
            System.out.println("GL_INVALID_VALUE");
        }
        // Specify lower left corner.
        MyOpenGL.setLowerleftX(x);
        MyOpenGL.setLowerleftY(y);
        MyOpenGL.setViewportHeight(height);
        MyOpenGL.setViewportWidth(width);
        
        
    }

    public void defineGluPerspective(float fovy, float aspect, float zNear, float zFar) {
        // TODO: has de ficar aqui el codi!
    	fovy = (float) Math.toRadians(fovy);
    	float f = 1/(float)Math.tan(fovy/2);
    	float [][] perspectiveMatrix = new float [][] {
    		
    		{f/aspect, 0f, 0f, 0f},
    		{0f, f, 0f, 0f},
    		{0f, 0f, (zFar+zNear)/(zNear-zFar), -2*zFar*zNear/(zNear-zFar)},
    		{0f, 0f, -1, 0f}
    	};
    	
        switch (MyOpenGL.getCurrentMatrixType()){
        case GL_MODELVIEW:
            MyOpenGL.setModelviewMatrix(MyOpenGL.mulMatrix(MyOpenGL.getModelviewMatrix(), perspectiveMatrix)); break;
        case GL_PROJECTION:
            MyOpenGL.setProjectionMatrix(MyOpenGL.mulMatrix(MyOpenGL.getProjectionMatrix(), perspectiveMatrix)); break;
        }
    	
    }

    // Replace the current matrix with the identity matrix.
    public void defineGlLoadIdentity() {
        // TODO: has de ficar aqui el codi!
        switch (MyOpenGL.getCurrentMatrixType()){
            case GL_MODELVIEW:
                MyOpenGL.setModelviewMatrix(new float [][] {{ 1f, 0f, 0f, 0f},
                        { 0f, 1f, 0f, 0f},
                        { 0f, 0f, 1f, 0f},
                        { 0f, 0f, 0f, 1f}}); break;
            case GL_PROJECTION:
                MyOpenGL.setProjectionMatrix(new float [][] {{ 1f, 0f, 0f, 0f},
                    { 0f, 1f, 0f, 0f},
                    { 0f, 0f, 1f, 0f},
                    { 0f, 0f, 0f, 1f}}); break;
        }
    }

    public void defineGlPushMatrix() {
        // TODO: has de ficar aqui el codi!
    	switch(MyOpenGL.getCurrentMatrixType()) {
    	case GL_PROJECTION:
    		MyOpenGL.pushProjection(); break;
    	case GL_MODELVIEW:
    		MyOpenGL.pushViewmodel(); break;
    	}
    	
    }

    public void defineGlPopMatrix() {
        // TODO: has de ficar aqui el codi!
    	switch(MyOpenGL.getCurrentMatrixType()) {
    	case GL_PROJECTION:
    		MyOpenGL.popProjection(); break;
    	case GL_MODELVIEW:
    		MyOpenGL.popViewmodel(); break;
    	}
    }

    public void defineGlTranslatef(float x, float y, float z) {
        // TODO: has de ficar aqui el codi!
    	float [][]translationMatrix = new float [][] {
    		{1f, 0f, 0f, x},
    		{0f, 1f, 0f, y},
    		{0f, 0f, 1f, z},
    		{0f, 0f, 0f, 1f}
    	};
        switch (MyOpenGL.getCurrentMatrixType()){
        case GL_MODELVIEW:
            MyOpenGL.setModelviewMatrix(MyOpenGL.mulMatrix(MyOpenGL.getModelviewMatrix(), translationMatrix)); break;
        case GL_PROJECTION:
            MyOpenGL.setProjectionMatrix(MyOpenGL.mulMatrix(MyOpenGL.getProjectionMatrix(), translationMatrix)); break;
        }
    }

    public void defineGlScalef(float x, float y, float z) {
        // TODO: has de ficar aqui el codi!
    	float [][]scaleMatrix = new float [][] {
    		{x, 0f, 0f, 0f},
    		{0f, y, 0f, 0f},
    		{0f, 0f, z, 0f},
    		{0f, 0f, 0f, 1f}
    	};
    	
        switch (MyOpenGL.getCurrentMatrixType()){
        case GL_MODELVIEW:
            MyOpenGL.setModelviewMatrix(MyOpenGL.mulMatrix(MyOpenGL.getModelviewMatrix(), scaleMatrix)); break;
        case GL_PROJECTION:
            MyOpenGL.setProjectionMatrix(MyOpenGL.mulMatrix(MyOpenGL.getProjectionMatrix(), scaleMatrix)); break;
        }
    	
    }

    public void defineGlRotatef(float angle, float x, float y, float z) {
    	
        // TODO: has de ficar aqui el codi!
    	angle = (float) Math.toRadians(angle);
    	float c = (float) Math.cos(angle);
    	float s =(float) Math.sin(angle);;
    	// Normalize vector so that ||(x,y,z)||=1
    	
    	float[][] rotationMatrix = { { x*x*(1-c)+c, x*y*(1-c)-z*s, x*z*(1-c)+y*s, 0},
    			{ y*x*(1-c)+z*s, y*y*(1-c)+c, y*z*(1-c)-x*s, 0},
    			{ x*z*(1-c)-y*s, y*z*(1-c)+x*s, z*z*(1-c)+c, 0},
    			{ 0, 0, 0, 1}
    	};
    	switch (MyOpenGL.getCurrentMatrixType()){
        case GL_MODELVIEW:
            MyOpenGL.setModelviewMatrix(MyOpenGL.mulMatrix(MyOpenGL.getModelviewMatrix(), rotationMatrix)); break;
        case GL_PROJECTION:
            MyOpenGL.setProjectionMatrix(MyOpenGL.mulMatrix(MyOpenGL.getProjectionMatrix(), rotationMatrix)); break;
        }
    }

    public void defineGlBeginPolygon() {
        // TODO: has de ficar aqui el codi!
        MyOpenGL.startVertex();
        MyOpenGL.setWindowWidth(getPanelWidth());
        MyOpenGL.setWindowHeight(getPanelHeight());
    }

    public void defineGlVertex3f(float x, float y, float z) {
        // TODO: has de ficar aqui el codi!
    	float[] coords= new float [] {x, y, z, 1};
    	MyOpenGL.addVertex(coords);
    }

    public void defineGlEndPolygon() {
        // TODO: has de ficar aqui el codi!
    	Integer[] p = MyOpenGL.getDrawVertex();
    	fillPolygon(p);
    	//drawPolygon(p);
    }

    // Fi codi de l'alumne
    public static void main(String[] args) {
        Laboratori3 example = new Laboratori3();
        example.run();
    }


}