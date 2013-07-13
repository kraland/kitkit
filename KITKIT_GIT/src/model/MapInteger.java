package model;

import java.util.ArrayList;
import java.util.List;

public class MapInteger{

	/**
	 * Map d'entier
	 */
	List<List<Integer>> map;
	
	/**
	 * Constructeur de map d'integer. Celui-ci est utile lorsqu'on souhaite travailler
	 * sur la "reference" et non simplement la valeur. En travaillant sur un objet de
	 * type MapInteger place en parametre d'une fonction la map sera bien modifiee meme
	 * lorsqu'on sera en dehors de la fonction
	 */
	public MapInteger()
	{
		// On initialise la map
		map = new ArrayList<List<Integer>>();
	}
	
	/**
	 * Renvoie la map d'entier
	 * @return
	 */
	public List<List<Integer>> getMap()
	{
		return map;
	}
	
	/**
	 * Change la map d'entier
	 * @param map
	 */
	public void setMap(List<List<Integer>> map)
	{
		this.map = map;
	}

	
	public List<List<Integer>> clone()
	{
		List<List<Integer>> mapCloned = new ArrayList<List<Integer>>();
		
		if(map != null)
		{
			for(List<Integer> listInteger : map)
			{
				mapCloned.add(new ArrayList<Integer>(listInteger));
			}
		}

		return mapCloned;
	}
}
