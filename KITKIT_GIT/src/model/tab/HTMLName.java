package model.tab;


public class HTMLName {
	
	/**
	 * Nom HTML
	 */
	private String s_nameHTML;
	
	/**
	 * Nom
	 */
	private String s_name;

	/**
	 * Renvoie le Nom HTML
	 * @return
	 */
	public String getS_nameHTML() {
		return s_nameHTML;
	}

	/**
	 * Change le Nom HTML
	 * @param s_link
	 */
	public void setS_nameHTML(String s_nameHTML) {
		this.s_nameHTML = s_nameHTML;
	}

	/**
	 * Renvoie la Nom
	 * @return
	 */
	public String getS_name() {
		return s_name;
	}

	/**
	 * Renvoie la Nom
	 * @param s_value
	 */
	public void setS_name(String s_name) {
		this.s_name = s_name;
	}
	
	/**
	 * Constructeur
	 * @param s_nameHTML
	 * @param s_name
	 */
	public HTMLName(String s_nameHTML,String s_name)
	{
		this.s_nameHTML = s_nameHTML;
		this.s_name = s_name;
	}
	
	/**
	 * ToString
	 */
	public String toString()
	{
		return s_nameHTML;
	}
	
}