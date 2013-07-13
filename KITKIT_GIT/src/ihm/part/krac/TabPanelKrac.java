package ihm.part.krac;



import ihm.I_SplitPaneView;
import ihm.I_Viewable;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import controller.Controller;
import controller.I_ControllerDialog;

@SuppressWarnings("serial")
public class TabPanelKrac extends JPanel implements I_Viewable,
													I_ControllerDialog,
													PropertyChangeListener,
													I_SplitPaneView
{
	/**
	 * Table contenant tous les classes de vues filles
	 */
	private List<I_Viewable> listI_Viewable;
	
	/**
	 * Controlleur
	 */
	private Controller ctrl; 
	
	/**
	 * JSplitPane
	 */
	private JSplitPane	splitPane;
	
	/**
	 * Panel affichant la partie configuration de l'onglet Krac
	 */
	private PanelKracConfig panelKracConfig;
	
	/**
	 * Panel affichant la partie resultat de l'onglet Krac
	 */
	private PanelKracResult panelKracResult;
  
	/**
	 * Constructeur de l'onglet Krac
	 */
	public TabPanelKrac()
	{
		// On change le layout
    	setLayout(new BorderLayout());
    	
    	// On initialise les objets
    	initObject();

    	// On change la position du Splitpane
    	splitPane.setDividerLocation(350);
    	
    	// On ajoute le splitpane au centre
    	add(splitPane, BorderLayout.CENTER);
 	}
 	
 	public void initObject()
	{
 		// On initialise le panel de configuration
 		panelKracConfig = new PanelKracConfig();
 		
 		// On initialise le panel de resultat
 		panelKracResult = new PanelKracResult();
		
 		// On ajoute les deux panels au SplitPane
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,new JScrollPane(panelKracConfig),panelKracResult);
		
		splitPane.addPropertyChangeListener("dividerLocation",this);
		
		// On authorise le "OneTouchExpandable" pour pouvoir retracter la partie configuration afin d'avoir une zone de dessin plus grande
    	splitPane.setOneTouchExpandable(true); 
	}
	
	/**
	 * Renvoie la liste d'interface visualisable
	 * @return
	 */	
	public List<I_Viewable> getListI_Viewable()
	{
		return listI_Viewable;
	}
	
	/**
	 * Change la liste d'interface visualisable
	 * @param listI_Viewable
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
		panelKracConfig.setListI_Viewable(listI_Viewable);
		panelKracResult.setListI_Viewable(listI_Viewable);
	}
	
	/**
	 * Initialise les valeurs de l'IHM
	 */	
	public void initValueIHM()
	{

	}

	/**
	 * Change le controlleur
	 */
	public void setController(Controller ctrl)
	{
		this.ctrl = ctrl;
		panelKracConfig.setController(ctrl);
		panelKracResult.setController(ctrl);
	}

	/**
	 * Renvoie le controlleur
	 */
	public Controller getController() {
		return ctrl;
	}

	@Override
	public void setDividerLocationSplitPane(int i_dividerLocation) {
		splitPane.setDividerLocation(i_dividerLocation);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(ctrl != null)
			ctrl.getView().setDividerLocationAllSplitPane(splitPane.getDividerLocation());
	}
}
