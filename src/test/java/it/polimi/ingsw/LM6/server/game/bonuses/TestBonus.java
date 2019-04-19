package it.polimi.ingsw.LM6.server.game.bonuses;


import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.users.SocketUser;


public class TestBonus {
	private Bonus b;
	private Player p;
	public TestBonus(){
		this.b=new Bonus(new ResourceSet(1,1,1,1,1,1,1), true, 'G', true,'G', 1,new ResourceSet(1,1,1,1,1,1,1), 
					true,false, 1, 2, true);
		this.p=new Player(new SocketUser(null),null,4);
	}
	
	@Test
	public void testSetZero(){
		b.setZero();
		assertEquals(new Bonus().toString(),b.toString());
	}
	
	
	@Test
	public void testToString(){
		assertEquals("(1W 1St 1C 1Se 1VP 1MP 1FP )xG  Action on G:1  Disc=1W 1St 1C 1Se 1VP 1MP 1FP   BonusProd:1  CB=2 different",b.toString());
	}
	
	
	@Test
	public void testSetBonusCouncil(){
		b=new Bonus(2);
		assertEquals(new Bonus(new ResourceSet(0,0,0,0,0,0,0), false, 'N', false,'N', 0,null,
			false,false, 0, 2, false).toString(),b.toString());
	}
	@Test
	public void testHasEmptyResourceSet(){
		boolean result= b.hasEmptyResourceSet();
		assertEquals(false, result);
		assertEquals(true, new Bonus().hasEmptyResourceSet());
	}
	
	@Test
	public void testIsEmpty(){
		boolean result= b.isEmpty();
		assertEquals(false, result);
		assertEquals(true, new Bonus().isEmpty());
	}
	
	@Test(expected=it.polimi.ingsw.LM6.server.game.exceptions.BadInputException.class)
	public void testRemoveCouncilBonus(){
		int n=b.getCouncilBonusesNumber();
		b.removeCouncilBonus();
		assertEquals(n-1,b.getCouncilBonusesNumber());
		new Bonus().removeCouncilBonus();		
	}
	@Test(expected=it.polimi.ingsw.LM6.server.game.exceptions.BadInputException.class)
	public void testRemoveDoubleCouncilBonus(){
		b.getCouncilBonusesNumber();
		b.removeDoubleCouncilBonus();
		assertEquals(0,b.getCouncilBonusesNumber());
		new Bonus().removeDoubleCouncilBonus();		
	}
	@Test(expected=it.polimi.ingsw.LM6.server.game.exceptions.BadInputException.class)
	public void testRemoveTripleCouncilBonus(){
		b=new Bonus(3);
		b.getCouncilBonusesNumber();
		b.removeTripleCouncilBonus();
		assertEquals(0,b.getCouncilBonusesNumber());
		new Bonus().removeTripleCouncilBonus();		
	}
	@Test(expected=it.polimi.ingsw.LM6.server.game.exceptions.BadInputException.class)
	public void testRemoveBonusHarvest(){
		this.b=new Bonus(new ResourceSet(1,1,1,1,1,1,1), true, 'G', true,'G', 1,new ResourceSet(1,1,1,1,1,1,1), 
				false,true, 1, 2, true);
		b.removeBonusHarvest();	
		assertEquals(false, b.hasBonusHarvest());
		b.removeBonusHarvest();	
	}
	@Test(expected=it.polimi.ingsw.LM6.server.game.exceptions.BadInputException.class)
	public void testRemoveBonusProduction(){
		b.removeBonusProduction();
		assertEquals(false, b.hasBonusProduction());
		b.removeBonusProduction();
	}
	
	@Test(expected=it.polimi.ingsw.LM6.server.game.exceptions.BadInputException.class)
	public void testRemoveBonusAction(){
		b.removeBonusAction();
		assertEquals(false, b.hasBonusAction());
		b.removeBonusAction();
	}

	@Test
	public void testSetResourceSet(){
		b.setResourceSet(new ResourceSet(1,1,1,2,2,2,3));
		assertTrue(b.getResourceSetFor(p).equals(new ResourceSet(1,1,1,2,2,2,3)));
	}
	@Test
	public void testGetResourceSetFor(){
		b=new Bonus(this.b);
		assertTrue(b.getResourceSetFor(p).equals(new ResourceSet()));
		b=new Bonus(new ResourceSet(1,1,1,1,1,1,1), true, 'Y', true,'G', 1,new ResourceSet(1,1,1,1,1,1,1), 
				true,false, 1, 2, true);
		assertTrue(b.getResourceSetFor(p).equals(new ResourceSet()));
		b=new Bonus(new ResourceSet(1,1,1,1,1,1,1), true, 'B', true,'G', 1,new ResourceSet(1,1,1,1,1,1,1), 
				true,false, 1, 2, true);
		assertTrue(b.getResourceSetFor(p).equals(new ResourceSet()));
		b=new Bonus(new ResourceSet(1,1,1,1,1,1,1), true, 'P', true,'G', 1,new ResourceSet(1,1,1,1,1,1,1), 
				true,false, 1, 2, true);
		assertTrue(b.getResourceSetFor(p).equals(new ResourceSet()));
		p.getResourceSet().setZero();
		b=new Bonus(new ResourceSet(1,1,1,1,1,1,1), true, 'M', true,'G', 1,new ResourceSet(1,1,1,1,1,1,1), 
				true,false, 1, 2, true);
		assertTrue(b.getResourceSetFor(p).equals(new ResourceSet()));
	}
	@Test
	public void testSum(){
		Bonus b2=new Bonus(new ResourceSet(1,1,1,1,1,1,1), true, 'G', true,'G', 1,new ResourceSet(1,1,1,1,1,1,1), 
				true,false, 1, 2, true);
		Bonus b3=new Bonus(new ResourceSet(1,1,1,1,1,1,1), true, 'G', true,'G', 1,new ResourceSet(1,1,1,1,1,1,1), 
				false,true, 1, 0, false);
		Bonus b4=b2.sum(b3, p);
		assertEquals(new Bonus(new ResourceSet(0,0,0,0,0,0,0), false, 'N', true,'G', 1,new ResourceSet(1,1,1,1,1,1,1), 
				true,true, 1, 2, true).toString(),b4.toString());
		
		b2=new Bonus(new ResourceSet(1,1,1,1,1,1,1), true, 'G', true,'G', 1,null, 
				true,false, 1, 2, true);
		b3=new Bonus(new ResourceSet(1,1,1,1,1,1,1), true, 'G', true,'G', 1,new ResourceSet(1,1,1,1,1,1,1), 
				false,true, 1, 0, false);
		b4=b2.sum(b3, p);
		assertEquals(new Bonus(new ResourceSet(0,0,0,0,0,0,0), false, 'N', true,'G', 1,new ResourceSet(1,1,1,1,1,1,1), 
				true,true, 1, 2, true).toString(),b4.toString());
		
		b2=new Bonus(new ResourceSet(1,1,1,1,1,1,1), true, 'G', false,'G', 1,new ResourceSet(1,1,1,1,1,1,1), 
				true,false, 1, 2, true);
		b3=new Bonus(new ResourceSet(1,1,1,1,1,1,1), true, 'G', true,'G', 1,new ResourceSet(1,1,1,1,1,1,2), 
				false,true, 1, 0, false);
		b4=b2.sum(b3, p);
		assertEquals(new Bonus(new ResourceSet(0,0,0,0,0,0,0), false, 'N', true,'G', 1,new ResourceSet(1,1,1,1,1,1,2), 
				true,true, 1, 2, true).toString(),b4.toString());
	}
	@Test(expected=it.polimi.ingsw.LM6.server.game.exceptions.BadInputException.class)
	public void testCreate(){
		Bonus b3=new Bonus(new ResourceSet(1,1,1,1,1,1,1), true, 'D', true,'G', 1,new ResourceSet(1,1,1,1,1,1,1), 
				false,true, 1, 0, false);
	}
	@Test
	public void testGetBonusActionDiscount(){
		assertTrue(b.getbonusActionDiscount().equals(new ResourceSet(1,1,1,1,1,1,1)));
	}
}
