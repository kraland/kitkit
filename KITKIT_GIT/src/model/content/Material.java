package model.content;

 
 public class Material {   
 	
	 // TODO
	 // Ajouter la valeur indicative d'un materiel
	 // A noter que le billet de musee n'a pas de valeur indicative referencee.
	 
 	/**
 	 * Nom du materiel
 	 */
 	private String s_name;
	
	/**
	 * Prix d'achat du materiel
	 */
	private int i_buyPrice;

	/**
	 * Prix de vente du materiel
	 */
	private int i_soldPrice;
	
	/**
	 * Quantite de materiel disponible dans le stock
	 */
	private int i_stockedQuantity;	
	
	/**
	 * Quantite max possible de materiel
	 */
	private int i_maxQuantity;	

	/**
	 * Construteur
	 */
	public Material(){}

	/**
	 * Renvoie le nom du materiel
	 * @return
	 */
	public String getS_name() {
		return s_name;
	}

	/**
	 * Change le nom du materiel
	 * @param s_name
	 */
	public void setS_name(String s_name) {
		this.s_name = s_name;
	}

	/**
	 * Renvoie le prix d'achat du materiel
	 * @return
	 */
	public int getI_buyPrice() {
		return i_buyPrice;
	}

	/**
	 * Change le prix d'achat du materiel
	 * @param i_buyPrice
	 */
	public void setI_priceBuy(int i_buyPrice) {
		this.i_buyPrice = i_buyPrice;
	}

	/**
	 * Renvoie le prix de vente du materiel
	 * @return
	 */
	public int getI_priceSold() {
		return i_soldPrice;
	}

	/**
	 * Change le prix de vente du materiel
	 * @param i_soldPrice
	 */
	public void setI_priceSold(int i_soldPrice) {
		this.i_soldPrice = i_soldPrice;
	}

	/**
	 * Renvoie la quantite de materiel disponible dans le stock
	 * @return
	 */
	public int getI_stockedQuantity() {
		return i_stockedQuantity;
	}

	/**
	 * Change la quantite de materiel disponible dans le stock
	 * @param i_stockedQuantity
	 */
	public void setI_stockedQuantity(int i_stockedQuantity) {
		this.i_stockedQuantity = i_stockedQuantity;
	}

	/**
	 * Renvoie la quantite max possible de materiel
	 * @return
	 */
	public int getI_maxQuantity() {
		return i_maxQuantity;
	}

	/**
	 * Change la quantite max possible de materiel
	 * @param i_maxQuantity
	 */
	public void setI_maxQuantity(int i_maxQuantity) {
		this.i_maxQuantity = i_maxQuantity;
	}

 }
