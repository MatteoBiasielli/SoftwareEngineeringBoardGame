package it.polimi.ingsw.LM6.server.game.exceptions;



public class DoubleCostException extends Exception{
	private final String message;
	public DoubleCostException(String m)
	{
		this.message=m;
	}
	@Override
	public String getMessage(){
		return message;
	}
}
