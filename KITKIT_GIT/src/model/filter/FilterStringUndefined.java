package model.filter;

import java.util.List;


public class FilterStringUndefined extends Filter {

	/**
	 * String a filtrer
	 */
	private String s_Filter ="";


	/**
	 * Constructeur de filtre de liste de string definie
	 * @param i_type
	 * @param i_idFilter
	 * @param i_nbFilter
	 * @param s_name
	 */

	public FilterStringUndefined(int i_type, int i_idFilter, int i_nbFilter, String s_name) {
		super(i_type, i_idFilter, i_nbFilter, s_name);
	}
	
	
	/**
	 * Renvoie le filtre
	 * @return
	 */
	public String getS_Filter() {
		return s_Filter;
	}

	/**
	 * Change le filtre
	 * @param s_Filter
	 */
	public void setS_Filter(String s_Filter) {
		this.s_Filter = s_Filter;
	}
	
	@Override
	public boolean checkSpecificFilter(String strToCheck) {
		
		if(s_Filter.isEmpty() || (strToCheck != null && !strToCheck.isEmpty() && strToCheck.toUpperCase().contains(s_Filter.toUpperCase())))
		{
			return true;
		}
		
		return false;
	}

	@Override
	public void keepParametersFilter(List<Object> listParameters) {
		//////////////////////////////
		// 		List<Object> :		//
		//////////////////////////////
		// - String					//
		//////////////////////////////
		
		if(listParameters.size() == 1)
		{
			setS_Filter((String)listParameters.get(0));
		}
		else
		{
			System.out.println("Erreur de taille");
		}
	}
}
