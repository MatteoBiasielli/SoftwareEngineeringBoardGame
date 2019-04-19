package it.polimi.ingsw.LM6.server.game.board;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

import com.sun.media.jfxmedia.logging.Logger;

import it.polimi.ingsw.LM6.server.game.Action;
import it.polimi.ingsw.LM6.server.game.LorenzoIlMagnifico;
import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.game.board.spaces.*;
import it.polimi.ingsw.LM6.server.game.bonuses.Bonus;
import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;
import it.polimi.ingsw.LM6.server.game.card.BlueCard;
import it.polimi.ingsw.LM6.server.game.card.Card;
import it.polimi.ingsw.LM6.server.game.card.ExcommunicationCard;
import it.polimi.ingsw.LM6.server.game.card.GreenCard;
import it.polimi.ingsw.LM6.server.game.card.PurpleCard;
import it.polimi.ingsw.LM6.server.game.card.YellowCard;
import it.polimi.ingsw.LM6.server.game.debug.Debug;
import it.polimi.ingsw.LM6.server.game.exceptions.*;

public class Board {
	private static final int NUMBER_OF_TOWERS=4;
	private static final int NUMBER_OF_FAITH_BONUSES=16;

	private LorenzoIlMagnifico game;
	private Tower[] towers;
	private Market market;
	private Council council;
	private ProductionArea productionArea;
	private ExcommunicationCard[] excommunications;
	private ResourceSet[] faithPointsBonuses;
	private static final String separator=";";

	
	/** Board Constructor.
	 * Creates all the areas' controllers.
	 * 
	 * @param g - the game it refers to
	 * @throws BadInputException - if there's any problem or mismatch between in values of the conf files
	 * @author matteo
	 */
	public Board(LorenzoIlMagnifico g){
		this.game=g;
		this.towers=new Tower[NUMBER_OF_TOWERS];
		this.excommunications=new ExcommunicationCard[3];
		this.faithPointsBonuses=new ResourceSet[NUMBER_OF_FAITH_BONUSES];
		createGreenTower();
		createBlueTower();
		createYellowTower();
		createPurpleTower();
		Debug.print("Torri Fatte");
		for(int i=0;i<NUMBER_OF_TOWERS;i++){
			towers[i].shuffleCards();
		}
		
		this.market=new Market(game);
		randomExcommunications();
		setFaithTrack();
		this.council=new Council();
		this.productionArea=new ProductionArea(game);
		
	}
	
	/** @return a string version of the Board
	 *  @author matteo
	 */
	@Override
	public String toString() {
		String app="";
		for(int i=0;i<NUMBER_OF_TOWERS;i++){
			app+=towers[i].toString()+separator;
		}
		app+=council.toString()+separator;
		app+=market.toString()+separator;
		app+=productionArea.toString()+separator;
		for(int i=0;i<3;i++){
			app+=this.excommunications[i].toString()+separator;
		}
		return app;
	}
	
	
	/**reinitialize the board when a new turn starts
	 * @param newTurnNumber represents the turn number (1,2,3,4,5,6 are the only valid inputs)
	 * @author matteo
	 */
	public void newTurn(int newTurnNumber) {
		for(int i=0;i<NUMBER_OF_TOWERS;i++)
			this.towers[i].newTurn(newTurnNumber);
		this.market.newTurn();
		this.council.newTurn();
		this.productionArea.newTurn();
	}

	/**@param i  - the number of the the requested tower (0=GREEN, 1=BLUE, 2=YELLOW, 3=PURPLE)
	 *
	 * @return the requested tower controller
	 * @author matteo
	 */
	public Tower getTower( int i) {
		return this.towers[i];
	
	}
	
	/**@param col  - the color of the the requested tower
	 *
	 * @return the requested tower cntroller
	 * @author matteo
	 */
	private Tower getTower(TowerCardColor col) {
		return this.getTower(col.getNumber());
	
	}
	
	/**Returns the requires Excommunication Card
	 * @param era
	 *
	 * @return the requested Excommunication card
	 * @author matteo
	 */
	public ExcommunicationCard getExcommunicationCard( int era) {
		return this.excommunications[era-1];
	
	}
	

	/**
	 * 
	 * @return the number of VPs corresponding to the given number of faith points
	 * @author matteo
	 */
	public ResourceSet getFaithPointsBonus(int faithPoints){
		if(faithPoints >15)
			return this.faithPointsBonuses[15];
		return this.faithPointsBonuses[faithPoints];
	}
	
	
	/**Calls the placeFamiliar Method of the right tower
	 * @throws NotEnoughResourcesException if the player doesn't have the required resources
	 * @throws NotStrongEnoughException if the familiar is not strong enough
	 * @throws AlreadyOccupiedException if the space is already used
	 * @throws AnotherFamiliarSameColourException if there's another familiar of the same colour in the area
	 * @throws NotEnoughMilitaryPoints if the player doesn't have enough military points to get a green card, if the card is green
	 * @throws DoubleCostException if the cards has two possible costs
	 * 
	 * @return the immediate bonus gained from the placement
	 * @author matteo
	 */
	public Bonus placeOnTower( Action action) throws AnotherFamiliarSameColourException, AlreadyOccupiedException, NotStrongEnoughException, NotEnoughResourcesException, NotEnoughMilitaryPointsException, SixCardsException, DoubleCostException{
		Bonus result=this.getTower(action.getTowerCardColor()).placeFamiliar(action);
		action.getPlayer().getPermanentBonusMalus().applyModifier(action, result);
		action.getPlayer().getPermanentBonusMalus().applyOnCardsBonus(action, result);
		return result;
	}
	
	
	/**Calls the placeFamiliar Method of the council
	 * @throws NotStrongEnoughException if the familiar is not strong enough
	 * @throws NotEnoughResourcesException if the player doesn't have the required resources
	 * 
	 * @return the immediate bonus gained from the placement
	 * @author matteo
	 */
	public Bonus placeOnCouncil( Action action) throws NotStrongEnoughException, NotEnoughResourcesException{
		Bonus result=this.council.placeFamiliar(action);
		action.getPlayer().getPermanentBonusMalus().applyModifier(action, result);
		return result;
	}
	
	
	/**Calls the PlaceFamiliar Method of the market
	 * @throws NotStrongEnoughException if the familiar is not strong enough
	 * @throws NotEnoughResourcesException if the player doesn't have the required resources
	 * @throws AlreadyOccupiedException if the space is already occupied
	 * @throws CannotGoOnMarketException if teh player has the related malus
	 * 
	 * @return the immediate bonus gained from the placement
	 * @author matteo
	 */
	public Bonus placeOnMarket( Action action) throws NotStrongEnoughException, NotEnoughResourcesException, AlreadyOccupiedException, CannotGoOnMarketException{
		Bonus result=this.market.placeFamiliar(action);
		action.getPlayer().getPermanentBonusMalus().applyModifier(action, result);
		return result;
	}
	
	
	/**Calls the placeFamiliarHarvest Method of the productionArea
	 * @throws NotStrongEnoughException if the familiar is not strong enough
	 * @throws AnotherFamiliarSameColourException if there's another familiar of the same colour in the area
	 * @throws AlreadyOccupiedException if the space is already occupied
	 * @throws NotEnoughResourcesException if the player doesn't have enough resources
	 * 
	 * @return the immediate bonus gained from the placement
	 * @author matteo
	 */
	public Bonus placeFamiliarHarvest(Action action) throws NotStrongEnoughException, AnotherFamiliarSameColourException, AlreadyOccupiedException, NotEnoughResourcesException{
		return this.productionArea.placeFamiliarHarvest(action);
	}
	
	
	/**Calls the placeFamiliarProduction Method of the productionArea
	 * @throws NotStrongEnoughException if the familiar is not strong enough
	 * @throws AnotherFamiliarSameColourException if there's another familiar of the same colour in the area
	 * @throws AlreadyOccupiedException if the space is already occupied
	 * @throws NotEnoughResourcesException if the player doesn't have enough resources
	 * 
	 * @return the immediate bonus gained from the placement
	 * @author matteo
	 */
	public Bonus placeFamiliarProduction( Action action) throws NotStrongEnoughException, AnotherFamiliarSameColourException, AlreadyOccupiedException, NotEnoughResourcesException{
		return this.productionArea.placeFamiliarProduction(action);
	}
	
	
	
	/** completely created the green tower, reading  the cards 
	 * from greencards.txt and the space bonuses from greentower.txt
	 * @throws BadInputException if there's a problem or data mismatch/incoherence in the files
	 * @author matteo
	 */
	private void createGreenTower(){
		FileReader gameConfig;
		try {
			/*cards setup*/
			gameConfig=new FileReader(this.getClass().getResource("/it/polimi/ingsw/LM6/configuration/greencards.txt").getPath());
			BufferedReader inputFromFile=new BufferedReader(gameConfig);
			String app=inputFromFile.readLine();
			ArrayList<Card> cards=new ArrayList<Card>();
			for(int i=0;i<24;i++){
				app=inputFromFile.readLine();
				Card newCard=new GreenCard(app.split(";"));
				cards.add(newCard);
			}
			gameConfig.close();
			/*spaces setup*/
			gameConfig=new FileReader(this.getClass().getResource("/it/polimi/ingsw/LM6/configuration/greentower.txt").getPath());
			inputFromFile=new BufferedReader(gameConfig);
			app=inputFromFile.readLine();
			ArrayList<TowerSpace> spaces=new ArrayList<TowerSpace>();
			for(int i=0;i<4;i++){
				app=inputFromFile.readLine();
				TowerSpace tSpace=new TowerSpace(app);
				spaces.add(tSpace);
			}
			towers[0]=new Tower(cards,spaces,TowerCardColor.GREEN);
			inputFromFile.close();
			gameConfig.close();
		} catch (IOException e) {
			
		}
		
	}
	
	/** completely created the blue tower, reading  the cards 
	 * from bluecards.txt and the space bonuses from bluetower.txt
	 * @throws BadInputException if there's a problem or data mismatch/incoherence in the files
	 * @author matteo
	 */
	private void createBlueTower(){
		FileReader gameConfig;
		try {
			/*cards setup*/
			gameConfig=new FileReader(this.getClass().getResource("/it/polimi/ingsw/LM6/configuration/bluecards.txt").getPath());
			BufferedReader inputFromFile=new BufferedReader(gameConfig);
			String app=inputFromFile.readLine();
			
			ArrayList<Card> cards=new ArrayList<Card>();
			for(int i=0;i<24;i++){
				app=inputFromFile.readLine();
				Card newCard=new BlueCard(app.split(";"));
				cards.add(newCard);
			}
			gameConfig.close();
			/*spaces setup*/
			gameConfig=new FileReader(this.getClass().getResource("/it/polimi/ingsw/LM6/configuration/bluetower.txt").getPath());
			inputFromFile=new BufferedReader(gameConfig);
			app=inputFromFile.readLine();
			ArrayList<TowerSpace> spaces=new ArrayList<TowerSpace>();
			for(int i=0;i<4;i++){
				app=inputFromFile.readLine();
				TowerSpace tSpace=new TowerSpace(app);
				spaces.add(tSpace);
			}
			towers[1]=new Tower(cards,spaces,TowerCardColor.BLUE);
			inputFromFile.close();
			gameConfig.close();
		} catch (IOException e) {
			
		}
	}
	
	/** completely created the blue tower, reading  the cards 
	 * from yellowcards.txt and the space bonuses from yellowtower.txt
	 * @throws BadInputException if there's a problem or data mismatch/incoherence in the files
	 * @author matteo
	 */
	private void createYellowTower(){
		FileReader gameConfig;
		try {
			/*cards setup*/
			gameConfig=new FileReader(this.getClass().getResource("/it/polimi/ingsw/LM6/configuration/yellowcards.txt").getPath());
			BufferedReader inputFromFile=new BufferedReader(gameConfig);
			String app=inputFromFile.readLine();
			ArrayList<Card> cards=new ArrayList<Card>();
			for(int i=0;i<24;i++){
				app=inputFromFile.readLine();
				Card newCard=new YellowCard(app.split(";"));
				cards.add(newCard);
			}
			gameConfig.close();
			/*spaces setup*/
			gameConfig=new FileReader(this.getClass().getResource("/it/polimi/ingsw/LM6/configuration/yellowtower.txt").getPath());
			inputFromFile=new BufferedReader(gameConfig);
			app=inputFromFile.readLine();
			ArrayList<TowerSpace> spaces=new ArrayList<TowerSpace>();
			for(int i=0;i<4;i++){
				app=inputFromFile.readLine();
				TowerSpace tSpace=new TowerSpace(app);
				spaces.add(tSpace);
			}
			towers[2]=new Tower(cards,spaces,TowerCardColor.YELLOW);
			inputFromFile.close();
			gameConfig.close();
		} catch (IOException e) {
			
		}
	
	}
	
	/** completely created the blue tower, reading  the cards 
	 * from purplecards.txt and the space bonuses from purpletower.txt
	 * @throws BadInputException if there's a problem or data mismatch/incoherence in the files
	 * @author matteo
	 */
	private void createPurpleTower(){
		FileReader gameConfig;
		try {
			/*cards setup*/
			gameConfig=new FileReader(this.getClass().getResource("/it/polimi/ingsw/LM6/configuration/purplecards.txt").getPath());
			BufferedReader inputFromFile=new BufferedReader(gameConfig);
			String app=inputFromFile.readLine();
			ArrayList<Card> cards=new ArrayList<Card>();
			for(int i=0;i<24;i++){
				app=inputFromFile.readLine();
				Card newCard=new PurpleCard(app.split(";"));
				cards.add(newCard);
			}
			gameConfig.close();
			/*spaces setup*/
			gameConfig=new FileReader(this.getClass().getResource("/it/polimi/ingsw/LM6/configuration/purpletower.txt").getPath());
			inputFromFile=new BufferedReader(gameConfig);
			app=inputFromFile.readLine();
			ArrayList<TowerSpace> spaces=new ArrayList<TowerSpace>();
			for(int i=0;i<4;i++){
				app=inputFromFile.readLine();
				TowerSpace tSpace=new TowerSpace(app);
				spaces.add(tSpace);
			}
			towers[3]=new Tower(cards,spaces,TowerCardColor.PURPLE);
			inputFromFile.close();
			gameConfig.close();
		} catch (IOException e) {
			
		}
	}
	
	/**completely creates the faith track, reading the bonuses from the file fathpointsbonuses.txt
	 * 
	 * @throws BadInputException if there's a problem or data mismatch/incoherence in the files
	 * @author matteo
	 */
	private void setFaithTrack(){
		FileReader gameConfig;
		try {
			/*faithTrack setup*/
			gameConfig=new FileReader(this.getClass().getResource("/it/polimi/ingsw/LM6/configuration/fathpointsbonuses.txt").getPath());
			BufferedReader inputFromFile=new BufferedReader(gameConfig);
			String app=inputFromFile.readLine();
			app=inputFromFile.readLine();
			for(int i=0; i<NUMBER_OF_FAITH_BONUSES;i++){
				app=inputFromFile.readLine();
				String split[]=app.split(";");
				this.faithPointsBonuses[Integer.parseInt(split[0])]= new ResourceSet(
									Integer.parseInt(split[1]),Integer.parseInt(split[2]),Integer.parseInt(split[3]),
									Integer.parseInt(split[4]),Integer.parseInt(split[5]),Integer.parseInt(split[6]),0);
			}
			inputFromFile.close();
			gameConfig.close();
		} catch (IOException e) {
			
		}
	}
	
	/**sorts all the excommunication cards
	 * 
	 * @author matteo
	 */
	private void randomExcommunications(){
		randomExcommunication(0);
		randomExcommunication(1);
		randomExcommunication(2);

	}
	
	/**sorts a single excommnication card from a specified era, reading from the file excommunicationcards.txt
	 * 
	 * @param i - the era of the required excommunication
	 * @author matteo
	 */
	private void randomExcommunication(int i){
		int ex;
		ex=(int)(i*7.0 + Math.random()*7.0 + 1.0);
		if(ex==(i+1)*7+1)
			ex-=1;
		FileReader gameConfig;
		try {
			/*excommunications setup*/
			gameConfig=new FileReader(this.getClass().getResource("/it/polimi/ingsw/LM6/configuration/excommunicationcards.txt").getPath());
			
			BufferedReader inputFromFile=new BufferedReader(gameConfig);
			String app=inputFromFile.readLine();
			for(int j=0;j<ex;j++){
				app=inputFromFile.readLine();
			}
			excommunications[i]=new ExcommunicationCard(app.split(";"));
			inputFromFile.close();
			gameConfig.close();
		} catch (IOException e) {
			
		}

	}
	
	
	/**@return an ArrayList of the players in the council, ordered from the first to the last player that
	 * placed a familiar in the council. 
	 */
	public ArrayList<Player> getPlayersInCouncil(){
		return this.council.getPlayersInCouncil();
	}
	
	public void reloadExcommunication(int era, String s) throws IOException{
		String[] app2=s.split("!");
		FileReader gameConfig=new FileReader(this.getClass().getResource("/it/polimi/ingsw/LM6/configuration/excommunicationcards.txt").getPath());
		BufferedReader inputFromFile=new BufferedReader(gameConfig);
		String app=inputFromFile.readLine();
		for(int j=0;j<21;j++){
			app=inputFromFile.readLine();
			ExcommunicationCard c=new ExcommunicationCard(app.split(";"));
			if(c.getNumber()==Integer.parseInt(app2[1]))
			{
				this.excommunications[era-1]=c;
				for(int i=2;i<app2.length;i++)
					c.excommunicatePlayer(game.getPlayerByNickname(app2[i]));
				break;
			}
		}
		inputFromFile.close();
		gameConfig.close();
	}
}
