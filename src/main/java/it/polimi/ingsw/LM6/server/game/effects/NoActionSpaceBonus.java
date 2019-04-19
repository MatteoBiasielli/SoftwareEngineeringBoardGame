package it.polimi.ingsw.LM6.server.game.effects;

import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;

public class NoActionSpaceBonus extends Effect{
	String description;
	public NoActionSpaceBonus(String s){
		this.description=s;
	}
	@Override
	public String toString(){
		return this.description;
	}
	@Override
	public void applyNoActionSpaceBonus(ResourceSet bonus) {
		bonus.setZero();
	}

}
