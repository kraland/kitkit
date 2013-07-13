package model.gps;

import java.awt.Point;
import model.Model;
import model.TypeKI;
import model.content.City;
import model.content.District;

public class Polygon{

	/**
	 * Nom de la province dans laquelle se trouve ce point
	 */
	private String s_nameDistrict;
	
	/**
	 * Nom de la case compose de la facon suivante "Element [x,y]"
	 */
	private String s_nameInDistrict;
	
	/**
	 * Coordonnees en X des points du polygone
	 */
	private int[] i_tab_xPoints;
	
	/**
	 * Coordonnees en Y des points du polygone
	 */
	private int[] i_tab_yPoints;
	
	/**
	 * Booleen indiquant si le polygone est selectionne
	 */
	private boolean isSelected = false;
	
	/**
	 * Point 2D ou se trouve le polygone
	 */
	private Point point2D;
	
	/**
	 * Point 3D ou se trouve le polygone
	 */
	private Point point3D;

	/**
	 * Renvoie les coordonnees en X des points du polygone 
	 */
	public int[] getI_tab_xPoints() {
		return i_tab_xPoints;
	}

	/**
	 * Change les coordonnees en X des points du polygone 
	 */
	public void setI_tab_xPoints(int[] i_tab_xPoints) {
		this.i_tab_xPoints = i_tab_xPoints;
	}

	/**
	 * Renvoie les coordonnees en Y des points du polygone
	 */
	public int[] getI_tab_yPoints() {
		return i_tab_yPoints;
	}

	/**
	 * Change les coordonnees en Y des points du polygone  
	 */
	public void setI_tab_yPoints(int[] i_tab_yPoints) {
		this.i_tab_yPoints = i_tab_yPoints;
	}

	/**
	 * Renvoie le booleen indiquant si le polygone est selectionne
	 */
	public boolean isSelected() {
		return isSelected;
	}

	/**
	 * Change le booleen indiquant si le polygone est selectionne 
	 */
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	/**
	 * Renvoie le point 2D ou se trouve le polygone
	 */
	public Point getPoint2D() {
		return point2D;
	}

	/**
	 * Change le point 2D ou se trouve le polygone
	 */
	public void setPoint2D(Point point2D) {
		this.point2D = point2D;
	}

	/**
	 * Renvoie le point 3D ou se trouve le polygone 
	 */
	public Point getPoint3D() {
		return point3D;
	}
	
	/**
	 * Change le point 3D ou se trouve le polygone 
	 */
	public void setPoint3D(Point point3D) {
		this.point3D = point3D;
	}

	
	/**
	 * Recherche le nom du polygone
	 * @param model
	 */
	public void searchNamePolygon(Model model)
	{
		// On va chercher quel est la ligne de la province
		int i_yDistrict = point2D.y / model.getI_nbVerticalSquareInDistrict();
		int i_xDistrict;
		
		int i_yInProvince = point2D.y % model.getI_nbVerticalSquareInDistrict();
		int i_xInProvince = point2D.x;
		
		// On regarde si la province est sur une ligne paire (0, 2, 4, 6)
		if((i_yDistrict % 2) == 0)
		{
			i_xDistrict = point2D.x / model.getI_nbHorizontalSquareInDistrict();
		}		
		else
		{
			i_xDistrict = (point2D.x - model.getI_nbHorizontalSquareInDistrict()/2) / model.getI_nbHorizontalSquareInDistrict();
			i_xInProvince = i_xInProvince - model.getI_nbHorizontalSquareInDistrict()/2;
		}
		
		i_xInProvince = i_xInProvince % model.getI_nbHorizontalSquareInDistrict();

		// On parcourt le modele pour trouver la province correspondante
		for(District district : model.getListDistrict())
		{
			// Si il s'agit de la bonne province
			if(i_xDistrict == district.getI_x() && i_yDistrict == district.getI_y())
			{
				setS_nameDistrict(district.getS_name());
				setS_nameInDistrit(getS_Description(district.getMap2D().get(i_yInProvince).get(i_xInProvince), district, i_xInProvince, i_yInProvince) + " [" + i_xInProvince + "," + i_yInProvince + "]");
				break;
			}
		}
		
	}

	/**
	 * Renvoie la desription du polygone
	 * @param s_nameCase
	 * @param district
	 * @param i_xInProvince
	 * @param i_yInProvince
	 * @return
	 */
	public String getS_Description(int i_typeCase, District district, int i_xInProvince, int i_yInProvince)
	{
		// Si ce n'est pas une ville, c'est a dire :
		// si le type de case est different de 51, de 52, et de 53
		if(i_typeCase != 51 && i_typeCase != 52 && i_typeCase != 53)
		{
			return TypeKI.getInstance().getNamePolygonDistrict(i_typeCase);			
		}
		else
		{
			// On parcourt chaque ville de la province
			for(City eachCity : district.getListCity())
			{
				// On verifie les coordonnees x, y
				if(eachCity.getI_x() == i_xInProvince && eachCity.getI_y() == i_yInProvince)
				{
					// On renvoie le nom de la ville
					return eachCity.getS_name();
				}
			}	
		}
		
		return "Inconnu au bataillon";
		
	}
	
	/**
	 * Renvoie le nom de la province ou se trouve le polygone
	 * @return
	 */
	public String getS_nameDistrict() {
		return s_nameDistrict;
	}

	/**
	 * Change le nom de la province ou se trouve le polygone
	 * @param s_nameDistrict
	 */
	public void setS_nameDistrict(String s_nameDistrict) {
		this.s_nameDistrict = s_nameDistrict;
	}

	/**
	 * Renvoie le nom du polygone au sein de la province
	 * @return
	 */
	public String getS_nameInDistrict() {
		return s_nameInDistrict;
	}

	/**
	 * Change le nom du polygone au sein de la provine
	 * @param s_nameInDistrict
	 */
	public void setS_nameInDistrit(String s_nameInDistrict) {
		this.s_nameInDistrict = s_nameInDistrict;
	}
}
