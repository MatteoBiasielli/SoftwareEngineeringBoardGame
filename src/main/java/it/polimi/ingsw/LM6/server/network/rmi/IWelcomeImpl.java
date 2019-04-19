package it.polimi.ingsw.LM6.server.network.rmi;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import it.polimi.ingsw.LM6.client.UserOperationStub;
import it.polimi.ingsw.LM6.server.network.ConnectionSetupMethods;
import it.polimi.ingsw.LM6.server.network.SetupMessage;
import it.polimi.ingsw.LM6.server.network.exception.InvalidFormatException;
import it.polimi.ingsw.LM6.server.users.RMIUser;


@SuppressWarnings("serial")
public class IWelcomeImpl extends UnicastRemoteObject implements IWelcome {
	
	public IWelcomeImpl() throws RemoteException {
		//THERE IS NOTHING TO INITIALZE
	}
	
	@Override
	public IConnectionSetupHandler connect(String username, String password, UserOperationStub stub) throws RemoteException{
		RMIUser user = new RMIUser(stub);
		SetupMessage message = null;
		IConnectionSetupHandler setup = null;
		
		try{
			message = SetupMessage.buildLoginRequest(username, password);
		} catch (InvalidFormatException e){
			SetupMessage invalidLogin = SetupMessage.buildInvalidLogin("Invalid format for username or password: only letters and numbers allowed.");
			invalidLogin.send(user);
			return setup;
		}
		
		try{
			ConnectionSetupMethods.userAuthenticationProcedure(user, message);
		} catch(IOException e){
			SetupMessage invalidLogin = SetupMessage.buildInvalidLogin("Login failed due to system failure.");
			invalidLogin.send(user);
			return setup;
		}
		
		if(user.getNickname() != null){
			setup = new IConnectionSetupHandlerImpl(user);
			SetupMessage setupMenu = SetupMessage.buildSetupMenu(" ", " ");
			setupMenu.send(user);
		}
		
		return setup;
	}

}
