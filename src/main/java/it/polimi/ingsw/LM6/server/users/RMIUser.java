package it.polimi.ingsw.LM6.server.users;

import java.rmi.RemoteException;

import it.polimi.ingsw.LM6.client.UserOperationStub;
import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.network.rmi.GameAction;
import it.polimi.ingsw.LM6.server.network.rmi.GameActionImpl;
import it.polimi.ingsw.LM6.server.network.rmi.RMIConnectionStatusChecker;

public class RMIUser extends IUser {
	private UserOperationStub stubServerToClient;
	private GameAction stubClientToServer;
	
	public RMIUser(UserOperationStub stubServerToClient){
		this.stubServerToClient = stubServerToClient;
	}
	
	public GameAction getStubClientToServer(){
		return this.stubClientToServer;
	}
	
	public synchronized void send(String m) {
		
		String mex[] = m.split(";");
		
		if("MENU".equals(mex[0])){
			String s = this.compactString(mex);
			try {
				stubServerToClient.setMenu(s);
			} catch (RemoteException e) {
				System.out.println("errore send client");
			}		
		} 
		else if("INFO".equals(mex[0])){
			String s = this.compactString(mex);
			try {
				stubServerToClient.setInfo(s);
			} catch (RemoteException e) {
				System.out.println("errore send client");
			}
		}
		else if("SETUPMENU".equals(mex[0])){
			String s = this.compactString(mex);
			try {
				stubServerToClient.setSetUpMenu(s);
			} catch (RemoteException e) {
				System.out.println("errore send client");
			}		
		}
		else if("INVALIDLOGIN".equals(mex[0])){
			String s = this.compactString(mex);
			try {
				stubServerToClient.setLoginRequest(s);
			} catch (RemoteException e) {
				System.out.println("errore send client");
			}
		}
		else if("SCOREBOARD".equals(mex[0])){
			String s = this.compactString(mex);
			try {
				stubServerToClient.setScoreboard(s);
			} catch (RemoteException e) {
				System.out.println("errore send client");
			}
		}
		else if("BONUSACTIONREQUEST".equals(mex[0])) {
			String s = this.compactString(mex);
			try {
				stubServerToClient.setBonusAction(s);
			} catch (RemoteException e) {
				System.out.println("errore send client");
			}
		}
		else if("BONUSPRODUCTIONREQUEST".equals(mex[0])) {
			String s = this.compactString(mex);
			try {
				stubServerToClient.setBonusProduction(s);
			} catch (RemoteException e) {
				System.out.println("errore send client");
			}
		}
		else if("BONUSHARVESTREQUEST".equals(mex[0])) {
			String s = this.compactString(mex);
			try {
				stubServerToClient.setBonusHarvest(s);
			} catch (RemoteException e) {
				System.out.println("errore send client");
			}
		}	
		else if("COSTCHOICEREQUEST".equals(mex[0])) {
			String s = this.compactString(mex);
			try {
				stubServerToClient.setCostChoiceRequest(s);
			} catch (RemoteException e) {
				System.out.println("errore send client");
			}
		}
		else if("BONUSCOUNCILREQUEST".equals(mex[0])) {
			String s = this.compactString(mex);
			try {
				stubServerToClient.setBonusCouncilRequest(s);
			} catch (RemoteException e) {
				System.out.println("errore send client");
			}
		}
		else if("BOARD".equals(mex[0])) {
			String s = this.compactString(mex);
			try {
				stubServerToClient.setBoard(s);
			} catch (RemoteException e) {
				System.out.println("errore send client");
			}
		}
		else if("PLAYERS".equals(mex[0])) {
			String s = this.compactString(mex);
			try {
				stubServerToClient.setPlayers(s);
			} catch (RemoteException e) {
				System.out.println("errore send client");
			}
		}
		else if("LORENZOILMAGNIFICOCHOICE".equals(mex[0])){
			String s = this.compactString(mex);
			try {
				stubServerToClient.setLorenzoIlMagnifico(s);
			} catch (RemoteException e) {
				System.out.println("errore send client");
			}
		}
		else if("UPDATEPLAYERS".equals(mex[0])) {
			String s = this.compactString(mex);
			try {
				stubServerToClient.setPlayersUpdate(s);
			} catch (RemoteException e) {
				System.out.println("errore send client");
			}
		}

		else if("UPDATEBOARD".equals(mex[0])) {
			String s = this.compactString(mex);
			try {
				stubServerToClient.setUpdateBoard(s);
			} catch (RemoteException e) {
				System.out.println("errore send client");
			}
		}
		else if("EXCOMMCHOICEREQUEST".equals(mex[0])) {
			String s = this.compactString(mex);
			try {
				stubServerToClient.setExcommunication(s);
			} catch (RemoteException e) {
				System.out.println("errore send client");
			}
		}
		else if("LEADERDRAFT".equals(mex[0])) {
			String s = this.compactString(mex);
			try {
				stubServerToClient.setLeaderDraft(s);
			} catch (RemoteException e) {
				System.out.println("errore send client");
			}
			
		}
		else if("TILESDRAFT".equals(mex[0])) {
			String s = this.compactString(mex);
			try {
				stubServerToClient.setTilesDraft(s);
			} catch (RemoteException e) {
				System.out.println("errore send client");
			}
			
		}
		else if("FAMOFFERREQUEST".equals(mex[0])) {
			String s = this.compactString(mex);
			try {
				stubServerToClient.setFamiliarOffer(s);
			} catch (RemoteException e) {
				System.out.println("errore send client");
			}
			
		}
		
		else if("ENDGAME".equals(mex[0])) {
			String s = this.compactString(mex);
			try {
				stubServerToClient.setEndGame(s);
			} catch (RemoteException e) {
				System.out.println("errore send client");
			}
			
		}
	}
	
	
	@Override
	public void listenTo(Player player){
		GameAction stubClientToServer;
		try {
			stubClientToServer = new GameActionImpl(this.getGame(), player);
			this.stubClientToServer = stubClientToServer;
		} catch (RemoteException e) {
			//Empty as disconnection will be detected by the RMIConnectionStatusChecker.
		}
		
		RMIConnectionStatusChecker checker = new RMIConnectionStatusChecker(this.game, player);
		
		checker.start();
		
		player.setIsConnected(true);
	}
	
	public void poke() throws RemoteException{
		if(this.stubServerToClient.poke())
			throw new RemoteException("Client Terminato");
	}

	public void deleteStubServerToClient() {
		this.stubServerToClient=null;	
	}
	
	private String compactString(String[] s){
		String x = new String();
		int i;
		for(i=1;i<s.length;i++)
			x+=s[i]+";";
		return x;
	}
}
