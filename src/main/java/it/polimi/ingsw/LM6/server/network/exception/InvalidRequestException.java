package it.polimi.ingsw.LM6.server.network.exception;

import java.rmi.RemoteException;

@SuppressWarnings("serial")
public class InvalidRequestException extends RemoteException{
	private final String message;

	
	public InvalidRequestException(String message){
		this.message = message;
	}
	@Override
	public String getMessage(){
		return this.message;
	}
}
