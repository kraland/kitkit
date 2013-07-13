package ihm.part.gps;




import ihm.I_Viewable;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import model.gps.I_ModelGPSListener;
import model.gps.Polygon;

import controller.Controller;
import controller.I_ControllerDialog;

@SuppressWarnings("serial")
public class PanelGPS2D extends JPanel implements	I_Viewable, 
													I_ModelGPSListener, 
													I_ControllerDialog,
													MouseListener,
													MouseMotionListener,
													MouseWheelListener,
													KeyListener
{
	/**
	 * Table contenant tous les classes de vues
	 */
	private List<I_Viewable> listI_Viewable;
	
	/**
	 * Controleur
	 */
	private Controller ctrl; 
	
	/**
	 * Map 2D
	 */
	private List<List<Integer>> map2D;
	
	/** 
	 * Nombre de case (hauteur)
	 */
	private int i_nbCaseHeight = 0;
		
	/**
	 * Nombre de case (largeur)
	 */
	private int i_nbCaseWidth = 0;
	
	/** 
	 * Graphique 2D
	 */
	private Graphics2D bufferGraphics;
	
	/** 
	 * Graphique 2D
	 */
	private Graphics2D g2;
	
	/**
	 * Image du background
	 */
	private Image imgOnBackground;
	
	
	/**
	 * Taille actuelle de l'image apres redimensionnement
	 */
	private int i_sizeSquare = 4;
	
	/**
	 * Booleen indiquant si la souris a bouge
	 */
	private boolean hasMooved = false;

	/**
	 * Popmenu lors de l'action clic droit
	 */
	private JPopupMenu	popupMenu;
	
	/**
	 * Menu Depart compris dans le JPopupMenu
	 */
	private JMenuItem	menuItemBegin;
	
	/**
	 * Menu Arrivee compris dans le JPopupMenu
	 */
	private JMenuItem	menuItemEnd;
	
	/**
	 * Menu Calcul compris dans le JPopupMenu
	 */
	private JMenuItem	menuItemCalcul;
	
	/**
	 * Booleen detectant si la touche "ctrl" est enfoncee
	 */
	private boolean hasCtrlPressed = false;
	 
	public PanelGPS2D()
	{
		// On initialise l'objet
		initObject();
		
		// On ajoute un listener detectant un deplacement de la souris
		addMouseMotionListener(this);
		
		// On ajoute un listenet detectant une action sur la roulette de la souris 
		addMouseWheelListener(this);
		
		// On ajoute un listener detectant un click de la souris 
		addMouseListener(this);
		
		// On ajoute un listener detectant une action clavier
		addKeyListener(this);
		
		// On se concentre sur le panel
		setFocusable(true);
	}
 	
	// Initialise les objets
 	public void initObject()
	{
		// Creation du menu et de ces sous-menus
 		popupMenu = new JPopupMenu();
 		menuItemBegin = new JMenuItem("Point de départ");
 		menuItemEnd = new JMenuItem("Point d'arrivée");
 		menuItemCalcul = new JMenuItem("Calculer");
 		
		// On ajoute un detecteur de click sur chacun des sous-menu
 		menuItemBegin.addMouseListener(this);
 		menuItemEnd.addMouseListener(this);
 		menuItemCalcul.addMouseListener(this);
 		menuItemCalcul.setEnabled(false);
 		
		// On ajoute les sous-menus
 		popupMenu.add(menuItemBegin);
 		popupMenu.add(menuItemEnd);
 		popupMenu.add(menuItemCalcul);
	}
	
	// Initialise les valeurs de la vue
	public void initValueIHM()
	{
		// On recupere la map 2D
		map2D = ctrl.getMap2D();

		try
		{
			// On recupere le nombre de case en hauteur de la map 2D
			i_nbCaseHeight = ctrl.getMap2D().size();

			// On recupere le nombre de case en largeur de la map 2D		
			i_nbCaseWidth = ctrl.getMap2D().get(0).size();
		}
		catch(Exception exception)
		{
			i_nbCaseHeight = 0;
			
			i_nbCaseWidth = 0;
		}

	}
 	
	// Creation de l'arriere plan
 	private void createBackGround()
 	{
 		if(!(i_nbCaseWidth <= 0 && i_nbCaseHeight <= 0))
 		{
 			// On cree l'image de l'arriere plan
 	   		imgOnBackground = createImage(i_nbCaseWidth * i_sizeSquare, i_nbCaseHeight * i_sizeSquare);
 	   		
 	 		//imgOnBackground = createImage(getWidth(),getHeight());
 	 		bufferGraphics=(Graphics2D)imgOnBackground.getGraphics();
 	 		
 	  		// On parcourt chaque ligne
 			for(int i_idRow = 0 ; i_idRow < i_nbCaseHeight ; i_idRow++)
 	    	{
 				// On parcourt chaque colonne
 				for(int i_idColumn = 0 ; i_idColumn < i_nbCaseWidth ; i_idColumn++)
 	    		{
 					// On cree un polygone temporaire
 					Polygon polygonTmp = new Polygon();
 					
 					// On lui affecte le point 2D
 					polygonTmp.setPoint2D(new Point(i_idColumn,i_idRow));
 					
 					// On cree le tableau de point du polygone
 	    			createTabPoints(polygonTmp);
 				
 					// On dessine le polygone
 	    			drawPolygon(bufferGraphics, polygonTmp,getColor(ctrl.getMap2D().get(i_idRow).get(i_idColumn)),false);
 	    		}
 			}
 		}
 	}
 	
 	/**
 	 * Renvoie la couleur associee a un type de case
 	 * @param i_typeCase
 	 * @return
 	 */
 	private Color getColor(Integer i_typeCase)
 	{	
		// Ville
		if(51 == i_typeCase || 52 == i_typeCase || 53 == i_typeCase)
		{
				return Color.RED;	
		}
 		
		// Herbe + Jardin
		else if(100 == i_typeCase || 108 == i_typeCase)
		{
			return Color.GREEN;
		}
		
		// Arbre + Sapin + Arbres Mutants + Bambou + Agro + Menhir
		else if(101== i_typeCase || 102 == i_typeCase || 132 == i_typeCase || 133 == i_typeCase || 141 == i_typeCase || 142 == i_typeCase || 143 == i_typeCase || 144 == i_typeCase || 145 == i_typeCase)
		{
			return new Color(0,108,11);
		}
		
		// Eau
		else if(103 == i_typeCase || 104== i_typeCase)
		{
			return Color.BLUE;
		}
		// Montagne + Cratere
		else if(105 == i_typeCase || 146 == i_typeCase || 147 == i_typeCase)
		{
			return new Color(97,23,4);
		}
		// Sable
		else if(106 == i_typeCase)
		{
			return Color.YELLOW;
		}		
		// Glace
		else if(107 == i_typeCase)
		{
			return Color.WHITE;
		}
		// Rue
		else if(110 == i_typeCase || 111 == i_typeCase || 112 == i_typeCase || 113 == i_typeCase || 114 == i_typeCase || 115 == i_typeCase || 116 == i_typeCase || 117 == i_typeCase || 118 == i_typeCase || 119 == i_typeCase || 120 == i_typeCase || 121 == i_typeCase || 122 == i_typeCase)
		{
			return Color.GRAY;
		}
		// Pas de la map
		else if(131 == i_typeCase)
		{
			return new Color(227,181,28);
		}
		// Lave ?
		else if(148 == i_typeCase)
		{
			return new Color(246,139,0);
		}		
		// ?
		else if(149 == i_typeCase)
		{
			return new Color(79,76,74);
		}		
		
		// Pas de la map
		else if(0 == i_typeCase)
		{
			return Color.BLACK;
		}

		return new Color(250,142,243);
 	}
 	
	// Dessine le background
	private void drawBackground()
	{
 		if(!(i_nbCaseWidth <= 0 && i_nbCaseHeight <= 0))
 		{
 			g2.drawImage(imgOnBackground,0,0,this);
 		}
	}
 	
    public void paintComponent(Graphics g)
    {
		// On requiert l'attention
    	requestFocus();
    	
		// On convertit le graphics en graphics2D
		this.g2 = (Graphics2D)g;
    	
		//this.bufferGraphics = (Graphics2D)g; 

		// On change la taille preferee
   		setPreferredSize(new Dimension(i_nbCaseWidth * i_sizeSquare, i_nbCaseHeight * i_sizeSquare));
		
   		// On dessine une image de fond
   		g2.setColor(new Color(238,238,238));
   		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
   		
		// On cree l'arriere plan
   		createBackGround();
    	
		// On dessine l'arriere plan
    	drawBackground();

		// Si la souris a bouge et que le polygone contenant la position de la souris n'est pas null
    	if(hasMooved && ctrl.getPolygonMouse() != null)
    	{
			// On cree le tableau de point du polygone
    		createTabPoints(ctrl.getPolygonMouse());
    		
			// On dessine le polygone
    		drawPolygon(g2,ctrl.getPolygonMouse(),Color.ORANGE,true);
    	}
    	
		// Si le polygone de depart n'est pas null
    	if(ctrl.getPolygonBegin() != null)
    	{
			// On cree le tableau de point du polygone
    		createTabPoints(ctrl.getPolygonBegin());
			
			// On dessine le polygone
    		drawPolygon(g2,ctrl.getPolygonBegin(),Color.GREEN,true);
    	}
    	
		// Si le polygone d'arrivee n'est pas null
    	if(ctrl.getPolygonEnd() != null)
    	{
			// On cree le tableau de point du polygone	
    		createTabPoints(ctrl.getPolygonEnd());
			
			// On dessine le polygone
    		drawPolygon(g2,ctrl.getPolygonEnd(),Color.RED,true);
    	}
    	
		// Si on doit dessiner le chemin
    	if(ctrl.getListPointToDraw() != null)
    	{
			// On dessine le chemin
    		drawWay();
    	}
	} 
 	
	// Dessine le chemin entre le point de depart et d'arrivee
    private void drawWay() {
		
		// Pour chaque point du chemin
		for(Point eachPoint : ctrl.getListPointToDraw())
		{
			// On cree un polygone temporaire
			Polygon polygonTmp = new Polygon();
			
			// On lui affecte le point 2D
			polygonTmp.setPoint2D(eachPoint);
			
			// On lui affecte le point 3D
			polygonTmp.setPoint3D(ctrl.convert2DTo3D(eachPoint));
			
			// On cree le tableau de point du polygone
			createTabPoints(polygonTmp);
			
			// On dessine le polygone
			drawPolygon(g2, polygonTmp,Color.MAGENTA,true);
		}
	}

	// Cree le tableau contenant la position des quatre points du polygone
	private void createTabPoints(Polygon polygon)
    {
		Point point2D = polygon.getPoint2D();
		
		//On cree un couple x,y afin de positionner la case
		int i_x = point2D.x * i_sizeSquare;
		int i_y = point2D.y * i_sizeSquare;

		// On cree le tableau contenant l'abscisse des quatre points du polygone
		int x[] = {i_x,i_x + i_sizeSquare,i_x + i_sizeSquare,i_x};
	    
		// On cree le tableau contenant l'ordonnee des quatre points du polygone  
		int y[] = {i_y,i_y,i_y + i_sizeSquare,i_y + i_sizeSquare}; 
	    
		// On enregistre le tableau contenant la position des quatre points du polygone
	    polygon.setI_tab_xPoints(x);
	    polygon.setI_tab_yPoints(y);
    }
    
	
	// Dessine le polygone
	private void drawPolygon(Graphics2D g2, Polygon polygonToDraw, Color color, boolean isTransparent)
	{
		Stroke strokeMem = g2.getStroke();
		
		// On change la couleur
	    g2.setColor(color);
		    
	    if(!isTransparent)
	    {
	    	g2.setStroke(new BasicStroke(2));
	    	g2.setColor(Color.BLACK);
	    }
	    
		// On dessine le contour du polygone
	    g2.drawPolygon (polygonToDraw.getI_tab_xPoints(), polygonToDraw.getI_tab_yPoints(), polygonToDraw.getI_tab_xPoints().length);
	    
	    if(!isTransparent)
	    {
	    	g2.setStroke(strokeMem);
	    	g2.setColor(color);
	    }
	    
		// On aplique la transparence
	    if(isTransparent)g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER,Math.min(0.3f,1.0f))) ;
	    	    
		// On dessine le fond transparent du polygone
		g2.fillPolygon (polygonToDraw.getI_tab_xPoints(), polygonToDraw.getI_tab_yPoints(), polygonToDraw.getI_tab_xPoints().length);
	    
		// On retire la transparence
		if(isTransparent)g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER,Math.min(1.0f,1.0f))) ;
	    
		// On change la valeur du booleen indiquant que la souris a ete deplacee
		hasMooved = false;
	}

	// Renvoie la liste d'interface visualisable
	public List<I_Viewable> getListI_Viewable()
	{
		return listI_Viewable;
	}
	
	// Change la liste d'interface visualisable
	public void setListI_Viewable(List<I_Viewable> listI_Viewable)
	{
		this.listI_Viewable = listI_Viewable;
		addI_Viewable();
	}

	// Ajoute une interface visualisable
	public void addI_Viewable()
	{
		listI_Viewable.add(this);
	}
	


	// Recupere la position de la souris
	Point getPositionMouse(int i_x, int i_y)
	{
		int i_xPoint = i_x / i_sizeSquare;
		int i_yPoint = i_y / i_sizeSquare;
		
		// On cree le point 2D ou se trouve la souris
		Point pointMouse = new Point(i_xPoint,i_yPoint);
		
		// On renvoie ce point
		return pointMouse;
	}

	@Override
	public void setController(Controller ctrl) {
		this.ctrl = ctrl;
	}

	@Override
	public Controller getController() {
		return ctrl;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent event) {
		
		// Si on fait tourner la roulette vers le haut
		if(event.getWheelRotation() < 0)
		{
			if(hasCtrlPressed)
			{
				ctrl.zoomMap(false);
			}
			else
			{
				ctrl.mooveMap(false,event.getWheelRotation());
			}

		}
		// Sinon, on fait tourner la roulette vers le bas
		else
		{
			if(hasCtrlPressed)
			{
				ctrl.unzoomMap(false);
			}
			else
			{
				ctrl.mooveMap(false,event.getWheelRotation());
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// Non utile
	}

	@Override
	public void mouseMoved(MouseEvent arg0)
	{
			
		try
		{
			if(this.getMousePosition() != null)
			{

				// On calcule la position de la souris dans la map en 2 dimension
				Point point2D = getPositionMouse(this.getMousePosition().x,this.getMousePosition().y);
	
				// On met le polygone ou est la souris a "null"
				ctrl.setPolygonMouse(null);
				
				// Si le point fait partie de la map
				if(point2D.x >= 0 && point2D.y >= 0 && point2D.x < map2D.get(0).size() && point2D.y < map2D.size() && map2D.get(point2D.y).get(point2D.x) != 0)
				{	
					// On indique que la souris a bouge
					hasMooved = true;
					
					// On cree un nouveau polygone
					Polygon polygonMouseTmp = new Polygon();
					
					// On lui affecte le point 2D
					polygonMouseTmp.setPoint2D(point2D);
						
					// On lui affecte le point 3D
					polygonMouseTmp.setPoint3D(ctrl.convert2DTo3D(point2D));
					
					// On change le modele via le controlleur
					ctrl.setPolygonMouse(polygonMouseTmp);

				}
				
				// On fait un repaint
				repaint();
				
				// On revalide
				revalidate();
			}
		}
		catch(Exception e)
		{
			System.out.println("(WARNING) Exception in function mouseMoved in Class PanelGPS2D");
			repaint();
			revalidate();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e){
		// Non utile
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Non utile
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Non utile
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// Non utile
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		// On recupere le bouton de la souris qui a ete relache
	    int buttonDown = e.getButton();
	    
	    // Si c'est le bouton de gauche
	    if (buttonDown == MouseEvent.BUTTON1)
	    {
	    	// Si il s'agit du menu pour placer le depart
	    	if(e.getSource()==menuItemBegin)
			{
	    		// On enregistre le polygone de la souris dans celui de depart
	    		ctrl.setPolygonBegin(ctrl.getPolygonMouse());
			}
	    	
	    	// Si il s'agit du menu pour placer l'arrivee
	    	else if(e.getSource()==menuItemEnd)
	    	{
	    		// On enregistre le polygone de la souris dans celui d'arrivee
	    		ctrl.setPolygonEnd(ctrl.getPolygonMouse());
	    	}
	    	
	    	// Si il s'agit du menu pour calculer le trajet
	    	else if(e.getSource()==menuItemCalcul)
	    	{
	    		// On recupere la liste de point du trajet
	    		ctrl.calculatePdVWay();
	    	}
	    	
			// On fait un repaint
			repaint();
			
			// On revalide
			revalidate();
	    }
	    // Si c'est le bouton de droite
		if(buttonDown == MouseEvent.BUTTON3)
		{
			// Si le polygone de la souris n'est pas null
			if(ctrl.getPolygonMouse() != null)
			{
				// Si le polygone de debut et de fin ont ete defini on degrise le menu calcul
				if(ctrl.getPolygonBegin() != null && ctrl.getPolygonEnd() != null)
				{
					menuItemCalcul.setEnabled(true);
				}
				// Sinon on le grise
				else
				{
					menuItemCalcul.setEnabled(false);
				}
				// On affiche le JPopupMenu
				popupMenu.show(e.getComponent(),e.getX(), e.getY());	
			}
		}
	}


	@Override
	public void keyPressed(KeyEvent arg0) {	
		if(arg0.getKeyCode() == KeyEvent.VK_CONTROL)
		{
			hasCtrlPressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if(arg0.getKeyCode() == KeyEvent.VK_CONTROL)
		{
			hasCtrlPressed = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void typeMapChanged(boolean is3dMap) {
		// Si on a demande d'afficher la map 2D
		if(!is3dMap)
		{
			repaint();
			revalidate();	
		}
	}

	@Override
	public void scaleMapChanged(int i_scale, boolean is3dMap) {
		// Si on a demande d'afficher la map 2D
		if(!is3dMap)
		{
		
			// On memorise l'identifiant de l'echelle
			i_sizeSquare = i_scale;
			
			repaint();
			revalidate();	
		}
	}

	@Override
	public void calculDone(float f_nbPdV) {
		repaint();
		revalidate();
	}

	@Override
	public void newPositionMouse(Polygon polygonMouse) {
		// Rien a faire
	}

	@Override
	public void beginSelected(Polygon polygonBegin) {
		repaint();
		revalidate();
	}

	@Override
	public void stopSelected(Polygon polygonStop) {
		repaint();
		revalidate();
	}

	@Override
	public void searchCriteriaChange() {
		repaint();
		revalidate();
	}

	@Override
	public void changeTypeMap3D(int i_typeMap3D) {
		// Rien a faire
	}


}
