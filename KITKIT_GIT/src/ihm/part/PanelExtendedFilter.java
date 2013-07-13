package ihm.part;

import ihm.ConfigIcon;
import ihm.IHMTools;
import ihm.filter.IHMFilterBoolean;
import ihm.filter.IHMFilterCase;
import ihm.filter.IHMFilterInteger;
import ihm.filter.IHMFilterListStringDefined;
import ihm.filter.IHMFilterStringUndefined;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import controller.Controller;
import controller.I_ControllerDialog;

import model.filter.Filter;
import model.tab.TabModel;

@SuppressWarnings("serial")
public class PanelExtendedFilter extends JPanel implements 	ActionListener,
															MouseListener,
															I_ControllerDialog
{
	/**
	 * Controlleur
	 */
	private Controller ctrl;
	
	/**
	 * Interface de vue
	 */
	private I_TableViewer iTableViewer;
	
	/**
	 * Booleen indiquant si le panel a deja ete initialise
	 */
	private boolean hasDoneInit;
	
	/**
	 * Booleen indiquant si le panel est developpe ou non
	 */
	private boolean isExtended;
	
	/**
	 * Liste de composant contenu dans le panel
	 */
	private JComponent[] listComponent;
	
	/**
	 * Nom du panel
	 */
	private String s_name;
	
	/**
	 * Composant situe avant le nom du panel
	 */
	private JComponent jComponentBegin;
	
	/**
	 * Bouton permettant de developper ou non le panel
	 */
	private JButton jButtonExtendPanel;
	
	/**
	 * Separateur
	 */
	private JSeparator jSeparator;

	/**
	 * Modele du tableau
	 */
	private TabModel tabModel;


	/**
	 * Constructeur
	 */
	@SuppressWarnings("static-access")
	public PanelExtendedFilter()
	{
		setBorder( BorderFactory.createRaisedBevelBorder());
		setLayout(new BorderLayout());
		
		// Initialisation des valeurs
		isExtended = false;
		hasDoneInit = false;
		jButtonExtendPanel =  IHMTools.getInstance().getNewButtonWithIcon_ActionListener(ConfigIcon.getInstance().TOP,this);
	}
	
	/**
	 * Cree un panel etendu
	 */
	public void createPanelExtended()
	{
		// Si le panel n'a pas encore ete initialise
		if(!hasDoneInit)
		{
			// Boite temporaire
			Box bH = Box.createHorizontalBox();
			Box bV = Box.createVerticalBox();
				
			// On cree la partie superieure
			bH.add(jComponentBegin);
			((JButton)jComponentBegin).addActionListener(this);
			bH.add(new JLabel(s_name));
			bH.add(Box.createHorizontalGlue());
			bH.add(jButtonExtendPanel);
			bV.add(bH);
			
			// On cree le separateur
			jSeparator = IHMTools.getInstance().addHorizontalSeparator(bV, 0, 0);
			bV.add(jSeparator);
			
			// On ajoute tous les composants
			for(JComponent componentTmp : listComponent)
			{
				Box bHTmp = Box.createHorizontalBox();
				bHTmp.add(componentTmp);
				bHTmp.add(Box.createHorizontalGlue());
				bV.add(bHTmp);
				((JButton)componentTmp).addActionListener(this);
				((JButton)componentTmp).addMouseListener(this);
			}
			
			// Ajoute la boite verticale dans le panel
			this.add(bV, BorderLayout.CENTER);
			
			// On affiche le panel
			displayExtendedPanel();
			
			// On indique que le panel est initialise
			hasDoneInit = true;
		}

	}
	
	/**
	 * Affiche un panel etendu ou non
	 */
	@SuppressWarnings("static-access")
	private void displayExtendedPanel()
	{
		// Si le panel est ouvert
		if(isExtended)
		{
			// On affiche la fleche vers le haut
			jButtonExtendPanel.setIcon(ConfigIcon.getInstance().TOP);
		}
		// Sinon
		else
		{
			// On affiche la fleche vers le bas
			jButtonExtendPanel.setIcon(ConfigIcon.getInstance().BOTTOM);
		}
		
		// On affiche ou non le separateur
		jSeparator.setVisible(isExtended);
		
		// On affiche ou non tous les composants
		for(JComponent componentTmp : listComponent)
		{
			componentTmp.setVisible(isExtended);
		}
	}
	
	/**
	 * Renvoie le booleen indiquant qu'on a deja initialise le panel
	 * @return
	 */
	public boolean hasDoneInit() {
		return hasDoneInit;
	}

	/**
	 * Change le booleen indiquant qu'on a deja initialise le panel
	 * @param hasDoneInit
	 */
	public void setHasDoneInit(boolean hasDoneInit) {
		this.hasDoneInit = hasDoneInit;
	}
	
	/**
	 * Renvoie le nom du panel qui s'etend
	 * @return
	 */
	public String getS_name() {
		return s_name;
	}

	/**
	 * Change le nom du panel qui s'etend
	 * @param s_name
	 */
	public void setS_name(String s_name) {
		this.s_name = s_name;
	}
	
	/**
	 * Renvoie le composant positionne en haut a gauche du panel
	 * @return
	 */
	public JComponent getJComponentBegin() {
		return jComponentBegin;
	}

	/**
	 * Change le composant positionne en haut a gauche du panel
	 * @param jComponentBegin
	 */
	public void setJComponentBegin(JComponent jComponentBegin) {
		this.jComponentBegin = jComponentBegin;
	}

	/**
	 * Renvoie la liste de composant
	 * @return
	 */
	public JComponent[] getListComponent() {
		return listComponent;
	}

	/**
	 * Change la liste de composant
	 * @param listComponent
	 */
	public void setListComponent(JComponent[] listComponent) {
		this.listComponent = listComponent;
	}

	/**
	 * Renvoie le booleen indiquant si le panel est etendu
	 * @return
	 */
	public boolean isExtended() {
		return isExtended;
	}

	/**
	 * Change le booleen indiquant si le panel est etendu
	 * et redessine le panel en meme temps
	 * @param isExtended
	 */
	public void setExtended(boolean isExtended) {
		this.isExtended = isExtended;

		// On reaffiche le panel
		displayExtendedPanel();
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		
		Object source = actionEvent.getSource();
		
		if(source == jButtonExtendPanel)
		{
			isExtended = !isExtended;
			
			// On reaffiche le panel
			displayExtendedPanel();
		}
		else if(source == jComponentBegin)
		{
			if(((JButton)jComponentBegin).getIcon() == ConfigIcon.ALERT)
			{
				// On parcourt tous les filtres (boutons)
				for(Filter filterTmp : tabModel.getListFilter())
				{
					filterTmp.setActive(false);
				}
				
				// On met a jour les boutons de filtres
				updateButtonFilter();
			}
		}
		else
		{
			// On parcourt tous les filtres (boutons)
			for(int i_idFilter = 0 ; i_idFilter < tabModel.getListFilter().length ; i_idFilter++)
			{
				// Si la source est le bouton actuel
				if(source == listComponent[i_idFilter])
				{									
					// On inverse l'activite du filtre
					tabModel.getListFilter()[i_idFilter].setActive(!tabModel.getListFilter()[i_idFilter].isActive());
					
					// On met a jour les boutons de filtres
					updateButtonFilter();
					
					break;
				}
			}
		}

	}

	@Override
	public void mouseClicked(MouseEvent mouseEvent) {
		
		Object source = mouseEvent.getSource();
		
		// On parcourt tous les filtres (boutons)
		for(int i_idFilter = 0 ; i_idFilter < tabModel.getListFilter().length ; i_idFilter++)
		{
			// Si la source est le bouton actuel
			if(source == listComponent[i_idFilter])
			{				
				// Si il s'agit d'un clic droit
				if(mouseEvent.getModifiers() == InputEvent.BUTTON3_MASK)
				{
					// JDialog a afficher
					JDialog dialogToShow = null;
					
					/**
					 * Type des filtres
					 * 1 : Filtre de liste de string definie
					 * 2 : Filtre de string indefinie
					 * 10 : Filtre d'entier
					 * 11 : Filtre de cases
					 * 12 : Filtre de booleen
					 */
					switch(tabModel.getListFilter()[i_idFilter].getI_type())
					{
						case 1 :
							System.out.println("Filtre de liste de string definie");
							dialogToShow = new IHMFilterListStringDefined(tabModel.getListFilter()[i_idFilter], ctrl.getView());
							dialogToShow.setSize(500,600);
							break;
						case 2 :
							System.out.println("Filtre de string indefinie");
							dialogToShow = new IHMFilterStringUndefined(tabModel.getListFilter()[i_idFilter], ctrl.getView());
							dialogToShow.setSize(500,150);
							break;
						case 10 :
							System.out.println("Filtre d'entier");
							dialogToShow = new IHMFilterInteger(tabModel.getListFilter()[i_idFilter], ctrl.getView());
							dialogToShow.setSize(500,150);
							break;
						case 11 :
							System.out.println("Filtre de cases");
							dialogToShow = new IHMFilterCase(tabModel.getListFilter()[i_idFilter], ctrl.getView());
							dialogToShow.setSize(500,150);
							break;
						case 12 :
							System.out.println("Filtre de booleen");
							dialogToShow = new IHMFilterBoolean(tabModel.getListFilter()[i_idFilter], ctrl.getView());
							dialogToShow.setSize(500,150);
							break;
						default :
							System.out.println("Filtre inconnu");
							break;
					}
					
					if(dialogToShow != null)
					{
						dialogToShow.setLocation(IHMTools.getInstance().getLocation(dialogToShow.getWidth(), dialogToShow.getHeight()));
						dialogToShow.setVisible(true);
					}

					// On met a jour les boutons de filtres
					updateButtonFilter();
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent mouseEvent) {
		// Seul le clic droit est utile
	}

	@Override
	public void mouseExited(MouseEvent mouseEvent) {
		// Seul le clic droit est utile
	}

	@Override
	public void mousePressed(MouseEvent mouseEvent) {
		// Seul le clic droit est utile
	}

	@Override
	public void mouseReleased(MouseEvent mouseEvent) {
		// Seul le clic droit est utile
	}

	
	/**
	 * Met a jour les boutons de filtres
	 */
	@SuppressWarnings("static-access")
	public void updateButtonFilter() {
		
		boolean hasOneActiveFilter = false;
		
		// On parcourt tous les filtres
		for(int i_idFilter = 0 ; i_idFilter < listComponent.length ; i_idFilter++)
		{
			if(tabModel.getListFilter()[i_idFilter].isActive())
			{									
				hasOneActiveFilter = true;
				((JButton)listComponent[i_idFilter]).setIcon(ConfigIcon.getInstance().ORANGE_CIRCLE);
			}
			else
			{
				((JButton)listComponent[i_idFilter]).setIcon(ConfigIcon.getInstance().GRAY_CIRCLE);
			}
		}
		
		// Si au moins un filtre est actif
		if(hasOneActiveFilter)
		{
			((JButton)jComponentBegin).setIcon(ConfigIcon.getInstance().ALERT);
		}
		// Sinon
		else
		{
			((JButton)jComponentBegin).setIcon(ConfigIcon.getInstance().EMPTY_16);
		}
		
		// On lance la recherche
		iTableViewer.launchResearch();
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
	public I_TableViewer getiTableViewer() {
		return iTableViewer;
	}

	public void setiTableViewer(I_TableViewer iTableViewer) {
		this.iTableViewer = iTableViewer;
	}
	
	public TabModel getTabModel() {
		return tabModel;
	}

	public void setTabModel(TabModel tabModel) {
		this.tabModel = tabModel;
	}
}
