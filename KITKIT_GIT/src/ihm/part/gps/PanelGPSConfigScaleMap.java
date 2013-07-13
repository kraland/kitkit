package ihm.part.gps;

import ihm.I_Viewable;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.gps.I_ModelGPSListener;
import model.gps.Polygon;
import controller.Controller;
import controller.I_ControllerDialog;

@SuppressWarnings("serial")
public class PanelGPSConfigScaleMap extends JPanel implements	I_Viewable,
																I_ControllerDialog,
																I_ModelGPSListener,
																ActionListener,
																ChangeListener{

	/**
	 * Table contenant tous les classes de vues
	 */
	private List<I_Viewable> listI_Viewable;
	
	/**
	 * Controleur
	 */
	private Controller ctrl; 
	
	/**
	 * JSpinner permettant de selectionner l'echelle 2D
	 */
	private JSpinner jSpinnerScale2D;
	
	/**
	 * JSpinner permettant de selectionner l'echelle 3D
	 */
	@SuppressWarnings("rawtypes")
	private JComboBox jComboBoxScale3D;
	
	/**
	 * Modele de la combobox
	 */
	@SuppressWarnings("rawtypes")
	DefaultComboBoxModel model;
	
	/**
	 * Item de la combo
	 */
	Vector<String> comboBoxItems;
	
	/**
	 * CardLayout
	 */
	private CardLayout cardLayout;
	
	/**
	 * ActionCommand utilise pour les panels au sein du CardLayout
	 */
	private String listActionCommand[] = {"GPS_2D","GPS_3D"};
	
	public PanelGPSConfigScaleMap()
	{
		// On cree un nouveau cardlayout
		cardLayout = new CardLayout();
		
		// On cree une boite
		Box bH; 
		
		// On change le layout
    	setLayout(cardLayout);
		
    	// On initialise les objets
    	initObject();
    	    	
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////// On cree la partie pour modifier l'echelle 2D /////////////////////////////// 
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    	
    	bH = Box.createHorizontalBox();
   	
    	bH.add(new JLabel("Echelle :"));
    	bH.add(Box.createHorizontalStrut(5));
    	bH.add(jSpinnerScale2D);
    	bH.add(Box.createHorizontalStrut(5));
    	bH.add(new JLabel("px"));
    	   	
    	add(bH,listActionCommand[0]);
    	
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////// On cree la partie pour modifier l'echelle 3D /////////////////////////////// 
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    	
    	bH = Box.createHorizontalBox();
       	
    	bH.add(new JLabel("Echelle :"));
    	bH.add(Box.createHorizontalStrut(5));
    	bH.add(jComboBoxScale3D);
    	bH.add(Box.createHorizontalStrut(5));
    	bH.add(new JLabel("%"));
    	
    	add(bH,listActionCommand[1]);
    	
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initObject() {

		comboBoxItems = new Vector<String>();
		model = new DefaultComboBoxModel(comboBoxItems);
		
		// On initialise la Combo
		jComboBoxScale3D = new JComboBox(model);
				
		// On ajoute un listener a la combo
		jComboBoxScale3D.addActionListener(this);
		
		// On initialise le Spinner
		jSpinnerScale2D = new JSpinner(new SpinnerNumberModel(4,4,15,1));
		
		// On ajoute un listener au spinner
		jSpinnerScale2D.addChangeListener(this);
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
	}
	

	@Override
	public void initValueIHM() {

		comboBoxItems.removeAllElements();
		
		// On transfere chaque entier dans le tableau de String
	    for (int idTab : ctrl.getTab_I_percentageZoomMap3D())
	    {
	    	comboBoxItems.add(Integer.toString(idTab));
	    }
	    	
		jComboBoxScale3D.setSelectedIndex(0);
		
	}

	@Override
	public void typeMapChanged(boolean is3dMap) {
		
		// Si on demande d'afficher une map 3D
		if(is3dMap)
		{
			cardLayout.show(this,listActionCommand[1]);
		}
		// Sinon, on demande d'afficher une map 2D
		else
		{
			cardLayout.show(this,listActionCommand[0]);
		}
	}

	@Override
	public void scaleMapChanged(int i_scale, boolean is3dMap) {
		// Si on change l'echelle de la map 3D
		if(is3dMap)
		{
			jComboBoxScale3D.setSelectedIndex(i_scale);
		}
		// Si on change l'echelle de la map 2D
		else
		{
			jSpinnerScale2D.setValue(i_scale);
		}
	}

	@Override
	public void calculDone(float f_nbPdV) {
		// Aucun impact sur cette partie de l'IHM		
	}


	@Override
	public void newPositionMouse(Polygon polygonMouse) {
		// Aucun impact sur cette partie de l'IHM	
	}

	@Override
	public void beginSelected(Polygon polygonBegin) {
		// Aucun impact sur cette partie de l'IHM	
	}

	@Override
	public void stopSelected(Polygon polygonStop) {
		// Aucun impact sur cette partie de l'IHM	
	}

	@Override
	public void searchCriteriaChange() {
		// Aucun impact sur cette partie de l'IHM	
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		
		// On recupere la source de l'actionEvent
		Object source = actionEvent.getSource();
		
		// Si il s'agit d'une action sur la combobox
		if(source == jComboBoxScale3D)
		{
			ctrl.changeScaleMap(jComboBoxScale3D.getSelectedIndex(),true);
		}
	}

	@Override
	public void stateChanged(ChangeEvent changeEvent) {
		
		// On recupere la source du changeEvent
		Object source = changeEvent.getSource();
		
		// Si il s'agit d'une action sur le spinner
		if(source == jSpinnerScale2D)
		{
			ctrl.changeScaleMap((Integer)jSpinnerScale2D.getValue(),false);
		}
	}

	@Override
	public void changeTypeMap3D(int i_typeMap3D) {
		// Rien a faire
	}

}
