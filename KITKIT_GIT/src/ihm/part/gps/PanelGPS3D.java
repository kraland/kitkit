package ihm.part.gps;

import ihm.I_Viewable;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import model.gps.I_ModelGPSListener;
import model.gps.Polygon;

import controller.Controller;
import controller.I_ControllerDialog;

@SuppressWarnings("serial")
public class PanelGPS3D extends JPanel implements	I_Viewable,
													I_ModelGPSListener,
													I_ControllerDialog,
													MouseWheelListener,
													MouseMotionListener,
													MouseListener,
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
	 * Map 3D
	 */
	private List<List<Integer>> map3D;
	
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
	 * Liste d'image de BackGround
	 */
	private Image[] tab_imgBackground;
			
	/**
	 * Booleen indiquant si les lignes paires de la map sont toutes a gauche
	 */
	private boolean hasPairLineOnLeft;
	
	/**
	 * Taille de l'image de base
	 */
	private static int i_sizeImg_Basis = 54;
	
	/**
	 * Taille actuelle de l'image apres redimensionnement
	 */
	private int i_sizeImg;
	
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
	
	/**
	 * Nombre de fois que l'image carree de cote "i_sizeImg"
	 * est repetee sur la carte suivant Y
	 */
	private double d_factorY;
	
	/**
	 * Nombre de fois que l'image carree de cote "i_sizeImg"
	 * est repetee sur la carte suivant X
	 */
	private double d_factorX;
	
	/**
	 * Identifiant de l'echelle 3D a dessinner
	 */
	private int i_idScale = 0;
	
	/**
	 * Constructeur
	 */
	public PanelGPS3D()
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
 		menuItemCalcul.setEnabled(false);
 		
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
	
	public void initValueIHM()
	{
		// On recupere la map 3D
		map3D = ctrl.getMap3D();
		
		try
		{
			// On recupere le nombre de case en hauteur de la map 2D
			i_nbCaseHeight = ctrl.getMap2D().size();

			// On recupere le nombre de case en largeur de la map 2D		
			i_nbCaseWidth = ctrl.getMap2D().get(0).size();
			
			// On calcule le nombre de fois que l'image carree de cote "i_sizeImg" est repetee sur la carte suivant Y
			d_factorY = (i_nbCaseHeight + i_nbCaseWidth - 1)/4 + (i_nbCaseHeight + i_nbCaseWidth - 1)%4 * 0.25 + 1.5;
				
			// On calcule le nombre de fois que l'image carree de cote "i_sizeImg" est repetee sur la carte suivant X
			d_factorX = (i_nbCaseHeight + i_nbCaseWidth)/2 + 0.5;
					
			// On cree un tableau de taille fixe
			tab_imgBackground = new Image[ctrl.getTab_I_percentageZoomMap3D().length];

		}
		catch(Exception exception)
		{
			i_nbCaseHeight = 0;
			i_nbCaseWidth = 0;
			d_factorY = 0;
			d_factorX = 0;
			tab_imgBackground = new Image[0];
		}

	}
	
	// Creation de l'arriere plan
 	private void createBackGround()
 	{
		
		// On regarde si l'image n'a pas deja ete cree
		if(tab_imgBackground.length > 0 && tab_imgBackground[i_idScale] == null)
		{
			// On cree l'image de l'arriere plan
			tab_imgBackground[i_idScale] = createImage((int)Math.floor(d_factorX * i_sizeImg), (int)Math.floor(d_factorY * i_sizeImg));
			
			System.out.println("PanelGPS3D.java - Creation de la map 3D : " + (int)Math.floor(d_factorX * i_sizeImg) + "*" + (int)Math.floor(d_factorY * i_sizeImg));
			
			// On recupere l'image correspondante dans le tableau
			bufferGraphics=(Graphics2D)tab_imgBackground[i_idScale].getGraphics();
			
			// Si la ligne est paire
			if(i_nbCaseHeight%2 == 0)
			{
				hasPairLineOnLeft = false;	
			}
			// Si la ligne est impaire
			else
			{
				hasPairLineOnLeft = true;	
			}
			
			// On parcourt chaque ligne
			for(int i_idRow = 0 ; i_idRow< (i_nbCaseHeight + i_nbCaseWidth - 1) ; i_idRow++)
			{
				
				// On parcourt chaque colonne
				for(int i_idColumn = 0 ; i_idColumn < ((i_nbCaseHeight + i_nbCaseWidth)/2) ; i_idColumn++)
				{				
					// Image icone temporaire de chaque case
					ImageIcon imgIconTmp = null;

					imgIconTmp = new ImageIcon(getClass().getResource("/img/map/" + ctrl.getI_typeMap3D() + "/" + map3D.get(i_idRow).get(i_idColumn) + ".gif"));
					
					// Image a dessiner
					Image imgToDraw = scaleImage(imgIconTmp.getImage(), ((double)ctrl.getI_percentageZoom()/100));
					
					Point pointToDraw = getTopLeftPoint3D(new Point(i_idColumn,i_idRow));
					
					// On ajoute l'image au graphics
					bufferGraphics.drawImage(imgToDraw,pointToDraw.x,pointToDraw.y,null);
				}
			}
		}
 	}
 	
	// Dessine le background
	private void drawBackground()
	{
		if(tab_imgBackground.length > i_idScale)
			g2.drawImage(tab_imgBackground[i_idScale],0,0,this);
	}
 	
    public void paintComponent(Graphics g)
    {
		// On requiert l'attention
    	requestFocus();
    	
		// On convertit le graphics en graphics2D
		this.g2 = (Graphics2D)g;
				
		// On calcule la taille de l'image retaillee
		i_sizeImg = (int) (i_sizeImg_Basis * ((double)ctrl.getI_percentageZoom()/100));
		    	
		// On change la taille preferee
   		setPreferredSize(new Dimension((int)Math.floor(d_factorX * i_sizeImg), (int)Math.floor(d_factorY * i_sizeImg)));
   		
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
    		drawPolygon(ctrl.getPolygonMouse(),Color.BLUE);
    	}
    	
		// Si le polygone de depart n'est pas null
    	if(ctrl.getPolygonBegin() != null)
    	{
			// On cree le tableau de point du polygone
    		createTabPoints(ctrl.getPolygonBegin());
			
			// On dessine le polygone
    		drawPolygon(ctrl.getPolygonBegin(),Color.GREEN);
    	}
    	
		// Si le polygone d'arrivee n'est pas null
    	if(ctrl.getPolygonEnd() != null)
    	{
			// On cree le tableau de point du polygone	
    		createTabPoints(ctrl.getPolygonEnd());
			
			// On dessine le polygone
    		drawPolygon(ctrl.getPolygonEnd(),Color.RED);
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
			drawPolygon(polygonTmp,Color.YELLOW);
		}
	}

    private Point getTopLeftPoint3D(Point point3D)
    {
    	Point pointToReturn = getBottomLeftPoint3D(point3D);
    	pointToReturn.y = pointToReturn.y - i_sizeImg;
    	return pointToReturn;
    }
    
    private Point getBottomLeftPoint3D(Point point3D)
    {
		//On cree un couple x,y afin de positionner la case
		int i_x = 0;
		int i_y = point3D.y * (i_sizeImg/4) + i_sizeImg;
		
		// Si la ligne est paire
		if(point3D.y%2 == 0)
		{
					
			// Si les lignes paires sont situees tout a gauche
			if(hasPairLineOnLeft)
			{
				// On change l'abscisse de l'image
				i_x = i_sizeImg * point3D.x;
			}
			// Sinon cela signifie que c'est les lignes impaires qui se trouve tout a gauche
			else
			{
				i_x = i_sizeImg * point3D.x + i_sizeImg/2;
			}
		}
		
		// Sinon, la ligne est impaire
		else
		{
			// Si les lignes paires sont situees a gauche
			if(hasPairLineOnLeft)
			{
				i_x = i_sizeImg * point3D.x + i_sizeImg/2;
			}
			// Sinon cela signifie que c'est les lignes impaires qui se trouve tout a gauche
			else
			{
				i_x = i_sizeImg * point3D.x;						
			}
		}
		
		return new Point(i_x,i_y);
    }
    
	// Cree le tableau contenant la position des quatre points du polygone
	private void createTabPoints(Polygon polygon)
    {
		// On recupere le point a dessiner
		Point pointToDraw = getBottomLeftPoint3D(polygon.getPoint3D());
				
		// On cree le tableau contenant l'abscisse des quatre points du polygone
		int x[] = {pointToDraw.x,pointToDraw.x + i_sizeImg/2,pointToDraw.x + i_sizeImg,pointToDraw.x + i_sizeImg/2};
	    
		// On cree le tableau contenant l'ordonnee des quatre points du polygone  
		int y[] = {pointToDraw.y - i_sizeImg/4 ,pointToDraw.y - i_sizeImg/2,pointToDraw.y - i_sizeImg/4,pointToDraw.y};
	    
		// On enregistre le tableau contenant la position des quatre points du polygone
	    polygon.setI_tab_xPoints(x);
	    polygon.setI_tab_yPoints(y);
    }
    
	
	// Dessine le polygone
	private void drawPolygon(Polygon polygonToDraw, Color color)
	{
		// On change la couleur
	    g2.setColor(color);
		
		// On dessine le contour du polygone
	    g2.drawPolygon (polygonToDraw.getI_tab_xPoints(), polygonToDraw.getI_tab_yPoints(), polygonToDraw.getI_tab_xPoints().length);
	    
		// On aplique la transparence
		g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER,Math.min(0.3f,1.0f))) ;
	    
		// On dessine le fond transparent du polygone
		g2.fillPolygon (polygonToDraw.getI_tab_xPoints(), polygonToDraw.getI_tab_yPoints(), polygonToDraw.getI_tab_xPoints().length);
	    
		// On retire la transparence
		g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER,Math.min(1.0f,1.0f))) ;
	    
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

	// Trouve la distance entre l'origine et le projete sur l'axe des abscisses ou des ordonnees de la position de la souris
	double findDistance(boolean isXAxis, Point ptOrigin, Point ptOnAxis)
	{
	
		// Booleen indiquant si la valeur est negative
		boolean isNegativeValueToReutrn = false;

		// Si c'est suivant l'axe X
		if(isXAxis)
		{
			if(ptOnAxis.getX() < ptOrigin.getX())
			{
				isNegativeValueToReutrn = true;
			}
		}
		// Sinon c'est suivant l'axe Y
		else
		{
			if(ptOnAxis.getY() < ptOrigin.getY())
			{
				isNegativeValueToReutrn = true;
			}		
		}
	
		// On calcule la distance entre les deux points
		double d_distanceFound =  Math.sqrt(Math.pow(ptOrigin.getX() - ptOnAxis.getX(),2) + Math.pow(ptOrigin.getY() - ptOnAxis.getY(),2));

		// Si c'est une valeur negative a renvoyer
		if(isNegativeValueToReutrn)
		{
			d_distanceFound = 0 - d_distanceFound;
		}
	
		// On renvoie la distance trouvee
		return d_distanceFound;
	}

	// Renvoie le point d'intersection entre deux droites
	Point getIntersection(double d_coeff1, double d_ord1, double d_coeff2, double d_ord2)
	{
		// On calcule l'ascissse
		double d_x = (d_ord2 - d_ord1) / (d_coeff1 - d_coeff2); 
		
		// On calcule l'ordonnee
		double d_y = d_coeff1 * d_x + d_ord1;
		
		// On cree le point d'intersection
		Point pointInter = new Point((int)d_x,(int)d_y);
		
		// On renvoie le point d'intersection
		return pointInter;
		 
	}
	
	// Recupere la position de la souris
	Point getPositionMouse(int i_x, int i_y)
	{
		//TODO
		// Erreur d'arrondi a un moment... Fonctionne pour les multiples de 15%
		// (i_tab_percentageZoomMap3D[] = {15,30,45,60,75,90};)
		// La case selectionnee n'est pas à la bonne place en echelle 100%
		
		// Point d'origine
		Point pointOrigin = new Point(52*i_sizeImg,i_sizeImg/2);
		
		// Coefficient de l'axe des abscisses
		double d_coeffX = 0.5;
		
		// Coefficient de l'axe des ordonnees
		double d_coeffY = -0.5;
		
		// Ordonnee a l'origine de l'axe des abscisses
		double d_ordX = pointOrigin.getY() - d_coeffX * pointOrigin.getX();
		
		// Ordonnee a l'origine de l'axe des ordonnees
		double d_ordY = pointOrigin.getY() - d_coeffY * pointOrigin.getX();
		
		// On recupere l'ordonnee a l'origine de la droite parallele
		// a l'axe des ordonnees passant par le point ou se trouve la souris
		double d_ordX_projete = i_y - d_coeffX * i_x;

		// On recupere l'ordonnee a l'origine de la droite parallele
		// a l'axe des abscisses passant par le point ou se trouve la souris
		double d_ordY_projete = i_y - d_coeffY * i_x;
		
		// On calcule du projete sur les deux axes et la distance entre ce point et l'origine
		Point pointOnXAxis = getIntersection(d_coeffX,d_ordX,d_coeffY,d_ordY_projete);
		Point pointOnYAxis = getIntersection(d_coeffY,d_ordY,d_coeffX,d_ordX_projete);
		
		// Trouve la distance entre le point d'origine et le projete du point sur chaque axe
		double d_distanceX = findDistance(true,pointOrigin,pointOnXAxis);
		double d_distanceY = findDistance(false,pointOrigin,pointOnYAxis);
		
		// On calcule la distance d'une unite sur le graphique
		double d_distanceScale = Math.sqrt(Math.pow((double)i_sizeImg/4,2) + Math.pow((double)i_sizeImg/2,2));
		
		// On cree le point 2D ou se trouve la souris
		Point pointMouse = new Point((int)(d_distanceX/d_distanceScale),(int)(d_distanceY/d_distanceScale));
		
		// On renvoie ce point
		return pointMouse;
	}
	
	// Fonction qui va retailler une image suivant un facteur (<1 pour retrecir, >1 pour agrandir):
	public static Image scaleImage(final Image source, final double factor)
	{
	    int width = (int) (source.getWidth(null) * factor);
	    int height = (int) (source.getHeight(null) * factor);
	    return scaleImage(source, width, height);
	}
	
	// Fonction qui va retailler une image suivant une taille en pixels (=hauteur si portrait, largeur si paysage):
	public static Image scaleImage(Image source, int size)
	{
		int width = source.getWidth(null);
	    int height = source.getHeight(null);
	    double f = 0;
	    
	    // Portrait
	    if (width < height)
	    {
	        f = (double) height / (double) width;
	        width = (int) (size / f);
	        height = size;
	    }
	    // Paysage
	    else
	    {
	        f = (double) width / (double) height;
	        width = size;
	        height = (int) (size / f);
	    }
	    
	    return scaleImage(source, width, height);
	}
	
	// Fonction retaillant une image en fonction de la largeur et de la hauteur fourni en parametre
	public static Image scaleImage(Image source, int width, int height)
	{
	    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g = (Graphics2D) img.getGraphics();
	    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g.drawImage(source, 0, 0, width, height, null);
	    g.dispose();
	    return img;
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
				ctrl.zoomMap(true);
			}
			else
			{
				ctrl.mooveMap(true,event.getWheelRotation());
			}

		}
		// Sinon, on fait tourner la roulette vers le bas
		else
		{
			if(hasCtrlPressed)
			{
				ctrl.unzoomMap(true);
			}
			else
			{
				ctrl.mooveMap(true,event.getWheelRotation());
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
			Point pointTmp = this.getMousePosition();
			
			// On calcule la position de la souris dans la map en 2 dimension
			Point point2D = getPositionMouse(pointTmp.x,pointTmp.y);
				
			// On recupere la map 2D
			List<List<Integer>> map2D = ctrl.getMap2D();
			
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
		catch(Exception e)
		{
			System.out.println("(WARNING) Exception in function mouseMoved in Class PanelGPS3D");
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
	public void keyPressed(KeyEvent arg0)
	{
		if(arg0.getKeyCode() == KeyEvent.VK_CONTROL)
		{
			hasCtrlPressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0)
	{
		if(arg0.getKeyCode() == KeyEvent.VK_CONTROL)
		{
			hasCtrlPressed = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

	@Override
	public void typeMapChanged(boolean is3dMap)
	{
		// Si on a demande d'afficher la map 3D
		if(is3dMap)
		{
			repaint();
			revalidate();	
		}
	}

	@Override
	public void scaleMapChanged(int i_scale, boolean is3dMap) {
		// Si on a demande d'afficher la map 3D
		if(is3dMap)
		{
			// On memorise l'identifiant de l'echelle
			i_idScale = i_scale;
			
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
		
		for(int i_idTypeMap3D = 0 ; i_idTypeMap3D < tab_imgBackground.length ; i_idTypeMap3D++)
		{
			tab_imgBackground[i_idTypeMap3D] = null;
		}
		
		// On cree un tableau de taille fixe
		tab_imgBackground = new Image[ctrl.getTab_I_percentageZoomMap3D().length];
		
		repaint();
		revalidate();
	}
}
