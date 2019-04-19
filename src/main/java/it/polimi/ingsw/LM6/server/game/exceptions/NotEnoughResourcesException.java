package it.polimi.ingsw.LM6.server.game.exceptions;


public class NotEnoughResourcesException extends Exception{
	private final String message;
	public NotEnoughResourcesException(String m)
	{
		this.message=m;
	}
	@Override
	public String getMessage(){
		return message;
	}
}