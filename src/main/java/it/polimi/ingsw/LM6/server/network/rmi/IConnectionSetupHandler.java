package it.polimi.ingsw.LM6.server.network.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IConnectionSetupHandler extends Remote{
	
	public GameAction startNewGame(Boolean usingAdvRules, int maxNumOfPlayer) throws RemoteException;
	
	public void showScoreboard(int from, int to) throws RemoteException;
	
	public void showRecord(String nicknameToFind) throws RemoteException;
	
	public void logout() throws RemoteException;
}
