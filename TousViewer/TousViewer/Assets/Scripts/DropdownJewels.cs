using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class DropdownJewels : MonoBehaviour
{
    List<string> names = new List<string>{ "None", "Ruby and silver ring" };


    public Dropdown dropdown;
    public Text selectedName;
    GameObject currentJewel = null;
    Renderer childRenderer;


    public void Dropdown_IndexChanged(int index){
    	selectedName.text = names[index];


    	switch (names[index]) {
    		case "None":

    			if (currentJewel!=null) {
	    			foreach (Transform child in currentJewel.transform)
					{
						childRenderer = child.GetComponent<Renderer>();
						childRenderer.enabled = false;
					}
				}
    			currentJewel = null;
    			
    			break;
    		case "Ruby and silver ring":
    			currentJewel = GameObject.Find("RubyRing");
 
    			break;
    	}
    	if (currentJewel!=null) {

    		foreach (Transform child in currentJewel.transform)
			{
				childRenderer = child.GetComponent<Renderer>();
				childRenderer.enabled = true;
			}
    	}


    }

    void Start(){

    	PopulateList();
    }

    void PopulateList(){
    	// TODO; read values from file.
    	
    	dropdown.AddOptions(names);
    }

}
