package model.gps.calc;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class CalcThread extends Thread{

	
	/**
	 * ThreadManager
	 */
	CalcThreadManager threadManager;
	
	/**
	 * Liste de chemin qu'il reste a parcourir
	 */
	List<CalcWay> listWayToBrowse;
	
	/**
	 * Liste de chemin qui se termine par le point d'arrivee
	 */
	List<CalcWay> listWayWithPointStop;
	
	/**
	 * Nombre de chemin different a trouver
	 */
	private int i_nbDifferentCalcWay;
	
	/**
	 * 
	 * @param threadManager
	 * @param listWayToBrowse
	 * @param i_listWaySizeMinimum
	 */
	CalcThread(CalcThreadManager threadManager, List<CalcWay> listWayToBrowse, int i_nbDifferentCalcWay)
	{
		listWayWithPointStop = new ArrayList<CalcWay>();
		
		// On recupere les parametres
		this.i_nbDifferentCalcWay = i_nbDifferentCalcWay;
		this.threadManager = threadManager;
		this.listWayToBrowse = listWayToBrowse;
	}
	
	@Override
	public void run() {
		
		// Si on doit trouver un nombre fixe de different chemin
		// C'est a dire au moins le nombre de thread de calcul
		if(i_nbDifferentCalcWay > 0)
		{
			// Tant qu'un chemin a ete trouve
			// ou
			// Tant qu'il reste des chemins a parcourir on s'y atelle
			// et tant que le nombre de chemin a parcourir est inferieur strict au nombre de different chemin a trouver
			while(listWayToBrowse.size() > 0 && (listWayWithPointStop.size() > 0 || listWayToBrowse.size() < i_nbDifferentCalcWay))
			{
				findNextPoint();	
			}
			
			// Si le nombre de chemin limite est suffisant
			if(listWayToBrowse.size() == 0)
			{
				threadManager.setHasTerminatedFirstThread(true);
			}
		}
		// Il n'y a pas de nombre fixe de different chemin
		else
		{
			// Tant qu'il reste des chemins a parcourir on s'y atelle
			while(listWayToBrowse.size() > 0)
			{
				findNextPoint();	
			}
		}
		
		// On transmet la liste des chemins qui se termine par le point d'arrivee au threadmanager
		// Le threadmanager va s'occuper de cherher le meilleur chemin
		threadManager.addListWayPointStop(listWayWithPointStop);
		
	}
	
	@SuppressWarnings("static-access")
	private List<Point> listPointToAdd(Point pointCurrent)
	{
		int i_xPointCurrent = pointCurrent.x;
		int i_yPointCurrent = pointCurrent.y;
		
		// Schema representant l'indice des points suivants (0,1,2,3) et le dernier de la liste (X) 
		//	|0|
		//|3|X|1|
		//	|2|			
				
		// On cree la liste des points a ajouter    
		List<Point> listPointToAdd = new ArrayList<Point>();
				
		// On s'occupe du point 0
		if(i_yPointCurrent > 0)
		{
			listPointToAdd.add(new Point(i_xPointCurrent, i_yPointCurrent - 1));
		}
		// On s'occupe du point 1
		if(i_xPointCurrent < threadManager.getMap2D().get(0).size() - 1)
		{
			listPointToAdd.add(new Point(i_xPointCurrent + 1, i_yPointCurrent));
		}
		// On s'occupe du point 2
		if(i_yPointCurrent < threadManager.getMap2D().size() - 1)
		{
			listPointToAdd.add(new Point(i_xPointCurrent, i_yPointCurrent + 1));
		}
		// On s'occupe du point 3
		if(i_xPointCurrent > 0)
		{
			listPointToAdd.add(new Point(i_xPointCurrent - 1, i_yPointCurrent));
		}
		
		return listPointToAdd;
	}
	
	@SuppressWarnings("static-access")
	private void findNextPoint()
	{
		
		// Point courant qu'on va parcourir
		CalcWay currentWay = listWayToBrowse.get(0);
		
		// On recupere la liste de point de ce chemin
		List<Point> listPointCurrentWay = currentWay.getListPoint();
		
		// Recupere les coordonnes du dernier point de la liste de point du chemin 
		Point pointCurrent = listPointCurrentWay.get(listPointCurrentWay.size()-1);
		int i_xPointCurrent = (int) pointCurrent.getX();
		int i_yPointCurrent = (int) pointCurrent.getY();

		// On recupere les coordonnes du point d'arrivee
		int i_xPointStop = threadManager.getPointStop().x;
		int i_yPointStop = threadManager.getPointStop().y;
			
		// Si le point courant est le point d'arrivee
		if(i_xPointCurrent == threadManager.getPointStop().x && i_yPointCurrent == threadManager.getPointStop().y)
		{
			// Si le nombre de PdV pour se rendre au point courant est inferieur ou egale au nombre de PdV du meilleur chemin du point d'arret
			if(currentWay.getD_nbPdV() <= threadManager.getD_BestWay(i_xPointStop, i_yPointStop))
			{		
				// On ajoute le chemin courant a la liste de chemin avec en dernier point le point d'arrivee
				listWayWithPointStop.add(currentWay);
			}
		}
		else
		{
			//    Si le nombre de PdV pour se rendre au point courant est inferieur ou egale au nombre de PdV du meilleur chemin du point courant
			// et si le nombre de PdV pour se rendre au point courant est inferieur strict au nombre de PdV du meilleur chemin du point d'arret
			if(	currentWay.getD_nbPdV() <= threadManager.getD_BestWay(i_xPointCurrent, i_yPointCurrent) &&
				currentWay.getD_nbPdV() < threadManager.getD_BestWay(i_xPointStop, i_yPointStop))
			{
				// On parcourt les points a ajouter
				for(Point ptToAdd : listPointToAdd(pointCurrent))
				{
					// Variable temporaire de l'eventuel futur chemin a ajoute au sein de la liste
					CalcWay 		calcWayTmp;
					List<Point>		listPointTmp = new ArrayList<Point>(listPointCurrentWay);
					List<Integer>	listTypeTmp = new ArrayList<Integer>();
					double 			d_nbPdVTmp;
					
					// On ajoute le nombre de pdv pour ce rendre jusqu'au nouveau point a ajouter  
					d_nbPdVTmp = currentWay.getD_nbPdV() + calculatePdv(threadManager.getMap2D().get(i_yPointCurrent).get(i_xPointCurrent),threadManager.getMap2D().get(ptToAdd.y).get(ptToAdd.x));
						
					// Si le nombre de pdv qu'on vient de calculer est inferieur a celui du meilleur chemin
					if(d_nbPdVTmp > 0 && d_nbPdVTmp < threadManager.getD_BestWay(ptToAdd.x, ptToAdd.y) && d_nbPdVTmp < threadManager.getD_BestWay(i_xPointStop, i_yPointStop))
					{
						// On memorise le meilleur temps au sein de la map de meilleur temps
						threadManager.setD_BestWay(ptToAdd.x, ptToAdd.y, d_nbPdVTmp);
						
						// On ajoute le point a la liste
						listPointTmp.add(new Point(ptToAdd.x, ptToAdd.y));
						
						// On cree le nouveau chemin a parcourir
						calcWayTmp = new CalcWay(listPointTmp, listTypeTmp, d_nbPdVTmp);
						
						// On ajoute le chemin a la liste de chemin a parcourir
						listWayToBrowse.add(calcWayTmp);
					}
				}
			}
		}
		
		// On supprime le point courant de la liste
		listWayToBrowse.remove(0);
	}

		
	/**
	 * Calcul le nombre de pdv   
	 */
	private double calculatePdv(int i_typeCase1, int i_typeCase2)
	{
		return (getPdVCase(i_typeCase1) + getPdVCase(i_typeCase2))*10;
	}
	
	/**
	 * Renvoie le nombre de pdv assimile a une case
	 */
	private double getPdVCase(int i_typeCase)
	{
		if(100 == i_typeCase)
		{
			return 0.02;
		}
		else if(101 == i_typeCase)
		{
			return 0.04;
		}
		else if(102 == i_typeCase)
		{
			return 0.04;
		}
		else if(103 == i_typeCase)
		{
			return -5000;
		}
		else if(104 == i_typeCase)
		{
			return -5000;
		}
		else if(105 == i_typeCase)
		{
			return 0.04;
		}
		else if(106 == i_typeCase)
		{
			return 0.02;
		}
		else if(107 == i_typeCase)
		{
			return 0.02;
		}
		if(108 == i_typeCase)
		{
			return 0.02;
		}
		else if(110 == i_typeCase)
		{
			return 0.01;
		}
		else if(111 == i_typeCase)
		{
			return 0.01;			
		}
		else if(112 == i_typeCase)
		{
			return 0.01;			
		}
		else if(113 == i_typeCase)
		{
			return 0.01;			
		}
		else if(114 == i_typeCase)
		{
			return 0.01;			
		}
		else if(115 == i_typeCase)
		{
			return 0.01;			
		}
		else if(116 == i_typeCase)
		{
			return 0.01;			
		}
		else if(117 == i_typeCase)
		{
			return 0.01;			
		}
		else if(118 == i_typeCase)
		{
			return 0.01;			
		}
		else if(119 == i_typeCase)
		{
			return 0.01;			
		}
		else if(120 == i_typeCase)
		{
			return 0.01;			
		}
		else if(121 == i_typeCase)
		{
			return 0.01;			
		}
		else if(122 == i_typeCase)
		{
			return 0.01;			
		}
		else if(131 == i_typeCase)
		{
			return 0.04;			
		}
		else if(132 == i_typeCase)
		{
			return 0.04;			
		}
		else if(133 == i_typeCase)
		{
			return 0.04;			
		}
		else if(141 == i_typeCase)
		{
			return 0.02;			
		}
		else if(142 == i_typeCase)
		{
			return 0.02;				
		}
		else if(143 == i_typeCase)
		{
			return 0.02;				
		}
		else if(144 == i_typeCase)
		{
			return 0.02;				
		}
		else if(145 == i_typeCase)
		{
			return 0.02;				
		}
		else if(146 == i_typeCase)
		{
			return 0.02;				
		}
		else if(147 == i_typeCase)
		{
			return 0.02;				
		}
		else
		{
			return 0.02;				
		}
	}
	
	
	/**
	 * Booleen indiquant si le point place en parametre est une case d'eau
	 * @param pointToFind
	 * @return
	 */
	@SuppressWarnings("static-access")
	public boolean isWater(Point pointToFind)
	{
		// Si le string associe a ce point est 103 ou 104 c'est une case d'eau
		if(threadManager.getMap2D().get(pointToFind.y).get(pointToFind.x).equals("103") || threadManager.getMap2D().get(pointToFind.y).get(pointToFind.x).equals("104"))
		{
			// On renvoie vrai
			return true;
		}
		
		// On renvoie faux		
		return false;
	}
}
