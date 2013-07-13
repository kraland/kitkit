package model.content;

import java.util.ArrayList;
import java.util.List;


public class Building {   
	
	/**
	 * Id du batiment
	 */
	private int i_id;
	
	/**
	 * Abscisse du batiment
	 */
	private int i_x;
	
	/**
	 * Ordonnee du batiment
	 */
	private int i_y;
	
	/**
	 * Type du batiment
	 */
	private String s_type;
	
	/**
	 * Nom du batiment
	 */
	private String s_name;

	/**
	 * Personne a qui appartient le batiment
	 */
	private Person owner;
		
	/**
	 * Valeur du batiment
	 */
	private int i_value;
	
	/**
	 * Booleen indiquant si le commerce du batiment est ferme
	 */
	private boolean isClosed = false;
	
	/**
	 * Nombre de point de batiment
	 */
	private int i_nbPdB;
	
	/**
	 * Salaire dans le batiment
	 */
	private int i_salary;
	
	/**
	 * Nombre de salaire dans le batiment
	 */
	private int i_nbSalary;
	
	/**
	 * Booleen indiquant si il est possible d'acheter le batiment
	 */
	private boolean isPossibleToBuy = false;
	
	/**
	 * Prix de vente du batiment
	 */
	private int i_soldPrice;
				
	/**
	 * Booleen indiquant si le batiment est reserve a la vente
	 */
	private boolean isReservedForBuy = false;
	
	/**
	 * Liste du materiel contenu dans le batiment
	 */
	private List<Material> listMaterial;
	
	/**
	 * Booleen indiquant si le batiment est public
	 */
	private boolean isPublicBuilding;
	
	
	
	/**
	 * Nom du type du batiment
	 */
	//private String s_nameType;
	
	/**
	 * Id de l'image du batiment
	 */
	//private int i_idImage;
	
	/**
	 * Adresse url du batiment
	 */
 	//private String s_url;
		
	/**
	 * Ville dans laquelle le batiment se trouve
	 */
	private City city;
			
	/**
	 * Niveau du batiment
	 */
	private int i_level;
	
	/**
	 * Constructeur
	 */
	public Building()
	{
		// On initialise la liste de materiel dans ce batiment
		listMaterial = new ArrayList<Material>();
	}

	/**
	 * Ajoute un materiel a la liste de materiel du batiment
	 * @param materialToAdd
	 */
	public void addMaterial(Material materialToAdd)
	{
		// Ajout d'un materiel a la liste de materiel
		listMaterial.add(materialToAdd);
	}
	
	/**
	 * Renvoie la liste de materiel present dans le batiment
	 * @return
	 */
	public List<Material> getListMaterial() {
		return listMaterial;
	}

	/**
	 * Renvoie l'ID du batiment
	 * @return
	 */
	public int getI_id() {
		return i_id;
	}

	/**
	 * Change l'ID du batiment
	 * @param i_id
	 */
	public void setI_id(int i_id) {
		this.i_id = i_id;
	}

	/**
	 * Renvoie l'abscisse du batiment
	 * @return
	 */
	public int getI_x() {
		return i_x;
	}

	/**
	 * Change l'abscisse du batiment
	 * @param i_x
	 */
	public void setI_x(int i_x) {
		this.i_x = i_x;
	}

	/**
	 * Renvoie l'ordonnee du batiment
	 * @return
	 */
	public int getI_y() {
		return i_y;
	}

	/**
	 * Change l'ordonnee du batiment
	 * @param i_y
	 */
	public void setI_y(int i_y) {
		this.i_y = i_y;
	}

	/**
	 * Renvoie le nom du batiment
	 * @return
	 */
	public String getS_name() {
		return s_name;
	}

	/**
	 * Change le nom du batiment
	 * @param s_name
	 */
	public void setS_name(String s_name) {
		this.s_name = s_name;
	}

	/**
	 * Renvoie le proprietaire du batiment
	 * @return
	 */
	public Person getOwner() {
		return owner;
	}

	/**
	 * Change le proprietaire du batiment
	 * @param owner
	 */
	public void setOwner(Person owner) {
		this.owner = owner;
	}

	/**
	 * Renvoie la valeur
	 * @return
	 */
	public int getI_value() {
		return i_value;
	}

	/**
	 * Change la valeur
	 * @param i_value
	 */
	public void setI_value(int i_value) {
		this.i_value = i_value;
	}

	/**
	 * Renvoie le booleen indiquant si le commerce est ferme
	 * @return
	 */
	public boolean isClosed() {
		return isClosed;
	}

	/**
	 * Change le booleen indiquant si le commerce est ferme
	 * @param isClosed
	 */
	public void setIsClosed(boolean isClosed) {
		this.isClosed = isClosed;
	}

	/**
	 * Renvoie le nombre de PDB
	 * @return
	 */
	public int getI_nbPdB() {
		return i_nbPdB;
	}

	/**
	 * Change le nombre de PDB
	 * @param i_nbPdB
	 */
	public void setI_nbPdB(int i_nbPdB) {
		this.i_nbPdB = i_nbPdB;
	}

	/**
	 * Renvoie le salaire
	 * @return
	 */
	public int getI_salary() {
		return i_salary;
	}

	/**
	 * Change le salaire
	 * @param i_salary
	 */
	public void setI_salary(int i_salary) {
		this.i_salary = i_salary;
	}

	/**
	 * Renvoie le nombre de salaire
	 * @return
	 */
	public int getI_nbSalary() {
		return i_nbSalary;
	}

	/**
	 * Change le nombre de salaire
	 * @param i_nbSalary
	 */
	public void setI_nbSalary(int i_nbSalary) {
		this.i_nbSalary = i_nbSalary;
	}

	/**
	 * Renvoie le booleen indiquant si c'est possible d'acheter
	 * @return
	 */
	public boolean isPossibleToBuy() {
		return isPossibleToBuy;
	}

	/**
	 * Change le booleen indiquant si c'est possible d'acheter
	 * @param isPossibleToBuy
	 */
	public void setIsPossibleToBuy(boolean isPossibleToBuy) {
		this.isPossibleToBuy = isPossibleToBuy;
	}

	/**
	 * Renvoie le prix de vente du batiment
	 * @return
	 */
	public int getI_soldPrice() {
		return i_soldPrice;
	}

	/**
	 * Change le prix de vente du batiment
	 * @param i_soldPrice
	 */
	public void setI_soldPrice(int i_soldPrice) {
		this.i_soldPrice = i_soldPrice;
	}

	/**
	 * Renvoie le booleen indiquant si le batiment est reserve
	 * @return
	 */
	public boolean isReservedForBuy() {
		return isReservedForBuy;
	}

	/**
	 * Change le booleen indiquant si le batiment est reserve
	 * @param isReservedForBuy
	 */
	public void setIsReservedForBuy(boolean isReservedForBuy) {
		this.isReservedForBuy = isReservedForBuy;
	}

	
	/**
	 * Renvoie la ville ou se trouve le batiment
	 * @return
	 */
	public City getCity() {
		return city;
	}

	/**
	 * Change la ville ou se trouve le batiment
	 * @param city
	 */
	public void setCity(City city) {
		this.city = city;
	}

	/**
	 * Renvoie le type du batiment
	 * @return
	 */
	public String getS_type() {
		return s_type;
	}

	/**
	 * Change le type du batiment
	 * @param s_type
	 */
	public void setS_type(String s_type) {
		this.s_type = s_type;
	}

	/**
	 * Renvoie le booleen indiquant si le batiment est public
	 * @return
	 */
	public boolean isPublicBuilding() {
		return isPublicBuilding;
	}

	/**
	 * Change le booleen indiquant si le batiment est public
	 * @param isPublicBuilding
	 */
	public void setIsPublicBuilding(boolean isPublicBuilding) {
		this.isPublicBuilding = isPublicBuilding;
	}

	/**
	 * Renvoie le niveau du batiment
	 * @return
	 */
	public int getI_level() {
		return i_level;
	}

	/**
	 * Change le niveau du batiment
	 * @param i_level
	 */
	public void setI_level(int i_level) {
		this.i_level = i_level;
	}


 }
