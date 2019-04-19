package it.polimi.ingsw.LM6.server.game;

public class NotBonusException extends Exception{
	private final String message;
	
	public NotBonusException(String s){
		this.message= new String(s);
	}
	@Override
	public String getMessage(){
		return this.message;
	}
}
