package model.content;

import java.util.ArrayList;
import java.util.List;

 
 public class District {   
 	
 	/**
 	 * Identifiant de la province
 	 */
 	private int i_id;
	
 	/**
 	 * Abscisse de la province
 	 */
 	private int i_x;
 	
 	/**
 	 * Ordonnee de la province
 	 */
 	private int i_y;
 	
	/**
	 * Nom de la province
	 */
	private String s_name;
	
	/**
	 * Map 2D
	 */
	List<List<Integer>> map2d;
	
	/**
	 * Map 3D
	 */
	List<List<Integer>> map3d;
	
	/**
	 * Liste de ville dans la province
	 */
	private List<City> listCity;
	
	/**
	 * Constructeur
	 */
	public District()
	{
		// On initialise la liste de ville
		listCity = new ArrayList<City>();
	}

	/**
	 * Renvoie l'id de la province
	 * @return
	 */
	public int getI_id() {
		return i_id;
	}

	/**
	 * Change l'id de la province
	 * @param i_id
	 */
	public void setI_id(int i_id) {
		this.i_id = i_id;
	}

	/**
	 * Renvoie l'abscisse de la province
	 * @return
	 */
	public int getI_x() {
		return i_x;
	}

	/**
	 * Change l'abscisse de la province
	 * @param i_x
	 */
	public void setI_x(int i_x) {
		this.i_x = i_x;
	}

	/**
	 * Renvoie l'ordonnee de la province
	 * @return
	 */
	public int getI_y() {
		return i_y;
	}

	/**
	 * Change l'ordonne de la province
	 * @param i_y
	 */
	public void setI_y(int i_y) {
		this.i_y = i_y;
	}
	
	/**
	 * Renvoie le nom de la province
	 * @return
	 */
	public String getS_name() {
		return s_name;
	}

	/**
	 * Change le nom de la province
	 * @param s_name
	 */
	public void setS_name(String s_name) {
		this.s_name = s_name;
	}

	/**
	 * Change la map 2D
	 * @param map2d
	 */
	public void setMap2D(List<List<Integer>> map2d) {
		this.map2d = map2d;
	}
	
	/**
	 * Renvoie la map 2D
	 * @return
	 */
	public List<List<Integer>> getMap2D() {
		return map2d;
	}
	
	/**
	 * Change la map 3D
	 * @param map3d
	 */
	public void setMap3D(List<List<Integer>> map3d) {
		this.map3d = map3d;
	}
	
	/**
	 * Renvoie la map 3D
	 * @return
	 */
	public List<List<Integer>> getMap3D() {
		return map3d;
	}

	/**
	 * On ajoute une ville
	 * @param cityToAdd
	 */
	public void addCity(City cityToAdd)
	{
		listCity.add(cityToAdd);
	}
	
	/**
	 * Renvoie la liste de ville
	 * @return
	 */
	public List<City> getListCity() {
		return listCity;
	}
	
}
