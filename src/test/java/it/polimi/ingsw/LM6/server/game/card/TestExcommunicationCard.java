package it.polimi.ingsw.LM6.server.game.card;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;
import it.polimi.ingsw.LM6.server.users.SocketUser;

public class TestExcommunicationCard {
	private Player test;
	private SocketUser userFake;
	private ExcommunicationCard card;
	public TestExcommunicationCard(){
		userFake=new SocketUser(null);
		userFake.setNickname("aaa");
		try {
			this.test= new Player(userFake,4,"102W 104St 105C 103Se 100VP 17MP 100FP"
					,"117","4;","56;","30;","77;74;","100;101","102");
		} catch (IOException e) {
			e.printStackTrace();
		}
		card= new ExcommunicationCard("1;0;0;0;0;0;0;0;0;0;0;oneCoinMalus;description4;125".split(";"));
	
	}
	@Test(expected=it.polimi.ingsw.LM6.server.game.exceptions.BadInputException.class)
	public void testCostOfExcommunication(){
		assertTrue(card.costOfExcommunication().equals(new ResourceSet(0,0,0,0,0,0,3)));
		card= new ExcommunicationCard("2;0;0;0;0;0;0;0;0;0;0;oneCoinMalus;description4;125".split(";"));
		assertTrue(card.costOfExcommunication().equals(new ResourceSet(0,0,0,0,0,0,4)));
		card= new ExcommunicationCard("3;0;0;0;0;0;0;0;0;0;0;oneCoinMalus;description4;125".split(";"));
		assertTrue(card.costOfExcommunication().equals(new ResourceSet(0,0,0,0,0,0,5)));
		card= new ExcommunicationCard("4;0;0;0;0;0;0;0;0;0;0;oneCoinMalus;description4;125".split(";"));
		assertTrue(card.costOfExcommunication().equals(new ResourceSet(0,0,0,0,0,0,5)));
	}
	@Test
	public void testGetNumber(){
		assertTrue(card.getNumber()==125);
	}
	@Test
	public void testGetDescripton(){
		assertTrue(card.getDescription().equals("description4"));
	}
	@Test
	public void testExcommunicatePlayer(){
		int a=test.getPermanentBonusMalus().getEffects().size();
		card.excommunicatePlayer(test);
		assertTrue(a+1==test.getPermanentBonusMalus().getEffects().size());
	}
	@Test
	public void testToString(){
		card.excommunicatePlayer(test);
		assertTrue(card.toString().equals("oneCoinMalus  !125!aaa!"));
	}
}
