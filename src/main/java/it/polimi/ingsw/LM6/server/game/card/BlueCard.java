package it.polimi.ingsw.LM6.server.game.card;

import java.util.ArrayList;

import it.polimi.ingsw.LM6.server.game.Action;
import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.game.board.TowerCardColor;
import it.polimi.ingsw.LM6.server.game.bonuses.Bonus;
import it.polimi.ingsw.LM6.server.game.bonuses.PermanentBonusMalus;
import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;
import it.polimi.ingsw.LM6.server.game.debug.Debug;
import it.polimi.ingsw.LM6.server.game.exceptions.SixCardsException;
public class BlueCard extends Card {
	private ResourceSet cost;
	private PermanentBonusMalus permanentBonus;
	
	
	public BlueCard(String[] app){
		super(app[1],Integer.parseInt(app[0]),Integer.parseInt(app[2]),
				new Bonus(
						new ResourceSet(0,0,0,0,Integer.parseInt(app[4]),Integer.parseInt(app[6]),Integer.parseInt(app[5]))
						, Boolean.parseBoolean(app[9]), app[10].charAt(0), Boolean.parseBoolean(app[11]),app[12].charAt(0), 
						Integer.parseInt(app[13]),
						new ResourceSet(Integer.parseInt(app[14]),Integer.parseInt(app[15]),Integer.parseInt(app[16]),0,0,0,0),
						Boolean.parseBoolean(app[17]),Boolean.parseBoolean(app[18]), Integer.parseInt(app[19]), 
						Integer.parseInt(app[7]),Boolean.parseBoolean(app[8])), TowerCardColor.BLUE);
		this.cost=new ResourceSet(0,0,Integer.parseInt(app[3]),0,0,0,0);
		this.permanentBonus= new PermanentBonusMalus(Integer.parseInt(app[25]), Integer.parseInt(app[24]),
				Integer.parseInt(app[23]),Integer.parseInt(app[21]),Integer.parseInt(app[20]),Integer.parseInt(app[22]),
				0,0,0,0,app[26]);
		Debug.print("Carta "+ app[1] + " creata.");

	}


	/**Adds the card to the player's list of blue cards. Assigns the PermanentBonusMalus contained in this card to teh player.
	 * @Overrides Card.giveCardTo
	 */
	@Override
	public void giveCardTo( Player p) {
		ArrayList<BlueCard> app=p.getBlueCardList();
		p.acquirePermBonus(this.permanentBonus);
		app.add(this);
	}
	
	
	/**@return the absolute cost of the card
	 * @Overrides Card.getCost
	 */
	public ResourceSet getCost() {
		return cost;
	
	}
	
	
	/** calculates the real cost of the card relative to the Action object, given as parameter
	 * @param Action a - the action that has to be performed
	 * @return the real cost of the card, for the player contained in the parameter
	 */
	@Override
	public ResourceSet getCost(Action a){
		ResourceSet cost2=new ResourceSet(cost);
		a.getPlayer().getPermanentBonusMalus().applyDiscountOnBlue(cost2);
		return cost2;
	}
	
	/** @return a string version of the card
	 * @Overrides Card.toString
	 */
	@Override
	public String toString() {
		return this.getNumber().toString() +separator+this.getName()+separator+this.getCost().toString()+separator+this.getImmediateBonus().toString()
				+separator+this.permanentBonus.toString();
	
	}
	
	/** succesfully completes ONLY if the player contained in the parameter satisfies the conditions to acquire the card
	 * @param action - Action object that contains all the data required to perform the action
	 * @throws SixCardsException if the player has already 6 blue cards
	 */
	@Override
	public void conditions(Action action)throws SixCardsException{
		ArrayList<BlueCard> app=action.getPlayer().getBlueCardList();
		int size=app.size();
		if(size>=6)
			throw new SixCardsException("You already ahve 6 blue cards.");
	}
	
}
