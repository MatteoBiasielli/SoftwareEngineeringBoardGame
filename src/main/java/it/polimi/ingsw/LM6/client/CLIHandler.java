
package it.polimi.ingsw.LM6.client;

import java.util.Scanner;

import it.polimi.ingsw.LM6.client.exceptions.OutputNotNecessaryException;

public class CLIHandler implements MessageHandler {
	
	private ClientController controller;
	private static final String SEPARATOR = "\n**************************";
	private static final String ERR_MESSAGE = "Invalid input. Try again.";
	/**
	 * this method will contain a switch structure to select what static method will be called among all one in
	 * the rest of the class, then return to the caller the result of that method. Before calling the respective method,
	 * handleMessage show to the client the eventual text message contained in the server request for action.
	 * @throws OutputNotNecessaryException if the return of this method is not necessary to send to server
	 * @param message is the message recived from the server
	 * @return the output to send to server
	 */
	@Override
	public String handleMessage(String message) throws OutputNotNecessaryException{
		String[]input = message.split(";");
		String output;
		
		if(this.controller.checkContent(input[1])){
			System.out.printf(input[1]);
			System.out.println("");
		}
		
		output = this.controller.solveMessage(input);
		return output;

	}
	
	
	public CLIHandler(){
		ClientController a = new ClientController(this);
		this.controller=a;
	}
	
	/**
	 * This method works to print the dynamic menu using the string[] s 
	 * @param s is the message, whose header is "MENU", received by the server
	 */
	protected void printMenu(String[] s){
		
		if(s[2].compareTo("1")==0)
			System.out.println("1 - Go visit a Tower;");
		if(s[3].compareTo("1")==0)
			System.out.println("2 - Perform a production;");
		if(s[4].compareTo("1")==0)
			System.out.println("3 - Perform a harvest;");
		if(s[5].compareTo("1")==0)
			System.out.println("4 - Go to the Council Palace;");
		if(s[6].compareTo("1")==0)
			System.out.println("5 - Go to the Market;");
		if(s[7].compareTo("1")==0)
			System.out.println("6 - Play a leader card;");
		if(s[8].compareTo("1")==0)
			System.out.println("7 - Discard a leader card;");
		if(s[9].compareTo("1")==0)
			System.out.println("8 - Request board update;");
		if(s[10].compareTo("1")==0)
			System.out.println("9 - Request players data update;");
		if(s[11].compareTo("1")==0)
			System.out.println("10 - Pass;");
		if(s[12].compareTo("1")==0)
			System.out.println("11 - Perform a bonus leader harvest;");
		if(s[13].compareTo("1")==0)
			System.out.println("12 - Perform a bonus leader production;");
		if(s[14].compareTo("1")==0)
			System.out.println("13 - Get your bonus leader resources;");
		if(s[15].compareTo("1")==0)
			System.out.println("14 - Increase one of your family members' value to 6.");
	}
	
	/**
	 * this method just print the setUp menu before starting a game
	 */
	protected void printSetUpMenu(){
		System.out.println("1 - Start a new game;");
		System.out.println("2 - Show the stats of a single player;");
		System.out.println("3 - Show the global scoreboard;");
		System.out.println("4 - Logout.");
	}
	
	protected String getPlayerName(){
		Scanner in = new Scanner(System.in);
		System.out.println("Insert the name of the player:");
		return new String(in.nextLine());		
	}
	
	protected void printFromIndex(String[]a, int n){
		int i;
		for(i=n;i<a.length;i++){
			String[] app=a[i].split("ù");
			System.out.println(app[0]);
		}
	}
	
	
	/**
	 * This method print the guide messages to get client's input about the leaderDiscard action.
	 * @return the player's choice
	 */
	protected String getLeaderDiscard(){
		String a;
		Scanner in = new Scanner(System.in);
		System.out.println("Choose which leader card will be discarded:");
		a = new String(in.nextLine());
		while(this.controller.checkInt(a)==false){
			System.out.println(ERR_MESSAGE);
			a=in.nextLine();
		}
		return a;
	}
	
	/**
	 * This method print the guide messages to get client's input about the leaderPlay action.
	 * @return the player's choice
	 */
	protected String getLeaderPlay(){
		String a;
		Scanner in = new Scanner(System.in);
		System.out.println("Choose which leader card will be played.");
		a = new String(in.nextLine());
		while(!this.controller.checkInt(a)){
			System.out.println(ERR_MESSAGE);
			a=in.nextLine();
		}
		return a;
	}
	
	/**
	 * This method print the guide messages to get client's family input.
	 * @return the player's choice
	 */
	protected String getFamilyInput(){
		Scanner in = new Scanner(System.in);
		String a;
		
		System.out.println("Choose which family member will be placed. [(b)BLACK/(w)WHITE/(o)ORANGE/(u)UNCOLOURED]");
		a = new String(in.nextLine());
		if("UNCOLOURED".equals(a))
			a = "UNCOLOUR";
		while(!this.controller.checkFamilyColour(a)){
			System.out.println(ERR_MESSAGE);
			a=in.nextLine();
		}
		return a;
	}
	
	/**
	 * This method print the guide messages to get client's input about the councilBonus.
	 * @return player's choice
	 */
	protected String getCouncilBonusInput(){
		String s;
		Scanner in = new Scanner(System.in);
		System.out.println("Choose a bonus:");
		System.out.println("1 - One Wood and one Stone;");
		System.out.println("2 - Two Servants;");
		System.out.println("3 - Two Coins;");
		System.out.println("4 - Two Military Points;");
		System.out.println("5 - One Faith Point.");
		s = new String(in.nextLine());
		while (!this.controller.checkCouncilBonusInput(s)){
			System.out.println(ERR_MESSAGE);
			s = in.nextLine();
		}
		return s;
	}
	
	/**
	 * This method print the guide messages to get client's input about the slaves.
	 * @return player's choice
	 */
	protected String getSlavesInput(){
		String b;
		Scanner in = new Scanner(System.in);
		
		System.out.println("Choose the number of servants you wish to use:");
		b = new String(in.nextLine());
		while(!this.controller.checkInt(b)){
			System.out.println(ERR_MESSAGE);
			b=in.nextLine();
		}
		return b;
	}
	
	/**
	 * This method print the guide messages to get client's input about a tower floor.
	 * @return player's choice
	 */
	protected String getTowerFloor(){
		String b;
		Scanner in = new Scanner(System.in);
		
		System.out.println("Choose the tower floor to visit: [0/1/2/3]");
		b = new String(in.nextLine());
		while(!this.controller.checkTowerFloor(b)){
			System.out.println(ERR_MESSAGE);
			b=in.nextLine();
		}
		return b;
	}
	
	/**
	 * This method print the guide messages to get client's input about a tower color.
	 * @return player's choice
	 */
	protected String getTowerColour(){
		String a;
		Scanner in = new Scanner(System.in);
		
		System.out.println("Choose the tower to visit: [GREEN/BLUE/YELLOW/PURPLE]");
		a = new String(in.nextLine());
		while(!this.controller.checkTowerColour(a)){
			System.out.println(ERR_MESSAGE);
			a=in.nextLine();
		}
		return a;
	}
	
	/**
	 * This method print the guide messages to get client's input about a production or harvest space.
	 * @return player's choice
	 */
	protected String getHarvestProductionSpace(){
		String a;
		Scanner in = new Scanner(System.in);
		System.out.println("Choose the action space:");
		System.out.println("1 - Regular action space;");
		System.out.println("2 - Extended action space (-3 on action value).");
		a = in.nextLine();
		while(!this.controller.checkForcedMenuInput(a)){
			System.out.println(ERR_MESSAGE);
			a=in.nextLine();
		}
		return a;
	}
	
	/**
	 * This method print the guide messages to get client's input about the choices of witch cards activate on production action.
	 * @param numerodicartegiallepossedute used to know how many time ask the client to take a choice
	 * @return player's choices in a int array
	 */
	protected String[] getProductionCardsChoices (int numerodicartegiallepossedute){
		String c;
		Scanner in = new Scanner(System.in);
		int k;
		String[] productionchoices = new String[numerodicartegiallepossedute];		
		for(k=0;k<numerodicartegiallepossedute;k++){
			System.out.println("Make your choice for card "+(k+1)+"?");
			System.out.println("0 - Do not activate the card;");
			System.out.println("1 - Activate effect 1;");
			System.out.println("2 - Activate effect 2 (if not present, effect 1 will be activated).");
			
			c = new String(in.nextLine());
			while(!this.controller.checkProductionEffectNumber(c)){
				System.out.println(ERR_MESSAGE);
				c=in.nextLine();
			}
			productionchoices[k]=c;
		}
		return productionchoices;
	}
	
	/**
	 * This method print the guide messages to get client's input about the the forced menu.
	 * @return client's choice (checked)
	 */
	protected String printReadForcedMenu(){
		String a;
		Scanner in = new Scanner(System.in);
		System.out.println("1 - Get your bonus;");
		System.out.println("2 - Renounce to your bonus (not always the best option).");
		a = new String(in.nextLine());
		while (!this.controller.checkForcedMenuInput(a)){
			System.out.println(ERR_MESSAGE);
			a = in.nextLine();
		}
		return a;
	}
	
	/**
	 * This method print the guide messages to get client's input about the discount or double cost request for the 
	 * player.
	 * @return the player's choice (checked)
	 */
	protected String printReadCostChoice(){
		String a;
		Scanner in = new Scanner(System.in);
		System.out.println("Choose which cost you wish to pay.");
		System.out.println("1 - Cost 1;");
		System.out.println("2 - Cost 2.");
		a = new String(in.nextLine());
		while(!this.controller.checkForcedMenuInput(a)){
			System.out.println(ERR_MESSAGE);
			a = in.nextLine();
		}
		return a;
	}
	
	/**
	 * This method print the guide messages to get client's input about the DoubleCouncilBonus request.
	 * @return the player's choices (checked)
	 */
	protected String getDoubleCouncilBonusInput(){
		String a;
		String b;
		Scanner in = new Scanner(System.in);
		
		System.out.println("Choose two different bonuses:");
		System.out.println("1 - One Wood and one Stone;");
		System.out.println("2 - Two Servants;");
		System.out.println("3 - Two Coins;");
		System.out.println("4 - Two Military Points;");
		System.out.println("5 - One Faith Point.");
		
		a = new String(in.nextLine());
		b = new String(in.nextLine());
		while (!this.controller.checkDifferent2(a, b) || !this.controller.checkCouncilBonusInput(a) || !this.controller.checkCouncilBonusInput(b)){
			System.out.println("Invalid choice or input. Try again.");
			a = in.nextLine();
			b = in.nextLine();
		}
		return a+";"+b;		
	}
	
	/**
	 * This method print the guide messages to get client's input about the TripleCouncilBonus request.
	 * @return the player's choices (checked)
	 */
	protected String getTripleCouncilBonusInput(){
		String a;
		String b;
		String c;
		Scanner in = new Scanner(System.in);
		
		System.out.println("Choose three different bonuses:");
		System.out.println("1 - One Wood and one Stone;");
		System.out.println("2 - Two Servants;");
		System.out.println("3 - Two Coins;");
		System.out.println("4 - Two Military Points;");
		System.out.println("5 - One Faith Point.");
		
		a = new String(in.nextLine());
		b = new String(in.nextLine());
		c = new String(in.nextLine());
		while (!this.controller.checkDifferent2(a, b)|| !this.controller.checkDifferent2(b, c) || !this.controller.checkDifferent2(a, c) || !this.controller.checkCouncilBonusInput(a) || !this.controller.checkCouncilBonusInput(b)  || !this.controller.checkCouncilBonusInput(c)){
			System.out.println("Invalid choice or input. Try again.");
			a = in.nextLine();
			b = in.nextLine();
			c = in.nextLine();
		}
		return a+";"+b+";"+c;		
	}
	
	/**
	 * This method print the guide messages to get client's input about the market action.
	 * @return player's choice (checked)
	 */
	protected String getMarketZone(){
		
		String s;
		Scanner in = new Scanner(System.in);
		
		System.out.println("Choose the market action space: [0/1/2/3]");
		
		s = new String(in.nextLine());
		while (!this.controller.checkMarketInput(s)){
			System.out.println(ERR_MESSAGE);
			s = in.nextLine();
		}
		return s;
	}
	
	/**
	 * This method print the guide messages to get client's input for tower color.
	 * @return player's choice (checked)
	 */
	protected String getCardColour(){
		
		String s;
		Scanner in = new Scanner(System.in);
		
		System.out.println("Inserisci il colore delle carte che si intende visualizzare");
		s = new String(in.nextLine());
		
		while(!this.controller.checkTowerColour(s)){
			System.out.println(ERR_MESSAGE);
			s = in.nextLine();
		}
		return s;
	}
	
	/**
	 * This method print the guide messages to get client's input about Nickname and Password.
	 * @return Nick&Pass inserted by the client
	 */
	protected String getNickAndPass(){
		
		String a;
		String b;
		Scanner in = new Scanner(System.in);
		
		System.out.println("Login requested.");
		System.out.println("Insert your username:");
		a = in.nextLine();
		System.out.println("Insert your password:");
		b = in.nextLine();
		return a+";"+b;
	}
	
	/**
	 * This method print the guide messages to get client's input about the SetUp menu.
	 * @return player's choice(checked)
	 */
	protected String getChoiceForSetUpMenu(){
		
		String a;
		Scanner in = new Scanner(System.in);
		
		a = new String(in.nextLine());
		while (!this.controller.checkSetUpMenuInput(a)){
			System.out.println(ERR_MESSAGE);
			a = in.nextLine();
		}
		return a;
	}
	
	/**
	 * This method print the guide messages to get client's input about the new game settings.
	 * @return player's choice (checked)
	 */
	protected String getChoicesForNewGame(){
		
		String a;
		Scanner in = new Scanner(System.in);
		
		System.out.println("Choose the rules:");
		System.out.println("1 - Base rules;");
		System.out.println("2 - Advanced rules.");
		a = new String (in.nextLine());
		while(!this.controller.checkForcedMenuInput(a)){
			System.out.println(ERR_MESSAGE);
			a = in.nextLine();
		}
		return a;		
	}
	
	/**
	 * This method print the guide messages to get client's input about the scoreboard request and its settings
	 * @return player's choice (checked)
	 */
	protected String getChoicesForScoreBoard(){
		
		String a;
		String b;
		Scanner in = new Scanner(System.in);
		
		System.out.println("Choose the ranking interval: [Choose (0,0) to see the whole scoreboard]");
		System.out.println("Starting from:");
		a = new String(in.nextLine());
		while (!this.controller.checkInt(a)){
			System.out.println(ERR_MESSAGE);
			a = in.nextLine();
		}
		System.out.println("Ending with:");
		b = new String(in.nextLine());
		while (!this.controller.checkInt(b)){
			System.out.println(ERR_MESSAGE);
			b = in.nextLine();
		}
		return a+";"+b;
	}
	
	/**
	 * This method print the guide messages to get client's input about the Excommunication choice
	 * @return player's choice (checked)
	 */
	protected String getExcommChoice(){
		String a;
		Scanner in = new Scanner(System.in);
		
		System.out.println("Do you wish to avoid excommunication?");
		System.out.println("1 - Yes (Pay your FPs);");
		System.out.println("2 - No.");
		a = new String(in.nextLine());
		while(!this.controller.checkForcedMenuInput(a)){
			System.out.println(ERR_MESSAGE);
			a = in.nextLine();
		}
		return a;
	}
	
	/**prints the board with further splits of the array board
	 * 
	 * @param board an array of strings, containing the whole board as it's been sent from the server, splitted for ";"
	 */
	protected void updateBoard(String[] board){
		System.out.println("GREEN TOWER");
		this.printTower(board[2].split("!"));
		System.out.println(SEPARATOR);
		System.out.println("BLUE TOWER");
		this.printTower(board[3].split("!"));
		System.out.println(SEPARATOR);
		System.out.println("YELLOW TOWER");
		this.printTower(board[4].split("!"));
		System.out.println(SEPARATOR);
		System.out.println("PURPLE TOWER");
		this.printTower(board[5].split("!"));
		System.out.println(SEPARATOR);
		System.out.println("COUNCIL");
		this.printCouncil(board[6].split("!"));
		System.out.println(SEPARATOR);
		System.out.println("MARKET");
		this.printMarket(board[7].split("!"));
		System.out.println(SEPARATOR);
		System.out.println("WORK AREA");
		this.printProductionArea(board[8].split("!"));
		System.out.println(SEPARATOR);
		System.out.println("EXCOMMUNICATIONS");
		this.printExcommunication(board[9].split("!"),1);
		this.printExcommunication(board[10].split("!"),2);
		this.printExcommunication(board[11].split("!"),3);
	}
	
	/**prints the excommunication card described by the array of strings excomm. Called by this.updateBoard
	 * 
	 * @param excomm - the excommunication array of strings
	 * @param i - the era it belongs to
	 */
	private void printExcommunication(String[] excomm, int i) {
		System.out.printf("%s%d%s%s","Excommunication era:",i," ",excomm[0]);
		if(excomm.length>2){
			for(int j=2;j<excomm.length;j++){
				System.out.printf("%s ", excomm[j]);
			}
		}
		System.out.println();
		
	}


	/**prints the tower described by the array of strings tower. Called by this.updateBoard
	 * 
	 * @param tower - the tower array of strings
	 */
	private void printTower(String[] tower){
		System.out.printf("%nFloor 0 - ");
		this.printTowerSpace(tower[0].split("ì"));
		System.out.printf("%nFloor 1 - ");
		this.printTowerSpace(tower[1].split("ì"));
		System.out.printf("%nFloor 2 - ");
		this.printTowerSpace(tower[2].split("ì"));
		System.out.printf("%nFloor 3 - ");
		this.printTowerSpace(tower[3].split("ì"));
	}
	
	/**prints the tower space described by the array of strings tower space. Called by this.updateBoard
	 * 
	 * @param space - the tower space array of strings
	 */
	private void printTowerSpace(String[] space){
		if(!space[0].equals(" ")){
			System.out.printf("%s","    ");
			this.printCard(space[0].split("ù"));
		}
		System.out.printf("%s","  |  ");
		this.printSpace(space[1].split("ù"));
	}
	
	/**prints the card described by the array of strings card. Called by this.updateBoard
	 * 
	 * @param card - the card array of strings
	 */
	private void printCard(String[] card){
		System.out.printf("%s, Cost:%s, ImmBonus:%s, PermBonus:%s", card[1], card[2], card[3],card[4]);
	}
	
	/**prints the space described by the array of strings space. Called by this.updateBoard
	 * 
	 * @param space - the space array of strings
	 */
	private void printSpace(String[] space){
		if(" ".equals(space[2]))
			System.out.printf("%s, ImmBonus:%s, PlayerHere:%s", space[0], space[1], space[2]);
		else
			System.out.printf("%s, ImmBonus:%s, PlayerHere:%s%s", space[0], space[1], space[2],space[3]);
	}
	
	
	/**prints the council described by the array of strings council. Called by this.updateBoard
	 * 
	 * @param council - the council array of strings
	 */
	private void printCouncil(String[] council){
		System.out.printf("%s, ImmBonus:%s" ,council[0],council[1]);
		if(council.length>2)
			System.out.printf(", PlayersHere:%s",council[2]);
	}
	
	/**prints the market described by the array of strings market. Called by this.updateBoard
	 * 
	 * @param market - the market
	 */
	private void printMarket(String[] market){
		System.out.println(market[0]);
		String[] app;
		for(int i=1;i<market.length;i++){
			app=market[i].split("ù");
			System.out.println((i-1)+"- "+app[0]+ "   "+ app[1]);
		}
	}
	
	
	/**prints the work area described by the array of strings area. Called by this.updateBoard
	 * 
	 * @param area - the work area
	 */
	private void printProductionArea(String[] area){
		System.out.println(area[0]);
		System.out.println("PRODUCTION");
		System.out.println(area[1] + "  |  " + area[2]);
		System.out.println("HARVEST");
		System.out.println(area[3] + "  |  " + area[4]);
		
	}

	
	/**prints out all the data about the players in the game by doing further splits of the array a
	 * 
	 * @param a - an array of STrings, as it was sent by the server, splitted for ";"
	 */
	protected void updatePlayers(String[] a) {
		if(a.length>2){
			for(int i=2;i<a.length;i++)
				this.printPlayer(a[i].split("!"));
		}
		
	}

	/** prints out the data of the player. SUpports this.updatePlayers
	 * 
	 * @param player - the players array of strings
	 */
	private void printPlayer(String[] player) {
		String[] app=player[8].split("ù");
		System.out.println(player[0]+ "   RESOURCES: "+player[1]+"     "+app[0]);
		System.out.println("GREEN CARDS");
		if(!" ".equals(player[2])){
			this.printCards(player[2].split("-"));
		}
		System.out.println("YELLOW CARDS");
		if(!" ".equals(player[3])){
			this.printCards(player[3].split("-"));
		}
		System.out.println("BLUE CARDS");
		if(!" ".equals(player[4])){
			this.printCards(player[4].split("-"));
		}
		System.out.println("PURPLE CARDS");
		if(!" ".equals(player[5])){
			this.printCards(player[5].split("-"));
		}
		System.out.println("LEADERS IN HAND");
		if(!" ".equals(player[6])){
			this.printLeaders(player[6].split("-"));
		}
		System.out.println("LEADERS PLAYED");
		if(!" ".equals(player[7])){
			this.printLeaders(player[7].split("-"));
		}
		
		
	}

	
	/** prints out the data of the leaders. SUpports this.updatePlayers
	 * 
	 * @param split - the leaders array of strings
	 */
	private void printLeaders(String[] split) {
		for(int i=0;i<split.length;i++){
			String[] app=split[i].split("ù");
			System.out.printf("%s","    ");
			System.out.println(app[0]);
		}
		
	}

	/** prints out the data of the cards. SUpports this.updatePlayers
	 * 
	 * @param cards - the cards array of strings
	 */
	private void printCards(String[] cards) {
		for(int i=0;i<cards.length;i++){
			if(!cards[i].equals("") && !cards[i].equals(" ")){
				System.out.printf("%s","    ");
				this.printCard(cards[i].split("ù"));
				System.out.println();
			}
		}
		
	}
	
	protected String getDraftInput(int n){
		
		String tmp;
		Scanner in = new Scanner(System.in);
		System.out.println("Choose a card:");
		tmp = in.nextLine();
		while(!this.controller.checkFromZeroTo(tmp, n)){
			System.out.println(ERR_MESSAGE);
			tmp = in.nextLine();
		}
		return tmp;
	}
	
	protected String getNumberOfPlayers(){
		
		Scanner in = new Scanner(System.in);
		String tmp;
		System.out.println("Choose the maximum number of players: [Min = 2; Max = 5]");
		tmp = new String(in.nextLine());
		while(!this.controller.checkNumberOfPlayers(tmp)){
			System.out.println(ERR_MESSAGE);
			tmp = in.nextLine();
		}
		return tmp;
	}
	
	protected String getSlavesOffer(){
		
		Scanner in = new Scanner(System.in);
		String tmp;
		System.out.println("How many Servants do you wish to offer?");
		tmp = new String(in.nextLine());
		while(!this.controller.checkInt(tmp)){
			System.out.println(ERR_MESSAGE);
			tmp = in.nextLine();
		}
		return tmp;
	}
	
	protected String getGoldOffer(){
			
			Scanner in = new Scanner(System.in);
			String tmp;
			System.out.println("How many Coins do you wish to offer?");
			tmp = new String(in.nextLine());
			while(!this.controller.checkInt(tmp)){
				System.out.println(ERR_MESSAGE);
				tmp = in.nextLine();
			}
			return tmp;
		}

	protected String getStoneOffer(){
			
			Scanner in = new Scanner(System.in);
			String tmp;
			System.out.println("How many Stones do you wish to offer?");
			tmp = new String(in.nextLine());
			while(!this.controller.checkInt(tmp)){
				System.out.println(ERR_MESSAGE);
				tmp = in.nextLine();
			}
			return tmp;
		}
	
	protected String getWoodOffer(){
		
		Scanner in = new Scanner(System.in);
		String tmp;
		System.out.println("How much Wood do you wish to offer?");
		tmp = new String(in.nextLine());
		while(!this.controller.checkInt(tmp)){
			System.out.println(ERR_MESSAGE);
			tmp = in.nextLine();
		}
		return tmp;
	}
		
	protected boolean endGameInput(){
		Scanner in = new Scanner(System.in);
		System.out.println("The game ended. Press enter to continue...");
		in.nextLine();
		return true;
	}
	
	protected void printFinalScoreBoard(String[] a){
		System.out.println("FINAL LOCAL SCOREBOARD:");
		System.out.printf(a[2]);
		System.out.println("FINAL GLOBAL SCOREBOARD:");
		System.out.printf(a[3]);
	}
	
}

