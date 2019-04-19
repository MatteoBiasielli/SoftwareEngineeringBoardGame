package it.polimi.ingsw.LM6.game;

import org.junit.Test;

import it.polimi.ingsw.LM6.server.game.Action;
import it.polimi.ingsw.LM6.server.game.LorenzoIlMagnifico;
import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;
import it.polimi.ingsw.LM6.server.game.exceptions.FullGameException;
import it.polimi.ingsw.LM6.server.users.SocketUser;

public class TestAction {
	Action act;
	
	@Test (expected=it.polimi.ingsw.LM6.server.game.exceptions.BadInputException.class)
	public void testActionBadInputExceptionFamiliar() throws FullGameException{
		LorenzoIlMagnifico game = new LorenzoIlMagnifico(false, 4, "ucgd");
		Player p1 = game.addPlayer(new SocketUser(null));
		String a = new String("TOWERACTION;GREEN;0;utuyvyu;0");
		act = new Action(a.split(";"), p1, new ResourceSet(0,0,0,0,0,0,0));		
	}
	
	@Test (expected=it.polimi.ingsw.LM6.server.game.exceptions.BadInputException.class)
	public void testActionBadInputExceptionTower() throws FullGameException{
		LorenzoIlMagnifico game = new LorenzoIlMagnifico(false, 4, "ucgd");
		Player p1 = game.addPlayer(new SocketUser(null));
		String a = new String("TOWERACTION;aergerv;0;UNCOLOUR;0");
		act = new Action(a.split(";"), p1, new ResourceSet(0,0,0,0,0,0,0));		
	}
	
	@Test (expected=it.polimi.ingsw.LM6.server.game.exceptions.BadInputException.class)
	public void testActionBadInputExceptionNumberFive() throws FullGameException{
		LorenzoIlMagnifico game = new LorenzoIlMagnifico(false, 4, "ucgd");
		Player p1 = game.addPlayer(new SocketUser(null));
		String a = new String("COUNCILREQUIREMENT;6");
		act = new Action(a.split(";"), p1, new ResourceSet(0,0,0,0,0,0,0));		
	}
	
	@Test (expected=it.polimi.ingsw.LM6.server.game.exceptions.BadInputException.class)
	public void testActionBadInputExceptionDifferent2() throws FullGameException{
		LorenzoIlMagnifico game = new LorenzoIlMagnifico(false, 4, "ucgd");
		Player p1 = game.addPlayer(new SocketUser(null));
		String a = new String("DOUBLECOUNCILREQUIREMENT;1,1");
		act = new Action(a.split(";"), p1, new ResourceSet(0,0,0,0,0,0,0));		
	}
	
	@Test (expected=it.polimi.ingsw.LM6.server.game.exceptions.BadInputException.class)
	public void testActionBadInputExceptionDifferent3() throws FullGameException{
		LorenzoIlMagnifico game = new LorenzoIlMagnifico(false, 4, "ucgd");
		Player p1 = game.addPlayer(new SocketUser(null));
		String a = new String("TRIPLECOUNCILREQUIREMENT;2;2;2");
		act = new Action(a.split(";"), p1, new ResourceSet(0,0,0,0,0,0,0));		
	}
	
	@Test (expected=it.polimi.ingsw.LM6.server.game.exceptions.BadInputException.class)
	public void testActionBadInputExceptionNumber4() throws FullGameException{
		LorenzoIlMagnifico game = new LorenzoIlMagnifico(false, 4, "ucgd");
		Player p1 = game.addPlayer(new SocketUser(null));
		String a = new String("TOWERACTION;GREEN;5;UNCOLOUR;0");
		act = new Action(a.split(";"), p1, new ResourceSet(0,0,0,0,0,0,0));		
	}
	
	@Test (expected=it.polimi.ingsw.LM6.server.game.exceptions.BadInputException.class)
	public void testActionBadInputExceptionSlaves() throws FullGameException{
		LorenzoIlMagnifico game = new LorenzoIlMagnifico(false, 4, "ucgd");
		Player p1 = game.addPlayer(new SocketUser(null));
		String a = new String("TOWERACTION;GREEN;2;UNCOLOUR;-1");
		act = new Action(a.split(";"), p1, new ResourceSet(0,0,0,0,0,0,0));		
	}
	
	@Test (expected=it.polimi.ingsw.LM6.server.game.exceptions.BadInputException.class)
	public void testActionBadInputExceptionSlaves2() throws FullGameException{
		LorenzoIlMagnifico game = new LorenzoIlMagnifico(false, 4, "ucgd");
		Player p1 = game.addPlayer(new SocketUser(null));
		String a = new String("TOWERACTION;GREEN;2;UNCOLOUR;a");
		act = new Action(a.split(";"), p1, new ResourceSet(0,0,0,0,0,0,0));		
	}
	
	@Test (expected=it.polimi.ingsw.LM6.server.game.exceptions.BadInputException.class)
	public void testActionBadInputExceptionCheckNumber2() throws FullGameException{
		LorenzoIlMagnifico game = new LorenzoIlMagnifico(false, 4, "ucgd");
		Player p1 = game.addPlayer(new SocketUser(null));
		String a = new String("TOWERACTIONWITHCHOICE;GREEN;2;UNCOLOUR;0;3");
		act = new Action(a.split(";"), p1, new ResourceSet(0,0,0,0,0,0,0));		
	}
	
	@Test (expected=it.polimi.ingsw.LM6.server.game.exceptions.BadInputException.class)
	public void testActionBadInputExceptionProductionChoices() throws FullGameException{
		LorenzoIlMagnifico game = new LorenzoIlMagnifico(false, 4, "ucgd");
		Player p1 = game.addPlayer(new SocketUser(null));
		String a = new String("PRODUCTIONACTION;WHITE;0;1;1;A;0;0;0;0");
		act = new Action(a.split(";"), p1, new ResourceSet(0,0,0,0,0,0,0));		
	}
	
	@Test (expected=it.polimi.ingsw.LM6.server.game.exceptions.BadInputException.class)
	public void testActionBadInputExceptionProductionChoices2() throws FullGameException{
		LorenzoIlMagnifico game = new LorenzoIlMagnifico(false, 4, "ucgd");
		Player p1 = game.addPlayer(new SocketUser(null));
		String a = new String("PRODUCTIONACTION;WHITE;0;1;1;-3;0;0;0;0");
		act = new Action(a.split(";"), p1, new ResourceSet(0,0,0,0,0,0,0));		
	}
}
