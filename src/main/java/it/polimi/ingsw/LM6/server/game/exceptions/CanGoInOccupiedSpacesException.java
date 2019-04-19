package it.polimi.ingsw.LM6.server.game.exceptions;

public class CanGoInOccupiedSpacesException extends Exception{
	private final String message;
	public CanGoInOccupiedSpacesException(String m)
	{
		this.message=m;
	}
	@Override
	public String getMessage(){
		return message;
	}
}