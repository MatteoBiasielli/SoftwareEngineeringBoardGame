package it.polimi.ingsw.LM6.server.game.exceptions;


public class NotEnoughMilitaryPointsException  extends Exception{
	private final String message;
	public NotEnoughMilitaryPointsException(String m)
	{
		this.message=m;
	}
	@Override
	public String getMessage(){
		return message;
	}
}