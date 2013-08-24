package ihm;


import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;


import controller.Controller;
import controller.I_ControllerDialog;
import model.tab.HTMLLink;
import model.tab.TabModel;


@SuppressWarnings("serial")
public class TabPanel  extends JPanel implements I_ControllerDialog{
	
	/**
	 * Modele du tableau
	 */
	private TabModel tabModel;
	
	/**
	 * JTable
	 */
	private JTable tableau;
	
	/**
	 * Nom du tableau
	 */
	private String s_name;
	
	/**
	 * Liste des titres des colonnes du tableau
	 */
	private List<String> listTitle;
	
	/**
	 * Controleur de tableau
	 */
	private Controller ctrl;
	
	/**
	 * Scrollpane du tableau
	 */
	private JScrollPane scrollPaneTab;
	
	/**
	 * Constructeur de panel de tab
	 * @param listTitle
	 * @param hasFilter
	 * @param hasToolBar
	 */	
	public TabPanel()
	{
		// On met un layout pour ce panel
		setLayout(new BorderLayout());
		
		// On initialise les objets
		initObject();
	}
	
	/**
	 * Fonction qui initialise tous les objets presents dans ce panel. C'est une
	 * fonction qui sera appele lors de l'appel du constructeur de cette classe
	 */
	public void initObject()
	{
		
		// On insere ce modele dans le tableau
		tableau = new JTable(tabModel){
			protected void paintComponent (Graphics g)
			{
				//MultiLineCellRenderer.evaluateRowHeight(this);
				super.paintComponent(g);
			}
		};
		
		tableau.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
			@Override
			public void mousePressed(MouseEvent arg0){}
			
			@Override
			public void mouseExited(MouseEvent arg0) {}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {

				// Si double click
				if (arg0.getClickCount() == 2)
				{
					// Si bouton gauche
					if (arg0.getButton() == MouseEvent.BUTTON1)
					{
						// On recupere l'objet sur lequel on a clicke
						Object objectClicked = tableau.getValueAt(tableau.rowAtPoint(arg0.getPoint()), tableau.columnAtPoint(arg0.getPoint()));
						
						// Si il s'agit d'un lien HTML on affiche la page
						if(objectClicked instanceof HTMLLink)
						{
						    if (Desktop.isDesktopSupported())
						    {
						        URI uri = URI.create(((HTMLLink)objectClicked).getS_link());
						        try
						        {
						            Desktop.getDesktop().browse(uri);
						        }
						        catch (IOException e)
						        {
						            e.printStackTrace();
						        }
						    }
						}
					}
			     }
			}
		});
		
		centerCells();
		
		// On bloque le deplacement des colonnes
		tableau.getTableHeader().setReorderingAllowed(false);


		// TODO A voir...
//		// On cree un renderer multi-ligne pour le header
//		// Afin d'avoir un retour a la ligne dans le header apres un "\n"
//		MultiLineHeaderRenderer renderer = new MultiLineHeaderRenderer();
//		Enumeration<TableColumn> e = tableau.getColumnModel().getColumns();
//		while(e.hasMoreElements())
//		{
//			((TableColumn)e.nextElement()).setHeaderRenderer(renderer);
//		}
		
		
		// On fixe la hauteur des lignes
		tableau.setRowHeight(20);
		
		// On bloque la selection a une seule ligne
		tableau.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		// Ajout de la scroll
		scrollPaneTab = new JScrollPane(tableau);
		
		// On incorpore le tableau dans un scrollpane qu'on place au centre
		add(scrollPaneTab,BorderLayout.CENTER);	
	
	}
	
	/**
	 * Centre les cellules
	 */
	private void centerCells()
	{
		// TODO A voir...
//		// On cree un renderer pour le tableau pour centrer le contenu des colonnes
//		DefaultTableCellRenderer rendererTable = new DefaultTableCellRenderer();
//		rendererTable.setHorizontalAlignment(SwingConstants.CENTER);
//		//tableau.setDefaultRenderer(Object.class, rendererTable);
//		
//		// On met ce Renderer dans chaque cellule
//		for(int i_idColumn = 0 ; i_idColumn < tableau.getModel().getColumnCount(); i_idColumn++)
//		{
//			tableau.getColumnModel().getColumn(i_idColumn).setCellRenderer(rendererTable);
//		}
	}
	
	/**
	 * Change le tabModel
	 * @param tabModel
	 */
	public void setTabModel(TabModel tabModel)
	{
		this.tabModel = tabModel;
		tableau.setModel(tabModel);

		// On regarde si le modele ne dispose pas deja d'un "ColumnHider"
		if(tabModel.getColumnHider() == null)
		{
			// On fournit le cacheur de colonne au modele
			tabModel.setColumnHider(new TableColumnHider(tableau));
		}
		
		centerCells();

	}
	
	/**
	 * Renvoie le modele du tableau
	 * @return
	 */
	public TabModel getTabModel() {
		return tabModel;
	}
	
	/**
	 * Change le tableau
	 * @param tableau
	 */
	public void setTableau(JTable tableau) {
		this.tableau = tableau;
	}
	
	/**
	 * Renvoie la JTable
	 * @return
	 */
	public JTable getTableau() {
		return tableau;
	}

	/**
	 * Change le nom du tableau
	 * @param s_name
	 */
	public void setS_name(String s_name) {
		this.s_name = s_name;
	}

	/**
	 * Renvoie le nom du tableau
	 * @return
	 */
	public String getS_name() {
		return s_name;
	}

	/**
	 * Change la liste de nom des colonnes
	 * @param listTitle
	 */
	public void setListTitle(List<String> listTitle) {
		this.listTitle = listTitle;
	}

	/**
	 * Renvoie la liste de nom des colonnes
	 * @return
	 */
	public List<String> getListTitle() {
		return listTitle;
	}

	/**
	 * Ajoute un listener de selection de liste au tableau
	 * @param listSelectionListener
	 */
	public void addListSelectionListener(ListSelectionListener listSelectionListener)
	{
		tableau.getSelectionModel().addListSelectionListener(listSelectionListener);
	}

	/**
	 * Change le controlleur du tableau
	 * @param ctrlTab
	 */
	public void setCtrlTab(Controller ctrl) {
		this.ctrl = ctrl;
		getTabModel().setController(this.ctrl);
		//ctrlTab.setTabPanel(this);
	}

	/**
	 * Renvoie le controlleur du tableau
	 * @return
	 */
	public Controller getCtrlTab() {
		return ctrl;
	}

	/**
	 * Renvoie la JScrollpane
	 * @return
	 */
	public JScrollPane getScrollPaneTab() {
		return scrollPaneTab;
	}

	/**
	 * Change la JScrollPane
	 * @param scrollPaneTab
	 */
	public void setScrollPaneTab(JScrollPane scrollPaneTab) {
		this.scrollPaneTab = scrollPaneTab;
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

}
