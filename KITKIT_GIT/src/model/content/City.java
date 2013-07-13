package model.content;

import java.util.ArrayList;
import java.util.List;

 
 public class City {   
 	
 	/**
 	 * Identifiant de la ville
 	 */
 	private int i_id;
	
 	/**
 	 * Abscisse de la ville
 	 */
 	private int i_x;
 	
 	/**
 	 * Ordonnee de la ville
 	 */
 	private int i_y;
 		
	/**
	 * Nom de la ville
	 */
	private String s_name;
	
	/**
	 * Map 2D
	 */
	List<List<String>> map2d;
		
	/**
	 * Liste de Batiment dans la ville
	 */
	private List<Building> listBuilding;

		
	/**
	 * Map 3D
	 */
	List<List<String>> map3d;
	
 	/**
 	 * Province ou se trouve la ville
 	 */
 	private District district;
 	
 	/**
 	 * Liste de ville connectee par avion
 	 */
 	private List<City> listCityConnectedByPlane;
 	
 	/**
 	 * Constructeur
 	 */
	public City()
	{
		listBuilding = new ArrayList<Building>();
		listCityConnectedByPlane = new ArrayList<City>();
	}

	/**
	 * Renvoie le batiment qui a l'identifiant fourni en parametre
	 * @param i_idBuilding
	 * @return
	 */
	public Building getBuilding(int i_idBuilding)
	{
		// On parcourt la liste de batiment de la ville
		for(Building buildingTmp : listBuilding)
		{
			// Si l'identifiant du batiment est egal a celui fourni en parametre
			if(buildingTmp.getI_id() == i_idBuilding)
				return buildingTmp;
		}
		
		// Renvoie null
		return null;
	}
	
	/**
	 * Ajoute une ville connectee par avion a la liste
	 * @param cityConnectedByPlane
	 */
	public void addCityConnectedByPlane(City cityConnectedByPlane)
	{
		listCityConnectedByPlane.add(cityConnectedByPlane);
	}
	
	/**
	 * Renvoie la liste de batiment
	 * @return
	 */
	public List<City> getListCityConnectedByPlane() {
		return listCityConnectedByPlane;
	}
	
	/**
	 * Ajoute un batiment a la liste
	 * @param buildingToAdd
	 */
	public void addBuilding(Building buildingToAdd)
	{
		listBuilding.add(buildingToAdd);
	}

	/**
	 * Renvoie la liste de batiment
	 * @return
	 */
	public List<Building> getListBuilding() {
		return listBuilding;
	}

	/**
	 * Renvoie l'id de la ville
	 * @return
	 */
	public int getI_id() {
		return i_id;
	}

	/**
	 * Change l'id de la ville
	 * @param i_id
	 */
	public void setI_id(int i_id) {
		this.i_id = i_id;
	}

	/**
	 * Renvoie l'abscisse de la ville
	 * @return
	 */
	public int getI_x() {
		return i_x;
	}

	/**
	 * Change l'abscisse de la ville
	 * @param i_x
	 */
	public void setI_x(int i_x) {
		this.i_x = i_x;
	}

	/**
	 * Renvoie l'ordonnee de la ville
	 * @return
	 */
	public int getI_y() {
		return i_y;
	}

	/**
	 * Change l'ordonne de la ville
	 * @param i_y
	 */
	public void setI_y(int i_y) {
		this.i_y = i_y;
	}
	
	/**
	 * Renvoie le nom de la ville
	 * @return
	 */
	public String getS_name() {
		return s_name;
	}

	/**
	 * Change le nom de la ville
	 * @param s_name
	 */
	public void setS_name(String s_name) {
		this.s_name = s_name;
	}

	/**
	 * Change la map 2D
	 * @param map2d
	 */
	public void setMap2D(List<List<String>> map2d) {
		this.map2d = map2d;
	}
	
	/**
	 * Renvoie la map 2D
	 * @return
	 */
	public List<List<String>> getMap2D() {
		return map2d;
	}
	
	/**
	 * Change la map 3D
	 * @param map3d
	 */
	public void setMap3D(List<List<String>> map3d) {
		this.map3d = map3d;
	}
    
	/**
	 * Renvoie la province ou se trouve la ville
	 * @return
	 */
	public District getDistrict() {
		return district;
	}

	/**
	 * Change la province ou se trouve la ville
	 * @param district
	 */
	public void setDistrict(District district) {
		this.district = district;
	}



 }
