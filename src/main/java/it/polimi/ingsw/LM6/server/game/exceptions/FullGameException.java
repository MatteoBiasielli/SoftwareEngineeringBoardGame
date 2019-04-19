package it.polimi.ingsw.LM6.server.game.exceptions;

public class FullGameException extends Exception{
	private final String message;
	public FullGameException(String m)
	{
		this.message=m;
	}
	@Override
	public String getMessage(){
		return message;
	}
}
