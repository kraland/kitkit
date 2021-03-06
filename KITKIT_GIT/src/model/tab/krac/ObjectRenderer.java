package model.tab.krac;

import ihm.ConfigIcon;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class ObjectRenderer extends DefaultTableCellRenderer {
   
	@SuppressWarnings("static-access")
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,boolean hasFocus, int row, int column)
    {
		JLabel label = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);     
		label.setIcon(null);
		
		if(	table.getColumnName(column).equals("Nom") ||
			table.getColumnName(column).equals("Ville") ||
			table.getColumnName(column).equals("Province"))
		{
			label.setText(value.toString());
		}
		else if(table.getColumnName(column).equals("Fermé"))
		{
			label.setText("");
			if(value.toString().equals("true")){label.setIcon(ConfigIcon.getInstance().CLOSED);}
			else{label.setIcon(ConfigIcon.getInstance().EMPTY_16);}
		}
		
		label.setOpaque(true);
		label.setHorizontalAlignment(CENTER);
		
		return label;
    }
}