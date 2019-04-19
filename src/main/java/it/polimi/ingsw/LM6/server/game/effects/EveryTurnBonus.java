package it.polimi.ingsw.LM6.server.game.effects;

import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.game.bonuses.Bonus;


public class EveryTurnBonus extends Effect{
	private String description;
	private Bonus everyTurnBonusValues;
	private boolean isUsed;
	public EveryTurnBonus(Bonus b, String description){
		this.everyTurnBonusValues=b;
		this.description=description;
		this.isUsed=false;
	}
	@Override
	public String toString(){
		return this.description;
	}
	@Override
	public Bonus getEveryTurnResources(Player p){
		if(!isUsed){
			this.isUsed=true;
			return new Bonus(this.everyTurnBonusValues);
		}
		return new Bonus();
	}
	@Override
	public void resetEveryTurnBonuses(){
		this.isUsed=false;
	}

}
