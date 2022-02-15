using UnityEngine;
using UnityEngine.UI;
using System.Collections;

[RequireComponent(typeof(Text))]
public class LevelCounter : MonoBehaviour
{
	public LevelCounter instance = null;
	private Text text;
	private static int level = 1;

	// Use this for initialization
	void Start()
	{
		instance = this;
		text = GetComponent<Text>();
		changeLevelText();
	}

	// Update is called once per frame
	void Update()
	{
		
	}

	// Increment to the next level
	public void IncrementLevel()
    {
		level++;
		changeLevelText();
	}

	// Reset back to the first level
	public void Reset()
    {
		level = 1;
		changeLevelText();
	}

	public void changeLevelText()
    {
		text.text = "Level: " + level;
    }
}
