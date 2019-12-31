using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ObjectRotateMouse : MonoBehaviour
{
	bool mousePressed = false;
	float scaleFactor = 5f;
	Vector3 vPrevPos = Vector3.zero;
	Vector3 vPosDelta = Vector3.zero;

	void Update(){

	 

	    if(Input.GetMouseButton(0)){
	    	if (mousePressed){
		    	vPosDelta = Input.mousePosition - vPrevPos;
		    	//transform.Rotate(transform.up, -Vector3.Dot(vPosDelta, Camera.main.transform.right), Space.World);
		    	//Debug.Log("Dif: "+Vector3.Dot(vPosDelta, Camera.main.transform.right));
		    	//Debug.Log(" mp: "+Input.mousePosition.x+", "+Input.mousePosition.y+", "+Input.mousePosition.z);
		    	//Debug.Log(" pp: "+vPrevPos.x+", "+vPrevPos.y+", "+vPrevPos.z);
		    	// Control for minimal mouse movement
		    	if ((Vector3.Dot(vPosDelta, Camera.main.transform.right) > 10)||(Vector3.Dot(vPosDelta, Camera.main.transform.right) < -10)) {
		    		if (Vector3.Dot(transform.up, Vector3.up)>=0)
		    			transform.RotateAround(transform.Find("ChangedPivot").position, transform.up, -Vector3.Dot(vPosDelta, Camera.main.transform.right)*Time.deltaTime*scaleFactor);
		    		else
		    			transform.RotateAround(transform.Find("ChangedPivot").position, transform.up, Vector3.Dot(vPosDelta, Camera.main.transform.right)*Time.deltaTime*scaleFactor);
		    	}
		    	if ((Vector3.Dot(vPosDelta, Camera.main.transform.up) > 10)||(Vector3.Dot(vPosDelta, Camera.main.transform.up) < -10)) {
		    		transform.RotateAround(transform.Find("ChangedPivot").position, Camera.main.transform.right, Vector3.Dot(vPosDelta, Camera.main.transform.up)*Time.deltaTime*scaleFactor);
		    	}
		    	//transform.RotateAround(transform.Find("ChangedPivot").position, Vector3.up, 0);
		    	//Debug.Log(""+-Vector3.Dot(vPosDelta, Camera.main.transform.right));
		    }
		    else mousePressed = true;
		    vPrevPos = Input.mousePosition;
	    }
	    else mousePressed=false;
	    
	}
}
