package ihm.part.gps;

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
public class TabPanelGPS extends JPanel implements	I_Viewable,
													I_ControllerDialog,
													PropertyChangeListener,
													I_SplitPaneView
{
	// Table contenant toutes les vues
	private List<I_Viewable> listI_Viewable;
	
	// Controlleur
	private Controller ctrl;
	
	// SplitPane
	private JSplitPane	splitPane;
	
	// Panel affichant la partie configuration du GPS
	private PanelGPSConfig panelGPSConfig;
	
	// Panel affichant la map du GPS
	private PanelGPSResult panelGPSResult;
  
	public TabPanelGPS()
	{
		// On change le layout
		setLayout(new BorderLayout());
    	
		// On initialise les objets
		initObject();

		// On place le SplitPane
    	splitPane.setDividerLocation(350);
    	
    	// Ajout du splitPane au centre du Panel
    	add(splitPane, BorderLayout.CENTER);
 	}
 	
 	public void initObject()
	{
 		// On cree le panel affichant la partie configuration du GPS
 		panelGPSConfig = new PanelGPSConfig();
 		
 		// On cree le panel affichant la map du GPS
 		panelGPSResult = new PanelGPSResult();
		
 		// On ajoute les deux panels au SplitPane
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,new JScrollPane(panelGPSConfig),panelGPSResult);
  
		// On ajoute les deux panels au SplitPane
		splitPane.addPropertyChangeListener("dividerLocation",this);
		
		// On authorise le "OneTouchExpandable" pour pouvoir retracter la partie configuration afin d'avoir une zone de dessin plus grande
		splitPane.setOneTouchExpandable(true); 
	}
	
	// Renvoie la table contenant toutes les vues
	public List<I_Viewable> getListI_Viewable()
	{
		return listI_Viewable;
	}
	
	// Change la table contenant toutes les vues
	public void setListI_Viewable(List<I_Viewable> listI_Viewable)
	{
		this.listI_Viewable = listI_Viewable;
		addI_Viewable();
	}

	// On ajoute ce panel a la liste de vue
	public void addI_Viewable()
	{
		listI_Viewable.add(this);
		panelGPSConfig.setListI_Viewable(listI_Viewable);
		panelGPSResult.setListI_Viewable(listI_Viewable);
	}
	
	// On initialise les valeurs de l'IHM
	public void initValueIHM()
	{

	}

	// Change le controlleur
	public void setController(Controller ctrl) {
		this.ctrl = ctrl;
		panelGPSConfig.setController(ctrl);
		panelGPSResult.setController(ctrl);
		
	}

	// Renvoie le controlleur
	public Controller getController() {
		return ctrl;
	}
	

	public PanelGPSConfig getPanelGPSConfig()
	{
		return panelGPSConfig;
	}

	public PanelGPSResult getPanelGPSResult()
	{
		return panelGPSResult;
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
