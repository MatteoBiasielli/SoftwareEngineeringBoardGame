package it.polimi.ingsw.LM6.server.game.effects;

import it.polimi.ingsw.LM6.server.game.Action;

public class DoubleServantMalus extends Effect{
	String description;
	public DoubleServantMalus(String s){
		this.description=s;
	}
	@Override
	public String toString(){
		return this.description;
	}
	@Override
	public void applyOn(Action action){
		action.setStrength(action.getActionStrength()-(int)((action.getServants()+1)/2));
		action.setServants(action.getServants()-action.getServants()%2);
	}
}
