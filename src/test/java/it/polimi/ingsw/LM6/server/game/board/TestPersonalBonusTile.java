package it.polimi.ingsw.LM6.server.game.board;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;

public class TestPersonalBonusTile {
	@Test
	public void testGetNumber(){
		PersonalBonusTile pbt=new PersonalBonusTile("1;1;0;1;0;0;0;0;0;2;0;1;0;0;117");
		assertTrue(pbt.getNumber()==117);
	}
	@Test
	public void testGetHarvestSet(){
		PersonalBonusTile pbt=new PersonalBonusTile("1;1;0;1;0;0;0;0;0;2;0;1;0;0;117");
		assertTrue(pbt.getHarvestSet().equals(new ResourceSet(1,1,0,1,0,0,0)));
	}
	@Test
	public void testGetProductionSet(){
		PersonalBonusTile pbt=new PersonalBonusTile(new ResourceSet(1,1,1,1,1,1,1),new ResourceSet(2,2,2,2,2,2,2),0);
		assertTrue(pbt.getProductionSet().equals(new ResourceSet(2,2,2,2,2,2,2)));
	}
}
