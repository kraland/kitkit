package model;

import ihm.ListSmiley;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import controller.Controller;
import controller.I_ControllerDialog;

import model.content.City;
import model.content.District;
import model.content.Material;
import model.content.Person;
import model.gps.ModelGPS;
import model.tab.TabModel;
import model.tab.cenkury21.TabModelBuildingToBuy;
import model.tab.krac.TabModelObject;
import model.tab.pagesjaukes.TabModelBuilding;


public class Model implements I_ControllerDialog{

	/**
	 * Controleur
	 */
	private Controller ctrl;
	
	/**
	 * Nombre de colonne de provinces
	 */
	private static int i_nbDistrictHorizontal = 8;
	
	/**
	 * Nombre de ligne de provinces
	 */
	private static int i_nbDistrictVertical = 8;
	
	/**
	 * Nombre de case en largeur par province
	 */
	private static int i_nbHorizontalSquareInDistrict = 20;
	
	/**
	 * Nombre de case en hauteur par province
	 */
	private static int i_nbVerticalSquareInDistrict = 13;

	/**
	 * Liste de materiel
	 */
	private List<Material> listMaterial;
	
	/**
	 * Liste de province
	 */
	private List<District> listDistrict;
	
	/**
	 * TabModel du JTable affichant la recherche d'objets dans l'onglet KRAC
	 */
	private TabModel	tabModelObject;
	
	/**
	 * TabModel du JTable affichant les batiments a vendre et/ou louer dans l'onglet CENKURY21
	 */
	private TabModel	tabModelBuildingToBuy;

	/**
	 * TabModel du JTable affichant les batiments d'un PJ ou d'une organisation dans l'onglet PAGES JAUKES
	 */
	private TabModel	tabModelBuilding;
		
	/**
	 * Cette liste contient les entiers a partir duquel une case d'une map 3D fait partie de la map 2D retranscrite
	 */
	private List<Integer> listIntegerBeginTransfo;
	
	/**
	 * Cette liste contient les entiers jusqu'auquel une case d'une map 3D fait partie de la map 2D retranscrite
	 */
	private List<Integer> listIntegerStopTransfo;
	
	/**
	 * Timestamp de la mise a jour du modele
	 */
	private long l_timestampUpdate;
	
	/**
	 * Nombre de province sur la map
	 */
	private int i_nbDistrict;
	
	/**
	 * Nombre de provinces parcourues
	 */
	private int i_nbDistrictBrowsed = 0;
	
	/**
	 * Informations recuperees lors du chargement
	 */
	private String s_informationLoad;
	
	/**
	 * Liste de personnes referencees
	 */
	private List<Person> listPerson;
		
	/**
	 * Hauteur de la map 2D. C'est a dire 8 provinces ayant 13 cases d'hauteur
	 */
	private int i_heightMap;
	
	/**
	 * Largeur de la map 2D. C'est a dire 8 province ayant 20 cases de largeur
	 * avec une zone de 10 cases en plus (soit a gauche, soit a droite)
	 */
	private int i_widthMap;
	
	/**
	 * Map 2D TOTALE
	 */
	private MapInteger map2D;
	
	/**
	 * Map 3D TOTALE
	 */
	private MapInteger map3D;
	
	/**
	 * Modele propre a la partie GPS
	 */
	private ModelGPS modelGPS;
	
	/**
	 * Constructeur de modele
	 */
	public Model()
	{
		// On calcule le nombre de case dans la mad TOTALE
		i_heightMap = i_nbDistrictVertical * i_nbVerticalSquareInDistrict;
		i_widthMap = i_nbDistrictHorizontal * i_nbHorizontalSquareInDistrict + i_nbHorizontalSquareInDistrict/2;
		
		
 		// On initialise le modele de GPS
 		modelGPS = new ModelGPS();
		
		// On cree la liste de nom des colonnes du modele de tableau affichant les objets present dans tous les batiments.
		// En on initialise le dit modele de tableau
		tabModelObject = new TabModelObject();
		
		
		// On cree la liste de nom des colonnes du modele de tableau affichant les batiments a louer et a acheter.
		// En on initialise le dit modele de tableau
 		tabModelBuildingToBuy = new TabModelBuildingToBuy();
 		
 		
		// On cree la liste de nom des colonnes du modele de tableau affichant les possessions d'un personnage
 		// ou d'une organisation commerciale. Dans le cas d'une organisation commerciale on renvoie
 		// les possessions de tous les personnages faisant partie de l'organisation.
		// En on initialise le dit modele de tableau
 		tabModelBuilding = new TabModelBuilding();

 		// On cree les deux listes d'identifiants
 		createId(i_heightMap,i_widthMap);
 		
 		//initValue();

	}
	
	/**
	 * Ajoute une personne a la liste de personnes
	 * @param person
	 */
	public void addPerson(Person person)
	{
		listPerson.add(person);
	}
	
	/**
	 * Ajoute un materiel a la liste de materiel
	 * @param materialToAdd
	 */
	public void addMaterial(Material materialToAdd)
	{
		listMaterial.add(materialToAdd);
	}
	
	/**
	 * Ajoute une province a la liste de province
	 * @param provinceToAdd
	 */
	public void addDistrict(District districtToAdd)
	{
		listDistrict.add(districtToAdd);
	}

	/**
	 * Vide le modele du tableau affichant les batiments a vendre et/ou louer dans l'onglet CENKURY21 
	 */
	public void clearTabModelBuildingToBuy(){
		tabModelBuildingToBuy.removeAll();
	}
	
	/**
	 * Renvoie le modele du tableau affichant les batiments a vendre et/ou louer dans l'onglet CENKURY21 
	 */
	public TabModel getTabModelBuildingToBuy()	{
		return tabModelBuildingToBuy;
	}
	
	/**
	 * Ajoute un batiment au modele du tableau affichant les batiments a vendre et/ou louer dans l'onglet CENKURY21 
	 */
	public boolean addRowTabModelBuildingToBuy(List<String> listString){
		return tabModelBuildingToBuy.addRow(listString);
	}
	
	/**
	 * Vide le modele du tableau affichant les batiments d'un PJ ou d'une organisation dans l'onglet PAGES JAUKES
	 */
	public void clearTabModelBuilding(){
		tabModelBuilding.removeAll();
	}
	
	/**
	 * Renvoie le modele du tableau affichant les batiments d'un PJ ou d'une organisation dans l'onglet PAGES JAUKES
	 */
	public TabModel getTabModelBuilding()	{
		return tabModelBuilding;
	}
	
	/**
	 * Ajoute un batiment au modele du tableau affichant les batiments d'un PJ ou d'une organisation dans l'onglet PAGES JAUKES
	 */
	public boolean addRowTabModelBuilding(List<String> listString){
		return tabModelBuilding.addRow(listString);
	}
	
	/**
	 * Vide le modele du tableau affichant la recherche d'objets dans l'onglet KRAC
	 */
	public void clearTabModelObject(){
		tabModelObject.removeAll();
	}
	
	/**
	 * Renvoie le modele du tableau affichant la recherche d'objets dans l'onglet KRAC
	 */
	public TabModel getTabModelObject()	{
		return tabModelObject;
	}
	
	/**
	 * Ajoute un batiment au modele du tableau affichant la recherche d'objets dans l'onglet KRAC
	 */
	public boolean addRowTabModelObject(List<String> listString){
		return tabModelObject.addRow(listString);
	}
	
	/**
	 * Recherche un materiel dans la liste de tous les materiels. Cette recherche s'effectue
	 * en regardant si un materiel du meme nom n'existe pas deja. Si c'est le cas on renvoie l'objet
	 * Materiel, sinon on en cree un nouveau qu'on ajoute au modele et qu'on renvoie a la fin de la fonction
	 * @param s_name
	 * @return
	 */
	public void lookForMaterial(String s_name)
	{
		// On cree un nouveau materiel
		Material typeMaterialToAdd = new Material();
		
		// On lui met le nom place en parametre de cette methode
		typeMaterialToAdd.setS_name(s_name);
		
		// On parcourt chaque materiel deja present dans le modele
		for (Material typeMaterial : listMaterial)
		{
			// Si le materiel a le meme nom que celui passe en parametre 
			if(typeMaterial.getS_name().equals(s_name))
			{
				return;
			}
		}
		
		// On l'ajoute a la liste de materiel
		listMaterial.add(typeMaterialToAdd);
	}
	
	/**
	 * Recherche une personne dans la liste de toutes les personnes. Cette recherche s'effectue
	 * en regardant si une personne du meme nom n'existe pas deja. Si c'est le cas on renvoie l'objet
	 * Person, sinon on en cree un nouveau qu'on ajoute au modele et qu'on renvoie a la fin de la fonction
	 * @param s_name
	 * @return
	 */
	public Person lookForPerson(String s_name)
	{
		// On cree une nouvelle personne
		Person personToAdd = new Person();
		
		// On lui met le nom place en parametre de cette methode
		personToAdd.setS_name(s_name);

		// On parcourt chaque personne deja presente dans le modele
		for (Person person : listPerson)
		{
			// Si la personne a le meme nom que celui passe en parametre 
			if(person.getS_name().equals(s_name))
			{
				// On renvoie cette personne 
				return person;
			}
		}
			
		// On traite les smileys de chaque nom...
		String[] tabName = ListSmiley.getInstance().identifySmiley(s_name);
		personToAdd.setS_nameWithoutSmiley(tabName[0]);
		personToAdd.setS_nameHtml(tabName[1]);		
		
		// On l'ajoute a la liste de personnes
		listPerson.add(personToAdd);
		
		// On renvoie cette personne
		return personToAdd;	

	}
	
	// TODO
	/**
	 * Recherche une organisation dans la liste de toutes les organisations. Cette recherche s'effectue
	 * en regardant si une organisation du meme nom n'existe pas deja. Si c'est le cas on renvoie l'objet
	 * Organization, sinon on en cree un nouveau qu'on ajoute au modele et qu'on renvoie a la fin de la fonction
	 * @param s_name
	 * @return
	 
	public Organization lookForOrganization(String s_name)
	{
		// On parcourt chaque organisation deja presente dans le modele
		for (Organization organization : listOrganization)
		{
			// Si l'organisation a le meme nom que celui passe en parametre
			if(organization.getS_name().equals(s_name))
			{
				// On renvoie cette organisation
				return organization;
			}
		}
		
		// On cree une nouvelle organisation commerciale
		Organization organizationToAdd = new Organization();
		
		// On lui met le nom place en parametre de cette methode
		organizationToAdd.setS_name(s_name);
			
		// On l'ajoute a la liste d'organisations commerciales
		listOrganization.add(organizationToAdd);
		
		// On renvoie cette organisation commerciale
		return organizationToAdd;
	}
	*/

	/**
	 * Renvoie la liste de province
	 * @return
	 */
	public List<District> getListDistrict() {
		return listDistrict;
	}

	/**
	 * Renvoie la liste de chaque materiel qu'on peut vendre ou acheter dans un batiment.
	 * @return
	 */
	public List<Material> getListMaterial() {
		return listMaterial;
	}

	/**
	 * Change la liste de chaque materiel qu'on peut vendre ou acheter dans un batiment.
	 * @param listAllMaterial
	 */
	public void setListMaterial(List<Material> listMaterial) {
		this.listMaterial = listMaterial;
	}
	
	/**
	 * Change le timestamp indiquant le moment de la mise a jour du modele
	 * @param l_timestampUpdate
	 */
	public void setL_timestampUpdate(long l_timestampUpdate) {
		this.l_timestampUpdate = l_timestampUpdate;
	}
	
	/**
	 * Renvoie le timestamp indiquant le moment de la mise a jour du modele
	 * @return
	 */
	public long getL_timestampUpdate() {
		return l_timestampUpdate;
	}

	/**
	 * Renvoie le nombre de province
	 * @return
	 */
	public int getI_nbDistrict() {
		return i_nbDistrict;
	}

	/**
	 * Change le nombre de province
	 * @param i_nbDistrict
	 */
	public void setI_nbDistrict(int i_nbDistrict) {
		this.i_nbDistrict = i_nbDistrict;
	}

	/**
	 * Renvoie le nombre de province parcourut. Ce nombre sera incremente lorsqu'on parcourera une province
	 * decrite dans le fichier de configuration
	 * @return
	 */
	public int getI_nbDistrictBrowsed() {
		return i_nbDistrictBrowsed;
	}

	/**
	 * Change le nombre de province parcourut. Ce nombre sera incremente lorsqu'on parcourera une province
	 * decrite dans le fichier de configuration
	 * @param i_nbDistrictBrowsed
	 */
	public void setI_nbDistrictBrowsed(int i_nbDistrictBrowsed) {
		this.i_nbDistrictBrowsed = i_nbDistrictBrowsed;
	}
	
	/**
	 * Ajoute une ligne d'information. 
	 * @param s_informationToAdd
	 */
	public void addLineInformation(String s_informationToAdd)
	{
		setS_informationLoad(getS_informationLoad() + s_informationToAdd); 
	}

	/**
	 * Renvoie les informations sur le chargement du fichier de configuration
	 * @return
	 */
	public String getS_informationLoad() {
		return s_informationLoad;
	}

	/**
	 * Change les informations sur le chargement du fichier de configuration
	 * @param s_informationLoad
	 */
	public void setS_informationLoad(String s_informationLoad) {
		this.s_informationLoad = s_informationLoad;
	}

	/**
	 * Renvoie la liste de personne reference
	 * @return
	 */
	public List<Person> getListPerson() {
		return listPerson;
	}

	/**
	 * Fonction qui va creer la map 2D
	 */
	public void create2DMap()
	{	
		// On initilise la map a creer
		List<List<Integer>> mapToReturn = new ArrayList<List<Integer>>();
		
		// On recupere la liste de province trier par indice
		List<District> listDistrictSorted = getListProvinceSortedByID();
				
		// On parcourt chaque province
		for(int i_idProvince = 0 ; i_idProvince < listDistrictSorted.size() ; i_idProvince++)
		{
			// Si on est en debut d'une ligne de province 
			if(i_idProvince % i_nbDistrictHorizontal == 0)
			{
				// On va ajouter autant de ligne que de nombre de case verticale dans une province
				for(int i_id = 0 ; i_id < i_nbVerticalSquareInDistrict ; i_id++)
				{
					mapToReturn.add(new ArrayList<Integer>());	
				}
			}
			
			// Si c'est une province de debut de ligne et que cette ligne est impaire
			// Dans ce cas on ajoute un tableau de hauteur i_nbCaseVertical et de largeur i_nbCaseHorizontal/2
			// Car il y a un decalage de la ligne de province a droite sur les lignes impaires
			if(i_idProvince % i_nbDistrictHorizontal == 0 && (i_idProvince/i_nbDistrictHorizontal)%2 != 0)
			{
				// On parcourt chaque ligne
				for (int i_idRow = 0 ; i_idRow < i_nbVerticalSquareInDistrict ; i_idRow++)
				{					
					// On parcourt chaque colonne
					for(int i_idColumn = 0 ; i_idColumn < (i_nbHorizontalSquareInDistrict/2) ; i_idColumn++)
					{
						// On recupere la ligne de la map actuelle
						int i_idRowMap = mapToReturn.size() - i_nbVerticalSquareInDistrict + i_idRow;
						
						// On ajoute un 0 a la map
						mapToReturn.get(i_idRowMap).add(0);
					}					
				}
			}
						
			// On parcourt chaque ligne
			for (int i_idRow = 0 ; i_idRow < listDistrictSorted.get(i_idProvince).getMap2D().size() ; i_idRow++)
			{
				// On parcourt chaque colonne
				for(int i_idColumn = 0 ; i_idColumn < listDistrictSorted.get(i_idProvince).getMap2D().get(i_idRow).size() ; i_idColumn++)
				{
					// On recupere la ligne de la map actuelle
					int i_idRowMap = mapToReturn.size() - i_nbVerticalSquareInDistrict + i_idRow;
					
					// On ajoute la valeur a la map
					mapToReturn.get(i_idRowMap).add(listDistrictSorted.get(i_idProvince).getMap2D().get(i_idRow).get(i_idColumn));
				}
				
			}

			// Si c'est une province de fin de ligne et que cette ligne est paire
			// Dans ce cas on ajoute un tableau de hauteur i_nbCaseVertical et de largeur i_nbCaseHorizontal/2
			// Car il y a un decalage de la ligne de province a gauche sur les lignes paires et une zone vide s'y rajoute
			if(i_idProvince % i_nbDistrictHorizontal == (i_nbDistrictHorizontal - 1) && (i_idProvince/i_nbDistrictHorizontal)%2 == 0)
			{
				// On parcourt chaque ligne
				for (int i_idRow = 0 ; i_idRow < i_nbVerticalSquareInDistrict ; i_idRow++)
				{			
					// On parcourt chaque colonne
					for(int i_idColumn = 0 ; i_idColumn < (i_nbHorizontalSquareInDistrict/2) ; i_idColumn++)
					{
						// On recupere la ligne de la map actuelle
						int i_idRowMap = mapToReturn.size() - i_nbVerticalSquareInDistrict + i_idRow;

						// On ajoute un 0 a la map
						mapToReturn.get(i_idRowMap).add(0);
					}
				}	
			}
		}
		
		// On met a jour la map 2D qu'on vient de creer
		map2D.setMap(mapToReturn);
	}
	
	/**
	 * Fonction qui va creer la map 3D
	 */
	public void create3DMap()
	{
		// On initialise la map qu'on va renvoyer
		List<List<Integer>> mapToReturn = new ArrayList<List<Integer>>();
		
		// On parcourt chaque ligne de la map 3D
		for(int i_idRow = 0 ; i_idRow < (i_heightMap + i_widthMap - 1) ; i_idRow ++)
		{
			// On ajoute une nouvelle ligne a la map
			mapToReturn.add(new ArrayList<Integer>());	
			
			// On parcourt chaque colonne de la map 3D
			for(int i_idColumn = 0 ; i_idColumn < (i_heightMap + i_widthMap)/2 ; i_idColumn++)
			{
				// Si on est dans la zone ou se situe la map 2D
				if(listIntegerStopTransfo.get(i_idRow) >= i_idColumn && listIntegerBeginTransfo.get(i_idRow) <= i_idColumn)
				{

					// On cree le point 3D
					Point point3D = new Point(i_idColumn,i_idRow);
					
					// On cree le point 2D
					Point point2D = convert3DTo2D(point3D); 
					
					// On ajoute le point
					mapToReturn.get(i_idRow).add(map2D.getMap().get(point2D.y).get(point2D.x));	
				}
				else
				{
					// On ajoute une case de "vide"
					mapToReturn.get(i_idRow).add(0);
					
				}
			}
		}
		
		// On met a jour la map 3D
		map3D.setMap(mapToReturn);
	}
	
	/**
	 * Creer les deux listes d'identifiants permettant de savoir a partir de quelle case d'une map 3D celle-ci
	 * fait partie d'une map 2D
	 * @param i_height
	 * @param i_width
	 */
	private void createId(int i_height, int i_width)
	{
		// On cree la liste d'entier de depart pour effectuer la transformation
		listIntegerBeginTransfo = createIdBegin(i_height, i_width);
		
		// On cree la liste d'entier d'arret pour effectuer la transformation
		listIntegerStopTransfo = createIdStop(i_height, i_width);
	}
	
	/**
	 * Ajoute des entiers deux par deux au sein d'une liste placee en parametre (listInteger).
	 * Par exemple si on demande une liste de s'incrementer (hasToIncrease = true) avec un identifiant de depart de 10
	 * (i_idBegin = 10) et qu'on demande qu'il commence non pas par ajouter deux valeurs mais une seule (hasBegunFirst = false)
	 * et qu'on demande que l'identifiant d'arret soit 14 (i_idStop = 14) et qu'on demande qu'il s'arrete en ajoutant seulement
	 * une fois la derniere valeur (hasStoppedFirst = true) dans ce cas le tableau ci-dessous sera ajoute a la liste placee
	 * en parametre, c'est a dire listInteger.
	 * 
	 * Le tableau rajoute est le suivant [10,11,11,12,12,13,13,14]
	 * @param listInteger
	 * @param hasToIncrease
	 * @param i_idBegin
	 * @param hasBegunFirst
	 * @param i_idStop
	 * @param hasStoppedFirst
	 */
	void addList(List<Integer> listInteger, boolean hasToIncrease,int i_idBegin, boolean hasBegunFirst, int i_idStop, boolean hasStoppedFirst)
	{
		// On me morise l'identifiant de depart
		int i_idNow = i_idBegin;
		
		// Si on doit incrementer
		if(hasToIncrease)
		{
			// Tant que l'identifiant actuel est inferieur a celui de fin
			while (i_idNow <= i_idStop)
			{
				// Si l'identifiant actuel est egal a l'identifiant de depart
				if(i_idNow == i_idBegin)
				{
					// Si on commence a la premiere position
					if(hasBegunFirst)
					{
						// On ajoute le premier identifiant a la liste 
						listInteger.add(i_idNow);					
					}
					// Sinon on n'ajoute pas le premier identifiant a la liste 
				}
				// Sinon ce n'est pas l'identifiant de depart
				else
				{
					// On ajoute le premier identifiant a la liste
					listInteger.add(i_idNow);	
				}
				
				// Si l'identifiant actuel est egal a l'identifiant de stop
				if(i_idNow == i_idStop)
				{
					// Si on arrete a la deuxieme position
					if(!hasStoppedFirst)
					{
						// On ajoute le second identifiant a la liste 
						listInteger.add(i_idNow);					
					}
					// Sinon on n'ajoute pas le second identifiant a la liste 
				}
				// Sinon ce n'est pas l'identifiant d'arret
				else
				{
					// On ajoute le second identifiant a la liste
					listInteger.add(i_idNow);	
				}
				
				// On ajoute l'identifiant a la liste
							
				i_idNow++;
			}
		}
		
		// Sinon, cela signifie qu'on doit decrementer
		else
		{
			// Tant que l'identifiant actuel est superieur a celui de fin
			while (i_idNow >= i_idStop)
			{
				// Si l'identifiant actuel est egal a l'identifiant de depart
				if(i_idNow == i_idBegin)
				{
					// Si on commence a la premiere position
					if(hasBegunFirst)
					{
						// On ajoute le premier identifiant a la liste 
						listInteger.add(i_idNow);					
					}
					// Sinon on n'ajoute pas le premier identifiant a la liste 
				}
				// Sinon ce n'est pas l'identifiant de depart
				else
				{
					// On ajoute le premier identifiant a la liste
					listInteger.add(i_idNow);	
				}
				
				// Si l'identifiant actuel est egal a l'identifiant de stop
				if(i_idNow == i_idStop)
				{
					// Si on arrete a la deuxieme position
					if(!hasStoppedFirst)
					{
						// On ajoute le second identifiant a la liste 
						listInteger.add(i_idNow);					
					}
					// Sinon on n'ajoute pas le second identifiant a la liste 
				}
				// Sinon ce n'est pas l'identifiant d'arret
				else
				{
					// On ajoute le second identifiant a la liste
					listInteger.add(i_idNow);	
				}
				
				// On ajoute l'identifiant a la liste
							
				i_idNow--;
			}
		}
	}

	
	/**
	 * Cree les identifiant de depart
	 * @param i_height
	 * @param i_width
	 * @return
	 */
	private List<Integer> createIdBegin(int i_height, int i_width)
	{
		// On cree une nouvelle liste
		listIntegerBeginTransfo = new ArrayList<Integer>();
		
		int i_idBegin0,i_idBegin1;
		boolean hasTwiceValue0,hasTwiceValue1;
		
		if(i_height%2 == 0)
		{
			i_idBegin0 = (i_height-1)/2;
			hasTwiceValue0 = true;
		}
		else
		{
			i_idBegin0 = i_height/2;
			hasTwiceValue0 = false;			
		}
		
		if(i_width%2 == 0)
		{
			i_idBegin1 = (i_width-1)/2;
			hasTwiceValue1 = true;
		}
		else
		{
			i_idBegin1 = i_width/2;
			hasTwiceValue1 = false;			
		}
		
		// On ajoute la partie qui decroit
		addList(listIntegerBeginTransfo, false,	i_idBegin0, hasTwiceValue0, 0,false);
		
		// On ajoute la partie qui croit
		addList(listIntegerBeginTransfo, true,	0, 	false, 	i_idBegin1, !hasTwiceValue1);
		
		// Renvoie la liste d'entier de depart de la transformation
		return listIntegerBeginTransfo;
	}

	/**
	 * Cree la liste d'identifiant d'arret permettant d'effectuer la transformation
	 * @param i_height
	 * @param i_width
	 * @return
	 */
	private List<Integer> createIdStop(int i_height, int i_width)
	{
		// On cree une nouvelle liste
		listIntegerStopTransfo = new ArrayList<Integer>();
		
		
		int i_idStop0,i_idStop1, i_idStop2,i_idStop3;
		boolean hasTwiceValue0,hasTwiceValue1,hasTwiceValue2,hasTwiceValue3;
		
		if(i_height%2 == 0)
		{
			i_idStop0 = (i_height-1)/2;
			hasTwiceValue0 = false;
			
			if(i_width%2 == 0)
			{
				i_idStop1 = i_idStop0 + i_width/2;
				hasTwiceValue1 = false;
				i_idStop2 = i_idStop1-1;
				hasTwiceValue2 = true;
				i_idStop3 =  (i_width-1)/2;
				hasTwiceValue3 = false;
			}
			else
			{
				i_idStop1 = i_idStop0 + i_width/2;
				hasTwiceValue1 = true;
				i_idStop2 = i_idStop1;
				hasTwiceValue2 = false;
				i_idStop3 =  i_width/2;
				hasTwiceValue3 = true;
			}
		}
		else
		{
			i_idStop0 = i_height/2;
			hasTwiceValue0 = true;	
			 
			if(i_width%2 == 0)
			{
				i_idStop1 = (i_width-1)/2;
				hasTwiceValue1 = true;
				i_idStop2 = i_idStop1;
				hasTwiceValue2 = false;
				i_idStop3 =  (i_width-1)/2;
				hasTwiceValue3 = false;
			}
			else
			{
				i_idStop1 = i_idStop0 + i_width/2;
				hasTwiceValue1 = false;		
				i_idStop2 = i_idStop1-1;
				hasTwiceValue2 = true;
				i_idStop3 =  i_width/2;
				hasTwiceValue3 = true;				
			}
		}
		

		
		// On ajoute la partie qui croit
		addList(listIntegerStopTransfo, true,	i_idStop0, 	hasTwiceValue0, i_idStop1, !hasTwiceValue1);
		
		// On ajoute la partie qui decroit
		addList(listIntegerStopTransfo, false,	i_idStop2, hasTwiceValue2, 	i_idStop3, !hasTwiceValue3);
		
		// Renvoie la liste d'entier d'arret de la transformation
		return listIntegerStopTransfo;
	}
	
	
	/**
	 * Renvoie la liste de Province triee par ID
	 * @return
	 */
	public List<District> getListProvinceSortedByID() {
		
		// On initialise la liste de province a renvoyer
		List<District> listToReturn = new ArrayList<District>();
		
		// On parcourt chaque province de la liste
		for(int i_idDistrict = 0 ; i_idDistrict < getListDistrict().size() ; i_idDistrict++)
		{
			// On recupere l'ID de la province parcourue
			int i_idProvinceBrowsed = getListDistrict().get(i_idDistrict).getI_id();
			
			// Identifiant de la nouvelle position
			int i_idNewPosition = 0;
			
			// Si la liste de province a renvoyer est superieure a zero
			if(listToReturn.size() > 0)
			{
				// On parcourt chaque province de la liste a renvoyer
				for(int i_idListToReturn = 0 ; i_idListToReturn < listToReturn.size() ; i_idListToReturn++)
				{
					// Si l'id de la province parcourue est superieure a celui de la province qu'on
					// parcourt au sein de la map a renvoyer
					if(i_idProvinceBrowsed > listToReturn.get(i_idListToReturn).getI_id())
					{
						// On change le nouvel identifiant
						i_idNewPosition = i_idListToReturn + 1;
					}
				}	
			}
			// Sinon cela signifie que c'est la premiere province qu'on ajoute a la liste a renvoyer
			else
			{
				// On change le nouvel identifiant
				i_idNewPosition = 0;
			}
			
			// On ajoute la province avec le bonne identifiant au sein de la map
			listToReturn.add(i_idNewPosition, getListDistrict().get(i_idDistrict));

		}
		
		// Renvoie la liste a renvoyer
		return listToReturn;
		
	}

	/**
	 * Renvoie la map 2D
	 * @return
	 */
	public MapInteger getMap2D() {
		return map2D;
	}

	/**
	 * Change la map 2D
	 * @param map2D
	 */
	public void setMap2D(MapInteger map2D) {
		this.map2D = map2D;
	}
	
	/**
	 * Renvoie la map 3D
	 * @return
	 */
	public MapInteger getMap3D() {
		return map3D;
	}

	/**
	 * Change la map 3D
	 * @param map3D
	 */
	public void setMap3D(MapInteger map3D) {
		this.map3D = map3D;
	}
	
	/**
	 * Convertit un point 2D place en parametre en un point 3D
	 * @param point2D
	 * @return
	 */
	public Point convert2DTo3D(Point point2D)
	{
		// On initialise le point 3D a renvoyer
		Point point3D = new Point();
		
		// On recupere l'ordonnee du point 3D
		point3D.y = (int)point2D.getX() + (int)point2D.getY();
		
		// Positionnement de la case au sein de la ligne
		int i_idX = listIntegerBeginTransfo.get((int)point3D.getY());
		
		// On cree un point 2D temporaire qu'on va deplacer sur la diagonale
		// Le but etant de le ramener en bas a gauche
		Point point2DTmp  = new Point(point2D.x,point2D.y);
			
		// Tant que le point n'a pas pour abscisse 0
		// Et son ordonnee ne vaut pas la taille max de la map 2D
		while(point2DTmp.x != 0 && point2DTmp.y != (map2D.getMap().size() - 1))
		{
			// On decale notre point d'une case vers la gauche puis une case vers le bas
			point2DTmp.x = point2DTmp.x - 1;
			point2DTmp.y = point2DTmp.y + 1;
			
			// On incremente le positionnement de la case au sein de la map
			i_idX++;
		}
		
		// On enregistre l'abscisse 3D
		point3D.x = i_idX;
		
		// on renvoie le point
		return point3D;
		
	}

	/**
	 * Convertit un point 3D en 2D
	 * @param point3D
	 * @return
	 */
	public Point convert3DTo2D(Point point3D)
	{
		// On initialise le point 2D a renvoyer
		Point point2D = new Point();
		
		int i_x3D = (int)point3D.getX();
		int i_y3D = (int)point3D.getY();
		int i_idX = 0;
		int i_begin = listIntegerBeginTransfo.get((int)point3D.getY());
		int i_stop = listIntegerStopTransfo.get((int)point3D.getY());
		
		// Si l'abscisse 3D est comprise entre l'abscisse de depart et de stop
		if( i_x3D >= i_begin && i_x3D <= i_stop)
		{
			// On parcourt les identifiants entre le depart et le stop
			for(int i_idCase = i_begin ; i_idCase <= i_stop ; i_idCase++)
			{
				// Si on a trouve l'identifiant correspondant a l'abscisse de la map 3D
				if(i_x3D == i_idCase)
				{
					i_idX = i_idCase - i_begin;	
				}
			}
			
			point2D.x = i_idX + Math.max(map2D.getMap().size() - 1,i_y3D) - (map2D.getMap().size() - 1);
			point2D.y = Math.min(map2D.getMap().size()-1,i_y3D) - i_idX; 
		}
		
		return point2D;
	}

	/**
	 * Renvoie le modele du GPS
	 * @return
	 */
	public ModelGPS getModelGPS() {
		return modelGPS;
	}

	/**
	 * Change le modele du GPS
	 * @param modelGPS
	 */
	public void setModelGPS(ModelGPS modelGPS) {
		this.modelGPS = modelGPS;
	}
	
	/**
	 * Renvoie la hauteur de la map 2D totale
	 * @return
	 */
	public int getI_heightMap()
	{
		return i_heightMap;
	}
	
	/**
	 * Renvoie la largeur de la map 2D totale
	 * @return
	 */
	public int getI_widthMap()
	{
		return i_widthMap;
	}
	
	/**
	 * Renvoie le nombre de province horizontal
	 * @return
	 */
	public int getI_nbDistrictHorizontal()
	{
		return i_nbDistrictHorizontal;
	}
	
	/**
	 * Renvoie le nombre de province vertical 
	 * @return
	 */
	public int getI_nbDistrictVertical()
	{
		return i_nbDistrictVertical;
	}
	
	/**
	 * Renvoie le nombre de case horizontale 
	 * @return
	 */
	public int getI_nbHorizontalSquareInDistrict()
	{
		return i_nbHorizontalSquareInDistrict;
	}
	
	/**
	 * Renvoie le nombre de case verticale 
	 * @return
	 */
	public int getI_nbVerticalSquareInDistrict()
	{
		return i_nbVerticalSquareInDistrict;
	}

	public void initValue()
	{
		// On initialise la liste de chaque materiel
		listMaterial = new ArrayList<Material>();
		
		// On initialise la liste de province
		listDistrict = new ArrayList<District>();
		
		// On initialise la liste de personne
		listPerson = new ArrayList<Person>();
 		
 		// On initialise les maps
 		map2D = new MapInteger();
 		map3D = new MapInteger();
 		
 		// On vide les tableaux
 		clearTabModelObject();
 		clearTabModelBuilding();
 		clearTabModelBuildingToBuy();
 		
		// On initialise le nombre de province parcourues
		i_nbDistrictBrowsed = 0;
	}

	public void initFilter()
	{
		tabModelBuilding.initFilter();
		tabModelBuildingToBuy.initFilter();
		tabModelObject.initFilter();
	}
	
	
	/**
	 * Cree la liste de villes interconnectees par train, avion, bateau
	 */
	public void createListInterconnectedCity()
	{
		
		List<City> listAllCity = new ArrayList<City>();
		
		// On parcourt chaque provinces afin de recurer la liste de toutes les villes
		for(District districtTmp : listDistrict)
		{
			for(City cityTmp : districtTmp.getListCity())
			{
				listAllCity.add(cityTmp);
			}
		}
		
		///////////////////////////////////////////////////////////////////////////////
		// On cree chaque liste de villes situees a 3 provinces maximum de chaque ville
		//
		// On parcourt chaque ville de la map
		for(int i_idCity = 0 ; i_idCity < listAllCity.size() ; i_idCity++)
		{
			// On cherche quelles provinces sont a 3 cases de la villes
			List<String> listNameDistrict = getListDistrictSelected(listAllCity.get(i_idCity).getDistrict().getS_name(),3);
			
			// On parcourt chaque province
			for(String s_nameDistrictTmp : listNameDistrict)
			{
				// On parcourt la liste de province
				for(District districtTmp : listDistrict)
				{
					// Si le nom de la province correspond
					if(districtTmp.getS_name().equals(s_nameDistrictTmp))
					{
						// On parcourt chaque ville de cette province
						for(City cityTmp : districtTmp.getListCity())
						{
							// Si le nom de la ville n'est pas celle d'origine
							if(!cityTmp.equals(listAllCity.get(i_idCity)))
							{
								listAllCity.get(i_idCity).addCityConnectedByPlane(cityTmp);
							}
						}
					}
				}
			}
		}
		
		//On parcourt toutes les provinces
		
	}
	
	/**
	 * Renvoie la liste de provinces selectionnees. Ces provinces sont celles situes dans un rayon de
	 * i_nbProvinceAround autour de la province selectionne ayant pour nom s_nameProvinceSelected
	 * @param s_nameProvinceSelected
	 * @param i_nbProvinceAround
	 * @return
	 */
	public List<String> getListDistrictSelected(String s_nameProvinceSelected, int i_nbProvinceAround)
	{
		// On initialise les coordonnees de la province selectionnee
		int i_xProvinceSelected = -1;
		int i_yProvinceSelected = -1;
		
		// On initialise la liste de nom 
		List<String> listProvinceSelected = new ArrayList<String>();
	
		// Si le nombre de provinces autour est superieur a 0
		if(i_nbProvinceAround > 0)
		{
			// On parcourt la liste de province present dans le modele
			for(District district : getListDistrict())
			{
				// Si le nom de la province parcourue et le meme que celui de la province place en parametre
				if(district.getS_name() == s_nameProvinceSelected)
				{
					// On recupere les coordonnees de la province
					i_xProvinceSelected = district.getI_x();
					i_yProvinceSelected = district.getI_y();
				}
			}
			
			// On parcourt la liste de province present dans le modele
			for(District district : getListDistrict())
			{
				// On recupere les coordonnes de la province parcourue
				int i_xProvinceBrowse = district.getI_x();
				int i_yProvinceBrowse = district.getI_y();
				
				// Si la position en ordonnee est correcte
				if( (i_yProvinceSelected - i_nbProvinceAround) <= i_yProvinceBrowse && i_yProvinceBrowse <= (i_yProvinceSelected + i_nbProvinceAround))
				{
					
					// Nombre de case par ligne
					int i_nbCaseOkPerRow;
					
					// Identifiant de depart de la ligne contenant la province selectionnee
					int i_idBegin = i_xProvinceSelected - i_nbProvinceAround;

					// Identifiant d'arret
					int i_idStop;
					
					// On calcule combien il y a de case selectionnee par ligne
					i_nbCaseOkPerRow = 2 * i_nbProvinceAround + 1 - Math.abs(i_yProvinceSelected - i_yProvinceBrowse);
					
					// Si c'est une ligne paire (0,2,4,6,8)
					if((i_yProvinceSelected % 2)==0)
					{
						// On calcule l'identifiant de depart
						i_idBegin += Math.abs(i_yProvinceSelected - i_yProvinceBrowse)/2;
					}
					// Sinon c'est une ligne impaire (1,3,5,7)
					else
					{
						// On calcule l'identifiant de depart
						i_idBegin += Math.round((float)Math.abs(i_yProvinceSelected - i_yProvinceBrowse)/2);
					}
					
					// On calcule l'identifiant de fin
					i_idStop = i_idBegin + i_nbCaseOkPerRow - 1;
					
					// Si la position en abscissse est comprise entre le depart et la fin
					if(i_xProvinceBrowse >= i_idBegin && i_xProvinceBrowse <= i_idStop)
					{
						// On ajoute la province a la liste de province selectionnee
						listProvinceSelected.add(district.getS_name());
					}
				}
			}			
		}
		// Sinon on a choisit qu'une province aux alentours
		else
		{
			// On ajoute la province a la liste de province selectionnee
			listProvinceSelected.add(s_nameProvinceSelected);
		}
		
		// On renvoie la liste de province selectionnee
		return(listProvinceSelected);
	}

	@Override
	public void setController(Controller ctrl) {
		this.ctrl = ctrl;
		
		tabModelBuilding.setController(ctrl);
		tabModelBuildingToBuy.setController(ctrl);
		tabModelObject.setController(ctrl);

	}

	@Override
	public Controller getController() {
		return ctrl;
	}


}
