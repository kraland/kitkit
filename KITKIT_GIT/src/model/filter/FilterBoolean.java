package model.filter;

import java.util.List;

public class FilterBoolean extends Filter{

	/**
	 * Booleen indiquant quelle valeur on filtre
	 */
	private boolean isFilteringValue;
	
	
	public FilterBoolean(int i_type, int i_idFilter, int i_nbFilter, String s_name) {
		super(i_type, i_idFilter, i_nbFilter, s_name);
	}

	/**
	 * Change le booleen indiquant quelle valeur on filtre
	 * @return
	 */
	public boolean isFilteringValue() {
		return isFilteringValue;
	}

	/**
	 * Renvoie le booleen indiquant quelle valeur on filtre
	 * @param isFilteringValue
	 */
	public void setFilteringValue(boolean isFilteringValue) {
		this.isFilteringValue = isFilteringValue;
	}


	@Override
	public boolean checkSpecificFilter(String strToCheck) {
		
		if(isFilteringValue && strToCheck.equals("true"))
		{
			return true;
		}
		else if(!isFilteringValue && strToCheck.equals("false"))
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
		// - FilteringValue			//
		//////////////////////////////
		
		if(listParameters.size() == 1)
		{
			setFilteringValue((Boolean)listParameters.get(0));
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
		setFilteringValue(false);
	}
}
