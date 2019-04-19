package it.polimi.ingsw.LM6.server.game.states;

import java.util.ArrayList;

import it.polimi.ingsw.LM6.server.game.Action;
import it.polimi.ingsw.LM6.server.game.LorenzoIlMagnifico;
import it.polimi.ingsw.LM6.server.game.board.spaces.ProductionSpace;
import it.polimi.ingsw.LM6.server.game.bonuses.Bonus;
import it.polimi.ingsw.LM6.server.game.familiar.FamiliarColor;

public class ProductionAreaState {
	private ProductionSpace firstProduction;
	private ProductionSpace firstHarvest;
	private ArrayList<ProductionSpace> productions;
	private ArrayList<ProductionSpace> harvests;
	private Boolean moreThanTwoPlayers;
	private static final String SEPARATOR="!";
	
	public ProductionAreaState(LorenzoIlMagnifico game){
		this.firstProduction= new ProductionSpace();
		this.firstHarvest=new ProductionSpace();
		this.productions=new ArrayList<>();
		this.harvests=new ArrayList<>();
		if(game.getNumberOfPlayers()>2)
			this.moreThanTwoPlayers=true;
		else
			this.moreThanTwoPlayers=false;
	}
	
	/** @return a string version of the production area
	 * 
	 */
	@Override
	public String toString(){
		String app="";
		app+=firstProduction.toString()+SEPARATOR;
		if(!this.productions.isEmpty())
			for(ProductionSpace psp:productions)
				app+=psp.toString();
		else
			app+=" ";
		app+=SEPARATOR+firstHarvest.toString()+SEPARATOR;
		if(!this.harvests.isEmpty())
			for(ProductionSpace psp:harvests)
				app+=psp.toString();
		else
			app+=" ";
		return app;
		
	}
	
	/** sets the production area ready for a new turn
	 * 
	 */
	public void newTurn(){
		this.firstProduction.newTurn();
		this.firstHarvest.newTurn();
		this.productions=new ArrayList<>();
		this.harvests=new ArrayList<>();
	}
	public ProductionSpace getFirstHarvestSpace() {
		return this.firstHarvest;
	}
	public ProductionSpace getFirstProductionSpace() {
		return this.firstProduction;
	}
	public ArrayList<ProductionSpace> getHavestSpaces(){
		return this.harvests;
	}
	public ArrayList<ProductionSpace> getProductionSpaces(){
		return this.productions;
	}
	public boolean moreThanTwoPlayers() {
		return this.moreThanTwoPlayers;
	}
	
	/**places the familiar and player specified in the parameter in the right place. If the familiar contains a fake familiar,
	 * that means that the player is requesting a bonus harvest, the familiar is not placed.
	 * @param a - the action object containing the data about the  action that is going to be performed
	 * @return null, the return value is not important
	 */
	public Bonus placeHarvest(Action a){
		if(!(a.getFamiliar().getColour()==FamiliarColor.FAKE)){
			if(a.getFloorTowerMarket()==1)
				return this.firstHarvest.placeFamiliar(a.getPlayer(), a.getFamiliar());
			else if(a.getFloorTowerMarket()==2){
				ProductionSpace psp= new ProductionSpace();
				this.harvests.add(psp);
				return psp.placeFamiliar(a.getPlayer(), a.getFamiliar());
			}
		}
		return null;
	}
	
	/**places the familiar and player specified in the parameter in the right place. If the familiar contains a fake familiar,
	 * that means that the player is requesting a bonus production, the familiar is not placed.
	 * @param a - the action object containing the data about the  action that is going to be performed
	 * @return null, the return value is not important
	 */
	public Bonus placeProduction(Action a) {
		if(!(a.getFamiliar().getColour()==FamiliarColor.FAKE)){
			if(a.getFloorTowerMarket()==1)
				return this.firstProduction.placeFamiliar(a.getPlayer(), a.getFamiliar());
			else if(a.getFloorTowerMarket()==2){
				ProductionSpace psp= new ProductionSpace();
				this.productions.add(psp);
				return psp.placeFamiliar(a.getPlayer(), a.getFamiliar());
			}
		}
		return null;
	}
}
