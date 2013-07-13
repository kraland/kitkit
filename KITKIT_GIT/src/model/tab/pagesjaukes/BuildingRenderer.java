package model.tab.pagesjaukes;


import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class BuildingRenderer extends DefaultTableCellRenderer{
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,boolean hasFocus, int row, int column)
    {
		JLabel label = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);     
		label.setIcon(null);
				
		if(	table.getColumnName(column).equals("Nom") ||
			table.getColumnName(column).equals("Ville") ||
			table.getColumnName(column).equals("Province") ||
			table.getColumnName(column).equals("Proprietaire"))
		{
			label.setText(value.toString());
		}
		else if(table.getColumnName(column).equals("Valeur KI"))
		{
			String s_valeurKI = "";
						
			switch(Integer.valueOf(value.toString()))
			{
				case 1 :
					s_valeurKI = "entre 0 et 500";
					break;
				case 2 :
					s_valeurKI = "entre 500 et 1000";
					break;
				case 3 :
					s_valeurKI = "entre 1000 et 2500";
					break;
				case 4 :
					s_valeurKI = "entre 2500 et 5000";
					break;
				case 5 :
					s_valeurKI = "entre 5000 et 10000";
					break;
				case 6 :
					s_valeurKI = "au-delà de 10000";
					break;
				default :
					break;
			}
			
			label.setText(s_valeurKI);
		}
		
		label.setOpaque(true);
		label.setHorizontalAlignment(CENTER);
		
		return label;
    }
}
