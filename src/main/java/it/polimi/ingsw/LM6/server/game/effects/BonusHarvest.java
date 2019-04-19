package it.polimi.ingsw.LM6.server.game.effects;

import it.polimi.ingsw.LM6.server.game.bonuses.Bonus;

public class BonusHarvest extends Effect{
	private String description;
	private Bonus everyTurnBonus;
	private boolean isUsed;
	public BonusHarvest(Bonus b, String description){
		this.everyTurnBonus=b;
		this.description=description;
		this.isUsed=false;
	}
	@Override
	public String toString(){
		return this.description;
	}
	@Override
	public Bonus getEveryTurnHarvest(){
		if(!isUsed){
			this.isUsed=true;
			return new Bonus(this.everyTurnBonus);
		}
		return new Bonus();
	}
	@Override
	public void resetEveryTurnBonuses(){
		this.isUsed=false;
	}

}
