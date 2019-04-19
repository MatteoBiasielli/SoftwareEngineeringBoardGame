package it.polimi.ingsw.LM6.server.game.card;

import java.util.ArrayList;

import it.polimi.ingsw.LM6.server.game.Action;
import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.game.board.TowerCardColor;
import it.polimi.ingsw.LM6.server.game.bonuses.Bonus;
import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;
import it.polimi.ingsw.LM6.server.game.debug.Debug;
import it.polimi.ingsw.LM6.server.game.exceptions.DoubleCostException;
import it.polimi.ingsw.LM6.server.game.exceptions.SixCardsException;

public class YellowCard extends Card {
	private ResourceSet cost;
	private int productionStrengthCondition;
	private Boolean hasTwoProductions;
	private Production firstProduction;
	private Production secondProduction;
	
	public YellowCard(String[] app) {
		super(app[1],Integer.parseInt(app[0]),Integer.parseInt(app[2]),
				new Bonus(new ResourceSet(0,0,0,0,0,Integer.parseInt(app[7]),Integer.parseInt(app[8])),
						 false, 'N', false,'N', 0,null,
						false,false,0, 0,false),TowerCardColor.YELLOW);
		this.cost=new ResourceSet(Integer.parseInt(app[3]),Integer.parseInt(app[4]),Integer.parseInt(app[5]),Integer.parseInt(app[6]),0,0,0);
		this.productionStrengthCondition=Integer.parseInt(app[9]);
		this.hasTwoProductions=Boolean.parseBoolean(app[28]);
		ResourceSet cost1=new ResourceSet(Integer.parseInt(app[13]),Integer.parseInt(app[14]),Integer.parseInt(app[15])
							,Integer.parseInt(app[16]),0,0,Integer.parseInt(app[17]));
		Bonus gain1=new Bonus(new ResourceSet(Integer.parseInt(app[18]),Integer.parseInt(app[19]),Integer.parseInt(app[20]),
				Integer.parseInt(app[21]),Integer.parseInt(app[22]),Integer.parseInt(app[24]),Integer.parseInt(app[23])),
				 Boolean.parseBoolean(app[12]),app[27].charAt(0),false,'N', 0,null,
				false,false, 0, Integer.parseInt(app[25]),Boolean.parseBoolean(app[26]));
		ResourceSet cost2=new ResourceSet(Integer.parseInt(app[29]),Integer.parseInt(app[30]),Integer.parseInt(app[31])
				,Integer.parseInt(app[32]),0,0,Integer.parseInt(app[33]));
		Bonus gain2=new Bonus(new ResourceSet(Integer.parseInt(app[34]),Integer.parseInt(app[35]),Integer.parseInt(app[36])
				,Integer.parseInt(app[37]),Integer.parseInt(app[38]),Integer.parseInt(app[40]),Integer.parseInt(app[39])),
				false, 'N', false,'N', 0,null,
				false,false,0, 0,false);
		if(hasTwoProductions){
			this.firstProduction= new ExchangeProduction(gain1,cost1);
			this.secondProduction= new ExchangeProduction(gain2,cost2);
		}
		else if(Boolean.parseBoolean(app[11])){
			this.firstProduction= new ExchangeProduction(gain1,cost1);
			this.secondProduction=null;
		}
		else{
			this.firstProduction= new FixedProduction(gain1);
			this.secondProduction=null;
		}
		Debug.print("Carta "+ app[1] + " creata.");
	}


	/**Adds the card to the player's list of yellor cards
	 * @Overrides Card.giveCardTo
	 */
	@Override
	public void giveCardTo( Player p) {
		ArrayList<YellowCard> app=p.getYellowCardList();
		app.add(this);
	}
	
	
	/**@return the absolute cost of the card
	 * @Overrides Card.getCost
	 */
	public ResourceSet getCost() {
		return this.cost;
	
	}
	
	/** calculates the real cost of the card relative to the Action object, given as parameter
	 * @param Action a - the action that has to be performed
	 * @throws DoubleCostException if the cars has two alternative costs and it's not possible to determine which one the player has to pay
	 * @return the real cost of the card, for the player contained in the action parameter
	 */
	@Override
	public ResourceSet getCost(Action a) throws DoubleCostException{
		ResourceSet cost2=new ResourceSet(cost);
		ResourceSet cost3=new ResourceSet(cost);
		if(a.isWhichCostRelevant()){
			a.getPlayer().getPermanentBonusMalus().applyDiscountOnYellow(a.getCost(), cost2);
			return cost2;
		}
		else if(cost.getStone()==0){
			a.getPlayer().getPermanentBonusMalus().applyDiscountOnYellow(1, cost2);
			return cost2;
		}
		else if(cost.getWood()==0){
			a.getPlayer().getPermanentBonusMalus().applyDiscountOnYellow(2, cost2);
			return cost2;
		}
		else{
			a.getPlayer().getPermanentBonusMalus().applyDiscountOnYellow(1, cost2);
			a.getPlayer().getPermanentBonusMalus().applyDiscountOnYellow(2, cost3);
			if(cost2.equals(cost3))
				return cost2;
		}
		throw new DoubleCostException("You have to decide between using the Discount of 1W  or the Discount of 1S");
	}
	
	
	/** @return a string version of the card
	 * @Overrides Card.toString
	 */
	@Override
	public String toString() {
		String app= this.getNumber().toString();
		app+=separator+this.getName();
		app+=separator+this.getCost().toString();
		app+=separator+this.getImmediateBonus().toString();
		app+=separator+this.firstProduction.toString();		
		if(this.hasTwoProductions)
			app+=" OR "+ this.secondProduction.toString();
		app+=" Str=" + Integer.toString(this.productionStrengthCondition);
		return app;
	
	}
	
	
	/**@return true if the strength condition of the production is satisfied,
	 * false otherwise
	 */
	public Boolean canProduce(int str) {
		return str>=this.productionStrengthCondition;	
	}
	
	
	/** handles the production operations of the card. Adds the eventual cost to the cost parameter
	 * @return the produced resources
	 * @param a - the action parameter containing the data about the action that has to be performed
	 * @param cost - a ResourceSet representing a cumulative cost. At the end of the procedure it is increased by the eventual cost of the production. If teh production doesn't ahve a cost, it remains untouched
	 * @param choice - an integer value representing which production has to be done, if tha card has two production. If the card doesn't have two production it is not considered.
	 */
	public Bonus produce(Action a, ResourceSet cost, int choice) {
		if(!this.hasTwoProductions)
			return this.firstProduction.activate(a, cost);
		else if(choice==1)
			return this.firstProduction.activate(a, cost);
		else return this.secondProduction.activate(a, cost);
	}
	
	/** succesfully completes ONLY if the player contained in the parameter satisfies the conditions to acquire the card
	 * @param action - Action object that contains all the data required to perform the action
	 * @throws SixCardsException if the player has already 6 purple cards
	 */
	@Override
	public void conditions(Action action)throws SixCardsException{
		ArrayList<YellowCard> app=action.getPlayer().getYellowCardList();
		int size=app.size();
		if(size>=6)
			throw new SixCardsException("You already ahve 6 yellow cards.");
	}
}
