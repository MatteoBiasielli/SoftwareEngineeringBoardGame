package it.polimi.ingsw.LM6.server.game.exceptions;


public class AlreadyOccupiedException extends Exception{
	private final String message;
	public AlreadyOccupiedException(String m)
	{
		this.message=m;
	}
	@Override
	public String getMessage(){
		return message;
	}
}