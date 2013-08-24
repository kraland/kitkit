package ihm;

import ihm.I_Viewable;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.swing.Box;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

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
	 * Panel contenant les informations du modèle en cours de telechargement ou celui telecharge
	 */
	private JPanel jPanelDownload;
	
	/**
	 * Label affichant les informations sur la MAJ
	 */
	private JLabel jLabelDownload;
	
	/**
	 * JComboBox
	 */
	private JProgressBar jProgressBarDownload;
	
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
		bH.add(jPanelDownload);
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
		
		jPanelDownload = new JPanel();
		jPanelDownload.setLayout(new CardLayout());
		jLabelDownload = new JLabel();
		jProgressBarDownload = new JProgressBar();
		jProgressBarDownload.setModel(new DefaultBoundedRangeModel());
		jProgressBarDownload.setStringPainted(true);
		
		jPanelDownload.add(jLabelDownload,"LABEL");
		jPanelDownload.add(jProgressBarDownload,"PROGRESS");
		jPanelDownload.setMaximumSize(new Dimension(400,20));
		
		
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
			jLabelDownload.setText("<html><font color = #F00000 >Fichier de données manquant/corrompu !</font></html>");			
		}
		else
		{
			jLabelDownload.setText("Données mises à jour le : " + s_timeUpdateModel);			
		}
		jLabelDownload.repaint();
		jLabelDownload.revalidate();
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
			jLabelDownload.setText("<html><font color = #F00000 >Fichier de données manquant/corrompu !</font></html>");			
		}
		// Si c'est un pourcentage
		else
		{
			jLabelDownload.setText("<html><font color = #668DC8 ><i>Téléchargement en cours : " + i_percentage + "%</i></font></html>");			
		}
		jLabelDownload.repaint();
		jLabelDownload.revalidate();
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
			ctrl.tryToLoadOldModel();
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
	
	/**
	 * Change le contenu du panel de telechargement :
	 * - Soit on affiche le timestamp du modele de l'application
	 * - Soit on affiche la barre de progression indiquant l'avancement du telechargement du modele
	 * @param hasToShowProgress
	 */
	public void changeContentPanelDownload(boolean hasToShowProgress)
	{
		
		// On recupere le layout
		CardLayout cl = (CardLayout)(jPanelDownload.getLayout());
   		
		if(hasToShowProgress)
		{
			// On affiche la progress bar
			cl.show(jPanelDownload, "PROGRESS");			
		}
		else
		{
			// On affiche le label
			cl.show(jPanelDownload, "LABEL");
		}
	}
	
	/**
	 * Met a jour le maximum de la barre de progression
	 * @param i_maximumProgressBar
	 */
	public void updateMaximumProgressBar(int i_maximumProgressBar)
	{
		jProgressBarDownload.getModel().setMinimum(0);
		jProgressBarDownload.getModel().setMaximum(i_maximumProgressBar);
		jProgressBarDownload.getModel().setValue(0);
	}
	
	/**
	 * Met a jour la valeur de la barre de progression
	 * @param i_valueProgressBar
	 */
	public void updateValueProgressBar(int i_valueProgressBar)
	{
		jProgressBarDownload.getModel().setValue(i_valueProgressBar);
		jProgressBarDownload.repaint();
	}
}
