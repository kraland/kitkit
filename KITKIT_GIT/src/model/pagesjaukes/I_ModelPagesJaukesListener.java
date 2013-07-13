package model.pagesjaukes;

import java.util.EventListener;

public interface I_ModelPagesJaukesListener extends EventListener {

	/**
	 * Indique qu'au moins un critere de la recherche a change
	 */
	public void criteriaPagesJaukesChange();
	
}
