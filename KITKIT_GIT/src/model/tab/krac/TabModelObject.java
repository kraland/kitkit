package model.tab.krac;


import controller.I_ControllerDialog;
import model.tab.TabModel;

@SuppressWarnings({ "serial", "unchecked" })
public class TabModelObject extends TabModel implements I_ControllerDialog{

	/**
	 * Titre des colonnes
	 */
	private static String[] listTitleColumn = {	"Niveau",
												"Type",
												"Nom",
												"Case",
												"Ville",
												"Province",
												"Prix Vente",
												"Prix Achat",
												"Quantité",
												"Quantité Max.",
												"Fermé"};
	
	/**
	 * Type des filtres
	 * 1 : Filtre de liste de string definie
	 * 2 : Filtre de string indefinie
	 * 10 : Filtre d'entier
	 * 11 : Filtre de cases
	 * 12 : Filtre de booleen
	 */
	private static int[]	listTypeFilter	=	{	10,
													1,
													2,
													11,
													1,
													1,
													10,
													10,
													10,
													10,
													12};
	
	/**
	 * Constructeur de modele de tableau affichant les objets present dans tous les batiments
	 * situes dans la zone de recherche et correspondant aux criteres de recherche
	 */
	public TabModelObject() {
		super(listTitleColumn, listTypeFilter);
	}

	/**
	 * Methode a redefinir dans chaque classe "extendant" d'un TabModel afin de redefinir le type du contenu 
	 * @return 
	 */
	public void setTypeContent()
	{
		defaultContent = new ContentObject();
	}

}
