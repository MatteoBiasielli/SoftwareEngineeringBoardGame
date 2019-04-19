package it.polimi.ingsw.LM6.client.exceptions;

public class TerminatedException extends RuntimeException{
	private final String message;
	public TerminatedException(String s){
		this.message=s;
	}
	@Override
	public String getMessage(){
		return this.message;
	}
}
