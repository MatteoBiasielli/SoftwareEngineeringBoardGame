package it.polimi.ingsw.LM6.server.game.effects;

import java.util.ArrayList;

import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;
import it.polimi.ingsw.LM6.server.game.card.YellowCard;

public class LoseOneVPEveryWoodStoneYellowCost extends Effect{
	String description;
	public LoseOneVPEveryWoodStoneYellowCost(String s){
		this.description=s;
	}
	@Override
	public String toString(){
		return this.description;
	}
	@Override
	public void applyVPNumericalMaluses(Player p){
		ArrayList<YellowCard> yellowcards=p.getYellowCardList();
		ResourceSet rs=p.getResourceSet();
		for(YellowCard y: yellowcards){
			ResourceSet cost=y.getCost();
			rs.internalSub(new ResourceSet(0,0,0,0,0,cost.getStone() + cost.getWood() ,0));
		}
	}
	
}
