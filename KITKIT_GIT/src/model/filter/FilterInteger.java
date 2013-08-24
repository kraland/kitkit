package model.filter;

import java.util.List;

public class FilterInteger extends Filter{

	/**
	 * Valeur minimale (mis au lancement de l'application a 0)
	 */
	private int i_valueMin;
	
	/**
	 * Valeur maximale (mis au lancement de l'application a 100)
	 */
	private int i_valueMax;

	/**
	 * Constructeur de filtre d'entiers
	 * @param i_type
	 * @param i_idFilter
	 * @param i_nbFilter
	 * @param s_name
	 */
	public FilterInteger(int i_type, int i_idFilter, int i_nbFilter, String s_name) {
		super(i_type, i_idFilter, i_nbFilter, s_name);
	}
	
	/**
	 * Renvoie la valeur minimale
	 * @return
	 */
	public int getI_valueMin() {
		return i_valueMin;
	}
	
	/**
	 * Change la valeur minimale
	 * @param i_valueMin
	 */
	public void setI_valueMin(int i_valueMin) {
		this.i_valueMin = i_valueMin;
	}

	/**
	 * Renvoie la valeur maximale
	 * @return
	 */
	public int getI_valueMax() {
		return i_valueMax;
	}

	/**
	 * Change la valeur maximale
	 * @param i_valueMax
	 */
	public void setI_valueMax(int i_valueMax) {
		this.i_valueMax = i_valueMax;
	}

	@Override
	public boolean checkSpecificFilter(String strToCheck)
	{
		int i_value = Integer.valueOf(strToCheck);
		if( i_value >= i_valueMin && i_value <=i_valueMax)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public void keepParametersFilter(List<Object> listParameters)
	{
		//////////////////////////////
		// 		List<Object> :		//
		//////////////////////////////
		// - Valeur minimale		//
		// - Valeur maximale		//
		//////////////////////////////
		
		if(listParameters.size() == 2)
		{
			setI_valueMin((Integer)listParameters.get(0));
			setI_valueMax((Integer)listParameters.get(1));
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
		setI_valueMin(0);
		setI_valueMax(100);
	}
	
}
