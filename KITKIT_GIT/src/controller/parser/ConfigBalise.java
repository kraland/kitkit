package controller.parser;

/**
 * Balises presentes dans confContentBatiment.xml
 */
public class ConfigBalise{
	
	/**
	 * Balise affichant le timestamp de mise a jour du modele
	 */
	public static String BALISE_TIMESTAMP;
	
	/**
	 * Balise d'une province
	 */
	public static String BALISE_DISTRICT;
	
	/**
	 * Balise de l'identifiant d'une province
	 */
	public static String BALISE_DISTRICT_ID;
	
	/**
	 * Balise de l'identifiant d'un empire d'une province
	 */
	public static String BALISE_DISTRICT_ID_EMPIRE;
	
	/**
	 * Balise de l'abscisse d'une province
	 */
	public static String BALISE_DISTRICT_X;
	
	/**
	 * Balise de l'ordonnee d'une province
	 */
	public static String BALISE_DISTRICT_Y;

	/**
	 * Balise du nom d'une province
	 */
	public static String BALISE_DISTRICT_NAME;
	
	/**
	 * Balise de la map horizontale d'une province (2D)
	 */
	public static String BALISE_DISTRICT_MAP;

	
	/**
	 * Balise d'une ville
	 */
	public static String BALISE_CITY;
	
	/**
	 * Balise de l'identifiant d'une ville
	 */
	public static String BALISE_CITY_ID;
	
	/**
	 * Balise de l'abscisse d'une ville
	 */
	public static String BALISE_CITY_X;
	
	/**
	 * Balise de l'ordonnee d'une ville
	 */
	public static String BALISE_CITY_Y;

	/**
	 * Balise du nom d'une ville
	 */
	public static String BALISE_CITY_NAME;

	/**
	 * Balise de la map horizontale d'une ville (2D)
	 */
	public static String BALISE_CITY_MAP;
	
	
	/**
	 * Balise d'un batiment
	 */
	public static String BALISE_BUILDING;
	
	/**
	 * Balise d'un identifiant d'un batiment
	 */
	public static String BALISE_BUILDING_ID;
	
	/**
	 * Balise de l'abscisse d'une ville
	 */
	public static String BALISE_BUILDING_X;
	
	/**
	 * Balise de l'ordonnee d'une ville
	 */
	public static String BALISE_BUILDING_Y;
	
	/**
	 * Balise du type du batiment
	 */
	public static String BALISE_BUILDING_TYPE;
	
	/**
	 * Balise du niveau du batiment
	 */
	public static String BALISE_BUILDING_LEVEL;
	
	/**
	 * Balise du nom d'un batiment
	 */
	public static String BALISE_BUILDING_NAME;

	/**
	 * Balise du nom du proprietaire, si le contenu de la
	 * balise est vide il s'agit d'un batiment public
	 */
	public static String BALISE_BUILDING_OWNER;
	
	/**
	 * Balise de la valeur d'un batiment
	 */
	public static String BALISE_BUILDING_VALUE;
	
	/**
	 * Balise indiquant si le commerce du batiment est ferme
	 */
	public static String BALISE_BUILDING_CLOSE;
	
	/**
	 * Balise du nombre de pdb restant d'un batiment
	 */
	public static String BALISE_BUILDING_PDB;

	/**
	 * Balise du salaire d'un batiment
	 */
	public static String BALISE_BUILDING_SALARY;
	
	/**
	 * Balise du nombe de salaire d'un batiment
	 */
	public static String BALISE_BUILDING_NB_SALARY;
	
	/**
	 * Balise du prix de vente d'un batiment
	 */
	public static String BALISE_BUILDING_BUY_PRICE;
	
	/**
	 * Balise indiquant si la vente est reservee pour quelqu'un ou non
	 */
	public static String BALISE_BUILDING_ISRESERVED;	
	
	/**
	 * Balise du materiel
	 */
	public static String BALISE_MATERIAL;

	/**
	 * Balise du nom de materiel
	 */
	public static String BALISE_MATERIAL_NAME;
	
	/**
	 * Balise du prix d'achat du batiment
	 */
	public static String BALISE_MATERIAL_BUY_PRICE; 
	
	/**
	 * Balise du prix de vente d'un batiment
	 */
	public static String BALISE_MATERIAL_SOLD_PRICE; 
	
	/**
	 * Balise de la quantite en stock d'un materiel
	 */
	public static String BALISE_MATERIAL_QUANTITY_STOCKED; 
	
	/**
	 * Balise de la quantite max d'un materiel
	 */
	public static String BALISE_MATERIAL_QUANTITY_MAX; 
	

	/**
	 * Instance
	 */
	private static ConfigBalise instance = null;
	
	/**
	 * Implementation du pattern "singleton"
	 * @return
	 */
	public static ConfigBalise getInstance()
	{
		if(instance == null) instance = new ConfigBalise();
		return instance;
	}

	/**
	 * Constructeur, initialisant la valeur de toutes les balises
	 */
	private ConfigBalise()
	{
		BALISE_TIMESTAMP = "TIMESTAMP";
		
		BALISE_DISTRICT = "D";
		BALISE_DISTRICT_ID = "D_ID";
		BALISE_DISTRICT_ID_EMPIRE = "D_EMP";
		BALISE_DISTRICT_X = "D_X";
		BALISE_DISTRICT_Y = "D_Y";	
		BALISE_DISTRICT_NAME = "D_NAME";
		BALISE_DISTRICT_MAP = "D_MAP";

		BALISE_CITY = "C";
		BALISE_CITY_ID = "C_ID";
		BALISE_CITY_X = "C_X";
		BALISE_CITY_Y = "C_Y";
		BALISE_CITY_NAME = "C_NAME";
		BALISE_CITY_MAP = "C_MAP";

		BALISE_BUILDING = "B"; 
		BALISE_BUILDING_ID = "B_ID";
		BALISE_BUILDING_X = "B_X";
		BALISE_BUILDING_Y = "B_Y";
		BALISE_BUILDING_TYPE = "B_TYPE";
		BALISE_BUILDING_LEVEL = "B_LVL";
		BALISE_BUILDING_NAME = "B_NAME";
		BALISE_BUILDING_OWNER = "B_OWNER";
		BALISE_BUILDING_VALUE = "B_VALUE";
		BALISE_BUILDING_CLOSE= "B_CLOSE";
		BALISE_BUILDING_PDB = "B_PDB";
		BALISE_BUILDING_SALARY = "B_SAL";
		BALISE_BUILDING_NB_SALARY = "B_NBSAL";	
		BALISE_BUILDING_BUY_PRICE = "B_PRICE";	
		BALISE_BUILDING_ISRESERVED = "B_RES";
		
		
		BALISE_MATERIAL = "M";
		BALISE_MATERIAL_NAME = "M_NAME";
		BALISE_MATERIAL_BUY_PRICE = "M_BUY"; 
		BALISE_MATERIAL_SOLD_PRICE = "M_SOLD"; 
		BALISE_MATERIAL_QUANTITY_STOCKED = "M_STOCK"; 
		BALISE_MATERIAL_QUANTITY_MAX = "M_MAX";
	}
}
