package ihm.filter;

import ihm.IHMTools;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;

import model.filter.Filter;
import model.filter.FilterCase;

@SuppressWarnings("serial")
public class IHMFilterCase extends IHMFilter {

	/**
	 * Combobox indiquant le filtre de la case X
	 */
	@SuppressWarnings("rawtypes")
	private JComboBox jComboBoxCaseX;

	/**
	 * Combobox indiquant le filtre de la case Y
	 */
	@SuppressWarnings("rawtypes")
	private JComboBox jComboBoxCaseY;
	
	/**
	 * filtre de la case X
	 */
	private String s_caseX;
	
	/**
	 * filtre de la case Y
	 */
	private String s_caseY;
	
	/**
	 * Constructeur
	 * @param filter
	 * @param frame
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public IHMFilterCase(Filter filter, JFrame frame) {
		// On cree la JDialog...
		super(filter, frame);
		setLayout(new BorderLayout());
		
		
		// On cree la partie haute de la JDialog
		Box bV = Box.createVerticalBox();
		Box bH = Box.createHorizontalBox();
		bH.add(jButtonActivation);
		bH.add(Box.createHorizontalStrut(5));
		bH.add(new JLabel(filter.getS_name()));
		bH.add(Box.createHorizontalGlue());
		bH.setMaximumSize(new Dimension(3000,16));
		bV.add(bH);
		
		// On cree le separateur
		JSeparator separator = IHMTools.getInstance().addHorizontalSeparator(bV,5,5);
		separator.setMaximumSize(new Dimension(3000,16));
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Partie a redefinir //////////////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		Vector<String> comboBoxItemsCaseX = new Vector<String>();
		DefaultComboBoxModel modelCaseX = new DefaultComboBoxModel(comboBoxItemsCaseX);
		jComboBoxCaseX = new JComboBox(modelCaseX);
		
		comboBoxItemsCaseX.add("X");
		for(int i_idItemCaseX = 0 ; i_idItemCaseX < 20 ; i_idItemCaseX++)
		{
			comboBoxItemsCaseX.add(String.valueOf(i_idItemCaseX));
		}
		
		if(((FilterCase)filter).getS_caseX().equals("X"))
		{
			jComboBoxCaseX.setSelectedIndex(0);
		}
		else
		{
			jComboBoxCaseX.setSelectedIndex(Integer.valueOf(((FilterCase)filter).getS_caseX()) + 1);
		}
		
		
		Vector<String> comboBoxItemsCaseY = new Vector<String>();
		DefaultComboBoxModel modelCaseY = new DefaultComboBoxModel(comboBoxItemsCaseY);
		jComboBoxCaseY = new JComboBox(modelCaseY);
		
		comboBoxItemsCaseY.add("Y");
		for(int i_idItemCaseY = 0 ; i_idItemCaseY < 13 ; i_idItemCaseY++)
		{
			comboBoxItemsCaseY.add(String.valueOf(i_idItemCaseY));
		}
		
		if(((FilterCase)filter).getS_caseY().equals("Y"))
		{
			jComboBoxCaseY.setSelectedIndex(0);
		}
		else
		{
			jComboBoxCaseY.setSelectedIndex(Integer.valueOf(((FilterCase)filter).getS_caseY()) + 1);
		}
		
		
		
		bH = Box.createHorizontalBox();
		bH.add(new JLabel("Case = ["));
		bH.add(Box.createHorizontalStrut(5));
		bH.add(jComboBoxCaseX);
		bH.add(Box.createHorizontalStrut(5));
		bH.add(new JLabel(","));
		bH.add(Box.createHorizontalStrut(5));
		bH.add(jComboBoxCaseY);
		bH.add(Box.createHorizontalStrut(5));
		bH.add(new JLabel("]"));
		bH.add(Box.createHorizontalGlue());
		bH.setMaximumSize(new Dimension(3000,16));
		bV.add(bH);
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Fin partie a redefinir //////////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		
		// On cree la partie du bas
		bV.add(Box.createVerticalGlue());
		bH = Box.createHorizontalBox();
		bH.add(Box.createHorizontalGlue());
		bH.add(jButtonCancel);
		bH.add(Box.createHorizontalStrut(15));
		bH.add(jButtonValidate);
		bH.add(Box.createHorizontalGlue());
		bH.setMaximumSize(new Dimension(3000,16));
		bV.add(bH);
		
		// On ajoute la boite
		add(bV,BorderLayout.CENTER);
	}

	@Override
	protected void keepParametersFilter()
	{
		// On change le statut du filtre
		filter.setActive(isActive);
			
		s_caseX = (String)jComboBoxCaseX.getSelectedItem();
		s_caseY = (String)jComboBoxCaseY.getSelectedItem();
		
		// On cree la liste d'objet a renvoyer
		List<Object> listObjectToKeep = new ArrayList<Object>();
		listObjectToKeep.add(s_caseX);
		listObjectToKeep.add(s_caseY);
		filter.keepParametersFilter(listObjectToKeep);
	}

}
