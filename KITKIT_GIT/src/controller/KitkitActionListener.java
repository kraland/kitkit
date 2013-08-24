package controller;

import ihm.I_ViewDialog;
import ihm.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class KitkitActionListener implements ActionListener, I_ViewDialog{

	/**
	 * Vue de l'application
	 */
	private View view;
		
	/**
	 * KeyEvent
	 */
	private int i_keyEvent;
	
	/**
	 * KeyEvent associe a ce listener d'action
	 * @param keyEvent
	 */
	public KitkitActionListener(int i_keyEvent)
	{
		this.i_keyEvent = i_keyEvent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		// Si l'utilisateur a demande un affichage/desaffichage en plein ecran
		if(i_keyEvent == KeyEvent.VK_F11)
		{	
			view.getController().setFullScreen(!view.isFullScreen());
		}
		// Si l'utilisateur a demande un changement de skin
		else if(i_keyEvent == KeyEvent.VK_F2)
		{
			view.getController().changeSkin();
		}		
		// Si l'utilisateur a demande une mise a jour du modele
		else if(i_keyEvent == KeyEvent.VK_F5)
		{
			view.getController().tryToUpdateModel();
		}
		// Si l'utilisateur a demande une mise a jour du modele d'un ancien modele
		else if(i_keyEvent == KeyEvent.VK_F6)
		{
			view.getController().tryToLoadOldModel();
		}		
		
		// TODO Pourquoi la touche espace a le même comportement qu'un F5 ? WTF
	}

	@Override
	public void setView(View view) {
		this.view = view;
	}

	@Override
	public View getView() {
		return view;
	}

}
