package it.polimi.ingsw.LM6.server.game.exceptions;

public class InvalidActionException extends Throwable{
	private final String message;
	public InvalidActionException(String m)
	{
		this.message=m;
	}
	@Override
	public String getMessage(){
		return message;
	}
}
