package it.polimi.ingsw.LM6.server.network;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.LM6.server.network.exception.InvalidFormatException;

public class TestSetupMessage {
	SetupMessage message;
	
	/**	@author Emilio
	 */
	@Test
	public void testBuilders_HeaderCheckers(){
		String expected;
		
		//Invalid login builder test.
		expected = "INVALIDLOGIN;Lala; ";
		this.message = SetupMessage.buildInvalidLogin("Lala");
		assertTrue(message.isInvalidLogin());
		assertTrue(expected.equals(message.toString()));
		this.message = null;
		
		//Login request builder success test.
		expected = "LOGINREQUEST;Bl2a;N65b";
		this.message = SetupMessage.buildLoginRequest("Bl2a", "Bl2a");
		assertTrue(message.isLoginRequest());
		assertTrue(expected.equals(message.toString()));
		this.message = null;
		
		//Login request builder fail test (nickname -> invalid character).
		try{
			this.message = SetupMessage.buildLoginRequest("bl.a", "bla");
		} catch(InvalidFormatException e){}
		assertTrue(this.message == null);
		this.message = null;
		
		//Login request builder fail test (nickname -> invalid length).
		try{
			this.message = SetupMessage.buildLoginRequest("blablablablablablabla1", "bla");
		} catch(InvalidFormatException e){}
		assertTrue(this.message == null);
		this.message = null;
		
		//Login request builder fail test (password -> invalid character).
		try{
			this.message = SetupMessage.buildLoginRequest("bla", "bl.a");
		} catch(InvalidFormatException e){}
			assertTrue(this.message == null);
		this.message = null;
		
		//Login request builder fail test (password -> invalid length).
		try{
			this.message = SetupMessage.buildLoginRequest("bla", "blablablablablablabla1");
		} catch(InvalidFormatException e){}
			assertTrue(this.message == null);
		this.message = null;
		
		//Logout request builder test.
		expected = "LOGOUTREQUEST;pupu; ";
		this.message = SetupMessage.buildLogoutRequest("pupu");
		assertTrue(message.isLogoutRequest());
		assertTrue(expected.equals(message.toString()));
		this.message = null;
		
		//Setup menu builder test.
		expected = "SETUPMENU;frufru;yeeee";
		this.message = SetupMessage.buildSetupMenu("frufru", "yeeee");
		assertTrue(message.isSetupMenu());
		assertTrue(expected.equals(message.toString()));
		this.message = null;
		
		//Show record builder test.
		expected = "SHOWRECORD;a; ";
		this.message = SetupMessage.buildShowRecord("a");
		assertTrue(message.isShowRecord());
		assertTrue(expected.equals(message.toString()));
		this.message = null;
		
		//Show scoreboard builder test.
		expected = "SHOWSCOREBOARD;1;2";
		this.message = SetupMessage.buildShowScoreboard(1, 2);
		assertTrue(message.isShowScoreboard());
		assertTrue(expected.equals(message.toString()));
		this.message = null;
		
		//Start new game builder simple rules test.
		expected = "STARTNEWGAME;0;4";
		this.message = SetupMessage.buildStartNewGame(false, 4);
		assertTrue(message.isStartNewGame());
		assertTrue(expected.equals(message.toString()));
		this.message = null;
		
		//Start new game builder advanced rules test.
		expected = "STARTNEWGAME;1;4";
		this.message = SetupMessage.buildStartNewGame(true, 4);
		assertTrue(expected.equals(message.toString()));
		this.message = null;
	}
	
	/**	@author Emilio
	 */
	@Test
	public void testToString(){
		this.message = SetupMessage.buildLoginRequest("fefad", "calimero");
		String expected = "LOGINREQUEST;fefad;ovojqt5s";
		assertEquals(expected, message.toString());
	}
}
