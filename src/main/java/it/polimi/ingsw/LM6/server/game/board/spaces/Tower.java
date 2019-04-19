package it.polimi.ingsw.LM6.server.game.board.spaces;

import java.util.ArrayList;

import it.polimi.ingsw.LM6.server.game.Action;
import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.game.board.TowerCardColor;
import it.polimi.ingsw.LM6.server.game.bonuses.Bonus;
import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;
import it.polimi.ingsw.LM6.server.game.card.Card;
import it.polimi.ingsw.LM6.server.game.debug.Debug;
import it.polimi.ingsw.LM6.server.game.exceptions.*;
import it.polimi.ingsw.LM6.server.game.familiar.Familiar;
import it.polimi.ingsw.LM6.server.game.familiar.FamiliarColor;
import it.polimi.ingsw.LM6.server.game.states.TowerState;

public class Tower {
	private TowerState state;
	
	public Tower(ArrayList<Card> cards, ArrayList<TowerSpace> spaces, TowerCardColor col) {
		this.state=new TowerState(cards,spaces,col);
	}


	/**@return true if the tower is occupied,
	 * false otherwise
	 */
	public Boolean isOccupied() {
		TowerSpace[] spaces=this.state.getSpaces();
		for(TowerSpace tsp:spaces)
			if(tsp.isUsed() && tsp.getFamiliar().getColour()!=FamiliarColor.FAKE)
				return true;
		return false;
	}
	
	/**@return true if the familiar's value is higher than the space's condition
	 * 
	 * @param action - the action object containing the data required to perform the action
	 * @param space - the space where the Player and familiar (indicated in action) have to be placed
	 */
	private Boolean strongEnough(Action action, TowerSpace space){
		return action.getActionStrength()>=space.getStrCondition();
	}
	/**@return true if the player can pay the eventual 3 coins cost if the tower is occupied,
	 * false otherwise
	  * @param action - the action object containing the data required to perform the action
	 */
	private Boolean canPayThreeCoins(Action action){
		ResourceSet cost=new ResourceSet(0,0,3,0,0,0,0);
		//NoTowerCoinsCost application
		action.getPlayer().getPermanentBonusMalus().applyNoTowerCoinsCost(cost);
		return action.getPlayer().getResourceSet().contains(cost);
	}
	
	/**@return true if the Player can pay the whole cost, considering all the possibilities,
	 * false otherwise
	* @param action - the action object containing the data required to perform the action
	* @param space - the space where the Player and familiar (indicated in action) have to be placed
	* @param cost - the effective cost of the card in the space
	* @param spaceRes - the effective immediate bonus of the TowerSpace
	 */
	private Boolean hasEnoughResources(Action action,TowerSpace space, ResourceSet cost, ResourceSet spaceRes){
		ResourceSet slaves=new ResourceSet(0,0,0,action.getServants(),0,0,0);
		ResourceSet coins=new ResourceSet(0,0,3,0,0,0,0);
		//NoTowerCoinsCost application
		action.getPlayer().getPermanentBonusMalus().applyNoTowerCoinsCost(coins);
			
		Debug.print("RISORSE:"+action.getPlayer().getResourceSet().toString());
		Debug.print("SPACE:"+spaceRes.toString());
		Debug.print("COSTO: " + cost.toString());
		Debug.print("SLAVES: " + slaves.toString());
		if(!this.isOccupied())
			return (action.getPlayer().getResourceSet().sum(spaceRes)).contains(cost.sub(action.getDiscount()).sum(slaves));
		return (action.getPlayer().getResourceSet().sum(spaceRes)).contains(cost.sub(action.getDiscount()).sum(slaves).sum(coins));
	}
	
	
	/**succesfully completes only if all placing conditions (tower's point of view) are verified
	 * @param action - the action object containing the data required to perform the action
	* @param space - the space where the Player and familiar (indicated in action) have to be placed
	* @param cost - the effective cost of the card in the space
	* @param spaceRes - the effective immediate bonus of the TowerSpace
	 * @throws NotEnoughResourcesException if the player doesn't have the required resources
	 * @throws AnotherFamiliarSameColourException if there's another familiar of the same colour in the area 
	 * @throws NotStrongEnoughException if the familiar is not strong enough
	 * @throws AlreadyOccupiedException if the space is already used
	 * @throws NotEnoughMilitaryPointsException if the player doesn't have enough military points to get a green card, if the card is green
	 */
	private void verifyConditions( Action action,TowerSpace space, ResourceSet cost, ResourceSet spaceRes ) throws AnotherFamiliarSameColourException, AlreadyOccupiedException, NotStrongEnoughException, NotEnoughResourcesException, NotEnoughMilitaryPointsException, SixCardsException {
		if(!this.isPlaceable(action))
			throw new AnotherFamiliarSameColourException("There's another familiar of the same colour here.");
		
		else if(this.isOccupied() && !this.canPayThreeCoins(action))
			throw new NotEnoughResourcesException("You don't have enough resources (3 coins).");
		
		if(space.isUsed())
			throw new AlreadyOccupiedException("This space is already used.");
		
		if(!this.strongEnough(action, space))
			throw new NotStrongEnoughException("This familiar's value is too low.");
		
		if(!this.hasEnoughResources(action, space, cost, spaceRes))
			throw new NotEnoughResourcesException("You don't have enough resources.");
		
		space.getAssociatedCard().conditions(action);
	}
	
	
	/**Calls the placeFamiliar method on the right TowerSpace
	 * @return the Bonus obtained by taking the card
	 * @param action - the action object containing the data required to perform the action
	 * @throws AnotherFamiliarSameColourException if there's another familiar of the same colour in the area
	 * @throws NotEnoughResourcesException if the player doesn't have the required resources
	 * @throws NotStrongEnoughException if the familiar is not strong enough
	 * @throws AlreadyOccupiedException if the space is already used
	 * @throws NotEnoughMilitaryPoints if the player doesn't have enough military points to get a green card, if the card is green
	 * @throws DoubleCostException if the cards has two possible costs
	 * @throws SixCardsException if the player has already 6 cards of the same colour
	 */
	public Bonus placeFamiliar( Action action) throws AnotherFamiliarSameColourException, AlreadyOccupiedException, NotStrongEnoughException, NotEnoughResourcesException, NotEnoughMilitaryPointsException, SixCardsException, DoubleCostException {	
		/*SPAZIO AZIONE*/
		TowerSpace space=this.state.getSpace(action.getFloorTowerMarket());
		/*Applica modifiers azione*/
		action.getPlayer().getPermanentBonusMalus().applyModifier(action);
		try{
			/*Costo carta modificato per il player*/
			ResourceSet cost=space.getAssociatedCard().getCost(action);
		
			/*Risorse spazio azione modificate per il player*/
			Bonus spaceBonus=new Bonus();
			spaceBonus.setResourceSet(space.getImmediateBonus());
			action.getPlayer().getPermanentBonusMalus().applyModifier(action, spaceBonus);
			ResourceSet spaceRes=spaceBonus.getResourceSetFor(action.getPlayer());
			action.getPlayer().getPermanentBonusMalus().applyNoActionSpaceBonus(spaceRes);
			
			this.verifyConditions(action, space, cost,spaceRes);
			/*Assegnazione risorse spazio azione*/
			action.getPlayer().acquireResources(spaceRes);
			/*eventuale spesa monete*/
			ResourceSet coins=new ResourceSet(0,0,3,0,0,0,0);
			//NoTowerCoinsCost application
			action.getPlayer().getPermanentBonusMalus().applyNoTowerCoinsCost(coins);
			
			/*spesa schiavi*/
			ResourceSet servants=new ResourceSet(0,0,0,action.getServants(),0,0,0);
			/*PAGAMENTO*/
			if(this.isOccupied())
				action.getPlayer().pay((cost.sub(action.getDiscount())).sum(servants).sum(coins));
			else
				action.getPlayer().pay((cost.sub(action.getDiscount())).sum(servants));
		}catch(NullPointerException e){
			Debug.print("SPACE ALREADY USED");
			throw new AlreadyOccupiedException("This space is already used.");
		}
		return space.placeFamiliar(action.getPlayer(), action.getFamiliar());
	
	}
	
	
	/**@return a string version of the Tower
	 * 
	 */
	@Override
	public String toString() {
		return state.toString();
	
	}
	
	
	/**prepares the tower for a new turn ( turn i)
	 * @param i - the turn number
	 */
	public void newTurn(int i) {
		this.state.newTurn(i);
	}
	
	
	/**@return true if there are no other familiars of the same colour in the tower,
	 * false otherwise
	 */
	private Boolean isPlaceable( Action action) {
		if(action.getFamiliar().getColour()==FamiliarColor.UNCOLOURED || action.getFamiliar().getColour()==FamiliarColor.FAKE)
			return true;
		TowerSpace[] app=this.state.getSpaces();
		for(TowerSpace tsp:app)
			if(tsp.getPlayer()!=null && tsp.getPlayer()==action.getPlayer() && tsp.getFamiliar().getColour()!=FamiliarColor.UNCOLOURED && tsp.getFamiliar().getColour()!=FamiliarColor.FAKE)
				return false;
		return true;
	}
	
	/**shuffles the cards in the tower, keeping them ordered by era
	 * 
	 */
	public void shuffleCards(){
		state.shuffleCards();
	}
	
	/** prints all the cards.toString()
	 * 
	 */
	public void printCards(){
		state.printCards();
	}
	
	public TowerCardColor getColor(){
		return this.state.getColor();
	}
	public Card[] getCard(){
		return this.state.getCard();
	}
}
