package ihm.part.pagesjaukes;


import ihm.I_Viewable;
import ihm.TabPanel;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableCellRenderer;

import model.tab.pagesjaukes.BuildingRenderer;


import controller.Controller;
import controller.I_ControllerDialog;

@SuppressWarnings("serial")
public class PanelPagesJaukesResult extends JPanel implements I_Viewable, I_ControllerDialog
{
	/**
	 * Table contenant toutes les vues
	 */
	private List<I_Viewable> listI_Viewable;
	
	/**
	 * Controlleur
	 */
	private Controller ctrl; 
	
    /**
     * JTable contenant les batiment dont une personne ou une organisation est proprietaire
     */
	private TabPanel jTableProprietaire;
		
	public PanelPagesJaukesResult()
	{
    	// On met un layout au panel
		setLayout(new BorderLayout());
   
		// On initialise les objets
    	initObject();

		// On ajoute les divers composants a cette liste
    	add(jTableProprietaire,BorderLayout.CENTER);

 	}
 	
	/**
	 * Initialise les objets
	 */
 	public void initObject()
	{
 		jTableProprietaire = new TabPanel();
 		jTableProprietaire.getTableau().setAutoCreateRowSorter(true);
 		
 		DefaultTableCellRenderer rendererPanelJaukes = new BuildingRenderer();	
 		jTableProprietaire.getTableau().setDefaultRenderer(Object.class, rendererPanelJaukes);
	}
	
	/**
	 * Renvoie la table contenant toutes les vues
	 */
	public List<I_Viewable> getListI_Viewable()
	{
		return listI_Viewable;
	}
	
	/**
	 * Change la table contenant toutes les vues
	 */
	public void setListI_Viewable(List<I_Viewable> listI_Viewable)
	{
		this.listI_Viewable = listI_Viewable;
		addI_Viewable();
	}

	/**
	 * On ajoute ce panel a la liste de vue
	 */
	public void addI_Viewable()
	{
		listI_Viewable.add(this);
	}
	
	/**
	 * On initialise les valeurs de l'IHM
	 */
	public void initValueIHM()
	{
		jTableProprietaire.setTabModel(ctrl.getTabModelBuilding());
	}

	/**
	 * On initialise les valeurs de l'IHM
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
