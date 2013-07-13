package model.gps.calc;

import java.awt.Point;
import java.util.List;

public class CalcWay {
	
	/**
	 * Liste de chaque points de ce chemin
	 */
	private List<Point> listPoint;
	
	/**
	 * Liste de type de moyen de deplacement de ce chemin
	 */
	private List<Integer> list_i_type;
		
	/**
	 * PdV propre a ce chemin
	 */
	private double d_nbPdV;
		
	/**
	 * Conctructeur de chemin contenant :
	 * - la liste de point parcourus dans ce chemin
	 * - la liste de type de moyen de deplacement dans ce chemin
	 * - le nombre de pdv propre a ce chemin
	 */
	public CalcWay(List<Point> listPoint, List<Integer> listType, double d_nbPdV)
	{	
		// Liste de point de ce chemin
		this.setListPoint(listPoint);
		
		//Liste de type de moyen de deplacement de ce chemin
		this.setList_i_type(listType);
		
		// Point d'arret
		this.setD_nbPdV(d_nbPdV);
	}

	/**
	 * Change la liste de points de ce chemin
	 * @param listPoint
	 */
	public void setListPoint(List<Point> listPoint) {
		this.listPoint = listPoint;
	}

	/**
	 * Renvoie la liste de points de ce chemin
	 * @return
	 */
	public List<Point> getListPoint() {
		return listPoint;
	}

	/**
	 * Change le nombre de PdV propre a ce chemin
	 * @param f_nbPdV
	 */
	public void setD_nbPdV(double d_nbPdV) {
		this.d_nbPdV = d_nbPdV;
	}

	/**
	 * Renvoie le nombre de PdV propre a ce chemin
	 * @return
	 */
	public double getD_nbPdV() {
		return d_nbPdV;
	}

	/**
	 * Renvoie la liste de moyen de deplacement de ce chemin
	 * @return
	 */
	public List<Integer> getList_i_type() {
		return list_i_type;
	}

	/**
	 * Change la liste de moyen de deplacement de ce chemin
	 * @param list_i_type
	 */
	public void setList_i_type(List<Integer> list_i_type) {
		this.list_i_type = list_i_type;
	}
	
}
