package it.polimi.ingsw.LM6.server.game.board.spaces;

import java.util.ArrayList;

import it.polimi.ingsw.LM6.server.game.Action;
import it.polimi.ingsw.LM6.server.game.LorenzoIlMagnifico;
import it.polimi.ingsw.LM6.server.game.bonuses.Bonus;
import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;
import it.polimi.ingsw.LM6.server.game.card.GreenCard;
import it.polimi.ingsw.LM6.server.game.card.YellowCard;
import it.polimi.ingsw.LM6.server.game.exceptions.*;
import it.polimi.ingsw.LM6.server.game.familiar.FamiliarColor;
import it.polimi.ingsw.LM6.server.game.states.ProductionAreaState;

public class ProductionArea {
	private ProductionAreaState state;
	private int strCondition;
	private int strMalusAfterFirst;
	public ProductionArea(LorenzoIlMagnifico game){
		this.state=new ProductionAreaState(game);
		this.strCondition=1;
		this.strMalusAfterFirst=3;
	}
	
	/**@return a string version of the ProductionArea
	 * 
	 */
	@Override
	public String toString() {
		return "Str="+ Integer.toString(this.strCondition)+"("+Integer.toString(-this.strMalusAfterFirst)+
				")!"+this.state.toString();
	}
	
	
	/**@return true if the familiar is strong enough to be placed here,
	 *false otherwise
	 */
	public Boolean strongEnough(Action action) {
			return action.getActionStrength()>=this.strCondition;
	}
	
	
	/**succesfully completes if all placing conditions (production) are true and if the familiar has not already been placed
	 * @param action - the action that has to be performed
	 *@throws NotStrongEnoughException if the familiar if not strong enough
	 *@throws AlreadyOccupiedException if the space is already occupied
	 *@throws AnotherFamiliarSameColourException if there's another familiar of the same colour in the area
	 */
	public void verifyConditionForProduction(Action action) throws NotStrongEnoughException, AnotherFamiliarSameColourException, AlreadyOccupiedException {
		if(!this.strongEnough(action))
			throw new NotStrongEnoughException("Your familiar's value is too low.");
		if(!this.isAvailableProduction(action))
			throw new AlreadyOccupiedException("The space is already used");
		if(!this.isPlaceableProduction(action))
			throw new AnotherFamiliarSameColourException("There's another familiar of the same colour here.");
		
	}
	
	
	/**succesfully completes if all placing conditions (production) are true and if the familiar has not already been placed
	 * @param action - the action that has to be performed
	 *@throws NotStrongEnoughException if the familiar if not strong enough
	 *@throws AlreadyOccupiedException if the space is already occupied
	 *@throws AnotherFamiliarSameColourException if there's another familiar of the same colour in the area
	 */
	public void verifyConditionForHarvest(Action action) throws NotStrongEnoughException, AnotherFamiliarSameColourException, AlreadyOccupiedException {
		if(!this.strongEnough(action))
			throw new NotStrongEnoughException("Your familiar's value is too low.");
		if(!this.isAvailableHarvest(action))
			throw new AlreadyOccupiedException("The space is already used");
		if(!this.isPlaceableHarvest(action))
			throw new AnotherFamiliarSameColourException("There's another familiar of the same colour here.");
		
	}
	
	
	/**@return true if there are no other familiars of the same colour in the harvest area,
	 * false otherwise
	 */
	public Boolean isPlaceableHarvest(Action action) {
		if(action.getFamiliar().getColour()==FamiliarColor.UNCOLOURED || action.getFamiliar().getColour()==FamiliarColor.FAKE)
			return true;
		ProductionSpace first=this.state.getFirstHarvestSpace();
		int size=first.getPlayers().size();
		for(int i=0; i<size;i++){
			if(first.getPlayers().get(i)==action.getPlayer() && first.getFamiliars().get(i).getColour()!=FamiliarColor.UNCOLOURED)
				return false;
		}
		if(this.state.moreThanTwoPlayers()){
			ArrayList<ProductionSpace> harvests=this.state.getHavestSpaces();
			size=harvests.size();
			for(int i=0;i<size;i++){
				if(harvests.get(i).getPlayers().get(0)==action.getPlayer() && harvests.get(i).getFamiliars().get(0).getColour()!=FamiliarColor.UNCOLOURED)
					return false;
			}
		}
		return true;
	}
	
	
	/**@return true if there are no other familiars of the same color in the production area,
	 * false otherwise
	 */
	public Boolean isPlaceableProduction(Action action) {
		if(action.getFamiliar().getColour()==FamiliarColor.UNCOLOURED || action.getFamiliar().getColour()==FamiliarColor.FAKE)
			return true;
		ProductionSpace first=this.state.getFirstProductionSpace();
		int size=first.getPlayers().size();
		for(int i=0; i<size;i++){
			if(first.getPlayers().get(i)==action.getPlayer() && first.getFamiliars().get(i).getColour()!=FamiliarColor.UNCOLOURED)
				return false;
		}
		if(this.state.moreThanTwoPlayers()){
			ArrayList<ProductionSpace> productions=this.state.getProductionSpaces();
			size=productions.size();
			for(int i=0;i<size;i++){
				if(productions.get(i).getPlayers().get(0)==action.getPlayer() && productions.get(i).getFamiliars().get(0).getColour()!=FamiliarColor.UNCOLOURED)
					return false;
			}
		}
		return true;
	
	}
	
	
	/**@return true if the required space is available,
	 * false otherwise
	 */
	public Boolean isAvailableHarvest(Action action){
		if(action.getFloorTowerMarket()==2){
			return this.state.moreThanTwoPlayers();
		}
		if(action.getFloorTowerMarket()==1){
			try{
				//CanGoInOccupiedSpaces application
				action.getPlayer().getPermanentBonusMalus().applyCanGoInOccupiedSpaces();
				if(!this.state.getFirstHarvestSpace().isOccupied() || action.getFamiliar().getColour()==FamiliarColor.FAKE)
					return true;
			}catch(CanGoInOccupiedSpacesException e){
				return true;
			}	
		}
		return false;
	}
	
	
	/**@return true if the required space is available,
	 * false otherwise
	 */
	public Boolean isAvailableProduction(Action action){
		if(action.getFloorTowerMarket()==2){
			if(this.state.moreThanTwoPlayers())
				return true;
			else
				return false;
		}
		if(action.getFloorTowerMarket()==1){
			try{
				//CanGoInOccupiedSpaces application
				action.getPlayer().getPermanentBonusMalus().applyCanGoInOccupiedSpaces();
				if(!this.state.getFirstProductionSpace().isOccupied() || action.getFamiliar().getColour()==FamiliarColor.FAKE)
					return true;
			}catch(CanGoInOccupiedSpacesException e){
				return true;
			}		
		}
		return false;	
	}
	
	/**handles the placing operations and triggers the production
	 * @return the total Bonus from the production
	 * @param action - the action that has to be performed
	 * @throws NotStrongEnoughException if the familiar if not strong enough
	 * @throws NotEnoughResourcesException if the player doesn't have enough resources
	 * @throws AlreadyOccupiedException if the space is already occupied
	 * @throws AnotherFamiliarSameColourException if there's another familiar of the same colour in the area
	 */
	public Bonus placeFamiliarProduction( Action action) throws NotStrongEnoughException, AnotherFamiliarSameColourException, AlreadyOccupiedException, NotEnoughResourcesException {
		action.getPlayer().getPermanentBonusMalus().applyModifier(action);
		/* MALUS VALORE IN CASO SPAZIO GRANDE*/
		if(action.getFloorTowerMarket()==2)
			action.setStrength(action.getActionStrength()-this.strMalusAfterFirst);
		
		this.verifyConditionForProduction(action);
		ResourceSet cost=new ResourceSet(0,0,0,action.getServants(),0,0,0);
		Bonus result= this.produce(action, cost);
		action.getPlayer().pay(cost);
		this.state.placeProduction(action);
		return result;
	
	}
	
	
	


	/**handles the placing operations and triggers the Harvest
	 * @return the total Bonus from the Harvest
	 * @param action - the action that has to be performed
	 *@throws NotStrongEnoughException if the familiar if not strong enough
	 *@throws NotEnoughResourcesException if the player doesn't have enough resources
	 *@throws AlreadyOccupiedException if the space is already occupied
	 *@throws AnotherFamiliarSameColourException if there's another familiar of the same colour in the area
	 */
	public Bonus placeFamiliarHarvest(Action action) throws NotStrongEnoughException, AnotherFamiliarSameColourException, AlreadyOccupiedException, NotEnoughResourcesException {
		action.getPlayer().getPermanentBonusMalus().applyModifier(action);
		/* MALUS VALORE IN CASO SPAZIO GRANDE*/
		if(action.getFloorTowerMarket()==2)
			action.setStrength(action.getActionStrength()-this.strMalusAfterFirst);
		
		this.verifyConditionForHarvest(action);
		ResourceSet servants=new ResourceSet(0,0,0,action.getServants(),0,0,0);
		Bonus result= this.harvest(action);
		action.getPlayer().pay(servants);
		this.state.placeHarvest(action);
		return result;
	}
	
	
	/**handles the operation required to start and complete the harvesting procedure.
	 * @param action - the action that has to be performed
	 * @return the total Bonus from the harvest
	 */
	private Bonus harvest(Action action){
		Bonus result=new Bonus();
		
		/* CARDS HARVEST */
		ArrayList<GreenCard> cards=action.getPlayer().getGreenCardList();
		for(GreenCard gc: cards){
			if(gc.canHarvest(action.getActionStrength())){
				Bonus hrv=gc.harvest();
				/* APPLY MALUSES */
				action.getPlayer().getPermanentBonusMalus().applyModifier(action, hrv);
				result=result.sum(hrv, action.getPlayer());
			}
		}
		/*PERSONAL TILE HARVEST*/
		ResourceSet personal=action.getPlayer().getPersonalTile().getHarvestSet();
		Bonus hrv=new Bonus();
		hrv.setResourceSet(personal);
		/* APPLY MALUSES */
		action.getPlayer().getPermanentBonusMalus().applyModifier(action, hrv);
		result=result.sum(hrv, action.getPlayer());
		
		return result;
	}
	
	
	/**handles the operation required to start and complete the production procedure.
	 * @param action - the action that has to be performed
	 * @param cost - a cumulative cost that, at the end of the prcedure, contains the total cost of the action (slaves + cumulative productions cost)
	 * @return the total Bonus from the production
	 */
	private Bonus produce(Action action, ResourceSet cost) {
		Bonus result=new Bonus();
		
		/* CARDS PRODUCTIONS */
		ArrayList<YellowCard> cards=action.getPlayer().getYellowCardList();
		int size=cards.size();
		for(int i=0;i<size;i++){
			if(cards.get(i).canProduce(action.getActionStrength()) && action.getProductionChoice(i)!=0){
				Bonus prod=cards.get(i).produce(action, cost, action.getProductionChoice(i));
				/* APPLY MALUSES */
				action.getPlayer().getPermanentBonusMalus().applyModifier(action, prod);
				result=result.sum(prod, action.getPlayer());
			}
		}
		/*PERSONAL TILE PRODUCTION*/
		ResourceSet personal=action.getPlayer().getPersonalTile().getProductionSet();
		Bonus prod=new Bonus();
		prod.setResourceSet(personal);
		/* APPLY MALUSES */
		action.getPlayer().getPermanentBonusMalus().applyModifier(action, prod);
		result=result.sum(prod, action.getPlayer());
		
		return result;
	}
	
	/**clears the area, setting it ready for the new turn*/
	public void newTurn() {
		this.state.newTurn();
	}
	
}
