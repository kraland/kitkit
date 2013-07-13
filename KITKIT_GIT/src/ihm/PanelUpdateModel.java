package ihm;

import ihm.I_Viewable;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Controller;
import controller.I_ControllerDialog;

@SuppressWarnings("serial")
public class PanelUpdateModel extends JPanel implements I_Viewable, I_ControllerDialog, ActionListener
{
	/**
	 * Table contenant tous les classes de vues
	 */
	private List<I_Viewable> listI_Viewable;
	
	/**
	 * Controlleur
	 */
	private Controller ctrl; 
	
	/**
	 * Bouton pour effectuer la mise a jour du modele
	 */
	private JButton jButtonUpdateModel;
	
	/**
	 * Bouton pour revenir a un modele precedent
	 */
	private JButton jButtonRestoreBackUpModel;
	
	/**
	 * Bouton pour mettre ou non l'application en plein ecran
	 */
	private JButton jButtonFullScreen;
	
	/**
	 * Bouton pour acceder a kraland
	 */
	private JButton jButtonKI;

	/**
	 * Bouton pour acceder a kitkit
	 */
	private JButton jButtonKITKIT;
	
	/**
	 * Label affichant les informations sur la MAJ
	 */
	private JLabel jLabelTimeUpdateModel;

	/**
	 * Constructeur de panel de mise a jour du modele
	 */
	public PanelUpdateModel()
	{
		// Change le layout
    	setLayout(new BorderLayout());
		
    	// Initialise les objets
    	initObject();
    	
		// On cree une boite horizontale
		Box bH = Box.createHorizontalBox();
				
		bH.add(jButtonUpdateModel);
		bH.add(jButtonRestoreBackUpModel);
		bH.add(Box.createHorizontalStrut(15));
		bH.add(jLabelTimeUpdateModel);
		bH.add(Box.createHorizontalGlue());
		
		bH.add(jButtonKI);
		bH.add(Box.createHorizontalStrut(5));
		bH.add(new JLabel("KI"));
		bH.add(Box.createHorizontalStrut(25));
		bH.add(jButtonKITKIT);
		bH.add(Box.createHorizontalStrut(5));
		bH.add(new JLabel("KITKIT"));
		bH.add(Box.createHorizontalStrut(25));
		bH.add(jButtonFullScreen);
		bH.add(Box.createHorizontalStrut(5));
		
		add(bH,BorderLayout.CENTER);
 	}
 	
	/**
	 * Initialise les objets
	 */
 	@SuppressWarnings("static-access")
	public void initObject()
	{
 		jButtonUpdateModel = new JButton(ConfigIcon.getInstance().REFRESH);
		jButtonRestoreBackUpModel = new JButton(ConfigIcon.getInstance().UNDO);
		jButtonRestoreBackUpModel.setEnabled(false);
		
		jButtonKI = new JButton(ConfigIcon.getInstance().LOGO_KI);
		jButtonKI.setOpaque(false);
		jButtonKI.setContentAreaFilled(false);
		jButtonKI.setFocusPainted(false);
		jButtonKI.setMargin(new Insets(0, 0, 0, 0));
    	
    	
		jButtonKITKIT = new JButton(ConfigIcon.getInstance().LOGO_KITKIT_16);
		jButtonKITKIT.setOpaque(false);
		jButtonKITKIT.setContentAreaFilled(false);
		jButtonKITKIT.setFocusPainted(false);
		jButtonKITKIT.setMargin(new Insets(0, 0, 0, 0));
		
		jButtonFullScreen = new JButton(ConfigIcon.getInstance().FULL_SCREEN);
		jButtonFullScreen.setOpaque(false);
		jButtonFullScreen.setContentAreaFilled(false);
		jButtonFullScreen.setFocusPainted(false);
		jButtonFullScreen.setMargin(new Insets(0, 0, 0, 0));
		
		jLabelTimeUpdateModel = new JLabel();
		
		jButtonUpdateModel.addActionListener(this);
		jButtonRestoreBackUpModel.addActionListener(this);
		jButtonKI.addActionListener(this);
		jButtonKITKIT.addActionListener(this);
		jButtonFullScreen.addActionListener(this);
	}
	
	/**
	 * Change les informations sur la date de mise a jour du modele
	 * @param s_timeUpdateModel
	 */
	public void updateTimeUpdateModel(String s_timeUpdateModel)
	{
		if(s_timeUpdateModel.equals(""))
		{
			jLabelTimeUpdateModel.setText("<html><font color = #F00000 >Fichier de données manquant/corrompu !</font></html>");			
		}
		else
		{
			jLabelTimeUpdateModel.setText("Données mises à jour le : " + s_timeUpdateModel);			
		}
		jLabelTimeUpdateModel.repaint();
		jLabelTimeUpdateModel.revalidate();
	}
	
	/**
	 * Change le pourcentage de telechargement
	 * @param s_timeUpdateModel
	 */
	public void updateStatusDownload(int i_percentage)
	{
		// TODO, le repaint/revalidate ne passait pas => laissé en suspend
		
		// Si il ne s'agit pas d'un pourcentage...
		if(i_percentage < 0 || i_percentage > 100)
		{
			jLabelTimeUpdateModel.setText("<html><font color = #F00000 >Fichier de données manquant/corrompu !</font></html>");			
		}
		// Si c'est un pourcentage
		else
		{
			jLabelTimeUpdateModel.setText("<html><font color = #668DC8 ><i>Téléchargement en cours : " + i_percentage + "%</i></font></html>");			
		}
		jLabelTimeUpdateModel.repaint();
		jLabelTimeUpdateModel.revalidate();
	}
	
	/**
	 * Change la liste d'interface visualisable
	 * @param listI_Viewable
	 */
	public List<I_Viewable> getListI_Viewable()
	{
		return listI_Viewable;
	}

	/**
	 * Renvoie la liste d'interface visualisable
	 * @return
	 */
	public void setListI_Viewable(List<I_Viewable> listI_Viewable)
	{
		this.listI_Viewable = listI_Viewable;
		addI_Viewable();
	}

	/**
	 * Ajoute une interface visualisable
	 */
	public void addI_Viewable()
	{
		listI_Viewable.add(this);
	}
	
	/**
	 * Initialise les valeurs de l'IHM
	 */
	public void initValueIHM()
	{

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
	
	
	@SuppressWarnings("static-access")
	@Override
	public void actionPerformed(ActionEvent arg0) {
	
		if(arg0.getSource() == jButtonUpdateModel)
		{
			ctrl.tryToUpdateModel();
		}
		else if(arg0.getSource() == jButtonKI)
		{
		    if (Desktop.isDesktopSupported())
		    {
		        URI uri = URI.create("http://www.kraland.org");
		        try
		        {
		            Desktop.getDesktop().browse(uri);
		        }
		        catch (IOException e)
		        {
		            e.printStackTrace();
		        }
		    }
		}
		else if(arg0.getSource() == jButtonKITKIT)
		{
		    if (Desktop.isDesktopSupported())
		    {
		        URI uri = URI.create("http://kitkit.ki.free.fr");
		        try
		        {
		            Desktop.getDesktop().browse(uri);
		        }
		        catch (IOException e)
		        {
		            e.printStackTrace();
		        }
		    }
		}
		else if(arg0.getSource() == jButtonRestoreBackUpModel)
		{
			//TODO Taper dans le fichier http://kitkit.ki.free.fr/confContentBatiment/list
			// Fichier a definir et a modifier chaque jour.
			// Mais ce fichier contiendra la liste de tous les modeles disponibles sur le ftp dans le "dossier" : http://kitkit.ki.free.fr/confContentBatiment/
		}
		else if (arg0.getSource() == jButtonFullScreen)
		{
			if(jButtonFullScreen.getIcon() == ConfigIcon.getInstance().FULL_SCREEN)
			{
				ctrl.setFullScreen(true);
			}
			else
			{	
				ctrl.setFullScreen(false);
			}
		}
	}

	
	@SuppressWarnings("static-access")
	/**
	 * Change l'icone du bouton plein ecran
	 * @param isFullScreen
	 */
	public void setFullScreen(boolean isFullScreen)
	{
		if(isFullScreen)
		{
			jButtonFullScreen.setIcon(ConfigIcon.getInstance().NO_FULL_SCREEN);		
		}
		else
		{
			jButtonFullScreen.setIcon(ConfigIcon.getInstance().FULL_SCREEN);
		}
	}
}
