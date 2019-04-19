package it.polimi.ingsw.LM6.server.network.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import it.polimi.ingsw.LM6.server.game.debug.Debug;
import it.polimi.ingsw.LM6.server.network.ConnectionSetupMethods;
import it.polimi.ingsw.LM6.server.network.SetupMessage;
import it.polimi.ingsw.LM6.server.users.RMIUser;

@SuppressWarnings("serial")
public class IConnectionSetupHandlerImpl extends UnicastRemoteObject implements IConnectionSetupHandler{
	private transient RMIUser user;
	
	public IConnectionSetupHandlerImpl(RMIUser user) throws RemoteException{
		this.user = user;
	}
	
	@Override
	public GameAction startNewGame(Boolean usingAdvRules, int maxNumOfPlayers) throws RemoteException{
		if(user != null){
			Debug.print(Boolean.toString(usingAdvRules));
			SetupMessage startNewGame = SetupMessage.buildStartNewGame(usingAdvRules, maxNumOfPlayers);
			ConnectionSetupMethods.manageGameStart(user, startNewGame);
			
			return user.getStubClientToServer();
		}
		return null;
	}
	
	@Override
	public void showScoreboard(int from, int to) throws RemoteException{
		if(user != null)
			ConnectionSetupMethods.showScoreboard(user, from, to);
		
		return;
	}
	
	@Override
	public void showRecord(String nicknameOfPlayerToFind) throws RemoteException{
		if(user != null)
			ConnectionSetupMethods.showRecord(user, nicknameOfPlayerToFind);	
		
		return;
	}
	
	@Override
	public void logout() throws RemoteException{
		ConnectionSetupMethods.logout(user);
		this.user = null;
	}
}
