package it.polimi.ingsw.LM6.server.game.exceptions;



public class AlreadyUsedBonusException extends Exception{
	private final String message;
	public AlreadyUsedBonusException(String m)
	{
		this.message=m;
	}
	@Override
	public String getMessage(){
		return message;
	}
}