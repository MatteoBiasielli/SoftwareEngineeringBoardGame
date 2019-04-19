package it.polimi.ingsw.LM6.server.game.effects;

import it.polimi.ingsw.LM6.server.game.Action;
import it.polimi.ingsw.LM6.server.game.bonuses.Bonus;
import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;

public class DoubleWoodStoneCoinsServantsFromCards extends Effect{
	String description;
	public DoubleWoodStoneCoinsServantsFromCards(String s){
		this.description=s;
	}
	@Override
	public String toString(){
		return this.description;
	}
	@Override
	public void applyOnCardsBonus(Action a,Bonus b){
		int w;
		int st;
		int c;
		int se;
		w=b.getResourceSetFor(a.getPlayer()).getWood();
		st=b.getResourceSetFor(a.getPlayer()).getStone();
		c=b.getResourceSetFor(a.getPlayer()).getCoin();
		se=b.getResourceSetFor(a.getPlayer()).getServants();
		b.setResourceSet(new ResourceSet(w*2,st*2,c*2,se*2,b.getResourceSetFor(a.getPlayer()).getMilitaryPoints(),
															b.getResourceSetFor(a.getPlayer()).getVictoryPoints(),
															b.getResourceSetFor(a.getPlayer()).getFaithPoints()));
	}
}