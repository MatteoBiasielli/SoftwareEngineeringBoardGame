package it.polimi.ingsw.LM6.server.game.exceptions;



public class LeaderException extends Exception{
	private final String message;
	public LeaderException(String m)
	{
		this.message=m;
	}
	@Override
	public String getMessage(){
		return message;
	}
}