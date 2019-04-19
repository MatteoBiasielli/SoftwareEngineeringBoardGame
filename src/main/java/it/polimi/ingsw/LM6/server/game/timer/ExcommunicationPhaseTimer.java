package it.polimi.ingsw.LM6.server.game.timer;

import it.polimi.ingsw.LM6.server.game.LorenzoIlMagnifico;

public class ExcommunicationPhaseTimer extends Timer {

	public ExcommunicationPhaseTimer(LorenzoIlMagnifico game, long time){
		super(game, time);
	}
	
	/**	Ends the excommunication phase when the time is over.
	 * 
	 * 	@author Emilio
	 */
	@Override
	protected synchronized void performAction(){
		game.excommunicationPhaseEnd();
	}
}