package it.polimi.ingsw.LM6.server.game.bonuses;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.LM6.server.game.Action;
import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.game.effects.*;
import it.polimi.ingsw.LM6.server.game.exceptions.AlreadyUsedBonusException;
import it.polimi.ingsw.LM6.server.game.exceptions.CanGoInOccupiedSpacesException;
import it.polimi.ingsw.LM6.server.game.exceptions.CannotGoOnMarketException;
import it.polimi.ingsw.LM6.server.game.exceptions.NoVPException;
import it.polimi.ingsw.LM6.server.game.familiar.Familiar;

public class PermanentBonusMalus {
	
	private ArrayList<Effect> effects;
	
	public PermanentBonusMalus(int harvestBonus, int productionBonus ,int purpleActionBonus ,int yellowActionBonus,
	 						int greenActionBonus, int blueActionBonus,int bonusWhiteFamiliar,int bonusBlackFamiliar,
	 						int bonusOrangeFamiliar,int bonusUncolouredFamiliar, String flag){
		this.effects=new ArrayList<Effect>();
		if(harvestBonus!=0 || productionBonus!=0 || purpleActionBonus!=0 || yellowActionBonus!=0 || greenActionBonus!=0 || blueActionBonus!=0 || 
				bonusWhiteFamiliar!=0 || bonusBlackFamiliar!=0 || bonusOrangeFamiliar!=0 || bonusUncolouredFamiliar!=0){
			effects.add(new NumericEffect( harvestBonus,  productionBonus , purpleActionBonus , yellowActionBonus,
	 						 greenActionBonus,  blueActionBonus, bonusWhiteFamiliar, bonusBlackFamiliar,
	 						 bonusOrangeFamiliar, bonusUncolouredFamiliar));
		}
		if(("noActionSpaceBonus").equals(flag)) 
			effects.add(new NoActionSpaceBonus("noActionSpaceBonus"));
		else if(("oneCoinMalus").equals(flag)) 
			effects.add(new OneCoinMalus("oneCoinMalus"));
		else if(("oneMPMalus").equals(flag)) 
			effects.add(new OneMPMalus("oneMPMalus"));
		else if(("oneRockWoodMalus").equals(flag)) 
			effects.add(new OneRockWoodMalus("oneRockWoodMalus"));
		else if(("oneServantMalus").equals(flag)) 
			effects.add(new OneServantMalus("oneServantMalus"));
		else if(("doubleServantMalus").equals(flag)) 
			effects.add(new DoubleServantMalus("doubleServantMalus"));
		else if(("skipFirstActionMalus").equals(flag)) 
			effects.add(new SkipFirstActionMalus("skipFirstActionMalus"));
		else if(("cannotUseMarketMalus").equals(flag)) 
			effects.add(new CannotUseMarketMalus("cannotUseMarketMalus"));
		else if(("noBlueVP").equals(flag)) 
			effects.add(new NoBlueVP("noBlueVP"));
		else if(("noGreenVP").equals(flag)) 
			effects.add(new NoGreenVP("noGreenVP"));
		else if(("noPurpleVP").equals(flag))
			effects.add(new NoPurpleVP("noPurpleVP"));
		else if(("loseOneVPEveryFive").equals(flag)) 
			effects.add(new LoseOneVPEveryFive("loseOneVPEveryFive"));
		else if(("loseOneVPEveryMP").equals(flag)) 
			effects.add(new LoseOneVPEveryMP("loseOneVPEveryMP"));
		else if(("loseOneVPEveryRes").equals(flag)) 
			effects.add(new LoseOneVPEveryRes("loseOneVPEveryRes"));
		else if(("loseOneVPEveryWoodStoneYellowCost").equals(flag)) 
			effects.add(new LoseOneVPEveryWoodStoneYellowCost("loseOneVPEveryWoodStoneYellowCost"));
		else if(("woodOrStoneDiscountOnYellow").equals(flag)) 
			effects.add(new WoodOrStoneDiscountOnYellow("woodOrStoneDiscountOnYellow"));
		else if(("oneCoinDiscountOnBlue").equals(flag)) 
			effects.add(new OneCoinDiscountOnBlue("oneCoinDiscountOnBlue"));
		else if(("threeCoinsDiscount").equals(flag)) 
			effects.add(new ThreeCoinsDiscount("threeCoinsDiscount"));
		else if(("faithBonus").equals(flag)) 
			effects.add(new FaithBonus("faithBonus"));
		else if(("oneSixFamiliarPerTurn").equals(flag)) 
			effects.add(new OneSixFamiliarPerTurn("oneSixFamiliarPerTurn"));
		else if(("canGoInOccupiedSpaces").equals(flag)) 
			effects.add(new CanGoInOccupiedSpaces("canGoInOccupiedSpaces"));
		else if(("noMilitaryRequirements").equals(flag)) 
			effects.add(new NoMilitaryRequirements("noMilitaryRequirements"));
		else if(("noTowerCoinsCost").equals(flag)) 
			effects.add(new NoTowerCoinsCost("noTowerCoinsCost"));
		else if(("fixedFiveBonus").equals(flag)) 
			effects.add(new FixedFiveBonus("fixedFiveBonus"));
		else if(("doubleWoodStoneCoinsServantsFromCards").equals(flag)) 
			effects.add(new DoubleWoodStoneCoinsServantsFromCards("doubleWoodStoneCoinsServantsFromCards"));
		
	}

	/** @return a string version of the PermanentBonusMalus
	 * 
	 */
	public String toString(){
		String app="";
		for(Effect e:effects)
			app+=e.toString()+"  ";
		if(app=="")
			return " ";
		return app;
	}
	
	
	
	/**merges two PermanentBonusMalus objects, the result goes in the caller object.
	 * 
	 */
	public void merge(PermanentBonusMalus pbm) {
		for(Effect e: pbm.getEffects()){
			this.effects.add(e);
		}
	}
	
	/** applies contained effects on the game turnOrder
	 * 
	 * @param p - the player that has to be moved in case the SkipFirstTurn malus is found
	 * @param turnOrder - the turn order array that has to be modified
	 */
	public void applyModifier(Player p, Player[] turnOrder){
			for(Effect e:effects)e.applyOn(p, turnOrder);
	}
	
	/** applies contained effects on the action
	 * 
	 * @param action - the action object that contains the data about the action that is going to be performed
	 */
	public void applyModifier(Action action){
		//FixedFiveBonus Application
		action.getPlayer().getPermanentBonusMalus().applyFixedFiveBonus(action.getPlayer());
		action.setStrength(action.getFamiliar().getStrength()+ action.getServants());
		for(Effect e:effects)e.applyOn(action);
	}
	
	/** applies contained effects on the cost (blue card)
	 * 
	 * @param cost - the cost that has to be reduced in case the caller object contains some effects
	 */
	public void applyDiscountOnBlue(ResourceSet cost){
		for(Effect e:effects)e.applyDiscountOnBlue(cost);
	}
	
	/** applies contained effects on the cost (yellow card)
	 * 
	 * @param cost - the cost that has to be reduced in case the caller object contains some effects
	 * @param which - an intger value representing which discount has to be applied, since the discount on yellow cards is 1W OR 1 St
	 */
	public void applyDiscountOnYellow(int which,ResourceSet cost){
		for(Effect e:effects)e.applyDiscountOnYellow(which,cost);
	}
	
	
	/** applies contained effects on the cost (purple card)
	 * 
	 * @param cost - the cost that has to be reduced in case the caller object contains some effects
	 */
	public void applyDiscountOnPurple(ResourceSet cost){
		for(Effect e:effects)e.applyDiscountOnPurple(cost);
	}
	
	
	
	/** applies contained effects on the Bonus bonus relative to the Action a
	 * 
	 * @param a - the action object that contains the data about the action that is going to be performed
	 * @param b - the bonus that has to be modified with maluses, if present
	 */
	public void applyModifier(Action a, Bonus bonus){
		for(Effect e:effects)e.applyOn(a,bonus);
	}
	
	/** applies contained market effects
	 *  
	 * @throws CannotGoOnMarketException if the caller PermanentBonusMalus belongs to a player that cannot go on the market due to a malus previously acquired
	 */
	public void applyMarketMaluses() throws CannotGoOnMarketException{
		for(Effect e:effects)e.applyMarketMaluses();
	}
	
	/** applies contained maluses about VP got from green cards at the end of thr game
	 * 
	 * @throws NoVPException if the malus is present
	 */
	public void applyGreenVPMalus() throws NoVPException{
		for(Effect e:effects)e.applyGreenVPMalus();
	}
	
	/** applies contained maluses about VP got from blue cards at the end of thr game
	 * 
	 * @throws NoVPException if the malus is present
	 */
	public void applyBlueVPMalus() throws NoVPException{
		for(Effect e:effects)e.applyBlueVPMalus();
	}
	
	/** applies contained maluses about VP got from purple cards at the end of thr game
	 * 
	 * @throws NoVPException if the malus is present
	 */
	public void applyPurpleVPMalus() throws NoVPException{
		for(Effect e:effects)e.applyPurpleVPMalus();
	}
	
	/** applies contained maluses about VP
	 * 
	 * @param p - the player that is going to receive the malus, if present
	 */
	public void applyVPNumericalMaluses(Player p){
		for(Effect e:effects)e.applyVPNumericalMaluses(p);
	}
	
	/** applies contained maluses about bonuses. In particular, if present, it sets all the bonus values to zero/false.
	 * 
	 * @param bonus - the bonus that has to be controlled
	 */
	public void applyNoActionSpaceBonus(ResourceSet bonus){
		for(Effect e:effects)e.applyNoActionSpaceBonus(bonus);
	}
	
	/**called on every player when the excommunication phase ends, it assigns 5 VP (if present) to the players 
	 * that haven't been excommunicated
	 * @param p - player
	 */
	public void applyFaithBonus(Player p){
		for(Effect e:effects)e.applyFaithBonus(p);	
	}
	
	/**called when a player is about to be placed on the production area or the market
	 * 
	 * @throws CanGoInOccupiedSpacesException if the player can effectively use an already occupied space
	 */
	public void applyCanGoInOccupiedSpaces() throws CanGoInOccupiedSpacesException{
		for(Effect e:effects)e.applyCanGoInOccupiedSpaces();	
	}
	
	/**called when the player is about to get a green card, it modifies the military points required to get the card,
	 * in particular, sets the cost to zero if the effect is present
	 * @param milReq - a resource set representing the military points requirement
	 */
	public void applyMilitaryRequirementBonuses(ResourceSet milReq) {
		for(Effect e:effects)e.applyMilitaryRequirementBonuses(milReq);	
	}
	
	/**called when the player is about to place a familiar on a tower, it modifies
	 * the number of couns required to go on the tower, and, in particular, sets the cost 
	 * to zero if the effect is present
	 * @param coinsCost - a resource set representing the coins cost (3 coins)
	 */
	public void applyNoTowerCoinsCost(ResourceSet coinsCost){
		for(Effect e:effects)e.applyNoTowerCoinsCost(coinsCost);	
	}
	
	/** sets all the player's coloured familiar values to five
	 * 
	 * @param p - the player
	 */
	public void applyFixedFiveBonus(Player p) {
		for(Effect e:effects)e.applyFixedFiveBonus(p);
	}
	
	/**calculates the total of the resources that the player has to get on a certain turn
	 * thanks to one or more leaders' effects
	 * @param p - the player
	 * @return the total bonus (resources)
	 * @throws AlreadyUsedBonusException if the player does't have the bonus or has already used it
	 */
	public Bonus getEveryTurnResources(Player p) throws AlreadyUsedBonusException {
		Bonus b= new Bonus();
		for(Effect e:effects){
			Bonus app=e.getEveryTurnResources(p);
			b=b.sum(app, p);
		}
		if(!b.isEmpty())
			return b;
		throw new AlreadyUsedBonusException("You don't have this Bonus or it's already been used.");
	}
	/** called when a new turn starts, resets all the bonuses to a "not taken" status, allowing players to get them again
	 * 
	 */
	public void resetEveryTurnBonuses() {
		for(Effect e:effects)e.resetEveryTurnBonuses();
	}
	
	/**checks if the player has the every turn harvest given from a leader
	 * @param p - the player
	 * @return a Bonus object representing the bonus harvest
	 * @throws AlreadyUsedBonusException if the player does't have the bonus or has already used it
	 */
	public Bonus getEveryTurnHarvest(Player p) throws AlreadyUsedBonusException {
		Bonus b= new Bonus();
		for(Effect e:effects){
			Bonus app=e.getEveryTurnHarvest();
			b=b.sum(app,p);
			if(!b.isEmpty())
				return b;
		}
		throw new AlreadyUsedBonusException("You don't have this Bonus or it's already been used.");
	}
	
	/**checks if the player has the every turn production given from a leader
	 * @param p - the player
	 * @return a Bonus object representing the bonus production
	 * @throws AlreadyUsedBonusException if the player does't have the bonus or has already used it
	 */
	public Bonus getEveryTurnProduction(Player p) throws AlreadyUsedBonusException {
		Bonus b= new Bonus();
		for(Effect e:effects){
			Bonus app=e.getEveryTurnProduction();
			b=b.sum(app,p);
			if(!b.isEmpty())
				return b;
		}
		throw new AlreadyUsedBonusException("You don't have this Bonus or it's already been used.");
	}
	
	/** applies the specified bonus, if present, setting the familiar's given as parameter's value to 6
	 * 
	 * @param p - the player
	 * @param f - the familiar
	 * @throws AlreadyUsedBonusException
	 */
	public void applyOneSixFamiliarPerTurn(Player p, Familiar f) throws AlreadyUsedBonusException{
		boolean result=false;
		for(Effect e:effects){
			result=e.applyOneSixFamiliarPerTurn(p, f);
			if(result)
				return;
		}
		throw new AlreadyUsedBonusException("You don't have this bonus");
	}
	
	
	public void applyOnCardsBonus(Action a, Bonus b){
		for(Effect e:effects)e.applyOnCardsBonus(a, b);
	}
	
	
	
	
	/** adds an effect to the list
	 * 
	 * @param everyTurnBonus- the effect that ahs to be added
	 */
	public void add(Effect everyTurnBonus) {
		this.effects.add(everyTurnBonus);
	}
	/******GETTERS******/
	public ArrayList<Effect> getEffects(){
		return this.effects;
	}

	
}
