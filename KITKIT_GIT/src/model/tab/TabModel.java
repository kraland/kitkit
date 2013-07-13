package model.tab;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

import model.filter.Filter;
import model.filter.FilterBoolean;
import model.filter.FilterCase;
import model.filter.FilterInteger;
import model.filter.FilterListStringDefined;
import model.filter.FilterStringUndefined;

import controller.Controller;
import controller.I_ControllerDialog;


@SuppressWarnings("serial")
public abstract class TabModel extends AbstractTableModel implements I_ControllerDialog{
	
	/**
	 * Liste de "contenu" dans la JTable. Un "contenu" representant une ligne du tableau
	 */
	private List<I_DefaultContent> listObject;
	
	/**
	 * Type de contenu du modele
	 */
	protected I_DefaultContent defaultContent;
		
	/**
	 * Liste des titres des colonnes
	 */
	private String[] listTitleColumn;
	
	/**
	 * Liste de type de filtre
	 */
	private int[] listTypeFilter;
	
	/**
	 * Liste de filtre
	 */
	private Filter[] listFilter;
	
	/**
	 * Controlleur de l'application
	 */
	private Controller ctrl;
	
	/**
	 * Constructeur de modele de tableau
	 * @param listTitleColumn
	 * @param listTypeFilter 
	 */
	public TabModel(String[] listTitleColumn, int[] listTypeFilter)
	{
		// On memorise la liste des titres des colonnes
		this.listTitleColumn = listTitleColumn;
		
		// On memorise la liste de type de filtre
		this.listTypeFilter = listTypeFilter;
		
		// On cree la liste d'objets present dans le tableau
		listObject = new ArrayList<I_DefaultContent>();
		
		// On cree la liste de filtre
		listFilter = new Filter[listTypeFilter.length];
		for(int i_idFilter = 0 ; i_idFilter < listTypeFilter.length ; i_idFilter++)
		{
			
			/**
			 * Type des filtres
			 * 1 : Filtre de liste de string definie
			 * 2 : Filtre de string indefinie
			 * 10 : Filtre d'entier
			 * 11 : Filtre de cases
			 * 12 : Filtre de booleen
			 */
			
			switch(listTypeFilter[i_idFilter])
			{
				case 1 :
					listFilter[i_idFilter] = new FilterListStringDefined(listTypeFilter[i_idFilter],i_idFilter,listTypeFilter.length,listTitleColumn[i_idFilter]);
					break;
				case 2 :
					listFilter[i_idFilter] = new FilterStringUndefined(listTypeFilter[i_idFilter],i_idFilter,listTypeFilter.length,listTitleColumn[i_idFilter]);
					break;
				case 10 :
					listFilter[i_idFilter] = new FilterInteger(listTypeFilter[i_idFilter],i_idFilter,listTypeFilter.length,listTitleColumn[i_idFilter]);
					break;
				case 11 :
					listFilter[i_idFilter] = new FilterCase(listTypeFilter[i_idFilter],i_idFilter,listTypeFilter.length,listTitleColumn[i_idFilter]);	
					break;
				case 12 :
					listFilter[i_idFilter] = new FilterBoolean(listTypeFilter[i_idFilter],i_idFilter,listTypeFilter.length,listTitleColumn[i_idFilter]);
					break;
				default :
					listFilter[i_idFilter] = new FilterListStringDefined(listTypeFilter[i_idFilter],i_idFilter,listTypeFilter.length,listTitleColumn[i_idFilter]);	
					break;
			}
		}
	}
	
	public void initFilter()
	{	
		for(int i_idFilter = 0 ; i_idFilter < listTypeFilter.length ; i_idFilter++)
		{

			listFilter[i_idFilter].setActive(false);
			
			/**
			 * Type des filtres
			 * 1 : Filtre de liste de string definie
			 * 2 : Filtre de string indefinie
			 * 10 : Filtre d'entier
			 * 11 : Filtre de cases
			 * 12 : Filtre de booleen
			 */
			
			switch(listTypeFilter[i_idFilter])
			{
				case 1 :
					
					((FilterListStringDefined)listFilter[i_idFilter]).setListStringFiltered(new ArrayList<String>());
					
					// Ville
					if(listTitleColumn[i_idFilter].equals("Ville")) {
						((FilterListStringDefined)listFilter[i_idFilter]).setListStringDefined(ctrl.getListCitySortedByName());
					}
					
					// Province
					else if(listTitleColumn[i_idFilter].equals("Province")) {
						((FilterListStringDefined)listFilter[i_idFilter]).setListStringDefined(ctrl.getListDistrictSortedByName());	
					}
					
					// Type
					else if(listTitleColumn[i_idFilter].equals("Type")) {
						((FilterListStringDefined)listFilter[i_idFilter]).setListStringDefined(ctrl.getListTypeBuildingSortedByName());	
					}
					
					// Sinon
					else {
						System.err.println("Erreur");
					}
					
					break;
				case 2 :
					((FilterStringUndefined)listFilter[i_idFilter]).setS_Filter("");
					break;
				case 10 :
					((FilterInteger)listFilter[i_idFilter]).setI_valueMin(0);
					((FilterInteger)listFilter[i_idFilter]).setI_valueMax(100);
					break;
				case 11 :
					((FilterCase)listFilter[i_idFilter]).setS_caseX("X");
					((FilterCase)listFilter[i_idFilter]).setS_caseY("Y");
					break;
				case 12 :
					((FilterBoolean)listFilter[i_idFilter]).setFilteringValue(false);
					break;
				default :
					System.err.println("Unknow filter");
					break;
			}
		}

	}
	
	/**
	* Retourne le titre de la colonne a l'indice specifie
	*/
	public String getColumnName(int col)
	{
		// On renvoie le nom de la colonne
	  return this.listTitleColumn[col];
	}

	/**
	 * Retourne le nombre de colonnes
	 */
	public int getColumnCount()
	{
		// On renvoie le nombre de colonnes
		return this.listTitleColumn.length;
	}
	
	/**
	 * Retourne le nombre de lignes
	 */
	public int getRowCount() {
		// On renvoie le nombre de ligne dans le tableau
		return this.listObject.size();
	}
	
	/**
	 * Retourne la valeur a l'emplacement specifie
	 */
	public Object getValueAt(int row, int col) {
		// Renvoie la valeur de la case demandee
		return this.listObject.get(row).getValueAt(col);
	}
	
	/**
	 * Defini la valeur a l'emplacement specifie
	 * @param value
	 * @param row
	 * @param col
	 */
	public void setValueAt(Object value, int row, int col) {
		// Change la valeur de la case demandee
		this.listObject.get(row).setValueAt(col, value);
	}
			
	/**
	* Retourne la classe de la donnee de la colonne
	* @param col
	*/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int col){
		//On retourne le type de la cellule a la colonne demandee
		return this.defaultContent.getClass(col);
	}

	/**
	 * Methode permettant de retirer une ligne du tableau
	 * @param position
	 */
	public void removeRow(int position){
		
		listObject.remove(position);
		//Cette methode permet d'avertir le tableau que la ligne a ete efface
		this.fireTableRowsDeleted(position,position);
	}
	
	/**
	 * Permet d'ajouter une ligne dans le tableau
	 * @param listObjectToAdd
	 */
	public boolean addRow(List<String> listObjectToAdd){
		
		boolean hasToAddRow = true;
			
		if(hasToAddRow)
		{		
			setTypeContent();
			defaultContent.add(listObjectToAdd);
	
			for(Filter filter : listFilter)
			{
				if(!filter.checkFilter(defaultContent.getRealListString()))
				{
					hasToAddRow = false;
					break;
				}
			}
			 if(hasToAddRow)
			 {
					listObject.add(defaultContent);
					
					//Cette methode permet d'avertir le tableau que les donnees ont ete modifiees et d'effectuer une mise a jour de la derniere ligne
					this.fireTableRowsInserted(listObject.size()-1, listObject.size()-1); 
			 }
		}
		
		return hasToAddRow;
		


	}
	
	/**
	 * Supprimme tous le contenu du tableau
	 */
	public void removeAll()
	{
		// On supprime toutes les lignes du tableau
		for(int i = getRowCount()-1 ; i >= 0 ; i--)
		{
			removeRow(i);
		}
	}
	
	/**
	 * Fonction indiquant si la cellule est editable
	 * @param row
	 * @param col
	 */
	public boolean isCellEditable(int row, int col){
		// Peu importe le tableau une case ne sera jamais editable
		return false;
	}
	
	/**
	 * Change le controlleur
	 * @param ctrl
	 */
	public void setController(Controller ctrl) {
		this.ctrl = ctrl;
	}

	/**
	 * Renvoie le controlleur
	 */
	public Controller getController() {
		return ctrl;
	}
	
	/**
	 * IMPORTANT
	 * Methode a redefinir dans chaque modele
	 */
	public abstract void setTypeContent();
	
	
	public String[] getListTitleColumn() {
		return listTitleColumn;
	}
	
	public Filter[] getListFilter() {
		return listFilter;
	}

	public void setListFilter(Filter[] listFilter) {
		this.listFilter = listFilter;
	}

}
