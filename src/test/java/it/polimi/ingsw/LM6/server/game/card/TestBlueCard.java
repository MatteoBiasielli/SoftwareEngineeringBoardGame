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

public class TestBlueCard {
	private Player test;
	private SocketUser userFake;
	private BlueCard card;
	public TestBlueCard(){
		userFake=new SocketUser(null);
		userFake.setNickname("aaa");
		try {
			this.test= new Player(userFake,4,"102W 104St 105C 103Se 100VP 17MP 100FP"
					,"117","4;","56;","30;","77;74;","100;101","102");
		} catch (IOException e) {
			e.printStackTrace();
		}
		card= new BlueCard("55;Predicatore;1;2;0;4;0;0;false;false;N;false;N;0;0;0;0;false;false;0;0;0;0;0;0;0;noActionSpaceBonus".split(";"));
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
	public void testGetCost(){
		assertTrue(card.getCost().equals(new ResourceSet(0,0,2,0,0,0,0)));
	}
}
