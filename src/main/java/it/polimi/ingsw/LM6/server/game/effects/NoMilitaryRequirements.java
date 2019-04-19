package it.polimi.ingsw.LM6.server.game.effects;

import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;

public class NoMilitaryRequirements extends Effect{
	String description;
	public NoMilitaryRequirements(String s){
		this.description=s;
	}
	@Override
	public String toString(){
		return this.description;
	}
	@Override
	public void applyMilitaryRequirementBonuses(ResourceSet milReq){
		milReq.setZero();
	}
}