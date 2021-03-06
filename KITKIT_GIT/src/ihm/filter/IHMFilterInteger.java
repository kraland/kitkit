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
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.filter.Filter;
import model.filter.FilterInteger;

@SuppressWarnings("serial")
public class IHMFilterInteger extends IHMFilter implements ChangeListener {

	/**
	 * Spinner indiquant la valeur minimale
	 */
	private JSpinner jSpinnerValueMin;

	/**
	 * Spinner indiquant la valeur maximale
	 */
	private JSpinner jSpinnerValueMax;
	
	/**
	 * Valeur minimale temporaire
	 */
	private int i_valueMin;
	
	/**
	 * Valeur maximale temporaire
	 */
	private int i_valueMax;
	
	/**
	 * Constructeur
	 * @param filter
	 * @param frame
	 */
	public IHMFilterInteger(Filter filter, JFrame frame) {
		
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
		
		i_valueMin = ((FilterInteger)filter).getI_valueMin();
		jSpinnerValueMin = new JSpinner(new SpinnerNumberModel(i_valueMin,-2147483648,2147483647,1));
		jSpinnerValueMin.addChangeListener(this);

		
		i_valueMax = ((FilterInteger)filter).getI_valueMax();
		jSpinnerValueMax = new JSpinner(new SpinnerNumberModel(i_valueMax,-2147483648,2147483647,1));
		jSpinnerValueMax.addChangeListener(this);
		
		
		bH = Box.createHorizontalBox();
		bH.add(Box.createHorizontalStrut(5));
		bH.add(jSpinnerValueMin);
		bH.add(Box.createHorizontalStrut(5));
		bH.add(new JLabel("< Valeur <"));
		bH.add(Box.createHorizontalStrut(5));
		bH.add(jSpinnerValueMax);
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
		listObjectToKeep.add(i_valueMin);
		listObjectToKeep.add(i_valueMax);
		filter.keepParametersFilter(listObjectToKeep);
	}

	@Override
	public void stateChanged(ChangeEvent e)
	{
		// On regarde si la source de l'evenement et le spinner affichant le prix d'achat minimal	
		if(e.getSource() == jSpinnerValueMin)
		{
			i_valueMin = (Integer) jSpinnerValueMin.getValue();
			((SpinnerNumberModel)jSpinnerValueMax.getModel()).setMinimum((Integer)i_valueMin+1);
		}

		// On regarde si la source de l'evenement et le spinner affichant le prix d'achat maximal
		else if(e.getSource() == jSpinnerValueMax)
		{
			i_valueMax = (Integer) jSpinnerValueMax.getValue();
			((SpinnerNumberModel)jSpinnerValueMin.getModel()).setMaximum((Integer)i_valueMax-1);
		}

		// On recupere la valeur des deux spinners
		i_valueMin = (Integer) jSpinnerValueMin.getValue();
		i_valueMax = (Integer) jSpinnerValueMax.getValue();
		
	}

}
