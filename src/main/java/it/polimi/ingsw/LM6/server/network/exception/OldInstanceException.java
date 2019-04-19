package it.polimi.ingsw.LM6.server.network.exception;

import java.rmi.RemoteException;

@SuppressWarnings("serial")
public class OldInstanceException extends RemoteException{
	private final String message;

	public OldInstanceException(String message){
		this.message = message;
	}
	@Override
	public String getMessage(){
		return this.message;
	}

}
