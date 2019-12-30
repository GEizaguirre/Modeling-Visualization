using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class MyFunctions : MonoBehaviour
{
	float resultado;
	private Vector3 target;
	
    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
    	float movimientoHorizontal = Input.GetAxis("Horizontal");
    	float movimientoVertical = Input.GetAxis("Vertical");
    	target = new Vector3(movimientoVertical, movimientoHorizontal, 0.0f);
    	transform.RotateAround(transform.Find("ChangedPivot").position, target ,50*Time.deltaTime);
       	//float movimientoHorizontal = Input.GetAxis("Horizontal");
       	//float movimientoVertical = Input.GetAxis("Vertical");
       	//transform.Rotate(movimientoVertical, movimientoHorizontal, 0);
        
    }
}
