package it.polimi.ingsw.LM6.server.game.card;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import it.polimi.ingsw.LM6.server.game.Action;
import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.game.bonuses.PermanentBonusMalus;
import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;
import it.polimi.ingsw.LM6.server.game.exceptions.DoubleCostException;
import it.polimi.ingsw.LM6.server.game.exceptions.SixCardsException;
import it.polimi.ingsw.LM6.server.users.SocketUser;

public class TestYellowCard {
	private Player test;
	private SocketUser userFake;
	private YellowCard card;
	public TestYellowCard(){
		userFake=new SocketUser(null);
		userFake.setNickname("aaa");
		try {
			this.test= new Player(userFake,4,"102W 104St 105C 103Se 100VP 17MP 100FP"
					,"117","4;","56;","30;","77;74;","100;101","102");
		} catch (IOException e) {
			e.printStackTrace();
		}
		card= new YellowCard("36;Gilda degli Scultori;2;0;4;0;0;6;0;5;false;true;false;0;1;0;0;0;0;0;0;0;0;0;3;0;false;N;true;0;3;0;0;0;0;0;0;0;0;0;7".split(";"));
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
	public void testProduce(){
		Action a=new Action(test);
		ResourceSet cost=new ResourceSet();
		assertTrue(card.produce(a, cost, 1).getResourceSetFor(test).equals(new ResourceSet(0,0,0,0,0,3,0)));
		assertTrue(cost.equals(new ResourceSet(0,1,0,0,0,0,0)));
		cost=new ResourceSet();
		assertTrue(card.produce(a, cost, 2).getResourceSetFor(test).equals(new ResourceSet(0,0,0,0,0,7,0)));
		assertTrue(cost.equals(new ResourceSet(0,3,0,0,0,0,0)));
		YellowCard card2=new YellowCard("37;Gilda dei Costruttori;2;1;2;0;0;4;0;4;false;true;false;1;1;0;1;0;0;0;0;0;0;0;6;0;false;N;false;0;0;0;0;0;0;0;0;0;0;0;0".split(";"));
		cost=new ResourceSet();
		assertTrue(card2.produce(a, cost, 2).getResourceSetFor(test).equals(new ResourceSet(0,0,0,0,0,6,0)));
		assertTrue(cost.equals(new ResourceSet(1,1,0,1,0,0,0)));
	}
	@Test
	public void testCanProduce(){
		assertFalse(card.canProduce(4));
		assertTrue(card.canProduce(5));
	}
	@Test
	public void testGiveCardTo(){
		card.giveCardTo(test);
		assertTrue(test.getYellowCardList().contains(card));
	}
	@Test(expected=it.polimi.ingsw.LM6.server.game.exceptions.DoubleCostException.class)
	public void testGetCost() throws DoubleCostException{
		test.acquirePermBonus(new PermanentBonusMalus(0,0,0,0,0,0,0,0,0,0,"woodOrStoneDiscountOnYellow"));
		Action a=new Action("TOWERACTIONWITHCHOICE;YELLOW;2;BLACK;2;1".split(";"), test, new ResourceSet());
		assertTrue(card.getCost(a).equals(new ResourceSet(0,4,0,0,0,0,0)));
		a=new Action("TOWERACTIONWITHCHOICE;YELLOW;2;BLACK;2;2".split(";"), test, new ResourceSet());
		assertTrue(card.getCost(a).equals(new ResourceSet(0,3,0,0,0,0,0)));
		a=new Action("TOWERACTION;YELLOW;2;BLACK;2".split(";"), test, new ResourceSet());
		assertTrue(card.getCost(a).equals(new ResourceSet(0,3,0,0,0,0,0)));
		YellowCard card2=new YellowCard("37;Gilda dei Costruttori;2;1;0;0;0;4;0;4;false;true;false;1;1;0;1;0;0;0;0;0;0;0;6;0;false;N;false;0;0;0;0;0;0;0;0;0;0;0;0".split(";"));
		assertTrue(card2.getCost(a).equals(new ResourceSet(0,0,0,0,0,0,0)));
		card2=new YellowCard("37;Gilda dei Costruttori;2;0;0;3;0;4;0;4;false;true;false;1;1;0;1;0;0;0;0;0;0;0;6;0;false;N;false;0;0;0;0;0;0;0;0;0;0;0;0".split(";"));
		assertTrue(card2.getCost(a).equals(new ResourceSet(0,0,3,0,0,0,0)));
		card2=new YellowCard("37;Gilda dei Costruttori;2;1;2;0;0;4;0;4;false;true;false;1;1;0;1;0;0;0;0;0;0;0;6;0;false;N;false;0;0;0;0;0;0;0;0;0;0;0;0".split(";"));
		assertTrue(card2.getCost(a).equals(new ResourceSet(0,0,0,0,0,0,0)));
	}
}
