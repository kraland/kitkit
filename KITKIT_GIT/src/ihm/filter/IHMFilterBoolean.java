package ihm.filter;

import ihm.ConfigIcon;
import ihm.IHMTools;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;

import model.filter.Filter;
import model.filter.FilterBoolean;

@SuppressWarnings("serial")
public class IHMFilterBoolean extends IHMFilter implements ActionListener {

	/**
	 * Bouton indiquant le filtre booleen
	 */
	private JButton jButton;

	/**
	 * Booleen
	 */
	private boolean isFilteringValue;

	/**
	 * Constructeur
	 * @param filter
	 * @param frame
	 */
	@SuppressWarnings("static-access")
	public IHMFilterBoolean(Filter filter, JFrame frame) {
		
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
		
		isFilteringValue = ((FilterBoolean)filter).isFilteringValue();
		
		if(isFilteringValue)
		{
			jButton = IHMTools.getInstance().getNewButtonWithIcon_ActionListener_TextOnRight(ConfigIcon.getInstance().BOOL_OK, this, "Filtre valeurs \"vraies\"");
		}
		else
		{
			jButton = IHMTools.getInstance().getNewButtonWithIcon_ActionListener_TextOnRight(ConfigIcon.getInstance().BOOL_KO, this, "Filtre valeurs \"fausses\"");
		}

		
		bH = Box.createHorizontalBox();
		bH.add(jButton);
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
			
		// On cree la liste d'objet a renvoyer
		List<Object> listObjectToKeep = new ArrayList<Object>();
		listObjectToKeep.add(isFilteringValue);
		filter.keepParametersFilter(listObjectToKeep);
	}


	@SuppressWarnings("static-access")
	public void actionPerformed(ActionEvent actionEvent)
	{ 		
		// Si on appuie sur le bouton d'activation
		if(actionEvent.getSource() == jButtonActivation)
		{
			// Si le filtre est actif
			if(isActive) {
				jButtonActivation.setIcon(ConfigIcon.getInstance().GRAY_LIGHT);
			}
			// Sinon, si le filtre est inactif
			else {
				jButtonActivation.setIcon(ConfigIcon.getInstance().ORANGE_LIGHT);
			}
			
			isActive = !isActive;
		}
		// Sinon, si on appuie sur le bouton annuler
		else if(actionEvent.getSource() == jButtonCancel)
		{
			setVisible(false);
		}
		
		// Sinon si on appuie sur le bouton valider
		else if(actionEvent.getSource() == jButtonValidate)
		{
			// On memorise les parametres
			keepParametersFilter();
			setVisible(false);
		}
		
		// Si on appuie sur le bouton d'activation
		if(actionEvent.getSource() == jButton)
		{
			// Si on filtre les valeurs "TRUE"
			if(isFilteringValue)
			{		
				jButton.setIcon(ConfigIcon.getInstance().BOOL_KO);
				jButton.setText("Filtre valeurs \"fausses\"");
			}
			else
			{
				jButton.setIcon(ConfigIcon.getInstance().BOOL_OK);
				jButton.setText("Filtre valeurs \"vraies\"");
			}
			
			isFilteringValue = !isFilteringValue;
		}
	}

}
