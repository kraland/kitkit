package controller;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import controller.parser.ParserConfContent;
import de.javasoft.plaf.synthetica.SyntheticaBlackMoonLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaBlackStarLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaBlueIceLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaBlueMoonLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaBlueSteelLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaGreenDreamLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaSilverMoonLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel;
import ihm.ConfigIcon;
import ihm.I_ViewDialog;
import ihm.View;
import ihm.load.WindowLoad;
import model.I_ModelDialog;
import model.Model;
import model.content.Building;
import model.content.City;
import model.content.District;
import model.content.Material;
import model.content.Person;
import model.gps.I_ModelGPSListener;
import model.gps.Polygon;
import model.gps.calc.CalcThreadManager;
import model.tab.TabModel;
public class Controller implements I_ViewDialog, I_ModelDialog{
		
	/**
	 * Modele de l'application
	 */
	private Model model;
	
	/**
	 * Vue de l'application
	 */
	private View view;
	
	/**
	 * Fenetre de chargement de l'application
	 */
	private WindowLoad windowLoad;
	
	/**
	 * Parser parcourant le fichier xml afin de recreer le modele de l'application
	 */
	private ParserConfContent parserConfContent;

	/**
	 * Booleen indiquant si le chargement a reussi
	 */
	private boolean hasSuccedLoad = false;
	
	/**
	 * Constructeur
	 */
	public Controller()
	{
		initClass();	
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////	LOAD AND UPDATE MODEL		//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	/**
	 * Initialise la classe
	 */
	public void initClass()
	{
		// On cree le modele
		model = new Model();
				
		// On cree la vue
		view = new View();
		
		// On affiche la vue
		view.setVisible(true);
		
		// On met a jour le controlleur au sein de la vue
		view.setController(this);
		
		// On met a jour le controlleur au sein du modele
		model.setController(this);
		
		// Si le parcours du fichier de configuration s'est bien deroule
		loadFileConf("fileConf/confContentBatiment.xml");
				
		// Ajout des listeners au modele
		addListenersToModel();			
	}
		
	/**
	 * Charge le fichier de configuration dont le chemin est place en parametre
	 * @param s_pathFileConf
	 */
	public void loadFileConf(String s_pathFileConf)
	{
		
		// On cree une nouvelle fenetre de chargement
		windowLoad = new WindowLoad();

		// On rend visible la fenetre
		windowLoad.setVisible(true);
				
		// On met la JWindow au premier plan
		windowLoad.setAlwaysOnTop(true);
		
		// On indique l'action qu'on est en train de realiser 
		updateStatusLoadWindow("Parsage " + s_pathFileConf);
		
		// On initialise les valeurs du modele
		model.initValue();
		
		// On cree une instance du parser de fichier de configuration
		parserConfContent = new ParserConfContent();
		
		// On lui fournit le modele , le controlleur, et le nom du fichier de configuration
		parserConfContent.setModel(model);				
		parserConfContent.setController(this);
		parserConfContent.setS_PathFileToBrowse(s_pathFileConf);
		
		// On retire le timestamp
		model.setL_timestampUpdate(-1);
		
		// On lance le parseur au sein d'un thread
		Thread threadParser = new Thread(parserConfContent);
		threadParser.run();
		
		try{
			threadParser.join();
		}
		catch (InterruptedException e){
			e.printStackTrace();
		}
		
		// Si le parcourt du fichier de configuration s'est bien deroule
		if(hasSuccedLoad())
		{
			try
			{
				// On indique a la fenetre de chargement que le chargement est ok
				windowLoad.setStatusLoad(true);
				
				// On cree la map totale 2D
				model.create2DMap();
				
				// On cree la map totale 3D
				model.create3DMap();
				
				model.initFilter();
				
				//TODO pour mettre en place les voyages en train/avion/bateau
				// On cree la liste des villes connectees entre elles par train, avion et bateau
				//model.createListInterconnectedCity();
			}
			
			catch(Exception exception)
			{
				model.setL_timestampUpdate(-1);
			}
	
		}

		// Si le timestamp est superieur a zero
		if(model.getL_timestampUpdate() > 0)
		{
			Date d = new Date(model.getL_timestampUpdate());
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
			
			// On met a jour le timestamp
			view.getPanelUpdateModel().updateTimeUpdateModel(formatter.format(d));
		}
		else
		{
			// On met a jour le timestamp
			view.getPanelUpdateModel().updateTimeUpdateModel("");
		}
		
		// On initialise les valeurs de l'IHM
		view.initValueIHM();
		
		// On indique a la fenetre de chargement que celui-ci s'est mal deroule
		windowLoad.setStatusLoad(false);
		
		// On ferme la fenetre
		windowLoad.setVisible(false);
		windowLoad.dispose();
		
	}

	/**
	 * Ajoute les listeners au modele
	 */
	private void addListenersToModel()
	{
		I_ModelGPSListener I_ModelGPSListenerTmp;

		I_ModelGPSListenerTmp = view.getPanelGPS().getPanelGPSResult();
		model.getModelGPS().addModelGPSListener(I_ModelGPSListenerTmp);
		
		I_ModelGPSListenerTmp = view.getPanelGPS().getPanelGPSConfig().getPanelGPSConfigScaleMap();
		model.getModelGPS().addModelGPSListener(I_ModelGPSListenerTmp);

		I_ModelGPSListenerTmp = view.getPanelGPS().getPanelGPSConfig();
		model.getModelGPS().addModelGPSListener(I_ModelGPSListenerTmp);
		
		I_ModelGPSListenerTmp = view.getPanelGPS().getPanelGPSResult().getPanelGPS3D();
		model.getModelGPS().addModelGPSListener(I_ModelGPSListenerTmp);
		
		I_ModelGPSListenerTmp = view.getPanelGPS().getPanelGPSResult().getPanelGPS2D();
		model.getModelGPS().addModelGPSListener(I_ModelGPSListenerTmp);
	}
		
	/**
	 * Essaye de mettre a jour le modele
	 */
	public void tryToUpdateModel()
	{
		ModelDownloader downloader = new ModelDownloader(true);
		downloader.setController(this);
		downloader.setModel(this.getModel());
		downloader.setView(this.getView());
		
		downloader.execute();
	}
	
	/**
	 * Essaye de mettre a jour le modele
	 */
	public void tryToLoadOldModel()
	{
		ModelDownloader downloader = new ModelDownloader(false);
		downloader.setController(this);
		downloader.setModel(this.getModel());
		downloader.setView(this.getView());
		
		downloader.execute();
	}
		
	/**
	 * Recupere le contenu de l'URL placee en parametre et le met dans un StringBuffer
	 * @param s_nameUrl
	 * @return
	 */
	public StringBuffer convertContentUrlToStringBuffer(String s_nameUrl)
	{
		StringBuffer buffer = new StringBuffer();

		try
		{
			boolean isFirstLine= true;
			URL url = new URL(s_nameUrl);
			URLConnection urlConnection = url.openConnection();

			InputStream ips = urlConnection.getInputStream();
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String line = null;
			while ((line = br.readLine()) != null)
			{
				if(!isFirstLine)
				{
					buffer.append('\n');
				}
				buffer.append(line);
				
				isFirstLine = false;
			}
			ips.close();
			ipsr.close();
			br.close();
			
		}
		
		catch (IOException e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(getView(),"Impossible de récupérer le contenu du fichier :\n" + s_nameUrl + "\nVérifiez que vous êtes connecté à Internet.","Erreur",JOptionPane.ERROR_MESSAGE);

		} 
		
		return buffer;
	}
		
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////	TOOLS		//////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	/**
	 * Met l'application en plein ecran ou non
	 * @param isFullScreen
	 */
	public void setFullScreen(boolean isFullScreen)
	{
		view.getPanelUpdateModel().setFullScreen(isFullScreen);
		
		if(isFullScreen)
		{
    		view.dispose();
    		view.setUndecorated(true);
    		view.setVisible(true); 
    		view.setIsFullScreen(true);
		}
		else
		{
    		view.dispose();
    		view.setUndecorated(false);
    		view.setVisible(true); 
    		view.setIsFullScreen(false);
		}
	}

	/**
	 * Renvoie le texte sans lien html contenant une image
	 * @param s_inHtml
	 * @return
	 */
	public String convertToText(String s_inHtml)
	{
		// On retire les balises html
		s_inHtml = s_inHtml.replace("<html>", "");
		s_inHtml = s_inHtml.replace("</html>", "");

		// Tant qu'il y a une image dans la chaine de caracteres...
		// On est impitoyable et on la retire
		while(s_inHtml.indexOf("<img") >= 0)
		{
			String s_stop = "\" >";
			int i_begin = s_inHtml.indexOf("<img");
			int i_stop = s_inHtml.indexOf(s_stop,i_begin);
			
			// On verifie que l'index de depart est bien inferieur a celui de fin
			if(i_begin < i_stop)
			{
				// On ajout la taille de la balise fermante d'une image
				i_stop += s_stop.length();
				
				// On retire le code html de l'image
				s_inHtml = s_inHtml.substring(0, i_begin) + s_inHtml.substring(i_stop, s_inHtml.length());
			}
			else
			{
				System.err.println("Function convertToText - String : " + s_inHtml);
			}
		}

		return s_inHtml;
	}
	
	/**
	 * Cree une url au format KI avec : 
	 * i_id1 l'identifiant de la province
	 * i_id2 l'identifiant de la ville
	 * i_typeUrl le type d'URL, 0 pour une province, 1 pour une ville, et 2 pour un batiment
	 * @param i_id1
	 * @param i_id2
	 * @param i_typeUrl
	 * @return
	 */
	public String createUrlKI(int i_id1, int i_id2, int i_typeUrl)
	{
		String s_url = "";
		
		// On regarde quel est le type d'URL
		switch (i_typeUrl)
		{
			// C'est une province
			case 0:
				s_url = "http://www.kraland.org/map.php?p=1_" + i_id1;
				break;
				
			// C'est une ville
			case 1:
				s_url = "http://www.kraland.org/map.php?p=1_" + i_id1 + "_" + i_id2;	
				break;
				
			// C'est un batiment
			case 2:
				s_url = "http://www.kraland.org/order.php?p1=1500&p2=" + i_id2 + "&p3=" + i_id1;
				break;
				
			default:
				break;
		}
		
		return s_url;
	}
	
	/**
	 * Renvoie une case
	 * @param i_x
	 * @param i_y
	 * @return
	 */
	private String transformToCase(int i_x, int i_y)
	{
		return "[" + i_x + "," + i_y + "]";
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////	RESEARCH OBJECT		//////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	/**
	 * Fonction permettant de chercher a batiment a acheter qui se trouve dans la zone de province selectionnee
	 * @param s_nameDistrict
	 * @param i_nbDistrictAround
	 */
	public String searchBuilding(	String s_nameDistrict, int i_nbDistrictAround)
{
	int i_nbRow = 0;
	int i_nbRowFiltered = 0;	
	
	// Vide le modele du tableau affichant les batiments a acheter
	model.clearTabModelBuildingToBuy();
		
	// On recupere le nom des provinces selectionnees
	List<String> listDistrictAround = model.getListDistrictSelected(s_nameDistrict,i_nbDistrictAround);
	
	// On cree une liste de string contenant toutes les donnees a ajouter au tableau de resultats
	List<String> listString_RowToAdd = new ArrayList<String>();
	
	// On parcourt toutes les provinces du modele
	for(District district : model.getListDistrict())
	{
		// On parcourt la liste de province selectionnees
		for(String s_nameDistrictAround : listDistrictAround)
		{
			// Si la province du modele fait partie de la liste de provinces selectionnees 
			if(s_nameDistrictAround.equals(district.getS_name()))
			{
				//On parcourt chaque ville de la province
				for(City city : district.getListCity())
				{
					// On parcourt chaque batiment de la ville
					for(Building building : city.getListBuilding())
					{
						// Si il est possible d'acheter le batiment
						if(building.isPossibleToBuy())
						{
							i_nbRow++;
							
							listString_RowToAdd.add(String.valueOf(building.getI_level()));
							listString_RowToAdd.add(building.getS_type());
							listString_RowToAdd.add(createUrlKI(city.getI_id(), building.getI_id(), 2));
							listString_RowToAdd.add(building.getS_name());
							listString_RowToAdd.add(transformToCase(building.getI_x()+1,building.getI_y()+1));
							listString_RowToAdd.add(createUrlKI(district.getI_id(),city.getI_id(),1));
							listString_RowToAdd.add(city.getS_name());
							listString_RowToAdd.add(createUrlKI(district.getI_id(),0,0));
							listString_RowToAdd.add(district.getS_name());
							listString_RowToAdd.add(building.getOwner().getS_nameHtml());
							listString_RowToAdd.add(building.getOwner().getS_nameWithoutSmiley());
							listString_RowToAdd.add(String.valueOf(building.getI_soldPrice()));						
							if(building.isReservedForBuy())
							{
								listString_RowToAdd.add("true");	
							}
							else
							{
								listString_RowToAdd.add("false");
							}
													
							// On ajoute cette ligne au tableau
							if(model.addRowTabModelBuildingToBuy(listString_RowToAdd))
							{
								i_nbRowFiltered++;
							}
															
							// On vide la liste
							listString_RowToAdd.clear();
						}			
					}
				}
			}
		}
	}
	
	if(i_nbRow == 0)
	{
		return "Aucun résultat";
	}
	else
	{
		return i_nbRowFiltered + "/" + i_nbRow + " Résultat(s) filtré(s)";
	}
	
}
	
	/**
	 * Fonction permettant de chercher un objet qui se trouve dans la zone de province selectionnee
	 * @param s_nameMaterial
	 * @param s_nameDistrict
	 * @param i_nbDistrictAround
	 */
	public String searchMaterial(	String s_nameMaterial,
								String s_nameDistrict,
								int i_nbDistrictAround)
{
	int i_nbRow = 0;
	int i_nbRowFiltered = 0;	
		
	model.clearTabModelObject();
		
	// On recupere le nom des provinces selectionnees
	List<String> listDistrictAround = model.getListDistrictSelected(s_nameDistrict,i_nbDistrictAround);
	
	// On cree une liste de string contenant toutes les donnees a ajouter au tableau de resultats
	List<String> listString_RowToAdd = new ArrayList<String>();
	
	// On parcourt toutes les provinces du modele
	for(District district : model.getListDistrict())
	{
		// On parcourt la liste de province selectionnees
		for(String s_nameDistrictAround : listDistrictAround)
		{
			// Si la province du modele fait partie de la liste de provinces selectionnees 
			if(s_nameDistrictAround.equals(district.getS_name()))
			{
				//On parcourt chaque ville de la province
				for(City city : district.getListCity())
				{
					// On parcourt chaque batiment de la province
					for(Building building : city.getListBuilding())
					{						
						// On parcourt chaque materiel 
						for(Material material : building.getListMaterial())
						{
							// Si le nom du materiel est le meme que celui mis en parametre
							if(material.getS_name().equals(s_nameMaterial))
							{
								i_nbRow++;
								
								listString_RowToAdd.add(String.valueOf(building.getI_level()));
								listString_RowToAdd.add(building.getS_type());
								listString_RowToAdd.add(createUrlKI(city.getI_id(), building.getI_id(), 2));
								listString_RowToAdd.add(building.getS_name());
								listString_RowToAdd.add(transformToCase(building.getI_x()+1,building.getI_y()+1));
								listString_RowToAdd.add(createUrlKI(district.getI_id(),city.getI_id(),1));
								listString_RowToAdd.add(city.getS_name());
								listString_RowToAdd.add(createUrlKI(district.getI_id(),0,0));
								listString_RowToAdd.add(district.getS_name());
								listString_RowToAdd.add(String.valueOf(material.getI_priceSold()));
								listString_RowToAdd.add(String.valueOf(material.getI_buyPrice()));
								listString_RowToAdd.add(String.valueOf(material.getI_stockedQuantity()));
								listString_RowToAdd.add(String.valueOf(material.getI_maxQuantity()));
								listString_RowToAdd.add(String.valueOf(building.getI_salary()));
								listString_RowToAdd.add(String.valueOf(building.getI_nbSalary()));
								if(building.isClosed())
								{
									listString_RowToAdd.add("true");	
								}
								else
								{
									listString_RowToAdd.add("false");
								}
								
								// On ajoute cette ligne au tableau
								if(model.addRowTabModelObject(listString_RowToAdd))
								{
									i_nbRowFiltered++;
								}
								
								// On vide la liste
								listString_RowToAdd.clear();
							}
						}
					}
				}
			}
		}
	}
	
	if(i_nbRow == 0)
	{
		return "Aucun résultat";
	}
	else
	{
		return i_nbRowFiltered + "/" + i_nbRow + " Résultat(s) filtré(s)";
	}
}
	
	/**
	 * Fonction permettant de chercher les batiments appartenant a une personne ou alors a toute les personnes d'une organisation
	 * @param s_name
	 * @param isPerson
	 */
	public String searchBuilding(String s_name, boolean isPerson)
	{
		int i_nbRow = 0;
		int i_nbRowFiltered = 0;	
		
		model.clearTabModelBuilding();
		
		List<String> listNamePerson = new ArrayList<String>();
		
		// Si c'est une personne
		if(isPerson)
		{
			// On ajoute la personne
			listNamePerson.add(s_name);
		}
		else
		{
			//TODO
			// Ajouter les noms de chaque personne de l'organisation dans la liste : listNamePerson
		}
				
		// On parcourt toutes les personnes du modele
		for(String s_namePerson : listNamePerson)
		{	
			// On parcourt toutes les personnes du modele
			for(Person person : model.getListPerson())
			{			
				// Si le nom place en parametre est trouve
				if(person.getS_nameWithoutSmiley().equals(s_namePerson))
				{
					// On cree une liste de string contenant toutes les donnees a ajouter au tableau de resultats
					List<String> listString_RowToAdd = new ArrayList<String>();

					// On parcourt toutes les batiments "proprietaire"
					for(Building building : person.getListBuilding())
					{
						i_nbRow++;
						
						//On cree la liste de string a ajouter au tableau
						listString_RowToAdd.add(String.valueOf(building.getI_level()));
						listString_RowToAdd.add(building.getS_type());
						listString_RowToAdd.add(createUrlKI(building.getCity().getI_id(), building.getI_id(), 2));
						listString_RowToAdd.add(building.getS_name());
						listString_RowToAdd.add(transformToCase(building.getI_x()+1,building.getI_y()+1));
						listString_RowToAdd.add(createUrlKI(building.getCity().getDistrict().getI_id(),building.getCity().getI_id(),1));
						listString_RowToAdd.add(building.getCity().getS_name());
						listString_RowToAdd.add(createUrlKI(building.getCity().getDistrict().getI_id(),0,0));
						listString_RowToAdd.add(building.getCity().getDistrict().getS_name());
						listString_RowToAdd.add(building.getOwner().getS_nameHtml());
						listString_RowToAdd.add(building.getOwner().getS_nameWithoutSmiley());
									
						// TODO
						// Ajouter le nom de l'organisation commerciale du personnage
						listString_RowToAdd.add("");
								
						listString_RowToAdd.add(String.valueOf(building.getI_value()));
									
						// On ajoute cette ligne au tableau
						if(model.addRowTabModelBuilding(listString_RowToAdd))
						{
							i_nbRowFiltered++;
						}
												
						// On vide la liste
						listString_RowToAdd.clear();						
						}
				}
			}
		}

		
		if(i_nbRow == 0)
		{
			return "Aucun résultat";
		}
		else
		{
			return i_nbRowFiltered + "/" + i_nbRow + " Résultat(s) filtré(s)";
		}
	}	
			
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////	GETTERS LIST SORTED BY NAME		//////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	/**
	 * Renvoie la liste de province triee par nom
	 * @return
	 */
	public List<String> getListDistrictSortedByName()
	{
		// On initialise la liste de nom de province
		List<String> listSortNameDistrict = new ArrayList<String>();
		
		// On parcourt chaque province de la liste de province
		for(District district : model.getListDistrict())
		{
			// On ajoute la province a la liste
			listSortNameDistrict.add(district.getS_name());
		}
		
		// On trie cette liste
		Collections.sort(listSortNameDistrict);
		
		// On renvoie cette liste
		return  listSortNameDistrict;
	}
	
	/**
	 * Renvoie la liste de ville (avec la province ou se trouve la ville) triee par nom
	 * @return
	 */
	public List<String> getListCityWithDistrictSortedByName() {
		
		// On initialise la liste de nom de ville
		List<String> listSortNameCity = new ArrayList<String>();
				
		// On parcourt chaque province de la liste de province
		for(District district : model.getListDistrict())
		{
			for(City city : district.getListCity())
			{
				// On ajoute la ville a la liste
				listSortNameCity.add(city.getS_name() + ", " + district.getS_name());
			}
		}
		
		// On trie cette liste
		Collections.sort(listSortNameCity);
		
		// On renvoie cette liste
		return  listSortNameCity;
	}
	
	/**
	 * Renvoie la liste de ville triee par nom
	 * @return
	 */
	public List<String> getListCitySortedByName() {
		
		// On initialise la liste de nom de ville
		List<String> listSortNameCity = new ArrayList<String>();
				
		// On parcourt chaque province de la liste de province
		for(District district : model.getListDistrict())
		{
			for(City city : district.getListCity())
			{
				// On ajoute la ville a la liste
				listSortNameCity.add(city.getS_name());
			}
		}
		
		// On trie cette liste
		Collections.sort(listSortNameCity);
		
		// On renvoie cette liste
		return  listSortNameCity;
	}
	
	/**
	 * Renvoie la liste de type de batiment triee par nom
	 * @return
	 */
	public List<String> getListTypeBuildingSortedByName() {
		
		// On initialise la liste de type de batiments
		List<String> listSortTypeBuilding = new ArrayList<String>();
				
		// On parcourt chaque province de la liste de province
		for(District district : model.getListDistrict())
		{
			// On parcourt chaque ville de la province
			for(City city : district.getListCity())
			{
				// On parcourt chaque batiment de la ville
				for(Building building : city.getListBuilding())
				{
					boolean typeExists = false;
					
					// On parcourt chaque valeur de la liste de type de batiments
					for(String s_type : listSortTypeBuilding)
					{
						// Si le type du batiment est deja dans la liste
						if(building.getS_type().equals(s_type))
						{
							typeExists = true;
							break;
						}
					}
					
					// Si le type n'existe pas encore dans la liste
					if(!typeExists)
					{
						listSortTypeBuilding.add(building.getS_type());
					}
				}
			}
		}
		
		// On trie cette liste
		Collections.sort(listSortTypeBuilding);
		
		// On renvoie cette liste
		return  listSortTypeBuilding;
	}
	
	/**
	 * Renvoie la liste de materiel triee par nom
	 * @return
	 */
	public List<String> getListMaterialSortedByName()
	{
		// On initialise la liste de nom de province		
		List<String> listSortNameMaterial = new ArrayList<String>();
		
		// On parcourt chaque materiel de la liste de materiel
		for(Material typeMaterial : model.getListMaterial())
		{
			// On ajoute le materiel a la liste
			 listSortNameMaterial.add(typeMaterial.getS_name());
		}

		// On trie cette liste
		Collections.sort(listSortNameMaterial);
		
		// On renvoie cette liste
		return listSortNameMaterial;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////	GETTERS TABMODEL		//////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
		
	/**
	 * Renvoie le modele du tableau contenant les objets
	 * @return
	 */
	public TabModel getTabModelObject() {
		return model.getTabModelObject();
	}

	/**
	 * Renvoie le modele contenant les batiments a louer ou acheter
	 * @return
	 */
	public TabModel getTabModelBuildingToBuy() {
		return model.getTabModelBuildingToBuy();
	}
	
	/**
	 * Renvoie le modele contenant les batiments d'un personnage ou de tous les personnages d'une organisation
	 * @return
	 */
	public TabModel getTabModelBuilding() {
		return model.getTabModelBuilding();
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////	TOOLS OF THE LOAD PART		//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	/**
	 * Ajoute un point de "chargement" afin de mettre a jour le pourcentage de chargement
	 * @param s_informationToAdd
	 */
	public void addCheckPoint(String s_informationToAdd)
	{
		// On recupere le nombre de province parcourues
		int i_nbDistrictBrowsed = model.getI_nbDistrictBrowsed();
		
		// On incremente le nombre de provinces
		i_nbDistrictBrowsed++;
		
		// On met a jour le nombre de province parcourues dans le modele
		model.setI_nbDistrictBrowsed(i_nbDistrictBrowsed);
		
		// On met a jour le pourcentage dans la vue
		if(model.getI_nbDistrict() != 0)
		{
			windowLoad.setPercentage((i_nbDistrictBrowsed*100)/model.getI_nbDistrict());			
		}
		
		// On ajoute la ligne au modele
		model.addLineInformation(s_informationToAdd);
	}
	
	/**
	 * Met a jour le statut de chargement (en cours, termine...) 
	 * @param s_newStatus
	 */
	public void updateStatusLoadWindow(String s_newStatus)
	{
		windowLoad.updateInfo(s_newStatus); 
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////	TOOLS OF THE PART : PAGES JAUKES		//////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	/**
	 * Renvoie un tableau de liste de personnes (isPerson = true) ou d'organisations (isPerson = false)
	 * qui contiennent les memes lettres que s_beginName
	 * @param s_beginName
	 * @param isPerson
	 * @return
	 */
	public List<String> getPersonCorrespondate(String s_beginName, boolean isPerson)
	{
		// On initialise la liste de nom correspondant
		List<String> listNameCorrespondate = new ArrayList<String>();
		
		// Si c'est une personne 
		if(isPerson)
		{
			// On parcourt chaque personne au sein du modele
			for(Person person : model.getListPerson())
			{
				String s_nameWithoutSmiley = person.getS_nameWithoutSmiley();
				
				// Si le nom fourni en parametre n'est pas nul ET
				// Si le nombre de caractere du nom fourni en parametre et inferieur au nom recupere ET
				if(s_beginName != null && s_beginName.length() <= s_nameWithoutSmiley.length())
				{
					if(s_nameWithoutSmiley.toUpperCase().contains(s_beginName.toUpperCase()))
					{
						// On ajoute le nom de la personne a la liste de string
						listNameCorrespondate.add(person.getS_nameHtml());	
					}
				}
			}
		}
		

		// On trie la liste
		Collections.sort(listNameCorrespondate);

		// Renvoie la liste de personnes correspondante
		return listNameCorrespondate;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////	TOOLS OF THE PART : GPS		//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	/**
	 * Renvoie la map 2D
	 * @return
	 */
	public List<List<Integer>> getMap2D() {
		return model.getMap2D().getMap();
	}

	/**
	 * Renvoie la map 3D
	 * @return
	 */
	public List<List<Integer>> getMap3D() {
		return model.getMap3D().getMap();
	}
	
	/**
	 * Convertie un point 2D en 3D
	 * @param point2D
	 * @return
	 */
	public Point convert2DTo3D(Point point2D)
	{
		return model.convert2DTo3D(point2D);
	}

	/**
	 * Renvoie le polygone ou se trouve la souris au sein du GPS
	 * @return
	 */
	public Polygon getPolygonMouse() {
		return model.getModelGPS().getPolygonMouse();
	}
	
    /**
     * Renvoie le polygone de depart au sein du GPS
     * @return
     */
	public Polygon getPolygonBegin() {
		return model.getModelGPS().getPolygonBegin();
	}

	/**
	 * Renvoie le polygone de fin au sein du GPS
	 * @return
	 */
	public Polygon getPolygonEnd() {
		return model.getModelGPS().getPolygonEnd();
	}

	/**
	 * Renvoie la liste de point du trajet entre le depart et la fin a dessiner
	 * @return
	 */
	public List<Point> getListPointToDraw() {
		return model.getModelGPS().getListPointToDraw();
	}
	
	/**
	 * Renvoie le nombre de pdv utile pour parcourir le chemin
	 * @return
	 */
	public float getF_pdvWay() {
		return model.getModelGPS().getF_pdvWay();
	}

	/**
	 * Change le polygone ou se trouve la souris
	 * @param polygonMouse
	 */
	public void setPolygonMouse(Polygon polygonMouse) {
		if(polygonMouse != null)
		{
			polygonMouse.searchNamePolygon(model);
		}
		model.getModelGPS().setPolygonMouse(polygonMouse);
	}

	/**
	 * Change le polygone de depart au sein du GPS
	 * @param polygonBegin
	 */
	public void setPolygonBegin(Polygon polygonBegin) {
		if(polygonBegin != null)
		{
			polygonBegin.searchNamePolygon(model);
		}
		model.getModelGPS().setPolygonBegin(polygonBegin);
	}

	/**
	 * Change le polygone de fin au sein du GPS
	 * @param polygonEnd
	 */
	public void setPolygonEnd(Polygon polygonEnd) {
		if(polygonEnd != null)
		{
			polygonEnd.searchNamePolygon(model);
		}
		model.getModelGPS().setPolygonEnd(polygonEnd);
	}
	
	/**
	 * Change la liste de point du trajet entre le depart et la fin a dessiner
	 * @param listPointToDraw
	 */
	public void setListPointToDraw(List<Point> listPointToDraw) {
		model.getModelGPS().setListPointToDraw(listPointToDraw);
	}
	
	/**
	 * Change le nombre de pdv utile pour parcourir le chemin
	 * @param f_pdvWay
	 */
	public void setF_pdvWay(float f_pdvWay) {
		model.getModelGPS().setF_pdVWay(f_pdvWay);
	}
		
	/**
	 * Calcul le nombre de pdv minimum afin d'effectuer le chemin entre le depart et la fin
	 */
	public void calculatePdVWay()
	{
		
		// Si le polygone de depart est defini
		// Si le polygone de fin est defini
		// Si le polygone de depart n'est pas celui de fin
		if(getPolygonBegin() != null && getPolygonEnd() != null && (getPolygonBegin().getPoint2D().x != getPolygonEnd().getPoint2D().x || getPolygonBegin().getPoint2D().y != getPolygonEnd().getPoint2D().y))
		{
			CalcThreadManager calcThreadManager = new CalcThreadManager(this,25);
			calcThreadManager.start();	
		}
		
		// Sinon
		else
		{
			// Change la liste de point a dessiner
			setListPointToDraw(null);
			
			// Change le nombre de pdv
			setF_pdvWay(-1);
		}	

		
	}

	/**
	 * Change le type de map 2D/3D
	 * @param is3DMap
	 */
	public void changeTypeMap(boolean is3DMap)
	{
		model.getModelGPS().setIs3DMap(is3DMap);
	}
	
	/**
	 * Change l'echelle de la map 2D/3D
	 * @param i_scale
	 * @param is3DMap
	 */
	public void changeScaleMap(int i_scale, boolean is3DMap)
	{
		if(is3DMap)
		{
			model.getModelGPS().setI_idTabCoeffZoom(i_scale);		
		}
		else
		{
			model.getModelGPS().setI_sizeSquareMap2D(i_scale);
		}

	}
	
	/**
	 * Dezoome la map 2D/3D
	 * @param is3DMap
	 * @return
	 */
	public boolean unzoomMap(boolean is3DMap)
	{
		if(is3DMap)
		{
			// On recupere l'identifiant
			int i_idTabCoeffZoom = model.getModelGPS().getI_idTabCoeffZoom();

			// Si l'identifiant n'est pas nul
			if(i_idTabCoeffZoom > 0)
			{
				i_idTabCoeffZoom--;
				model.getModelGPS().setI_idTabCoeffZoom(i_idTabCoeffZoom);
				
				return true;
			}		
		}
		else
		{
			int i_sizeSquareMap2D = model.getModelGPS().getI_sizeSquareMap2D();
			i_sizeSquareMap2D--;
			
			//TODO Gerer les min et les max
			model.getModelGPS().setI_sizeSquareMap2D(i_sizeSquareMap2D);
		}
		return false;
	}
	
	/**
	 * Zoom sur la map 2D/3D
	 * @param is3DMap
	 * @return
	 */
	@SuppressWarnings("static-access")
	public boolean zoomMap(boolean is3DMap)
	{
		if(is3DMap)
		{
			// On recupere l'identifiant
			int i_idTabCoeffZoom = model.getModelGPS().getI_idTabCoeffZoom();
			int i_tab_percentageZoomMap3D[] = model.getModelGPS().getI_tab_percentageZoomMap3D();  
			
			// Si l'identifiant n'est pas nul
			if(i_idTabCoeffZoom < i_tab_percentageZoomMap3D.length - 1)
			{
				i_idTabCoeffZoom++;
				model.getModelGPS().setI_idTabCoeffZoom(i_idTabCoeffZoom);
				
				return true;
			}
		}
		else
		{
			int i_sizeSquareMap2D = model.getModelGPS().getI_sizeSquareMap2D();
			i_sizeSquareMap2D++;
			
			//TODO Gerer les min et les max
			model.getModelGPS().setI_sizeSquareMap2D(i_sizeSquareMap2D);
		}
		return false;
	}
		
	/**
	 * Renvoie le pourcentage du zoom de la map 3D
	 * @return
	 */
	@SuppressWarnings("static-access")
	public int getI_percentageZoom()
	{
		// On recupere l'identifiant
		int i_idTabCoeffZoom = model.getModelGPS().getI_idTabCoeffZoom();
		int i_tab_percentageZoomMap3D[] = model.getModelGPS().getI_tab_percentageZoomMap3D();
		
		return i_tab_percentageZoomMap3D[i_idTabCoeffZoom];
	}

	/**
	 * Renvoie la liste de pourcentage du zoom de la map 3D
	 * @return
	 */
	@SuppressWarnings("static-access")
	public int[] getTab_I_percentageZoomMap3D() {
		return model.getModelGPS().getI_tab_percentageZoomMap3D();
	}
	
	/**
	 * Renvoie l'id du pourcentage du zoom de la map 3D
	 * @return
	 */
	public int getI_idPercentageZoom()
	{
		return model.getModelGPS().getI_idTabCoeffZoom();
	}

	/**
	 * Renvoie le polygone d'une ville dont le nom est place en parametre
	 * @param s_nameCity
	 * @return
	 */
	public Polygon getPolygon(String s_nameCity) {
		
		Polygon polygonToReturn = new Polygon();
		
		for(District district : model.getListDistrict())
		{
			for(City city : district.getListCity())
			{
				if(city.getS_name().equals(s_nameCity))
				{
					int i_x = district.getI_x() * model.getI_nbHorizontalSquareInDistrict() + city.getI_x() + (district.getI_y()%2)*model.getI_nbHorizontalSquareInDistrict()/2;
					int i_y = district.getI_y() * model.getI_nbVerticalSquareInDistrict() + city.getI_y();

					Point point2D = new Point(i_x, i_y);
					
					polygonToReturn.setPoint2D(point2D);
					polygonToReturn.setPoint3D(convert2DTo3D(point2D));
					
					return polygonToReturn;
				}
			}
		}
		
		return polygonToReturn;
	}
	
	/**
	 * Change le type de map 3D
	 * @param i_typeMap3D
	 */
	public void setI_typeMap3D(int i_typeMap3D) {
		model.getModelGPS().setI_typeMap3D(i_typeMap3D);
	}
	
	/**
	 * Renvoie le type de map 3D
	 * @return
	 */
	public int getI_typeMap3D() {
		return model.getModelGPS().getI_typeMap3D();
	}

	/**
	 * Change le boolean indiquant si il est possible de prendre l'avion
	 */
	public void setHasSelectedPlane(boolean hasSelectedPlane) {	
		// On memorise si l'utilisateur a authorise les voyages en avion dans le modele
		model.getModelGPS().setHasSelectedPlane(hasSelectedPlane);
	}
	
	/**
	 * Change le boolean indiquant si il est possible de prendre le train
	 */
	public void setHasSelectedTrain(boolean hasSelectedTrain) {	
		// On memorise si l'utilisateur a authorise les voyages en train dans le modele
		model.getModelGPS().setHasSelectedTrain(hasSelectedTrain);
	}
	
	/**
	 * Change le boolean indiquant si il est possible de prendre le bateau
	 */
	public void setHasSelectedBoat(boolean hasSelectedBoat) {	
		// On memorise si l'utilisateur a authorise les voyages en bateau dans le modele
		model.getModelGPS().setHasSelectedPlane(hasSelectedBoat);
	}

	/**
	 * Deplace la map dans la zone d'affichage
	 * @param is3Dmap
	 * @param wheelRotation
	 */
	public void mooveMap(boolean is3Dmap, int wheelRotation) {
		view.getPanelGPS().getPanelGPSResult().mooveMap(is3Dmap, wheelRotation);		
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////	GETTERS & SETTERS	//////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	/**
	 * Renvoie le booleen indiquant si on a reussit le chargement du fichier de configuration
	 * @return
	 */
	public boolean hasSuccedLoad() {
		return hasSuccedLoad;
	}

	/**
	 * Change le booleen indiquant si on a reussit le chargement du fichier de configuration
	 * @param hasSuccedLoad
	 */
	public void setHasSuccedLoad(boolean hasSuccedLoad) {
		this.hasSuccedLoad = hasSuccedLoad;
	}

	/**
	 * Renvoie le modele
	 */
	public Model getModel() {
		return model;
	}

	/**
	 * Change le modele
	 */
	public void setModel(Model model) {
		this.model = model;
	}

	/**
	 * Renvoie la vue
	 */
	public View getView() {
		return view;
	}
	
	/**
	 * Change la vue
	 */
	public void setView(View view) {
		this.view = view;
	}

	/**
	 * Change le skin du logiciel
	 */
	@SuppressWarnings("static-access")
	public void changeSkin()
	{
		//String[] tab_choiceSkin = {"Standard","BlueLight","AluOxide","Classy","BlackEye","Simple2D","WhiteVision","SkyMetallic","MauveMetallic","OrangeMetallic","BlueSteel","BlackMoon","BlueMoon","SilverMoon","BlueIce","GreenDream","BlackStar"};
		String[] tab_choiceSkin = {"Standard","BlueSteel","BlackMoon","BlueMoon","SilverMoon","BlueIce","GreenDream","BlackStar"};
		
		String s = (String)JOptionPane.showInputDialog(
                getView(),
                "Skin :",
                "Choix du skin",
                JOptionPane.PLAIN_MESSAGE,
                ConfigIcon.getInstance().LOGO_KITKIT_16,
                tab_choiceSkin,
                tab_choiceSkin[0]);

		//If a string was returned, say so.
		if ((s != null) && (s.length() > 0))
		{
			int i_idSkinSelected = 0;
			
			for(int i_idSkin = 0 ; i_idSkin < tab_choiceSkin.length ; i_idSkin++)
			{
				if(s.equals(tab_choiceSkin[i_idSkin]))
				{
					i_idSkinSelected = i_idSkin;
					break;
				}
			}
						
			try
			{
				LookAndFeel lookToApply = null;
				Rectangle bounds = view.getBounds();
				
				if(i_idSkinSelected !=0)
				{
					i_idSkinSelected += 10;
				}
				
				switch (i_idSkinSelected)
				{
/*
					case 2 :
						lookToApply = new SyntheticaBlueLightLookAndFeel();
						break;
					case 3 :
						lookToApply = new SyntheticaAluOxideLookAndFeel();
						break;
					case 4 :
						lookToApply = new SyntheticaClassyLookAndFeel();
						break;
					case 5 :
						lookToApply = new SyntheticaBlackEyeLookAndFeel();
						break;
					case 6 :
						lookToApply = new SyntheticaSimple2DLookAndFeel();
						break;
					case 7 :
						lookToApply = new SyntheticaWhiteVisionLookAndFeel();
						break;
					case 8 :
						lookToApply = new SyntheticaSkyMetallicLookAndFeel();
						break;
					case 9 :
						lookToApply = new SyntheticaMauveMetallicLookAndFeel();
						break;
					case 10 :
						lookToApply = new SyntheticaOrangeMetallicLookAndFeel();
						break;
*/
					case 11 :
						lookToApply = new SyntheticaBlueSteelLookAndFeel();
						break;
					case 12 :
						lookToApply = new SyntheticaBlackMoonLookAndFeel();
						break;
					case 13 :
						lookToApply = new SyntheticaBlueMoonLookAndFeel();
						break;
					case 14 :
						lookToApply = new SyntheticaSilverMoonLookAndFeel();
						break;
					case 15 :
						lookToApply = new SyntheticaBlueIceLookAndFeel();
						break;
					case 16 :
						lookToApply = new SyntheticaGreenDreamLookAndFeel();
						break;
					case 17 :
						lookToApply = new SyntheticaBlackStarLookAndFeel();
						break;					
					default :
						lookToApply = new SyntheticaStandardLookAndFeel();
						break;
				}
			
				UIManager.setLookAndFeel(lookToApply);
				SwingUtilities.updateComponentTreeUI(view);
				view.pack();
				view.setBounds(bounds);
			} 
			catch (Exception e) 
			{
				JOptionPane.showMessageDialog(getView(),"Problème lors du chargement du skin");
			}

		}
	}
}												 
