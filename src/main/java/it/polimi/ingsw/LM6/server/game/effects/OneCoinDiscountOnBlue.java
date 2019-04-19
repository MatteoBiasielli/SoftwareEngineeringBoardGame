package it.polimi.ingsw.LM6.server.game.effects;

import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;

public class OneCoinDiscountOnBlue extends Effect{
	String description;
	public OneCoinDiscountOnBlue(String s){
		this.description=s;
	}
	@Override
	public String toString(){
		return this.description;
	}
	@Override
	public void applyDiscountOnBlue(ResourceSet cost){
		cost.internalSub(new ResourceSet(0,0,1,0,0,0,0));
	}
}