package it.polimi.ingsw.LM6.server.game.exceptions;

import java.util.ArrayList;

public class LorenzoIlMagnificoException extends Exception{
	private final String message;
	private final ArrayList<String> playedLeaders;
	public LorenzoIlMagnificoException(String m, ArrayList<String> leaders)
	{
		this.message=m;
		this.playedLeaders=leaders;
	}
	@Override
	public String getMessage(){
		return message;
	}
	public ArrayList<String> getLeaders(){
		return this.playedLeaders;
	}
}
