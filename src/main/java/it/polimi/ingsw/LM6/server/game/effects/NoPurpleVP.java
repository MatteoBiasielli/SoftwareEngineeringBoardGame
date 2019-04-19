package it.polimi.ingsw.LM6.server.game.effects;

import it.polimi.ingsw.LM6.server.game.exceptions.NoVPException;

public class NoPurpleVP extends Effect{
	String description;
	public NoPurpleVP(String s){
		this.description=s;
	}
	@Override
	public String toString(){
		return this.description;
	}
	@Override
	public void applyPurpleVPMalus() throws NoVPException{
		throw new NoVPException("You cannot get VP from purple cards");
	}
}