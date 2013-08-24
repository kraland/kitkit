package ihm.part.cenkury21;

import ihm.ConfigIcon;
import ihm.IHMTools;
import ihm.I_Viewable;
import ihm.part.I_TableViewer;
import ihm.part.PanelExtendedColumn;
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
import javax.swing.JCheckBox;
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
public class PanelCenkury21Config extends JPanel  implements	ActionListener,
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
	 * Panel contenant la liste des colonnes
	 */
	private PanelExtendedColumn jPanelColumn;
		
	/**
	 * Label affichant ou non une alerte si au moins un filtre est actif
	 */
	private JButton jButtonColumnIndicator;
	
	/**
	 * Liste de checkbox de colonnes
	 */
	private JCheckBox[] list_jCheckBoxColumn;
	
	/**
	 * Booleen indiquant si l'initialisation est en cours
	 */
	private boolean isInitInProgress = false;
	
	/**
	 * Constructeur de panel de configuration
	 */
	public PanelCenkury21Config()
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
    	bV.add(Box.createVerticalStrut(10)); 
    	
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////// On cree la partie Colonnes ///////////////////////////////////////// 
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////  
    	
    	jPanelColumn = new PanelExtendedColumn();
    	bV.add(jPanelColumn);
    	jPanelColumn.setMaximumSize(new Dimension(3000,80));
    	
    	bV.add(Box.createVerticalGlue());
    	
    	add(bV,BorderLayout.CENTER);
	}

	@SuppressWarnings("static-access")
	public void createFilterPanel()
	{
		// Si le panel de filtre n'a pas deja ete initialise
		if(!jPanelFilter.hasDoneInit())
		{
			TabModel tabModel = ctrl.getModel().getTabModelBuildingToBuy();
			
			// On transmet son nom au panel affichant les filtres
			jPanelFilter.setS_name("Filtres :");
			
			// On cree le bouton indiquant si un filtre est actif
			jButtonFilterIndicator = IHMTools.getInstance().getNewButtonWithIcon_ActionListener(ConfigIcon.getInstance().EMPTY_16,this);
			
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
	
	@SuppressWarnings("static-access")
	public void createColumnPanel()
	{
		// Si le panel de colonne n'a pas deja ete initialise
		if(!jPanelColumn.hasDoneInit())
		{
			TabModel tabModel = ctrl.getModel().getTabModelBuildingToBuy();
			
			// On transmet son nom au panel affichant les filtres
			jPanelColumn.setS_name("Colonnes :");
			
			// On cree le bouton indiquant si un filtre est actif
			jButtonColumnIndicator = IHMTools.getInstance().getNewButtonWithIcon_ActionListener(ConfigIcon.getInstance().COLUMN_ALL_SELECTED,this);
			
			// On transmet le bouton indiquant si un filtre est actif au panel affichant les filtres
			jPanelColumn.setJComponentBegin(jButtonColumnIndicator);
					
			// On cree le tableau de bouton
			list_jCheckBoxColumn = new JCheckBox[tabModel.getListTitleColumn().length];
	    	
		 	// On cree chaque bouton
	    	for(int i_idFilter = 0 ; i_idFilter < tabModel.getListTitleColumn().length ; i_idFilter++)
	    	{
	    		list_jCheckBoxColumn[i_idFilter] = new JCheckBox(tabModel.getListTitleColumn()[i_idFilter]);
	    	}
	    	
			// On transmet la liste de bouton de filtres au panel affichant les filtres
	    	jPanelColumn.setListComponent(list_jCheckBoxColumn);
	    	
	    	jPanelColumn.setiTableViewer(this);
	    	jPanelColumn.setController(ctrl);
	    	jPanelColumn.setTabModel(tabModel);
			
			// On cree le panel
	    	jPanelColumn.createPanelExtended();
	    	
		}
		
		jPanelColumn.updateButtonColumn();
	}
	
	/**
	 * Initialise les objets
	 */
	@SuppressWarnings({ "static-access", "unchecked", "rawtypes" })
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
	 * @param listProvince
	 */
	public void initValueDistrict(List<String> listProvince)
	{
		// On supprime tous les items de la combobox contenant les provinces
		comboBoxItemsDistrict.removeAllElements();
		
		// On parcourt chaque nom de la liste passe en argument
		for (String s_nameProvince : listProvince){
			// On ajoute l'item a la combobox
			comboBoxItemsDistrict.add(s_nameProvince);
		}
		
		jComboDistrict.setSelectedIndex(-1);		
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent)
	{
		Object source = actionEvent.getSource(); 
		
		// Si il s'agit d'une action sur le bouton de recherche
		if(source == jButtonSearch || source == jComboDistrict)
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
		isInitInProgress = true;
		jLabelSearch.setText("Lancer la recherche...");
		jSpinnerNbDistrictAround.setValue(0);
		
		createFilterPanel();
		createColumnPanel();
		
		// Initialise les valeurs de la liste de province
		initValueDistrict(ctrl.getListDistrictSortedByName());
				
		isInitInProgress = false;
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
		if(!isInitInProgress)
		{
			// Si la combo contenant la province n'est pas selectionnee
			if(jComboDistrict.getItemAt(jComboDistrict.getSelectedIndex()) == null)
			{
				//JOptionPane.showMessageDialog(ctrl.getView(),"Choisissez une province...","Erreur",JOptionPane.ERROR_MESSAGE);
			}
			else
			{		
				// On cherche les batiments qui correspondent aux criteres de recherche
				jLabelSearch.setText(ctrl.searchBuilding((String)jComboDistrict.getItemAt(jComboDistrict.getSelectedIndex()),(Integer)jSpinnerNbDistrictAround.getValue()));
			}
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
