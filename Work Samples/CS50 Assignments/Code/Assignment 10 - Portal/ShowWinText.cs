using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class ShowWinText : MonoBehaviour {

	public GameObject collider;
	private Text text;

	// Use this for initialization
	void Start () {
		text = GetComponent<Text>();

		// start text off as completely transparent black
		text.color = new Color(0, 0, 0, 0);
	}
	
	// Update is called once per frame
	void Update () {
		if (collider == null)
        {
			// reveal text
			text.color = new Color(0, 0, 0, 1);
			text.text = "You reached the goal!";
		}
	}
}
