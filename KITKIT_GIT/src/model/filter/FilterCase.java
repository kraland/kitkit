package model.filter;

import java.util.List;

public class FilterCase extends Filter{

	/**
	 * Filtre de case sur X
	 */
	private String s_caseX = "X";
	
	/**
	 * Filtre de case sur Y
	 */
	private String s_caseY = "Y";
	
	/**
	 * Constructeur de filtre de case
	 * @param i_type
	 * @param i_idFilter
	 * @param i_nbFilter
	 * @param s_name
	 */
	public FilterCase(int i_type, int i_idFilter, int i_nbFilter, String s_name) {
		super(i_type, i_idFilter, i_nbFilter, s_name);
	}

	@Override
	public boolean checkSpecificFilter(String strToCheck) {
		
		// Si on a pas un filtre [X,Y]
		if(!(s_caseX.equals("X")&& s_caseY.equals("Y")))
		{
			// On split la chaine a verifier
			String str[]=strToCheck.substring(1,strToCheck.length()-1).split(",");
			
			// Si on a pas de X en abscisse
			// On regarde si la valeur respecte le filtre
			if(!s_caseX.equals("X"))
			{
				int i_caseX = Integer.valueOf(s_caseX);
				if(i_caseX != Integer.valueOf(str[0]))
				{
					return false;
				}
			}
			
			// Si on a pas de Y en ordonnee
			// On regarde si la valeur respecte le filtre
			if(!s_caseY.equals("Y"))
			{
				int i_caseY = Integer.valueOf(s_caseY);
				if(i_caseY != Integer.valueOf(str[1]))
				{
					return false;
				}
			}
		}
		
		return true;
	}

	@Override
	public void keepParametersFilter(List<Object> listParameters)
	{
		//////////////////////////////
		// 		List<Object> :		//
		//////////////////////////////
		// - Choix de la case X		//
		// - Choix de la case Y		//
		//////////////////////////////
		
		if(listParameters.size() == 2)
		{
			setS_caseX((String)listParameters.get(0));
			setS_caseY((String)listParameters.get(1));
		}
		else
		{
			System.out.println("Erreur de taille");
		}
	}

	/**
	 * Renvoie la case suivant X choisie
	 * @return
	 */
	public String getS_caseX() {
		return s_caseX;
	}

	/**
	 * Change la case suivant X choisie
	 * @param s_caseX
	 */
	public void setS_caseX(String s_caseX) {
		this.s_caseX = s_caseX;
	}

	/**
	 * Renvoie la case suivant Y choisie
	 * @return
	 */
	public String getS_caseY() {
		return s_caseY;
	}

	/**
	 * Change la case suivant Y choisie
	 * @param s_caseY
	 */
	public void setS_caseY(String s_caseY) {
		this.s_caseY = s_caseY;
	}

}
