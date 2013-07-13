package ihm.load;

import javax.swing.JComponent;


public class ThreadRefreshComponent implements Runnable {
	
	/**
	 * Temps de rafraichissement
	 */
	private int i_refreshTime;
	
	/**
	 * Composant a rafraichir
	 */
	private JComponent componentToRefresh;
		
	/**
	 * Booleen indiquant la fin du rafraichissement
	 */
	private boolean hasStoppedRefresh;
	
	/**
	 * Constructeur de thread de rafraichissement
	 * @param componentToRefresh
	 * @param i_refreshTime
	 */
	public ThreadRefreshComponent(JComponent componentToRefresh, int i_refreshTime)
	{
		this.hasStoppedRefresh = false;
		this.i_refreshTime = i_refreshTime;
		this.componentToRefresh = componentToRefresh;
	}
	
	@Override
	public void run()
	{	
		do {
			try
			{
				// On temporise  le temps du rafraichissement
				Thread.sleep(i_refreshTime);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			
			// On rafraichit le panel
			componentToRefresh.repaint();
			componentToRefresh.revalidate();
			
		}while (!hasStoppedRefresh);
		// Tant que le booleen n'indique pas la fin du rafraichissement

	}
	
	public void setHasStoppedRefresh(boolean hasStoppedRefresh)
	{
		this.hasStoppedRefresh = hasStoppedRefresh; 
	}
	
}
