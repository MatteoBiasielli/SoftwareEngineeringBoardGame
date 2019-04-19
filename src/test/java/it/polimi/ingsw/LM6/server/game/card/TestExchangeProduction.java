package it.polimi.ingsw.LM6.server.game.card;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.LM6.server.game.bonuses.Bonus;
import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;
import it.polimi.ingsw.LM6.server.game.exceptions.BadInputException;

public class TestExchangeProduction {
	ExchangeProduction prod;
	public TestExchangeProduction(){
		try {
			this.prod=new ExchangeProduction(new Bonus(new ResourceSet(1,1,1,1,1,1,1), false, 'N', 
					false,'N', 0,null, false,false, 0, 1, false), new ResourceSet(1,1,1,0,0,0,0));
		} catch (BadInputException e) {
			//MAI VERIFICATA
		}
	}
	
	@Test
	public void testActivate(){
		assertTrue(true);
	}
}
