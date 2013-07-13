package model.filter;

import java.util.List;

public abstract class Filter {

	/**
	 * Booleen indiquant si le filtre est actif
	 */
	private boolean isActive;
	
	/**
	 * Type du filtre
	 */
	private int i_type;
	
	/**
	 * ID du filtre
	 */
	private int i_idFilter;
	
	/**
	 * Nombre de filtre
	 */
	private int i_nbFilter;
	
	/**
	 * Nom du filtre
	 */
	private String s_name;
	
	/**
	 * Constructeur de filtre
	 * @param i_type
	 * @param i_idFilter
	 * @param i_nbFilter
	 */
	public Filter(int i_type, int i_idFilter, int i_nbFilter, String s_name)
	{
		setS_name(s_name);
		setActive(false);
		setI_type(i_type);
		setI_idFilter(i_idFilter);
		setI_nbFilter(i_nbFilter);
				
	}

	/**
	 * Renvoie le booleen indiquant si le filtre est actif
	 * @return
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * Change le booleen indiquant si le filtre est actif
	 * @param isActive
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * Renvoie le type de filtre
	 * @return
	 */
	public int getI_type() {
		return i_type;
	}

	/**
	 * Change le type de filtre
	 * @param i_type
	 */
	public void setI_type(int i_type) {
		this.i_type = i_type;
	}

	/**
	 * Renvoie l'ID du filtre
	 * @return
	 */
	public int getI_idFilter() {
		return i_idFilter;
	}

	/**
	 * Change l'ID du filtre
	 * @param i_idFilter
	 */
	public void setI_idFilter(int i_idFilter) {
		this.i_idFilter = i_idFilter;
	}

	/**
	 * Renvoie le nombre de filtre
	 * @return
	 */
	public int getI_nbFilter() {
		return i_nbFilter;
	}

	/**
	 * Changer le nombre de filtre
	 * @param i_nbFilter
	 */
	public void setI_nbFilter(int i_nbFilter) {
		this.i_nbFilter = i_nbFilter;
	}

	/**
	 * Renvoie le nom du filtre
	 * @return
	 */
	public String getS_name() {
		return s_name;
	}

	/**
	 * Change le nom du filtre
	 * @param s_name
	 */
	public void setS_name(String s_name) {
		this.s_name = s_name;
	}
	
	/**
	 * Methode pour verifier si la donnee respecte le filtre general
	 * @param listStr
	 * @return
	 */
	public boolean checkFilter(List<String> listStr)
	{
		if(isActive)
		{
			return checkSpecificFilter(listStr.get(i_idFilter));
		}
		else
		{
			// On renvoie vrai si le filtre n'est pas actif
			return true;			
		}
	}
	
	/**
	 * Methode pour verifier si la donnee respecte le filtre specifique
	 * @param strToCheck
	 * @return
	 */
	public abstract boolean checkSpecificFilter(String strToCheck);
	
	/**
	 * Methode pour enregistrer les parametres du filtre
	 * @param listParameters
	 */
	public abstract void keepParametersFilter(List<Object> listParameters);

}
