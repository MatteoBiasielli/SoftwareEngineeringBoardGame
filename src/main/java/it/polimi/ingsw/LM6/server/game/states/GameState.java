package it.polimi.ingsw.LM6.server.game.states;


import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.game.exceptions.FullGameException;


public class GameState {
	private int familiarsPerPlayer;
	private int maxNumberOfPlayers;
	private int numberOfPlayers;
	private int currentRound;
	private int currentEra;
	private int currentPlayerIndex;
	private int totalPlayerTurnsPerRound;
	private boolean isExcommunicationPhase;
	private boolean[] excommunicationChoices;
	
	private Player[] playersArray;
	private Player[] roundArray;
	
	/**used to create a new game
	 * 
	 * @param maxNumber - the max number of players
	 */
	public GameState(int maxNumber){
	    this.maxNumberOfPlayers=maxNumber;
		this.playersArray=new Player[maxNumberOfPlayers];
		this.numberOfPlayers=0;
		this.currentRound=1;
		this.currentEra=1;
		this.currentPlayerIndex=0;
		this.familiarsPerPlayer=4;
		this.isExcommunicationPhase = false;
	}
	/**used to reload a previously saved game
	 * 
	 * @param nPlayers - the number of players
	 * @param era - the era from which the game has to restart
	 */
	public GameState(int nPlayers, int era){
	    this.maxNumberOfPlayers=nPlayers;
		this.playersArray=new Player[maxNumberOfPlayers];
		this.numberOfPlayers=nPlayers;
		this.currentRound=2*era-2;
		this.currentEra=era;
		this.currentPlayerIndex=0;
		this.familiarsPerPlayer=4;
		this.isExcommunicationPhase = false;
		
	}
	
	/*At the start of every round, creates roundArray from playersArray (already reordered basing on the council)
	 */
	public void createRoundArray() {
		this.roundArray=new Player[totalPlayerTurnsPerRound];
		if(this.numberOfPlayers<=4)
			for(int i=0; i<totalPlayerTurnsPerRound;i++)
				this.roundArray[i]=playersArray[i%numberOfPlayers];
		else{
			for(int i=0; i<totalPlayerTurnsPerRound-1;i++)
				this.roundArray[i]=playersArray[i%numberOfPlayers];
			for(Player p:this.playersArray)
				if(p.isOfferPhaseWinner()){
					this.roundArray[15]=p;
					break;
				}
		}

		this.applyTurnOrderModifiers();
	}

	public Player currentTurnPlayer() {
		return this.roundArray[currentPlayerIndex];
	}
	
	
	public void addPlayer(Player p) throws FullGameException{
		if(this.numberOfPlayers<this.maxNumberOfPlayers){
			this.playersArray[this.numberOfPlayers]=p;
			this.numberOfPlayers++;
		}
		else
			throw new FullGameException("The selected game is already full");
	}
	
	public boolean hasReachedTheMaximumNumberOfPlayers() {
		return this.numberOfPlayers==this.maxNumberOfPlayers;
	}
	public boolean hasReachedTheMinimumNumberOfPlayers() {
		return this.numberOfPlayers==2;
	}
	public void setFamiliarsValues(int b, int o, int w, int u){
		for(Player p:playersArray){
			if(p!=null){
				p.setFamiliarsValues(b,o,w,u);
			}
		}
	}
	
	
	/*Creates the turn Array of the right length when the game starts
	 * 
	 */
	public void initializeRoundArray(){
		this.roundArray=new Player[totalPlayerTurnsPerRound];
	}
	
	/******GETTERS******/
	public int getNumberOfFamiliarsPerPlayer() {
		return familiarsPerPlayer;
	}

	public int getNumberOfPlayers() {
		return this.numberOfPlayers;
	}
	public Player[] getPlayers(){
		return playersArray;
	}

	public Player[] getRoundArray() {
		return this.roundArray;
	}
	
	public int getCurrentPlayerIndex(){
		return this.currentPlayerIndex;
	}
	
	public int getRound(){
		return this.currentRound;
	}
	
	public int getEra(){
		return this.currentEra;
	}
	
	public int getTotalPlayersTurnPerRound(){
		return this.totalPlayerTurnsPerRound;
	}
	
	public boolean isExcomunicationPhase(){
		return this.isExcommunicationPhase;
	}
	
	/******UPDATERS******/
	public void setExcomunicationPhase(){
		this.isExcommunicationPhase = true;
	}
	
	public void resetExcomunicationPhase(){
		this.isExcommunicationPhase = false;
	}
	
	public void resetCurrentPlayerIndex(){
		this.currentPlayerIndex = 0;
	}
	
	public void incrementCurrentRound(){
		this.currentRound++;
	}
	
	public void incrementCurrentEra(){
		this.currentEra++;
	}
	
	public void incrementCurrentPlayerIndex(){
		this.currentPlayerIndex++;
	}
	
	public void createExcommunicationChoices(){
		this.excommunicationChoices = new boolean[numberOfPlayers];
		
		for(int i = 0; i < numberOfPlayers; i++)
			excommunicationChoices[i] = false;
	}
	
	public void defineFamiliarsPerPlayer(){
		if(this.numberOfPlayers <=4){
			this.totalPlayerTurnsPerRound = familiarsPerPlayer*numberOfPlayers;
		}
		else{
			this.familiarsPerPlayer = 3;
			this.totalPlayerTurnsPerRound = familiarsPerPlayer*numberOfPlayers + 1; //due to extra familiar
		}
	}
	
	/*Modifies the turn order if any player has the skipFirstActionMalus malus.
	 */
	public void applyTurnOrderModifiers(){
		for(Player p:getPlayers()){
			if(p!=null)
				p.getPermanentBonusMalus().applyModifier(p, getRoundArray());
		}
	}
	
	public int getNumberOfConnectedUsers(){
		int count = 0;
		
		for(Player p: getPlayers())
			if(p!=null && p.isConnected())
				count++;
		
		return count;
	}
	
	public boolean[] getExcommChoices(){
		return this.excommunicationChoices;
	}
}
