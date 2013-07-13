package ihm;

import java.net.URL;
import javax.swing.ImageIcon;

public class Smiley {

	/**
	 * Code d'ecriture du smiley de KI
	 */
	private String s_codeSmiley;
	
	/**
	 * Image icone du smiley de KI
	 */
	private ImageIcon icon_smiley;

	/**
	 * URL du smiley de KI
	 */
	private URL url;
	
	/**
	 * 
	 * @param s_codeSmiley
	 * @param icon_smiley
	 */
	
	Smiley(String s_codeSmiley, ImageIcon icon_smiley, URL url)
	{
		this.s_codeSmiley = s_codeSmiley;
		this.icon_smiley = icon_smiley;
		this.url = url;
	}

	
	/**
	 * Constructeur de smiley
	 */
	public Smiley() {
	}

	/**
	 * Renvoie le code du smiley de KI
	 * @return
	 */
	public String getS_codeSmiley() {
		return s_codeSmiley;
	}

	/**
	 * Change le code du smiley de KI
	 * @param s_codeSmiley
	 */
	public void setS_codeSmiley(String s_codeSmiley) {
		this.s_codeSmiley = s_codeSmiley;
	}

	/**
	 * Renvoie l'icone du smiley de KI
	 * @return
	 */
	public ImageIcon getIcon_smiley() {
		return icon_smiley;
	}

	/**
	 * Change l'icone du smiley de KI
	 * @param icon_smiley
	 */
	public void setIcon_smiley(ImageIcon icon_smiley) {
		this.icon_smiley = icon_smiley;
	}

	/**
	 * Renvoie l'URL du smiley de KI
	 * @return
	 */
	public URL getUrl() {
		return url;
	}

	/**
	 * Change l'URL du smiley de KI
	 * @param url
	 */
	public void setUrl(URL url) {
		this.url = url;
	}
	
}
