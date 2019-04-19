package it.polimi.ingsw.LM6.server.game.card;

import it.polimi.ingsw.LM6.server.game.Action;
import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.game.board.TowerCardColor;
import it.polimi.ingsw.LM6.server.game.bonuses.Bonus;
import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;
import it.polimi.ingsw.LM6.server.game.exceptions.*;

public abstract class Card {
	private String name;
	private int number;
	private int era;
	private Bonus immediateBonus;
	private TowerCardColor color;
	public final String separator="ù";
	
	Card(String name, int number, int era, Bonus immediateBonus,TowerCardColor col){
		this.name=name;
		this.number=number;
		this.era=era;
		this.immediateBonus=immediateBonus;
		this.color=col;	
	}
	
	
	public Bonus getImmediateBonus() {
		return new Bonus(this.immediateBonus);
	}
	public String getName() {
		return name;
	
	}
	public Integer getEra(){
		return era;
	}
	public Integer getNumber(){return number;}
	@Override
	public abstract String toString();

	public abstract void giveCardTo( Player p);
	
	public abstract ResourceSet getCost(Action a) throws DoubleCostException, NotEnoughResourcesException;

	public abstract void conditions(Action action) throws NotEnoughMilitaryPointsException, SixCardsException;
}
