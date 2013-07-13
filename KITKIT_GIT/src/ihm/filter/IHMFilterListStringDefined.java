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
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

import model.filter.Filter;
import model.filter.FilterListStringDefined;

@SuppressWarnings("serial")
public class IHMFilterListStringDefined extends IHMFilter implements ActionListener{

	/**
	 * Liste de checkbox
	 */
	private List<JCheckBox> listCheckBox;
	
	/**
	 * Bouton permettant de cocher/decocher toutes les checks box
	 */
	private JButton jButtonCheckBoxSelection;
	
	/**
	 * Constructeur
	 * @param filter
	 * @param frame
	 */
	@SuppressWarnings("static-access")
	public IHMFilterListStringDefined(Filter filter, JFrame frame) {
		
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
		
		listCheckBox = new ArrayList<JCheckBox>();
		for(String chooseDefined : ((FilterListStringDefined)filter).getListStringDefined())
		{				
			boolean isSelected = false;
			for(String chooseFiltered : ((FilterListStringDefined)filter).getListStringFiltered())
			{
				if(chooseFiltered.equals(chooseDefined))
				{
					isSelected = true;
					break;
				}
			}
			listCheckBox.add(new JCheckBox(chooseDefined, isSelected));
		}
		JPanel jPanel = new JPanel();
		jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.PAGE_AXIS));
		JScrollPane jScrollPane = new JScrollPane(jPanel);	
		
		for(JCheckBox checkBoxToAdd : listCheckBox)
		{
			Box bHTmp = Box.createHorizontalBox();
			bHTmp.add(checkBoxToAdd);
			bHTmp.add(Box.createHorizontalGlue());
			jPanel.add(bHTmp);
		}
		
		bV.add(jScrollPane);
		
		
		jButtonCheckBoxSelection = IHMTools.getInstance().getNewButtonWithIcon_ActionListener_TextOnRight(ConfigIcon.getInstance().CHECK, this, "Cocher tout");
		bH = Box.createHorizontalBox();
		bH.add(jButtonCheckBoxSelection);
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
		
		List<String> listStringFiltered = new ArrayList<String>();
		
		for(JCheckBox checkBox : listCheckBox)
		{
			if(checkBox.isSelected())
			{
				listStringFiltered.add(checkBox.getText());
			}
		}
		
		// On cree la liste d'objet a renvoyer
		List<Object> listObjectToKeep = new ArrayList<Object>();
		listObjectToKeep.add(listStringFiltered);
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
		if(actionEvent.getSource() == jButtonCheckBoxSelection)
		{
			// Si le filtre est actif
			if(jButtonCheckBoxSelection.getIcon() == ConfigIcon.getInstance().CHECK)
			{
				for(JCheckBox checkBoxToAdd : listCheckBox)
				{
					checkBoxToAdd.setSelected(true);
				}
				
				jButtonCheckBoxSelection.setIcon(ConfigIcon.getInstance().UNCHECK);
				jButtonCheckBoxSelection.setText("Décocher tout");
			}
			else
			{
				for(JCheckBox checkBoxToAdd : listCheckBox)
				{
					checkBoxToAdd.setSelected(false);
				}
				
				jButtonCheckBoxSelection.setIcon(ConfigIcon.getInstance().CHECK);
				jButtonCheckBoxSelection.setText("Cocher tout");
			}
		}
	}
}
