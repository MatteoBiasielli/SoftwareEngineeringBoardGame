package it.polimi.ingsw.LM6.server.game.timer;

import it.polimi.ingsw.LM6.server.game.LorenzoIlMagnifico;
import it.polimi.ingsw.LM6.server.game.Player;

public class PlayerTurnTimer extends Timer{
	private int playerTurnAtStart;
	private int gameTurnAtStart;

	public PlayerTurnTimer(LorenzoIlMagnifico game, long time){
		super(game, time);
		this.playerTurnAtStart = game.getStatus().getCurrentPlayerIndex();
		this.gameTurnAtStart = game.getStatus().getRound();
	}
	
	/**	Applies a player shift if the player's turn is over.
	 * 
	 * 	@author Emilio
	 */
	@Override
	protected synchronized void performAction(){
		int playerTurnAtFinish = game.getStatus().getCurrentPlayerIndex();
		int gameTurnAtFinish = game.getStatus().getRound();
		if(this.playerTurnAtStart == playerTurnAtFinish && this.gameTurnAtStart == gameTurnAtFinish){
			Player currentPlayer = game.getPlayer(game.getStatus().getCurrentPlayerIndex());
			currentPlayer.setIsActive(false);
			game.sendAll("The player "+ currentPlayer.getNickname()+" is inactive.");
			game.playerShift();
		}
	}
}