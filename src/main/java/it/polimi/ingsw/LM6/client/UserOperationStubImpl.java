package it.polimi.ingsw.LM6.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;


public class UserOperationStubImpl extends UnicastRemoteObject implements UserOperationStub {
	private transient Waiter waiter;
	private Vector<ActionAllowed> buffer;
	private Boolean isTerminated;
	
	public UserOperationStubImpl(Waiter t, Vector<ActionAllowed> b) throws RemoteException{
		this.waiter=t;
		this.buffer=b;
		this.isTerminated=false;
	}

	@Override
	public void setOptionalMessage(String s, ActionAllowed act) throws RemoteException {
			act.setOptionalInfo(s);
			this.buffer.add(act);
			this.waiter.wakeUp();
	}

	@Override
	public void setSetUpMenu(String s) throws RemoteException {
		ActionAllowed act = new ActionAllowed();
		act.setSetupMenu();
		this.setOptionalMessage(s, act);
				
	}

	@Override
	public void setScoreboard(String s) throws RemoteException {
		ActionAllowed act = new ActionAllowed();
		act.setScoreBoard();
		this.setOptionalMessage(s, act);		
	}

	@Override
	public void setLoginRequest(String s) throws RemoteException {
		ActionAllowed act = new ActionAllowed();
		act.setInvalidLogin();
		this.setOptionalMessage(s, act);
	}

	@Override
	public void setMenu(String s) throws RemoteException {
		ActionAllowed act = new ActionAllowed();
		act.setMenu();
		this.setOptionalMessage(s, act);		
	}

	@Override
	public void setInfo(String s) throws RemoteException {
		ActionAllowed act = new ActionAllowed();
		act.setInfo();
		this.setOptionalMessage(s, act);	
	}

	@Override
	public void setBoard(String s) throws RemoteException {
		ActionAllowed act = new ActionAllowed();
		act.setBoard();
		this.setOptionalMessage(s, act);	
	}

	@Override
	public void setUpdateBoard(String s) throws RemoteException {
		ActionAllowed act = new ActionAllowed();
		act.setUpdateBoard();
		this.setOptionalMessage(s, act);	
	}

	@Override
	public void setPlayers(String s) throws RemoteException {
		ActionAllowed act = new ActionAllowed();
		act.setPlayers();
		this.setOptionalMessage(s, act);		
		
	}

	@Override
	public void setPlayersUpdate(String s) throws RemoteException {
		ActionAllowed act = new ActionAllowed();
		act.setUpdatePlayers();
		this.setOptionalMessage(s, act);	
	}

	@Override
	public void setBonusAction(String s) throws RemoteException {
		ActionAllowed act = new ActionAllowed();
		act.setBonusActionRequest();
		this.setOptionalMessage(s, act);		
		
	}

	@Override
	public void setBonusProduction(String s) throws RemoteException {
		ActionAllowed act = new ActionAllowed();
		act.setBonusProductionRequest();
		this.setOptionalMessage(s, act);	
		
	}

	@Override
	public void setBonusHarvest(String s) throws RemoteException {
		ActionAllowed act = new ActionAllowed();
		act.setBonusHarvestRequest();
		this.setOptionalMessage(s, act);		
		
	}

	@Override
	public void setBonusCouncilRequest(String s) throws RemoteException {
		ActionAllowed act = new ActionAllowed();
		act.setBonusCouncilRequest();
		this.setOptionalMessage(s, act);		
		
	}

	@Override
	public void setCostChoiceRequest(String s) throws RemoteException {
		ActionAllowed act = new ActionAllowed();
		act.setCostChoiceRequest();
		this.setOptionalMessage(s, act);	
		
	}

	@Override
	public void setExcommunication(String s) throws RemoteException {
		ActionAllowed act = new ActionAllowed();
		act.setExcommChoiceRequest();
		this.setOptionalMessage(s, act);		
		
	}

	@Override
	public void setLeaderDraft(String s) throws RemoteException {
		ActionAllowed act = new ActionAllowed();
		act.setLeaderDraft();
		this.setOptionalMessage(s, act);		
		
	}

	@Override
	public void setTilesDraft(String s) throws RemoteException {
		ActionAllowed act = new ActionAllowed();
		act.setTilesDraft();
		this.setOptionalMessage(s, act);	
		
	}

	@Override
	public void setLorenzoIlMagnifico(String s) throws RemoteException {
		ActionAllowed act = new ActionAllowed();
		act.setLorenzoIlMagnifico();
		this.setOptionalMessage(s, act);	
		
	}	
	
	@Override
	public boolean poke() throws RemoteException{
		return this.isTerminated;
	}
	
	@Override
	public void setTerminated() throws RemoteException{
		this.isTerminated=true;
	}

	@Override
	public void setFamiliarOffer(String s) throws RemoteException {
		ActionAllowed act = new ActionAllowed();
		act.setFamiliarOffer();
		this.setOptionalMessage(s, act);
	}

	@Override
	public void setEndGame(String s) throws RemoteException {
		ActionAllowed act = new ActionAllowed();
		act.setEndGame();
		this.setOptionalMessage(s, act);
	}
	
}