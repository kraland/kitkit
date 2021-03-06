package ihm.part.cenkury21;

import ihm.I_Viewable;
import ihm.TabPanel;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableCellRenderer;

import model.tab.cenkury21.BuildingToBuyRenderer;

import controller.Controller;
import controller.I_ControllerDialog;

@SuppressWarnings("serial")
public class PanelCenkury21Result extends JPanel implements I_Viewable, I_ControllerDialog
{
	/**
	 * Table contenant tous les classes de vues
	 */
	private List<I_Viewable> listI_Viewable;
	
	/**
	 * Controlleur
	 */
	private Controller ctrl; 
	
	/**
	 * JTable contenant les resultats
	 */
	private TabPanel jTableResult;

	/**
	 * Constructeur de panel de resultat de l'onglet Cenkury21
	 */
	public PanelCenkury21Result()
	{
		// Change le layout
    	setLayout(new BorderLayout());
    	
    	// Initialise les objets
    	initObject();
    	
    	// Ajoute le tableau au centre du panel
    	add(jTableResult,BorderLayout.CENTER);
 	}
 	
	/**
	 * Initialise les objets
	 */
 	public void initObject()
	{
 		jTableResult = new TabPanel();
 		jTableResult.getTableau().setAutoCreateRowSorter(true);
 		
 		DefaultTableCellRenderer rendererCenkury21 = new BuildingToBuyRenderer();	
 		jTableResult.getTableau().setDefaultRenderer(Object.class, rendererCenkury21);
	}
	
	/**
	 * Change la liste d'interface visualisable
	 * @param listI_Viewable
	 */
	public List<I_Viewable> getListI_Viewable()
	{
		return listI_Viewable;
	}

	/**
	 * Renvoie la liste d'interface visualisable
	 * @return
	 */
	public void setListI_Viewable(List<I_Viewable> listI_Viewable)
	{
		this.listI_Viewable = listI_Viewable;
		addI_Viewable();
	}

	/**
	 * Ajoute une interface visualisable
	 */
	public void addI_Viewable()
	{
		listI_Viewable.add(this);
	}
	
	/**
	 * Initialise les valeurs de l'IHM
	 */
	public void initValueIHM()
	{
		jTableResult.setTabModel(ctrl.getTabModelBuildingToBuy());
	}

	/**
	 * Change le controlleur
	 */
	public void setController(Controller ctrl) {
		this.ctrl = ctrl;
	}

	/**
	 * Renvoie le controlleur
	 */
	public Controller getController() {
		return ctrl;
	}
}
