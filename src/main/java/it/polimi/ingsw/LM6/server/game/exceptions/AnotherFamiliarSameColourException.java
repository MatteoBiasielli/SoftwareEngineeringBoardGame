package it.polimi.ingsw.LM6.server.game.exceptions;



public class AnotherFamiliarSameColourException extends Exception{
	private final String message;
	public AnotherFamiliarSameColourException(String m)
	{
		this.message=m;
	}
	@Override
	public String getMessage(){
		return message;
	}
}