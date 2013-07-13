package model.tab.cenkury21;

import java.util.ArrayList;
import java.util.List;

import model.tab.HTMLLink;
import model.tab.HTMLName;
import model.tab.I_DefaultContent;

public class ContentBuildingToBuy implements I_DefaultContent
{
	/**
	 * Liste de string
	 */
	private List<String> listColumn;
	
	/**
	 * Liste d'objets du contenu du modele
	 */
	private List<Object> listObject;
	
	/**
	 * Niveau du batiment
	 */
	private int i_level;
	
	/**
	 * Label affichant le type du batiment
	 */
	private String s_typeBuilding;
	
	/**
	 * Label contenant le nom du batiment
	 */
	private	HTMLLink htmlLinkNameBuilding;
	
	/**
	 * Label affichant la case
	 */
	private String s_case;
	
	/**
	 * Label contenant le lien et le nom de la ville 
	 */
	private	HTMLLink htmlLinkNameCity;
	
	/**
	 * Label contenant le lien et le nom de la province
	 */
	private HTMLLink htmlLinkNameDistrict;
	
	/**
	 * String contenant le nom du proprietaire
	 */
	private	HTMLName htmlNameOwner;
	
	/**
	 * Integer contenant le prix de vente du batiment
	 */
	private	Integer i_price;
	
	/**
	 * String affichant si la vente est reservee
	 */
	private	String s_reserved;

	@Override
	public Object getValueAt(int i_idColumn) {
		return listObject.get(i_idColumn);
	}

	@Override
	public void setValueAt(int i_idColumn, Object objetToSet) {
		listObject.set(i_idColumn, objetToSet);
	}

	@Override
	public void add(List<String> listString) {
		
		// On recupere l'entier contenant le niveau du batiment
		i_level = Integer.valueOf(listString.get(0));
		
		// On recupere le string contenant le type du batiment
		s_typeBuilding = listString.get(1);
		
		// On cree le lien contenant le nom du batiment 
		htmlLinkNameBuilding = new HTMLLink(listString.get(2),listString.get(3));
		
		// On recupere le string affichant la case
		s_case = listString.get(4);
		
		// On cree le lien contenant le nom de la ville
		htmlLinkNameCity = new HTMLLink(listString.get(5),listString.get(6));
		
		// On cree le lien contenant le nom de la province
		htmlLinkNameDistrict = new HTMLLink(listString.get(7),listString.get(8));
		
		// On recupere le string contenant le nom du proprietaire
		htmlNameOwner = new HTMLName(listString.get(9), listString.get(10));
		
		// On recupere l'entier contenantle prix de vente du batiment
		i_price = Integer.valueOf(listString.get(11));
		
		// On recupere le string contenant si la vente est reservee
		s_reserved = listString.get(12);
	

		// On ajoute les differents objets a la liste
		listObject = new ArrayList<Object>();
		listObject.add(i_level);
		listObject.add(s_typeBuilding);
		listObject.add(htmlLinkNameBuilding);
		listObject.add(s_case);
		listObject.add(htmlLinkNameCity);
		listObject.add(htmlLinkNameDistrict);
		listObject.add(htmlNameOwner);
		listObject.add(i_price);
		listObject.add(s_reserved);
		
		listColumn = new ArrayList<String>();
		listColumn.add(listString.get(0));
		listColumn.add(listString.get(1));
		listColumn.add(listString.get(2));
		listColumn.add(listString.get(4));
		listColumn.add(listString.get(6));
		listColumn.add(listString.get(8));
		listColumn.add(listString.get(10));
		listColumn.add(listString.get(11));
		listColumn.add(listString.get(12));
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getClass(int i_idColumn) {
		switch (i_idColumn) {
		case 0:
			return Integer.class;
		case 1:
			return String.class;
		case 2:
			return HTMLLink.class;
		case 3:
			return String.class;
		case 4:
			return HTMLLink.class;
		case 5:
			return HTMLLink.class;
		case 6:
			return HTMLName.class;
		case 7:
			return Integer.class;
		case 8:
			return String.class;
		default:
			break;
		}
		return null;
	}

	@Override
	public List<String> getRealListString() {
		return listColumn;
	}



}
