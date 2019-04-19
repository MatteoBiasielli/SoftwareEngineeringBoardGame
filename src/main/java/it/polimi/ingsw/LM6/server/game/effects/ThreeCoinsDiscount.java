package it.polimi.ingsw.LM6.server.game.effects;

import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;

public class ThreeCoinsDiscount extends Effect{
	String description;
	public ThreeCoinsDiscount(String s){
		this.description=s;
	}
	@Override
	public String toString(){
		return this.description;
	}
	@Override
	public void applyDiscountOnBlue(ResourceSet cost){
		this.applyDiscount(cost);
	}
	@Override
	public void applyDiscountOnYellow(int which,ResourceSet cost){
		this.applyDiscount(cost);
	}
	@Override
	public void applyDiscountOnPurple(ResourceSet cost){
		this.applyDiscount(cost);
	}
	private void applyDiscount(ResourceSet cost){
		cost.internalSub(new ResourceSet(0,0,3,0,0,0,0));
	}
}