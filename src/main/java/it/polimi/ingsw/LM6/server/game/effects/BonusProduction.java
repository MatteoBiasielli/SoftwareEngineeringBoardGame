package it.polimi.ingsw.LM6.server.game.effects;

import it.polimi.ingsw.LM6.server.game.bonuses.Bonus;

public class BonusProduction extends Effect{
	private String description;
	private Bonus everyTurnBonus;
	private boolean isUsed;
	public BonusProduction(Bonus b, String description){
		this.everyTurnBonus=b;
		this.description=description;
		this.isUsed=false;
	}
	@Override
	public String toString(){
		return this.description;
	}
	@Override
	public Bonus getEveryTurnProduction(){
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