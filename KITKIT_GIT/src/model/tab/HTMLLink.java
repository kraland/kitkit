package model.tab;


public class HTMLLink {
	
	/**
	 * Lien
	 */
	private String s_link;
	
	/**
	 * Valeur
	 */
	private String s_value;

	/**
	 * Renvoie le lien
	 * @return
	 */
	public String getS_link() {
		return s_link;
	}

	/**
	 * Change le lien
	 * @param s_link
	 */
	public void setS_link(String s_link) {
		this.s_link = s_link;
	}

	/**
	 * Renvoie la valeur
	 * @return
	 */
	public String getS_value() {
		return s_value;
	}

	/**
	 * Renvoie la valeur
	 * @param s_value
	 */
	public void setS_value(String s_value) {
		this.s_value = s_value;
	}
	
	/**
	 * Constructeur
	 * @param s_link
	 * @param s_value
	 */
	public HTMLLink(String s_link,String s_value)
	{
		this.s_link = s_link;
		this.s_value = s_value;
	}
	
	/**
	 * ToString
	 */
	public String toString()
	{
		return s_value;
	}
	
}