package model.pagesjaukes;

import javax.swing.event.EventListenerList;


public class ModelPagesJaukes {
	
	/**
	 * Liste de listeners de modele de Pages Jaukes
	 */
	private EventListenerList listeners;
	
	/**
	 * Constructeur
	 */
	public ModelPagesJaukes()
	{
		listeners = new EventListenerList();
	}

	/**
	 * Ajoute un listener de modele de Pages Jaukes
	 * @param listener 
	 */
	public void addModelPagesJaukesListener(I_ModelPagesJaukesListener listener){
		listeners.add(I_ModelPagesJaukesListener.class, listener);
	}
	
	/**
	 * Enleve un listener de modele de Pages Jaukes
	 * @param listener
	 */
	
	public void removeModelPagesJaukesListener(I_ModelPagesJaukesListener listener){
		listeners.remove(I_ModelPagesJaukesListener.class, listener);
	}
	
	/**
	 * Notifie le(s) listener(s) du modele de Pages Jaukes que
	 * celui-ci a ete modifie
	 */
	public void fireModelPagesJaukesChanged(int i_typeChange)
	{
		// On recupere la liste de listener du modele de Pages Jaukes
		I_ModelPagesJaukesListener[] listenerModelPagesJaukes = (I_ModelPagesJaukesListener[])listeners.getListeners(I_ModelPagesJaukesListener.class);
		
		// On parcourt chaque listener
		for(I_ModelPagesJaukesListener listener : listenerModelPagesJaukes)
		{
			switch (i_typeChange)
			{
			
			// Indique qu'au moins un critere de la recherche a change...
			case 0:
				// On relaye l'information au listener
				listener.criteriaPagesJaukesChange();
				break;
							
			default:
				System.err.println("ModelPagesJaukes.java - Erreur fonction criteriaPagesJaukesChange");
				break;

			}
		}
	}
	



}
