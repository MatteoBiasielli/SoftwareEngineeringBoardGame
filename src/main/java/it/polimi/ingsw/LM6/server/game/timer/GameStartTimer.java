package it.polimi.ingsw.LM6.server.game.timer;

import it.polimi.ingsw.LM6.server.game.LorenzoIlMagnifico;

public class GameStartTimer extends Timer {

	public GameStartTimer(LorenzoIlMagnifico game, long time){
		super(game, time);
	}
	
	/**	Let the game start if the time is over.
	 * 
	 * 	@author Emilio
	 */
	@Override
	protected synchronized void performAction(){
		//Removed connectedusers>2, explain to emilio
		if(!game.isStarted())
			game.initGame();
	}
}