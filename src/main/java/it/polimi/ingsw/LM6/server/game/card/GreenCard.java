package it.polimi.ingsw.LM6.server.game.card;

import java.util.ArrayList;

import it.polimi.ingsw.LM6.server.game.Action;
import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.game.board.TowerCardColor;
import it.polimi.ingsw.LM6.server.game.bonuses.Bonus;
import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;
import it.polimi.ingsw.LM6.server.game.debug.Debug;
import it.polimi.ingsw.LM6.server.game.exceptions.NotEnoughMilitaryPointsException;
import it.polimi.ingsw.LM6.server.game.exceptions.SixCardsException;

public class GreenCard extends Card {
	private int harvestStrengthCondition;
	private Bonus harvestBonus;
	
	
	public GreenCard(String[] app){
		super(app[1],Integer.parseInt(app[0]),Integer.parseInt(app[2]),
				new Bonus(
						new ResourceSet(Integer.parseInt(app[3]),Integer.parseInt(app[4]),Integer.parseInt(app[5]),
						Integer.parseInt(app[6]),Integer.parseInt(app[8]),Integer.parseInt(app[7]),Integer.parseInt(app[9]))
						,false, 'N', false,'N', 0,null, false,false, 0, Integer.parseInt(app[10]),false )
				,TowerCardColor.GREEN);
		this.harvestStrengthCondition=Integer.parseInt(app[11]);
		this.harvestBonus= new Bonus(
									new ResourceSet(Integer.parseInt(app[12]),Integer.parseInt(app[13]),Integer.parseInt(app[14]),
											Integer.parseInt(app[15]),Integer.parseInt(app[17]),Integer.parseInt(app[16]),
											Integer.parseInt(app[18])),
									 false, 'N', false,'N', 0,null,false,false, 0, Integer.parseInt(app[19]),false);
		Debug.print("Carta "+ app[1] + " creata.");
	}


	/**Adds the card to the player's list of green cards.
	 * @Overrides Card.giveCardTo
	 */
	@Override
	public void giveCardTo( Player p) {
		ArrayList<GreenCard> app=p.getGreenCardList();
		app.add(this);
	}
	
	
	/**@return the absolute cost of the card
	 */
	public ResourceSet getCost() {
		return new ResourceSet(0,0,0,0,0,0,0);
	}
	
	/** calculates the real cost of the card relative to the Action object, given as parameter
	 * @param Action a - the action that has to be performed
	 * @return the real cost of the card, for the player contained in the parameter
	 */
	@Override
	public ResourceSet getCost(Action a){
		return this.getCost();
	}
	
	
	/** @return a string version of the card
	 * @Overrides Card.toString
	 */
	@Override
	public String toString() {
		return this.getNumber().toString() +separator+this.getName()+separator+this.getCost().toString()+separator+this.getImmediateBonus().toString()
				+separator+this.harvestBonus.toString()+" Str="+ Integer.toString(this.harvestStrengthCondition);
	
	}
	
	
	/**@return the harvest bonus
	 */
	public Bonus harvest() {
		return new Bonus(this.harvestBonus);
	}
	
	
	/**@return true if the strength condition to harvest is satisfied,
	 * false otherwise
	 * @param str - an integer value representing the familiar's production value
	 */
	public Boolean canHarvest(int str) {
		return str>=this.harvestStrengthCondition;
	}
	
	/** successfully completes ONLY if the player contained in the parameter satisfies the conditions to acquire the card
	 * @param action - Action object that contains all the data required to perform the action
	 * @throws SixCardsException if the player has already 6 green cards
	 * @throws NotEnoughMilitaryPointsException if the player doesn't have enough military points to acquire the green card
	 */
	@Override
	public void conditions(Action action) throws NotEnoughMilitaryPointsException, SixCardsException{
		ArrayList<GreenCard> app=action.getPlayer().getGreenCardList();
		int size=app.size();
		ResourceSet militaryReq=new ResourceSet(0,0,0,0,0,0,0);
		if(size>=6)
			throw new SixCardsException("You already have 6 green cards.");
		else if(size==2)
			militaryReq=new ResourceSet(0,0,0,0,3,0,0);
		else if(size==3)
			militaryReq=new ResourceSet(0,0,0,0,7,0,0);
		else if(size==4)
			militaryReq=new ResourceSet(0,0,0,0,12,0,0);
		else if(size==5)
			militaryReq=new ResourceSet(0,0,0,0,18,0,0);
		//NoMilitaryRequirementBonus application
		action.getPlayer().getPermanentBonusMalus().applyMilitaryRequirementBonuses(militaryReq);

		if(!action.getPlayer().getResourceSet().contains(militaryReq))
			throw new NotEnoughMilitaryPointsException("You don't have Enough Military Points");
	}
}
