package it.polimi.ingsw.LM6.client;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestClientController {
	
	ClientController controller;
	CLIHandler view= new CLIHandler();
	
	public TestClientController(){
		this.controller= new ClientController(this.view);
	}
	
	@Test
	public void testCheckDifferentTwo(){
		boolean tmp;
		tmp = this.controller.checkDifferent2("1", "3");
		assertEquals(tmp, true);
		tmp = this.controller.checkDifferent2("0", "0");
		assertEquals(tmp,false);
	}
	
	@Test
	public void testCheckForcedMenuInput(){
		boolean tmp;
		tmp = this.controller.checkForcedMenuInput("1");
		assertEquals(tmp,true);
		tmp = this.controller.checkForcedMenuInput("a");
		assertEquals(tmp,false);
	}
	
	@Test 
	public void testCheckContent(){
		boolean tmp;
		tmp = this.controller.checkContent("");
		assertEquals(tmp,false);
		tmp = this.controller.checkContent(" ");
		assertEquals(tmp,false);
		tmp = this.controller.checkContent("jdkfu");
		assertEquals(tmp,true);
	}
	
	@Test
	public void testCheckInt(){
		boolean tmp;
		tmp = this.controller.checkInt("a");
		assertEquals(tmp,false);
		tmp = this.controller.checkInt("");
		assertEquals(tmp,false);
		tmp = this.controller.checkInt("12448");
		assertEquals(tmp,true);
	}
	
	@Test
	public void testCheckFamilyColour(){
		boolean tmp;
		tmp = this.controller.checkFamilyColour("");
		assertEquals(tmp,false);
		tmp = this.controller.checkFamilyColour("ewfqh");
		assertEquals(tmp,false);
		tmp = this.controller.checkFamilyColour("BLACK");
		assertEquals(tmp,true);
	}
	
	@Test
	public void testCheckTowerColour(){
		boolean tmp;
		tmp = this.controller.checkTowerColour("");
		assertEquals(tmp,false);
		tmp = this.controller.checkTowerColour("qwsnikq");
		assertEquals(tmp,false);
		tmp = this.controller.checkTowerColour("GREEN");
		assertEquals(tmp,true);
	}
	
	@Test
	public void  testCheckMarketInput(){
		boolean tmp;
		tmp = this.controller.checkMarketInput("");
		assertEquals(tmp,false);
		tmp = this.controller.checkMarketInput("a");
		assertEquals(tmp,false);
		tmp = this.controller.checkMarketInput("3");
		assertEquals(tmp,true);
	}
	
	@Test
	public void testCheckMenuChosenNumber(){
		boolean tmp;
		String[] a = {"1","1","1","1","1","1","1","1","1"};
		tmp = this.controller.checkMenuChosenNumber("1", a);
		assertEquals(tmp,true);
		tmp = this.controller.checkMenuChosenNumber("s", a);
		assertEquals(tmp,false);
		tmp = this.controller.checkMenuChosenNumber("7", a);
		assertEquals(tmp,false);
		tmp = this.controller.checkMenuChosenNumber("", a);
		assertEquals(tmp,false);
	}
	
	@Test
	public void testCheckProductionEffectNumber(){
		boolean tmp;
		tmp = this.controller.checkProductionEffectNumber("");
		assertEquals(tmp,false);
		tmp = this.controller.checkProductionEffectNumber("ssfre");
		assertEquals(tmp,false);
		tmp = this.controller.checkProductionEffectNumber("0");
		assertEquals(tmp,true);
	}
	
	@Test
	public void testCheckTowerFloor(){
		boolean tmp;
		tmp = this.controller.checkTowerFloor("");
		assertEquals(tmp,false);
		tmp = this.controller.checkTowerFloor("a");
		assertEquals(tmp,false);
		tmp = this.controller.checkTowerFloor("3");
		assertEquals(tmp,true);
	}
	
	@Test
	public void testEncapsulateRenounciation(){
		String a;
		a = this.controller.encapsulateRenounciation("aaa");
		assertEquals(a,"BONUSRENOUNCE;aaa");
	}
	
	@Test
	public void testEncapsulatePlayerRequest(){
		String a;
		a = this.controller.encapsulatePlayersRequest();
		assertEquals(a,"SHOWPLAYERSREQUEST");
	}
	
	@Test
	public void testEncapsulateTableRequest(){
		String a;
		a = this.controller.encapsulateTableRequest();
		assertEquals(a,"SHOWTABLEREQUEST");
	}
	
	@Test
	public void testEncapsulatePassTurn(){
		String a;
		a = this.controller.encapsulatePassTurn();
		assertEquals(a,"PASS");
	}
	
}
