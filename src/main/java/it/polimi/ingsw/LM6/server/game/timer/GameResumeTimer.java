package it.polimi.ingsw.LM6.server.game.timer;

import it.polimi.ingsw.LM6.server.game.LorenzoIlMagnifico;

public class GameResumeTimer extends Timer{

	public GameResumeTimer(LorenzoIlMagnifico game, long time){
		super(game, time);
	}
	
	/**	Let the game resume if the time is over.
	 * 
	 * 	@author Emilio
	 */
	@Override
	protected synchronized void performAction(){
		if(!game.isStarted() && game.getStatus().getNumberOfConnectedUsers() >= 2)
			game.resumeGame();
	}
}
