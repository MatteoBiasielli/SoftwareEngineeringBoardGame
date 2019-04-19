package it.polimi.ingsw.LM6.server.game.states;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.LM6.server.game.Action;
import it.polimi.ingsw.LM6.server.game.LorenzoIlMagnifico;
import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.game.bonuses.Bonus;
import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;
import it.polimi.ingsw.LM6.server.game.states.CouncilState;
import it.polimi.ingsw.LM6.server.users.SocketUser;

public class TestCouncilState {
	
	CouncilState tmp = new CouncilState();
	
	@Test 
	public void testGetStrCondition(){
		int a = tmp.getStrCondition();
		assertEquals(a,1);
	}
	
	@Test
	public void testToString(){
		String a = tmp.toString();
		assertEquals(a,"Str="+tmp.getStrCondition()+"!"+new Bonus(new ResourceSet(0,0,1,0,0,0,0), false, 'N',  false,'N', 0,null, false,false, 0, 1,false).toString()+"!");
		Player p = new Player(new SocketUser(null), new LorenzoIlMagnifico(false, 4, "cane"), 4);
		p.acquireResources(new ResourceSet(0,0,0,3,0,0,0));
		String app = new String ("COUNCILACTION;BLACK;3");
		String[] app2 = app.split(";");
		Action act = new Action(app2, p, new ResourceSet(0,0,0,0,0,0,0));
		tmp.placeFamiliar(act);
		assertEquals(tmp.toString(),"Str="+tmp.getStrCondition()+"!"+new Bonus(new ResourceSet(0,0,1,0,0,0,0), false, 'N',  false,'N', 0,null, false,false, 0, 1,false).toString()+"!"+"null:(b)-");
	}
	
	@Test 
	public void testNewTurn(){
		tmp.newTurn();
		assertTrue(tmp.getSpaces().isEmpty());
	}
	
	
}
