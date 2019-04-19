package it.polimi.ingsw.LM6.server.game.card;

import java.util.ArrayList;

import it.polimi.ingsw.LM6.server.game.Action;
import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.game.board.TowerCardColor;
import it.polimi.ingsw.LM6.server.game.bonuses.Bonus;
import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;
import it.polimi.ingsw.LM6.server.game.debug.Debug;
import it.polimi.ingsw.LM6.server.game.exceptions.*;

public class PurpleCard extends Card {
	private ResourceSet finalBonus;
	private Boolean hasAlternativeCost;
	
	private Boolean hasMPRequirement;
	private int militaryPointsRequirement;
	private ResourceSet firstCost;
	
	private ResourceSet secondCost;
	
	public PurpleCard(String[] app) {
		super(app[1],Integer.parseInt(app[0]),Integer.parseInt(app[2]),
				new Bonus(	new ResourceSet(Integer.parseInt(app[11]),Integer.parseInt(app[12]),Integer.parseInt(app[13]),
							Integer.parseInt(app[14]),Integer.parseInt(app[15]),0,Integer.parseInt(app[16])),
						 false, 'N', Boolean.parseBoolean(app[22]),app[23].charAt(0), Integer.parseInt(app[24]),null,
						Boolean.parseBoolean(app[20]),Boolean.parseBoolean(app[19]), Integer.parseInt(app[21]), 
						Integer.parseInt(app[17]),Boolean.parseBoolean(app[18])), TowerCardColor.PURPLE);
		this.hasAlternativeCost=Boolean.parseBoolean(app[7]);
		this.hasMPRequirement=Boolean.parseBoolean(app[8]);
		this.finalBonus=new ResourceSet(0,0,0,0,0,Integer.parseInt(app[25]),0);
		this.militaryPointsRequirement=Integer.parseInt(app[9]);
		if(hasMPRequirement){
			this.firstCost=new ResourceSet(0,0,0,0,Integer.parseInt(app[10]),0,0);
		}
		else{
			this.firstCost=new ResourceSet(Integer.parseInt(app[3]),Integer.parseInt(app[4]),
											Integer.parseInt(app[5]),Integer.parseInt(app[6]),0,0,0);
		}
		if(hasAlternativeCost){
			this.secondCost=new ResourceSet(Integer.parseInt(app[3]),Integer.parseInt(app[4]),
					Integer.parseInt(app[5]),Integer.parseInt(app[6]),0,0,0);
		}
		Debug.print("Carta "+ app[1] + " creata.");
	}


	/**Adds the card to the player's list of purple cards
	 * @Overrides Card.giveCardTo
	 */
	@Override
	public void giveCardTo( Player p) {
		ArrayList<PurpleCard> app=p.getPurpleCardList();
		app.add(this);
	}
	
	
	/** calculates the real cost of the card relative to the Action object, given as parameter
	 * @param Action a - the action that has to be performed
	 * @throws NotEnoughResourcesException if the military requirement (if present) is not satisifed and the military cost is the only possible one or the one chosen by the player
	 * @throws DoubleCostException if the cars has two alternative costs and it's not possible to determine which one the player has to pay
	 * @return the real cost of the card, for the player contained in the action parameter
	 */
	@Override
	public ResourceSet getCost(Action a) throws DoubleCostException, NotEnoughResourcesException{
		ResourceSet milReq=new ResourceSet(0,0,0,0,this.militaryPointsRequirement,0,0);
		ResourceSet firstCost1=new ResourceSet(this.firstCost);
		a.getPlayer().getPermanentBonusMalus().applyDiscountOnPurple(firstCost1);
		ResourceSet secondCost1=new ResourceSet(this.secondCost);
		a.getPlayer().getPermanentBonusMalus().applyDiscountOnPurple(secondCost1);
		if(!this.hasAlternativeCost && !this.hasMPRequirement)
			return firstCost1;
		if(!this.hasAlternativeCost &&this.hasMPRequirement){
			if(a.getPlayer().getResourceSet().contains(milReq))
				return firstCost1;
			else
				throw new NotEnoughResourcesException("You don't have enough resources.");
		}
		if(this.hasAlternativeCost){
			if(a.getPlayer().getResourceSet().contains(milReq)){
				if(a.isWhichCostRelevant() && a.getCost()==1)
					return firstCost1;
				if(a.isWhichCostRelevant() && a.getCost()==2)
					return secondCost1;
			}
			else{
				return secondCost1;
			}
		}
		throw new DoubleCostException("You have to decide between the MP cost and the resources cost");
	}
	
	
	/** @return a string version of the card
	 * @Overrides Card.toString
	 */
	@Override
	public String toString() {
		String app= this.getNumber().toString() +separator+this.getName()+separator;
		if(this.hasMPRequirement){
			app+="Req="+Integer.toString(this.militaryPointsRequirement)+"MP "+"Cost="+this.firstCost.toString();
			
		}
		else
			app+=firstCost.toString();
		if(this.hasAlternativeCost){
			app+="Cost2= "+secondCost.toString();
		}
		app+=separator+this.getImmediateBonus().toString()+separator+this.finalBonus.toString();
		return app;
	
	}
	
	
	/**@return the final bonus of the card
	 * 
	 */
	public ResourceSet getFinalBonus() {
		return finalBonus;
	
	}
	
	/** succesfully completes ONLY if the player contained in the parameter satisfies the conditions to acquire the card
	 * @param action - Action object that contains all the data required to perform the action
	 * @throws SixCardsException if the player has already 6 purple cards
	 */
	@Override
	public void conditions(Action action)throws SixCardsException{
		ArrayList<PurpleCard> app=action.getPlayer().getPurpleCardList();
		int size=app.size();
		if(size>=6)
			throw new SixCardsException("You already ahve 6 purple cards.");
	}
}
