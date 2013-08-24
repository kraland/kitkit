package ihm.part;

import ihm.ConfigIcon;
import ihm.IHMTools;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import controller.Controller;
import controller.I_ControllerDialog;

import model.filter.Filter;
import model.tab.TabModel;

@SuppressWarnings("serial")
public class PanelExtendedColumn extends JPanel implements 	ActionListener,
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
	public PanelExtendedColumn()
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
				((JCheckBox)componentTmp).addActionListener(this);
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
		
		// On met a jour les checkbox
		updateCheckBoxColumn();
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
		
			if(((JButton)jComponentBegin).getIcon() == ConfigIcon.COLUMN_NOT_ALL_SELECTED)
			{
				// On parcourt tous les filtres (boutons)
				for(int i_idFilter = 0 ; i_idFilter < tabModel.getListFilter().length ; i_idFilter++)
				{
					// Si on demande a ce que la colonne soit visible alors on l'affiche
					tabModel.getColumnHider().show(tabModel.getListFilter()[i_idFilter].getS_name());
				}
				
				// On parcourt tous les filtres (boutons)
				for(Filter filterTmp : tabModel.getListFilter())
				{
					filterTmp.setColumnVisible(true);
				}
				
				// On met a jour les boutons de filtres
				updateButtonColumn();
				
				// On met a jour les checkbox
				updateCheckBoxColumn();
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
					tabModel.getListFilter()[i_idFilter].setColumnVisible(!tabModel.getListFilter()[i_idFilter].isColumnVisible());
					
					// On met a jour les boutons de filtres
					updateButtonColumn();
					
					// Si on demande a ce que la colonne soit visible alors on l'affiche
					if(tabModel.getListFilter()[i_idFilter].isColumnVisible())
					{
						tabModel.getColumnHider().show(tabModel.getListFilter()[i_idFilter].getS_name());
					}
					// Sinon on la cache
					else
					{
						tabModel.getColumnHider().hide(tabModel.getListFilter()[i_idFilter].getS_name());
					}
					
					break;
				}
			}
		}

	}
	
	private void updateCheckBoxColumn() {
		
		// On parcourt tous les filtres
		for(int i_idFilter = 0 ; i_idFilter < listComponent.length ; i_idFilter++)
		{
			((JCheckBox)listComponent[i_idFilter]).setSelected(tabModel.getListFilter()[i_idFilter].isColumnVisible());
		}		
	}
	
	/**
	 * Met a jour les boutons de filtres
	 */
	@SuppressWarnings("static-access")
	public void updateButtonColumn() {
		
		boolean hasOneHideColumn = false;
		
		// On parcourt tous les filtres
		for(int i_idFilter = 0 ; i_idFilter < listComponent.length ; i_idFilter++)
		{
			if(!tabModel.getListFilter()[i_idFilter].isColumnVisible())
			{									
				hasOneHideColumn = true;
				break;
			}
		}
		
		// Si au moins un filtre est actif
		if(hasOneHideColumn)
		{
			((JButton)jComponentBegin).setIcon(ConfigIcon.getInstance().COLUMN_NOT_ALL_SELECTED);
		}
		// Sinon
		else
		{
			((JButton)jComponentBegin).setIcon(ConfigIcon.getInstance().COLUMN_ALL_SELECTED);
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
