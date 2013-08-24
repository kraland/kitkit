package ihm;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class TableColumnHider {
    
    /**
     * Modele de colonne du tableau
     */
    private TableColumnModel tcm;
    

    /**
     * Colonnes cachees
     */
    @SuppressWarnings("rawtypes")
	private Map hiddenColumns;

    /**
     * Constructeur de "cacheur" de colonnes de tableau
     * @param table
     */
    @SuppressWarnings("rawtypes")
	public TableColumnHider(JTable table) {
        tcm = table.getColumnModel();
        hiddenColumns = new HashMap();
    }

    /**
     * Cache une colonne
     * @param columnName
     */
    @SuppressWarnings("unchecked")
	public void hide(String columnName) {
        int index = tcm.getColumnIndex(columnName);
        TableColumn column = tcm.getColumn(index);
        hiddenColumns.put(columnName, column);
        hiddenColumns.put(":" + columnName, new Integer(index));
        tcm.removeColumn(column);
    }

    /**
     * Affiche une colonne
     * @param columnName
     */
    public void show(String columnName) {
        Object o = hiddenColumns.remove(columnName);
        if (o == null) {
            return;
        }
        tcm.addColumn((TableColumn) o);
        o = hiddenColumns.remove(":" + columnName);
        if (o == null) {
            return;
        }
        int column = ((Integer) o).intValue();
        int lastColumn = tcm.getColumnCount() - 1;
        if (column < lastColumn) {
            tcm.moveColumn(lastColumn, column);
        }
    }
}