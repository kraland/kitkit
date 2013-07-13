package model.gps;

import java.util.EventListener;

public interface I_ModelGPSListener extends EventListener {
	
	/**
	 * Indique que le type de map (2D/3D) a change
	 * @param is3DMap
	 */
	public void typeMapChanged(boolean is3DMap);

	/**
	 * Indique que l'echelle de la map a change
	 * @param i_scale
	 * @param is3DMap
	 */
	public void scaleMapChanged(int i_scale, boolean is3DMap);
	
	/**
	 * Indique qu'un calcul est termine
	 * @param f_nbPdV
	 */
	public void calculDone(float f_nbPdV);

	/**
	 * Indique que la position de la souris sur la map a changee
	 * @param polygonMouse
	 */
	public void newPositionMouse(Polygon polygonMouse);
	
	/**
	 * Indique que le point de depart est change
	 * @param polygonBegin
	 */
	public void beginSelected(Polygon polygonBegin);

	/**
	 * Indique que le point d'arret est change
	 * @param polygonStop
	 */
	public void stopSelected(Polygon polygonStop);

	/**
	 * Indique qu'au moins un critere a change
	 */
	public void searchCriteriaChange();
	
	/**
	 * Indique que le type de map 3D a ete change
	 * @param i_typeMap3D
	 */
	public void changeTypeMap3D(int i_typeMap3D);
}
