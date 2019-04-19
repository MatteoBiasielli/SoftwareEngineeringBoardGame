package it.polimi.ingsw.LM6.server.game.exceptions;

public class BadInputException extends RuntimeException{
	private final String message;
	public BadInputException(String m)
	{
		this.message=m;
	}
	@Override
	public String getMessage(){
		return message;
	}
}
