package it.polimi.ingsw.LM6.client;

import static org.junit.Assert.*;

import java.rmi.RemoteException;
import java.util.Vector;

import org.junit.Test;

public class TestUserOperationStubImpl {
	
	UserOperationStubImpl stub;
	
	@Test
	public void testSetSetupMenu(){
		Vector<ActionAllowed> vect = new Vector<ActionAllowed>();
		try {
			this.stub = new UserOperationStubImpl(new Waiter(), vect);
			this.stub.setSetUpMenu("ciao");
			assertEquals("SETUPMENU;ciao",vect.get(0).buildMessage());
		} catch (RemoteException e) {
			// it never happens
		}
	}
	
	@Test
	public void testMenu(){
		Vector<ActionAllowed> vect = new Vector<ActionAllowed>();
		try {
			this.stub = new UserOperationStubImpl(new Waiter(), vect);
			this.stub.setMenu("ciao");
			assertEquals("MENU;ciao",vect.get(0).buildMessage());
		} catch (RemoteException e) {
			// it never happens
		}
	}
	
	@Test
	public void testSetScoreboard(){
		Vector<ActionAllowed> vect = new Vector<ActionAllowed>();
		try {
			this.stub = new UserOperationStubImpl(new Waiter(), vect);
			this.stub.setScoreboard("ciao");
			assertEquals("SCOREBOARD;ciao",vect.get(0).buildMessage());
		} catch (RemoteException e) {
			// it never happens
		}
	}
	
	@Test
	public void testSetLoginRequest(){
		Vector<ActionAllowed> vect = new Vector<ActionAllowed>();
		try {
			this.stub = new UserOperationStubImpl(new Waiter(), vect);
			this.stub.setLoginRequest("ciao");
			assertEquals("INVALIDLOGIN;ciao",vect.get(0).buildMessage());
		} catch (RemoteException e) {
			// it never happens
		}
	}
	
	@Test
	public void testSetInfo(){
		Vector<ActionAllowed> vect = new Vector<ActionAllowed>();
		try {
			this.stub = new UserOperationStubImpl(new Waiter(), vect);
			this.stub.setInfo("ciao");
			assertEquals("INFO;ciao",vect.get(0).buildMessage());
		} catch (RemoteException e) {
			// it never happens
		}
	}
	
	@Test
	public void testSetBoard(){
		Vector<ActionAllowed> vect = new Vector<ActionAllowed>();
		try {
			this.stub = new UserOperationStubImpl(new Waiter(), vect);
			this.stub.setBoard("ciao");
			assertEquals("BOARD;ciao",vect.get(0).buildMessage());
		} catch (RemoteException e) {
			// it never happens
		}
	}
	
	@Test
	public void testUpdateBoard(){
		Vector<ActionAllowed> vect = new Vector<ActionAllowed>();
		try {
			this.stub = new UserOperationStubImpl(new Waiter(), vect);
			this.stub.setUpdateBoard("ciao");
			assertEquals("UPDATEBOARD;ciao",vect.get(0).buildMessage());
		} catch (RemoteException e) {
			// it never happens
		}
	}
	
	@Test
	public void testSetPlayers(){
		Vector<ActionAllowed> vect = new Vector<ActionAllowed>();
		try {
			this.stub = new UserOperationStubImpl(new Waiter(), vect);
			this.stub.setPlayers("ciao");
			assertEquals("PLAYERS;ciao",vect.get(0).buildMessage());
		} catch (RemoteException e) {
			// it never happens
		}
	}
	
	@Test
	public void testSetPlayersUpdate(){
		Vector<ActionAllowed> vect = new Vector<ActionAllowed>();
		try {
			this.stub = new UserOperationStubImpl(new Waiter(), vect);
			this.stub.setPlayersUpdate("ciao");
			assertEquals("UPDATEPLAYERS;ciao",vect.get(0).buildMessage());
		} catch (RemoteException e) {
			// it never happens
		}
	}
	
	@Test
	public void testSetBonusAction(){
		Vector<ActionAllowed> vect = new Vector<ActionAllowed>();
		try {
			this.stub = new UserOperationStubImpl(new Waiter(), vect);
			this.stub.setBonusAction("ciao");
			assertEquals("BONUSACTIONREQUEST;ciao",vect.get(0).buildMessage());
		} catch (RemoteException e) {
			// it never happens
		}
	}
	
	@Test
	public void testBonusProduction(){
		Vector<ActionAllowed> vect = new Vector<ActionAllowed>();
		try {
			this.stub = new UserOperationStubImpl(new Waiter(), vect);
			this.stub.setBonusProduction("ciao");
			assertEquals("BONUSPRODUCTIONREQUEST;ciao",vect.get(0).buildMessage());
		} catch (RemoteException e) {
			// it never happens
		}
	}
	
	@Test
	public void testSetBonusHarvest(){
		Vector<ActionAllowed> vect = new Vector<ActionAllowed>();
		try {
			this.stub = new UserOperationStubImpl(new Waiter(), vect);
			this.stub.setBonusHarvest("ciao");
			assertEquals("BONUSHARVESTREQUEST;ciao",vect.get(0).buildMessage());
		} catch (RemoteException e) {
			// it never happens
		}
	}
	
	@Test
	public void testSetCouncilRequest(){
		Vector<ActionAllowed> vect = new Vector<ActionAllowed>();
		try {
			this.stub = new UserOperationStubImpl(new Waiter(), vect);
			this.stub.setBonusCouncilRequest("ciao");
			assertEquals("BONUSCOUNCILREQUEST;ciao",vect.get(0).buildMessage());
		} catch (RemoteException e) {
			// it never happens
		}
	}
	
	@Test
	public void testSetCostChoice(){
		Vector<ActionAllowed> vect = new Vector<ActionAllowed>();
		try {
			this.stub = new UserOperationStubImpl(new Waiter(), vect);
			this.stub.setCostChoiceRequest("ciao");
			assertEquals("COSTCHOICEREQUEST;ciao",vect.get(0).buildMessage());
		} catch (RemoteException e) {
			// it never happens
		}
	}
	
	@Test
	public void testSetExcommuncationRequest(){
		Vector<ActionAllowed> vect = new Vector<ActionAllowed>();
		try {
			this.stub = new UserOperationStubImpl(new Waiter(), vect);
			this.stub.setExcommunication("ciao");
			assertEquals("EXCOMMCHOICEREQUEST;ciao",vect.get(0).buildMessage());
		} catch (RemoteException e) {
			// it never happens
		}
	}
	
	@Test
	public void testSetLeaderDraft(){
		Vector<ActionAllowed> vect = new Vector<ActionAllowed>();
		try {
			this.stub = new UserOperationStubImpl(new Waiter(), vect);
			this.stub.setLeaderDraft("ciao");
			assertEquals("LEADERDRAFT;ciao",vect.get(0).buildMessage());
		} catch (RemoteException e) {
			// it never happens
		}
	}
	
	@Test
	public void testSetTilesDraft(){
		Vector<ActionAllowed> vect = new Vector<ActionAllowed>();
		try {
			this.stub = new UserOperationStubImpl(new Waiter(), vect);
			this.stub.setTilesDraft("ciao");
			assertEquals("TILESDRAFT;ciao",vect.get(0).buildMessage());
		} catch (RemoteException e) {
			// it never happens
		}
	}
	
	@Test
	public void testSetLorenzoIlMagnifico(){
		Vector<ActionAllowed> vect = new Vector<ActionAllowed>();
		try {
			this.stub = new UserOperationStubImpl(new Waiter(), vect);
			this.stub.setLorenzoIlMagnifico("ciao");
			assertEquals("LORENZOILMAGNIFICO;ciao",vect.get(0).buildMessage());
		} catch (RemoteException e) {
			// it never happens
		}
	}
	
	@Test
	public void testSetFamiliarOffer(){
		Vector<ActionAllowed> vect = new Vector<ActionAllowed>();
		try {
			this.stub = new UserOperationStubImpl(new Waiter(), vect);
			this.stub.setFamiliarOffer("ciao");
			assertEquals("FAMOFFERREQUEST;ciao",vect.get(0).buildMessage());
		} catch (RemoteException e) {
			// it never happens
		}
	}
	
	@Test
	public void testPoke(){
		Vector<ActionAllowed> vect = new Vector<ActionAllowed>();
		try {
			this.stub = new UserOperationStubImpl(new Waiter(), vect);
			assertFalse(this.stub.poke());
			this.stub.setTerminated();
			assertTrue(this.stub.poke());
		} catch (RemoteException e) {
			// it never happens
		}
	}
}
