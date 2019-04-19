package it.polimi.ingsw.LM6.server.network.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.ingsw.LM6.client.UserOperationStub;
@FunctionalInterface
public interface IWelcome extends Remote{
	
	public IConnectionSetupHandler connect(String nickname, String password, UserOperationStub stub) throws RemoteException;
}
