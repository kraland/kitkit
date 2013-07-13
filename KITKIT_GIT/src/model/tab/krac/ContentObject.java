package model.tab.krac;

import java.util.ArrayList;
import java.util.List;

import model.tab.HTMLLink;
import model.tab.I_DefaultContent;

public class ContentObject implements I_DefaultContent
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
	 * Label contenant le prix d'achat
	 */
	private	Integer i_buyPrice;
	
	/**
	 * Label contenant le prix de vente
	 */
	private	Integer i_soldPrice;
	
	/**
	 * Label contenant la quantite en stock
	 */
	private Integer i_quantity;
	
	/**
	 * Label contenant la quantite max
	 */
	private	Integer i_quantityMax;
	
	/**
	 * Label affichant si le batiment est ferme
	 */
	private	String		s_closed;

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
	
		// On recupere l'entier contenant le prix de vente
		i_soldPrice = Integer.valueOf(listString.get(9));
		
		// On recupere l'entier contenant le prix d'achat
		i_buyPrice = Integer.valueOf(listString.get(10));
		
		// On recupere l'entier contenant la quantite d'un produit
		i_quantity = Integer.valueOf(listString.get(11));
		
		// On recupere l'entier contenant la quantite maximale d'un produit
		i_quantityMax = Integer.valueOf(listString.get(12));

		// On recupere le string indiquant si le batiment est ferme ou non
		s_closed = listString.get(13);
		
		
		// On ajoute les differents objets a la liste
		listObject = new ArrayList<Object>();
		listObject.add(i_level);
		listObject.add(s_typeBuilding);
		listObject.add(htmlLinkNameBuilding);
		listObject.add(s_case);
		listObject.add(htmlLinkNameCity);
		listObject.add(htmlLinkNameDistrict);
		listObject.add(i_soldPrice);
		listObject.add(i_buyPrice);
		listObject.add(i_quantity);
		listObject.add(i_quantityMax);
		listObject.add(s_closed);
		
		// On cree la liste de donnees utiles
		listColumn = new ArrayList<String>();
		listColumn.add(listString.get(0));
		listColumn.add(listString.get(1));
		listColumn.add(listString.get(3));
		listColumn.add(listString.get(4));
		listColumn.add(listString.get(6));
		listColumn.add(listString.get(8));
		listColumn.add(listString.get(9));
		listColumn.add(listString.get(10));
		listColumn.add(listString.get(11));
		listColumn.add(listString.get(12));
		listColumn.add(listString.get(13));
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
			return Integer.class;
		case 7:
			return Integer.class;
		case 8:
			return Integer.class;
		case 9:
			return Integer.class;
		case 10:
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
