package ihm.load;

import ihm.ConfigIcon;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;


import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PanelLoad extends JPanel{
	
	/**
	 * Pourcentage du telechargement
	 */
	private int i_percentage;

	/**
	 * Information de l'avancement du telechargement
	 */
	private String s_info = "";
	
	/**
	 * Statut du chargement
	 */
	private String s_status = "";
	
	/**
	 * Valeur en degree du dernier angle
	 */
	private int i_lastDegree = 0;
	
	/**
	 * Pas d'incrementation de l'angle en degree, avec 380 un multiple de cette valeur ( X * 20 = 380, ici X = 19 )
	 */
	private static int i_degreeScale = 20;
	
	/**
	 * Thread de rafraichissement
	 */
	private ThreadRefreshComponent threadRefresh;
	
	/**
	 * Graphics 2D
	 */
	private Graphics2D g2;
	
	/**
	 * Constructeur de panel de chargement
	 */
	public PanelLoad()
	{
		// On cree un thread rafraichissant ce panel tous les 50 milli
		threadRefresh = new ThreadRefreshComponent(this,50);
		
		// On demarre ce thread
		new Thread(threadRefresh).start();
	}
	
	/**
	 * Paint...
	 */
    public void paintComponent(Graphics g)
    {
    	// On convertit le Graphics en Graphics2D
    	g2=(Graphics2D)g;

    	// On dessine le cercle
    	drawCircle();
    	
    	// On dessine l'arriere plan qui est transparent afin de laisser aparaitre le cercle
    	drawBackGround();
    	
    	// On dessine le pourcentage
    	drawPercentage();
    	
    	// On dessine le statut
    	drawStatus();
    	
    	// On dessine l'information sur l'avancement du telechargement
    	drawInfo();
    	
    	// Si la valeur en degree du dernier angle est superieur ou egale a 380
    	if(i_lastDegree >= 380)
    	{
    		// On fait une RAZ de cette angle
        	i_lastDegree = 0;  		
    	}
    	// Sinon
    	else
    	{
    		// On l'increment de la valeur du pas
    		i_lastDegree += i_degreeScale;
    	}
    }
    
    /**
     * Dessine l'arriere plan
     */
    @SuppressWarnings("static-access")
	private void drawBackGround()
    {
    	// On recupere et on affiche l'image
    	Image imgTmp = ConfigIcon.getInstance().IMG_LOAD.getImage();
    	g2.drawImage(imgTmp,0,0,null);
    }
    
    /**
     * Dessine le cercle bleu pivotant
     */
    @SuppressWarnings("static-access")
	private void drawCircle()
    {
    	// Position de depart du cercle (donnee fixe)
    	int i_xNormal = 3;
    	int i_yNormal = 125;
    	
    	// On recupere l'image
    	ImageIcon imgIconTmp = ConfigIcon.getInstance().CIRCLE_LOAD;
    	Image imgTmp = imgIconTmp.getImage();
    	
    	// On calcule sa largeur
    	int i_widthImg = imgIconTmp.getIconWidth();
    	
    	// On fait une translation afin de placer le repere au centre du carre
    	g2.translate(i_xNormal + i_widthImg/2, i_yNormal + i_widthImg/2);  	
    	
    	// On fait une rotation du repere
    	g2.rotate(Math.toRadians(i_lastDegree), 0, 0);
    	
    	// On dessine l'arriere plan
    	g2.drawImage(imgTmp,-i_widthImg/2,-i_widthImg/2,null);
    	
    	// On fait une rotation inverse du repere
    	g2.rotate(Math.toRadians(-i_lastDegree), 0, 0);
    	
    	// On fait une translation inverse du repere
    	g2.translate(-(i_xNormal + i_widthImg/2), -(i_yNormal + i_widthImg/2));
    }
    
    /**
     * On dessine le pourcentage
     */
    private void drawPercentage()
    {
    	// On recupere la police et la couleur dans des donnees temporaires
    	Color colorMem = g2.getColor();
    	Font fontMem = g2.getFont();
    	
    	// On change la couleur et la police
    	Color colorTmp = new Color(27, 168, 196);
    	Font fontTmp = new Font("Serif", Font.ITALIC , 15);
    	g2.setFont(fontTmp);
    	g2.setColor(colorTmp);
    	
    	// On dessine le pourcentage
    	g2.drawString(i_percentage + "%", 64, 251);
    	
    	// On remet la couleur et la police a leur valeur initiale
    	g2.setColor(colorMem);
    	g2.setFont(fontMem);
    }
    
    /**
     * On dessine le statut
     */
    private void drawStatus()
    {
    	// On recupere la police et la couleur dans des donnees temporaires
    	Color colorMem = g2.getColor();
    	Font fontMem = g2.getFont();
    	
    	// On change la couleur et la police
    	Color colorTmp = new Color(27, 168, 196,240);
    	Font fontTmp = new Font("Serif", Font.BOLD , 20);
    	g2.setFont(fontTmp);
    	g2.setColor(colorTmp);
    	
    	// On dessine le statut
    	g2.drawString(s_status, 126, 195);
    	
    	// On remet la couleur et la police a leur valeur initiale
    	g2.setColor(colorMem);
    	g2.setFont(fontMem);
    }

    /**
     * On dessine les infos
     */
    private void drawInfo()
    {
    	// On recupere la police et la couleur dans des donnees temporaires
    	Color colorMem = g2.getColor();
    	Font fontMem = g2.getFont();
    	
    	// On change la couleur et la police
    	Color colorTmp = new Color(27, 168, 196,180);
    	Font fontTmp = new Font("Serif", Font.BOLD , 15);
    	g2.setFont(fontTmp);
    	g2.setColor(colorTmp);
    	
    	// On dessine les infos
    	g2.drawString(s_info, 126, 215);
    	
    	// On remet la couleur et la police a leur valeur initiale
    	g2.setColor(colorMem);
    	g2.setFont(fontMem);
    }
    
    /**
     * Renvoie le pourcentage
     * @return
     */
	public int getI_percentage() {
		return i_percentage;
	}

	/**
	 * Change le pourcentage
	 * @param i_percentage
	 */
	public void setI_percentage(int i_percentage) {
		this.i_percentage = i_percentage;
	}

	/**
	 * Renvoie le statut
	 * @return
	 */
	public String getS_status() {
		return s_status;
	}

	/**
	 * Change le statut
	 * @param s_status
	 */
	public void setS_status(String s_status) {
		this.s_status = s_status;
	}

	/**
	 * Renvoie la valeur du dernier degree
	 * @return
	 */
	public int getI_lastDegree() {
		return i_lastDegree;
	}

	/**
	 * Change la valeur du dernier degree
	 * @param i_lastDegree
	 */
	public void setI_lastDegree(int i_lastDegree) {
		this.i_lastDegree = i_lastDegree;
	}

	/**
	 * Renvoie de l'information
	 * @return
	 */
	public String getS_info() {
		return s_info;
	}

	/**
	 * Change l'information
	 * @param s_info
	 */
	public void setS_info(String s_info) {
		this.s_info = s_info;
	}

	public ThreadRefreshComponent getThreadRefresh() {
		return threadRefresh;
	}

	public void setThreadRefresh(ThreadRefreshComponent threadRefresh) {
		this.threadRefresh = threadRefresh;
	}

}
