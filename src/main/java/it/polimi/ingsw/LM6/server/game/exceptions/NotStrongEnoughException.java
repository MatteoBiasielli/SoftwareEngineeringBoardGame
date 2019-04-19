package it.polimi.ingsw.LM6.server.game.exceptions;


public class NotStrongEnoughException extends Exception{
	private final String message;
	public NotStrongEnoughException(String m)
	{
		this.message=m;
	}
	@Override
	public String getMessage(){
		return message;
	}
}
