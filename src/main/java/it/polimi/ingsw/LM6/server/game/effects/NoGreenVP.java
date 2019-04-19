package it.polimi.ingsw.LM6.server.game.effects;

import it.polimi.ingsw.LM6.server.game.exceptions.NoVPException;

public class NoGreenVP extends Effect{
	String description;
	public NoGreenVP(String s){
		this.description=s;
	}
	@Override
	public String toString(){
		return this.description;
	}
	@Override
	public void applyGreenVPMalus() throws NoVPException{
		throw new NoVPException("You cannot get VP from green cards");
	}
}
