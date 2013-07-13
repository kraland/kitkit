package model.content;

import java.util.ArrayList;
import java.util.List;

 
 public class Person {   
 	
	/**
	 * Liste de batiments appartenant a la personne
	 */
	List<Building> listBuilding;

	/**
	 * Identifiant associe a la personne
	 */
	private int i_id;

	/**
	 * Nom de la personne
	 */
	private String s_name;
	
	/**
	 * Nom de la personne en html
	 */
	private String s_nameHtml;
	
	/**
	 * Nom de la personne sans smiley
	 */
	private String s_nameWithoutSmiley;
	
	/**
	 * Constructeur
	 */
	public Person()
	{
		listBuilding = new ArrayList<Building>();
	}
	
	/**
	 * Constructeur
	 */
	public Person(String s_name)
	{
		this.s_name = s_name;
		listBuilding = new ArrayList<Building>();
	}

	/**
	 * Renvoie l'identifiant de la personne
	 * @return
	 */
	public int getI_id() {
		return i_id;
	}

	/**
	 * Change l'identifiant de la personne
	 * @param i_id
	 */
	public void setI_id(int i_id) {
		this.i_id = i_id;
	}

	/**
	 * Renvoie le nom de la personne
	 * @return
	 */
	public String getS_name() {
		return s_name;
	}

	/**
	 * Change le nom de la personne
	 * @param s_name
	 */
	public void setS_name(String s_name) {
		this.s_name = s_name;
	}
	
	/**
	 * On ajoute un batiment a la liste de batiments
	 * @param buildingToAdd
	 */
	public void addBuilding(Building buildingToAdd)
	{
		listBuilding.add(buildingToAdd);
	}

	/**
	 * Renvoie la liste des batiments
	 * @return
	 */
	public List<Building> getListBuilding() {
		return listBuilding;
	}

	/**
	 * Renvoie le nom de la personne en html
	 * @return
	 */
	public String getS_nameHtml() {
		return s_nameHtml;
	}

	/**
	 * Change le nom de la personne en html
	 * @param s_nameHtml
	 */
	public void setS_nameHtml(String s_nameHtml) {
		this.s_nameHtml = s_nameHtml;
	}

	/**
	 * Renvoie le nom de la personne sans smiley
	 * @return
	 */
	public String getS_nameWithoutSmiley() {
		return s_nameWithoutSmiley;
	}

	/**
	 * Change le nom de la personne sans smiley
	 * @param s_nameWithoutSmiley
	 */
	public void setS_nameWithoutSmiley(String s_nameWithoutSmiley) {
		this.s_nameWithoutSmiley = s_nameWithoutSmiley;
	}


 }
