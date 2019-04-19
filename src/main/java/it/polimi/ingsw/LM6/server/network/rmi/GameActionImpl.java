//Needs fixing later
package it.polimi.ingsw.LM6.server.network.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import it.polimi.ingsw.LM6.server.game.Action;
import it.polimi.ingsw.LM6.server.game.LorenzoIlMagnifico;

import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.game.ResponseBuilder;
import it.polimi.ingsw.LM6.server.game.bonuses.Bonus;
import it.polimi.ingsw.LM6.server.game.exceptions.AlreadyOccupiedException;
import it.polimi.ingsw.LM6.server.game.exceptions.AlreadyUsedBonusException;
import it.polimi.ingsw.LM6.server.game.exceptions.AlreadyUsedFamiliarException;
import it.polimi.ingsw.LM6.server.game.exceptions.AnotherFamiliarSameColourException;
import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;
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

public class GameActionImpl extends UnicastRemoteObject implements GameAction {
	
	private transient Player player;
	private transient LorenzoIlMagnifico game;
	private transient Bonus temporalBonus;
	private String temporalMessage;
	private boolean actionDone;
	private Boolean canDoAction;
	private Boolean canDoExcommunication;
	private Boolean canDoLeaderDraftChoice;
	private Boolean canDoBonusTilesChoice;
	private Boolean canDoOffer;
	
	public GameActionImpl(LorenzoIlMagnifico game, Player p) throws RemoteException{
		super();
		this.game=game;
		this.player=p;
		this.player.setIsActive(true);
		this.player.setIsConnected(true);
	}
	
	private void setPermitFlags(){
		this.canDoAction=this.game.isTurn(this.player) && !this.game.isExcommunicationPhase() && !this.game.isLeaderDraftPhase() && !this.game.isPersonalBonusTilesPhase();
		this.canDoExcommunication=this.game.isExcommunicationPhase();
		this.canDoLeaderDraftChoice= this.game.isLeaderDraftPhase();
		this.canDoBonusTilesChoice = this.game.isPersonalBonusTilesPhase();
		this.canDoOffer = this.game.isFamiliarOfferPhase();
	}
	
	
	@Override
	public void showBoard() throws RemoteException {
		this.setPermitFlags();
		if(this.checkSpeakCondition() && canDoAction){
			String board = this.game.showBoard();
			this.player.send(board);
			ResponseBuilder.SendMenu(" ", this.player, this.actionDone, this.game);
		}
	}

	@Override
	public void showPlayers() throws RemoteException {
		this.setPermitFlags();
		if(this.checkSpeakCondition() && canDoAction){
			String players = this.game.showPlayers(this.player);
			this.player.send(players);
			ResponseBuilder.SendMenu(" ", this.player, this.actionDone, this.game);
		}
	}

	@Override
	public void setExcommunicationResult(String s) throws RemoteException {
		this.setPermitFlags();
		if(this.checkSpeakCondition()){
			if(canDoExcommunication){
				if("1".equals(s)){
					this.game.setExcommunicationResult(this.player);
					this.player.send("INFO;You won't be excommunicated. Please wait...");
				}
				else {
					this.player.send("INFO;You will be excommunicated. Please wait...");
				}
			}
		}
	}
	
	@Override
	public void towerAction(String towerColour, String floor, String family, String servants) throws RemoteException {
		this.setPermitFlags();
		
		if(this.checkSpeakCondition() && canDoAction){
			String[] mex = this.createStringArray("TOWERACTION;"+towerColour+";"+floor+";"+family+";"+servants);		
			
			try{
				ResourceSet res = this.createResourceSet();
				Action act = new Action(mex, this.player, res);
				Bonus b = this.game.placeOnTower(act);
				this.actionDone=true;
				this.handleBonus(b, true);
			} catch(BadInputException e){
				ResponseBuilder.SendMenu("Invalid request to the server.", this.player, this.actionDone, this.game);		
			} catch(DoubleCostException e){
				this.temporalMessage = new String("TOWERACTION;"+towerColour+";"+floor+";"+family+";"+servants);
				this.player.send("COSTCHOICEREQUEST;"+e.getMessage());
			} catch(NotEnoughMilitaryPointsException | NotEnoughResourcesException | NotStrongEnoughException | SixCardsException e){
				ResponseBuilder.handleException("TOWERACTION;"+towerColour+";"+floor+";"+family+";"+servants, e.getMessage(), this.player, this.temporalBonus, this.actionDone, this.game);
			} catch(AnotherFamiliarSameColourException | AlreadyUsedFamiliarException | AlreadyOccupiedException  e){
				ResponseBuilder.SendMenu(e.getMessage(), this.player, this.actionDone, this.game);
			} 
		}
	}

	@Override
	public void towerActionWithChoice(String number) throws RemoteException {
		this.setPermitFlags();
		if(this.checkSpeakCondition() && canDoAction){
			if(this.temporalMessage==null){
				ResponseBuilder.SendMenu("Invalid request to the server.", this.player, this.actionDone, this.game);
				return ;
			}
			this.temporalMessage += ";"+number;
			String[] tmp = this.temporalMessage.split(";");
			if(tmp[0].equals("TOWERACTION"))
				tmp[0]="TOWERACTIONWITHCHOICE";
			else{
				if(this.temporalBonus==null){
					ResponseBuilder.SendMenu("Invalid request to the server.", this.player, this.actionDone, this.game);
					return ;
				}
				this.temporalMessage+=";"+this.temporalBonus.getBonusActionStrength();
				tmp = this.temporalMessage.split(";");
				tmp[0]="BONUSTOWERACTIONWITHCHOICE";				
			}
				
			
			ResourceSet res = this.createResourceSet();
			
			try{
				Action act = new Action(tmp, this.player, res);
				Bonus b = this.game.placeOnTower(act);
				this.actionDone=true;
				this.temporalMessage=null;
				this.handleBonus(b, true);
			} catch(BadInputException e){
				ResponseBuilder.SendMenu("Invalid request to the server.", this.player, this.actionDone, this.game);		
			} catch (AlreadyUsedFamiliarException | AnotherFamiliarSameColourException | AlreadyOccupiedException | 
					NotStrongEnoughException | NotEnoughResourcesException | NotEnoughMilitaryPointsException |
					SixCardsException e) {
				ResponseBuilder.SendMenu(e.getMessage(), this.player, this.actionDone, this.game);		
			}catch (DoubleCostException e) {
				//it never happens because this action is used to have the client chose a cost
			}
		}
	}

	@Override
	public void marketAction(String place, String family, String servants) throws RemoteException {
		this.setPermitFlags();
		if(this.checkSpeakCondition() && canDoAction){
			String[] tmp = this.createStringArray("MARKETACTION;"+place+";"+family+";"+servants);
			ResourceSet res = this.createResourceSet();
			
			try{
				Action act = new Action(tmp, this.player, res);
				Bonus b = this.game.placeOnMarket(act);
				this.actionDone=true;
				this.handleBonus(b, true);
			} catch(BadInputException e){
				ResponseBuilder.SendMenu("Invalid request to the server.", this.player, this.actionDone, this.game);		
			} catch (NotStrongEnoughException | AlreadyUsedFamiliarException | NotEnoughResourcesException | AlreadyOccupiedException | CannotGoOnMarketException e) {
				ResponseBuilder.SendMenu(e.getMessage(), this.player, this.actionDone, this.game);
			} 
		}
	}


	@Override
	public void productionAction(String family, String servants, String floor, String c1, String c2, String c3, String c4, String c5, String c6) throws RemoteException {
		this.setPermitFlags();
		if(this.checkSpeakCondition() && canDoAction){
			String[] tmp = this.createStringArray("PRODUCTIONACTION;"+family+";"+servants+";"+floor+";"+c1+";"+c2+";"+c3+";"+c4+";"+c5+";"+c6);
			
			try{
				ResourceSet res = this.createResourceSet();
				Action act = new Action(tmp, this.player, res);
				Bonus b = this.game.placeFamiliarProduction(act);
				this.actionDone=true;
				this.handleBonus(b, true);
			} catch(BadInputException e){
				ResponseBuilder.SendMenu("Invalid request to the server.", this.player, this.actionDone, this.game);			
			} catch (AlreadyUsedFamiliarException | NotStrongEnoughException | AnotherFamiliarSameColourException | AlreadyOccupiedException | NotEnoughResourcesException  e) {
				ResponseBuilder.SendMenu(e.getMessage(), this.player, this.actionDone, this.game);
			} 
			
		}
	}

	@Override
	public void harvestAction(String family, String slaves, String floor) throws RemoteException {
		this.setPermitFlags();
		if(this.checkSpeakCondition() && canDoAction){
			String[] tmp = this.createStringArray("HARVESTACTION;"+family+";"+slaves+";"+floor);
			ResourceSet res = this.createResourceSet();
			
			try{
				Action act = new Action(tmp, this.player, res);
				Bonus b = this.game.placeFamiliarHarvest(act);
				this.actionDone=true;
				this.handleBonus(b, true);
			} catch(BadInputException e){
				ResponseBuilder.SendMenu("Invalid request to the server.", this.player, this.actionDone, this.game);;			
			} catch (AlreadyUsedFamiliarException | NotStrongEnoughException | AnotherFamiliarSameColourException | AlreadyOccupiedException | NotEnoughResourcesException e) {
				ResponseBuilder.SendMenu(e.getMessage(), this.player, this.actionDone, this.game);
			} 
		}
	}

	@Override
	public void councilAction(String family, String servants) throws RemoteException {
		this.setPermitFlags();
		if(this.checkSpeakCondition() && canDoAction){
			String[] tmp = this.createStringArray("COUNCILACTION;"+family+";"+servants);
			
			ResourceSet res = this.createResourceSet();
			
			try{
				Action act = new Action(tmp, this.player, res);
				Bonus b = this.game.placeOnCouncil(act);
				this.actionDone=true;
				this.handleBonus(b, true);
			} catch(BadInputException e){
				ResponseBuilder.SendMenu("Invalid request to the server.", this.player, this.actionDone, this.game);		
			} catch (NotStrongEnoughException |AlreadyUsedFamiliarException | NotEnoughResourcesException e) {
				ResponseBuilder.SendMenu(e.getMessage(), this.player, this.actionDone, this.game);
			}
		}		
	}

	@Override
	public void leaderDiscard(String number) throws RemoteException {
		this.setPermitFlags();
		if(this.checkSpeakCondition() && canDoAction){
			String[] tmp = this.createStringArray("LEADERDISCARD;"+number);		
	
			ResourceSet res = this.createResourceSet();
			
			try{
				Action act = new Action(tmp, this.player, res);
				Bonus b = this.game.discardLeaderCard(act);
				this.actionDone=true;
				this.handleBonus(b, true);
			} catch(BadInputException e){
				ResponseBuilder.SendMenu("Invalid request to the server.", this.player, this.actionDone, this.game);
					
			} catch (LeaderException e) {
				ResponseBuilder.SendMenu(e.getMessage(), this.player, this.actionDone, this.game);
			}
		}		
	}

	@Override
	public void councilRequirement(String number) throws RemoteException {
		this.setPermitFlags();
		if(this.checkSpeakCondition() && canDoAction){
			String[] tmp = this.createStringArray("COUNCILREQUIREMENT;"+number);
			
			if(this.temporalBonus==null){
				ResponseBuilder.SendMenu("Invalid request to the server.", this.player, this.actionDone, this.game);
				return ;
			}
			else if(this.temporalBonus.getCouncilBonusesNumber()<1){
				ResponseBuilder.SendMenu("No council privilege to get.", this.player, this.actionDone, this.game);
				return ;
			}
				
			
			try{
				Action act = new Action(tmp, this.player, this.temporalBonus.getbonusActionDiscount());
				Bonus b = this.game.getCouncilBonus(act);
				this.temporalBonus.removeCouncilBonus();
				this.handleBonus(b, true);
			} catch(BadInputException e){
				ResponseBuilder.SendMenu("Invalid request to the server.", this.player, this.actionDone, this.game);		
			} catch (InvalidActionException e) {
				//it never happens
			}
		}
		
	}

	@Override
	public void doubleCouncilRequirement(String number1, String number2) throws RemoteException {
		this.setPermitFlags();
		if(this.checkSpeakCondition() && canDoAction){
			String[] tmp = this.createStringArray("DOUBLECOUNCILREQUIREMENT;"+number1+";"+number2);
			
	
			if(this.temporalBonus==null){
				ResponseBuilder.SendMenu("Invalid request to the server.", this.player, this.actionDone, this.game);
				return ;
			}
			if(this.temporalBonus.getCouncilBonusesNumber()<2 || !this.temporalBonus.mustBeDifferent()){
				ResponseBuilder.SendMenu("No double council privilege to get.", this.player, this.actionDone, this.game);
				return ;
			}
			
			try{
				Action act = new Action(tmp, this.player, this.temporalBonus.getbonusActionDiscount());
				Bonus b = this.game.getCouncilBonus(act);
				this.temporalBonus.removeDoubleCouncilBonus();
				this.handleBonus(b, true);
			} catch(BadInputException e){
				ResponseBuilder.SendMenu("Invalid request to the server.", this.player, this.actionDone, this.game);		
			} catch (InvalidActionException e) {
				
			}
		}
		
	}

	@Override
	public void tripleCouncilRequirement(String number1, String number2, String number3) throws RemoteException {
		this.setPermitFlags();
		if(this.checkSpeakCondition() && canDoAction){
			String[] tmp = this.createStringArray("TRIPLECOUNCILREQUIREMENT;"+number1+";"+number2+";"+number3);
			
			if(this.temporalBonus==null){
				ResponseBuilder.SendMenu("Invalid request to the server.", this.player, this.actionDone, this.game);
				return ;
			}
			if(this.temporalBonus.getCouncilBonusesNumber()<3 || !this.temporalBonus.mustBeDifferent()){
				ResponseBuilder.SendMenu("No triple council privilege to get.", this.player, this.actionDone, this.game);
				return ;
			}
			
			try{
				Action act = new Action(tmp, this.player, this.temporalBonus.getbonusActionDiscount());
				Bonus b = this.game.getCouncilBonus(act);
				this.temporalBonus.removeTripleCouncilBonus();
				this.handleBonus(b, true);
			} catch(BadInputException e){
				ResponseBuilder.SendMenu("Invalid request to the server.", this.player, this.actionDone, this.game);
			} catch (InvalidActionException e) {
				
			}
		}
	}

	@Override
	public void bonusTowerAction(String slaves, String floor, String towerColour) throws RemoteException {
		this.setPermitFlags();
		if(this.checkSpeakCondition() && canDoAction){
			
			if(this.temporalBonus==null){
				ResponseBuilder.SendMenu("Invalid request to the server.", this.player, this.actionDone, this.game);
				return ;
			}
			String[] tmp = this.createStringArray("BONUSTOWERACTION;"+slaves+";"+ floor +";"+ towerColour+";"+this.temporalBonus.getBonusActionStrength());

			if(this.temporalBonus.hasBonusAction()==false){
				ResponseBuilder.SendMenu("No bonus action available.", this.player, this.actionDone, this.game);
				return ;
			}
			if(tmp[3].compareTo("YELLOW")==0 && this.temporalBonus.getBonusActionTarget()!='Y' && this.temporalBonus.getBonusActionTarget()!='E'){
				ResponseBuilder.SendMenu("No yellow bonus action available.", this.player, this.actionDone, this.game);
				return ;
			}
			if(tmp[3].compareTo("GREEN")==0 && this.temporalBonus.getBonusActionTarget()!='G' && this.temporalBonus.getBonusActionTarget()!='E'){
				ResponseBuilder.SendMenu("No greeen bonus action available.", this.player, this.actionDone, this.game);
				return ;
			}
			if(tmp[3].compareTo("BLUE")==0 && this.temporalBonus.getBonusActionTarget()!='B' && this.temporalBonus.getBonusActionTarget()!='E'){
				ResponseBuilder.SendMenu("No blue bonus action available.", this.player, this.actionDone, this.game);
				return ;
			}
			if(tmp[3].compareTo("PURPLE")==0 && this.temporalBonus.getBonusActionTarget()!='P' && this.temporalBonus.getBonusActionTarget()!='E'){
				ResponseBuilder.SendMenu("No purple bonus action available.", this.player, this.actionDone, this.game);
				return ;
			}
			
			try{
				Action act = new Action(tmp, this.player, this.temporalBonus.getbonusActionDiscount());
				Bonus b = this.game.placeOnTower(act);
				this.temporalBonus.removeBonusAction();
				this.handleBonus(b, true);
			} catch(BadInputException e){
				ResponseBuilder.SendMenu("Invalid request to the server.", this.player, this.actionDone, this.game);		
			} catch(NotEnoughMilitaryPointsException | NotEnoughResourcesException | NotStrongEnoughException | SixCardsException e){
				ResponseBuilder.handleException("BONUSTOWERACTION;"+slaves+";"+ floor +";"+ towerColour, e.getMessage(), this.player, this.temporalBonus, this.actionDone, this.game);
			} catch (AlreadyUsedFamiliarException e) {
				//it never happens because during bonus action you don't place any familiar
			} catch (AnotherFamiliarSameColourException e) {
				//it never happens because during bonus action you don't place any familiar
			} catch (AlreadyOccupiedException e) {
				ResponseBuilder.handleException("BONUSTOWERACTION;"+slaves+";"+ floor +";"+ towerColour, e.getMessage(), this.player, this.temporalBonus, this.actionDone, this.game);
			} catch (DoubleCostException e) {
				this.temporalMessage= "BONUSTOWERACTION;"+slaves+";"+ floor +";"+ towerColour;
				this.player.send("COSTCHOICEREQUEST;"+e.getMessage());
			}
		}
	}

	@Override
	public void bonusHarvestAction(String servants) throws RemoteException {
		this.setPermitFlags();
		if(this.checkSpeakCondition() && canDoAction){
			
			if(this.temporalBonus==null){
				ResponseBuilder.SendMenu("Invalid request to the server.", this.player, this.actionDone, this.game);
				return ;
			}
			String[] tmp = this.createStringArray("BONUSHARVESTACTION;"+servants+";"+this.temporalBonus.getBonusPHStrength());

			if(this.temporalBonus.hasBonusHarvest()==false){
				ResponseBuilder.SendMenu("No bonus production available.", this.player, this.actionDone, this.game);
				return ;
			}
			
			try{
				Action act = new Action(tmp, this.player, this.temporalBonus.getbonusActionDiscount());
				Bonus b = this.game.placeFamiliarHarvest(act);
				this.temporalBonus.removeBonusHarvest();
				this.handleBonus(b, true);
			} catch(BadInputException e){
				ResponseBuilder.SendMenu("Invalid request to the server.", this.player, this.actionDone, this.game);			
			} catch(NotStrongEnoughException | NotEnoughResourcesException e){
				ResponseBuilder.handleException("BONUSHARVESTACTION;"+servants, e.getMessage(), this.player, this.temporalBonus, this.actionDone, this.game);
			} catch (AlreadyUsedFamiliarException e) {
				//it never happens because during bonus harvest there are not any familiar placement
			} catch (AnotherFamiliarSameColourException e) {
				//it never happens because during bonus harvest there are not any familiar placement
			} catch (AlreadyOccupiedException e) {
				//it never happens because during bonus harvest there are not any familiar placement
			}
		}
	}

	@Override
	public void bonusProductionAction(String servants, String c1, String c2, String c3, String c4, String c5, String c6) throws RemoteException {
		this.setPermitFlags();
		if(this.checkSpeakCondition() && canDoAction){			
			
			if(this.temporalBonus==null){
				ResponseBuilder.SendMenu("Invalid request to the server.", this.player, this.actionDone, this.game);
				return ;
			}
			
			String[] tmp = this.createStringArray("BONUSPRODUCTIONACTION;"+servants+";"+c1+";"+c2+";"+c3+";"+c4+";"+c5+";"+c6+";"+this.temporalBonus.getBonusPHStrength());
			
			if(this.temporalBonus.hasBonusProduction()==false){
				ResponseBuilder.SendMenu("No bonus production available.", this.player, this.actionDone, this.game);
				return ;
			}
			
			try{
				Action act = new Action(tmp, this.player, this.temporalBonus.getbonusActionDiscount());
				Bonus b = this.game.placeFamiliarProduction(act);
				this.temporalBonus.removeBonusProduction();
				this.handleBonus(b, true);
			} catch(BadInputException e){
				ResponseBuilder.SendMenu("Invalid request to the server.", this.player, this.actionDone, this.game);			
			} catch(NotStrongEnoughException | NotEnoughResourcesException e){
				ResponseBuilder.handleException("BONUSPRODUCTIONACTION"+";"+servants+";"+c1+";"+c2+";"+c3+";"+c4+";"+c5+";"+c6, e.getMessage(), this.player, this.temporalBonus, this.actionDone, this.game);
			} catch (AlreadyUsedFamiliarException e) {
				//it never happens
			} catch (AnotherFamiliarSameColourException e) {
				//it never happens
			} catch (AlreadyOccupiedException e) {
				//it never happens
			}
		}
	}

	@Override
	public void pass() throws RemoteException {
		this.setPermitFlags();
		if(this.checkSpeakCondition() && canDoAction){
			this.game.playerShift();
			this.player.activatePlayer();
			this.actionDone=false;
		}
	}

	@Override
	public void leaderPlay(String number) throws RemoteException {
		this.setPermitFlags();
		if(this.checkSpeakCondition() && canDoAction){
			try {
				this.game.playLeaderCard(this.player, Integer.parseInt(number));
				ResponseBuilder.SendMenu("Leader card played.", this.player, this.actionDone, this.game);
			} catch (NumberFormatException | LeaderException e) {
				ResponseBuilder.SendMenu(e.getMessage(), this.player, this.actionDone, this.game);
			} catch (LorenzoIlMagnificoException e) {
				this.player.send("LORENZOILMAGNIFICOCHOICE;"+e.getMessage());
			}
			
		}
	}

	@Override
	public void lorenzoIlMagnificoChoice(String number) throws RemoteException {
		this.setPermitFlags();
		if(this.checkSpeakCondition() && canDoAction){
			try {
				this.game.copyLeaderEffect(this.player, Integer.parseInt(number));
				ResponseBuilder.SendMenu("Effect copied.", this.player, this.actionDone, this.game);
			} catch (NumberFormatException | LeaderException e) {
				ResponseBuilder.SendMenu(e.getMessage(), this.player, this.actionDone, this.game);
			}
		}
	}

	@Override
	public void leaderResources() throws RemoteException {
		this.setPermitFlags();
		if(this.checkSpeakCondition() && canDoAction){
			Bonus b;
			try {
				b = this.game.getEveryTurnResources(this.player);
				this.handleBonus(b, true);
			} catch (AlreadyUsedBonusException e) {
				ResponseBuilder.SendMenu(e.getMessage(), this.player, this.actionDone, this.game);
			}
			
		}
	}

	@Override
	public void leaderProduction() throws RemoteException {
		this.setPermitFlags();
		if(this.checkSpeakCondition() && canDoAction){
			Bonus b;
			try {
				b = this.game.getEveryTurnProduction(this.player);
				this.handleBonus(b, true);
			} catch (AlreadyUsedBonusException e) {
				ResponseBuilder.SendMenu(e.getMessage(), this.player, this.actionDone, this.game);
			}
			
		}
	}

	@Override
	public void leaderHarvest() throws RemoteException {
		this.setPermitFlags();
		if(this.checkSpeakCondition() && canDoAction){
			Bonus b;
			try {
				b = this.game.getEveryTurnHarvest(this.player);
				this.handleBonus(b, true);
			} catch (AlreadyUsedBonusException e) {
				ResponseBuilder.SendMenu(e.getMessage(), this.player, this.actionDone, this.game);
			}
		
		}
	}

	@Override
	public void leaderFamilyBonus(String colour) throws RemoteException {
		this.setPermitFlags();
		if(this.checkSpeakCondition() && canDoAction){
			Familiar fam = this.player.getFamiliar(colour);
			try {
				this.game.applyOneSixFamiliarPerTurn(this.player, fam);
				ResponseBuilder.SendMenu("Your "+colour+" familiar has now value 6.", this.player, this.actionDone, this.game);
			} catch (AlreadyUsedBonusException e) {
				ResponseBuilder.SendMenu(e.getMessage(), this.player, this.actionDone, this.game);
			}
			
		}
	}

	@Override
	public void leaderDraftChoice(String number) throws RemoteException {
		this.setPermitFlags();
		if(this.checkSpeakCondition()){
			if(canDoLeaderDraftChoice){
				this.game.setDraftChoice(this.player, Integer.parseInt(number));
				this.player.send("INFO;You made your choice. Please wait...");
			}
			else
				this.player.send("INFO;Time is over to make this choice.");
		}
	}

	@Override
	public void tilesDraftChoice(String number) throws RemoteException {
		this.setPermitFlags();
		if(this.checkSpeakCondition()){
			if(canDoBonusTilesChoice){
				this.game.setDraftChoice(this.player, Integer.parseInt(number));
				this.player.send("INFO;You made your choice. Please wait...");
			}
			else
				this.player.send("INFO;Time is over to make this choice.");
		}
	}

	@Override
	public void bonusRenounce(String s) throws RemoteException {
		this.setPermitFlags();
		if(this.checkSpeakCondition() && canDoAction){
			if(s.equals("COUNCILREQUIREMENT") && canDoAction){
				if(this.temporalBonus==null){
					ResponseBuilder.SendMenu("Invalid request to the server.", this.player, this.actionDone, this.game);
					return;
				}
				try{
					this.temporalBonus.removeCouncilBonus();
				} catch (BadInputException e){
					ResponseBuilder.SendMenu(e.getMessage(), this.player, this.actionDone, this.game);
				}
				this.handleBonus(this.temporalBonus, false);
			}
			if(s.equals("DOUBLECOUNCILREQUIREMENT") && canDoAction){
				if(this.temporalBonus==null){
					ResponseBuilder.SendMenu("Invalid request to the server.", this.player, this.actionDone, this.game);
					return;
				}
				try{
					this.temporalBonus.removeDoubleCouncilBonus();
				} catch(BadInputException e){
					ResponseBuilder.SendMenu(e.getMessage(), this.player, this.actionDone, this.game);
				}
				this.handleBonus(this.temporalBonus, false);
			}
			if(s.equals("TRIPLECOUNCILREQUIREMENT") && canDoAction){
				if(this.temporalBonus==null){
					ResponseBuilder.SendMenu("Invalid request to the server.", this.player, this.actionDone, this.game);
					return;
				}
				try{
					this.temporalBonus.removeTripleCouncilBonus();
				} catch (BadInputException e){
					ResponseBuilder.SendMenu(e.getMessage(), this.player, this.actionDone, this.game);
				}
				this.handleBonus(this.temporalBonus, false);
			}
			if(s.equals("BONUSTOWERACTION") && canDoAction){
				if(this.temporalBonus==null){
					ResponseBuilder.SendMenu("Invalid request to the server.", this.player, this.actionDone, this.game);
					return;
				}
				try{
					this.temporalBonus.removeBonusAction();
				} catch(BadInputException e){
					ResponseBuilder.SendMenu(e.getMessage(), this.player, this.actionDone, this.game);
				}
				this.handleBonus(this.temporalBonus, false);
			}	
			if(s.equals("BONUSPRODUCTIONACTION") && canDoAction){
				if(this.temporalBonus==null){
					ResponseBuilder.SendMenu("Invalid request to the server.", this.player, this.actionDone, this.game);
					return;
				}
				try{
					this.temporalBonus.removeBonusProduction();
				} catch(BadInputException e){
					ResponseBuilder.SendMenu(e.getMessage(), this.player, this.actionDone, this.game);
				}
				this.handleBonus(this.temporalBonus, false);
			}
			if(s.equals("BONUSHARVESTACTION") && canDoAction){
				if(this.temporalBonus==null){
					ResponseBuilder.SendMenu("Invalid request to the server.", this.player, this.actionDone, this.game);
					return;
				}
				try{
					this.temporalBonus.removeBonusHarvest();
				} catch(BadInputException e){
					ResponseBuilder.SendMenu(e.getMessage(), this.player, this.actionDone, this.game);
				}
				this.handleBonus(this.temporalBonus, false);
			}
		}
	}
	
	/**
	 * Makes an String[] array using .split() string's method 
	 * @param s is the string
	 * @return the string just splitted
	 */
	private String[] createStringArray(String s) {
		return s.split(";");
	}

	private void handleBonus( Bonus b, boolean somma) {
		
		if (this.temporalBonus!=null && somma){
			Bonus tmp = this.temporalBonus.sum(b, this.player);
			this.setTemporalBonus(tmp);
		}
		else
			this.setTemporalBonus(b);
		
		if(this.temporalBonus.hasBonusAction() || this.temporalBonus.hasBonusHarvest() || this.temporalBonus.hasBonusProduction() || this.temporalBonus.getCouncilBonusesNumber()>0)
			handleBonusResponse(this.temporalBonus);
		else {
			this.game.giveResourcesToPlayer(this.player, this.temporalBonus);
			this.temporalBonus=null;
			ResponseBuilder.SendMenu(" ", this.player, this.actionDone, this.game);
		}
		
	}
	
	/**
	 * Prepares a reply for the client looking at his temporal bonus
	 * @param b is the bonus
	 */
	private void handleBonusResponse(Bonus b){
		String tmp;
		
		if(b.getCouncilBonusesNumber()>0){
			tmp = ResponseBuilder.buildBonusCouncilResponse(b);
			
			this.player.send(tmp);
			
		}
		else if(b.hasBonusAction()){
			tmp = ResponseBuilder.buildBonusActionResponse(b);			
			this.player.send(tmp);
		}			
		
		else if(b.hasBonusProduction()){
			tmp = ResponseBuilder.buildBonusProductionResponse(this.player, b);
			this.player.send(tmp);
		}
		else if(b.hasBonusHarvest()){
			tmp = ResponseBuilder.buildBonusHarvestResponse(b);
			
			this.player.send(tmp);
		}
	}
	

	/**
	 * Checks if the player can speak with the server looking at the turn and at each phase of the game.
	 * @return true if the player is allowed to talk, false otherwise.
	 */
	private boolean checkSpeakCondition(){

		if(!this.game.isStarted() || this.game.isFinished()){
			this.player.send("INFO;The game has yet to start or it is finished. Please wait...");
			return false;
		}
		
		if(!this.game.isTurn(this.player) && !this.game.isExcommunicationPhase() && !this.game.isPersonalBonusTilesPhase() && !this.game.isLeaderDraftPhase()){
			this.player.activatePlayer();
			this.actionDone=false;
			if(this.temporalBonus!=null){
				this.game.giveResourcesToPlayer(this.player, this.temporalBonus);
				this.temporalBonus = null;			
			}
			this.player.send("INFO;Not your turn.");	
			return false;
		}
	return true;
	}
	
	/**
	 * Creates a resourceSet if there isn't a temporalBonus saved in this class. It's necessary to avoid a NullPointerException
	 * in some methods which always requires it among the parameters.
	 * @return new ResourceSet if necessary.
	 */
	private ResourceSet createResourceSet(){
		if(this.temporalBonus==null)
			return new ResourceSet(0,0,0,0,0,0,0);
		return this.temporalBonus.getBonusActionDiscount();
	}

	@Override
	public void setFamiliarOffer(String a, String b, String c, String d) throws RemoteException {
		
		this.setPermitFlags();
		if(this.checkSpeakCondition()){
			if(canDoOffer){
				ResourceSet res = new ResourceSet(Integer.parseInt(a), Integer.parseInt(b), Integer.parseInt(c), Integer.parseInt(d), 0, 0, 0);
				this.game.setPlayerOffer(this.player, res);
				this.player.send("INFO;You made your bid. Please wait...");
			}
			else
				this.player.send("INFO;Time is over to make this choice.");
		}
	}
	
	public void setTemporalBonus(Bonus b){
		this.temporalBonus=b;
	}
	
	public Bonus getTemporalBonus(){
		return this.temporalBonus;
	}
	
	public void setTemporalMessage(String a){
		this.temporalMessage=a;
	}
	

}