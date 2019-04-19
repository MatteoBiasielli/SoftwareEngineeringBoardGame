package it.polimi.ingsw.LM6.server.game.effects;

import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;

public class FaithBonus extends Effect{
	String description;
	public FaithBonus(String s){
		this.description=s;
	}
	@Override
	public String toString(){
		return this.description;
	}
	@Override
	public void applyFaithBonus(Player p){
		p.acquireResources(new ResourceSet(0,0,0,0,0,5,0));
	}
}
