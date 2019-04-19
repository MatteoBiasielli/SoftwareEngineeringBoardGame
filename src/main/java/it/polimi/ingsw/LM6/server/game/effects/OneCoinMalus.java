package it.polimi.ingsw.LM6.server.game.effects;

import it.polimi.ingsw.LM6.server.game.Action;
import it.polimi.ingsw.LM6.server.game.bonuses.*;

public class OneCoinMalus extends Effect{
	String description;
	public OneCoinMalus(String s){
		this.description=s;
	}
	@Override
	public String toString(){
		return this.description;
	}
	@Override
	public void applyOn(Action a,Bonus b){
		b.setResourceSet(b.getResourceSetFor(a.getPlayer()).sub(new ResourceSet(0,0,1,0,0,0,0)));
	}
}