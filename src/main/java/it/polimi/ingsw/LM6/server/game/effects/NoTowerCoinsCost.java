package it.polimi.ingsw.LM6.server.game.effects;

import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;

public class NoTowerCoinsCost extends Effect{
	String description;
	public NoTowerCoinsCost(String s){
		this.description=s;
	}
	@Override
	public String toString(){
		return this.description;
	}
	@Override
	public void applyNoTowerCoinsCost(ResourceSet coinsCost){
		coinsCost.setZero();
	}
}
