package it.polimi.ingsw.LM6.server.game.effects;

import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;


public class WoodOrStoneDiscountOnYellow extends Effect{
	String description;
	public WoodOrStoneDiscountOnYellow(String s){
		this.description=s;
	}
	@Override
	public String toString(){
		return this.description;
	}
	@Override
	public void applyDiscountOnYellow(int which,ResourceSet cost){
		if(which==1)
			cost.internalSub(new ResourceSet(1,0,0,0,0,0,0));
		else if(which==2)
			cost.internalSub(new ResourceSet(0,1,0,0,0,0,0));
	}
}