package model.gps;

import java.awt.Point;
import java.util.List;

import javax.swing.event.EventListenerList;


public class ModelGPS {
	
	/**
	 * Liste de listeners de modele GPS
	 */
	private EventListenerList listeners;

	/**
	 * Type de map choisie pour l'application
	 */
	private int i_typeMap3D = 0;
	
	/**
	 * Vitesse de deplacement sur la terre
	 */
	double d_coeffEarth = 1;

	/**
	 * Vitesse de deplacement sur l'air
	 */
	double d_coeffAir = 1;
	
	/**
	 * Vitesse de deplacement sur l'eau
	 */
	double d_coeffWater = 1;
		
	/**
	 * Table contenant la liste des zooms (en pourcentage) disponible pour la map 3D
	 */
	private static int i_tab_percentageZoomMap3D[] = {15,30,45,60,75,90};	
	
	/**
	 * Identifiant du pourcentage selectionne
	 */
	private int i_idTabCoeffZoom = 0; 
	
	/**
	 * Booleen indiquant si on autorise les transport en commun en avion
	 */
	boolean hasSelectedPlane;

	/**
	 * Booleen indiquant si on autorise les transport en commun en bateau
	 */
	boolean hasSelectedBoat;
	
	/**
	 * Booleen indiquant si on autorise les transport en commun en train
	 */
	boolean hasSelectedTrain;
	
	/**
	 * Polygone ou se trouve la souris
	 */
	private Polygon polygonMouse;
	
	/**
	 * Polygone de depart
	 */
	private Polygon polygonBegin;
	
	/**
	 * Polygone de fin
	 */
	private Polygon polygonEnd;
		
	/**
	 * Liste des points a dessiner
	 */
	private List<Point> listPointToDraw;
	
	/**
	 * Nombre de pdv du trajet
	 */
	private float f_pdvWay;
	
	/**
	 * Taille en pixel d'un carre de la map 2D
	 */
	private int i_sizeSquareMap2D = 4;
	
	/**
	 * Booleen indiquant quel type de map on affiche
	 */
	private boolean is3DMap;
	
	/**
	 * Booleen indiquant si un critere a ete change depuis le dernier calcul
	 */
	private boolean hasAlreadyChangedCriteria = true;
	
	/**
	 * Constructeur
	 */
	public ModelGPS()
	{
		listeners = new EventListenerList();
	}

	/**
	 * Ajoute un listener de modele de GPS
	 * @param listener 
	 */
	public void addModelGPSListener(I_ModelGPSListener listener){
		listeners.add(I_ModelGPSListener.class, listener);
	}
	
	/**
	 * Enleve un listener de modele de GPS
	 * @param listener
	 */
	
	public void removeModelGPSListener(I_ModelGPSListener listener){
		listeners.remove(I_ModelGPSListener.class, listener);
	}
	
	/**
	 * Notifie le(s) listener(s) du modele du GPS que
	 * celui-ci a ete modifie
	 */
	public void fireModelGPSChanged(int i_typeChange)
	{
		// On recupere la liste de listener du modele GPS
		I_ModelGPSListener[] listenerModelGPS = (I_ModelGPSListener[])listeners.getListeners(I_ModelGPSListener.class);
		
		// On parcourt chaque listener
		for(I_ModelGPSListener listener : listenerModelGPS)
		{
			switch (i_typeChange)
			{
			
			//	Indique que le type de map a change
			case 0:
				// On relaye l'information au listener
				listener.typeMapChanged(is3DMap);
				break;
				
			// Indique que la taille de la map a changee
			case 1:
				// On relaye l'information au listener
				
				// Si on change l'echelle de la map 3D
				if(is3DMap)
				{
					listener.scaleMapChanged(i_idTabCoeffZoom, is3DMap);					
				}
				else
				{
					listener.scaleMapChanged(i_sizeSquareMap2D, is3DMap);					
				}
				break;
				
			// Indique que le calcul vient d'etre fait
			case 2:
				// On relaye l'information au listener
				listener.calculDone(f_pdvWay);
				break;
				
			// Indique une nouvelle position de la souris sur la map
			case 3:
				// On relaye l'information au listener
				listener.newPositionMouse(polygonMouse);
				break;
				
			// Indique qu'un depart a ete selectionne
			case 4:
				// On relaye l'information au listener
				listener.beginSelected(polygonBegin);
				break;
				
			// Indique qu'un arret a ete selectionne
			case 5:
				// On relaye l'information au listener
				listener.stopSelected(polygonEnd);
				break;
				
			// Indique qu'au moins un critere de la recherche a change...
			case 6:
				// On relaye l'information au listener
				listener.searchCriteriaChange();
				break;
			
			// Indique un changement de map 3D
			case 7:
				listener.changeTypeMap3D(i_typeMap3D);
				break;
			
			default:
				System.err.println("ModelGPS.java - Erreur fonction fireModelGPSChanged");
				break;

			}
		}
	}
	
	/**
	 * Renvoie le polygone ou se trouve la souris
	 */
	public Polygon getPolygonMouse() {
		return polygonMouse;
	}

	/**
	 * Change le polygone ou se trouve la souris
	 */ 	
	public void setPolygonMouse(Polygon polygonMouse) {
		
		// On memorise le polygone ou se trouve la souris
		this.polygonMouse = polygonMouse;
		
		// On indique qu'on a change la position de la souris
		fireModelGPSChanged(3);
	}

	/**
	 * Renvoie le polygone de depart
	 */ 
	public Polygon getPolygonBegin() {
		return polygonBegin;
	}

	/**
	 * Change le polygone de depart
	 */  	
	public void setPolygonBegin(Polygon polygonBegin) {
		
		// On memorise le polygone de depart
		this.polygonBegin = polygonBegin;
		
		// Si on a deja change un critere depuis le dernier calcul
		if(hasAlreadyChangedCriteria)
		{
			fireModelGPSChanged(4);
		}
		// Sinon cela signifie que c'est la premiere fois qu'on change un critere depuis le dernier calcul effectue
		else
		{
			f_pdvWay = -1;
			listPointToDraw = null;
			
			fireModelGPSChanged(6);
			fireModelGPSChanged(4);
		}
		
	}

	/**
	 * Renvoie le polygone de fin
	 */  	
	public Polygon getPolygonEnd() {
		return polygonEnd;
	}

	/**
	 * Change le polygone de fin
	 */
	public void setPolygonEnd(Polygon polygonEnd) {
		
		// On memorise le polygone d'arrivee
		this.polygonEnd = polygonEnd;
		
		// Si on a deja change un critere depuis le dernier calcul
		if(hasAlreadyChangedCriteria)
		{
			fireModelGPSChanged(5);
		}
		// Sinon cela signifie que c'est la premiere fois qu'on change un critere depuis le dernier calcul effectue
		else
		{
			f_pdvWay = -1;
			listPointToDraw = null;
			
			fireModelGPSChanged(6);
			fireModelGPSChanged(5);
		}
		
	}

	/**
	 * Renvoie la liste de point a dessiner
	 */	
	public List<Point> getListPointToDraw() {
		return listPointToDraw;
	}

	/**
	 * Change la liste de point a dessiner
	 */
	public void setListPointToDraw(List<Point> listPointToDraw) {
		this.listPointToDraw = listPointToDraw;
}

	/**
	 * Renvoie le nombre de pdv du trajet
	 */
	public float getF_pdvWay() {
		return f_pdvWay;
	}

	/**
	 * Change le nombre de pdv du trajet
	 */
	public void setF_pdVWay(float f_pdvWay) {
		
		// On memorise le nombre de pdV
		this.f_pdvWay = f_pdvWay;
		
		// Comme on vient de faire le calcul on indique qu'aucun critere a change
		hasAlreadyChangedCriteria = false;
		
		// On inidique au listener qu'on vient d'effectuer un calcul
		fireModelGPSChanged(2);
	}

	
	/**
	 * Renvoie le boolean indiquant si il est possible de prendre l'avion
	 */
	public boolean hasSelectedPlane() {
		return hasSelectedPlane;
	}

	/**
	 * Change le boolean indiquant si il est possible de prendre l'avion
	 */
	public void setHasSelectedPlane(boolean hasSelectedPlane) {
		
		// On memorise si l'utilisateur a authorise les voyages en avion
		this.hasSelectedPlane = hasSelectedPlane;

		// Si c'est la premiere fois qu'on change un critere depuis le dernier calcul effectue
		if(!hasAlreadyChangedCriteria)
		{
			f_pdvWay = -1;
			listPointToDraw = null;
			
			fireModelGPSChanged(6);
		}
		
	}
	
	/**
	 * Renvoie le boolean indiquant si il est possible de prendre le bateau
	 */
	public boolean hasSelectedBoat() {
		return hasSelectedBoat;
	}

	/**
	 * Change le boolean indiquant si il est possible de prendre le bateau
	 */
	public void setHasSelectedBoat(boolean hasSelectedBoat) {
		
		// On memorise si l'utilisateur a authorise les voyages en bateau 
		this.hasSelectedBoat = hasSelectedBoat;
				
		// Si c'est la premiere fois qu'on change un critere depuis le dernier calcul effectue
		if(!hasAlreadyChangedCriteria)
		{
			f_pdvWay = -1;
			listPointToDraw = null;
			
			fireModelGPSChanged(6);
		}
	}
	
	/**
	 * Renvoie le boolean indiquant si il est possible de prendre le train
	 */
	public boolean hasSelectedTrain() {
		return hasSelectedTrain;
	}

	/**
	 * Change le boolean indiquant si il est possible de prendre le train
	 */
	public void setHasSelectedTrain(boolean hasSelectedTrain) {
		
		// On memorise si l'utilisateur a authorise les voyages en train
		this.hasSelectedTrain = hasSelectedTrain;
		
		// Si c'est la premiere fois qu'on change un critere depuis le dernier calcul effectue
		if(!hasAlreadyChangedCriteria)
		{
			f_pdvWay = -1;
			listPointToDraw = null;
			
			fireModelGPSChanged(6);
		}
	}
	
	/**
	 * Renvoie le tableau de pourcentage d'echelle de la map 3D
	 * @return
	 */
	public static int[] getI_tab_percentageZoomMap3D() {
		return i_tab_percentageZoomMap3D;
	}

	/**
	 * Renvoie l'id de l'echelle de la map 3D au sein du tableau de pourentage
	 * @return
	 */
	public int getI_idTabCoeffZoom() {
		return i_idTabCoeffZoom;
	}

	/**
	 * Change l'id de l'echelle de la map 3D au sein du tableau de pourentage
	 * @param i_idTabCoeffZoom
	 */
	public void setI_idTabCoeffZoom(int i_idTabCoeffZoom) {
		
		// On memorise l'identifiant du zoom selectionne
		this.i_idTabCoeffZoom = i_idTabCoeffZoom;

		// On met a jour le zoom
		fireModelGPSChanged(1);
	}

	/**
	 * Renvoie le booleen indiquant le type de map (2D/3D)
	 * @return
	 */
	public boolean is3DMap() {
		return is3DMap;
	}

	/**
	 * Change le booleen indiquant le type de map (2D/3D)
	 * @param is3DMap
	 */
	public void setIs3DMap(boolean is3DMap)
	{
		// On memorise le type de map dans le modele
		this.is3DMap = is3DMap;
		
		// On indique que le type de map a changer
		fireModelGPSChanged(0);
	}

	/**
	 * Renvoie la taille en pixel d'un carre d'une map 2D
	 * @return
	 */
	public int getI_sizeSquareMap2D() {
		return i_sizeSquareMap2D;
	}

	/**
	 * Change la taille en pixel d'un carre d'une map 2D
	 * @param i_sizeSquareMap2D
	 */
	public void setI_sizeSquareMap2D(int i_sizeSquareMap2D) {
		
		// On memorise l'identifiant du zoom selectionne
		this.i_sizeSquareMap2D = i_sizeSquareMap2D;
		
		// On met a jour le zoom
		fireModelGPSChanged(1);
	}

	/**
	 * Change le type de map 3D
	 * @param i_typeMap3D
	 */
	public void setI_typeMap3D(int i_typeMap3D) {
		this.i_typeMap3D = i_typeMap3D;
		fireModelGPSChanged(7);
	}

	/**
	 * Renvoie le type de map 3D
	 * @return
	 */
	public int getI_typeMap3D() {
		return i_typeMap3D;
	}	


}
