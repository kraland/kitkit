
package ihm.load;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JWindow;

import controller.Controller;
import controller.I_ControllerDialog;

@SuppressWarnings("serial")
public class WindowLoad extends JWindow implements I_ControllerDialog{

	/**
	 * Containeur
	 */
	private Container container;
		
	/**
	 * Controleur
	 */
	private Controller ctrl;
	
	/**
	 * Panel de chargement
	 */
	private PanelLoad panelLoad;
	
	/**
	 * Constructeur de fenetre de chargement
	 */
	public WindowLoad()
	{		
		// On fixe la taille de la fenetre
		setSize(525,300);
		
		// On cree le contenu de la fenetre
		this.createContent();
		
		// On recupere la taille de l'ecran
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		   
		// On calcule le point de depart de la fenetre de chargement
		// afin de la centrer au sein de l'ecran
		int w = this.getSize().width;
		int h = this.getSize().height;
		int x = (dim.width-w)/2;
		int y = (dim.height-h)/2;
		
		// Change la position de la fenetre
		this.setLocation(x, y);  
	}

	private void createContent()
	{
		// On recupere le contenu de la fenetre
		container = getContentPane();

		// On assigne un layout a la fenetre
		container.setLayout(new BorderLayout());

		// On cree un panel de chargement
		panelLoad = new PanelLoad();
		
		// On ajoute un Panel au layout
		container.add(panelLoad, BorderLayout.CENTER);
			
	}

	/**
	 * Met a jour l'information sur l'avancement du chargement
	 * @param s_infoApplication
	 */
	public void updateInfo(String s_infoApplication)
	{
		panelLoad.setS_info(s_infoApplication);
	}
	
	/**
	 * Met a jour le pourcentage
	 * @param i_percentage
	 */
	public void setPercentage(int i_percentage)
	{
		// Change le pourcentage
		panelLoad.setI_percentage(i_percentage);
		
		// Si le pourcentage est inferieur ou egal a zero
		if(i_percentage <= 0)
		{
			panelLoad.setS_status("Chargement non débuté.");
		}
		// Sinon
		else
		{
			panelLoad.setS_status("Chargement en cours..."); 
		}
	}
	
	/**
	 * Met a jour le statut "final" du chargement. C'est a dire si celui-ci est ok ou non
	 * @param hasSuceedLoad
	 */
	public void setStatusLoad(boolean hasSuceedLoad)
	{
		if(hasSuceedLoad) 
		{
			panelLoad.setS_status("Chargement réussi."); 	
		}
		else
		{
			panelLoad.setS_status("Echec chargement."); 		
		}
		
		panelLoad.getThreadRefresh().setHasStoppedRefresh(true);
	}
	
	/**
	 * Change le controlleur
	 */
	public void setController(Controller ctrl) {
		this.ctrl = ctrl;
	}

	/**
	 * Renvoie le controlleur
	 */
	public Controller getController() {
		return ctrl;
	}
}

