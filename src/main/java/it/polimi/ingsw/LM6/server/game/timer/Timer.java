package it.polimi.ingsw.LM6.server.game.timer;

import it.polimi.ingsw.LM6.server.game.LorenzoIlMagnifico;

public class Timer extends Thread{
	protected LorenzoIlMagnifico game;
	protected long time;
	
	public Timer(LorenzoIlMagnifico game, long time){
		this.game = game;
		this.time = time;
	}
	
	/**Sleeps for the given time. When it wakes up, it performs the implemented action on the game.
	 * 
	 * @author Emilio
	 */
	@Override
	public void run(){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			this.interrupt();
			return;
		}
		
		this.performAction();
	}
	
	/**	The action performed when the timer wakes up.
	 * 
	 * 	@author Emilio
	 */
	protected synchronized void performAction(){
		//This method is meant to be implemented by extending classes to define an action to perform at the end of the sleep phase.
	}
}