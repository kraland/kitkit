package model;


public class MapDouble {

	/**
	 * Map de double
	 */
	double[][] map;
		
	/**
	 * Constructeur de map de double. Celui-ci est utile lorsqu'on souhaite travailler
	 * sur la "reference" et non simplement la valeur. En travaillant sur un objet de
	 * type MapDouble place en parametre d'une fonction la map sera bien modifiee meme
	 * lorsqu'on sera en dehors de la fonction.
	 * 
	 * On place en parametre le nombre de colonnes, de lignes, et la valeur dans chaque ligne
	 * 
	 * @param column
	 * @param row
	 * @param row
	 */
	public MapDouble(int column, int row, int value)
	{
		// On initialise la map de double
		map = new double[column][row];
		
		// On parcourt chaque colonne
		for (int i = 0; i < column; i++)
		{
			// On parcourt chaque ligne
			for(int j = 0; j < row; j++)
			{
				map[i][j] = value;
			}
		}	
	}
	
	/**
	 * Renvoie la map de double
	 * @return
	 */	
	public double[][] getMap()
	{
		return map;
	}
	
	/**
	 * Change la map de double
	 * @param map
	 */
	public void setMap(double[][] map)
	{
		this.map = map;
	}

}
