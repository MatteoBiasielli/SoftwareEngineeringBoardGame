package it.polimi.ingsw.LM6.server.game.board;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.LM6.server.game.LorenzoIlMagnifico;
import it.polimi.ingsw.LM6.server.game.card.ExcommunicationCard;

public class TestBoard {
	Board test=new Board(new LorenzoIlMagnifico(false,5,"0"));
	@Test
	public void TestConstructor(){
		Board test=new Board(new LorenzoIlMagnifico(false,5,"0"));
		
		assertTrue(test.getTower(0).getCard().length==24);
		assertTrue(test.getTower(1).getCard().length==24);
		assertTrue(test.getTower(2).getCard().length==24);
		assertTrue(test.getTower(3).getCard().length==24);
	}
	@Test
	public void testGetFaithPointsBonus(){
		assertTrue(test.getFaithPointsBonus(0).isEmpty());
		assertTrue(!test.getFaithPointsBonus(15).isEmpty());
		assertTrue(!test.getFaithPointsBonus(16).isEmpty());
	}
	@Test
	public void testExcomm(){
		assertTrue(test.getExcommunicationCard(1) instanceof ExcommunicationCard);
		assertTrue(test.getExcommunicationCard(2) instanceof ExcommunicationCard);
		assertTrue(test.getExcommunicationCard(3) instanceof ExcommunicationCard);
	}
	@Test
	public void testGetTower(){
		assertTrue(test.getTower(0).getColor()==TowerCardColor.GREEN);
		assertTrue(test.getTower(1).getColor()==TowerCardColor.BLUE);
		assertTrue(test.getTower(2).getColor()==TowerCardColor.YELLOW);
		assertTrue(test.getTower(3).getColor()==TowerCardColor.PURPLE);
	}
	@Test
	public void testGetPlayersInCouncil(){
		assertTrue(test.getPlayersInCouncil().size()==0);
	}
	@Test
	public void testToString(){
		assertTrue(test.toString().split(";").length==10);
	}
	@Test
	public void testNewTurn(){
		test.newTurn(2);
		assertTrue(test.getPlayersInCouncil().size()==0);
		assertFalse(test.getTower(0).isOccupied());
		assertFalse(test.getTower(1).isOccupied());
		assertFalse(test.getTower(2).isOccupied());
		assertFalse(test.getTower(3).isOccupied());
	}
}
