package controller;

public interface I_ControllerDialog
{
	/**
	 * Change le controlleur
	 * @param ctrl
	 */
	public void setController(Controller ctrl);
	
	/**
	 * Renvoie le controlleur
	 * @return
	 */
	public Controller getController();
}
