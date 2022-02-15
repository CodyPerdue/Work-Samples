using UnityEngine;
using System.Collections;

public class GemSpawner : MonoBehaviour
{

	public GameObject[] prefabs;

	// Use this for initialization
	void Start()
	{

		// infinite gem spawning function, asynchronous
		StartCoroutine(SpawnGem());
	}

	// Update is called once per frame
	void Update()
	{

	}

	IEnumerator SpawnGem()
	{
		while (true)
		{

			// spawn only one gem
			int gemsThisRow = 1;

			// instantiate the gem
			for (int i = 0; i < gemsThisRow; i++)
			{
				Instantiate(prefabs[Random.Range(0, prefabs.Length)], new Vector3(26, Random.Range(-10, 10), 10), Quaternion.identity);
			}

			// pause 10-15 seconds until the next gem spawns
			yield return new WaitForSeconds(Random.Range(10, 15));
		}
	}
}
