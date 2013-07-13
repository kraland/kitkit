package ihm.filter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ihm.ConfigIcon;
import ihm.IHMTools;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

import model.filter.Filter;

@SuppressWarnings("serial")
public abstract class IHMFilter extends JDialog implements ActionListener{

	/**
	 * Booleen indiquant si le filtre est actif
	 */
	protected boolean isActive;
	
	/**
	 * Bouton annuler
	 */
	protected JButton jButtonCancel;
	
	/**
	 * Bouton valider
	 */
	protected JButton jButtonValidate;
	
	/**
	 * Bouton pour activer/desactiver le filtre
	 */
	protected JButton jButtonActivation;
		
	/**
	 * Filtre appliquer
	 */
	protected Filter filter;
	
	/**
	 * Constructeur de filtre
	 * @param filter
	 * @param frame
	 */
	@SuppressWarnings("static-access")
	public IHMFilter(Filter filter, JFrame frame)
	{
		// On cree la JDialog...
		super(frame, "Filtre \"" + filter.getS_name() + "\"",true);
		
		// On recupere le filtre
		this.filter = filter;
		
		// On modifie le bouton indiquant si le filtre est active ou non
		if(filter.isActive()){jButtonActivation = IHMTools.getInstance().getNewButtonWithIcon_ActionListener(ConfigIcon.getInstance().ORANGE_LIGHT,this);isActive = true;}
		else{jButtonActivation = IHMTools.getInstance().getNewButtonWithIcon_ActionListener(ConfigIcon.getInstance().GRAY_LIGHT,this);isActive = false;}
		
		// On cree le bouton valider ou annuler
		jButtonValidate = IHMTools.getInstance().getNewButtonWithIcon_ActionListener_TextOnRight(ConfigIcon.getInstance().VALIDATE,this,"Valider");
		jButtonCancel = IHMTools.getInstance().getNewButtonWithIcon_ActionListener_TextOnRight(ConfigIcon.getInstance().CANCEL,this,"Annuler");
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
	}
	
	/**
	 * Methode a redefinir dans chaque filtre pour enregistrer les filtres
	 */
	protected abstract void keepParametersFilter();
}
