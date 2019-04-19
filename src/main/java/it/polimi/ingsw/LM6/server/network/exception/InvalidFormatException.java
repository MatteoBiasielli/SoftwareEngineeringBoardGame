package it.polimi.ingsw.LM6.server.network.exception;

@SuppressWarnings("serial")
public class InvalidFormatException extends RuntimeException{
	private final String message;
	
	public InvalidFormatException(){
		this.message = null;
	}
	
	public InvalidFormatException(String message){
		this.message = message;
	}
	@Override
	public String getMessage(){
		return this.message;
	}
}
