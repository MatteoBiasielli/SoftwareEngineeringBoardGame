package it.polimi.ingsw.LM6.server.game.effects;

import it.polimi.ingsw.LM6.server.game.Action;
import it.polimi.ingsw.LM6.server.game.bonuses.Bonus;
import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;

public class OneMPMalus extends Effect{
	String description;
	public OneMPMalus(String s){
		this.description=s;
	}
	@Override
	public String toString(){
		return this.description;
	}
	@Override
	public void applyOn(Action a,Bonus b){
		b.setResourceSet(b.getResourceSetFor(a.getPlayer()).sub(new ResourceSet(0,0,0,0,1,0,0)));
	}
}