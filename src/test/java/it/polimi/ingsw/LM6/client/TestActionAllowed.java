package it.polimi.ingsw.LM6.client;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestActionAllowed {
	
	ActionAllowed act = new ActionAllowed();
	
	@Test
	public void testSetSetupMenu(){
		act.setSetupMenu();
		act.setOptionalInfo("a");
		String s = act.buildMessage();
		assertEquals(s,"SETUPMENU;a");
	}
	
	@Test
	public void testBoard(){
		act.setBoard();
		act.setOptionalInfo("a");
		String s = act.buildMessage();
		assertEquals(s,"BOARD;a");
	}
	
	@Test
	public void testInvalidLogin(){
		act.setInvalidLogin();
		act.setOptionalInfo("a");
		String s = act.buildMessage();
		assertEquals(s,"INVALIDLOGIN;a");
	}
	
	@Test
	public void testBonusActionRequest(){
		act.setBonusActionRequest();
		act.setOptionalInfo("a");
		String s = act.buildMessage();
		assertEquals(s,"BONUSACTIONREQUEST;a");
	}
	
	@Test
	public void testSetBonusCouncilRequest(){
		act.setBonusCouncilRequest();
		act.setOptionalInfo("a");
		String s = act.buildMessage();
		assertEquals(s,"BONUSCOUNCILREQUEST;a");
	}
	
	@Test
	public void testSetBonusHarvestRequest(){
		act.setBonusHarvestRequest();
		act.setOptionalInfo("a");
		String s = act.buildMessage();
		assertEquals(s,"BONUSHARVESTREQUEST;a");
	}
	
	@Test
	public void testSetBonusProductionRequest(){
		act.setBonusProductionRequest();
		act.setOptionalInfo("a");
		String s = act.buildMessage();
		assertEquals(s,"BONUSPRODUCTIONREQUEST;a");
	}
	
	@Test
	public void testSetCostChoiceRequest(){
		act.setCostChoiceRequest();
		act.setOptionalInfo("a");
		String s = act.buildMessage();
		assertEquals(s,"COSTCHOICEREQUEST;a");
	}
	
	@Test
	public void testSetExcommunication(){
		act.setExcommChoiceRequest();
		act.setOptionalInfo("a");
		String s = act.buildMessage();
		assertEquals(s,"EXCOMMCHOICEREQUEST;a");
	}
	
	@Test
	public void testFamiliarOffer(){
		act.setFamiliarOffer();
		act.setOptionalInfo("a");
		String s = act.buildMessage();
		assertEquals(s,"FAMOFFERREQUEST;a");
	}
	
	@Test
	public void testInfo(){
		act.setInfo();
		act.setOptionalInfo("a");
		String s = act.buildMessage();
		assertEquals(s,"INFO;a");
	}
	
	@Test
	public void testLeaderDraft(){
		act.setLeaderDraft();
		act.setOptionalInfo("a");
		String s = act.buildMessage();
		assertEquals(s,"LEADERDRAFT;a");
	}
	
	@Test
	public void testLorenzoIlMagnifico(){
		act.setLorenzoIlMagnifico();
		act.setOptionalInfo("a");
		String s = act.buildMessage();
		assertEquals(s,"LORENZOILMAGNIFICO;a");
	}
	
	@Test
	public void testMenu(){
		act.setMenu();
		act.setOptionalInfo("a");
		String s = act.buildMessage();
		assertEquals(s,"MENU;a");
	}
	
	@Test
	public void testPlayers(){
		act.setPlayers();
		act.setOptionalInfo("a");
		String s = act.buildMessage();
		assertEquals(s,"PLAYERS;a");
	}
	
	@Test
	public void testScoreBoard(){
		act.setScoreBoard();
		act.setOptionalInfo("a");
		String s = act.buildMessage();
		assertEquals(s,"SCOREBOARD;a");
	}
	
	@Test
	public void testTilesDraft(){
		act.setTilesDraft();
		act.setOptionalInfo("a");
		String s = act.buildMessage();
		assertEquals(s,"TILESDRAFT;a");
	}
	
	
	@Test
	public void testUpdateBoard(){
		act.setUpdateBoard();
		act.setOptionalInfo("a");
		String s = act.buildMessage();
		assertEquals(s,"UPDATEBOARD;a");
	}
	
	
	@Test
	public void testUpdatePlayers(){
		act.setUpdatePlayers();
		act.setOptionalInfo("a");
		String s = act.buildMessage();
		assertEquals(s,"UPDATEPLAYERS;a");
	}
	
}
