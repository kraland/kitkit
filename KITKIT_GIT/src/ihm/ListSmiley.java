package ihm;


import javax.swing.ImageIcon;

public class ListSmiley {

	/**
	 * Tableau de smileys de KI
	 */
	public static Smiley[] TAB_SMILEY;
	
	/**
	 * Code de tous les smileys de KI
	 */
	private String[] tab_s_codeSmiley = {"[:)]","[;)]","[8)]","[:]]","[:D]","[:p]","[:6]","[3)]","[:,]","[:(]","[:[]","[)[]","[!(]","[^]]","[x(]","[8(]","[o)]","[%(]","[:o]","[:|]","[)|]","[;(]","[;[]","[:f]","[;o]","[;|]","[[(]","[0)]","[:B]","[:=]","[8]]","[|)]","[O)]","[8î]","[8Î]","[j)]","[p)]","[^)]","[)f]","[:î]","[:Î]","[%)]","[8O]","[OX]","[)%]","[oX]","[:.]","[o(]","[hp]","[:n]","[:P]","[:x]","[8p]","[j|]","[kD]","[k]]","[;p]","[:l]","[:+]","[:-]","[VV]","[%%]","[Q)]","[fr]","[en]","[de]","[es]","[it]","[nl]","[ca]","[sw]","[jp]","[*t]","[*j]","[*o]","[*r]","[*v]","[*c]","[*b]","[*m]","[*n]","[=o]","[=n]","[=S]","[ty]","[mt]","[so]","[iz]","[jo]","[tk]","[pk]","[ka]","[ke]","[3i]","[+)]","[st]","[§c]","[§o]","[§g]","[co]","[§p]","[=)]","[=!]","[=k]","[=y]"};

	/**
	 * Code des smileys avec les doubles anti-slash, sinon le replaceAll n'est pas content.
	 * 'cule !
	 */
	private String[] tab_s_codeSmileyBackslashs = {"\\[\\:\\)\\]","\\[\\;\\)\\]","\\[8\\)\\]","\\[\\:\\]\\]","\\[\\:D\\]","\\[\\:p\\]","\\[\\:6\\]","\\[3\\)\\]","\\[\\:\\,]","\\[\\:\\(\\]","\\[\\:\\[\\]","\\[\\)\\[\\]","\\[\\!\\(\\]","\\[\\^\\]\\]","\\[x\\(\\]","\\[8\\(\\]","\\[o\\)\\]","\\[\\%\\(\\]","\\[\\:o\\]","\\[\\:\\|\\]","\\[\\)\\|\\]","\\[\\;\\(\\]","\\[\\;\\[\\]","\\[\\:f\\]","\\[\\;o\\]","\\[\\;\\|\\]","\\[\\[\\(\\]","\\[0\\)\\]","\\[\\:B\\]","\\[\\:\\=\\]","\\[8\\]\\]","\\[\\|\\)\\]","\\[O\\)\\]","\\[8î\\]","\\[8Î\\]","\\[j\\)\\]","\\[p\\)\\]","\\[\\^\\)\\]","\\[\\)f\\]","\\[\\:î\\]","\\[\\:Î\\]","\\[\\%\\)\\]","\\[8O\\]","\\[OX\\]","\\[\\)\\%\\]","\\[oX\\]","\\[\\:\\.\\]","\\[o\\(\\]","\\[hp\\]","\\[\\:n\\]","\\[\\:P\\]","\\[\\:x\\]","\\[8p\\]","\\[j\\|\\]","\\[kD\\]","\\[k\\]\\]","\\[\\;p\\]","\\[\\:l\\]","\\[\\:\\+\\]","\\[\\:\\-\\]","\\[VV\\]","\\[\\%\\%\\]","\\[Q\\)\\]","\\[fr\\]","\\[en\\]","\\[de\\]","\\[es\\]","\\[it\\]","\\[nl\\]","\\[ca\\]","\\[sw\\]","\\[jp\\]","\\[\\*t\\]","\\[\\*j\\]","\\[\\*o\\]","\\[\\*r\\]","\\[\\*v\\]","\\[\\*c\\]","\\[\\*b\\]","\\[\\*m\\]","\\[\\*n\\]","\\[\\=o\\]","\\[\\=n\\]","\\[\\=S\\]","\\[ty\\]","\\[mt\\]","\\[so\\]","\\[iz\\]","\\[jo\\]","\\[tk\\]","\\[pk\\]","\\[ka\\]","\\[ke\\]","\\[3i\\]","\\[\\+\\)\\]","\\[st\\]","\\[\\§c\\]","\\[\\§o\\]","\\[\\§g\\]","\\[co\\]","\\[\\§p\\]","\\[\\=\\)\\]","\\[\\=\\!\\]","\\[\\=k\\]","\\[\\=y\\]"};
	
	/**
	 * Instance
	 */
	private static ListSmiley instance = null;
	
	/**
	 * Implementation du pattern "singleton"
	 * @return
	 */
	public static ListSmiley getInstance(){
		if(instance == null) instance = new ListSmiley();
		return instance;
	}
	
	/**
	 * Constructeur de liste de smiley
	 */
	private ListSmiley()
	{
		TAB_SMILEY = new Smiley[tab_s_codeSmiley.length];
		
		// On parcourt chaque smiley
		for(int i_idSmiley = 0 ; i_idSmiley < tab_s_codeSmiley.length ; i_idSmiley++)
		{
			// On convertit l'ID + 1 du smiley en hexa
			String s_nameIcon = Integer.toHexString(i_idSmiley+1);
			
			// Si la valeur hexa ne fais qu'un caractere
			if(Integer.toHexString(i_idSmiley+1).length() < 2)
			{
				// On ajoute 0 devant
				s_nameIcon = "0" + s_nameIcon;
			}
						
			// On met le code hexa en majuscule
			s_nameIcon = s_nameIcon.toUpperCase();
			
			// On cree le smiley parcouru
			TAB_SMILEY[i_idSmiley] = new Smiley(tab_s_codeSmiley[i_idSmiley],
												new ImageIcon(getClass().getResource("/img/smiley/" + s_nameIcon + ".gif")),
												getClass().getResource("/img/smiley/" + s_nameIcon + ".gif"));
		}
	}
		
	/**
	 * Fonction permettant d'identifier les smileys dans une chaine de caracteres fournie en parametre.
	 * Cette fonction renvoie un tableau de deux chaines de caracteres, contenant :
	 * - la chaine de caracteres sans code smiley
	 * - la chaine de caracteres codee en html
	 * Cette fonction est appelee lorsqu'on parcourt le xml afin de construire le modele.
	 * @param s_toConvert
	 * @return
	 */
	public String[] identifySmiley(String s_toConvert)
	{
		// Tableau de resultat
		String[] tabResult = new String[2];
		
		// Chaine de caracteres sans smiley
		String s_withoutSmiley = s_toConvert;
		
		// Chaine de caractere convertie en xml
		String s_html = s_toConvert;
		
		// On ajoute la balise html
		s_html = "<html>" + s_html;
		
		// On parcourt tous les smileys de KI
		for(int i_idSmiley = 0 ; i_idSmiley < tab_s_codeSmileyBackslashs.length ; i_idSmiley++)
		{
			// Si il y a une "bracket" ([)
			if(s_withoutSmiley.indexOf("[")>=0)
			{
				// On supprime chaque smiley
				s_withoutSmiley = s_withoutSmiley.replaceAll(tab_s_codeSmileyBackslashs[i_idSmiley],"");
				
				// On remplace chaque smiley par son equivalent en HTML
				s_html = s_html.replaceAll(tab_s_codeSmileyBackslashs[i_idSmiley],"<img src=\"" + TAB_SMILEY[i_idSmiley].getUrl() +"\" >");	
			}
			else
			{
				// Tout les smileys ont ete trouves
				break;
			}
		}
		
		// Ajout de la balise de fin de html
		s_html = s_html + "</html>";
		
		// Ajout des donnees au tableau a renvoyer
		tabResult[0] = s_withoutSmiley;
		tabResult[1] = s_html;
		
		return tabResult;
	}
	

}
