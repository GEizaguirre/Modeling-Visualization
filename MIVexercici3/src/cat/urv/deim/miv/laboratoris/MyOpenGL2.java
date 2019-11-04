package cat.urv.deim.miv.laboratoris;

import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

import cat.urv.deim.miv.Application;

import java.util.ArrayList;

import static cat.urv.deim.miv.Application.GL_MODELVIEW;
import static cat.urv.deim.miv.Application.GL_PROJECTION;

public class MyOpenGL2 {
    private final int MATRIX_SIZE = 4;
    private int angle;
    private float[][] currentMatrix;
    private float[][] projectionMatrix = new float[MATRIX_SIZE][MATRIX_SIZE];
    private float[][] modelviewMatrix = new float[MATRIX_SIZE][MATRIX_SIZE];
    private Stack <float[][]> projectionStack = new Stack<>();
    private Stack <float[][]> modelviewStack = new Stack<>();
    private int currentMatrixType;
    private int lowerleftX = 0;
    private int lowerleftY = 0;
    private int viewportWidth;
    private int viewportHeight;
    private ArrayList<float[]> currentVertex;
    private int windowWidth;
    private int windowHeight;

    public MyOpenGL2() {
    }

    public int getAngle() {
        return angle;
    }
    
    public float[][] pushProjection () {
    	float[][] newProj = new float[MATRIX_SIZE][MATRIX_SIZE];
    	for (int i=0; i<MATRIX_SIZE; i++) {
    		for (int j=0; j<MATRIX_SIZE; j++) {
    			newProj[i][j]=projectionMatrix[i][j];
    		}
    	}
    	return projectionStack.push(newProj);
    }
    
    public float[][] pushViewmodel () {
    	float[][] newModel = new float[MATRIX_SIZE][MATRIX_SIZE];
    	for (int i=0; i<MATRIX_SIZE; i++) {
    		for (int j=0; j<MATRIX_SIZE; j++) {
    			newModel[i][j]=modelviewMatrix[i][j];
    		}
    	}
    	return modelviewStack.push(newModel);
    }
    
    public void popProjection () {
    	float[][] newProj = projectionStack.pop();
    	for (int i=0; i<MATRIX_SIZE; i++) {
    		for (int j=0; j<MATRIX_SIZE; j++) {
    			projectionMatrix[i][j]=newProj[i][j];
    		}
    	}
    }
    
    public void popViewmodel () {
    	float[][] newModel = modelviewStack.pop();
    	for (int i=0; i<MATRIX_SIZE; i++) {
    		for (int j=0; j<MATRIX_SIZE; j++) {
    			modelviewMatrix[i][j]=newModel[i][j];
    		}
    	}
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public float[][] getCurrentMatrix() {
        return currentMatrix;
    }

    public void setCurrentMatrix(int model) {
        switch (model){
            case GL_MODELVIEW:
                currentMatrix = modelviewMatrix; break;
            case GL_PROJECTION:
                currentMatrix = projectionMatrix; break;
        }
        currentMatrixType = model;
    }

    public float[][] getProjectionMatrix() {
        return projectionMatrix;
    }

    public void setProjectionMatrix(float[][] projectionMatrix) {
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                this.projectionMatrix[i][j] = projectionMatrix[i][j];
            }
        }
        //System.out.println("Projection matrix:");
        //printMatrix(this.projectionMatrix);
    }

    public float[][] getModelviewMatrix() {
        return modelviewMatrix;
    }

    public void setModelviewMatrix(float[][] modelviewMatrix) {
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                this.modelviewMatrix[i][j] = modelviewMatrix[i][j];
            }
        }
    }

    public int getLowerleftX() {
        return lowerleftX;
    }

    public void setLowerleftX(int lowerleftX) {
        this.lowerleftX = lowerleftX;
    }

    public int getLowerleftY() {
        return lowerleftY;
    }

    public void setCurrentMatrix(float[][] currentMatrix) {
        this.currentMatrix = currentMatrix;
    }

    public int getCurrentMatrixType() {
        return currentMatrixType;
    }

    public void setCurrentMatrixType(int currentMatrixType) {
        this.currentMatrixType = currentMatrixType;
    }

    public void setLowerleftY(int lowerleftY) {
        this.lowerleftY = lowerleftY;
    }

    public int getViewportWidth() {
        return viewportWidth;
    }

    public void setViewportWidth(int viewportWidth) {
        this.viewportWidth = viewportWidth;
    }

    public int getViewportHeight() {
        return viewportHeight;
    }

    public void setViewportHeight(int viewportHeight) {
        this.viewportHeight = viewportHeight;
    }

    public void initVertex() {
        currentVertex = new ArrayList<>();
    }

    public void addVertex(float[] coord) {
        if (currentVertex != null) {
            currentVertex.add(coord);
        } else System.out.println("Did not call glBegin");
    }

    public Integer[] getDrawVertex() {
    	
    	/*System.out.println("Projection");
    	printMatrix(projectionMatrix);
    	System.out.println("Modelview");
    	printMatrix(modelviewMatrix);*/
        if (currentVertex == null) {
            // FIXME: generate GL_INVALID_OPERATION
            System.out.println("glBegin must be called first.");
            return null;
        } else if (currentVertex.size() < 2) {
            System.out.println("Add at least 2 vertex");
            return null;
        } else {
            // Projection matrix * Modelview matrix.
        	float [][] workingMatrix = new float [MATRIX_SIZE][MATRIX_SIZE];
        	//printMatrix(projectionMatrix);
            //mulMatrix(projectionMatrix, modelviewMatrix, workingMatrix);
        	mulMatrix(projectionMatrix, modelviewMatrix, workingMatrix);
 
            printMatrix(workingMatrix);
            //System.out.println();
            // Current matrix * each vertex.
            //printMatrix(workingMatrix);
            float[] new_coords = new float[MATRIX_SIZE];
            float value;
            for (float[] coords : currentVertex) {
                for (int i = 0; i < MATRIX_SIZE; i++) {
                    value = 0;
                    for (int j = 0; j < MATRIX_SIZE; j++) {
                        value = value + workingMatrix[i][j] * coords[j];
                    }
                    new_coords[i] = value;
                    System.out.print(" "+value);
               
                }
                
                // Normalize coordinates
                for (int i = 0; i < MATRIX_SIZE; i++) {
                    new_coords[i] = new_coords[i]/new_coords[MATRIX_SIZE-1];
                    //System.out.print(" "+value);
               
                }

                for (int i = 0; i < MATRIX_SIZE; i++) {
                    //System.out.print(" "+new_coords[i]);
                    if (i == 0) {
                    	//System.out.print("x");
                    	// Get window coordinates.
                    	if (new_coords[i] >= 0) {
                    		//System.out.print(" "+new_coords[i]+"->");
                    		coords[i] = windowWidth/2 + new_coords[i]*windowWidth/2;
                    		//System.out.print(new_coords[i]);
                    	}
                    	else {
                    		
                    		coords[i] = windowWidth/2*(1-Math.abs(new_coords[i]));
                    	}
                    	// Viewport transformation.
                    	coords[i] = lowerleftX + (coords[i]) * (viewportWidth-lowerleftX)/windowWidth;
                    }
                    else if (i == 1) {
                    	//System.out.print("y");
                    	// Get window coordinates.
                    	if (new_coords[i] >= 0) coords[i] = windowHeight/2 + new_coords[i]*windowHeight/2;
                    	else coords[i] = windowHeight/2*(1-Math.abs(new_coords[i]));
                    	//System.out.print(" "+new_coords[i]);
                    	
                    	// Viewport transformation.
                    	coords[i] = lowerleftY + (coords[i]) * (viewportHeight-lowerleftX)/windowHeight;
                    }
                    else coords[i] = new_coords[i];
                    //System.out.print(" "+new_coords[i]);
                    
                    //System.out.println(coords[i]);
                    //System.out.print("->"+coords[i]);
                	
                }
                //for (float f : coords) System.out.print(" "+f);
                System.out.println();
              
                
            }
            //System.out.println();
            Integer [] coords = new Integer [(MATRIX_SIZE-2)*currentVertex.size()];
            int counter =0;
            for (float[] cs: currentVertex) {
            	for (int i=0; i<MATRIX_SIZE-2; i++) {
            		coords[counter]=Math.round(cs[i]);
            		counter++;
            	}
            }
            this.currentVertex = null;
            //for (Integer c: coords) System.out.print(c+" ");
            //System.out.println();
            return coords;
        }
    }
    
    public void startVertex() {
    	this.currentVertex=new ArrayList<>();
    }
    
    
    //TODO REMOVE THESE FUNCTIONS
    public void printMatrix(float[][] matrix) {
    	for (int i=0; i<MATRIX_SIZE; i++) {
    		for (int j=0; j<MATRIX_SIZE; j++) {
    			System.out.print(" "+matrix[i][j]);
    		}
    		System.out.println();
    	}
    }
    
    public void setWindowWidth(int w) {
    	this.windowWidth = w;
    }
    
    public void setWindowHeight(int h) {
    	this.windowHeight = h;
    }

    public void mulMatrix(float[][] mat1, float[][] mat2, float[][] destination) {
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
    
    public float[][] mulMatrix(float[][] mat1, float[][] mat2) {
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
