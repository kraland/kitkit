package ihm.part.pagesjaukes;

import ihm.ConfigIcon;
import ihm.IHMTools;
import ihm.I_Viewable;
import ihm.part.I_TableViewer;
import ihm.part.PanelExtendedFilter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

import model.tab.TabModel;


import controller.Controller;
import controller.I_ControllerDialog;

@SuppressWarnings("serial")
public class PanelPagesJaukesConfig extends JPanel  implements 	ActionListener,
																KeyListener,
																I_Viewable,
																I_ControllerDialog,
																I_TableViewer
{
	
	//TODO Ameliorer le temps de recherche...
	// Revoir l'affichage pour passer en combobox ?
	// Autocomplete a revoir.
	
	private static String S_AUTOCOMPLETE_IMPOSSIBLE = "(minimum 2 caractères...)";
	private static String S_AUTOCOMPLETE_EMPTY = "(aucune donnée ne correspond...)";
	
	/**
	 * Table contenant toutes les vues
	 */
	private List<I_Viewable> listI_Viewable;
	
	/**
	 * Controlleur
	 */
	private Controller ctrl;
	
	/**
	 * Bouton permettant de choisir le type de recherche	
	 */
	private JButton jButtonResearchType;
	
	/**
	 * Label indiquant le type de recherche	
	 */
	private JLabel jLabelResearchType;
	
	/**
	 * Text field affichant la recherche
	 */
	private JTextField jTextFieldName;
	
	/**
	 * Fenetre d'auto-completation
	 */
	private JWindow jWindowAutoComplete;
	
	/**
	 * Model d'auto-completation
	 */
	@SuppressWarnings("rawtypes")
	private DefaultListModel listModelAutoComplete; 
	
	/**
	 * Liste d'auto-completation
	 */
	@SuppressWarnings("rawtypes")
	private JList jListAutoComplete;
	
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
	
	public PanelPagesJaukesConfig()
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
    	
    	// On ajoute la partie permettant d'afficher le cout en PdV d'un trajet
    	bH = Box.createHorizontalBox();
    	bH.add(jButtonResearchType);
    	bH.add(Box.createHorizontalStrut(5));
    	bH.add(jLabelResearchType);
    	bH.add(Box.createHorizontalGlue());
    	bVTmp.add(bH);
    	
    	IHMTools.getInstance().addHorizontalSeparator(bVTmp,5,5);
    	
    	// On ajoute la partie permettant d'afficher la position de depart
    	bH = Box.createHorizontalBox();
    	bH.add(new JLabel("Nom :"));
       	bH.add(Box.createHorizontalGlue());
       	bVTmp.add(bH);
       	
       	bVTmp.add(jTextFieldName);
    	
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
	
	@SuppressWarnings({ "static-access", "rawtypes", "unchecked" })
	public void initObject()
	{
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////// On cree le label affichant le type de la recherhe ////////////////////////////// 
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		jButtonResearchType = IHMTools.getInstance().getNewButtonWithBorder_Icon_ActionListener(ConfigIcon.getInstance().PERSON,this);

		jLabelResearchType = new JLabel("Particulier");

		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////// On cree la partie permettant l'auto-completation ////////////////////////////// 
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		jTextFieldName = new JTextField("Tapez un nom...");
		jTextFieldName.addKeyListener(this);
		
		listModelAutoComplete = new DefaultListModel();
		jListAutoComplete = new JList(listModelAutoComplete);
		jListAutoComplete.setBorder(BorderFactory.createEtchedBorder());
		jWindowAutoComplete = new JWindow();
		jWindowAutoComplete.add(new JScrollPane(jListAutoComplete));
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////// On cree le bouton permettant de configurer la recherhe //////////////////////////// 
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		jButtonSearch = IHMTools.getInstance().getNewButtonWithBorder_Icon_ActionListener(ConfigIcon.getInstance().SEARCH,this);
		
		jLabelSearch = new JLabel("Lancer la recherche...");
	}

	@SuppressWarnings("static-access")
	public void createFilterPanel()
	{
		// Si le panel de filtre n'a pas deja ete initialise
		if(!jPanelFilter.hasDoneInit())
		{
			TabModel tabModel = ctrl.getModel().getTabModelBuilding();
			
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
	
	
	@SuppressWarnings("static-access")
	@Override
	public void actionPerformed(ActionEvent actionEvent)
	{
		Object source = actionEvent.getSource(); 
		
		if(source == jButtonResearchType)
		{		
			jWindowAutoComplete.setVisible(false);
			if(jButtonResearchType.getIcon() == ConfigIcon.getInstance().PERSON)
			{
				jLabelResearchType.setText("Orga. Comm.");
				jButtonResearchType.setIcon(ConfigIcon.getInstance().GROUP);
				jTextFieldName.setText("Tapez un nom...");
			}
			else
			{
				jLabelResearchType.setText("Particulier");
				jButtonResearchType.setIcon(ConfigIcon.getInstance().PERSON);
				jTextFieldName.setText("Tapez un nom...");
			}
		}
		
		// Si il s'agit d'une action sur le bouton de recherche
		else if(actionEvent.getSource() == jButtonSearch)
		{
			launchResearch();			
		}
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
		jLabelSearch.setText("Lancer la recherche...");
		
		createFilterPanel();
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
	

	@Override
	public void keyTyped(KeyEvent e) {}
	
	@Override
	public void keyReleased(KeyEvent e)
	{	
		if(jTextFieldName.getText()==null || jTextFieldName.getText().length()==0)
		{
			jWindowAutoComplete.setVisible(false);
		}
		if(e.getKeyCode()==KeyEvent.VK_DOWN)
		{
			if(jListAutoComplete.getSelectedIndex() < jListAutoComplete.getModel().getSize())
			{
				jListAutoComplete.setSelectedIndex(jListAutoComplete.getSelectedIndex()+1);
			}
			else
			{
				jListAutoComplete.setSelectedIndex(0);
			}
		}
		else if(e.getKeyCode()==KeyEvent.VK_UP)
		{
			if(jListAutoComplete.getSelectedIndex() != jListAutoComplete.getModel().getSize())
			{
				jListAutoComplete.setSelectedIndex(jListAutoComplete.getSelectedIndex()-1);
			}
			else
			{
				jListAutoComplete.setSelectedIndex(jListAutoComplete.getModel().getSize());
			}
		}
		else if(e.getKeyCode()==KeyEvent.VK_ENTER)
		{
			jTextFieldName.setText(ctrl.convertToText(jListAutoComplete.getSelectedValue().toString()));
			jWindowAutoComplete.setVisible(false);
			launchResearch();
		}
		else
		{
			autoComplete();
		}
		
		jListAutoComplete.ensureIndexIsVisible(jListAutoComplete.getSelectedIndex());
	}
	
	@Override
	public void keyPressed(KeyEvent e) {}

	@SuppressWarnings({ "static-access", "unchecked" })
	public void autoComplete(){
		
		// Booleen indiquant si c'est une personne
		boolean isPerson;
		
		// Si on recherche une personne...
		if(jButtonResearchType.getIcon() == ConfigIcon.getInstance().PERSON)
		{
			isPerson = true;
		}
		// Sinon on recherche une organisation
		else
		{
			isPerson = false;
		}
		
		// On supprime le contenu du modele
		listModelAutoComplete.clear();
				
		// Si la taille du contenu du texte field est superieur a 2
		if(jTextFieldName.getText().length()>=2)
		{
			// On recupere la liste de nom de personne ou d'organisations correspondantes
			List<String> correspondants = ctrl.getPersonCorrespondate(jTextFieldName.getText(), isPerson);
			
			// Si la liste est nulle ou vide
			if(null == correspondants || correspondants.isEmpty())
			{
				listModelAutoComplete.addElement(S_AUTOCOMPLETE_EMPTY);
			}
			// Sinon au moins un personnage ou une organisation correspond au critere...
			else
			{
				for(String s : correspondants)
				{
					listModelAutoComplete.addElement(s);
				}
			}
		}
		else
		{
			listModelAutoComplete.addElement(S_AUTOCOMPLETE_IMPOSSIBLE);
		}

		
		Point pointTmp = new Point(jTextFieldName.getX(),jTextFieldName.getY());
		SwingUtilities.convertPointToScreen(pointTmp, jTextFieldName);
		
		jWindowAutoComplete.setBounds((int)pointTmp.getX() - 2, (int)pointTmp.getY() - 45, getWidth() - 10, 10*jTextFieldName.getHeight());
		jWindowAutoComplete.setVisible(true);
		
		jListAutoComplete.setSelectedIndex(0);
	}

	
	@SuppressWarnings("static-access")
	public void launchResearch()
	{
		boolean isPerson;
			
		if(jButtonResearchType.getIcon() == ConfigIcon.getInstance().PERSON)
		{
			isPerson = true;
		}
		else
		{
			isPerson = false;
		}
			
		jLabelSearch.setText(ctrl.searchBuilding(jTextFieldName.getText(),isPerson));
	}


}
