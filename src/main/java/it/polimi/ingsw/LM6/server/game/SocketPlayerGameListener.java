package it.polimi.ingsw.LM6.server.game;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import it.polimi.ingsw.LM6.server.game.bonuses.Bonus;
import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;
import it.polimi.ingsw.LM6.server.game.debug.Debug;
import it.polimi.ingsw.LM6.server.game.exceptions.AlreadyOccupiedException;
import it.polimi.ingsw.LM6.server.game.exceptions.AlreadyUsedBonusException;
import it.polimi.ingsw.LM6.server.game.exceptions.AlreadyUsedFamiliarException;
import it.polimi.ingsw.LM6.server.game.exceptions.AnotherFamiliarSameColourException;
import it.polimi.ingsw.LM6.server.game.exceptions.BadInputException;
import it.polimi.ingsw.LM6.server.game.exceptions.CannotGoOnMarketException;
import it.polimi.ingsw.LM6.server.game.exceptions.DoubleCostException;
import it.polimi.ingsw.LM6.server.game.exceptions.InvalidActionException;
import it.polimi.ingsw.LM6.server.game.exceptions.LeaderException;
import it.polimi.ingsw.LM6.server.game.exceptions.LorenzoIlMagnificoException;
import it.polimi.ingsw.LM6.server.game.exceptions.NotEnoughMilitaryPointsException;
import it.polimi.ingsw.LM6.server.game.exceptions.NotEnoughResourcesException;
import it.polimi.ingsw.LM6.server.game.exceptions.NotStrongEnoughException;
import it.polimi.ingsw.LM6.server.game.exceptions.SixCardsException;
import it.polimi.ingsw.LM6.server.game.familiar.Familiar;

public class SocketPlayerGameListener extends Thread{
	static String error = "Il sistema non è in grado di processare la tua richiesta";
	private LorenzoIlMagnifico game;
	private Player player;
	private Bonus temporalBonus;
	private String temporalMessage; 
	private Boolean actionDone;
	private Socket clientSocket;
	
	public SocketPlayerGameListener(LorenzoIlMagnifico g, Player p, Socket s){
		this.game=g;
		this.player=p;
		this.clientSocket=s;
		this.actionDone=false;
	}
	
	/**
	 * This method wait for a client's message, then call handleMessage if the game is started or isn't finished (obviously this have to be the player's turn), then
	 * if it's necessary calls handleAction to do an action on the game. After that, it see inside the bonus(HandleBonus method),
	 * if it require an extra action, call the handleBonusResponse to send a response to the client, otherwise send a new
	 * menu to the client and wait for another message. 
	 */
	@Override
	public void run() {
		String mex = new String();
		String mexAggiornato = new String();
		Bonus bonus;
		Scanner in=null;
		try {
			in= new Scanner(this.clientSocket.getInputStream());
			this.player.setIsActive(true);
			this.player.setIsConnected(true);
			
			this.player.send("INFO;You've been added to a game.");
			while(true){
				mex=in.nextLine();
				
				
				if(!this.game.isStarted() || this.game.isFinished()){
					this.player.send("INFO;Game not started or finished. Please wait...");
					continue;
				}
				
				if(!this.game.isTurn(this.player) && !this.game.isExcommunicationPhase() && !this.game.isPersonalBonusTilesPhase()){
					if(!this.game.isLeaderDraftPhase() && !this.game.isFamiliarOfferPhase()){
						this.player.activatePlayer();
						this.actionDone=false;
						if(this.temporalBonus!=null){
							this.game.giveResourcesToPlayer(this.player, this.temporalBonus);
							this.temporalBonus = null;
						}
						this.player.send("INFO;It's not your turn.");	
						continue;
					}
				}				
				
				try {
					Debug.print("MESSAGGIO: "+mex);
					mexAggiornato = this.handleMessageSocket(mex);
					
				} catch (BadInputException | NotBonusException | LeaderException e) {
					this.temporalBonus=null;
					ResponseBuilder.SendMenu(e.getMessage(), this.player, this.actionDone, this.game);
					continue;
				} catch (NumberFormatException e){
					ResponseBuilder.SendMenu("Invalid request to the server.", this.player, this.actionDone, this.game);
					continue;
				} catch (LorenzoIlMagnificoException e) {
					int i;
					String tmp3 = new String();
					ArrayList<String> leaderInBound = e.getLeaders();
					for(i=0;i<leaderInBound.size();i++)
						tmp3 = tmp3+i+"-"+leaderInBound.get(i)+";";
					this.player.send("LORENZOILMAGNIFICO;Choose which leader card effect you wish to copy.;"+tmp3);
					continue;
				} catch (AlreadyUsedBonusException e) {
					ResponseBuilder.SendMenu(e.getMessage(), this.player, this.actionDone, this.game);
					continue;
				} 
				
				if(mexAggiornato.compareTo("%%%")==0)
					continue;
				try {
					bonus = this.handleAction(mexAggiornato);
					this.handleBonus(bonus, true);
					
				}catch (BadInputException | NotEnoughResourcesException | NotStrongEnoughException |
						LeaderException | AlreadyOccupiedException | AlreadyUsedFamiliarException |
						AnotherFamiliarSameColourException | NotEnoughMilitaryPointsException |
						CannotGoOnMarketException | SixCardsException e){
					ResponseBuilder.handleException(mexAggiornato, e.getMessage(), this.player, this.temporalBonus, this.actionDone, this.game);
					continue;
				} catch (DoubleCostException e){
					this.temporalMessage = mex;
					this.player.send("COSTCHOICEREQUEST;"+e.getMessage());
					continue;
				}
			}
		}catch (IOException | NoSuchElementException e) {
			this.player.setIsActive(false);
			this.player.setIsConnected(false);
			game.sendAll("Il player "+this.player.getNickname()+ " è inattivo.");
			boolean isGamePhase=!this.game.isExcommunicationPhase() && !this.game.isPersonalBonusTilesPhase() && !this.game.isLeaderDraftPhase();
			if(this.game.isStarted() && isGamePhase && this.game.isTurn(this.player) && !this.game.isFinished())
				this.game.playerShift();
			
		}finally{
			if(in!=null)
				in.close();
		}
		return;	
	}
	
	
	/**
	 * This method handle the message received from the client then add some values if the header correspond to a bonus
	 * action, return the same string if it correspond to a normal action or return a special message if the message header
	 * not requires to build an action object.
	 * This method also checks if the bonus actions are actually own by the player.
	 * @param s is the message received from the client
	 * @return a string that is already ready to be used to create an action object (if it's necessary)
	 * @throws BadInputException if the header or another parameter isn't allowed
	 * @throws NotBonusException if the header is about a bonus action, but that bonus is not saved on the server
	 * @throws LeaderException if the chosen leader card for playing isn't owned by the player
	 * @throws NumberFormatException if some parameters are not number
	 * @throws AlreadyUsedBonusException 
	 */
	private String handleMessageSocket(String s) throws NotBonusException, LeaderException, LorenzoIlMagnificoException, AlreadyUsedBonusException{
		String[] tmp;
		String[] tmp2;
		int i;
		String output;
		tmp=s.split(";");
		
		/*MATTEO added these flags, for controlling the actions
		 * 
		 */
		Boolean canDoAction=this.game.isTurn(this.player) && !this.game.isExcommunicationPhase() && !this.game.isLeaderDraftPhase() && !this.game.isPersonalBonusTilesPhase() && !this.game.isFamiliarOfferPhase();
		Boolean canDoExcommunication=this.game.isExcommunicationPhase();
		Boolean canDoLeaderDraftChoice = this.game.isLeaderDraftPhase();
		Boolean canDoBonusTilesChoice = this.game.isPersonalBonusTilesPhase();
		Boolean canDoOffer = this.game.isFamiliarOfferPhase();
		
		if(!this.checkHeader(tmp[0]) || !canDoAction && !canDoExcommunication && !canDoBonusTilesChoice && !canDoLeaderDraftChoice && !canDoOffer)
			throw new BadInputException(error);
		
		if("TOWERACTIONWITHCHOICE".equals(tmp[0]) && canDoAction){
			if(this.temporalMessage==null)
				throw new BadInputException(error);
			tmp2 = this.temporalMessage.split(";");
			if("TOWERACTION".equals(tmp2[0]) && canDoAction)
				tmp2[0]="TOWERACTIONWITHCHOICE";
			else
				tmp2[0]="BONUSTOWERACTIONWITHCHOICE";
			output=new String();
			for(i=0;i<tmp2.length;i++){
				output=output+tmp2[i]+";";				
			}
			output=output+tmp[1];
			if("BONUSTOWERACTIONWITHCHOICE".equals(tmp2[0]))
				output=output+";"+this.temporalBonus.getBonusActionStrength();
			return output;
		}
		if("SHOWTABLEREQUEST".equals(tmp[0]) && canDoAction){
			this.player.send(this.game.showBoard());
			ResponseBuilder.SendMenu(" ", this.player, this.actionDone, this.game);
			return "%%%";
		}
		if("SHOWPLAYERSREQUEST".equals(tmp[0]) && canDoAction){
			this.player.send(this.game.showPlayers(this.player));
			ResponseBuilder.SendMenu(" ", this.player, this.actionDone, this.game);
			return "%%%";
		}
		if("EXCOMMUNICATIONCHOICE".equals(tmp[0])){
			if(canDoExcommunication){
				if(tmp[1].compareTo("1")==0){
					this.game.setExcommunicationResult(this.player);
					this.player.send("INFO;You won't be excommunicated. Please wait...");
				} else
					this.player.send("INFO;You will be excommunicated. Please wait...");
				return "%%%";
			}
			this.player.send("INFO;Time for this choice is over.");
			return "%%%";
		}
		if("COUNCILREQUIREMENT".equals(tmp[0]) && canDoAction){
			if(this.temporalBonus==null)
				throw new BadInputException(error);
			if(this.temporalBonus.getCouncilBonusesNumber()<1)
				throw new BadInputException("No bonuses avaiable.");
			return s;
		}
		if("DOUBLECOUNCILREQUIREMENT".equals(tmp[0]) && canDoAction){
			if(this.temporalBonus==null)
				throw new BadInputException(error);
			if(this.temporalBonus.getCouncilBonusesNumber()<2)
				throw new BadInputException("No bonuses avaiable.");
			return s;
		}
		if("TRIPLECOUNCILREQUIREMENT".equals(tmp[0]) && canDoAction){
			if(this.temporalBonus==null)
				throw new BadInputException(error);
			if(this.temporalBonus.getCouncilBonusesNumber()<3)
				throw new BadInputException("No bonuses avaiable.");
			return s;
		}
		if("BONUSTOWERACTION".equals(tmp[0]) && canDoAction){
			if(this.temporalBonus==null)
				throw new BadInputException(error);
			else if(this.temporalBonus.hasBonusAction()==false)
				throw new NotBonusException("No bonus action avaiable.");
			else if("YELLOW".equals(tmp[3]) && this.temporalBonus.getBonusActionTarget()!='Y' && this.temporalBonus.getBonusActionTarget()!='E')
				throw new NotBonusException("No yellow bonus action avaiable.");
			else if("GREEN".equals(tmp[3]) && this.temporalBonus.getBonusActionTarget()!='G' && this.temporalBonus.getBonusActionTarget()!='E')
				throw new NotBonusException("No green bonus action avaiable.");
			else if("BLUE".equals(tmp[3]) && this.temporalBonus.getBonusActionTarget()!='B' && this.temporalBonus.getBonusActionTarget()!='E')
				throw new NotBonusException("No blue bonus action avaiable.");
			else if("PURPLE".equals(tmp[3]) && this.temporalBonus.getBonusActionTarget()!='P' && this.temporalBonus.getBonusActionTarget()!='E')
				throw new NotBonusException("No purple bonus action avaiable.");
			
			return s+";"+this.temporalBonus.getBonusActionStrength();
		}
		if("BONUSHARVESTACTION".equals(tmp[0]) && canDoAction) {
			if(this.temporalBonus==null)
				throw new BadInputException(error);
			if(!this.temporalBonus.hasBonusHarvest())
				throw new NotBonusException("No bonus harvest avaiable.");
			output=s;
			return output+";"+this.temporalBonus.getBonusPHStrength();
		}
		if("BONUSPRODUCTIONACTION".equals(tmp[0]) && canDoAction){
			if(this.temporalBonus==null)
				throw new BadInputException(error);
			if(!this.temporalBonus.hasBonusProduction())
				throw new NotBonusException("No bonus production avaiable.");
			return s+";"+this.temporalBonus.getBonusPHStrength();
		}
		if("PASS".equals(tmp[0]) && canDoAction){
			this.game.playerShift();
			this.player.activatePlayer();
			this.actionDone=false;
			return "%%%";
		}
		if("LEADERPLAY".equals(tmp[0]) && canDoAction){
			
			this.game.playLeaderCard(this.player, Integer.parseInt(tmp[1]));		
			ResponseBuilder.SendMenu("Card played.", this.player, this.actionDone, this.game);
			return "%%%";		
		}
		if("LORENZOILMAGNIFICOCHOICE".equals(tmp[0]) && canDoAction){
			this.game.copyLeaderEffect(this.player, Integer.parseInt(tmp[1]));
			ResponseBuilder.SendMenu("Card played.", this.player, this.actionDone, this.game);
			return "%%%";
		}
		if("LEADERHARVEST".equals(tmp[0]) && canDoAction){
			Bonus b = this.game.getEveryTurnHarvest(this.player);
			this.handleBonus(b, true);
			return "%%%";
		}
		if("LEADERPRODUCTION".equals(tmp[0]) && canDoAction){
			Bonus b = this.game.getEveryTurnProduction(this.player);
			this.handleBonus(b, true);
			return "%%%";
		}
		if("LEADERRESOURCES".equals(tmp[0]) && canDoAction){
			Bonus b = this.game.getEveryTurnResources(this.player);
			this.handleBonus(b, true);
			return "%%%";
		}
		if("FAMILIAROFFER".equals(tmp[0])){
			if(canDoOffer){
				ResourceSet res = new ResourceSet(Integer.parseInt(tmp[1]), Integer.parseInt(tmp[2]), Integer.parseInt(tmp[3]), Integer.parseInt(tmp[4]), 0, 0, 0);
				this.game.setPlayerOffer(this.player, res);
				this.player.send("INFO;You made your bid. Please wait...");
				return "%%%";
			}
			this.player.send("INFO;Time for this choice is over.");
			return "%%%";
		}
		if("LEADERFAMILYBONUS".equals(tmp[0]) && canDoAction){
			Familiar fam = this.player.getFamiliar(tmp[1]);
			this.game.applyOneSixFamiliarPerTurn(this.player, fam);
			ResponseBuilder.SendMenu("Your "+tmp[1]+" family member has now value 6.", this.player, this.actionDone, this.game);
			return "%%%";
		}
		if("TILESDRAFTCHOICE".equals(tmp[0])){
			if(canDoBonusTilesChoice){
				this.game.setDraftChoice(this.player, Integer.parseInt(tmp[1]));
				this.player.send("INFO;You made your choice. Please wait...");
				return "%%%";
			}
			this.player.send("INFO;Time for this choice is over.");
			return "%%%";
		}
		if("LEADERDRAFTCHOICE".equals(tmp[0])){
			if(canDoLeaderDraftChoice){
				this.game.setDraftChoice(this.player, Integer.parseInt(tmp[1]));
				this.player.send("INFO;You made your choice. Please wait...");
				return "%%%";
			}
			this.player.send("INFO;Time for this choice is over.");
			return "%%%";
		}
		if("BONUSRENOUNCE".equals(tmp[0]) && canDoAction){
			if("COUNCILREQUIREMENT".equals(tmp[1])){
				if(this.temporalBonus==null)
						throw new BadInputException(error);
				this.temporalBonus.removeCouncilBonus();
				this.handleBonus(this.temporalBonus, false);
			}
			if("DOUBLECOUNCILREQUIREMENT".equals(tmp[1]) && canDoAction){
				if(this.temporalBonus==null)
						throw new BadInputException(error);
				this.temporalBonus.removeDoubleCouncilBonus();
				this.handleBonus(this.temporalBonus, false);
			}
			if("TRIPLECOUNCILBONUS".equals(tmp[1]) && canDoAction){
				if(this.temporalBonus==null)
						throw new BadInputException(error);
				this.temporalBonus.removeDoubleCouncilBonus();
				this.handleBonus(this.temporalBonus, false);
			}
			if("BONUSTOWERACTION".equals(tmp[1]) && canDoAction){
				if(this.temporalBonus==null)
						throw new BadInputException(error);
				this.temporalBonus.removeBonusAction();
				this.handleBonus(this.temporalBonus, false);
			}	
			if("BONUSPRODUCTIONACTION".equals(tmp[1]) && canDoAction){
				if(this.temporalBonus==null)
						throw new BadInputException(error);
				this.temporalBonus.removeBonusProduction();
				this.handleBonus(this.temporalBonus, false);
			}
			if("BONUSHARVESTACTION".equals(tmp[1]) && canDoAction){
				if(this.temporalBonus==null)
						throw new BadInputException(error);
				this.temporalBonus.removeBonusHarvest();
				this.handleBonus(this.temporalBonus, false);
			}
			
			return "%%%";
		}
		return s;
	}
	
	
	
	
	/**
	 * This method create the action starting from the message received from the client. then call the corresponding method
	 * on the game. (the type attribute of the action can make simply the choice among all methods of the game)
	 * @param message is the string received from the client
	 * @return the bonus obtained from anyone method called on the game
	 * @throws BadInputException if action constructor throws it
	 * @throws LeaderException if the action on leader card goes wrong
	 * @throws NotEnoughResourcesException if the player hasn't enough resource to complete the action
	 * @throws NotStrongEnoughException if the family chosen by the player is not strong enough to complete the action
	 * @throws AlreadyOccupiedException if the action space chosen by the player is already occupied
	 * @throws AlreadyUsedFamiliarException if the family chosen by the player is already been placed
	 * @throws AnotherFamiliarSameColourException if the tower or other spaces already contains a family with the same
	 * color as the chosen one
	 * @throws NotEnoughMilitaryPointsException if the military points own by the player aren't enough to get a card
	 * @throws DoubleCostException if the card corresponding the action has a double cost or the player have a discount
	 * on action
	 * @throws CannotGoOnMarketException if the player own the malus witch can't allow to go on a market place
	 * @throws SixCardsException if the player would earn a card type, but it has already six card of that type
	 */
	private Bonus handleAction(String message) throws LeaderException, NotEnoughResourcesException, NotStrongEnoughException, AlreadyOccupiedException, AlreadyUsedFamiliarException, AnotherFamiliarSameColourException, NotEnoughMilitaryPointsException, DoubleCostException, CannotGoOnMarketException, SixCardsException {
		
		String[] tmp;
		Action act;
		tmp = message.split(";");
		Bonus output;

		boolean controlvalue = false;
		
		if(this.temporalBonus==null){
			this.temporalBonus = new Bonus();
			controlvalue=true;
    }
		act = new Action(tmp, this.player, this.temporalBonus.getbonusActionDiscount());
		
		if(controlvalue)
			this.temporalBonus=null;
		
		switch(act.getType()){
			
			case "TOWERACTION":{
				output = this.game.placeOnTower(act);
				this.actionDone=true;
				return output;
			}
			case "TOWERACTIONWITHCHOICE": {
				output = this.game.placeOnTower(act);
				this.actionDone=true;
				this.temporalMessage=null;
				return output;
			}
			case "BONUSTOWERACTIONWITHCHOICE":{
				output = this.game.placeOnTower(act);
				this.temporalBonus.removeBonusAction();
				this.temporalMessage=null;
				return output;
			}
			case "MARKETACTION":{
				output = this.game.placeOnMarket(act);
				this.actionDone=true;
				return output;
			}
			case "HARVESTACTION":{
				output = this.game.placeFamiliarHarvest(act);
				this.actionDone=true;
				return output;
			}
			case "PRODUCTIONACTION":{
				output = this.game.placeFamiliarProduction(act);
				this.actionDone=true;
				return output;
			}
			case "COUNCILACTION": {
				output = this.game.placeOnCouncil(act);
				this.actionDone=true;
				return output;
			}
			case "BONUSTOWERACTION":{
				output = this.game.placeOnTower(act);
				this.temporalBonus.removeBonusAction();
				return output;
			}
			case "BONUSPRODUCTIONACTION":{
				output = this.game.placeFamiliarProduction(act);
				this.temporalBonus.removeBonusProduction();
				return output;
			}
			case "BONUSHARVESTACTION":{
				output = this.game.placeFamiliarHarvest(act);
				this.temporalBonus.removeBonusHarvest();
				return output;
			}
			case "COUNCILREQUIREMENT":{
				try {
					output = this.game.getCouncilBonus(act);
					this.temporalBonus.removeCouncilBonus();
					return output;
				} catch (InvalidActionException e) {
					//non si verifica mai
				}
				
			}
			case "DOUBLECOUNCILREQUIREMENT":{
				try {
					output = this.game.getCouncilBonus(act);
					this.temporalBonus.removeDoubleCouncilBonus();
					return output;
				} catch (InvalidActionException e) {
					//non si verifica mai
				}
				
			}
			case "TRIPLECOUNCILREQUIREMENT":{
				try {
					output = this.game.getCouncilBonus(act);
					this.temporalBonus.removeTripleCouncilBonus();
					return output;
				} catch (InvalidActionException e) {
					//non si verifica mai
				}
				
			}
			case "LEADERDISCARD":{
				return this.game.discardLeaderCard(act);
			}
		}

		return null; //MAI RITORNATO
	}
	
	
	
	
	

	/**
	 * This method look inside the bonus, if it not requires an extra action it give the bonus resources to the player, 
	 * otherwise it call handleBonusResponse method
	 * @param b is the bonus
	 */
	private void handleBonus( Bonus b, boolean somma) {
			
		if (this.temporalBonus!=null && somma){
			Bonus tmp = this.temporalBonus.sum(b, this.player);
			this.temporalBonus = tmp;
		}
		else
			this.temporalBonus=b;
		
		if(this.temporalBonus.hasBonusAction() || this.temporalBonus.hasBonusHarvest() || this.temporalBonus.hasBonusProduction() || this.temporalBonus.getCouncilBonusesNumber()>0)
			handleBonusResponse(this.temporalBonus);
		else {
			this.game.giveResourcesToPlayer(this.player, this.temporalBonus);
			this.temporalBonus=null;
			ResponseBuilder.SendMenu(" ", this.player, this.actionDone, this.game);
		}
		
	}
	
	
	
	/**
	 * This method look inside the bonuses and decides what kind of reply have to be send to the client, starting from 
	 * the council bonus
	 * @param b is the bonus
	 */
	private void handleBonusResponse(Bonus b){
		
		if(b.getCouncilBonusesNumber()>0)
			this.player.send(ResponseBuilder.buildBonusCouncilResponse(b));
		
		else if(b.hasBonusAction())
			this.player.send(ResponseBuilder.buildBonusActionResponse(b));
		
		else if(b.hasBonusProduction())
			this.player.send(ResponseBuilder.buildBonusProductionResponse(this.player, b));
		
		else if(b.hasBonusHarvest())
			this.player.send(ResponseBuilder.buildBonusHarvestResponse(b));
	}
		
	/**
	 * This method checks the string s header if it's among the all one allowed
	 * @param s is the header of the string received from the client
	 * @return a boolean witch confirm the header or not
	 */
	private Boolean checkHeader(String s){
		if(s.compareTo("TOWERACTION")==0 || s.compareTo("BONUSTOWERACTION")==0 || s.compareTo("TOWERACTIONWITHCHOICE")==0 || s.compareTo("BONUSTOWERACTIONWITHCHOICE")==0 ||
				s.compareTo("MARKETACTION")==0 || s.compareTo("HARVESTACTION")==0 || s.compareTo("PRODUCTIONACTION")==0 ||
				s.compareTo("COUNCILACTION")==0 || s.compareTo("COUNCILREQUIREMENT")==0 || s.compareTo("DOUBLECOUNCILREQUIREMENT")==0 ||
				s.compareTo("TRIPLECOUNCILREQUIREMENT")==0 || s.compareTo("LEADERDISCARD")==0 || s.compareTo("LEADERPLAY")==0 ||
				s.compareTo("SHOWTABLEREQUEST")==0 || s.compareTo("SHOWPLAYERSREQUEST")==0 || 
				s.compareTo("SHOWLEADERCARDREQUEST")==0 || s.compareTo("PASS")==0 || s.compareTo("BONUSHARVESTACTION")==0 ||
				s.compareTo("BONUSPRODUCTIONACTION")==0 || s.compareTo("BONUSRENOUNCE")==0 ||
				s.compareTo("EXCOMMUNICATIONCHOICE")==0 || s.equals("LEADERDRAFTCHOICE") || s.equals("LORENZOILMAGNIFICOCHOICE")
				|| s.equals("TILESDRAFTCHOICE") ||s.equals("LEADERHARVEST")||s.equals("LEADERPRODUCTION")||s.equals("LEADERRESOURCES")
				||s.equals("LEADERFAMILYBONUS") || s.equals("FAMILIAROFFER"))
			return true;
		return false;
			
	}
		
}
