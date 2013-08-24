package model.filter;

import java.util.List;

public class FilterKIValue extends Filter{

	/**
	 * Tableau contenant les differentes valeurs des batiments de KI
	 */
	private static String[] tab_s_KIValue = {"entre 0 et 500","entre 500 et 1000","entre 1000 et 2500","entre 2500 et 5000","entre 5000 et 10000","au-delà de 10000"};
	
	/**
	 * Valeur minimale (mis au lancement de l'application a 0)
	 */
	private boolean[] tab_isValueSelected;
	


	/**
	 * Constructeur de filtre d'entiers
	 * @param i_type
	 * @param i_idFilter
	 * @param i_nbFilter
	 * @param s_name
	 */
	public FilterKIValue(int i_type, int i_idFilter, int i_nbFilter, String s_name) {
		super(i_type, i_idFilter, i_nbFilter, s_name);
	}
	
	/**
	 * Renvoie le tableau contenant les differentes valeurs des batiments de KI
	 * @return
	 */
	public static String[] getTab_s_KIValue() {
		return tab_s_KIValue;
	}
	
	/**
	 * Renvoie le tableau contenant lees filtres des differentes valeurs des batiments de KI
	 * @return
	 */
	public boolean[] getTab_isValueSelected() {
		return tab_isValueSelected;
	}
	
	
	@Override
	public boolean checkSpecificFilter(String strToCheck)
	{
		int i_value = Integer.valueOf(strToCheck);
		if( i_value > 0 && i_value <= tab_isValueSelected.length)
		{
			if(tab_isValueSelected[i_value-1])
				return true;
			else
				return false;
		}
		else
		{
			System.err.println("FilterKIValue.java - Valeur Inconnue");
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void keepParametersFilter(List<Object> listParameters)
	{
		//////////////////////////////
		// 		List<Object> :		//
		//////////////////////////////
		// - valueSelected0			//
		//   valueSelected1			//
		//   valueSelected2			//
		//   valueSelected3			//
		//   valueSelected4			//
		//   valueSelected5			//
		//////////////////////////////
		
		if(listParameters.size() == 1)
		{
			List<Boolean> listFilter = (List<Boolean>) listParameters.get(0);
			if( listFilter.size() == tab_isValueSelected.length)
			{
				for(int i_idObject = 0 ; i_idObject < listFilter.size() ; i_idObject++)
				{
					tab_isValueSelected[i_idObject] = listFilter.get(i_idObject);
				}
			}
		}
		else
		{
			System.err.println("FilterKIValue.java - Erreur de taille");
		}
	}

	@SuppressWarnings("unused")
	@Override
	public void initFilter() {
		// On desactive le filtre
		setActive(false);
		
		// On initialise le tableau des filtres a faux
		tab_isValueSelected = new boolean[tab_s_KIValue.length];
		for(boolean isSelected : tab_isValueSelected)
		{
			isSelected = false;
		}
	}
}
