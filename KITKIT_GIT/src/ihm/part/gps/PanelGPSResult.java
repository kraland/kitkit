package ihm.part.gps;

import ihm.I_Viewable;
import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import model.gps.I_ModelGPSListener;
import model.gps.Polygon;

import controller.Controller;
import controller.I_ControllerDialog;

@SuppressWarnings("serial")
public class PanelGPSResult extends JPanel implements	I_Viewable,
														I_ControllerDialog,
														I_ModelGPSListener
{
	// Table contenant toutes les vues
	private List<I_Viewable> listI_Viewable;
	
	// Liste des panels au sein du CardLayout
	private List<JPanel> listJPanelInCardLayout;
	
	// Controller
	private Controller ctrl; 
	 
	// Panel contenant les resultats en rapport avec une personne
	private  PanelGPS2D panelGPS2D;
	 
	// Panel contenant les resultats en rapport avec une organisation commerciale
	private PanelGPS3D	panelGPS3D;
	
	private JScrollPane scroll3D;
	
	private JScrollPane scroll2D;
  
	// CardLayout
	private CardLayout cardLayout;
	
	// ActionCommand utilise pour les panels au sein du CardLayout
	private String listActionCommand[] = {"GPS_2D","GPS_3D"};
	
	public PanelGPSResult()
	{		
		// On cree un nouveau cardlayout
		cardLayout = new CardLayout();
		
		// On change le layout
    	setLayout(cardLayout);
		
    	// On initialise les objets
    	initObject();
    }
 	
	// Initialise les objets
 	public void initObject()
	{
 		// Liste de JPanel dans le CardLayout
 		listJPanelInCardLayout = new ArrayList<JPanel>();
		
 		// On ajoute les panels au cardLayout
 		addPanels(cardLayout);
	}
	
 	// Ajoute les Panels au CardLayout
	private void addPanels(CardLayout cardLayout)
	{
		// On cree le panel GPS affichant la carte 2D et on l'ajoute a la liste de panels dans le CardLayout
		panelGPS2D = new PanelGPS2D();
		listJPanelInCardLayout.add(panelGPS2D);

		// On cree le panel GPS affichant la carte 3D et on l'ajoute a la liste de panels dans le CardLayout
		panelGPS3D = new PanelGPS3D();
		listJPanelInCardLayout.add(panelGPS3D);

		// On parcourt chaque case de la liste de panels dans le CardLayout 
		for(int i_idPanel = 0 ; i_idPanel < listJPanelInCardLayout.size() ; i_idPanel++)
		{
			// On ajoute le panel au sein du CardLayout
			if(i_idPanel == 0)
			{
				scroll2D = new JScrollPane(listJPanelInCardLayout.get(i_idPanel));
				add(scroll2D,listActionCommand[i_idPanel]);				
			}
			else
			{
				scroll3D = new JScrollPane(listJPanelInCardLayout.get(i_idPanel));
				add(scroll3D,listActionCommand[i_idPanel]);	
			}
		}
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
		panelGPS2D.setListI_Viewable(listI_Viewable);
		panelGPS3D.setListI_Viewable(listI_Viewable);
	}
	
	// On initialise les valeurs de l'IHM
	public void initValueIHM()
	{
		
	}

	// Change le controlleur
	public void setController(Controller ctrl) {
		
		this.ctrl = ctrl;
		panelGPS2D.setController(ctrl);
		panelGPS3D.setController(ctrl);
	}

	// Renvoie le controlleur
	public Controller getController() {
		return ctrl;
	}
	
	public PanelGPS2D getPanelGPS2D()
	{
		return panelGPS2D;
	}
	
	public PanelGPS3D getPanelGPS3D()
	{
		return panelGPS3D;
	}

	@Override
	public void typeMapChanged(boolean is3dMap) {
		// Si la 2D est selectionnee
		if(!is3dMap)
		{
			cardLayout.show(this,listActionCommand[0]);				
		}
		// Sinon
		else
		{
			cardLayout.show(this,listActionCommand[1]);				
		}
		
	}

	@Override
	public void scaleMapChanged(int i_scale, boolean is3dMap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void calculDone(float f_nbPdV) {
		// Rien a faire
	}

	@Override
	public void newPositionMouse(Polygon polygonMouse) {
		// Rien a faire
	}

	@Override
	public void beginSelected(Polygon polygonBegin) {
		// Rien a faire
	}

	@Override
	public void stopSelected(Polygon polygonStop) {
		// Rien a faire
	}

	@Override
	public void searchCriteriaChange() {
		// Rien a faire
	}

	@Override
	public void changeTypeMap3D(int i_typeMap3D) {
		// Rien a faire
	}

	public void mooveMap(boolean is3Dmap, int wheelRotation) {
		
		JScrollBar scrollBar = null;
		
		if(is3Dmap)
		{
			scrollBar = scroll3D.getVerticalScrollBar();
		}
		else
		{
			scrollBar = scroll2D.getVerticalScrollBar();
		}
		
		if (scrollBar != null)
		{
	         scrollBar.setValue(scrollBar.getValue() + (scrollBar.getBlockIncrement(wheelRotation)*wheelRotation)/2 - (scrollBar.getBlockIncrement()*5*wheelRotation)/2);
	    }
	}
	
}
