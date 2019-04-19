package it.polimi.ingsw.LM6.server.game.exceptions;



public class CannotGoOnMarketException extends Exception{
	private final String message;
	public CannotGoOnMarketException(String m)
	{
		this.message=m;
	}
	@Override
	public String getMessage(){
		return message;
	}
}