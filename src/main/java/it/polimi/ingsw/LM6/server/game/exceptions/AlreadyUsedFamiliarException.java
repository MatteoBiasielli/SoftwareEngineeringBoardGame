package it.polimi.ingsw.LM6.server.game.exceptions;



public class AlreadyUsedFamiliarException extends Exception{
	private final String message;
	public AlreadyUsedFamiliarException(String m)
	{
		this.message=m;
	}
	@Override
	public String getMessage(){
		return message;
	}
}
