package it.polimi.ingsw.LM6.server.game.effects;

import it.polimi.ingsw.LM6.server.game.exceptions.CannotGoOnMarketException;

public class CannotUseMarketMalus extends Effect{
	String description;
	public CannotUseMarketMalus(String s){
		this.description=s;
	}
	@Override
	public String toString(){
		return this.description;
	}
	@Override
	public void applyMarketMaluses() throws CannotGoOnMarketException{
		throw new CannotGoOnMarketException("You can't use the market.");
	}
}