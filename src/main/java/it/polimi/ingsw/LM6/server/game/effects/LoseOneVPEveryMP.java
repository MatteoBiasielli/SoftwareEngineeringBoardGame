package it.polimi.ingsw.LM6.server.game.effects;

import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;

public class LoseOneVPEveryMP extends Effect{
	String description;
	public LoseOneVPEveryMP(String s){
		this.description=s;
	}
	@Override
	public String toString(){
		return this.description;
	}
	@Override
	public void applyVPNumericalMaluses(Player p){
		ResourceSet rs=p.getResourceSet();
		rs.internalSub(new ResourceSet(0,0,0,0,0,rs.getMilitaryPoints(),0));
	}
}