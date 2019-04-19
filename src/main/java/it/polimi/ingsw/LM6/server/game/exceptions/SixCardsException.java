package it.polimi.ingsw.LM6.server.game.exceptions;


public class SixCardsException extends Exception{
	private final String message;
	public SixCardsException(String m)
	{
		this.message=m;
	}
	@Override
	public String getMessage(){
		return message;
	}
}
