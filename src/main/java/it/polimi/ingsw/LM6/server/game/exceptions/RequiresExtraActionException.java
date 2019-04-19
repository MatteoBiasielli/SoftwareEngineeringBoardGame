package it.polimi.ingsw.LM6.server.game.exceptions;

public class RequiresExtraActionException extends Exception{
	private final String message;
	public RequiresExtraActionException(String m)
	{
		this.message=m;
	}
	@Override
	public String getMessage(){
		return message;
	}
}

