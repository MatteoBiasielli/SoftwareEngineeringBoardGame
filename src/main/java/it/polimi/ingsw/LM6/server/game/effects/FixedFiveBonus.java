package it.polimi.ingsw.LM6.server.game.effects;

import it.polimi.ingsw.LM6.server.game.Player;


public class FixedFiveBonus extends Effect{
	String description;
	public FixedFiveBonus(String s){
		this.description=s;
	}
	@Override
	public String toString(){
		return this.description;
	}
	@Override
	public void applyFixedFiveBonus(Player p){
		p.setFamiliarsValues(5, 5, 5, 0);
	}
}
