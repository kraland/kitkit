package model.gps.calc;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import controller.Controller;

import model.MapDouble;

public class CalcThreadManager extends Thread{

	/**
	 * Controleur
	 */
	private Controller ctrl;
	
	/**
	 * Map2D d'entier qui contient le contenu de chaque case
	 */
	private static List<List<Integer>> map2D;
	
	/**
	 * Map de double contenant les valeurs du meilleur parcourt pour aller a la case X
	 * Par exemple si on a un chemin permettant d'aller au point (x,y) qui dure 8pdV
	 * Et qu'on en calcule un autre faisant 9 PdV ca ne sert a rien de le rajouter a la liste
	 * donc la liste contiendra 8PdV. Si par la suite on trouve un chemin permettant d'aller
	 * au point (x,y) qui dure 7 PdV alors on remplacera 8 PdV par 7PdV
	 */
	private MapDouble map_d_bestWay;
						
	/**
	 * Nombre de thread maximum a lance en parallele (sans compter le manager)
	 */
	private int i_nbThread;
	
	/**
	 * Point de depart
	 */
	private Point pointBegin;
	
	/**
	 * Point d'arrivee
	 */
	private Point pointStop;
	
	/**
	 * Liste de liste de chemin qui finissent par le point d'arrivee
	 */
	List<List<CalcWay>> list_listWayWithPointStop;
	
	/**
	 * Liste de chemin a parcourir
	 */
	List<CalcWay> listWayToBrowse;
	
	/**
	 * Liste de thread de calcul
	 */
	List<Thread> listCalcThread;
	
	/**
	 * Booleen indiquant si le premier thread de calcul est suffisant
	 */
	boolean hasTerminatedFirstThread;
	
	/**
	 * Booleen indiquant si une exception a ete rencontree
	 */
	boolean hasDetectedException;
	
	/**
	 * Manageur de thread qui va s'occuper de diviser le travail au i_nbThread qu'il gere en parallele
	 * @param polygonBegin
	 * @param polygonEnd
	 * @param i_nbThread
	 */
	@SuppressWarnings("static-access")
	public CalcThreadManager(Controller ctrl, int i_nbThread)
	{
		this.setMap2D(ctrl.getModel().getMap2D().getMap());
		this.pointBegin = ctrl.getPolygonBegin().getPoint2D();
		this.pointStop = ctrl.getPolygonEnd().getPoint2D();
		this.i_nbThread = i_nbThread;
		map_d_bestWay = new MapDouble(ctrl.getModel().getI_widthMap(),ctrl.getModel().getI_heightMap(),50000);
		this.ctrl = ctrl;
		hasDetectedException = false;
	}
	
	@Override
	public void run() {

		// On initialise la liste de liste de chemin qui finissent par le point d'arrivee
		list_listWayWithPointStop = new ArrayList<List<CalcWay>>();
		
		// On initialise la liste de chemin a parcourir
		listWayToBrowse = new ArrayList<CalcWay>();

		// On initialise la liste de thread qui calcule les trajets
		listCalcThread = new ArrayList<Thread>();
		
		// On cree le premier chemin
		List<Point> firstListPoint = new ArrayList<Point>();
		firstListPoint.add(new Point(pointBegin));
		
		List<Integer> firstListType = new ArrayList<Integer>();
		firstListType.add(-1);
		
		CalcWay firstWay = new CalcWay(firstListPoint, firstListType, 0);

		// On ajoute le premier chemin
		listWayToBrowse.add(firstWay);
		
		// On parcourt une premiere fois jusqu'a avoir au moins i_nbThread chemin a parcourir
		CalcThread calcTmp= new CalcThread(this, listWayToBrowse, 2*i_nbThread);
		calcTmp.run();
			
		// On attend que le thread de calcul se termine
		try{calcTmp.join();}
		catch (InterruptedException e)
		{
			e.printStackTrace();
			hasDetectedException = true;
		}
						
		// Si on a pas detecte d'exception
		if(!hasDetectedException)
		{
			
			if(!hasTerminatedFirstThread)
			{
				
				// On calcule le nombre de chemin qu'un thread doit calculer
				int i_nbWayPerThread = listWayToBrowse.size() / i_nbThread;

				// On parcourt chaque thread
				for(int i_idThread = 0 ; i_idThread < i_nbThread ; i_idThread++)
				{
					// On initialise une liste qui calcule les chemins temporaire
					List<CalcWay> listWayToBrowseTmp = new ArrayList<CalcWay>();
					
					for(int i_idTmp = i_idThread * i_nbWayPerThread ; i_idTmp < (i_idThread+1) * i_nbWayPerThread ; i_idTmp++)
					{
						// On ajoute les chemins l'un apres l'autre
						listWayToBrowseTmp.add(listWayToBrowse.get(i_idTmp));
					}
					
					// Si il s'agit du dernier thread on lui attribue les chemins en surplus
					if(i_idThread == (i_nbThread - 1))
					{
						for(int i_idTmp = (i_idThread+1) * i_nbWayPerThread ; i_idTmp < listWayToBrowse.size() ; i_idTmp++)
						{
							// On ajoute les chemins l'un apres l'autre
							listWayToBrowseTmp.add(listWayToBrowse.get(i_idTmp));
						}
					}
					
					// On cree le thread qui calcule les trajets
					CalcThread calcThreadTmp = new CalcThread(this, listWayToBrowseTmp, 0);
					
					// On ajoute le thread qui calcule les trajets
					listCalcThread.add(calcThreadTmp);
					calcThreadTmp.run();
					
				}
				
				// On attend que tous les threads soient termines
				for(Thread calcThread : listCalcThread)
				{
					try 
					{
						calcThread.join();
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
						hasDetectedException = true;
					}
				}
			}
			
			// Si on a pas detecte d'exception
			if(!hasDetectedException)
			{
				CalcWay  calcWay = null;
								
				// On parcourt la liste de liste de chemin qui finissent par le point d'arrivee 
				for(List<CalcWay>  listWayWithPointStop : list_listWayWithPointStop)
				{
					for(CalcWay  calcWayTmp : listWayWithPointStop)
					{
						if(calcWay == null)
						{
							calcWay = calcWayTmp;
						}
						else
						{
							if(calcWayTmp.getD_nbPdV() < calcWay.getD_nbPdV())
							{
								calcWay = calcWayTmp;
							}
						}
					}	
				}
				
				if(calcWay != null)
				{				
					// Change la liste de point a dessiner
					ctrl.setListPointToDraw(calcWay.getListPoint());
					
					// Change le nombre de pdv
					ctrl.setF_pdvWay((float)round(calcWay.getD_nbPdV(),2));	
				}
				else
				{
					// Change la liste de point a dessiner
					ctrl.setListPointToDraw(null);
					
					// Change le nombre de pdv
					ctrl.setF_pdvWay(-1);
				}
			}
		}
	}
	
	/**
	 * Methode permettant d'arrondir un double
	 * @param value
	 * @param dec
	 * @return
	 */
	public static double round(double value, int dec) {
		double mult = Math.pow (10.0, (double)dec);
		return Math.round(value * mult) / mult;
		}

	/**
	 * Renvoie le booleen indiquant si le premier thread de calcul est suffisant
	 * @return
	 */
	public boolean hasTerminatedFirstThread() {
		return hasTerminatedFirstThread;
	}

	/**
	 * Change le booleen indiquant si le premier thread de calcul est suffisant
	 * @param hasTerminatedFirstThread
	 */
	public void setHasTerminatedFirstThread(boolean hasTerminatedFirstThread) {
		this.hasTerminatedFirstThread = hasTerminatedFirstThread;
	}

	/**
	 * Change la map 2D
	 * @param map2D
	 */
	public static void setMap2D(List<List<Integer>> map2D) {
		CalcThreadManager.map2D = map2D;
	}

	/**
	 * Renvoie la map 2D
	 * @return
	 */
	public static List<List<Integer>> getMap2D() {
		return map2D;
	}

	/**
	 * Change le point d'arret
	 * @param pointStop
	 */
	public void setPointStop(Point pointStop) {
		this.pointStop = pointStop;
	}

	/**
	 * Renvoie le point d'arret
	 * @return
	 */
	public Point getPointStop() {
		return pointStop;
	}

	/**
	 * Ajoute une liste de chemin se terminant par le point d'arret
	 * @param listWayWithPointStop
	 */
	public void addListWayPointStop(List<CalcWay> listWayWithPointStop) {
		list_listWayWithPointStop.add(listWayWithPointStop);
	}

	/**
	 * Change la map de meilleurs chemins
	 * @param map_d_bestWay
	 */
	public synchronized void setMap_d_bestWay(MapDouble map_d_bestWay) {
		this.map_d_bestWay = map_d_bestWay;
	}

	/**
	 * Renvoie la map de meilleurs chemins
	 * @return
	 */
	public synchronized MapDouble getMap_d_bestWay() {
		return map_d_bestWay;
	}

	/**
	 * Renvoie le meilleur chemin
	 * @param i_xPoint
	 * @param i_yPoint
	 * @return
	 */
	public synchronized double getD_BestWay(int i_xPoint, int i_yPoint)
	{
		return map_d_bestWay.getMap()[i_xPoint][i_yPoint];
	}

	/**
	 * Change le meilleur chemin
	 * @param i_xPoint
	 * @param i_yPoint
	 * @param d_nbPdV
	 */
	public synchronized void setD_BestWay(int i_xPoint, int i_yPoint, double d_nbPdV) {
		map_d_bestWay.getMap()[i_xPoint][i_yPoint] = d_nbPdV;
	}
}
