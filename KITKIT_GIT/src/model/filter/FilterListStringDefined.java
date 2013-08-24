package model.filter;

import java.util.ArrayList;
import java.util.List;

public class FilterListStringDefined extends Filter {

	/**
	 * Liste de string definie
	 */
	private List<String> listStringDefined;
	
	/**
	 * Liste de string a filtrer
	 */
	private List<String> listStringFiltered;
	
	/**
	 * Constructeur de filtre de liste de string definie
	 * @param i_type
	 * @param i_idFilter
	 * @param i_nbFilter
	 * @param s_name
	 */

	public FilterListStringDefined(int i_type, int i_idFilter, int i_nbFilter, String s_name) {
		super(i_type, i_idFilter, i_nbFilter, s_name);
	}

	/**
	 * Renvoie la liste de string definie
	 * @return
	 */
	public List<String> getListStringDefined() {
		return listStringDefined;
	}

	/**
	 * Change la liste de string definie
	 * @param listStringDefined
	 */
	public void setListStringDefined(List<String> listStringDefined) {
		this.listStringDefined = listStringDefined;
	}

	/**
	 * Renvoie la liste de string filtree
	 * @return
	 */
	public List<String> getListStringFiltered() {
		return listStringFiltered;
	}

	/**
	 * Change la liste de string filtree
	 * @param listStringFiltered
	 */
	public void setListStringFiltered(List<String> listStringFiltered) {
		this.listStringFiltered = listStringFiltered;
	}
		
	@Override
	public boolean checkSpecificFilter(String strToCheck) {
		
		// On parcourt la liste de string filtree
		for(String stringFiltered : listStringFiltered)
		{
			if(strToCheck.equals(stringFiltered))
			{
				return true;
			}
		}
		
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void keepParametersFilter(List<Object> listParameters) {
		//////////////////////////////
		// 		List<Object> :		//
		//////////////////////////////
		// - Liste de string filtree//
		//////////////////////////////
		
		if(listParameters.size() == 1)
		{
			setListStringFiltered((List<String>)listParameters.get(0));
		}
		else
		{
			System.out.println("Erreur de taille");
		}
	}

	@Override
	public void initFilter() {
		// On desactive le filtre
		setActive(false);
		
		// On initialise le filtre
		if(null == listStringFiltered)
		{
			listStringFiltered = new ArrayList<String>();
		}
		listStringFiltered.clear();
				
	}
}
