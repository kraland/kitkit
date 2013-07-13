package ihm.filter;

import ihm.IHMTools;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import model.filter.Filter;
import model.filter.FilterStringUndefined;

@SuppressWarnings("serial")
public class IHMFilterStringUndefined extends IHMFilter{

	/**
	 * JTextField
	 */
	private JTextField jTextField;
	
	/**
	 * Constructeur
	 * @param filter
	 * @param frame
	 */
	public IHMFilterStringUndefined(Filter filter, JFrame frame) {
		
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
		
		jTextField = new JTextField(((FilterStringUndefined)filter).getS_Filter());
				
		bH = Box.createHorizontalBox();
		bH.add(new JLabel("Filtre à appliquer :"));
		bH.add(Box.createHorizontalStrut(5));
		bH.add(jTextField);
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
			
		// On cree la liste d'objet a renvoyer
		List<Object> listObjectToKeep = new ArrayList<Object>();
		listObjectToKeep.add(jTextField.getText());
		filter.keepParametersFilter(listObjectToKeep);
	}
}
