package it.polimi.ingsw.LM6.server.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NameNotFoundException;

import it.polimi.ingsw.LM6.server.game.board.Board;
import it.polimi.ingsw.LM6.server.game.board.PersonalBonusTile;
import it.polimi.ingsw.LM6.server.game.bonuses.Bonus;
import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;
import it.polimi.ingsw.LM6.server.game.card.ExcommunicationCard;
import it.polimi.ingsw.LM6.server.game.card.LeaderCard;
import it.polimi.ingsw.LM6.server.game.card.PurpleCard;
import it.polimi.ingsw.LM6.server.game.debug.Debug;
import it.polimi.ingsw.LM6.server.game.exceptions.*;
import it.polimi.ingsw.LM6.server.game.familiar.Familiar;
import it.polimi.ingsw.LM6.server.game.scores.Record;
import it.polimi.ingsw.LM6.server.game.scores.Scoreboard;
import it.polimi.ingsw.LM6.server.game.states.GameState;
import it.polimi.ingsw.LM6.server.game.timer.ExcommunicationPhaseTimer;
import it.polimi.ingsw.LM6.server.game.timer.GameResumeTimer;
import it.polimi.ingsw.LM6.server.game.timer.GameStartTimer;
import it.polimi.ingsw.LM6.server.game.timer.PlayerTurnTimer;
import it.polimi.ingsw.LM6.server.game.timer.Timer;
import it.polimi.ingsw.LM6.server.network.NetworkData;
import it.polimi.ingsw.LM6.server.users.IUser;
import it.polimi.ingsw.LM6.server.users.SocketUser;

public class LorenzoIlMagnifico extends Thread{
	
	private long startTime;
	private long endTime;
	private long turnTimeMillis;
	private long startTimeOutMillis;
	
	private Boolean isStarted;
	private Boolean isFinished;

	private Boolean isPersonalBonusTilesPhase;
	private Boolean isLeaderDraftPhase;
	private Boolean isFamiliarOfferPhase;

	private Boolean usingAdvancedRules;
	private static final String playersSeparator=";";
	private GameState state;
	private Board gameBoard;
	
	private String filename;
	private final String savepath="/it/polimi/ingsw/LM6/savedgames/";
	
	/**used to create a new game
	 * 
	 * @param advancedRules
	 * @param maxNumOfPlayers
	 * @param filename
	 */
	public LorenzoIlMagnifico(boolean advancedRules, int maxNumOfPlayers, String filename){
		FileReader gameConfig;
		try {
			this.filename=filename;
			this.usingAdvancedRules=advancedRules;
			this.isStarted=false;
			this.isFinished=false;
			this.isLeaderDraftPhase=false;
			this.isFamiliarOfferPhase=false;
			this.isPersonalBonusTilesPhase=false;
			gameConfig=new FileReader(this.getClass().getResource("/it/polimi/ingsw/LM6/configuration/gameconfiguration.txt").getPath());
			BufferedReader inputFromFile=new BufferedReader(gameConfig);
			
			String app=inputFromFile.readLine();
			app=inputFromFile.readLine();
			this.turnTimeMillis=Long.parseLong(app);					
			app=inputFromFile.readLine();
			this.startTimeOutMillis=Long.parseLong(app);
			this.state=new GameState(maxNumOfPlayers);
			gameConfig.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Configuration files not found.");
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	/**used to reload a previously saved game
	 * 
	 * @param filename - the name of the file that contains the data about this game.
	 * @throws IOException 
	 */
	private LorenzoIlMagnifico(String filename) throws IOException{
		FileReader gameConfig;
		int era;
		int nPlayers;
		//BASE CONFIGURATION
		this.filename=filename;
		this.isStarted=false;
		this.isFinished=false;
		this.isLeaderDraftPhase=false;
		this.isFamiliarOfferPhase=false;
		this.isPersonalBonusTilesPhase=false;
		gameConfig=new FileReader(this.getClass().getResource("/it/polimi/ingsw/LM6/configuration/gameconfiguration.txt").getPath());
		BufferedReader inputFromFile=new BufferedReader(gameConfig);
		String app=inputFromFile.readLine();
		app=inputFromFile.readLine();
		this.turnTimeMillis=Long.parseLong(app);					
		app=inputFromFile.readLine();
		this.startTimeOutMillis=Long.parseLong(app);
		inputFromFile.close();
		gameConfig.close();
		
		//RELOADING THE GAME
		gameConfig=new FileReader(this.getClass().getResource(this.savepath).getPath()+ this.filename+".txt");
		inputFromFile=new BufferedReader(gameConfig);
		era=Integer.parseInt(inputFromFile.readLine());
		nPlayers=Integer.parseInt(inputFromFile.readLine());
		this.usingAdvancedRules=Boolean.parseBoolean(inputFromFile.readLine());
		app=inputFromFile.readLine();
		this.state= new GameState(nPlayers,era);
		for(int i=0;i<nPlayers;i++){
			IUser u= new SocketUser(null);
			u.setGame(this);
			u.setNickname(inputFromFile.readLine());
			Player p=new Player(u,this.state.getNumberOfFamiliarsPerPlayer(),inputFromFile.readLine(),inputFromFile.readLine(),inputFromFile.readLine(),inputFromFile.readLine(),inputFromFile.readLine(),inputFromFile.readLine(),inputFromFile.readLine(),inputFromFile.readLine());
			this.assignColourToPlayer(p, i+1);
			Player[] players=this.state.getPlayers();
			players[i]=p;
		}
		this.gameBoard=new Board(this);
		this.gameBoard.reloadExcommunication(1, inputFromFile.readLine());
		this.gameBoard.reloadExcommunication(2, inputFromFile.readLine());
		this.gameBoard.reloadExcommunication(3, inputFromFile.readLine());	
		inputFromFile.close();
		gameConfig.close();
	}
	/******RUN******/
	
	/**	Manages the game from the start to the first player's turn, possibly calling personalTilesDraft and leaderDraft.
	 * 	The other turns will be managed by the SocketClientGameListener.
	 * 
	 * 	@author Emilio
	 */
	@Override
	public void run() {
		this.sendAll("Game starts.;GAMESTART");

		this.setStartTime();

		
		if(this.usingAdvancedRules){
			this.personalTilesDraft();
			this.leaderDraft();
		}
		if(this.state.getNumberOfPlayers() == 5)
			this.familiarOfferPhase();
		
		this.newRound(); /** SET THE BOARD FOR THE FIRST ROUND **/
		this.updateBoard();
		this.updatePlayers();
		
		this.prepareNextTurn();
	}
	
	/******SHIFTERS******/
	
	/**	Handles the necessary operation to advance the game during every player turn.
	 * 	Updates CurrentPlayerIndex.
	 * 	Calls turnShift when the end of TurnArray is reached.
	 * 
	 * 	@author Emilio
	 **/
	public synchronized void playerShift() {
		if(this.state.getCurrentPlayerIndex() == this.state.getTotalPlayersTurnPerRound() - 1) //if it reaches the end of TurnArray
			this.roundShift();
		else{
			this.state.incrementCurrentPlayerIndex();
			Player currentPlayer = this.getPlayer(state.getCurrentPlayerIndex());
			if(!currentPlayer.isActive()){
				this.sendAll(currentPlayer.getNickname() + " is inactive.");
				this.playerShift();
			}
			else
				this.prepareNextTurn();
		}
	}
	
	/**	Handles the necessary operation to advance the game during every round.
	 * 	Updates CurrentRound and sets the board for the new round.
	 * 	Called by playerShift when it reaches the end of roundArray.
	 * 	Calls excommunicationPhase at the end of an even round.
	 * 
	 * 	@author Emilio
	 **/
	private void roundShift() {
		if(this.state.getRound()%2 == 0)
			this.excommunicationPhaseStart();
		else
			this.prepareNextRound();
	}
	
	/** Handles the necessary operation to advance the game during every era.
	 * 	Called by excommunicationPhase.
	 * 	Calls endGame at the end of era 3.
	 * 
	 * 	@author Emilio
	 **/
	public synchronized void eraShift() {

		if(state.getEra()<=2){
			this.state.incrementCurrentEra();
			this.sendAll("It's era " + state.getEra() + ".");
			
			if(this.state.getNumberOfPlayers() == 5)
				this.familiarOfferPhase();
			
			this.prepareNextRound();
			this.saveGame();
		}
		else{
			this.setEndTime();
			this.isFinished = true;
			this.endGame();
		}
	}
	
	/** Handles the initial part of the excommunication phase, sending the required messages to those who can afford not to be excommunicated.
	 * 
	 * 	@author Emilio
	 */
	private void excommunicationPhaseStart() {
		final long TIME = 60000;
		
		ExcommunicationCard exCard = gameBoard.getExcommunicationCard(state.getEra());

		Player[] players = state.getPlayers();
		int numOfPlayers = state.getNumberOfPlayers();
		
		this.state.setExcomunicationPhase();

		this.sendAll("Excommunication phase starts.");
		
		for(int i = 0; i < numOfPlayers; i++){
			ResourceSet pResources = players[i].getResourceSet();
			ResourceSet exCost = exCard.costOfExcommunication();
				
			if(pResources.contains(exCost)){
				String exCardDescription = new String(exCard.toString());
				players[i].send("EXCOMMCHOICEREQUEST;"+ exCardDescription);
			}
		}
		
		Timer timer = new ExcommunicationPhaseTimer(this, TIME);
		timer.start();	
	}
	
	/**	Handles the final part of the excommunication phase, determining the excommunicated players and notifying the players of the results.
	 * 	Called by the excommunicationPhaseTimer.
	 * 	Calls eraShift.  
	 * 	
	 * 	@author Emilio
	 */
	public void excommunicationPhaseEnd() {
		ExcommunicationCard exCard = gameBoard.getExcommunicationCard(state.getEra());
		Player[] players = state.getPlayers();
		int numOfPlayers = state.getNumberOfPlayers();
		
		this.sendAll("The following players have been excommunicated:");
		for(int i = 0; i<numOfPlayers; i++){
			if(players[i].hasBeenExcommunicated()){
				exCard.excommunicatePlayer(players[i]);
				this.sendAll(players[i].getNickname());
			}
			if(!players[i].hasBeenExcommunicated() || this.state.getEra() == 3){
				int pFaithPoints = players[i].getResourceSet().getFaithPoints();
				players[i].acquireResources(this.gameBoard.getFaithPointsBonus(pFaithPoints));
				players[i].getResourceSet().resetFaithPoints();
				//FaithBonus application
				if(!players[i].hasBeenExcommunicated())
					players[i].getPermanentBonusMalus().applyFaithBonus(players[i]);
			}
		}
		
		this.resetExcommunicationResult();
		this.state.resetExcomunicationPhase();
		
		this.eraShift();
	}
	
	/**	Handles the personalTilesDraft, progressively letting the players choose a tile between the ones avaiable. 
	 * 	Called at the beginning of the game.
	 * 	
	 * 	@author Emilio
	 */
	private void personalTilesDraft() {
		final long TIME = 30000;
		
		Player[] players = state.getPlayers();
		int numOfPlayers = state.getNumberOfPlayers();
		ArrayList<PersonalBonusTile> personalTiles = this.getPersonalBonusTilesAdvanced();
		String tiles;
		int i;
		int j;
		
		this.isPersonalBonusTilesPhase = true;
		this.sendAll("Personal Bonus Tiles Phase starts.");
		
		for(i = 0; i < numOfPlayers; i++){
			
			if(i < 4){
				tiles=new String();
				for(j = 0; j < personalTiles.size(); j++){
					tiles += ";";
					tiles += j+" - "+personalTiles.get(j).toString();
				}
				
				players[i].send("TILESDRAFT;Select one personal bonus tile beetween these ones." + tiles);
				
				Timer timer = new Timer(this, TIME);
				timer.start();
				
				try {
					timer.join();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					System.err.print("An interruption during the Personal Bonus Tiles Phase occurred.");
				}
			}
			
			int draftChoice = players[i].getDraftChoice();
			PersonalBonusTile choice = personalTiles.get(draftChoice);
			
			players[i].setPersonalTile(choice);
			personalTiles.remove(draftChoice);
			players[i].resetDraftChoice();
		}	
		
		this.sendAll("End of Personal Bonus Tiles Phase.");
		this.isPersonalBonusTilesPhase = false;
	}
	
	/**	Handles the leaderDraft, progressively letting the players choose the leader cards between the ones available. 
	 * 	Called at the beginning of the game.
	 * 	
	 * 	@author Emilio
	 */
	private void leaderDraft() {
		final int MAXSETSIZE = 4;
		final long TIME = 30000;
		
		Player[] players = this.state.getPlayers();
		int numOfPlayers = this.state.getNumberOfPlayers();
		ArrayList<LeaderCard> leaderList = this.getLeadersList();
		ArrayList<ArrayList<LeaderCard>> sets = new ArrayList<ArrayList<LeaderCard>>(numOfPlayers);
		ArrayList<LeaderCard> tmp = new ArrayList<LeaderCard>();
		String leaders;
		int i;
		int j;
		int k;
		
		this.isLeaderDraftPhase = true;
		this.sendAll("Leader cards choice phase starts.");
		
		for(i = 0; i < numOfPlayers; i++){
			sets.add(this.sortFourLeaders(leaderList));
		}
		
		for(i = 0; i < MAXSETSIZE; i++){
			
			if(i < 3){
				
				for(j = 0; j < numOfPlayers; j++){
					leaders=new String();
					for(k = 0; k < MAXSETSIZE - i; k++){
						leaders += ";";
						leaders += k+" - "+sets.get(j).get(k).toString();
					}
					players[j].send("LEADERDRAFT;Choose one leader between these ones." + leaders);
					
				}
				
				Timer timer = new Timer(this, TIME);
				timer.start();
				
				try{
					timer.join();
				}catch(InterruptedException e){
					Thread.currentThread().interrupt();
					System.err.print("An interruption during the Leader Draft Phase occurred.");
				}
				
			}
			
			//Distributing cards.
			for(j = 0; j < numOfPlayers; j++){
				int draftChoice = players[j].getDraftChoice();
				LeaderCard choice = sets.get(j).get(draftChoice);
				
				players[j].acquireLeader(choice);
				sets.get(j).remove(draftChoice);
				players[j].resetDraftChoice();
			}
			
			//Shifting sets.
			tmp = sets.get(0);
			sets.add(tmp);
			sets.remove(tmp);

		}
		
		this.sendAll("End of Leader Draft Phase.");
		this.isLeaderDraftPhase = false;
	}

	
	/**	Handles the familiar offer phase in a five players game, asking each player
	 * 	how many and which kind of resources he/she wants to offer to get the familiar.
	 * 	The player who makes the best offer (based on the amount of resources only) will
	 * 	get the familiar. If two or more players offer the same amount of resources, the
	 *  player will be chosen randomly.
	 *  
	 * 	@author Emilio
	 */
	protected void familiarOfferPhase(){
		final long TIME = 60000;
		
		this.resetPlayerOffers();
		this.disableBlackFamiliarsFivePlayersMode();
		this.isFamiliarOfferPhase = true;
		
		this.sendAll("Familiar offer phase starts.");
		for(Player p:this.state.getPlayers())
			if(p!=null)
				p.send("FAMOFFERREQUEST; ");
		
		Timer timer = new Timer(this, TIME);
		timer.start();
		
		try {
			timer.join();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			System.err.print("An interruption during the Familiar Offer Phase occurred.");
		}
		
		ResourceSet maxOffer = new ResourceSet();
		ArrayList<Player> winners = new ArrayList<Player>();
		
		for(Player p : state.getPlayers()){
			ResourceSet pOffer = p.getFamiliarOffer();
			if(!p.getResourceSet().contains(pOffer)){
				pOffer.internalSub(pOffer.sub(p.getResourceSet()));
			}
			if(pOffer.getTotalMaterialResources() > maxOffer.getTotalMaterialResources()){
				winners = new ArrayList<Player>();
				winners.add(p);
				maxOffer = pOffer;
			}
			if(pOffer.getTotalMaterialResources() == maxOffer.getTotalMaterialResources())
				winners.add(p);
		}
		
		int rand=(int)(Math.random()*winners.size());
		if(rand==winners.size())
			rand--;
		Player winner = winners.get(rand);
		try{
			winner.pay(winner.getFamiliarOffer());
		} catch(NotEnoughResourcesException e){
			//This can't happen.
		}
		this.enableBlackFamiliarWinnerFivePlayersMode(winner);
		
		this.sendAll("The familiar will be assigned to " + winner.getNickname() + ".");
		
		this.isFamiliarOfferPhase = false;
		this.resetFamiliarOffers();
	}
	
	/**	Calculates the final VPs of every player, showing the game's results and the local scoreboard.
	 * 
	 * 	@author Emilio
	 */
	private void endGame() {
		Player[] players = this.state.getPlayers();
		int numOfPlayers = state.getNumberOfPlayers();
		Scoreboard localScoreboard = new Scoreboard();
		Scoreboard globalScoreboard = new Scoreboard();
		long gameTime = this.endTime - this.startTime;
		String recordTime = Record.parseRecordTimeFormat(gameTime);
		int i;
		
		this.orderPlayersByMP(); //required for evaluateMilitaryVP
		for(i = 0; i<numOfPlayers; i++){
			players[i].getPermanentBonusMalus().applyVPNumericalMaluses(players[i]);
			
			int totalVP = 0;
			totalVP += this.evaluateGreenCardsVP(players[i]);
			totalVP += this.evaluateBlueCardsVP(players[i]);
			totalVP += this.evaluatePurpleCardsVP(players[i]);
			totalVP += this.evaluateMilitaryVP(players[i]);
			totalVP += this.evaluateResourceSetVP(players[i]);
			
			localScoreboard.update(new Record(1, players[i].getNickname(), 0, 0, 0, totalVP, recordTime));
		}
		
		localScoreboard = localScoreboard.applyResults();
		
		NetworkData.getGlobalScoreboard().update(localScoreboard);
		
		for(i = 0; i<numOfPlayers; i++){
			try{
				Record toPrint = NetworkData.getGlobalScoreboard().findPlayerRecord(players[i].getNickname());
				globalScoreboard.add(toPrint);
			} catch(NameNotFoundException e){
				//This shouldn't happen normally.
				this.sendAll("Error: no record found for " + players[i].getNickname());
			}
		}
		
		for(i = 0; i < numOfPlayers; i++)
			players[i].send("ENDGAME; ;" + localScoreboard.toString("%n") + ";"+ globalScoreboard.toString("%n") + ";" + localScoreboard.toString("!") + ";" + globalScoreboard.toString("!"));  
		
		this.sendAll("  ");
		this.sendAll("Thanks for playing the game!");
		this.sendAll("Until next time! ;)");
		this.sendAll("  ");
		this.sendAll("This game was programmed by Matteo Biasielli, Emilio Capo and LUCADONDONI.");
		
		this.clean();
	}
	
	/******MORE_METHODS******/
	
	public void initGame() {
		try {
			this.gameBoard=new Board(this);
			this.isStarted=true;
			this.decideInitialRoundOrder();
			this.assignInitialResources();
			this.state.defineFamiliarsPerPlayer();
			this.state.initializeRoundArray();
			this.state.createExcommunicationChoices();
			this.start();
		} catch (BadInputException e) {
			Logger.getLogger("MyLogger").log(Level.SEVERE, e.getMessage());
			System.err.println("Impossibile avviare la partita, controlla file di configurazione.");
		}
		
	}
	
	/**@return true if it's player p's turn,
	 * false otherwise
	 * @param p - the player of which want to know if it has the turn or not
	 * @author matteo
	 */
	public Boolean isTurn(Player p) {
		return p==this.state.currentTurnPlayer();
	}
	
	
	/**adds a new user to the game and returns the Player object
	 * @throws fullGameExeption if the game has already maxNumberOfPlayers Players
	 * @throws alreadyStartedGameException if the game has already started
	 * @param u - the IUser object containing the data about the user that is going to be added in the game
	 * @return the Player object relative to the user that has just been added
	 * @author matteo
	 */
	public Player addPlayer(IUser u) throws FullGameException {
		Debug.print("Adding player...");
		
		Player p=new Player(u, this, this.state.getNumberOfFamiliarsPerPlayer());
		this.state.addPlayer(p);
		u.setGame(this);
		this.assignColourToPlayer(p, this.state.getNumberOfPlayers());
		Debug.print("Player added.");
		return p;
	}
	
	
	/** @return true if the player is active (not disconnected or afk for too long),
	 * false otherwise
	 * @author matteo
	 */
	private Boolean isActive( Player p) {
		return p.isActive();	
	}
	
	
	public Board getGameBoard() {
		return gameBoard;	
	}
	
	/**	Sends a String message to every player in the game.
	 * 	It calls Player's method send which filters out inactive players.
	 * 
	 * 	@param s - The String message to be sent.
	 * 
	 * 	@author Emilio
	 */
	public void sendAll(String s) {
		Player[] players = state.getPlayers();
		int numOfPlayers = state.getNumberOfPlayers();
		for(int i = 0; i < numOfPlayers; i++){
			players[i].send("INFO;" + s);
		}
	}
	
	/**allows the player to get the resource set contained in the bonus specified as parameter
	 * @param p - the player that is obtaining the resources
	 * @param b - the bonus that is going to be given to the player p
	 * @author matteo
	 */
	public void giveResourcesToPlayer(Player p, Bonus b){
		p.acquireResourcesFromBonus(b);
		this.updateBoard();
		this.updatePlayers();
	}
	
	/**throws the dice (generates 3 random numbers: white, black, orange)
	 * updates the Familiars' values of every player
	 * @author matteo
	 */
	public void throwDice() {
		int b;
		int o;
		int w;
		b=(int)(Math.random()*6+1);
		o=(int)(Math.random()*6+1);
		w=(int)(Math.random()*6+1);
		this.state.setFamiliarsValues(b, o, w, 0);
		/*Sends the update to all active players*/
		this.sendAll("DICE ROLL: B="+b+"  O="+o+"  W="+w+";DICE;"+b+";"+o+";"+w);
	}

	/**randomly sorts the playersArray to decide the initial turn order
	 *@author matteo 
	 */
	private void decideInitialRoundOrder() {
		int nPlayers=this.state.getNumberOfPlayers();
		Player[] app=new Player[nPlayers];
		boolean[] used=new boolean[nPlayers];
		Player[] players=this.state.getPlayers();
		for(int i=0;i<nPlayers;i++)
			used[i]=false;
		for(int i=0;i<nPlayers;i++){
			int rand;
			do{
				rand=(int)(Math.random()*nPlayers);
				if(rand==nPlayers)
					rand--;
				Debug.print("RAND=" + Integer.toString(rand));
			}while(used[rand]);
			app[i]=players[rand];
			used[rand]=true;
		}
		for(int i=0;i<nPlayers;i++){
			players[i]=app[i];
			Debug.print("Nick=" + app[i].getNickname());
		}
	}
	
	

	/**Calls the placeFamiliar Method of the right tower
	 * @throws NotEnoughResourcesException if the player doesn't have the required resources
	 * @throws NotStrongEnoughException if the familiar is not strong enough
	 * @throws AlreadyOccupiedException if the space is already used
	 * @throws AlreadyUsedFamiliarException if the familiar has already been placed
	 * @throws AnotherFamiliarSameColourException if there's another familiar of the same colour in the area
	 * @throws NotEnoughMilitaryPoints if the player doesn't have enough military points to get a green card, if the card is green
	 * @throws DoubleCostException if the cards has two possible costs
	 * @throws SixCardsException if the player has already six card of the selected colour
	 * @param action - the action object containing the data about the action that has to be performed
	 * @return the bonus obtained from the action
	 * @author matteo
	 */
	public Bonus placeOnTower( Action action) throws AlreadyUsedFamiliarException, AnotherFamiliarSameColourException, AlreadyOccupiedException, NotStrongEnoughException, NotEnoughResourcesException, NotEnoughMilitaryPointsException, SixCardsException, DoubleCostException{
		if(action.getFamiliar().isPlaced())
			throw new AlreadyUsedFamiliarException("This familiar has already been placed in this turn");
		/*Handle the action and calculates the resulting bonus*/
		Bonus result=this.gameBoard.placeOnTower(action);
		/*Sends the update to all active players*/
		this.sendAll(action.getPlayer().getNickname()+" placed the "+ action.getFamiliar().getColourString()+" familiar in the "
					+ action.getTowerCardColor().toString() +" tower, floor " + action.getFloorTowerMarket());
		this.updateBoard();
		this.updatePlayers();
		return result;
	}
	
	
	/**Calls the placeFamiliar Method of the council
	 * @throws NotStrongEnoughException if the familiar is not strong enough
	 * @throws AlreadyUsedFamiliarException if the familiar has already been placed
	 * @throws NotEnoughResourcesException if the player doesn't have the required resources
	 * @param action - the action object containing the data about the action that has to be performed
	 * @return the bonus obtained from the action
	 * @author matteo
	 */
	public Bonus placeOnCouncil( Action action) throws NotStrongEnoughException, AlreadyUsedFamiliarException, NotEnoughResourcesException{
		if(action.getFamiliar().isPlaced())
			throw new AlreadyUsedFamiliarException("This familiar has already been placed in this turn");
		/*Handle the action and calculates the resulting bonus*/
		Bonus result=this.gameBoard.placeOnCouncil(action);
		/*Sends the update to all active players*/
		this.sendAll(action.getPlayer().getNickname()+" placed the "+ action.getFamiliar().getColourString()+" familiar in the council");
		this.updateBoard();
		this.updatePlayers();
		return result;
	}
	
	
	/**Calls the PlaceFamiliar Method of the market
	 * @throws NotStrongEnoughException if the familiar is not strong enough
	 * @throws AlreadyUsedFamiliarException if the familiar has already been placed
	 * @throws NotEnoughResourcesException if the player doesn't have the required resources
	 * @throws AlreadyOccupiedException if the space is already occupied
	 * @throws CannotGoOnMarketException if the player has the related malus
	 * @param action - the action object containing the data about the action that has to be performed
	 * @return the bonus obtained from the action
	 * @author matteo
	 */
	public Bonus placeOnMarket( Action action) throws NotStrongEnoughException, AlreadyUsedFamiliarException, NotEnoughResourcesException,
														AlreadyOccupiedException, CannotGoOnMarketException{
		if(action.getFamiliar().isPlaced())
			throw new AlreadyUsedFamiliarException("This familiar has already been placed in this turn");
		/*Handle the action and calculates the resulting bonus*/
		Bonus result=this.gameBoard.placeOnMarket(action);
		/*Sends the update to all active players*/
		this.sendAll(action.getPlayer().getNickname()+" placed the "+ action.getFamiliar().getColourString()+" familiar in the market"
				+ ", space " + action.getFloorTowerMarket());
		this.updateBoard();
		this.updatePlayers();		
		return result;
	}
	
	
	/**Calls the placeFamiliarHarvest Method of the productionArea
	 * @throws NotStrongEnoughException if the familiar is not strong enough
	 * @throws AlreadyUsedFamiliarException if the familiar has already been placed
	 * @throws AnotherFamiliarSameColourException if there's another familiar of the same colour in the area
	 * @throws AlreadyOccupiedException if the space is already occupied
	 * @throws NotEnoughResourcesException if the player doesn't have enough resources
	 * @param action - the action object containing the data about the action that has to be performed
	 * @return the bonus obtained from the action
	 * @author matteo
	 */
	public Bonus placeFamiliarHarvest(Action action) throws AlreadyUsedFamiliarException, NotStrongEnoughException, AnotherFamiliarSameColourException, AlreadyOccupiedException, NotEnoughResourcesException{
		if(action.getFamiliar().isPlaced())
			throw new AlreadyUsedFamiliarException("This familiar has already been placed in this turn");
		/*Handle the action and calculates the resulting bonus*/
		Bonus result=this.gameBoard.placeFamiliarHarvest(action);
		/*Sends the update to all active players*/
		this.sendAll(action.getPlayer().getNickname()+" placed the "+ action.getFamiliar().getColourString()+" familiar in the space "
				+ action.getFloorTowerMarket()+" of the harvest area");
		this.updateBoard();
		this.updatePlayers();	
		return result;
	}
	
	
	/**Calls the placeFamiliarProduction Method of the productionArea
	 * @throws NotStrongEnoughException if the familiar is not strong enough
	 * @throws AlreadyUsedFamiliarException if the familiar has already been placed
	 * @throws AnotherFamiliarSameColourException if there's another familiar of the same colour in the area 
	 * @param action - the action object containing the data about the action that has to be performed
	 * @return the bonus obtained from the action
	 * @author matteo
	 */
	public Bonus placeFamiliarProduction( Action action) throws AlreadyUsedFamiliarException, NotStrongEnoughException, AnotherFamiliarSameColourException, AlreadyOccupiedException, NotEnoughResourcesException{
		if(action.getFamiliar().isPlaced())
			throw new AlreadyUsedFamiliarException("This familiar has already been placed in this turn");
		/*Handle the action and calculates the resulting bonus*/
		Bonus result= this.gameBoard.placeFamiliarProduction(action);
		/*Sends the update to all active players*/
		this.sendAll(action.getPlayer().getNickname()+" placed the "+ action.getFamiliar().getColourString()+" familiar in the space "
				+ action.getFloorTowerMarket()+" of the production area");
		this.updateBoard();
		this.updatePlayers();
		return result;
	}
	
	
	/**Allows to play a LeaderCard
	 * @param p - the player that is playing the leader
	 * @param i - the number of the leader to play
	 * @throws LeaderException if the player doesn't satisfy leader requirements or if the specified leader doesn't exist
	 * @throws LorenzoIlMagnificoException if the played card is Lorenzo De Medici
	 * @author matteo
	 */
	public void playLeaderCard(Player p, int n) throws LeaderException, LorenzoIlMagnificoException{
		ArrayList<LeaderCard> leaders=p.getLeadersInHand();
		try{
			LeaderCard toBePlayed=leaders.get(n);
			if(toBePlayed.canBeActivatedBy(p)){
				if(toBePlayed.getName().equals("Lorenzo De Medici")){
					ArrayList<LeaderCard> played=this.getPlayedLeaders();
					ArrayList<String> playedString= new ArrayList<String>();
					for(int i=0;i<played.size();i++)
						playedString.add(played.get(i).toString());
					toBePlayed.activate(p);
					p.getLeadersPlayed().add(toBePlayed);
					p.getLeadersInHand().remove(toBePlayed);
					this.updatePlayers();
					if(played.size()>0)
						throw new LorenzoIlMagnificoException("You Played Lorenzo De Medici", playedString);	
				}
				else{
					toBePlayed.activate(p);	
					p.getLeadersPlayed().add(toBePlayed);
					p.getLeadersInHand().remove(toBePlayed);
					this.updatePlayers();
				}
			}
			else
				throw new LeaderException("You can't play this leader");
		}catch(IndexOutOfBoundsException e){
			throw new LeaderException("You don't have this leader");
		}
	}
	
	/**called onl after the leader Lorenzo De Medici has been played, allows the player that played it
	 * to copy the effect from another played leader, choosen from a list that was previously sent to the player
	 * @param p - the player  that played Lorenzo De Medici
	 * @param n - the index of the leader to copy (note that if 10 leaders have been played, n goes from 0 to 9)
	 * @throws LeaderException
	 */
	public void copyLeaderEffect(Player p, int n) throws LeaderException{
		ArrayList<LeaderCard> played=this.getPlayedLeaders();
		try{
			played.get(n).activate(p);
		}catch(IndexOutOfBoundsException e){
			throw new LeaderException("You cannot copy this leader (doesn't exist)");
		}
	}
	
	/**Allows to discard a LeaderCard specified in the Action object.
	 * @param a - the action object containing the data about the action that has to be performed
	 * @throws LeaderException if the specified leader doesn't exist
	 * @return 1 council bonus
	 * @author matteo
	 */
	public Bonus discardLeaderCard(Action a) throws LeaderException{
		ArrayList<LeaderCard> leaders=a.getPlayer().getLeadersInHand();
		try{
			leaders.remove(a.getFloorTowerMarket());
		}catch(IndexOutOfBoundsException e){
			throw new LeaderException("You can't discard this leader");
		}
		this.updatePlayers();
		return new Bonus(1);
	}
	
	/**calculates the total of the resources that the player has to get on a certain turn
	 * thanks to one or more leaders' effects
	 * @param p - the player
	 * @return the total bonus (resources)
	 * @throws AlreadyUsedBonusException if the player does't have the bonus or has already used it
	 * @author matteo
	 */
	public Bonus getEveryTurnResources(Player p) throws AlreadyUsedBonusException{
		return p.getPermanentBonusMalus().getEveryTurnResources(p);
	}
	
	/**checks if the player has the every turn harvest given from a leader
	 * @param p - the player
	 * @return a Bonus object representing the bonus harvest
	 * @throws AlreadyUsedBonusException if the player does't have the bonus or has already used it
	 * @author matteo
	 */
	public Bonus getEveryTurnHarvest(Player p) throws AlreadyUsedBonusException{
		return p.getPermanentBonusMalus().getEveryTurnHarvest(p);
	}
	
	/**checks if the player has the every turn production given from a leader
	 * @param p - the player
	 * @return a Bonus object representing the bonus production
	 * @throws AlreadyUsedBonusException if the player does't have the bonus or has already used it
	 * @author matteo
	 */
	public Bonus getEveryTurnProduction(Player p) throws AlreadyUsedBonusException{
		return p.getPermanentBonusMalus().getEveryTurnProduction(p);
	}
	
	/** applies the specified bonus, if present, setting the familiar's given as parameter's value to 6
	 * 
	 * @param p - the player
	 * @param f - the familiar
	 * @throws AlreadyUsedBonusException
	 * @author matteo
	 */
	public void applyOneSixFamiliarPerTurn(Player p, Familiar f) throws AlreadyUsedBonusException{
		p.getPermanentBonusMalus().applyOneSixFamiliarPerTurn(p, f);
	}
	
	
	/**@return the total Bonus considering the type of the request
	 * @author matteo
	 */
	public Bonus getCouncilBonus(Action a) throws InvalidActionException{
		if(("COUNCILREQUIREMENT").equals(a.getType()))
			return this.getSingleCouncilBonus(a.getFloorTowerMarket());
		if(("DOUBLECOUNCILREQUIREMENT").equals(a.getType()))
			return this.getSingleCouncilBonus(a.getProductionChoice(0)).sum(this.getSingleCouncilBonus(a.getProductionChoice(1)), a.getPlayer());
		if(("TRIPLECOUNCILREQUIREMENT").equals(a.getType()))
			return this.getSingleCouncilBonus(a.getProductionChoice(0)).sum(this.getSingleCouncilBonus(a.getProductionChoice(1)), a.getPlayer()).sum(this.getSingleCouncilBonus(a.getProductionChoice(2)),  a.getPlayer());
		throw new InvalidActionException("Invalid action data.");
	}
	
	
	/** This method supports and is only used by the getCouncilBonus method
	 *   @return the council Bonus corresponding to the number given as parameter.
	 *   @author matteo
	 */
	private Bonus getSingleCouncilBonus(int n){
		if(n==1)
			return new Bonus(new ResourceSet(1,1,0,0,0,0,0));
		if(n==2)
			return new Bonus(new ResourceSet(0,0,0,2,0,0,0));
		if(n==3)
			return new Bonus(new ResourceSet(0,0,2,0,0,0,0));
		if(n==4)
			return new Bonus(new ResourceSet(0,0,0,0,2,0,0));
		if(n==5)
			return new Bonus(new ResourceSet(0,0,0,0,0,0,1));			
		return new Bonus();
	}
	
	/**@return a string version of the board
	 * @author matteo
	 */
	public String showBoard(){
		return "BOARD;Game Board;"+this.gameBoard.toString()+this.getPlayerColourCorrespondance();
	}
	
	/**
	 * 
	 * @param pl - the player that asked to see other players' data
	 * @return a string that fully describes all the players, from the point of view of a player given as parameter
	 * @author matteo
	 */
	public String showPlayers(Player pl){
		String ris="PLAYERS"+playersSeparator+"Players list"+playersSeparator;
		for(Player p: this.state.getPlayers())
			if(p!=null)
				ris=ris+p.toString(pl)+playersSeparator;
		return ris;
	}
	/**used to update the GUI after every action, sends the updated board to every player
	 * 
	 */
	public void updateBoard(){
		String mess="UPDATEBOARD"+playersSeparator+" "+playersSeparator+this.gameBoard.toString()+this.getPlayerColourCorrespondance();
		for(Player p: this.state.getPlayers())
			if(p!=null)
				p.send(mess);
	}
	/**used to update the GUI after every action, sends the updated playerslist to every player
	 * 
	 */
	public void updatePlayers(){
		String ris="UPDATEPLAYERS"+playersSeparator+" "+playersSeparator;
		for(Player pl:this.state.getPlayers()){
			if(pl!=null){
				String app=ris;
				for(Player p: this.state.getPlayers())
					if(p!=null){
						app+=p.toString(pl)+playersSeparator;
					}
				pl.send(app);
			}
		}
	}
	public int getNumberOfPlayers(){
		return this.state.getNumberOfPlayers();
	}
	
	public Boolean isStarted(){
		return this.isStarted;
	}
	
	public Boolean isFinished(){
		return this.isFinished;
	}
	
	public GameState getStatus(){
		return this.state;
	}
	
	
	/**@return the player i from the players array
	 * 
	 * @param i the number of the player that has to be returned. Must be < than the number of players
	 * @author matteo
	 */
	public Player getPlayer(int i){
		Player[] list = state.getRoundArray();
		return list[i];
	}
	
	/**	Checks if the game can be started.
	 * 
	 * 	@author Emilio
	 */
	public void checkStartCondition(){
		if(this.state.hasReachedTheMaximumNumberOfPlayers()){
			Debug.print("Max number of players reached.");
			
			this.initGame();
		}
		else if(this.state.hasReachedTheMinimumNumberOfPlayers()){
			Debug.print("Min number of players reached.");
			
			Timer timer = new GameStartTimer(this, this.startTimeOutMillis);
			timer.start();
		}
	}
	
	/**	Checks if the game can be resumed.
	 * 
	 * 	@author Emilio
	 */
	public void checkResumeCondition(){
		Debug.print("connectedUsers: " + Integer.toString(this.state.getNumberOfConnectedUsers()));
		if(this.state.getNumberOfConnectedUsers() == this.state.getNumberOfPlayers()){
			Debug.print("Max number of players reached.");
			
			this.resumeGame();
		}
		else if(this.state.getNumberOfConnectedUsers() == 2){
			Debug.print("Min number of connected players reached.");
			
			Timer timer = new GameResumeTimer(this, this.startTimeOutMillis);
			timer.start();
		}
	}
	
	
	/**Sends the first menu to the player which is starting the turn
	 * 
	 * @param p - the player
	 * @author matteo
	 */
	public void sendMenu(Player p){
		String yellow=Integer.toString(p.getYellowCardList().size());
		String menu;
		if(!this.usingAdvancedRules  || !(p.getLeadersInHand().size()>0))
			menu="MENU; ;1;1;1;1;1;0;0;1;1;1;";
		else
			menu="MENU; ;1;1;1;1;1;1;1;1;1;1;";
		if(!(p.getLeadersPlayed().isEmpty()))
			menu+="1;1;1;1;";
		else
			menu+="0;0;0;0;";
		menu+=yellow;
		p.send(menu);
	}
	
	public boolean hasAdvancedRules(){
		return this.usingAdvancedRules;
	}
	

	/**handles the required operation to start a new turn
	 * @author matteo
	 */
	public void newRound() {
		this.orderPlayersNewRound();
		this.gameBoard.newTurn(this.state.getRound());
		this.resetFamiliars();
		this.state.createRoundArray();
		for(Player p:this.state.getPlayers())
			if(p!=null)
				p.getPermanentBonusMalus().resetEveryTurnBonuses();
		this.throwDice();
	}

	/**sets all the familiars to the not placed status
	 * @author matteo
	 */
	private void resetFamiliars(){
		int n=this.state.getNumberOfPlayers();
		Player[] p=this.state.getPlayers();
		for(int i=0;i<n;i++){
			if(p!=null)
				p[i].resetFamiliars(this.state.getNumberOfPlayers());
		}
	}
	
	/** when a new round starts, orders the playersArray in the status, according to the order the player placed their familiars in the council, if they did
	 * @author matteo
	 */
	private void orderPlayersNewRound(){
		ArrayList<Player> council= this.gameBoard.getPlayersInCouncil();
		Player[] players=this.state.getPlayers();
		int n=this.state.getNumberOfPlayers();
		for(int i=0;i<council.size();i++){
			Player p=council.get(i);
			for(int j=0;j<n;j++){
				if(p==players[j]){
					for(int k=j;k>i;k--)
						players[k]=players[k-1];
					players[i]=p;
					break;
				}
			}
		}
	}
	
	/** Finds the player that is going to have the next turn and sends him board&players and the menu.
	 * Starts the timer.
	 */
	private void prepareNextTurn(){
		Player next = this.getPlayer(state.getCurrentPlayerIndex());
		
		this.sendAll("It's " + next.getNickname() + "'s turn.");
		
		for(Player p : this.state.getPlayers())
			if(p != null)
				this.sendBoardPlayers(p);
		
		this.sendMenu(next);
		
		Timer timer = new PlayerTurnTimer(this, turnTimeMillis);
		timer.start();
	}
	
	/**	Carries out the necessary operations to prepare the next game round.
	 * 
	 * 	@author Emilio
	 */
	private void prepareNextRound(){
		this.state.resetCurrentPlayerIndex();
		this.state.incrementCurrentRound();
		
		this.newRound();
		
		this.sendAll("It's turn " + state.getRound() + ".");
		
		Player currentPlayer = this.getPlayer(state.getCurrentPlayerIndex());
		if(!currentPlayer.isActive()){
			this.sendAll(currentPlayer.getNickname() + " is inactive.");
			this.playerShift();
		}
		else
			this.prepareNextTurn();
	}

	
	/**sets Player.excommunicationResult to true for all the players. 
	 * That means that it's assumed that every player wants to be excommunicated and he's not excommunicated ONLY if he says the contrary
	 * @author matteo
	 */
	private void resetExcommunicationResult(){
		Player[] players = this.state.getPlayers();
		int numOfPlayers = state.getNumberOfPlayers();
		
		for(int i = 0; i<numOfPlayers; i++){
			players[i].resetExcommunicationResult();
		}
	}

	/**assigns the initial resources to the players
	 * @author matteo
	 * 
	 */
	private void assignInitialResources(){
		int n=this.state.getNumberOfPlayers();
		Player[] players=this.state.getPlayers();
		int w=2;
		int r=2;
		int c=5;
		int s=3;
		for(int i=0;i<n;i++){
			players[i].acquireResources(new ResourceSet(w,r,c,s,0,0,0));
			c++;
		}
	}
	
	
	/**Sends a string representing the board and  a string representing the players to the player p.
	 * The string representing the players shows the leaders in hand only for the player p, since they're secret and each player cannot see other players' leaders in hand
	 * @param p - the player that has to receive the string
	 * @author matteo
	 */
	private void sendBoardPlayers(Player p){
		p.send(this.showBoard());
		p.send(this.showPlayers(p));
	}
	
	
	/**Sets Player.excommunicationResult to false, which means that the player doesn't want to be excommunicated
	 * 
	 * @param p - the player
	 * @author matteo
	 */
	public void setExcommunicationResult(Player p){
		p.setExcommunicationResult();
	}
	
	/**	@author Emilio
	 */
	private void resetFamiliarOffers(){
		Player[] players = this.state.getPlayers();
		int numOfPlayers = state.getNumberOfPlayers();
		
		for(int i = 0; i<numOfPlayers; i++)
			players[i].resetFamiliarOffer();
	}
	
	/** Evaluates the VPs obtained through the player's green cards. 
	 * 
	 * 	@param p - The player whose VPs will be evaluated.
	 * 
	 * 	@return - The evaluated VPs.
	 * 
	 * 	@author Emilio
	 */
	public int evaluateGreenCardsVP(Player p){
		try{
			p.getPermanentBonusMalus().applyGreenVPMalus();
		} catch(NoVPException e){
			return 0;
		}
		return this.evaluateBGCardsVP(this.getClass().getResource("/it/polimi/ingsw/LM6/configuration/greencardsfinalvictorypoints.txt").getPath(), p.getGreenCardList().size());	
	}
	
	/** Evaluates the VPs obtained through the player's blue cards. 
	 * 
	 * 	@param p - The player whose VPs will be evaluated.
	 * 
	 * 	@return - The evaluated VPs.
	 * 
	 *  @author Emilio
	 */
	public int evaluateBlueCardsVP(Player p){
		try{
			p.getPermanentBonusMalus().applyBlueVPMalus();
		} catch(NoVPException e){
			return 0;
		}
		return this.evaluateBGCardsVP(this.getClass().getResource("/it/polimi/ingsw/LM6/configuration/finalbluecardsvictorypoints.txt").getPath(), p.getGreenCardList().size());	
	}
	
	/** Evaluates the VPs obtained through the player's blue or green cards. 
	 * 
	 * 	@param path - The path where the couples (number of cards -> number of VPS) are saved.
	 * 	@param pCards - The number of cards of a certain colour.
	 * 
	 * 	@return - The evaluated VPs.
	 * 
	 * 	@author Emilio
	 */
	private int evaluateBGCardsVP(String path, int pCards){
		int pCardsVP = 0;
		if(pCards != 0){
			File file = new File(path);
			FileReader f = null;
			try {
				f = new FileReader(file);
			
			
				Debug.print("File opened.");
				BufferedReader b = new BufferedReader(f);
				String line;

				line = b.readLine();
				
				for(int i = 0; i < pCards; i++){
					line = b.readLine();
				}
				
				String[] line_s = line.split(";");
				pCardsVP = Integer.parseInt(line_s[1]);
				f.close();	
			}catch(IOException e){
				//This can't happen.
			}	
		}
		return pCardsVP;
	}
	
	/** Evaluates the VPs obtained through the player's purple cards.
	 * 
	 * 	@param p - The player whose VPs will be evaluated.
	 * 
	 * 	@return - The evaluated VPs.
	 * 
	 * 	@author Emilio
	 */
	private int evaluatePurpleCardsVP(Player p){
		try{
			p.getPermanentBonusMalus().applyPurpleVPMalus();
		} catch(NoVPException e){
			return 0;
		}
		ArrayList<PurpleCard> purpleCards = p.getPurpleCardList();
		int purpleCardsVP = 0;
		
		for(PurpleCard card : purpleCards)
			purpleCardsVP += card.getFinalBonus().getVictoryPoints();
		
		return purpleCardsVP;
	}
	
	/**	Orders the players by MPs (descending order).
	 * 
	 * 	@author Emilio
	 */
	private void orderPlayersByMP(){
		Player[] players = this.state.getPlayers();
		int limit = state.getNumberOfPlayers();
		int i;
		int j;

		for(i = 0; i<limit-2; i++){
			for(j = i; j<limit-1; j++){
				if(players[j].getResourceSet().getMilitaryPoints()<players[j+1].getResourceSet().getMilitaryPoints()){
					Player tmp = players[j];
					players[j] = players[j+1];
					players[j+1] = tmp;
				}
			}
		}
	}
	
	/** Evaluates the VPs obtained through the player's resource set. 
	 * 
	 * 	@param p - The player whose VPs will be evaluated.
	 * 
	 * 	@return - The evaluated VPs.
	 * 
	 * 	@author Emilio
	 */
	private int evaluateMilitaryVP(Player p){
		Player[] players = this.state.getPlayers();
		int limit = state.getNumberOfPlayers();
		int i;

		int militaryVP = 5;
		
		if(!p.equals(players[0])){
			int referenceMP = players[0].getResourceSet().getMilitaryPoints();
			boolean breakflag = false;
			int currentMP;
			
			for(i = 1; i<limit; i++){
				currentMP = players[i].getResourceSet().getMilitaryPoints();
				if(!breakflag && referenceMP == currentMP){
					breakflag = true;
				}
				else if(referenceMP > currentMP){
					if(breakflag){
						militaryVP = 0;
						break;
					}
					else{
						referenceMP = currentMP;
						militaryVP = 2;
						breakflag = true;
					}
				}
				if(p.equals(players[i]))
					break;
			}
		}
		return militaryVP;
	}
	
	/** Evaluates the VPs obtained through the player's resource set. 
	 * 
	 * 	@param p - The player whose VPs will be evaluated.
	 * 
	 * 	@return - The evaluated VPs.
	 * 
	 * 	@author Emilio
	 */
	private int evaluateResourceSetVP(Player p){
		int materialResourcesVP = p.getResourceSet().getTotalMaterialResources()/5;
		return p.getResourceSet().getVictoryPoints() + materialResourcesVP;
	}
	
	
	public Boolean isExcommunicationPhase(){
		return this.state.isExcomunicationPhase();
	}
	
	/**	Finds the player in the game who corresponds to the given IUser.
	 * 
	 * 	@param user The IUser to compare with the IUser attribute in the player class.
	 * 
	 * 	@return the player who has user as its IUser attribute.
	 * 
	 * 	@throws NoSuchElementException When no player corresponds to the given IUser.
	 * 
	 * 	@author Emilio
	 */
	public Player findPlayer(IUser user){
		Player[] players = this.state.getPlayers();
		int limit = this.state.getNumberOfPlayers();
		
		for(int i=0; i < limit; i++)
			if(players[i].getUser().equals(user))
				return players[i];
		
		throw new NoSuchElementException("The given IUser does not correspond to a player.");
	}
	
	/** reads all the leader cards from the file leaders.txt
	 * 
	 * @return an arrayList containing all the LeaderCards
	 * @author matteo
	 */
	private ArrayList<LeaderCard> getLeadersList(){
		FileReader gameConfig;
		String s;
		ArrayList<LeaderCard> leaders= new ArrayList<LeaderCard>();
		try{
			gameConfig=new FileReader(this.getClass().getResource("/it/polimi/ingsw/LM6/configuration/leaders.txt").getPath());
			BufferedReader inputFromFile=new BufferedReader(gameConfig);
			s=inputFromFile.readLine();
			for(int i=0;i<20;i++){
				s=inputFromFile.readLine();
				LeaderCard app=new LeaderCard(s);
				leaders.add(app);
			}
			inputFromFile.close();
			gameConfig.close();
		}catch(IOException e){
			//TODO
		}
		return leaders;
	}
	
	
	/**randomly picks 4 leaders from the list given as parameter and returns a mini-list with the 4 leaders
	 * @param leaders - the list of leaders where the 4 leaders will be looked for
	 * @return an ArrayList of LeaderCards containing the 4 randomly picked leaders
	 * @author matteo
	 */
	private ArrayList<LeaderCard> sortFourLeaders(ArrayList<LeaderCard> leaders){
		if(leaders==null || leaders.size()<4)
			throw new BadInputException("Invalid input");
		ArrayList<LeaderCard> result= new ArrayList<LeaderCard>();
		for(int i=0;i<4;i++){
			int rand=(int)(Math.random()*leaders.size());
			if(rand==leaders.size())
				rand--;
			LeaderCard app=leaders.get(rand);
			result.add(app);
			leaders.remove(rand);
		}			
		return result;
	}
	
	/** reads all the personal bonus tiles used in the advanced game from the file personalbonustilesadvanced.txt
	 * 
	 * @return an arrayList containing all the personal bonus tiles
	 * @author matteo
	 */
	private ArrayList<PersonalBonusTile> getPersonalBonusTilesAdvanced(){
		FileReader gameConfig;
		String s;
		ArrayList<PersonalBonusTile> tiles= new ArrayList<PersonalBonusTile>();
		try{
			gameConfig=new FileReader(this.getClass().getResource("/it/polimi/ingsw/LM6/configuration/personalbonustilesadvanced.txt").getPath());
			BufferedReader inputFromFile=new BufferedReader(gameConfig);
			s=inputFromFile.readLine();
			for(int i=0;i<5;i++){
				s=inputFromFile.readLine();
				PersonalBonusTile app=new PersonalBonusTile(s);
				tiles.add(app);
			}
			inputFromFile.close();
			gameConfig.close();
		}catch(IOException e){
			//TODO
		}
		return tiles;
	}
	
	/** @return a list of played leaders, excluding lorenzo De Medici
	 * 
	 *
	 */
	private ArrayList<LeaderCard> getPlayedLeaders(){
		ArrayList<LeaderCard> playedLeaders=new ArrayList<LeaderCard>();
		Player[] players=this.state.getPlayers();
		int nPlayers=this.state.getNumberOfPlayers();
		for(int i=0;i<nPlayers;i++){
			ArrayList<LeaderCard> app=players[i].getLeadersPlayed();
			for(LeaderCard leader:app)
				if(!("Lorenzo De Medici").equals(leader.getName()))
					playedLeaders.add(leader);
		}
		return playedLeaders;
	}
	
	/**	@author Emilio
	 */
	public void setDraftChoice(Player p, int draftChoice){
		p.setDraftChoice(draftChoice);
	}
	
	/**	@author Emilio
	 */
	public Boolean isPersonalBonusTilesPhase(){
		return this.isPersonalBonusTilesPhase;
	}
	
	/**	@author Emilio
	 */
	public Boolean isLeaderDraftPhase(){
		return this.isLeaderDraftPhase;
	}
	
	/**	@author Emilio
	 */
	public void setStartTime(){
		this.startTime = System.currentTimeMillis();
	}
	
	/**	@author Emilio
	 */
	public void setEndTime(){
		this.endTime = System.currentTimeMillis();
	}
	
	/**Called from addPlayer when a new player joins the game, asigns a colour by default
	 * 
	 * @param p - the player that is joining the game
	 */
	private void assignColourToPlayer(Player p, int col){
		if(col==1)
			p.setColour(PlayerColour.BLUE);
		else if(col==2)
			p.setColour(PlayerColour.YELLOW);
		else if(col==3)
			p.setColour(PlayerColour.GREEN);
		else if(col==4)
			p.setColour(PlayerColour.RED);
		else if(col==5)
			p.setColour(PlayerColour.PURPLE);
		
	}
	/**Creates a string representing all the players and their colours, separated by a !.
	 * EXAMPLE: player1!r!player2!p!player3!b
	 * 
	 * @return - the string representing all the players and their colours
	 */
	private String getPlayerColourCorrespondance(){
		int n=this.state.getNumberOfPlayers();
		Player[] players=this.state.getPlayers();
		String app=new String();
		for(int i=0;i<n;i++){
			app+=players[i].getNickname()+"!"+players[i].getColourString();
			if(i!= n-1)
				app+="!";
		}
		return app;
	}
	
	/**sets the resource set offered by the player during the familiar offer phase
	 * 
	 * @param p - the player
	 * @param r - the resources
	 */
	public void setPlayerOffer(Player p, ResourceSet r){
		p.setOffer(r);
	}
	
	/**resets the resource set offered by the player during the familiar offer phase
	 * 
	 */
	private void resetPlayerOffers(){
		for(Player p:this.state.getPlayers()){
			if(p!=null){
				p.resetFamiliarOffer();
				p.setIsOfferPhaseWinner(false);
			}
		}
	}
	
	/** disables all the black familiars.
	 * Must be called in games with 5 players before the familiar offer phase starts.
	 * 
	 */
	private void disableBlackFamiliarsFivePlayersMode(){
		for(Player p:this.state.getPlayers()){
			if(p!=null)
				p.getFamiliar(0).setPlaced();
		}
	}
	
	/** enables the black familiar for the winner of the familiar offer phase
	 * Must be called in games with 5 players after the familiar offer phase.
	 * 
	 */
	private void enableBlackFamiliarWinnerFivePlayersMode(Player p){
		p.getFamiliar(0).resetPlaced();
		p.setIsOfferPhaseWinner(true);
	}
	
	public boolean isFamiliarOfferPhase(){
		return this.isFamiliarOfferPhase;
	}
	
	/** returns the player with the given nickname
	 * 
	 * @param nick - the nickname
	 * @return the Player, if found. null if the player with the given nickname has not been found has not been found
	 */
	public Player getPlayerByNickname(String nick){
		for(Player p: this.state.getPlayers())
			if(p!=null && p.getNickname().equals(nick))
				return p;
		return null;
	}
	
	/**saves the game. Called after each excommunicationPhase (except the last one)
	 * 
	 */
	public void saveGame(){
		File file=new File(getClass().getResource(this.savepath).getPath()+this.filename+".txt");
		try{
			if(!file.exists())
				file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file); 
			PrintWriter out = new PrintWriter(fos);
			out.println(this.state.getEra());
			out.println(this.state.getNumberOfPlayers());
			out.println(this.usingAdvancedRules);
			String app="";
			for(Player p: this.state.getPlayers())
				if(p!=null)
					app+=p.getNickname()+playersSeparator;
			out.println(app);
			for(Player p:this.state.getPlayers()){
				if(p!=null){
					out.println(p.getNickname());
					out.println(p.getResourceSet().toString());
					out.println(p.getPersonalTile().getNumber());
					out.println(p.getGreenCardsNumbers());
					out.println(p.getBlueCardsNumbers());
					out.println(p.getYellowCardsNumbers());
					out.println(p.getPurpleCardsNumbers());
					out.println(p.getLeaderCardsNumbers(p.getLeadersInHand()));
					out.println(p.getLeaderCardsNumbers(p.getLeadersPlayed()));	
				}
			}
			out.println(this.gameBoard.getExcommunicationCard(1).toString());
			out.println(this.gameBoard.getExcommunicationCard(2).toString());
			out.println(this.gameBoard.getExcommunicationCard(3).toString());
			out.close();
			fos.close();
		}catch(SecurityException | IOException e){
			System.err.println("Cannot save the game");
		}
	}
	
	/**	Carries out the necessary operations to resume the game.
	 * 
	 * 	@author Emilio
	 */
	public void resumeGame(){
		this.state.defineFamiliarsPerPlayer();
		this.isStarted = true;
		this.prepareNextRound();
    }
	/** loads a game from a file with the specified name.
	 * 
	 * @param filename - the name of the file containing data about the game to load
	 * @return a LorenzoIlMagnifico object that fully represents the game and the players in the game. All the Players are in disconnected and inactive status. The reloaded game is in the not started and not finshed status.
	 * @throws IOException if the reloading process fails
	 */
	public static LorenzoIlMagnifico loadGame(String filename) throws IOException{
		return new LorenzoIlMagnifico(filename);
	}
	
	/**	Carries out the necessary operations to delete the given game. It also removes the players from the users list.
	 * 
	 * 	@author Emilio
	 * 	@author Matteo
	 *  <3
	 */
	public void clean(){
		Player[] players = this.state.getPlayers();
		
		for(Player p : players)
			if(p!=null)
				NetworkData.getUsers().remove(p.getUser());
		
		try{
			File thisGame=new File(getClass().getResource(savepath).getPath()+filename+".txt");
			thisGame.delete();
			NetworkData.getSavedGames().remove(this);
			NetworkData.getGames().remove(this);
		}catch(SecurityException e){
			Logger.getLogger("MyLogger").log(Level.SEVERE, "CannotDeleteFile");
		}
	}
	public void createBoardTest(){
		this.gameBoard=new Board(this);
		this.isStarted=true;
		this.decideInitialRoundOrder();
		this.assignInitialResources();
		this.state.defineFamiliarsPerPlayer();
		this.state.initializeRoundArray();
	}
}