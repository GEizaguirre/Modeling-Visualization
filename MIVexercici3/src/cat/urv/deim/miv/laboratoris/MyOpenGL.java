package cat.urv.deim.miv.laboratoris;

import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

import cat.urv.deim.miv.Application;

import java.util.ArrayList;

import static cat.urv.deim.miv.Application.GL_MODELVIEW;
import static cat.urv.deim.miv.Application.GL_PROJECTION;

public class MyOpenGL {
    private static final int MATRIX_SIZE = 4;
    private static int angle;
    private static float[][] currentMatrix;
    private static float[][] projectionMatrix = new float[MATRIX_SIZE][MATRIX_SIZE];
    private static float[][] modelviewMatrix = new float[MATRIX_SIZE][MATRIX_SIZE];
    private static Stack <float[][]> projectionStack = new Stack<>();
    private static Stack <float[][]> modelviewStack = new Stack<>();
    private static int currentMatrixType;
    private static int lowerleftX = 0;
    private static int lowerleftY = 0;
    private static int viewportWidth;
    private static int viewportHeight;
    private static ArrayList<float[]> currentVertex;
    private static int windowWidth;
    private static int windowHeight;

    public MyOpenGL() {
    }

    public static int getAngle() {
        return angle;
    }
    
    public static float[][] pushProjection () {
    	float[][] newProj = new float[MATRIX_SIZE][MATRIX_SIZE];
    	for (int i=0; i<MATRIX_SIZE; i++) {
    		for (int j=0; j<MATRIX_SIZE; j++) {
    			newProj[i][j]=projectionMatrix[i][j];
    		}
    	}
    	return projectionStack.push(newProj);
    }
    
    public static float[][] pushViewmodel () {
    	float[][] newModel = new float[MATRIX_SIZE][MATRIX_SIZE];
    	for (int i=0; i<MATRIX_SIZE; i++) {
    		for (int j=0; j<MATRIX_SIZE; j++) {
    			newModel[i][j]=modelviewMatrix[i][j];
    		}
    	}
    	return modelviewStack.push(newModel);
    }
    
    public static void popProjection () {
    	float[][] newProj = projectionStack.pop();
    	for (int i=0; i<MATRIX_SIZE; i++) {
    		for (int j=0; j<MATRIX_SIZE; j++) {
    			projectionMatrix[i][j]=newProj[i][j];
    		}
    	}
    }
    
    public static void popViewmodel () {
    	float[][] newModel = modelviewStack.pop();
    	for (int i=0; i<MATRIX_SIZE; i++) {
    		for (int j=0; j<MATRIX_SIZE; j++) {
    			modelviewMatrix[i][j]=newModel[i][j];
    		}
    	}
    }

    public static void setAngle(int ng) {
        angle = ng;
    }

    public static float[][] getCurrentMatrix() {
        return currentMatrix;
    }

    public static void setCurrentMatrix(int model) {
        switch (model){
            case GL_MODELVIEW:
                currentMatrix = modelviewMatrix; break;
            case GL_PROJECTION:
                currentMatrix = projectionMatrix; break;
        }
        currentMatrixType = model;
    }

    public static float[][] getProjectionMatrix() {
        return projectionMatrix;
    }

    public static void setProjectionMatrix(float[][] pMatrix) {
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                projectionMatrix[i][j] = pMatrix[i][j];
            }
        }
    }

    public static float[][] getModelviewMatrix() {
        return modelviewMatrix;
    }

    public static void setModelviewMatrix(float[][] mMatrix) {
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                modelviewMatrix[i][j] = mMatrix[i][j];
            }
        }
    }

    public static int getLowerleftX() {
        return lowerleftX;
    }

    public static void setLowerleftX(int lleftX) {
        lowerleftX = lleftX;
    }

    public static int getLowerleftY() {
        return lowerleftY;
    }

    public static void setCurrentMatrix(float[][] cMatrix) {
        currentMatrix = cMatrix;
    }

    public static int getCurrentMatrixType() {
        return currentMatrixType;
    }

    public static void setCurrentMatrixType(int cMatrixType) {
        currentMatrixType = cMatrixType;
    }

    public static void setLowerleftY(int lleftY) {
        lowerleftY = lleftY;
    }

    public static int getViewportWidth() {
        return viewportWidth;
    }

    public static void setViewportWidth(int vWidth) {
        viewportWidth = vWidth;
    }

    public static int getViewportHeight() {
        return viewportHeight;
    }

    public static void setViewportHeight(int vHeight) {
        viewportHeight = vHeight;
    }

    public static void initVertex() {
        currentVertex = new ArrayList<>();
    }

    public static void addVertex(float[] coord) {

    	float[] aux_coord = new float[MATRIX_SIZE];
    	if (coord[MATRIX_SIZE-1] == 0) {
    		System.out.println ("ERROR :: Invalid value, homogene coordinate can not be 0");
    		System.exit(-1);
    	}
 
    	//Get
        if (currentVertex != null) {
        	
        	// Generate coordinates.
            for (int i = 0; i < MATRIX_SIZE; i++) {
                aux_coord[i] = 0;
                for (int j = 0; j < MATRIX_SIZE; j++) {
                    aux_coord[i] = aux_coord[i] + modelviewMatrix[i][j] * coord[j];
                }
            }

            for (int i = 0; i < MATRIX_SIZE; i++) {
                coord[i] = 0;
                for (int j = 0; j < MATRIX_SIZE; j++) {
                    coord[i] = coord[i] + projectionMatrix[i][j] * aux_coord[j];
                }
            }
        
        	currentVertex.add(coord);	
        	
        } else System.out.println("ERROR :: Did not call glBegin first.");
    }

    public static Integer[] getDrawVertex() {

        if (currentVertex == null) {
            // FIXME: generate GL_INVALID_OPERATION
            System.out.println("ERROR :: glBegin must be called first.");
            return null;
        } else if (currentVertex.size() < 2) {
            System.out.println("ERROR :: Add at least 2 vertex.");
            return null;
        } else {
        		// Generate window coordinates and apply Newport transformation.
                for (float[] cd: currentVertex) {
                	
                	if (cd[MATRIX_SIZE-1] != 0) {
                    	// Normalize coordinates.
        	            for (int i=0; i < MATRIX_SIZE; i++) {
        	            	cd[i] = cd[i]/cd[MATRIX_SIZE-1];
        	            }
        	            // Get window coordinates        	            
        	            if ((windowWidth==0) || (windowHeight==0)) System.out.println("WARNING :: Viewport may not be sspecified");
        	        	
        	            if (cd[0] >= 0) cd[0] = windowWidth/2 + cd[0]*windowWidth/2 ;
        	        	else cd[0] = windowWidth/2*(1-Math.abs(cd[0])) -1;
        	        	
        	        	if (cd[1] >= 0) cd[1] = windowHeight/2 + cd[1]*windowHeight/2+1;
        	        	else cd[1] = windowHeight/2*(1-Math.abs(cd[1]))+1;
        	        	
                    } else for (int i=0; i < MATRIX_SIZE; i++) cd[i]=Integer.MAX_VALUE;
                    
                	for (int i=0; i<MATRIX_SIZE; i++) {
	                    if (i == 0) {
	                    	// Viewport transformation.
	                    	cd[i] = lowerleftX + (cd[i]) * (viewportWidth-lowerleftX)/windowWidth;
	                    } else if (i == 1) {	            
	                    	// Viewport transformation.
	                    	cd[i] = lowerleftY + (cd[i]) * (viewportHeight-lowerleftX)/windowHeight;
	                    }
                	}

                  
            }
            Integer [] coords = new Integer [(MATRIX_SIZE-2)*currentVertex.size()];
            int counter =0;
            for (float[] cs: currentVertex) {
            	for (int i=0; i<MATRIX_SIZE-2; i++) {
            		coords[counter]=Math.round(cs[i]);
            		counter++;
            	}
            }
            currentVertex = null;
            return coords;
        }
    }
    
    public static void startVertex() {
    	currentVertex=new ArrayList<>();
    }
    
    
    //TODO REMOVE THESE FUNCTIONS
    public static void printMatrix(float[][] matrix) {
    	for (int i=0; i<MATRIX_SIZE; i++) {
    		for (int j=0; j<MATRIX_SIZE; j++) {
    			System.out.print(" "+matrix[i][j]);
    		}
    		System.out.println();
    	}
    }
    
    public static void setWindowWidth(int w) {
    	windowWidth = w;
    }
    
    public static void setWindowHeight(int h) {
    	windowHeight = h;
    }

    public static void mulMatrix(float[][] mat1, float[][] mat2, float[][] destination) {
    	float value;
    	for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                value = 0;
                for (int z = 0; z < MATRIX_SIZE; z++) {
                    value += mat1[i][z] * mat2[z][j];
                }
                destination[i][j] = value;
            }
        }
    }
    
    public static float[][] mulMatrix(float[][] mat1, float[][] mat2) {
    	float[][] destination =new float[MATRIX_SIZE][MATRIX_SIZE];
    	float value;
    	for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                value = 0;
                for (int z = 0; z < MATRIX_SIZE; z++) {
                    value += mat1[i][z] * mat2[z][j];
                }
                destination[i][j] = value;
            }
        }
    	return destination;
    }
}
