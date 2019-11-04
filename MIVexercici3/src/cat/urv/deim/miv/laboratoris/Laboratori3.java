package cat.urv.deim.miv.laboratoris;

import java.util.ArrayList;

import cat.urv.deim.miv.Application;

public class Laboratori3 extends Application {

    public static final long serialVersionUID = 1L;

    private int angle;

    private MyOpenGL myOpenGL = new MyOpenGL ();

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
        int width = getPanelWidth();
        int height = getPanelHeight();
        
        
        //System.out.println("width: "+width+", height: "+height);
        float aspect = width / (float) height;

        //System.out.println("start");
        glViewport(0, 0, width, height);

        setColor(1.0f, 0.0f, 0.0f);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        // Parallel projection.
        //gluPerspective(30.0f, aspect, 0.1f, 1000.0f);

        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        //glTranslatef(0.0f, 0.0f, -3.0f);
        

        /*glPushMatrix();
        int midAngle = angle % 200;
        if (midAngle > 100)
            midAngle = 200 - midAngle;
        glTranslatef(0.0f, 0.0f, midAngle * -0.1f);
        setColor(0.0f, 1.0f, 0.0f);
        drawRectangle();
        //System.out.println("end");
        glPopMatrix();*/

        glRotatef(angle, 0.0f, 1.0f, 0.0f);

		//glPushMatrix();
		//glRotatef(angle, 0.0f, 0.0f, 1.0f);
		glTranslatef(-2.0f, 0.0f, 0.0f);
		setColor(1.0f, 0.0f, 0.0f);
		drawRectangle();
		//glPopMatrix();

		/*glPushMatrix();
		glRotatef(2.0f * angle, 0.0f, 0.0f, 1.0f);
		glTranslatef( 1.0f, 0.0f, 0.0f);
		setColor(0.0f, 0.0f, 1.0f);
		drawRectangle();
		glPopMatrix();*/

        angle += 5.0f;
    }


    // Practica 3, implementa infraestructura de matrius OpenGL
    // Hint: Pots mirar l'API d'OpenGL

    // Inici codi de l'alumne

    public void defineGlMatrixMode(int model) {
        // TODO: has de ficar aqui el codi!
        switch(model) {
            case GL_MODELVIEW:
                myOpenGL.setCurrentMatrix(GL_MODELVIEW); break;
            case GL_PROJECTION:
                myOpenGL.setCurrentMatrix(GL_PROJECTION); break;
        }
    }

    public void defineGlViewport(int x, int y, int width, int height) {
        // TODO: has de ficar aqui el codi!
        if (width<0 || height<0)  {
            // TODO:GL_INVALID_VALUE
            System.out.println("GL_INVALID_VALUE");
        }
        // Specify lower left corner.
        myOpenGL.setLowerleftX(x);
        myOpenGL.setLowerleftY(y);
        myOpenGL.setViewportHeight(height);
        myOpenGL.setViewportWidth(width);
        
        
    }

    public void defineGluPerspective(float fovy, float aspect, float zNear, float zFar) {
        // TODO: has de ficar aqui el codi!
    	float f = 1/(float)Math.tan(fovy/2);
    	float [][] perspectiveMatrix = new float [][] {
    		
    		{f/aspect, 0f, 0f, 0f},
    		{0f, f, 0f, 0f},
    		{0f, 0f, (zFar+zNear)/(zNear-zFar), -2*zFar*zNear/(zNear-zFar)},
    		{0f, 0f, -1, 0f}
    	};
    	
        switch (myOpenGL.getCurrentMatrixType()){
        case GL_MODELVIEW:
            myOpenGL.setModelviewMatrix(myOpenGL.mulMatrix(myOpenGL.getModelviewMatrix(), perspectiveMatrix)); break;
        case GL_PROJECTION:
        	//myOpenGL.printMatrix(myOpenGL.mulMatrix(myOpenGL.getProjectionMatrix(), perspectiveMatrix));
            myOpenGL.setProjectionMatrix(myOpenGL.mulMatrix(myOpenGL.getProjectionMatrix(), perspectiveMatrix)); break;
        }
    	
    }

    // Replace the current matrix with the identity matrix.
    public void defineGlLoadIdentity() {
        // TODO: has de ficar aqui el codi!
        switch (myOpenGL.getCurrentMatrixType()){
            case GL_MODELVIEW:
                myOpenGL.setModelviewMatrix(new float [][] {{ 1f, 0f, 0f, 0f},
                        { 0f, 1f, 0f, 0f},
                        { 0f, 0f, 1f, 0f},
                        { 0f, 0f, 0f, 1f}}); break;
            case GL_PROJECTION:
                myOpenGL.setProjectionMatrix(new float [][] {{ 1f, 0f, 0f, 0f},
                    { 0f, 1f, 0f, 0f},
                    { 0f, 0f, 1f, 0f},
                    { 0f, 0f, 0f, 1f}}); break;
        }
    }

    public void defineGlPushMatrix() {
        // TODO: has de ficar aqui el codi!
    	switch(myOpenGL.getCurrentMatrixType()) {
    	case GL_PROJECTION:
    		myOpenGL.pushProjection(); break;
    	case GL_MODELVIEW:
    		myOpenGL.pushViewmodel(); break;
    	}
    	
    }

    public void defineGlPopMatrix() {
        // TODO: has de ficar aqui el codi!
    	switch(myOpenGL.getCurrentMatrixType()) {
    	case GL_PROJECTION:
    		myOpenGL.popProjection(); break;
    	case GL_MODELVIEW:
    		myOpenGL.popViewmodel(); break;
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
    	
        switch (myOpenGL.getCurrentMatrixType()){
        case GL_MODELVIEW:
        	//myOpenGL.printMatrix(myOpenGL.mulMatrix(myOpenGL.getModelviewMatrix(), translationMatrix));
            myOpenGL.setModelviewMatrix(myOpenGL.mulMatrix(myOpenGL.getModelviewMatrix(), translationMatrix)); break;
        case GL_PROJECTION:
        	//myOpenGL.printMatrix(myOpenGL.mulMatrix(myOpenGL.getProjectionMatrix(), perspectiveMatrix));
            myOpenGL.setProjectionMatrix(myOpenGL.mulMatrix(myOpenGL.getProjectionMatrix(), translationMatrix)); break;
        }
    }

    public void defineGlScalef(float x, float y, float z) {
        // TODO: has de ficar aqui el codi!
    	// TODO: has de ficar aqui el codi!
    	float [][]scaleMatrix = new float [][] {
    		{x, 0f, 0f, 0f},
    		{0f, y, 0f, 0f},
    		{0f, 0f, z, 0f},
    		{0f, 0f, 0f, 1f}
    	};
    	
        switch (myOpenGL.getCurrentMatrixType()){
        case GL_MODELVIEW:
        	//myOpenGL.printMatrix(myOpenGL.mulMatrix(myOpenGL.getModelviewMatrix(), translationMatrix));
            myOpenGL.setModelviewMatrix(myOpenGL.mulMatrix(myOpenGL.getModelviewMatrix(), scaleMatrix)); break;
        case GL_PROJECTION:
        	//myOpenGL.printMatrix(myOpenGL.mulMatrix(myOpenGL.getProjectionMatrix(), perspectiveMatrix));
            myOpenGL.setProjectionMatrix(myOpenGL.mulMatrix(myOpenGL.getProjectionMatrix(), scaleMatrix)); break;
        }
    	
    }

    public void defineGlRotatef(float angle, float x, float y, float z) {
        // TODO: has de ficar aqui el codi!
    	float c = (float)Math.cos(angle);
    	float s =(float)Math.sin(angle);
    	// TODO: Normalize vector so that ||(x,y,z)||=1
    	float[][] rotationMatrix = { { (float)Math.pow(x, 2)*(1-c)+c, x*y*(1-c)-z*s, x*z*(1-c)+y*s, 0},
    			{ y*x*(1-c)+z*s, (float)Math.pow(y, 2)*(1-c)+c, y*z*(1-c)-x*s, 0},
    			{ x*z*(1-c)-y*s, y*z*(1-c)+x*s, (float)Math.pow(z, 2)*(1-c)+c, 0},
    			{ 0, 0, 0, 1}
    	};
    	switch (myOpenGL.getCurrentMatrixType()){
        case GL_MODELVIEW:
        	//myOpenGL.printMatrix(myOpenGL.mulMatrix(myOpenGL.getModelviewMatrix(), translationMatrix));
            myOpenGL.setModelviewMatrix(myOpenGL.mulMatrix(myOpenGL.getModelviewMatrix(), rotationMatrix)); break;
        case GL_PROJECTION:
        	//myOpenGL.printMatrix(myOpenGL.mulMatrix(myOpenGL.getProjectionMatrix(), perspectiveMatrix));
            myOpenGL.setProjectionMatrix(myOpenGL.mulMatrix(myOpenGL.getProjectionMatrix(), rotationMatrix)); break;
        }
    }

    public void defineGlBeginPolygon() {
        // TODO: has de ficar aqui el codi!
        myOpenGL.startVertex();
        myOpenGL.setWindowWidth(getPanelWidth());
        myOpenGL.setWindowHeight(getPanelHeight());
    }

    public void defineGlVertex3f(float x, float y, float z) {
        // TODO: has de ficar aqui el codi!
    	float[] coords= new float [] {x, y, z, 1};
    	myOpenGL.addVertex(coords);


    }

    public void defineGlEndPolygon() {
        // TODO: has de ficar aqui el codi!
    	Integer[] p = myOpenGL.getDrawVertex();
    	/*for (Integer i: p) System.out.println(i);
    	System.out.println();*/
    	//fillPolygon(p);
    	drawPolygon(p);
    }

    // Fi codi de l'alumne
    public static void main(String[] args) {
        Laboratori3 example = new Laboratori3();
        example.run();
    }


}