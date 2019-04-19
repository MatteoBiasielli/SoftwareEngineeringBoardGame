package it.polimi.ingsw.LM6.server.game.exceptions;

public class NoVPException extends Exception{
	private final String message;
	public NoVPException(String m)
	{
		this.message=m;
	}
	@Override
	public String getMessage(){
		return message;
	}
}
