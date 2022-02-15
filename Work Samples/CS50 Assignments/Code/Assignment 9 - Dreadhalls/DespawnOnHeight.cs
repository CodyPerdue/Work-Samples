using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class DespawnOnHeight : MonoBehaviour {

	public LevelCounter level;

	// Use this for initialization
	void Start () {

	}
	
	// Update is called once per frame
	void Update () {
		if (transform.position.y < -1) {
			level.Reset();
			SceneManager.LoadScene("GameOver");
		}
	}
}
