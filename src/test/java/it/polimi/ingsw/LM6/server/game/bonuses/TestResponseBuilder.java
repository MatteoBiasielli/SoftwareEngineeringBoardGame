package it.polimi.ingsw.LM6.server.game.bonuses;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.game.ResponseBuilder;
import it.polimi.ingsw.LM6.server.users.SocketUser;

public class TestResponseBuilder {
	
	@Test
	public void testBuildActionResponse(){
		Bonus b = new Bonus( null, false, 'G', true, 'G', 1, null, false, false, 0, 0, false);
		assertEquals(ResponseBuilder.buildBonusActionResponse(b),"BONUSACTIONREQUEST;You got a green bonus action of value 1;GREEN");
	}
	

	@Test
	public void testBuildActionResponse1(){
		Bonus b = new Bonus( null, false, 'G', true, 'P', 1, null, false, false, 0, 0, false);
		assertEquals(ResponseBuilder.buildBonusActionResponse(b),"BONUSACTIONREQUEST;You got a purple bonus action of value 1;PURPLE");
	}
	
	@Test
	public void testBuildActionResponse2(){
		Bonus b = new Bonus( null, false, 'G', true, 'B', 1, null, false, false, 0, 0, false);
		assertEquals(ResponseBuilder.buildBonusActionResponse(b),"BONUSACTIONREQUEST;You got a blue bonus action of value 1;BLUE");
	}
	
	@Test
	public void testBuildActionResponse3(){
		Bonus b = new Bonus( null, false, 'G', true, 'Y', 1, null, false, false, 0, 0, false);
		assertEquals(ResponseBuilder.buildBonusActionResponse(b),"BONUSACTIONREQUEST;You got a yellow bonus action of value 1;YELLOW");
	}
	
	@Test
	public void testBuildActionResponse4(){
		Bonus b = new Bonus( null, false, 'G', true, 'E', 1, null, false, false, 0, 0, false);
		assertEquals(ResponseBuilder.buildBonusActionResponse(b),"BONUSACTIONREQUEST;You got a bonus action of value 1;EVERY");
	}
	
	@Test
	public void testBuildProductionResponse() throws IOException{
		Player p1= new Player(new SocketUser(null),4,"102W 104St 105C 103Se 100VP 100MP 100FP"
				,"117","4;","56;","30;","77;74;","116;100;101","102");
		Bonus b = new Bonus( null, false, ' ', false, ' ', 0, null, true, false, 3, 0, false);
		assertEquals(ResponseBuilder.buildBonusProductionResponse(p1, b),"BONUSPRODUCTIONREQUEST;You got a bonus production of value 3;1");
	}
	
	@Test
	public void testBuildHarvestResponse(){
		Bonus b = new Bonus( null, false, ' ', false, ' ', 1, null, false, true, 5, 0, false);
		assertEquals(ResponseBuilder.buildBonusHarvestResponse(b),"BONUSHARVESTREQUEST;You got a bonus harvest of value 5");
	}
	
	@Test
	public void testBuildCouncilResponse(){
		Bonus b = new Bonus( null, false, ' ', false, ' ', 0, null, false, false, 0, 6, true);
		assertEquals(ResponseBuilder.buildBonusCouncilResponse(b),"BONUSCOUNCILREQUEST;You got three different council privileges.;3");
		
		b = new Bonus( null, false, ' ', false, ' ', 0, null, false, false, 0, 6, false);
		assertEquals(ResponseBuilder.buildBonusCouncilResponse(b),"BONUSCOUNCILREQUEST;You got a council privilege.;1");
		
		b = new Bonus( null, false, ' ', false, ' ', 0, null, false, false, 0, 2, true);
		assertEquals(ResponseBuilder.buildBonusCouncilResponse(b),"BONUSCOUNCILREQUEST;You got two different council privileges.;2");
		
		b = new Bonus( null, false, ' ', false, ' ', 0, null, false, false, 0, 1, false);
		assertEquals(ResponseBuilder.buildBonusCouncilResponse(b),"BONUSCOUNCILREQUEST;You got a council privilege.;1");
	}
	
}
