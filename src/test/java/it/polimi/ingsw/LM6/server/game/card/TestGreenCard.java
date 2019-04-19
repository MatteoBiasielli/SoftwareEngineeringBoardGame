package it.polimi.ingsw.LM6.server.game.card;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import it.polimi.ingsw.LM6.server.game.Action;
import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;
import it.polimi.ingsw.LM6.server.game.exceptions.NotEnoughMilitaryPointsException;
import it.polimi.ingsw.LM6.server.game.exceptions.SixCardsException;
import it.polimi.ingsw.LM6.server.users.SocketUser;

public class TestGreenCard {
	private Player test;
	private SocketUser userFake;
	private GreenCard card;
	public TestGreenCard(){
		userFake=new SocketUser(null);
		userFake.setNickname("aaa");
		try {
			this.test= new Player(userFake,4,"102W 104St 105C 103Se 100VP 17MP 100FP"
					,"117","4;","56;","30;","77;74;","100;101","102");
		} catch (IOException e) {
			e.printStackTrace();
		}
		card= new GreenCard("2;Bosco;1;1;0;0;0;0;0;0;0;2;1;0;0;0;0;0;0;0".split(";"));
	}
	@Test(expected=it.polimi.ingsw.LM6.server.game.exceptions.NotEnoughMilitaryPointsException.class)
	public void testConditions() throws NotEnoughMilitaryPointsException, SixCardsException{
		Action a=new Action(test);
		card.giveCardTo(test);
		card.conditions(a);
		card.giveCardTo(test);
		card.conditions(a);
		card.giveCardTo(test);
		card.conditions(a);
		card.giveCardTo(test);
		card.conditions(a);
		card.giveCardTo(test);
		card.conditions(a);
		
	}
	@Test(expected=it.polimi.ingsw.LM6.server.game.exceptions.SixCardsException.class)
	public void testConditions2() throws NotEnoughMilitaryPointsException, SixCardsException{
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
	public void testCanHarvest(){
		assertTrue(!card.canHarvest(1));
		assertTrue(card.canHarvest(2));
		assertTrue(card.canHarvest(3));
	}
	@Test
	public void testHarvest(){
		assertTrue(card.harvest().getResourceSetFor(test).equals(new ResourceSet(1,0,0,0,0,0,0)));
	}
}
