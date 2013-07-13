
package ihm;



import ihm.part.cenkury21.TabPanelCenkury21;
import ihm.part.gps.TabPanelGPS;
import ihm.part.krac.TabPanelKrac;
import ihm.part.pagesjaukes.TabPanelPagesJaukes;


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;


import controller.Controller;
import controller.I_ControllerDialog;
import controller.KitkitActionListener;

@SuppressWarnings("serial")
public class View extends JFrame implements I_ControllerDialog, ActionListener{

	private int[] tab_KeyEventToDetect = {KeyEvent.VK_F11,KeyEvent.VK_F5, KeyEvent.VK_F6};
	
	private boolean isFullScreen = false;
	
	/**
	 * Table contenant tous les vues de l'HM
	 */
	private List<I_Viewable> listI_Viewable;

	/**
	 * Table contenant tous les vues affichant un splitpane
	 */
	private List<I_SplitPaneView> listI_SplitPaneView;
	
	/**
	 * Liste des panels au sein du CardLayout
	 */
	private List<JPanel> listJPanelInCardLayout;

	/**
	 * CardLayout
	 */
	private CardLayout cardLayout;

	/**
	 * Panel centrale
	 */
	private JPanel panelCenter;
	
	/**
	 * Controleur
	 */
	private Controller ctrl;
	
	/**
	 * Onglet KRAC
	 */
	private TabPanelKrac panelKrac;
	
	/**
	 * Onglet Cenkury21
	 */
	private TabPanelCenkury21 panelCenkury21;
	
	/**
	 * Onglet Pages Jaukes
	 */
	private TabPanelPagesJaukes panelPagesJaukes;
	
	/**
	 * Onglet GPS
	 */
	private TabPanelGPS panelGPS;
	
	/**
	 * Panel de mise a jour du modele
	 */
	
	private PanelUpdateModel panelUpdateModel;
	
	/**
	 * Liste d'icone de chacun des panels
	 */
	@SuppressWarnings("static-access")
	private ImageIcon listIcon[] = {ConfigIcon.getInstance().TB_KRAC,ConfigIcon.getInstance().TB_CENKURY21, ConfigIcon.getInstance().TB_PAGESJAUKES, ConfigIcon.getInstance().TB_TAMTAM};
	
	/**
	 * Liste de Tooltip de chacun des panels
	 */
	private String listToolTipText[] = {"Krac, Tu click, tu craques","Cenkury21, Ingrédient idéal pour pimenter vos achats immobilier","Pages Jaukes, Quoi, qui ? Où ?", "TamTam, GPS"};
	
	/**
	 * Liste de commande associe a chaque panel
	 */
	private String listActionCommand[] = {"KRAC","CENKURY21","PAGESJAUKES", "TOKTOK"};
	
	/**
	 * Constructeur de vue
	 */
	@SuppressWarnings("static-access")
	public View()
	{
		
		// On change le titre
		setTitle("KIT KIT (Kraland Interactif ToolsKIT) - Version 1.0.4");
		
		// On change l'icone de l'application
		setIconImage(ConfigIcon.getInstance().LOGO_KITKIT_32.getImage());
		
		// On initialise la liste de vue de l'IHM
		listI_Viewable = new ArrayList<I_Viewable>();
		
		// On initialise la liste de vue affichant un splitpane
		listI_SplitPaneView = new ArrayList<I_SplitPaneView>();
		
		// On agrandit la fenetre a son maximum
		setExtendedState(MAXIMIZED_BOTH);
		
		// On cree le contenu de la fenetre
		this.createContent();
		
		// On ferme le programme si on appuie sur le bouton X de la fenetre
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Creation du contenu de l'IHM
	 */
	private void createContent()
	{
		// On recupere le containeur de la fenetre
		Container container = getContentPane();
		
		// On initialise le card layout
		cardLayout = new CardLayout();
				
		// On cree une nouvelle barre d'outils
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		
		// On ajoute les boutons de chaque panel a la barre d'outils
        addButtons(toolBar);
        
        // On ajoute la barre d'outils a la fenetre
		container.add(toolBar, BorderLayout.NORTH);
		
		// On initialise le panel du centre
		panelCenter = new JPanel();
		
		// On change le layout du panel centrale
		panelCenter.setLayout(cardLayout);
		
		// On ajoute le panel centrale au centre de la fenetre
		container.add(panelCenter,BorderLayout.CENTER);
		
		panelUpdateModel = new PanelUpdateModel();
		
		// On ajoute le panel centrale au centre de la fenetre
		container.add(panelUpdateModel,BorderLayout.SOUTH);
		
		// On initialise la liste de JPanel au sein du cardlayout
		listJPanelInCardLayout = new ArrayList<JPanel>();
		
		// On ajoute chaque panel au cardlayout
		addPanels(cardLayout);

	}

	/**
	 * Ajoute les boutons a la JToolbar placee en parametre
	 * @param toolBar
	 */
	private void addButtons(JToolBar toolBar)
	{
		// On cree un bouton temporaire
		JButton buttonTmp = null;
		
		// On verifie que les trois listes (tooltip, icone et commande) font la meme taille
		if(listIcon.length ==  listToolTipText.length && listIcon.length == listActionCommand.length)
		{
			// On parcourt chaque icone de la liste d'icones
			for(int i_idButton = 0; i_idButton < listIcon.length ; i_idButton++)
			{
				// On cree le bouton temporaire
				buttonTmp = makeButton(listIcon[i_idButton],listActionCommand[i_idButton],listToolTipText[i_idButton]);
				
				// On ajoute le bouton temoraire a la barre d'outils
				toolBar.add(buttonTmp);
			}
		}
		
	}
	
	/**
	 * Cree un bouton en lui associant son icone, sa commande associee et son tooltip a afficher
	 * @param icon
	 * @param actionCommand
	 * @param toolTipText
	 * @return
	 */
	protected JButton makeButton(ImageIcon icon,String actionCommand,String toolTipText)
	{
        //Cree et initialise le bouton
        JButton button = new JButton();
        
        // On change la commande associee au bouton
        button.setActionCommand(actionCommand);
        
        // On change le tooltip a afficher
        button.setToolTipText(toolTipText);
        
        // On change l'icone
        button.setIcon(icon);
        
        // On ajoute un action listener
        button.addActionListener(this);
        
        // On renvoie le bouton qu'on vient de creer
        return button;
    }
	
	/**
	 * Cree et initialise tous les panels au sein du CardLayout 
	 * @param cardLayout
	 */
	private void addPanels(CardLayout cardLayout)
	{
	
		// Onglet KRAC
		panelKrac = new TabPanelKrac();
		panelKrac.setListI_Viewable(listI_Viewable);
		listJPanelInCardLayout.add(panelKrac);
		
		// Ongler Cenkury21
		panelCenkury21 = new TabPanelCenkury21();
		panelCenkury21.setListI_Viewable(listI_Viewable);
		listJPanelInCardLayout.add(panelCenkury21);
		
		// Onglet Pages Jaukes
		panelPagesJaukes = new TabPanelPagesJaukes();
		panelPagesJaukes.setListI_Viewable(listI_Viewable);
		listJPanelInCardLayout.add(panelPagesJaukes);
		
		// Onglet GPS
		panelGPS = new TabPanelGPS();
		panelGPS.setListI_Viewable(listI_Viewable);
		listJPanelInCardLayout.add(panelGPS);
		
		// On parcourt chaque panel
		for(int i_idPanel = 0 ; i_idPanel < listActionCommand.length ; i_idPanel++)
		{
			// On ajoute le panel en lui associant sa commande au sein du cardlayout
			panelCenter.add(listJPanelInCardLayout.get(i_idPanel),listActionCommand[i_idPanel]);	
			
			
			// *******************************************************************************
			// Attention, si rajout de vue n'affichant pas de splitPane...
			listI_SplitPaneView.add((I_SplitPaneView) listJPanelInCardLayout.get(i_idPanel));
			// *******************************************************************************
			
			
			// On gere les raccourcis clavier propre a l'application
			for(int i_KeyEventToDetect : tab_KeyEventToDetect)
			{
				KitkitActionListener actionListenerTmp = new KitkitActionListener(i_KeyEventToDetect);
				actionListenerTmp.setView(this);
				
				listJPanelInCardLayout.get(i_idPanel).registerKeyboardAction(actionListenerTmp, KeyStroke.getKeyStroke(i_KeyEventToDetect,0), JComponent.WHEN_IN_FOCUSED_WINDOW);
			}		
		}
	}
		
	/**
	 * Initialise les valeurs de l'IHM
	 */
	public void initValueIHM()
	{
		// On parcourt toutes les IHM filles en leur demandant de s'initialiser
		for (I_Viewable interface_Viewable : listI_Viewable) { 
			interface_Viewable.initValueIHM();
		}
		
	}

	/**
	 * Change le controlleur
	 */
	public void setController(Controller ctrl) {
		this.ctrl = ctrl;
		
		// Change le controlleur au sein de chaque onglet
		panelKrac.setController(ctrl);
		panelCenkury21.setController(ctrl);
		panelPagesJaukes.setController(ctrl);
		panelGPS.setController(ctrl);
		panelUpdateModel.setController(ctrl);
		
	}

	/**
	 * Renvoie le controlleur
	 */
	public Controller getController() {
		return ctrl;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		// On recupere l'action commande
        String s_cmd = arg0.getActionCommand();

        // On parcourt chaque action commande du tableau
    	for(int i_idCmd = 0; i_idCmd < listActionCommand.length ; i_idCmd++)
    	{
    		// Si l'action commande correspond a celle du tableau
    		if(listActionCommand[i_idCmd].equals(s_cmd))
    		{
    			// On recupere le layout
    			CardLayout cl = (CardLayout)(panelCenter.getLayout());
           		
    			// On affiche l'onglet correspondant a cette action commande
    			cl.show(panelCenter, s_cmd);
           		
    			return;
    		}
    	}        	
	}
	
	public TabPanelGPS getPanelGPS()
	{
		return panelGPS;
	}

	/**
	 * Renvoie le panel de mise a jour du modele
	 * @return
	 */
	public PanelUpdateModel getPanelUpdateModel()
	{
		return panelUpdateModel;
	}
	
	/**
	 * Change le panel de mise a jour du modele
	 * @param panelUpdateModel
	 */
	public void setPanelUpdateModel(PanelUpdateModel panelUpdateModel)
	{
		this.panelUpdateModel = panelUpdateModel;
	}

	public boolean isFullScreen() {
		return isFullScreen;
	}

	public void setIsFullScreen(boolean isFullScreen) {
		if(isFullScreen)
		{
			// On agrandit la fenetre a son maximum
			setExtendedState(MAXIMIZED_BOTH);			
		}

		this.isFullScreen = isFullScreen;
	}
	
	public void setDividerLocationAllSplitPane(int i_dividerLocation)
	{
		for(I_SplitPaneView iSplitPaneView : listI_SplitPaneView)
		{
			iSplitPaneView.setDividerLocationSplitPane(i_dividerLocation);
		}
	}

}

