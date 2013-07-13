package ihm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class IHMTools {

	/**
	 * Couleur des contours de panel
	 */
	public static Color colorBorder = new Color(64, 91, 164);
	
	/**
	 * Couleur des contours de sous-panel (panel inclu dans un panel)
	 */
	public static Color colorSubBorder = new Color(118, 132, 169);
	
	/**
	 * Couleur des separateurs
	 */
	public static Color colorSeparator = new Color(154, 157, 166);
	
	/**
	 * Instance
	 */
	private static IHMTools instance = null;
	
	/**
	 * Implementation du pattern "singleton"
	 * @return
	 */
	public static IHMTools getInstance(){
		if(instance == null) instance = new IHMTools();
		return instance;
	}
	
	/**
	 * Ajoute un separateur horizontal a la boite passe en argument et renvoie ce separateur.
	 * On indique aussi la marge avant et apres le separateur
	 * @param box
	 * @param i_marginBefore
	 * @param i_marginAfter
	 * @return
	 */
	public JSeparator addHorizontalSeparator(Box box, int i_marginBefore,  int i_marginAfter)
	{
		// Separateur
		JSeparator jSeparatorTmp;
		
		box.add(Box.createVerticalStrut(i_marginBefore));
    	jSeparatorTmp = new JSeparator();
    	jSeparatorTmp.setForeground(colorSeparator);
    	jSeparatorTmp.setOrientation(SwingConstants.HORIZONTAL);
    	box.add(jSeparatorTmp);
    	box.add(Box.createVerticalStrut(i_marginAfter));
    	
    	return jSeparatorTmp;
	}
	
	/**
	 * Renvoie un nouveau bouton
	 * @return
	 */
	public JButton getNewButton()
	{
		JButton jButtonTmp = new JButton();
		jButtonTmp.setOpaque(false);
		jButtonTmp.setContentAreaFilled(false);
		jButtonTmp.setFocusPainted(false);
		jButtonTmp.setBorderPainted(false);
		jButtonTmp.setMargin(new Insets(0, 0, 0, 0));
		return jButtonTmp;
	}
	
	/**
	 * Renvoie un nouveau bouton avec un bord
	 * @return
	 */
	public JButton getNewButtonWithBorder()
	{
		JButton jButtonTmp = getNewButton();
		jButtonTmp.setBorderPainted(true);
		return jButtonTmp;
	}
		
	/**
	 * Renvoie un bouton avec un icone
	 * @param icon
	 * @return
	 */
	public JButton getNewButtonWithIcon(Icon icon)
	{
		JButton jButtonTmp = getNewButton();
		jButtonTmp.setIcon(icon);
		return jButtonTmp;
	}
	
	/**
	 * Renvoie un nouveau bouton avec un bord une icone et ActionListener
	 * @param icon
	 * @param actionListener
	 * @return
	 */
	public JButton getNewButtonWithBorder_Icon_ActionListener(Icon icon, ActionListener actionListener)
	{
		JButton jButtonTmp = getNewButtonWithBorder();
		jButtonTmp.setIcon(icon);
		jButtonTmp.addActionListener(actionListener);
		return jButtonTmp;
	}
	
	/**
	 * Renvoie un nouveau bouton avec icone et ActionListener
	 * @param icon
	 * @param actionListener
	 * @return
	 */
	public JButton getNewButtonWithIcon_ActionListener(Icon icon, ActionListener actionListener)
	{
		JButton jButtonTmp = getNewButton();
		jButtonTmp.setIcon(icon);
		jButtonTmp.addActionListener(actionListener);
		return jButtonTmp;
	}
	

	/**
	 * Renvoie un nouveau bouton avec icone et ActionListener et texte a droite
	 * @param icon
	 * @param actionListener
	 * @param s_text
	 * @return
	 */
	public JButton getNewButtonWithIcon_ActionListener_TextOnRight(Icon icon, ActionListener actionListener,String s_text)
	{
		JButton jButtonTmp = getNewButtonWithIcon_ActionListener(icon, actionListener);
		jButtonTmp.setText(s_text);
		jButtonTmp.setVerticalTextPosition(SwingConstants.CENTER);
		jButtonTmp.setHorizontalTextPosition(SwingConstants.RIGHT);
		return jButtonTmp;
	}
	
	/**
	 * Renvoie un bouton de filtre
	 * @param icon
	 * @param s_text
	 * @return
	 */
	public JButton getButtonFilter(Icon icon, String s_text)
	{
		JButton jButtonTmp = getNewButtonWithIcon(icon);
		jButtonTmp.setText(s_text);
		jButtonTmp.setVerticalTextPosition(SwingConstants.CENTER);
		jButtonTmp.setHorizontalTextPosition(SwingConstants.RIGHT);
		return jButtonTmp;
	}
	
	/**
	 * Renvoie la position de la fenetre
	 * @param i_width
	 * @param i_height
	 * @return
	 */
	public Point getLocation(int i_width, int i_height)
	{
		//TODO A voir pour les fenetres qui ne sont pas en plein ecran...
		
		// On recupere la taille de l'ecran
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		   
		// On calcule le point de depart de la fenetre de chargement
		// afin de la centrer au sein de l'ecran
		int x = (dim.width-i_width)/2;
		int y = (dim.height-i_height)/2;
		
		return new Point(x,y);	
	}

}
