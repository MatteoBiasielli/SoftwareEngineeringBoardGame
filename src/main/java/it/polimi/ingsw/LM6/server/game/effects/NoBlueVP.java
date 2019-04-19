package it.polimi.ingsw.LM6.server.game.effects;

import it.polimi.ingsw.LM6.server.game.exceptions.NoVPException;

public class NoBlueVP extends Effect{
	String description;
	public NoBlueVP(String s){
		this.description=s;
	}
	@Override
	public String toString(){
		return this.description;
	}
	@Override
	public void applyBlueVPMalus() throws NoVPException{
		throw new NoVPException("You cannot get VP from blue cards");
	}
}