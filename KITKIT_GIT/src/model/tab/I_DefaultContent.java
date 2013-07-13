package model.tab;

import java.util.List;

public interface I_DefaultContent
{
	/**
	 * On renvoie l'objet se situant dans la colonne placee en parametre
	 * @param i_idColumn
	 */
	public Object getValueAt(int i_idColumn);
	
	/**
	 * On change l'objet se situant dans la colonne place en parametre
	 * @param i_idColumn
	 * @param objetToSet
	 */
	public void setValueAt(int i_idColumn, Object objetToSet);
	
	/**
	 * Ajout de la liste de String au modele
	 * @param listString
	 */
	public void add(List<String> listString);
	
	/**
	 * Renvoie la classe de la colonne propre au contenu
	 * @param i_idColumn
	 */
	@SuppressWarnings("rawtypes")
	public Class getClass(int i_idColumn);
	
	public List<String> getRealListString();
}
