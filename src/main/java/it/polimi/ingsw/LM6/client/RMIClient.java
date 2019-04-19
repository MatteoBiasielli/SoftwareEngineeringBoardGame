package it.polimi.ingsw.LM6.client;


import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import java.util.Vector;

import it.polimi.ingsw.LM6.client.exceptions.OutputNotNecessaryException;
import it.polimi.ingsw.LM6.client.exceptions.TerminatedException;
import it.polimi.ingsw.LM6.server.network.rmi.GameAction;
import it.polimi.ingsw.LM6.server.network.rmi.IConnectionSetupHandler;
import it.polimi.ingsw.LM6.server.network.rmi.IWelcome;

public class RMIClient implements IClient{
	
	private MessageHandler messageHandler;
	private GameAction gameActions;
	private IWelcome welcomeInterface;
	private IConnectionSetupHandler setupInterface;
	private UserOperationStub stub;
	private Vector<ActionAllowed> buffer;
	private Waiter waiter;
	
	public RMIClient(MessageHandler m){
		this.messageHandler=m;
		this.buffer = new Vector<ActionAllowed>();
		this.waiter = new Waiter();
	}

	@Override
	public void sendToServer(String message) throws RemoteException {
		
		if(!this.sendForPreGame(message)){
			
			String[] tmp = message.split(";");
					
					switch(tmp[0]){
						case "SHOWPLAYERSREQUEST":{
							this.gameActions.showPlayers();
							break;
						}
						case "SHOWTABLEREQUEST":{
							this.gameActions.showBoard();
							break;
						}
						case "TOWERACTION":{
							this.gameActions.towerAction(tmp[1], tmp[2], tmp[3], tmp[4]);
							break;
						}
						case "MARKETACTION":{
							this.gameActions.marketAction(tmp[1], tmp[2], tmp[3]);
							break;
						}
						case "PRODUCTIONACTION":{
							this.gameActions.productionAction(tmp[1], tmp[2], tmp[3], tmp[4], tmp[5], tmp[6], tmp[7], tmp[8], tmp[9]);
							break;
						}
						case "HARVESTACTION":{
							this.gameActions.harvestAction(tmp[1], tmp[2], tmp[3]);
							break;
						}
						case "COUNCILACTION":{
							this.gameActions.councilAction(tmp[1], tmp[2]);
							break;
						}
						case "COUNCILREQUIREMENT":{
							this.gameActions.councilRequirement(tmp[1]);
							break;
						}
						case "DOUBLECOUNCILREQUIREMENT":{
							this.gameActions.doubleCouncilRequirement(tmp[1], tmp[2]);
							break;
						}
						case "TRIPLECOUNCILREQUIREMENT":{
							this.gameActions.tripleCouncilRequirement(tmp[1], tmp[2], tmp[3]);
							break;
						}
						case "BONUSPRODUCTIONACTION":{
							this.gameActions.bonusProductionAction(tmp[1], tmp[2], tmp[3], tmp[4], tmp[5], tmp[6], tmp[7]);
							break;
						}
						case "BONUSHARVESTACTION":{
							this.gameActions.bonusHarvestAction(tmp[1]);
							break;
						}
						case "TOWERACTIONWITHCHOICE":{
							this.gameActions.towerActionWithChoice(tmp[1]);
							break;
						}
						case "LEADERPLAY":{
							this.gameActions.leaderPlay(tmp[1]);
							break;
						}
						case "LEADERDISCARD":{
							this.gameActions.leaderDiscard(tmp[1]);
							break;
						}
						case "LEADERHARVEST":{
							this.gameActions.leaderHarvest();
							break;
						}
						case "LEADERPRODUCTION":{
							this.gameActions.leaderProduction();
							break;
						}
						case "LEADERRESOURCES":{
							this.gameActions.leaderResources();
							break;
						}
						case "LORENZOILMAGNIFICOCHOICE":{
							this.gameActions.lorenzoIlMagnificoChoice(tmp[1]);
							break;
						}
						case "TILESDRAFTCHOICE":{
							this.gameActions.tilesDraftChoice(tmp[1]);
							break;
						}
						case "LEADERDRAFTCHOICE":{
							this.gameActions.leaderDraftChoice(tmp[1]);
							break;
						}
						case "LEADERFAMILYBONUS":{
							this.gameActions.leaderFamilyBonus(tmp[1]);
							break;
						}
						case "PASS":{
							this.gameActions.pass();
							break;
						}
						case "BONUSRENOUNCE":{
							this.gameActions.bonusRenounce(tmp[1]);
							break;
						}
						case "EXCOMMUNICATIONCHOICE":{
							this.gameActions.setExcommunicationResult(tmp[1]);
							break;
						}
						case "BONUSTOWERACTION":{
							this.gameActions.bonusTowerAction(tmp[1], tmp[2], tmp[3]);
							break;
						}
						case "FAMILIAROFFER":{
							this.gameActions.setFamiliarOffer(tmp[1], tmp[2], tmp[3], tmp[4]);
							break;
						}
						default :{
							
						}
					}
			
			}		
	}


	@Override
	public String getClientInput() throws OutputNotNecessaryException {
		
		while(this.buffer.isEmpty()){
			synchronized(this.waiter){
				try {
					this.waiter.wait();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		}
		ActionAllowed act = this.buffer.get(0);
		this.buffer.remove(0);
		String tmp = act.buildMessage();
		try{
			return this.messageHandler.handleMessage(tmp);
		}catch(TerminatedException e){
			try {
				this.stub.setTerminated();
			} catch (RemoteException e1) {
				//never verified, stub is a local object
			}
			throw new TerminatedException("GUI terminated.");
		}

	}

	@Override
	public String getClientNickAndPass() {
		try {
			return this.messageHandler.handleMessage("INVALIDLOGIN; ");
		} catch (OutputNotNecessaryException e) {
			//this never happens
		}
		return null;
	}

	@Override
	public void startConnection(String a){
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry(a,8080);
			this.welcomeInterface = (IWelcome)registry.lookup("IWelcome");
		} catch (RemoteException | NotBoundException  e) {
			System.err.println("LookUp failed.");
			System.exit(0);
		}				
	}	
	
	
	public boolean sendForPreGame(String message) throws RemoteException{
		String[] tmp = message.split(";");
		
		switch(tmp[0]){
		
			case "LOGINREQUEST": {
				
				stub= new UserOperationStubImpl(waiter,buffer);
				IConnectionSetupHandler connectionStub =this.welcomeInterface.connect(tmp[1], tmp[2], stub);
				this.setupInterface = connectionStub;
				return true;
			}	
		
			case "STARTNEWGAME": {
				
				Boolean a=true;
				if("0".equals(tmp[1]))
					a=false;				
				this.gameActions = this.setupInterface.startNewGame(a , Integer.parseInt(tmp[2]));
				return true;
			}
		
			case "SHOWSCOREBOARD": {
				
				this.setupInterface.showScoreboard(Integer.parseInt(tmp[1]), Integer.parseInt(tmp[2]));
				return true;
			}
			
			case "SHOWRECORD":{
				this.setupInterface.showRecord(tmp[1]);
				return true;
			}
			
			case "LOGOUTREQUEST":{
				this.setupInterface.logout();
				return true;
			}
			
			default :{
				
			}
		}
		
		return false;
	}


	
}
