package model.tab.pagesjaukes;


import controller.I_ControllerDialog;
import model.tab.TabModel;

@SuppressWarnings({ "serial", "unchecked" })
public class TabModelBuilding extends TabModel implements I_ControllerDialog{

	/**
	 * Titre des colonnes
	 */
	private static String[] listTitleColumn = 	{	"Niveau",
													"Type",
													"Nom",
													"Case",
													"Ville",
													"Province",
													"Proprietaire",
													"Orga. Comm.",
													"Valeur KI"};
	
	/**
	 * Type des filtres
	 * 1 : Filtre de liste de string definie
	 * 2 : Filtre de string indefinie
	 * 10 : Filtre d'entier
	 * 11 : Filtre de cases
	 * 12 : Filtre de booleen
	 * 13 : Filtre de valeur KI
	 */
	private static int[]	listTypeFilter	=	{	10,
													1,
													2,
													11,
													1,
													1,
													2,
													2,
													13};
	
	/**
	 * Constructeur de modele de tableau affichant les possessions d'un personnage
	 * ou d'une organisation commerciale. Dans le cas d'une organisation commerciale on renvoie
	 * les possessions de tous les personnages faisant partie de l'organisation
	 */
	public TabModelBuilding() {
		super(listTitleColumn, listTypeFilter);
	}

	/**
	 * Methode a redefinir dans chaque classe "extendant" d'un TabModel afin de redefinir le type du contenu 
	 * @return 
	 */
	public void setTypeContent()
	{
		defaultContent = new ContentBuilding();
	}

}
