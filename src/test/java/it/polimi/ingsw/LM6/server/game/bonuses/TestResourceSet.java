package it.polimi.ingsw.LM6.server.game.bonuses;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;
import it.polimi.ingsw.LM6.server.game.exceptions.BadInputException;

public class TestResourceSet {
	ResourceSet res;
	
	public TestResourceSet(){
		this.res= new ResourceSet(2,3,4,5,6,7,8);
	}
	
	@Test
	public void testSum(){
		ResourceSet ris=res.sum(new ResourceSet(1,1,1,1,1,1,1));
		assertEquals(new ResourceSet(3,4,5,6,7,8,9).toString(),ris.toString());
	}
	
	@Test
	public void testSub(){
		ResourceSet ris=res.sub(new ResourceSet(1,1,1,1,1,1,1));
		assertEquals(new ResourceSet(1,2,3,4,5,6,7).toString(),ris.toString());
		ResourceSet ris2=res.sub(new ResourceSet(10,10,10,10,10,10,10));
		assertEquals(new ResourceSet(0,0,0,0,0,0,0).toString(),ris2.toString());
	}
	
	@Test
	public void testInternalSum(){
		res.internalSum(new ResourceSet(1,1,1,1,1,1,1));
		assertEquals(new ResourceSet(3,4,5,6,7,8,9).toString(),res.toString());
	}
	
	@Test
	public void testInternalSub(){			
		res.internalSub(new ResourceSet(1,1,1,1,1,1,1));
		assertEquals(new ResourceSet(1,2,3,4,5,6,7).toString(),res.toString());
		res.internalSub(new ResourceSet(10,10,10,10,10,10,10));
		assertEquals(new ResourceSet(0,0,0,0,0,0,0).toString(),res.toString());
	}
	
	@Test
	public void testContains(){

		boolean result= res.contains(new ResourceSet(0,0,0,0,0,0,0));
		assertEquals(true, result);
		result= res.contains(new ResourceSet(2,3,4,5,6,7,8));
		assertEquals(true, result);
		result= res.contains(new ResourceSet(10,0,0,0,0,0,0));
		assertEquals(false, result);

	}
	
	@Test
	public void testSetZero(){
		res.setZero();
		assertEquals(new ResourceSet(0,0,0,0,0,0,0).toString(),res.toString());

	}
	
	@Test(expected=it.polimi.ingsw.LM6.server.game.exceptions.BadInputException.class)
	public void testMultiply() throws BadInputException{
			ResourceSet result= res.multiply(0);
			assertEquals(new ResourceSet(0,0,0,0,0,0,0).toString(),result.toString());
			result= res.multiply(1);
			assertEquals(new ResourceSet(2,3,4,5,6,7,8).toString(),result.toString());
			result= res.multiply(2);
			assertEquals(new ResourceSet(4,6,8,10,12,14,16).toString(),result.toString());
			result=res.multiply(-1);
	}
	
	@Test
	public void testEquals(){
		boolean result= res.equals(new ResourceSet(0,3,4,5,6,7,8));
		assertEquals(false, result);
		result= res.equals(new ResourceSet(2,0,4,5,6,7,8));
		assertEquals(false, result);
		result= res.equals(new ResourceSet(2,3,0,5,6,7,8));
		assertEquals(false, result);
		result= res.equals(new ResourceSet(2,3,4,0,6,7,8));
		assertEquals(false, result);
		result= res.equals(new ResourceSet(2,3,4,5,0,7,8));
		assertEquals(false, result);
		result= res.equals(new ResourceSet(2,3,4,5,6,0,8));
		assertEquals(false, result);
		result= res.equals(new ResourceSet(2,3,4,5,6,7,0));
		assertEquals(false, result);
		result= res.equals(new ResourceSet(2,3,4,5,6,7,8));
		assertEquals(true, result);
	}
	
	
	@Test
	public void testGetTotalMaterialResources(){
		int result= res.getTotalMaterialResources();
		assertEquals(14, result);
	}
	
	@Test
	public void testIsEmpty(){
		boolean result=res.isEmpty();
		assertEquals(false, result);
		res.setZero();
		result=res.isEmpty();
		assertEquals(true, result);
	}
	@Test
	public void testToString(){
		assertTrue("102W 104St 105C 103Se 100VP 100MP 100FP ".equals(new ResourceSet(102,104,105,103,100,100,100).toString()));
		assertTrue(" ".equals(new ResourceSet().toString()));
	}
	@Test
	public void testParseResourceSet(){
		assertTrue(new ResourceSet(102,104,105,103,100,100,100).equals(ResourceSet.parseResourceSet("102W 104St 105C 103Se 100VP 100MP 100FP ")));
	}
	
}
