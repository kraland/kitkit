package ihm.part.krac;

import ihm.ConfigIcon;
import ihm.IHMTools;
import ihm.I_Viewable;
import ihm.part.I_TableViewer;
import ihm.part.PanelExtendedFilter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.tab.TabModel;


import controller.Controller;
import controller.I_ControllerDialog;

@SuppressWarnings("serial")
public class PanelKracConfig extends JPanel  implements ActionListener,
														ChangeListener,
														I_Viewable,
														I_ControllerDialog,
														I_TableViewer
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
	 * Bouton permettant d'afficher un globe
	 */
	private JButton jButtonDistrict;
	
	/**
	 * Combobox contenant les provinces
	 */
	@SuppressWarnings("rawtypes")
	private JComboBox jComboDistrict;
	
	/**
	 * Modele de la combobox
	 */
	@SuppressWarnings("rawtypes")
	DefaultComboBoxModel modelDistrict;
	
	/**
	 * Item de la combo
	 */
	Vector<String> comboBoxItemsDistrict;
	
	/**
	 * Bouton permettant d'afficher un rayon
	 */
	private JButton jButtonRadius;
	
	/**
	 * JSpinner affichant le nombre de province
	 */
	private JSpinner jSpinnerNbDistrictAround;
	
	/**
	 * Bouton permettant d'afficher une clef
	 */
	private JButton jButtonTools;
	
	/**
	 * Combobox contenant les objets
	 */
	@SuppressWarnings("rawtypes")
	private JComboBox jComboTools;
	
	/**
	 * Modele de la combobox
	 */
	@SuppressWarnings("rawtypes")
	DefaultComboBoxModel modelTools;
	
	/**
	 * Item de la combo
	 */
	Vector<String> comboBoxItemsTools;
	
	/**
	 * Boutton lancant la recherche
	 */
	private JButton jButtonSearch;

	/**
	 * Label indiquant le resultat de la recherche
	 */
	private JLabel jLabelSearch;
	
	/**
	 * Panel contenant les filtres
	 */
	private PanelExtendedFilter jPanelFilter;
	
	/**
	 * Label affichant ou non une alerte si au moins un filtre est actif
	 */
	private JButton jButtonFilterIndicator;
	
	/**
	 * Liste de boutons de filtre
	 */
	private JButton[] list_jButtonFilter;
		
	/**
	 * Constructeur de panel de configuration de l'onglet KRAC
	 */
	public PanelKracConfig()
	{
		// On change le layout
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    	
		// On cree des boites
		Box bV, bH, bVTmp; 
		
		// On initialise les objets
    	initObject();
    	
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////// On cree la partie Criteres ///////////////////////////////////////// 
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////  
    	
    	JPanel panelCriteria = new JPanel();
    	panelCriteria.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(IHMTools.colorBorder),"Critères :"),BorderFactory.createEmptyBorder(1,1,1,1)));
    	panelCriteria.setLayout(new BorderLayout());
    	
    	bVTmp = Box.createVerticalBox();
    	    	    	
    	// On ajoute la partie permettant d'afficher la position de depart
    	bH = Box.createHorizontalBox();
    	bH.add(new JLabel("Localisation :"));
       	bH.add(Box.createHorizontalGlue());
       	bVTmp.add(bH);
       	bVTmp.add(Box.createVerticalStrut(5));
       	
    	// On ajoute la partie permettant de choisir la province
    	bH = Box.createHorizontalBox();
    	bH.add(jButtonDistrict);
    	bH.add(Box.createHorizontalStrut(5));
    	bH.add(jComboDistrict);
    	bVTmp.add(bH);
    	bVTmp.add(Box.createVerticalStrut(5));
    	
    	// On ajoute la partie permettant de choisir le nombre de province autour
    	bH = Box.createHorizontalBox();
    	bH.add(jButtonRadius);
    	bH.add(Box.createHorizontalStrut(5));
    	bH.add(jSpinnerNbDistrictAround);
    	bVTmp.add(bH);
    	bVTmp.add(Box.createVerticalStrut(10));
    	
    	// On ajoute la partie permettant d'afficher la position de depart
    	bH = Box.createHorizontalBox();
    	bH.add(new JLabel("Objet :"));
       	bH.add(Box.createHorizontalGlue());
       	bVTmp.add(bH);
       	bVTmp.add(Box.createVerticalStrut(5));
       	
    	// On ajoute la partie permettant de choisir la province
    	bH = Box.createHorizontalBox();
    	bH.add(jButtonTools);
    	bH.add(Box.createHorizontalStrut(5));
    	bH.add(jComboTools);
    	bVTmp.add(bH);
    	bVTmp.add(Box.createVerticalStrut(5));
    	
    	panelCriteria.add(bVTmp,BorderLayout.CENTER);
    	panelCriteria.setMaximumSize(new Dimension(3000,80));
    	
    	bV = Box.createVerticalBox();
    	bV.add(panelCriteria);
       	bV.add(Box.createVerticalStrut(10));
    	
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////// On cree la partie Recherche ///////////////////////////////////////// 
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////  
       	
    	// On ajoute la partie permettant d'afficher les resultats de la recherche
    	bH = Box.createHorizontalBox();
    	bH.add(Box.createHorizontalStrut(5));
    	bH.add(jButtonSearch);
    	bH.add(Box.createHorizontalStrut(5));
    	bH.add(jLabelSearch);
    	bH.add(Box.createHorizontalGlue());
    	bH.setMaximumSize(new Dimension(3000,32));
    	bV.add(bH);
    	bV.add(Box.createVerticalStrut(10));
    	
  
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////// On cree la partie Filtres ////////////////////////////////////////// 
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////  
    	
    	jPanelFilter = new PanelExtendedFilter();
    	bV.add(jPanelFilter);
    	jPanelFilter.setMaximumSize(new Dimension(3000,80));
    	
    	bV.add(Box.createVerticalGlue());
    	
    	add(bV,BorderLayout.CENTER);
	}
	
	@SuppressWarnings("static-access")
	public void createFilterPanel()
	{
		// Si le panel de filtre n'a pas deja ete initialise
		if(!jPanelFilter.hasDoneInit())
		{
			TabModel tabModel = ctrl.getModel().getTabModelObject();
			
			// On transmet son nom au panel affichant les filtres
			jPanelFilter.setS_name("Filtres :");
			
			// On cree le bouton indiquant si un filtre est actif
			jButtonFilterIndicator = IHMTools.getInstance().getNewButtonWithIcon_ActionListener(ConfigIcon.getInstance().EMPTY_16,this);
			jButtonFilterIndicator.addActionListener(this);
			
			// On transmet le bouton indiquant si un filtre est actif au panel affichant les filtres
			jPanelFilter.setJComponentBegin(jButtonFilterIndicator);
						
			// On cree le tableau de bouton
		 	list_jButtonFilter = new JButton[tabModel.getListTitleColumn().length];
	    	
		 	// On cree chaque bouton
	    	for(int i_idFilter = 0 ; i_idFilter < tabModel.getListTitleColumn().length ; i_idFilter++)
	    	{
	    		list_jButtonFilter[i_idFilter] = IHMTools.getInstance().getButtonFilter(ConfigIcon.getInstance().GRAY_CIRCLE,tabModel.getListTitleColumn()[i_idFilter]);
	    	}
	    	
			// On transmet la liste de bouton de filtres au panel affichant les filtres
			jPanelFilter.setListComponent(list_jButtonFilter);
	    	
			jPanelFilter.setiTableViewer(this);
			jPanelFilter.setController(ctrl);
			jPanelFilter.setTabModel(tabModel);
			
			// On cree le panel
			jPanelFilter.createPanelExtended();
	    	
		}   
		
		jPanelFilter.updateButtonFilter();
	}
	
	
	/**
	 * Initialise les objets
	 */
	@SuppressWarnings({ "static-access", "rawtypes", "unchecked" })
	public void initObject()
	{
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/////////////////////////// On cree la partie affichant la localisation de la recherhe ////////////////////////// 
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		jButtonDistrict = IHMTools.getInstance().getNewButtonWithIcon(ConfigIcon.getInstance().DISTRICT);
		jButtonRadius  = IHMTools.getInstance().getNewButtonWithIcon(ConfigIcon.getInstance().RADIUS);
		
		comboBoxItemsDistrict = new Vector<String>();
		modelDistrict = new DefaultComboBoxModel(comboBoxItemsDistrict);
		jComboDistrict = new JComboBox(modelDistrict);
		jComboDistrict.addActionListener(this);		
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		////////////////////////////// On cree la partie affichant l'objet de la recherhe /////////////////////////////// 
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		jButtonTools = IHMTools.getInstance().getNewButtonWithIcon(ConfigIcon.getInstance().TOOLS);
				
		comboBoxItemsTools = new Vector<String>();
		modelTools = new DefaultComboBoxModel(comboBoxItemsTools);
		jComboTools = new JComboBox(modelTools);
		jComboTools.addActionListener(this);
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////// On cree le bouton permettant de configurer la recherhe //////////////////////////// 
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		jButtonSearch = IHMTools.getInstance().getNewButtonWithBorder_Icon_ActionListener(ConfigIcon.getInstance().SEARCH,this);
		
		// On cree le spinner permettant de selectionner la valeur du rayon autour de la province selectionnee
		jSpinnerNbDistrictAround = new JSpinner(new SpinnerNumberModel(0,0,20,1));
		jSpinnerNbDistrictAround.addChangeListener(this);
		
		jLabelSearch = new JLabel("Lancer la recherche...");

	}
	
	
	/**
	 * Initialise la liste des provinces au sein de la combobox
	 * @param listDistrict
	 */
	public void initValueDistrict(List<String> listDistrict)
	{
		// On supprime tous les items de la combobox contenant les provinces
		comboBoxItemsDistrict.removeAllElements();
		
		// On parcourt chaque nom de la liste passe en argument
		for (String s_nameDistrict : listDistrict){
			// On ajoute l'item a la combobox
			comboBoxItemsDistrict.add(s_nameDistrict);
		}
		
		jComboDistrict.setSelectedIndex(-1);
					
	}

	/**
	 * Initialise les valeurs du materiel
	 * @param listMaterial
	 */
	public void initValueMaterial(List<String> listMaterial)
	{
		// On supprime tous les items de la combobox contenant les provinces
		comboBoxItemsTools.removeAllElements();
		
		// On parcourt chaque nom de la liste passe en argument
		for (String s_nameTools : listMaterial){
			// On ajoute l'item a la combobox
			comboBoxItemsTools.add(s_nameTools);
		}

		jComboTools.setSelectedIndex(-1);
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent)
	{
		Object source = actionEvent.getSource();
		
		// Si il s'agit d'une action sur le bouton de recherche
		if(source == jButtonSearch || source == jComboDistrict || source == jComboTools)
		{
			launchResearch();			
		}

	}
	
	/**
	  * Renvoie la liste d'interface visualisable
	 */
	public List<I_Viewable> getListI_Viewable()
	{
		return listI_Viewable;
	}
	
	/**
	  * Change la liste d'interface visualisable
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
		jLabelSearch.setText("Lancer la recherche...");
		
		createFilterPanel();
		
		// Initialise les valeurs de la liste de province
		initValueDistrict(ctrl.getListDistrictSortedByName());
		
		// Initialise les valeurs de la liste de materiel
		initValueMaterial(ctrl.getListMaterialSortedByName());
	}
	
	/**
	 * Change le controlleur
	 */
	public void setController(Controller ctrl)
	{
		this.ctrl = ctrl;
	}

	
	/**
	 * Renvoie le controlleur
	 */
	public Controller getController()
	{
	   return ctrl;
	}
	
	public void launchResearch()
	{
		// Si la combo contenant la province n'est pas selectionnee
		if(jComboDistrict.getItemAt(jComboDistrict.getSelectedIndex()) == null)
		{
			//JOptionPane.showMessageDialog(ctrl.getView(),"Choisissez une province...","Erreur",JOptionPane.ERROR_MESSAGE);
		}
		else if(jComboTools.getItemAt(jComboTools.getSelectedIndex()) == null)
		{
			//JOptionPane.showMessageDialog(ctrl.getView(),"Choisissez un objet...","Erreur",JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			// On cherche le materiel qui correspond aux criteres de recherche
			jLabelSearch.setText(ctrl.searchMaterial(	(String)jComboTools.getItemAt(jComboTools.getSelectedIndex()),
														(String)jComboDistrict.getItemAt(jComboDistrict.getSelectedIndex()),
														(Integer)jSpinnerNbDistrictAround.getValue()));
		}
	}

	@Override
	public void stateChanged(ChangeEvent changeEvent) {
		if(changeEvent.getSource() == jSpinnerNbDistrictAround)
		{
			launchResearch();
		}
	}

}
