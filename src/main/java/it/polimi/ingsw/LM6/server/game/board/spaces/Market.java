package it.polimi.ingsw.LM6.server.game.board.spaces;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.LM6.server.game.Action;
import it.polimi.ingsw.LM6.server.game.LorenzoIlMagnifico;
import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.game.bonuses.Bonus;
import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;
import it.polimi.ingsw.LM6.server.game.debug.Debug;
import it.polimi.ingsw.LM6.server.game.exceptions.AlreadyOccupiedException;
import it.polimi.ingsw.LM6.server.game.exceptions.BadInputException;
import it.polimi.ingsw.LM6.server.game.exceptions.CanGoInOccupiedSpacesException;
import it.polimi.ingsw.LM6.server.game.exceptions.CannotGoOnMarketException;
import it.polimi.ingsw.LM6.server.game.exceptions.NotEnoughResourcesException;
import it.polimi.ingsw.LM6.server.game.exceptions.NotStrongEnoughException;
import it.polimi.ingsw.LM6.server.game.familiar.Familiar;

public class Market {
	private static final int NUMBER_OF_MARKETSPACES=4;
	private int strCondition;
	private MarketSpace[] marketSpaces;
	private final String SEPARATOR="!";
	
	/** Market constructor. It builds the market basing on the game it receives as parameter. If 4 players are in the game it builds the whole market
	 * Otherwise it builds only the first 2 spaces.
	 * @param game - the game it is referred to
	 * @throws BadInputException if there are issues or mismatches in the conf files' values
	 */
	public Market(LorenzoIlMagnifico game){
		this.strCondition=1;
		this.marketSpaces=new MarketSpace[NUMBER_OF_MARKETSPACES];
		FileReader gameConfig;
		try {
			/*cards setup*/
			gameConfig=new FileReader(this.getClass().getResource("/it/polimi/ingsw/LM6/configuration/market.txt").getPath());
			Debug.print("Mercato.");
			BufferedReader inputFromFile=new BufferedReader(gameConfig);
			String app=inputFromFile.readLine();
			for(int i=0;i<NUMBER_OF_MARKETSPACES;i++){
				app=inputFromFile.readLine();
				String[] data=app.split(";");
				if(!Boolean.parseBoolean(data[0]) || (Boolean.parseBoolean(data[0]) && game.getNumberOfPlayers()==4)){
					marketSpaces[i]=new MarketSpace(new Bonus(
										new ResourceSet(Integer.parseInt(data[1]),Integer.parseInt(data[2]),Integer.parseInt(data[3]),
												Integer.parseInt(data[4]),Integer.parseInt(data[5]),0,Integer.parseInt(data[6])),
										false,'N', false,'N', 0,null,
										false, false, 0, Integer.parseInt(data[7]),Boolean.parseBoolean(data[8])));
					Debug.print("Spazio mercato "+ Integer.toString(i)+ " creato.");
				}
			}
			gameConfig.close();
			
		} catch (IOException e) {
			Logger.getLogger("MyLogger").log(Level.SEVERE, e.getMessage());
		}
		
	}
	
	/**
	 * @return a string version of the Market
	 */
	@Override
	public String toString(){
		String app="Str="+Integer.toString(this.strCondition)+SEPARATOR;
		for(MarketSpace msp:marketSpaces){
			if(msp!=null)
				app+=msp.toString()+SEPARATOR;
		}
		return app;
	}
	public void newTurn(){
		for(MarketSpace msp:marketSpaces)
			if(msp!=null)
				msp.newTurn();
	}
	
	
	/**Sets Player and familiar fields and returns the Bonus corresponding to this space.
	 * @param action - the action object that contains the required information
	* @throws NotStrongEnoughException if the familiar if not strong enough
	* @throws NotEnoughResourcesException if the player doesn't have the required resources
	* @throws AlreadyOccupiedException if the space is already occupied
	* @throws CannotGoOnMarketException if teh player has the related malus
	* @return the bonus obtained with the action, if it is succesfully completed
	*/
	public Bonus placeFamiliar(Action action) throws NotStrongEnoughException, NotEnoughResourcesException, AlreadyOccupiedException, CannotGoOnMarketException{
		action.getPlayer().getPermanentBonusMalus().applyModifier(action);
		action.getPlayer().getPermanentBonusMalus().applyMarketMaluses();
		verifyCondition(action);
		action.getPlayer().pay(new ResourceSet(0,0,0, action.getServants(),0,0,0));
		return this.marketSpaces[action.getFloorTowerMarket()].placeFamiliar(action.getPlayer(), action.getFamiliar());
		
	}
	
	/**completes succesfully only if the space is not occupied and player is strong enough and the familiar has not been used yet
	 *@throws NotStrongEnoughException if the familiar if not strong enough
	 *@throws AlreadyOccupiedException if the space is already occupied
	 *@throws AlreadyUsedFamiliarException if the familiar has already been placed in this turn
	 *@author matteo
	 */
	public void verifyCondition(Action action) throws NotStrongEnoughException, AlreadyOccupiedException{
		if(!this.strongEnough(action))
			throw new NotStrongEnoughException("This familiar's value is too low.");
		try{
			if(this.isOccupied(this.marketSpaces[action.getFloorTowerMarket()])){
				try{
					//CanGoInOccupiedSpaces application
					action.getPlayer().getPermanentBonusMalus().applyCanGoInOccupiedSpaces();

					throw new AlreadyOccupiedException("This space is occupied.");
				}catch(CanGoInOccupiedSpacesException e){
					
				}
			}
		}catch(NullPointerException | ArrayIndexOutOfBoundsException e){
			throw new AlreadyOccupiedException("This space doesn't exist.");
		}
	}
	
	
	/**@return true if the familiar is strong enough to be placed in the MarketSpace,
	 * false otherwise 
	 */
	public Boolean strongEnough(Action action) {
		return action.getActionStrength()>=this.strCondition;
	}
	/**@param msp- the MarketSpace which has to be controlled
	 * @return true if the required space is not occupied from another familiar,
	 * false otherwise 
	 */
	public Boolean isOccupied(MarketSpace msp){
		return msp.isOccupied();
	}
}
