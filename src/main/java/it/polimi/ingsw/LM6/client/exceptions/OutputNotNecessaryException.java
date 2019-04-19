package it.polimi.ingsw.LM6.client.exceptions;

public class OutputNotNecessaryException extends Exception{
	private final String message;
	public OutputNotNecessaryException(String m)
	{
		this.message=m;
	}
	@Override
	public String getMessage(){
		return message;
	}
}
