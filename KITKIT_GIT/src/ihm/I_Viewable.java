package ihm;

import java.util.List;

public interface I_Viewable
{
	/**
	 * Renvoie la liste d'interface visualisable
	 * @return
	 */
	public List<I_Viewable> getListI_Viewable(); 
	
	/**
	 * Change la liste d'interface visualisable
	 * @param listI_Viewable
	 */
	public void setListI_Viewable(List<I_Viewable> listI_Viewable);

	/**
	 * Ajoute une interface visualisable
	 */
	public void addI_Viewable();
	
	
	/**
	 * Initialise les valeurs de l'IHM
	 */
	public void initValueIHM();
}
