package controller.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import model.I_ModelDialog;
import model.Model;
import model.content.Building;
import model.content.City;
import model.content.Material;
import model.content.Person;
import model.content.District;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import controller.Controller;
import controller.I_ControllerDialog;

//TODO
// Parser a refaire pour le fichier qui va contenir les organisations
// Parser a refaire pour le fichier qui va contenir les valeurs indicatives

public class ParserConfContent implements I_ModelDialog, I_ControllerDialog, Runnable{

	/**
	 * Booleen activant les traces
	 */
	private boolean isDebugging = false;
	
	/**
	 * Modele
	 */
	private Model model;
	
	/**
	 * Controlleur
	 */
	private Controller ctrl;
	
	/**
	 * Chemin du fichier a charger
	 */
	private String s_pathFileToBrowse;
	

	@Override
	public void run() {
		browseFile();
	}
	
	@SuppressWarnings("static-access")
	private void browseFile()
	{
		// Chargement du fichier .xml
		File fileConf = new File(s_pathFileToBrowse);
		
		if (fileConf.exists())
		{
			DocumentBuilderFactory dbf_builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder db_builder;
			
			// Numero de la province temporaire
			String s_num = "";
			
			try
			{
				db_builder = dbf_builderFactory.newDocumentBuilder();
				Document d_documentXml = db_builder.parse(fileConf);
				d_documentXml.getDocumentElement().normalize();
				d_documentXml.getChildNodes();
				
				//Identification du noeud <Timestamp>
				NodeList nl_TS = d_documentXml.getElementsByTagName(ConfigBalise.getInstance().BALISE_TIMESTAMP);
				long l_timestamp = Long.parseLong(nl_TS.item(0).getFirstChild().getNodeValue());
				model.setL_timestampUpdate(l_timestamp);
				if(isDebugging)System.out.println("ParserConfContent.java ; boolean browseFile(String sPathFileToBrowse) | Timestamp : " + l_timestamp);
				
				// Identification des noeuds <Province>
				NodeList nl_District = d_documentXml.getElementsByTagName(ConfigBalise.getInstance().BALISE_DISTRICT);
				model.setI_nbDistrict(nl_District.getLength());
				if(isDebugging)System.out.println("ParserConfContent.java ; boolean browseFile(String sPathFileToBrowse) | Nombre de Provinces : " + nl_District.getLength());
				
				District districtTmp;
				City cityTmp;
				Building buildingTmp;
				
				// On parcourt chaque province
				for (int idDistrict = 0; idDistrict < nl_District.getLength() ; idDistrict++)
				{
					// On memorise le numero de la province
					s_num = String.valueOf(idDistrict);
					if(isDebugging)System.out.println("Parcourt de la province n° " + (idDistrict + 1) + " au sein du XML.");
					
					// On recupere le noeud contenant la province
					Node n_DistrictNode = nl_District.item(idDistrict);
					
					if (n_DistrictNode.getNodeType() == Node.ELEMENT_NODE)
					{
						
						// On recupere l'identifiant de la province
						int i_idDistrict =  Integer.parseInt(getStringValue(n_DistrictNode,ConfigBalise.getInstance().BALISE_DISTRICT_ID));
						
						// On recupere la position en abscisse de la province
						int i_xDistrict =  Integer.parseInt(getStringValue(n_DistrictNode,ConfigBalise.getInstance().BALISE_DISTRICT_X));
						
						// On recupere la position en ordonnee de la province
						int i_yDistrict =  Integer.parseInt(getStringValue(n_DistrictNode,ConfigBalise.getInstance().BALISE_DISTRICT_Y));
						
						// On recupere le nom de la province
						String s_nameDistrict = getStringValue(n_DistrictNode,ConfigBalise.getInstance().BALISE_DISTRICT_NAME);
						
						// On recupere la map horizontale "brute"
						String s_mapDistrict = getStringValue(n_DistrictNode,ConfigBalise.getInstance().BALISE_DISTRICT_MAP);
						
						// On applique le split afin de mettre chaque ligne de la map 2D dans un tableau
						String s_line_mapDistrict[] = s_mapDistrict.split(";");
						
						// On initialise une map de "String"
						List<List<Integer>> mapDistrict = new ArrayList<List<Integer>>();
						
						// On parcourt chaque ligne du tableau
						for(int i_idLine = 0 ; i_idLine < s_line_mapDistrict.length ; i_idLine++)
						{
							// On ajoute une nouvelle ligne a la map de String
							mapDistrict.add(new ArrayList<Integer>());
							
							// On applique le split afin de mettre chaque case d'une ligne de la map 2D dans un tableau
							String s_case_map[] = s_line_mapDistrict[i_idLine].split(",");
							
							// On parcourt chaque colonne de la map
							for(int i_idColumn = 0 ; i_idColumn < s_case_map.length ; i_idColumn++)
							{
								// On ajoute la valeur correspondante
								mapDistrict.get(i_idLine).add(Integer.valueOf(s_case_map[i_idColumn]));
							}
							
						}
						
						// On cree une province et on lui change ces specifications
						districtTmp = new District();
						districtTmp.setI_id(i_idDistrict);
						districtTmp.setI_x(i_xDistrict);
						districtTmp.setI_y(i_yDistrict);
						districtTmp.setS_name(s_nameDistrict);
						districtTmp.setMap2D(mapDistrict);
											
						// Identification des noeuds <City>					
						Element e_ListCityItem = (Element) n_DistrictNode;
						NodeList nl_cityFieldList = e_ListCityItem.getElementsByTagName(ConfigBalise.getInstance().BALISE_CITY);
						if(isDebugging)System.out.println("ParserConfContent.java ; boolean browseFile(String sPathFileToBrowse) | Nombre de ville(s) : " + nl_cityFieldList.getLength());
						
						// Parcours de toutes les balises <Ville>
						for (int i_numCity = 0; i_numCity < nl_cityFieldList.getLength() ; i_numCity++)
						{
							
							// On recupere le noeud "Ville" correspondant
							Node n_CityNode = nl_cityFieldList.item(i_numCity);

							// On cree une ville et on lui change ces specifications
							cityTmp = new City();
							
							if (n_CityNode.getNodeType() == Node.ELEMENT_NODE)
							{
								// On recupere l'identifiant de la province
								int i_idCity =  Integer.parseInt(getStringValue(n_CityNode,ConfigBalise.getInstance().BALISE_CITY_ID));
								
								// On recupere la position en abscisse de la province
								int i_xCity =  Integer.parseInt(getStringValue(n_CityNode,ConfigBalise.getInstance().BALISE_CITY_X));
								
								// On recupere la position en ordonnee de la province
								int i_yCity =  Integer.parseInt(getStringValue(n_CityNode,ConfigBalise.getInstance().BALISE_CITY_Y));
								
								// On recupere le nom de la province
								String s_nameCity = getStringValue(n_CityNode,ConfigBalise.getInstance().BALISE_CITY_NAME);
								
								// On recupere la map horizontale "brute"
								String s_mapCity = getStringValue(n_CityNode,ConfigBalise.getInstance().BALISE_CITY_MAP);
								
								// On applique le split afin de mettre chaque ligne de la map 2D dans un tableau
								String s_line_mapCity[] = s_mapCity.split(";");
								
								// On initialise une map de "String"
								List<List<String>> mapCity = new ArrayList<List<String>>();
								
								// On parcourt chaque ligne du tableau
								for(int i_idLine = 0 ; i_idLine < s_line_mapCity.length ; i_idLine++)
								{
									// On ajoute une nouvelle ligne a la map de String
									mapCity.add(new ArrayList<String>());
									
									// On applique le split afin de mettre chaque case d'une ligne de la map 2D dans un tableau
									String s_case_map[] = s_line_mapCity[i_idLine].split(",");
									
									// On parcourt chaque colonne de la map
									for(int i_idColumn = 0 ; i_idColumn < s_case_map.length ; i_idColumn++)
									{
										// On ajoute la valeur correspondante
										mapCity.get(i_idLine).add(s_case_map[i_idColumn]);
									}
									
								}
								
								// On cree une ville et on lui change ces specifications
								cityTmp.setI_id(i_idCity);
								cityTmp.setI_x(i_xCity);
								cityTmp.setI_y(i_yCity);
								cityTmp.setS_name(s_nameCity);
								cityTmp.setMap2D(mapCity);
								
								// Identification des noeuds <Batiment>					
								Element e_ListBuildingItem = (Element) n_CityNode;
								NodeList nl_buildingFieldList = e_ListBuildingItem.getElementsByTagName(ConfigBalise.getInstance().BALISE_BUILDING);
								if(isDebugging)System.out.println("ParserConfContent.java ; boolean browseFile(String sPathFileToBrowse) | Nombre de batiment(s) : " + nl_buildingFieldList.getLength());
						
								// Parcours de toutes les balises <Batiment>
								for (int i_numBuilding = 0; i_numBuilding < nl_buildingFieldList.getLength() ; i_numBuilding++)
								{
									// On cree un batiment
									buildingTmp = new Building();
									
									// On recupere le noeud "Batiment" correspondant
									Node n_BuildingNode = nl_buildingFieldList.item(i_numBuilding);
									if (n_BuildingNode.getNodeType() == Node.ELEMENT_NODE)
									{
																			
										// On recupere l'identifiant du batiment
										int i_idBuilding =  Integer.parseInt(getStringValue(n_BuildingNode,ConfigBalise.getInstance().BALISE_BUILDING_ID));
										
										// On recupere la position en abscisse du batiment
										int i_xBuilding =  Integer.parseInt(getStringValue(n_BuildingNode,ConfigBalise.getInstance().BALISE_BUILDING_X));
										
										// On recupere la position en ordonnee du batiment
										int i_yBuilding =  Integer.parseInt(getStringValue(n_BuildingNode,ConfigBalise.getInstance().BALISE_BUILDING_Y));
										
										// On recupere le type du batiment
										String s_typeBuilding = getStringValue(n_BuildingNode,ConfigBalise.getInstance().BALISE_BUILDING_TYPE);
										
										// On recupere le nom du batiment
										String s_nameBuilding = getStringValue(n_BuildingNode,ConfigBalise.getInstance().BALISE_BUILDING_NAME);
										
										// On recupere le proprietaire du batiment
										String s_ownerBuilding = getStringValue(n_BuildingNode,ConfigBalise.getInstance().BALISE_BUILDING_OWNER);
										boolean isPublicBuilding = false;
										boolean isClosed = false;
										Person ownerBuilding = null;
										
										if(s_ownerBuilding.equals(""))
										{
											isPublicBuilding = true;
										}
										else
										{
											ownerBuilding = model.lookForPerson(s_ownerBuilding);
											ownerBuilding.addBuilding(buildingTmp);
										}
											
										// On recupere la valeur du batiment
										int i_valueBuilding =  Integer.parseInt(getStringValue(n_BuildingNode,ConfigBalise.getInstance().BALISE_BUILDING_VALUE));
										
										// On verifie si le batiment est une "chasse" gardee ou non...
										Element e_element = (Element) n_BuildingNode;
										NodeList nl_fieldList = e_element.getElementsByTagName(ConfigBalise.getInstance().BALISE_BUILDING_CLOSE);
										if(nl_fieldList.getLength() > 0)
										{
											isClosed = true;
										}
										
										// On recupere le nombre de pdb du batiment
										int i_pdbBuilding =  Integer.parseInt(getStringValue(n_BuildingNode,ConfigBalise.getInstance().BALISE_BUILDING_PDB));

										// On recupere le niveau du batiment
										int i_levelBuilding =  Integer.parseInt(getStringValue(n_BuildingNode,ConfigBalise.getInstance().BALISE_BUILDING_LEVEL));
										
										// On recupere le salaire du batiment
										int i_salaryBuilding =  Integer.parseInt(getStringValue(n_BuildingNode,ConfigBalise.getInstance().BALISE_BUILDING_SALARY));
										
										// On recupere le nombre de salaire du batiment
										int i_nbSalaryBuilding =  Integer.parseInt(getStringValue(n_BuildingNode,ConfigBalise.getInstance().BALISE_BUILDING_NB_SALARY));

											
										boolean isPossibleToBuyBuilding = false;
										boolean isReservedForBuy = false;
										int i_soldPriceBuilding = 0;
										
										// On recupere le prix de vente du batiment
										//Element e_element = (Element) n_BuildingNode;
										nl_fieldList = e_element.getElementsByTagName(ConfigBalise.getInstance().BALISE_BUILDING_BUY_PRICE);
										if(nl_fieldList.getLength() > 0)
										{
											isPossibleToBuyBuilding = true;
											i_soldPriceBuilding = Integer.parseInt(getStringValue(n_BuildingNode,ConfigBalise.getInstance().BALISE_BUILDING_BUY_PRICE));
											
											// On verifie si le batiment est une "chasse" gardee ou non...
											e_element = (Element) n_BuildingNode;
											nl_fieldList = e_element.getElementsByTagName(ConfigBalise.getInstance().BALISE_BUILDING_ISRESERVED);
											if(nl_fieldList.getLength() > 0)
											{
												isReservedForBuy = true;
											}
										}
									
									
										// On change les differents parametres du batiment
										buildingTmp.setI_id(i_idBuilding);
										buildingTmp.setI_x(i_xBuilding);
										buildingTmp.setI_y(i_yBuilding);
										buildingTmp.setS_type(s_typeBuilding);
										buildingTmp.setS_name(s_nameBuilding);
										buildingTmp.setOwner(ownerBuilding);
										buildingTmp.setIsPublicBuilding(isPublicBuilding);
										buildingTmp.setI_value(i_valueBuilding);
										buildingTmp.setI_nbPdB(i_pdbBuilding);
										buildingTmp.setI_level(i_levelBuilding);
										buildingTmp.setI_salary(i_salaryBuilding);
										buildingTmp.setI_nbSalary(i_nbSalaryBuilding);
										buildingTmp.setIsPossibleToBuy(isPossibleToBuyBuilding);
										buildingTmp.setI_soldPrice(i_soldPriceBuilding);
										buildingTmp.setIsReservedForBuy(isReservedForBuy);
										buildingTmp.setIsClosed(isClosed);
										buildingTmp.setCity(cityTmp);
										
										// Identification des noeuds <Material>					
										Element e_ListMaterialItem = (Element) n_BuildingNode;
										NodeList nl_materialFieldList = e_ListMaterialItem.getElementsByTagName(ConfigBalise.getInstance().BALISE_MATERIAL);
										if(isDebugging)System.out.println("Nombre de materiel(s) : " + nl_materialFieldList.getLength());
						
										// Parcours de toutes les balises <Material>
										for (int i_numMaterial = 0; i_numMaterial < nl_materialFieldList.getLength() ; i_numMaterial++)
										{
											// On recupere le noeud "Materiel" correspondant
											Node n_MaterialNode = nl_materialFieldList.item(i_numMaterial);
											if (n_MaterialNode.getNodeType() == Node.ELEMENT_NODE)
											{
												// Variable temporaires specifiant le batiment
												String s_nameMaterial;
												int i_priceBuyMaterial, i_priceSoldMaterial, i_quantityMaterialStocked, i_quantityMaterialMax;
												
												// On recupere le nom du materiel
												s_nameMaterial = getStringValue(n_MaterialNode,ConfigBalise.getInstance().BALISE_MATERIAL_NAME);
												
												// On recupere le prix d'achat du materiel
												i_priceBuyMaterial = Integer.parseInt(getStringValue(n_MaterialNode,ConfigBalise.getInstance().BALISE_MATERIAL_BUY_PRICE));
												
												// On recupere le prix de vente du materiel
												i_priceSoldMaterial = Integer.parseInt(getStringValue(n_MaterialNode,ConfigBalise.getInstance().BALISE_MATERIAL_SOLD_PRICE));
												
												// On recupere la quantite de materiel en stock
												i_quantityMaterialStocked = Integer.parseInt(getStringValue(n_MaterialNode,ConfigBalise.getInstance().BALISE_MATERIAL_QUANTITY_STOCKED));
												
												// On recupere la quantite de materiel maximum
												i_quantityMaterialMax = Integer.parseInt(getStringValue(n_MaterialNode,ConfigBalise.getInstance().BALISE_MATERIAL_QUANTITY_MAX));
												
												// On recherche ce materiel dans le modele.
												// - Si il existe alors on ne fait rien
												// - Sinon on ajoute ce materiel a la liste de materiel
												model.lookForMaterial(s_nameMaterial);
												
												// On cree un Materiel et on lui change ces specifications
												Material materialTmp = new Material();
												materialTmp.setS_name(s_nameMaterial);
												materialTmp.setI_priceBuy(i_priceBuyMaterial);
												materialTmp.setI_priceSold(i_priceSoldMaterial);
												materialTmp.setI_stockedQuantity(i_quantityMaterialStocked);
												materialTmp.setI_maxQuantity(i_quantityMaterialMax);
												
												// On ajoute le materiel au batiment
												buildingTmp.addMaterial(materialTmp);
											}
										}								
										
										// On ajoute le batiment dans la ville
										cityTmp.addBuilding(buildingTmp);	
										cityTmp.setDistrict(districtTmp);
									}
								}
								// On ajoute la ville dans la province
								districtTmp.addCity(cityTmp);
							}
						}
						
						// On ajoute la province qu'on vient de parcourir au sein du modele
						model.addDistrict(districtTmp);
						
						// On met a jour le statut de la fenetre de chargement
						ctrl.updateStatusLoadWindow(districtTmp.getS_name() + " chargée");
						
						// On ajoute un check point
						ctrl.addCheckPoint("Chargement de " + districtTmp.getS_name() + " OK");
					}
				}
			}
			
			catch (Exception e)
			{
				if(s_num != "")
				{
					ctrl.addCheckPoint("Chargement de la province n°" + s_num + " KO");
					ctrl.updateStatusLoadWindow("Chargement de la " + s_num + "ème province KO");
				}
				else
				{
					ctrl.addCheckPoint("Chargement fichier XML contenu KO");
					ctrl.updateStatusLoadWindow("Chargement fichier XML contenu KO");
				}
				ctrl.setHasSuccedLoad(false);
			}
			
			ctrl.setHasSuccedLoad(true);			
		}
		else
		{
			ctrl.updateStatusLoadWindow("Le fichier " + s_pathFileToBrowse + " n'existe pas.");
			ctrl.setHasSuccedLoad(false);
		}
	}
	
	
	/**
	 * Renvoie la valeur au sein du noeud (node) a l'interieur de la balise (s_baliseToCatch)
	 * @param node
	 * @param s_baliseToCatch
	 * @return
	 */
	public String getStringValue(Node node, String s_baliseToCatch)
	{
		// Recuperation du champ <s_baliseToCatch>
		Element e_element = (Element) node;
		NodeList nl_fieldList = e_element.getElementsByTagName(s_baliseToCatch);
		Element e_field = (Element) nl_fieldList.item(0);
		NodeList nl_nodeList = e_field.getChildNodes();

		String s_value = "";
		if(nl_nodeList.item(0)!=null)
		{
			s_value = nl_nodeList.item(0).getNodeValue();
		}
		if(isDebugging)System.out.println("ParserConfContent.java ; String getStringValue(Node node, String s_baliseToCatch) | <" + s_baliseToCatch + "> = " + s_value);
		
		return s_value;
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
	 * Change le controlleur
	 */
	public void setController(Controller ctrl) {
		this.ctrl = ctrl;
	}

	/**
	 * Renvoie le controlleur
	 */
	public Controller getController() {
		return ctrl;
	}

	/**
	 * Renvoie le nom du fichier a charger
	 * @return
	 */
	public String getS_PathFileToBrowse() {
		return s_pathFileToBrowse;
	}

	/**
	 * Change le nom du fichier a charger
	 * @param sPathFileToBrowse
	 */
	public void setS_PathFileToBrowse(String s_pathFileToBrowse) {
		this.s_pathFileToBrowse = s_pathFileToBrowse;
	}

}
