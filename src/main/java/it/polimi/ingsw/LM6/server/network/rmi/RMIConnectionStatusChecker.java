package it.polimi.ingsw.LM6.server.network.rmi;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.LM6.server.game.LorenzoIlMagnifico;
import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.users.*;

public class RMIConnectionStatusChecker extends Thread{
	private static final int GRANULARITYMILLIS = 1000;
	private Player player;
	private LorenzoIlMagnifico game;
	
	public RMIConnectionStatusChecker(LorenzoIlMagnifico game, Player player){
		this.player = player;
		this.game = game;
	}
	
	@Override
	public void run(){
		this.player.send("INFO;Sei stato aggiunto a una partita.");
		try{
			while(true){
				try {
					Thread.sleep(GRANULARITYMILLIS);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				
				((RMIUser)(player.getUser())).poke();
				
			}
		} catch(RemoteException e){
			Logger.getLogger("MyLogger").log(Level.INFO, "RMI Player disconnected");
			((RMIUser)(player.getUser())).deleteStubServerToClient();
			this.player.setIsActive(false);
			this.player.setIsConnected(false);
			game.sendAll("Il player "+this.player.getNickname()+ " è inattivo.");
			boolean isGamePhase=!this.game.isExcommunicationPhase() && !this.game.isPersonalBonusTilesPhase() && !this.game.isLeaderDraftPhase();
			if(game.isStarted() && isGamePhase && game.isTurn(player) && !this.game.isFinished())
				this.game.playerShift();
		}
	}
}
