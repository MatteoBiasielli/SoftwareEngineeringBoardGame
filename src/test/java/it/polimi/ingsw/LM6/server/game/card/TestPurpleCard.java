package it.polimi.ingsw.LM6.server.game.card;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import it.polimi.ingsw.LM6.server.game.Action;
import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.game.bonuses.PermanentBonusMalus;
import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;
import it.polimi.ingsw.LM6.server.game.exceptions.DoubleCostException;
import it.polimi.ingsw.LM6.server.game.exceptions.NotEnoughResourcesException;
import it.polimi.ingsw.LM6.server.game.exceptions.SixCardsException;
import it.polimi.ingsw.LM6.server.users.SocketUser;

public class TestPurpleCard {
	private Player test;
	private SocketUser userFake;
	private PurpleCard card;
	public TestPurpleCard(){
		userFake=new SocketUser(null);
		userFake.setNickname("aaa");
		try {
			this.test= new Player(userFake,4,"102W 104St 105C 103Se 100VP 17MP 100FP"
					,"117","4;","56;","30;","77;74;","100;101","102");
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.card= new PurpleCard("86;Accogliere gli Stranieri;2;4;0;0;0;false;false;0;0;0;0;0;5;0;0;0;false;false;false;0;false;E;0;4".split(";"));
	}
	@Test(expected=it.polimi.ingsw.LM6.server.game.exceptions.SixCardsException.class)
	public void testConditions() throws SixCardsException{
		Action a=new Action(test);
		card.giveCardTo(test);
		card.giveCardTo(test);
		card.giveCardTo(test);
		card.giveCardTo(test);
		card.giveCardTo(test);
		card.giveCardTo(test);
		card.conditions(a);
		
	}
	@Test
	public void testGetFinalBonus(){
		assertTrue(card.getFinalBonus().equals(new ResourceSet(0,0,0,0,0,4,0)));
	}
	@Test
	public void testGiveCardTo(){
		card.giveCardTo(test);
		assertTrue(test.getPurpleCardList().contains(card));
	}
	@Test(expected=it.polimi.ingsw.LM6.server.game.exceptions.NotEnoughResourcesException.class)
	public void testGetCost() throws DoubleCostException, NotEnoughResourcesException{
		Action a=new Action("TOWERACTIONWITHCHOICE;PURPLE;2;BLACK;2;1".split(";"), test, new ResourceSet());
		assertTrue(card.getCost(a).equals(new ResourceSet(4,0,0,0,0,0,0)));
		PurpleCard card2=new PurpleCard("93;Conquista Militare;3;0;0;0;0;false;true;12;6;3;3;3;0;0;0;0;false;false;false;0;false;E;0;7".split(";"));
		assertTrue(card2.getCost(a).equals(new ResourceSet(0,0,0,0,6,0,0)));
		test.getResourceSet().setZero();
		assertTrue(card2.getCost(a).equals(new ResourceSet(0,0,0,0,6,0,0)));
	}
	@Test(expected=it.polimi.ingsw.LM6.server.game.exceptions.DoubleCostException.class)
	public void testGetCost2() throws DoubleCostException, NotEnoughResourcesException{
		Action a=new Action("TOWERACTIONWITHCHOICE;PURPLE;2;BLACK;2;1".split(";"), test, new ResourceSet());
		PurpleCard card2=new PurpleCard("96;Sostegno al Papa;3;3;3;4;0;true;true;10;5;0;0;0;0;0;2;0;false;false;false;0;false;E;0;10".split(";"));
		assertTrue(card2.getCost(a).equals(new ResourceSet(0,0,0,0,5,0,0)));
		test.getResourceSet().setZero();
		assertTrue(card2.getCost(a).equals(new ResourceSet(3,3,4,0,0,0,0)));
		test.acquireResources(new ResourceSet(10,10,10,10,10,10,10));
		a=new Action("TOWERACTIONWITHCHOICE;PURPLE;2;BLACK;2;2".split(";"), test, new ResourceSet());
		assertTrue(card2.getCost(a).equals(new ResourceSet(3,3,4,0,0,0,0)));
		a=new Action("TOWERACTION;PURPLE;2;BLACK;2".split(";"), test, new ResourceSet());
		assertTrue(card2.getCost(a).equals(new ResourceSet(3,3,4,0,0,0,0)));
	}
}
