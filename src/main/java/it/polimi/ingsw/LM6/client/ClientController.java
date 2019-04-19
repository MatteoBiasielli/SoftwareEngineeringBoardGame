package it.polimi.ingsw.LM6.client;

import java.util.Scanner;

import it.polimi.ingsw.LM6.client.exceptions.OutputNotNecessaryException;
import it.polimi.ingsw.LM6.client.exceptions.TerminatedException;

public class ClientController {
	
		private CLIHandler view;
		
		public ClientController(CLIHandler a){
			this.view=a;
		}
		
		/**
		 * It checks if the two parameters are different.
		 * @return true if the two parameters are different, false otherwise
		 */
		protected Boolean checkDifferent2(String a, String b){
			if(a.equals(b))
				return false;
			return true;
		}
		
		/**
		 * Checks if the number is among 1,2,3 or 4.
		 * @param s is the number
		 * @return a boolean as a result of the control
		 */
		protected Boolean checkSetUpMenuInput(String s){
			if( "1".equals(s) || "2".equals(s) || "3".equals(s) || "4".equals(s))
				return true;
			return false;
		}
		
		/**All forced menu are always composed by only two choices, so this method just check this.
		 * 
		 * @param s is the player choice about the forced menu
		 * @return true if the parameter is allowed between the parameter of the forced menu
		 */
		protected Boolean checkForcedMenuInput(String s){
			if (("1").equals(s) || ("2").equals(s))
				return true;
			return false;
		}
		
		/**
		 * Checks if the number is among 2,3,4 or 5.
		 * @param s is the number
		 * @return a boolean as a result of the control
		 */
		protected Boolean checkNumberOfPlayers(String s){
			if(("2").equals(s) || ("3").equals(s) || ("4").equals(s) || ("5").equals(s))
				return true;
			return false;
		}
		
		
		/** this method only checks if the string can be printed
		 * @param is the string
		 * @return true if the String is not empty, false otherwise
		 */
		protected Boolean checkContent(String s){
			
			if ((" ").equals(s) || ("").equals(s))
				return false;
			return true;
		}
		
		/**
		 * This method checks if the player's input is one of the possible choices about council bonus input
		 * @param s is the choice
		 * @return true if the choice is allowed, false otherwise
		 */
		protected Boolean checkCouncilBonusInput(String s){
			
			if (("1").equals(s) || ("2").equals(s) || ("3").equals(s))
				return true;
			if(("4").equals(s) || ("5").equals(s))
				return true;
			return false;
		}
		
		/**
		 * This method checks if the parameter is a number
		 * @param s is the number
		 * @return true the parameter is a number, false otherwise
		 */
		protected Boolean checkInt(String s){
			try{
				Integer.parseInt(s);
			}
			catch (NumberFormatException e){
				return false;
			}
			return true;
		}
		
		/**
		 * Checks if the input is in the integer interval between 0 and the parameter n.
		 * @param num is the integer which will be checked
		 * @param n
		 * @return a boolean if the number is allowed, false otherwise.
		 */
		protected Boolean checkFromZeroTo (String num, int n){
			try{
				int i = Integer.parseInt(num);
				if (i>=0 && i<=n)
					return true;
				return false;
			} catch (NumberFormatException e){
				return false;
			}
			
		}
		
		/**
		 * This method checks if the parameter is a possible family's color
		 * @param s is the chosen color
		 * @return true if the color is allowed, false otherwise
		 */
		protected Boolean checkFamilyColour (String s){
			
			if(("BLACK").equals(s) || ("ORANGE").equals(s) || ("WHITE").equals(s)|| ("UNCOLOUR").equals(s))
				return true;
			return false;
		}
		
		/**
		 * This method checks if the parameter is a possible tower's color
		 * @param s is the chosen color
		 * @return true if the color is allowed, false otherwise
		 */
		protected Boolean checkTowerColour (String s){
			
			if(("GREEN").equals(s) || ("YELLOW").equals(s) || ("PURPLE").equals(s)|| ("BLUE").equals(s))
				return true;
			return false;
		}
		
		/**
		 * This method checks if the parameter is a possible choice number of the market
		 * @param s is the chosen number
		 * @return true if the number is allowed, false otherwise
		 */
		protected Boolean checkMarketInput(String s){
			
			if(("0").equals(s) || ("1").equals(s) || ("2").equals(s) || ("3").equals(s))
				return true;
			return false;
		}
		
		/**
		 * this method checks if the player's chosen number is one among the possible choices in menu, look if the chosen number is 
		 * among the possiblechoices's ones 
		 * @param s is the chosen number
		 * @param possiblechoices is the array witch contains all choices allowed by the menu
		 * @return true if the chosen number is allowed, false otherwise
		 */
		protected Boolean checkMenuChosenNumber (String s, String[] possiblechoices){
			
			if (!this.checkInt(s))
				return false;
			//the positions in the string of the player choices is shifted ahead 3 cells. so if the choices plus 3 exceded
			//array's dimension the choice isn't allowed.
			if((Integer.parseInt(s)+3)>possiblechoices.length)
				return false;
			//this control only checks if the action corresponding to the chosen number is allowed by the server (by server's flag)
			if(possiblechoices[Integer.parseInt(s)+1].compareTo("1")==0)
				return true;
			return false;
		}
		
		/**
		 * This method checks if the parameter is a possible tower floor (0,1,2 or 3)
		 * @param s is the chosen number
		 * @return true if the number is allowed, false otherwise
		 */
		protected Boolean checkTowerFloor(String s){
			
			if (!this.checkInt(s))
				return false;
			
			if(("0").equals(s) || ("1").equals(s) || ("2").equals(s) || ("3").equals(s))
				return true;
			return false;
		}
		
		/**
		 * allowed choice for this case are only 0(i want not activate this production card), 1(i want activate the first
		 * production effect of this card) and 2( i want activate the second production effect of this card, if the card
		 * //has only one effect, that effect will be activate)
		 * @param s is the chosen number
		 * @return true if the chosen number is allowed, false otherwise
		 */
		protected Boolean checkProductionEffectNumber(String s){
			
			if (!this.checkInt(s))
				return false;
			
			if(("0").equals(s) || ("1").equals(s) || ("2").equals(s))
				return true;
			return false;
		}
		
		
		/**
		 * This method encapsulates the towerAction String message
		 * @return "TOWERACTION"+all player's choices about this action
		 */
		protected String encapsulateTowerAction (){
			String a;
			String b;
			String c;
			String d;
			
			a = this.view.getTowerColour();
			
			b = this.view.getTowerFloor();
			
			c = this.view.getFamilyInput();
			
			d = this.view.getSlavesInput();
			
			return "TOWERACTION"+";"+a+";"+b+";"+c+";"+d;			
		}
		
		/**
		 * This method encapsulates the Excommunication String message
		 * @return "EXCOMMUNICATIONCHOICE"+ player's choice
		 */
		protected String encapsulateExcommunicationChoice(){
			String a = this.view.getExcommChoice();
			return "EXCOMMUNICATIONCHOICE;"+a;
		} 
		
		
		/**
		 * This method encapsulates the marketAction String message
		 * @return "MARKETACTION"+all player's choices about this action
		 */
		protected String encapsulateMarketAction(){
			String a;
			String b;
			String c;
			
			a = this.view.getMarketZone();
			b = this.view.getFamilyInput();
			c = this.view.getSlavesInput();
			
			return "MARKETACTION"+";"+a+";"+b+";"+c;
		}
		
		/**
		 * This method encapsulates the leaderPlayAction String message
		 * @return "LEADERPLAY"+the player's choice about this action
		 */
		protected String encapsulatePlayLeaderCard(){
			String a;			

			a = this.view.getLeaderPlay();
			return "LEADERPLAY"+";"+a;
		}
		
		
		/**
		 * This method encapsulates the harvestAction String message
		 * @return "HARVESTACTION"+all player's choices abotu this action
		 */
		protected String encapsulateFarmingAction(){
			String a;
			String b;
			String c;
			
			a = this.view.getFamilyInput();
			
			b = this.view.getSlavesInput();
			
			c = this.view.getHarvestProductionSpace();
			
			return "HARVESTACTION"+";"+a+";"+b+";"+c;
		}
		
		
		/**
		 * This method encapsulates the productionAction String message.
		 * Using the parameter "numcartegialle" ask the player to insert the activation choice about all cards owned.
		 * Then, completes the String with zero's if the number of yellow cards owned is less than six.
		 * @param numcartegialle is the number of yellow card owned by the player
		 * @return "PRODUCTIONACTION"+ all player's choices about this action
		 */
		protected String encapsulateProductionAction(String numcartegialle){
			int n = Integer.parseInt(numcartegialle);
			String a;
			String b;
			String c;

			a = this.view.getFamilyInput();						
			b = this.view.getSlavesInput();
			c = this.view.getHarvestProductionSpace();
			//get the player's production choices 
			String[] productionchoices = this.view.getProductionCardsChoices(n);
	
			
			String tmp = new String("PRODUCTIONACTION"+";"+a+";"+b+";"+c+";");
			int i;
			int k;
			for (i=0;i<productionchoices.length;i++)
				if(i!=6)
					tmp=tmp+productionchoices[i]+";";
				else
					tmp=tmp+productionchoices[i];
			for(k=0;k<(6-i);k++)
				tmp=tmp+"0;";
			return tmp;
		}
		
		/**
		 * This method encapsulates the player's renounce referring to a bonus action String message
		 * @param bonustorenunce is the action witch the player want to renounce for
		 * @return "BONUSRENOUNCE"+the parameter
		 */
		protected String encapsulateRenounciation(String bonustorenunce){
			return "BONUSRENOUNCE"+";"+bonustorenunce;
		}
		
		/**
		 * This method encapsulates the player's request String message to see all players owned resources and cards
		 * @return "SHOWPLAYERSREQUEST"
		 */
		protected String encapsulatePlayersRequest(){			
			return "SHOWPLAYERSREQUEST";
		}
		
		/**
		 * This method encapsulates the player's council bonus request String message. Using the parameter a this method
		 * can discerns if the return  must be about single, double of triple request about councilbonuses.
		 * @param a is the message received by the client from the server
		 * @return single, double or triple String message for council bonuses requirement
		 */
		protected String encapsulateCouncilBonusRequirement(String[] a){
			
			String choice1;
			String choice2;
			String output;
			
			if(a[2].compareTo("1")==0){
				choice1 = this.view.printReadForcedMenu();
				if(choice1.compareTo("1")==0){
	
					choice2 = this.view.getCouncilBonusInput();
					return "COUNCILREQUIREMENT"+";"+choice2;
				
				}
				else {
					output = new String();
					output = this.encapsulateRenounciation("COUNCILREQUIREMENT");
				}
				return output;
			}
			
			else if(a[2].compareTo("2")==0){
				
				choice1 = this.view.printReadForcedMenu();
				if(choice1.compareTo("1")==0){
	
					choice2 = this.view.getDoubleCouncilBonusInput();
					return "DOUBLECOUNCILREQUIREMENT"+";"+choice2;
				
				}
				else {
					output = this.encapsulateRenounciation("DOUBLECOUNCILREQUIREMENT");
				}
				return output;
			}
			
			else if(a[2].compareTo("3")==0){
				
				choice1 = this.view.printReadForcedMenu();
				if(choice1.compareTo("1")==0){
				
					choice2 = this.view.getTripleCouncilBonusInput();
					return "TRIPLECOUNCILREQUIREMENT"+";"+choice2;
			
				}				
				
			}
			output = this.encapsulateRenounciation("TRIPLECOUNCILREQUIREMENT");
			return output;
		}
			
		
		/**
		 * This method encapsulates the leaderDiscard String message
		 * @return "LEADERDISCARD"+player's choice
		 */
		protected String encapsulateDiscardLeaderCard(){
			String a;
			
			a = this.view.getLeaderDiscard();
			return "LEADERDISCARD"+";"+a;
		}
		
		
		/**
		 * This method encapsulates the councilAction String message
		 * @return "COUNCILACTION"+all player's choices about this action
		 */
		protected String encapsulateCouncilAction(){
			String a;
			String b;

			a = this.view.getFamilyInput();
			b = this.view.getSlavesInput();

			return "COUNCILACTION"+";"+a+";"+b;
		}
		
		
		/**
		 * This method encapsulates the player's request for see the game's Board String message
		 * @return "SHOWTABLEREQUEST"
		 */
		protected String encapsulateTableRequest(){
			return "SHOWTABLEREQUEST";
		}
		
		
		/**
		 * This method encapsulates the player's PASS action String message
		 * @return "PASS"
		 */
		protected String encapsulatePassTurn(){
			return "PASS";
		}
		
		
		/**
		 * This method encapsulate BonusTowerAction String message. First of all ask the client if he would or not refuse this bonus.
		 *  It uses the parameter a to see if the bonus action
		 * earned is on a single color or on all possible color. In the second case this method also ask the player
		 * to insert the tower color, otherwise the color will be automatically insert by the system.
		 * @param a is the message received by the client from the server
		 * @return "BONUSTOWERACTION"+ all player's choices about this action
		 */
		protected String encapsulateBonusTowerAction(String[] a){
			
			String choice1;
			String choice2;
			String choice3;
			String output;
			
			choice3 = this.view.printReadForcedMenu();
			
			
			//the player can accept or refuse the bonus action, case "1" acceptance
			if (choice3.compareTo("1")==0){
				if(a[2].compareTo("EVERY")!=0){
					choice1 = this.view.getSlavesInput();			
					choice2 = this.view.getTowerFloor();		
					return "BONUSTOWERACTION"+";"+choice1+";"+choice2+";"+a[2];
				}
				
				choice1 = this.view.getSlavesInput();			
				choice2 = this.view.getTowerFloor();
				choice3 = this.view.getTowerColour();
				return "BONUSTOWERACTION"+";"+choice1+";"+choice2+";"+choice3;
			}
			
			output = this.encapsulateRenounciation("BONUSTOWERACTION");
			return output;
			
		}
		
		
		/**
		 * This method encapsulates the bonusProductionAction String message. First of all it ask the player if he would
		 * or not to refuse this bonus. Then uses the parameter a to see the number of yellow cards owned by the player
		 * and ask him for each card if he would or not activate that one in the production action.
		 * @param a is the message received by the client from the server
		 * @return "BONUSPRODUCTIONACTION"+ all player's choices about this action
		 */
		protected String encapsulateBonusProductionAction(String[] a){
			
			String choice1; 
			String choice2;
			String output;
			String[] yellowCardChoices;
			choice1 = this.view.printReadForcedMenu();
			
			if(choice1.compareTo("1")==0){
				choice2 = this.view.getSlavesInput();
				yellowCardChoices = this.view.getProductionCardsChoices(Integer.parseInt(a[a.length-1]));
				output = new String("BONUSPRODUCTIONACTION"+";"+choice2+";");
				int i;
				int k;
				for (i=0;i<yellowCardChoices.length;i++)
						output = output+yellowCardChoices[i]+";";
				for (k=0;k<(6-i);k++)
						output = output+"0;";
				return output;
				
			}
			else {
				output = this.encapsulateRenounciation("BONUSPRODUCTIONACTION");
			}
			return output;
			
			
		}
		
		/**
		 * Used by the client when he/she want to get his leader bonus about the familiar.
		 * @return The message just encapsulated and ready to be sent to server.
		 */
		protected String encapsulateLeaderFamilyBonus(){
			String tmp = this.view.getFamilyInput();
			return "LEADERFAMILYBONUS;"+tmp;
		}
		
		
		/**
		 * This method encapsulates the bonusHarvestAction String message. First of all it ask the client if he would
		 * or not to refuse this bonuses.
		 * @return "BONUSHARVESTACTION"+ all player's choices about this action
		 */
		protected String encapsulateBonusFarmingAction(){
			
			String choice1;
			String choice2;
			String output;
			choice1 = this.view.printReadForcedMenu();
			
			if (choice1.compareTo("1")==0){
				choice2 = this.view.getSlavesInput();
				return "BONUSHARVESTACTION"+";"+choice2;
			}
			else {
				output = this.encapsulateRenounciation("BONUSHARVESTACTION");
			}
			return output;
		}
		
		
		/**
		 * This method encapsulates the player's cost choice referring a card when is necessary.
		 * @return "TOWERACTIONWITHCHOICE"+the chosen cost.
		 */
		protected String encapsulateCostChoice(){
			String choice1;
			
			choice1 = this.view.printReadCostChoice();
			return "TOWERACTIONWITHCHOICE"+";"+choice1;
		}
		
		/**
		 * This method encapsulates the client's request for starting a new game.
		 * @return "STARTNEWGAME"+the type of rules chosen by the client.
		 */
		private String encapsulateNewGameRequest(){
			String s;
			String tmp;
			
			s = this.view.getChoicesForNewGame();

			if(s.compareTo("1")==0)
				tmp = new String("STARTNEWGAME"+";"+"0;");
			else
				tmp = new String("STARTNEWGAME"+";"+"1;");
			
			s = this.view.getNumberOfPlayers();
			
			return tmp+s;
			
		}
		
		/**
		 * This method encapsulates client's request to see the general scoreboard of all players.
		 * @return "SCOREBOARD"+ choices about the starting row and the ending row
		 */
		private String encapsulateScoreBoardRequest(){
			String s;
			
			s = this.view.getChoicesForScoreBoard();
			return "SHOWSCOREBOARD"+";"+s;
		}
		
		private String getDraftInput(String[] a){
			String tmp;
			this.view.printFromIndex(a, 2);
			tmp = this.view.getDraftInput(a.length-3);
			return tmp;
		}
		
		
		/**
		 * This method encapsulates the client's Login request
		 * @return "LOGINREQUEST"+ username and password
		 */
		private String encapsulateLoginMessage(){
			String choice1;
			
			choice1 = this.view.getNickAndPass();
			return "LOGINREQUEST"+";"+choice1;
		}
		/**
		 * It takes the player's choices about leader draft.
		 * @param a is the message received by the server already splitted, it's useful to check if the player's choices
		 * is among the allowed ones
		 * @return the encapsulated message.
		 */
		private String encapsulateLeaderDraftChoice(String[] a){
			
			String tmp = this.getDraftInput(a);
			return "LEADERDRAFTCHOICE;"+tmp;
		}
		
		/**
		 * Used to print and get client input about the new 5th player feature "familiar offer"
		 * @return the string just encapsulated, ready to be sent to server.
		 */
		private String encapsulateFamiliarOffer(){
			
			String a;
			String b;
			String c;
			String d;
			a = this.view.getWoodOffer();
			b = this.view.getStoneOffer();
			c = this.view.getGoldOffer();
			d = this.view.getSlavesOffer();
			return "FAMILIAROFFER;"+a+";"+b+";"+c+";"+d;
		}
		
		/**
		 * It takes the player's choices about tiles draft.
		 * @param a is the message received by the server already splitted, it's useful to check if the player's choices
		 * is among the allowed ones
		 * @return the encapsulated message.
		 */
		private String encapsulateTilesDraftChoice(String[] a){
			
			String tmp = this.getDraftInput(a);
			return "TILESDRAFTCHOICE;"+tmp;
		}
		
		/**
		 * It takes the player's choice after playing the leader card "Lorenzo deMedici".
		 * @param a is the message received by the server already splitted, it's useful to check if the player's choices
		 * is among the allowed ones
		 * @return the encapsulated message.
		 */
		private String encapsulateLorenzoIlMagnificoChoice(String[] a){
			
			this.view.printFromIndex(a, 2);
			String tmp = this.view.getDraftInput(a.length-3);
			return "LORENZOILMAGNIFICOCHOICE;"+tmp;
		}
		
		private String encapsulateSinglePlayerScoreBoard(){
			String tmp = this.view.getPlayerName();
			return "SHOWRECORD;"+tmp+"; ";
		}
		
		private String encapsulateLogoutRequest(){
			return "LOGOUTREQUEST; ; ";
		}
		
		/**
		 * this method works to return encapsulate message depending on player's choice. (the choice can be for start a new game or
		 * to request a general scoreboard)
		 * @return start new game request or scoreboard request.
		 */
		protected String setUpChoiceHandler() {
			
			String s;
			
			this.view.printSetUpMenu();
			//getting player's choice 
			s = this.view.getChoiceForSetUpMenu();
			if (s.compareTo("1")==0){
				//getting player's choice about the rules then the encapsulation and the return of the String.
				return this.encapsulateNewGameRequest();
			}
			
			else if("2".equals(s)){
				return this.encapsulateSinglePlayerScoreBoard();
			}
			
			else if("3".equals(s)){
				return this.encapsulateScoreBoardRequest();
			}
			
			return this.encapsulateLogoutRequest();
		}
		
		/**
		 * //this method handles the player's choice among menu's ones, then returns to CLIHandler the encapsulate message.
		 * it uses the message received by the client to print the dinamic menu and for know the number of yellow cards
		 * owned by the player.
		 * @param s is the message received by the client from the server
		 * @return the corresponding action String message
		 */
		protected String choiceHandler(String[] s){
			
			
			Scanner in = new Scanner(System.in);
			String choice1;
			
			
			//first of all the menu is printed looking at the flags contained in the string s.
			this.view.printMenu(s);
				
				//subsequently checks if the choice is allowed by menu.
				choice1=in.nextLine();			
				while(!this.checkMenuChosenNumber(choice1, s)){
					System.out.println("il parametro inserito non corrisponde a nessuna voce nel menu, inserirlo nuovamente");
					choice1=in.nextLine();
				}

				//the number of yellow owned cards of the player is contained in the last cell of the array a
				//chiamata all'handler della scelta fatta, esso si occuperà di restituire il messaggio gia incapsulato e pronto all invio.
				
			switch (choice1){
					
					//this case correspond at Tower action, so it get the necessary parameters to identify the action
					case "1":{	
						return this.encapsulateTowerAction();
					}
					//Production Action
					case "2":{	
						return this.encapsulateProductionAction(s[s.length-1]);
					}
					//Harvest action
					case "3": {
						return this.encapsulateFarmingAction();
					}
					//Council action
					case "4": {					
						return this.encapsulateCouncilAction();
					}
					//market action
					case "5": {
						return this.encapsulateMarketAction();
					}
					//Leader play action
					case "6": {	
						return this.encapsulatePlayLeaderCard();
					}
					//Leader discard action
					case "7": {	
						return this.encapsulateDiscardLeaderCard();
					}
					//Print table request
					case "8": {
						return this.encapsulateTableRequest();
					}
					//Request for show my card
					case "9": {
						return this.encapsulatePlayersRequest();
					}
					case "11":{	
						return "LEADERHARVEST";
					}
					case "12":{
						return "LEADERPRODUCTION";
					}
					case "13":{
						return "LEADERRESOURCES";
					}
					case "14":{
						return this.encapsulateLeaderFamilyBonus();
					}
					default :{
						
					}
				}
			
			return this.encapsulatePassTurn();
			}
		
		
		/**
		 * This method works for handle all input messages of the client looking at the header. The parameter "a" is the
		 * received client string, already splitted.
		 * @param a is the message received by client
		 * @return the response for the server
		 * @throws OutputNotNecessaryException if the string returned is not necessary to send it to server
		 */
		protected String solveMessage(String[] a) throws OutputNotNecessaryException{
						

			switch (a[0]) {
		
				case "MENU": {		
					return this.choiceHandler(a);
				}
		
				//this case is used to print the others players game update.
				case "INFO": {
						break;
				}
				//this case is used to make player insert the parameters for a bonus tower action 
				case "BONUSACTIONREQUEST": {
					return this.encapsulateBonusTowerAction(a);
				}
				case "BONUSPRODUCTIONREQUEST": {
					return this.encapsulateBonusProductionAction(a);	
				}
				case "BONUSHARVESTREQUEST": {	
					return this.encapsulateBonusFarmingAction();
				}
				case "BONUSCOUNCILREQUEST": {					
					return this.encapsulateCouncilBonusRequirement(a);
				}
				case "COSTCHOICEREQUEST": {	
					return this.encapsulateCostChoice();
				}
				case "INVALIDLOGIN" : {	
					return this.encapsulateLoginMessage();
				}
				case "SETUPMENU": {
					return this.setUpChoiceHandler();
				}
				case "EXCOMMCHOICEREQUEST": {
					return this.encapsulateExcommunicationChoice();
				}
				case "BOARD":{
					this.view.updateBoard(a);
					break;
				}
				
				case "PLAYERS":{
					this.view.updatePlayers(a);
					break;
				}
				case "UPDATEBOARD":{
					break;
				}
				
				case "UPDATEPLAYERS":{
					break;
				}
				
				case "LEADERDRAFT":{
					
					return this.encapsulateLeaderDraftChoice(a);
				}
				
				case "TILESDRAFT":{
					
					return this.encapsulateTilesDraftChoice(a);
				}
				
				case "LORENZOILMAGNIFICO":{
					
					return this.encapsulateLorenzoIlMagnificoChoice(a);
				}
				
				case "FAMOFFERREQUEST":{
					
					return this.encapsulateFamiliarOffer();
				}
				
				case "ENDGAME":{
					this.view.printFinalScoreBoard(a);
					this.view.endGameInput();
					throw new TerminatedException(" ");
				}
				
				default: {
					
				}
			}	
			throw new OutputNotNecessaryException("Not necessary output");
		}
		
}
