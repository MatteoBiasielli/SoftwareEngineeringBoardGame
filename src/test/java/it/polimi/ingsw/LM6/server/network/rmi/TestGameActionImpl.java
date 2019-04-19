package it.polimi.ingsw.LM6.server.network.rmi;

import static org.junit.Assert.*;

import java.io.IOException;
import java.rmi.RemoteException;

import org.junit.Test;

import it.polimi.ingsw.LM6.server.game.LorenzoIlMagnifico;
import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.game.PlayerColour;
import it.polimi.ingsw.LM6.server.game.bonuses.Bonus;
import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;
import it.polimi.ingsw.LM6.server.game.exceptions.FullGameException;
import it.polimi.ingsw.LM6.server.users.SocketUser;

public class TestGameActionImpl {
	
	private LorenzoIlMagnifico game;
	private GameActionImpl stub;
	private Player p;
	
	public TestGameActionImpl() throws FullGameException, RemoteException{
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		this.p = game.addPlayer(new SocketUser(null));
		p.setIsActive(false);
		game.createBoardTest();
		game.newRound();
		game.getStatus().createExcommunicationChoices();
		this.stub = new GameActionImpl(game, p);
		p.setIsActive(false);
	}
	
	@Test 
	public void testTowerAction() throws RemoteException{
		stub.towerAction("GREEN", "0", "WHITE", "0");
		assertEquals(this.game.getGameBoard().getTower(0).getCard()[0].toString(), this.game.getPlayer(0).getGreenCardList().get(0).toString());
	}
	
	@Test 
	public void testTowerAction2() throws RemoteException{
		stub.towerAction("GREEN", "0", "WHITE", "0");
		stub.towerAction("PURPLE", "0", "WHITE", "0");		
		assertFalse(game.getGameBoard().getTower(3).isOccupied());
	}
	
	@Test 
	public void testTowerActionAnotherFamiliarSameColour() throws RemoteException{
		p.getResourceSet().setZero();
		p.acquireResources(new ResourceSet(0,0,0,3,0,0,0));
		stub.towerAction("GREEN", "0", "WHITE", "0");
		stub.towerAction("GREEN", "1", "BLACK", "3");
		assertTrue(p.getGreenCardList().size()==1);
	}
	
	@Test 
	public void testTowerActionAlreadyUsedFamiliarException() throws RemoteException{
		p.getResourceSet().setZero();
		p.acquireResources(new ResourceSet(0,0,0,3,0,0,0));
		stub.towerAction("GREEN", "0", "WHITE", "0");
		stub.towerAction("GREEN", "1", "WHITE", "3");
		assertTrue(p.getGreenCardList().size()==1);
	}
	
	@Test 
	public void testTowerActionAlreadyOccupiedException() throws RemoteException{
		p.getResourceSet().setZero();
		p.acquireResources(new ResourceSet(0,0,0,3,0,0,0));
		stub.towerAction("GREEN", "0", "WHITE", "0");
		stub.towerAction("GREEN", "0", "BLACK", "3");
		assertFalse(p.getFamiliar("BLACK").isPlaced());
	}
	
	@Test 
	public void testTowerActionNotEnoughResources() throws RemoteException{
		p.getResourceSet().setZero();
		stub.towerAction("GREEN", "1", "BLACK", "3");
		assertTrue(p.getGreenCardList().size()==0);
	}
	
	@Test 
	public void testTowerActionNotStronghEnough() throws RemoteException{
		p.getResourceSet().setZero();
		p.acquireResources(new ResourceSet(0,0,0,3,0,0,0));
		stub.towerAction("GREEN", "3", "UNCOLOUR", "3");
		assertTrue(!p.getFamiliar("UNCOLOURED").isPlaced());
	}
	
	@Test 
	public void testTowerActionBadInput() throws RemoteException{
		p.getResourceSet().setZero();
		p.acquireResources(new ResourceSet(0,0,0,3,0,0,0));
		stub.towerAction("GREEN", "a", "UNCOLOUR", "3");
		assertTrue(!p.getFamiliar("UNCOLOURED").isPlaced());
		assertTrue(game.getGameBoard()!=null);
	}

	@Test
	public void testSetExcommunicatonChoice() throws RemoteException{
		game.getStatus().setExcomunicationPhase();
		stub.setExcommunicationResult("0");
		assertFalse(this.game.getStatus().getExcommChoices()[0]);
	}
	
	@Test 
	public void testMarketAction() throws RemoteException{
		p.getResourceSet().setZero();
		p.acquireResources(new ResourceSet(0,0,0,3,0,0,0));
		stub.marketAction("1", "WHITE", "0");
		assertTrue(p.getFamiliar("WHITE").isPlaced());
	}
	
	@Test 
	public void testMarketActionNotEnoughResources() throws RemoteException{
		p.getResourceSet().setZero();
		p.acquireResources(new ResourceSet(0,0,0,0,0,0,0));
		stub.marketAction("1", "WHITE", "1");
		assertTrue(!p.getFamiliar("WHITE").isPlaced());
	}
	
	@Test 
	public void testMarketActionNotStrongEnough() throws RemoteException{
		p.getResourceSet().setZero();
		p.acquireResources(new ResourceSet(0,0,0,3,0,0,0));
		stub.marketAction("1", "UNCOLOUR", "0");
		assertTrue(!p.getFamiliar("UNCOLOURED").isPlaced());
	}
	
	@Test 
	public void testMarketActionAlreadyUsedFamiliar() throws RemoteException{
		p.getResourceSet().setZero();
		stub.marketAction("1", "WHITE", "0");
		assertEquals(p.getResourceSet().getServants(),5);
		stub.marketAction("0", "WHITE", "0");
		assertEquals(p.getResourceSet().getCoin(),0);
	}
	
	@Test 
	public void testMarketActionAlreadyOccupied() throws RemoteException{
		p.getResourceSet().setZero();
		p.acquireResources(new ResourceSet(0,0,0,1,0,0,0));
		stub.marketAction("1", "UNCOLOUR", "1");
		assertEquals(p.getResourceSet().getServants(),5);
		stub.marketAction("1", "WHITE", "0");
		assertEquals(p.getResourceSet().getServants(),5);
	}
	
	@Test 
	public void testMarketActionBadInput() throws RemoteException{
		p.getResourceSet().setZero();
		p.acquireResources(new ResourceSet(0,0,0,1,0,0,0));
		stub.marketAction("1", "kjb", "1");
		assertEquals(p.getResourceSet().getServants(),1);
	}
	
	@Test
	public void testProductionAction() throws RemoteException{
		p.getResourceSet().setZero();
		p.acquireResources(new ResourceSet(0,0,0,1,0,0,0));
		stub.productionAction("UNCOLOUR", "1", "1", "0", "0", "0", "0", "0", "0");
		assertTrue(p.getFamiliar("UNCOLOURED").isPlaced());
		assertEquals(p.getResourceSet().getCoin(),2);
		assertEquals(p.getResourceSet().getMilitaryPoints(),1);
	}
	
	@Test
	public void testProductionActionNotEnoughResources() throws RemoteException{
		p.getResourceSet().setZero();
		p.acquireResources(new ResourceSet(0,0,0,0,0,0,0));
		stub.productionAction("UNCOLOUR", "1", "1", "0", "0", "0", "0", "0", "0");
		assertTrue(!p.getFamiliar("UNCOLOURED").isPlaced());
	}
	
	@Test
	public void testProductionActionNotStrongEnough() throws RemoteException{
		p.getResourceSet().setZero();
		stub.productionAction("UNCOLOUR", "0", "1", "0", "0", "0", "0", "0", "0");
		assertTrue(!p.getFamiliar("UNCOLOURED").isPlaced());
	}
	
	@Test
	public void testProductionActionBadInput() throws RemoteException{
		p.getResourceSet().setZero();
		p.acquireResources(new ResourceSet(0,0,0,0,0,0,0));
		stub.productionAction("UNCOLOUR", "0", "jhf7trucyku", "0", "0", "0", "0", "0", "0");
		assertTrue(!p.getFamiliar("UNCOLOURED").isPlaced());
	}
	
	@Test
	public void testProductionActionAlreadyOccupied() throws RemoteException{
		p.getResourceSet().setZero();
		p.acquireResources(new ResourceSet(0,0,0,0,0,0,0));
		stub.productionAction("WHITE", "0", "1", "0", "0", "0", "0", "0", "0");
		assertEquals(p.getResourceSet().getCoin(),2);
		assertEquals(p.getResourceSet().getMilitaryPoints(),1);
		stub.productionAction("ORANGE", "0", "1", "0", "0", "0", "0", "0", "0");
		assertEquals(p.getResourceSet().getCoin(),2);
		assertEquals(p.getResourceSet().getMilitaryPoints(),1);
	}
	
	@Test
	public void testProductionActionAlreadyUsedFamiliar() throws RemoteException{
		p.getResourceSet().setZero();
		p.acquireResources(new ResourceSet(0,0,0,10,0,0,0));
		stub.towerAction("GREEN", "0", "WHITE", "5");
		p.getResourceSet().setZero();
		stub.productionAction("WHITE", "5", "1", "0", "0", "0", "0", "0", "0");
		assertEquals(p.getResourceSet().getCoin(),0);
		assertEquals(p.getResourceSet().getMilitaryPoints(),0);
	}
	
	
	@Test
	public void testHarvestAction() throws RemoteException{
		p.getResourceSet().setZero();
		p.acquireResources(new ResourceSet(0,0,0,1,0,0,0));
		stub.harvestAction("UNCOLOUR", "1", "1");
		assertTrue(p.getFamiliar("UNCOLOURED").isPlaced());
		assertEquals(p.getResourceSet().getWood(),1);
		assertEquals(p.getResourceSet().getStone(),1);
		assertEquals(p.getResourceSet().getServants(),1);
	}
	
	@Test
	public void testHarvestActionBadInput() throws RemoteException{
		p.getResourceSet().setZero();
		p.acquireResources(new ResourceSet(0,0,0,1,0,0,0));
		stub.harvestAction("UNCOLOUR", "pihyciuo", "1");
		assertTrue(!p.getFamiliar("UNCOLOURED").isPlaced());
	}
	
	@Test
	public void testHarvestActionNotStrongEnough() throws RemoteException{
		p.getResourceSet().setZero();
		p.acquireResources(new ResourceSet(0,0,0,0,0,0,0));
		stub.harvestAction("UNCOLOUR", "0", "1");
		assertEquals(p.getResourceSet().getWood(),0);
		assertEquals(p.getResourceSet().getStone(),0);
		assertEquals(p.getResourceSet().getServants(),0);
	}
	
	@Test
	public void testHarvestActionNotEnoughResources() throws RemoteException{
		p.getResourceSet().setZero();
		p.acquireResources(new ResourceSet(0,0,0,0,0,0,0));
		stub.harvestAction("UNCOLOUR", "3", "1");
		assertTrue(!p.getFamiliar("UNCOLOURED").isPlaced());
	}
	
	@Test
	public void testHarvestActionAlreadyUsedFamiliar() throws RemoteException{		
		stub.towerAction("GREEN", "0", "WHITE", "0");
		p.getResourceSet().setZero();
		stub.harvestAction("WHITE", "0", "1");
		assertEquals(p.getResourceSet().getWood(),0);
		assertEquals(p.getResourceSet().getStone(),0);
		assertEquals(p.getResourceSet().getServants(),0);
	}
	
	@Test
	public void testHarvestActionAlreadyOccupied() throws RemoteException{		
		stub.harvestAction("ORANGE", "0", "1");
		p.getResourceSet().setZero();
		stub.harvestAction("WHITE", "0", "1");
		assertEquals(p.getResourceSet().getWood(),0);
		assertEquals(p.getResourceSet().getStone(),0);
		assertEquals(p.getResourceSet().getServants(),0);
	}
	
	@Test
	public void testCouncilAction() throws RemoteException{
		p.getResourceSet().setZero();
		p.acquireResources(new ResourceSet(0,0,0,1,0,0,0));
		stub.councilAction("UNCOLOUR", "1");
		assertTrue(game.getGameBoard().getPlayersInCouncil().size()>0);
	}
	
	@Test
	public void testCouncilActionBadInput() throws RemoteException{
		p.getResourceSet().setZero();
		p.acquireResources(new ResourceSet(0,0,0,1,0,0,0));
		stub.councilAction("UNCOLOUR", "rWB<ERb");
		assertTrue(game.getGameBoard().getPlayersInCouncil().isEmpty());
		stub.councilAction("UNCOL", "1");
		assertTrue(game.getGameBoard().getPlayersInCouncil().isEmpty());
	}
	
	@Test
	public void testCouncilActionNotStrongEnough() throws RemoteException{
		stub.councilAction("UNCOLOUR", "0");
		assertTrue(game.getGameBoard().getPlayersInCouncil().isEmpty());
	}
	
	@Test
	public void testCouncilActionNotEnoughResources() throws RemoteException{
		p.getResourceSet().setZero();
		p.acquireResources(new ResourceSet(0,0,0,1,0,0,0));
		stub.councilAction("UNCOLOUR", "3");
		assertTrue(game.getGameBoard().getPlayersInCouncil().isEmpty());
	}
	
	@Test
	public void testCouncilActionAlreadyUsedFamiliar() throws RemoteException{
		stub.towerAction("GREEN", "0", "WHITE", "0");
		stub.councilAction("WHITE", "0");
		assertTrue(game.getGameBoard().getPlayersInCouncil().isEmpty());
	}
	
	@Test
	public void testCouncilRequirement() throws RemoteException{
		p.getResourceSet().setZero();
		stub.councilRequirement("1");
		assertEquals(p.getResourceSet().getWood(),0);
		assertEquals(p.getResourceSet().getStone(),0);
		stub.setTemporalBonus(new Bonus(new ResourceSet(0,0,0,0,0,0,0),false, ' ', false, ' ', 0, new ResourceSet(0,0,0,0,0,0,0), false, false, 0, 0, false));
		stub.councilRequirement("1");
		assertEquals(p.getResourceSet().getWood(),0);
		assertEquals(p.getResourceSet().getStone(),0);
		stub.setTemporalBonus(new Bonus(new ResourceSet(0,0,0,0,0,0,0),false, ' ', false, ' ', 0, new ResourceSet(0,0,0,0,0,0,0), false, false, 0, 1, false));
		stub.councilRequirement("qehrqrbqa");
		assertEquals(p.getResourceSet().getWood(),0);
		assertEquals(p.getResourceSet().getStone(),0);
		stub.setTemporalBonus(new Bonus(new ResourceSet(0,0,0,0,0,0,0),false, ' ', false, ' ', 0, new ResourceSet(0,0,0,0,0,0,0), false, false, 0, 1, false));
		stub.councilRequirement("1");
		assertEquals(p.getResourceSet().getWood(),1);
		assertEquals(p.getResourceSet().getStone(),1);
	}
	
	@Test
	public void testDoubleCouncilRequirement() throws RemoteException{
		p.getResourceSet().setZero();
		stub.doubleCouncilRequirement("1", "2");
		assertEquals(p.getResourceSet().getWood(),0);
		assertEquals(p.getResourceSet().getStone(),0);
		assertEquals(p.getResourceSet().getCoin(),0);
		stub.setTemporalBonus(new Bonus(new ResourceSet(0,0,0,0,0,0,0),false, ' ', false, ' ', 0, new ResourceSet(0,0,0,0,0,0,0), false, false, 0, 0, false));
		stub.doubleCouncilRequirement("1", "2");
		assertEquals(p.getResourceSet().getWood(),0);
		assertEquals(p.getResourceSet().getStone(),0);
		assertEquals(p.getResourceSet().getCoin(),0);
		stub.setTemporalBonus(new Bonus(new ResourceSet(0,0,0,0,0,0,0),false, ' ', false, ' ', 0, new ResourceSet(0,0,0,0,0,0,0), false, false, 0, 2, false));
		stub.doubleCouncilRequirement("1", "2");
		assertEquals(p.getResourceSet().getWood(),0);
		assertEquals(p.getResourceSet().getStone(),0);
		assertEquals(p.getResourceSet().getCoin(),0);
		stub.setTemporalBonus(new Bonus(new ResourceSet(0,0,0,0,0,0,0),false, ' ', false, ' ', 0, new ResourceSet(0,0,0,0,0,0,0), false, false, 0, 2, true));
		stub.doubleCouncilRequirement("1", "erhqabr");
		assertEquals(p.getResourceSet().getWood(),0);
		assertEquals(p.getResourceSet().getStone(),0);
		assertEquals(p.getResourceSet().getServants(),0);
		stub.setTemporalBonus(new Bonus(new ResourceSet(0,0,0,0,0,0,0),false, ' ', false, ' ', 0, new ResourceSet(0,0,0,0,0,0,0), false, false, 0, 2, true));
		stub.doubleCouncilRequirement("1", "2");
		assertEquals(p.getResourceSet().getWood(),1);
		assertEquals(p.getResourceSet().getStone(),1);
		assertEquals(p.getResourceSet().getServants(),2);
	}
	
	@Test
	public void testTripleCouncilRequirement() throws RemoteException{
		p.getResourceSet().setZero();
		stub.tripleCouncilRequirement("1", "2", "3");
		assertEquals(p.getResourceSet().getWood(),0);
		assertEquals(p.getResourceSet().getStone(),0);
		assertEquals(p.getResourceSet().getCoin(),0);
		assertEquals(p.getResourceSet().getServants(),0);
		stub.setTemporalBonus(new Bonus(new ResourceSet(0,0,0,0,0,0,0),false, ' ', false, ' ', 0, new ResourceSet(0,0,0,0,0,0,0), false, false, 0, 0, false));
		stub.tripleCouncilRequirement("1", "2", "3");
		assertEquals(p.getResourceSet().getWood(),0);
		assertEquals(p.getResourceSet().getStone(),0);
		assertEquals(p.getResourceSet().getCoin(),0);
		assertEquals(p.getResourceSet().getServants(),0);
		stub.setTemporalBonus(new Bonus(new ResourceSet(0,0,0,0,0,0,0),false, ' ', false, ' ', 0, new ResourceSet(0,0,0,0,0,0,0), false, false, 0, 3, false));
		stub.tripleCouncilRequirement("1", "2", "3");
		assertEquals(p.getResourceSet().getWood(),0);
		assertEquals(p.getResourceSet().getStone(),0);
		assertEquals(p.getResourceSet().getCoin(),0);
		assertEquals(p.getResourceSet().getServants(),0);
		stub.setTemporalBonus(new Bonus(new ResourceSet(0,0,0,0,0,0,0),false, ' ', false, ' ', 0, new ResourceSet(0,0,0,0,0,0,0), false, false, 0, 3, true));
		stub.tripleCouncilRequirement("afnwr", "2", "3");
		assertEquals(p.getResourceSet().getWood(),0);
		assertEquals(p.getResourceSet().getStone(),0);
		assertEquals(p.getResourceSet().getServants(),0);
		assertEquals(p.getResourceSet().getCoin(),0);
		stub.setTemporalBonus(new Bonus(new ResourceSet(0,0,0,0,0,0,0),false, ' ', false, ' ', 0, new ResourceSet(0,0,0,0,0,0,0), false, false, 0, 3, true));
		stub.tripleCouncilRequirement("1", "2", "3");
		assertEquals(p.getResourceSet().getWood(),1);
		assertEquals(p.getResourceSet().getStone(),1);
		assertEquals(p.getResourceSet().getServants(),2);
		assertEquals(p.getResourceSet().getCoin(),2);
	}
	
	@Test 
	public void testBonusTowerAction() throws RemoteException{
		
		stub.bonusTowerAction("0", "0", "GREEN");
		assertTrue(p.getGreenCardList().isEmpty());
		
		stub.setTemporalBonus(new Bonus(new ResourceSet(0,0,0,0,0,0,0),false, ' ', false, ' ', 0, new ResourceSet(0,0,0,0,0,0,0), false, false, 0, 0, false));
		stub.bonusTowerAction("0", "0", "GREEN");
		assertTrue(p.getGreenCardList().isEmpty());
		
		stub.setTemporalBonus(new Bonus(new ResourceSet(0,0,0,0,0,0,0),false, ' ', true, 'Y', 0, new ResourceSet(0,0,0,0,0,0,0), false, false, 0, 0, false));
		stub.bonusTowerAction("0", "0", "GREEN");
		assertTrue(p.getGreenCardList().isEmpty());
		
		stub.setTemporalBonus(new Bonus(new ResourceSet(0,0,0,0,0,0,0),false, ' ', true, 'G', 0, new ResourceSet(0,0,0,0,0,0,0), false, false, 0, 0, false));
		p.acquireResources(new ResourceSet(0,0,0,1,0,0,0));
		stub.bonusTowerAction("1", "0", "GREEN");
		assertTrue(!p.getGreenCardList().isEmpty());
		
		//NotEnoughResources
		stub.setTemporalBonus(new Bonus(new ResourceSet(0,0,0,0,0,0,0),false, ' ', true, 'Y', 0, new ResourceSet(0,0,0,0,0,0,0), false, false, 0, 0, false));
		p.getResourceSet().setZero();
		stub.bonusTowerAction("3", "0", "YELLOW");
		assertTrue(p.getYellowCardList().isEmpty());
		
		//NotStrongEnough
		stub.setTemporalBonus(new Bonus(new ResourceSet(0,0,0,0,0,0,0),false, ' ', true, 'P', 0, new ResourceSet(0,0,0,0,0,0,0), false, false, 0, 0, false));
		p.getResourceSet().setZero();
		stub.bonusTowerAction("0", "3", "PURPLE");
		assertTrue(p.getPurpleCardList().isEmpty());
		
		//AlreadyOccupied
		stub.setTemporalBonus(new Bonus(new ResourceSet(0,0,0,0,0,0,0),false, ' ', true, 'E', 1, new ResourceSet(0,0,0,0,0,0,0), false, false, 0, 0, false));
		p.getResourceSet().setZero();
		stub.towerAction("GREEN", "0", "WHITE", "0");
		assertTrue(!p.getGreenCardList().isEmpty());
		stub.bonusTowerAction("0", "0", "GREEN");
		assertEquals(p.getGreenCardList().size(),1);
		
		//BadInput
		stub.setTemporalBonus(new Bonus(new ResourceSet(0,0,0,0,0,0,0),false, ' ', true, 'P', 0, new ResourceSet(0,0,0,0,0,0,0), false, false, 0, 0, false));
		p.getResourceSet().setZero();
		stub.bonusTowerAction("fhewat", "3", "PURPLE");
		assertTrue(p.getPurpleCardList().isEmpty());
	}
	
	@Test
	public void testBonusHarvestAction() throws RemoteException{
		p.getResourceSet().setZero();
		
		stub.bonusHarvestAction("1");
		assertEquals(p.getResourceSet().getWood(),0);
		assertEquals(p.getResourceSet().getStone(),0);
		assertEquals(p.getResourceSet().getServants(),0);	
		
		//no bonus harvest available
		stub.setTemporalBonus(new Bonus(new ResourceSet(0,0,0,0,0,0,0),false, ' ', false, ' ', 0, new ResourceSet(0,0,0,0,0,0,0), false, false, 0, 0, false));
		stub.bonusHarvestAction("0");
		assertEquals(p.getResourceSet().getWood(),0);
		assertEquals(p.getResourceSet().getStone(),0);
		assertEquals(p.getResourceSet().getServants(),0);	
	
		//bonus harvest available
		stub.setTemporalBonus(new Bonus(new ResourceSet(0,0,0,0,0,0,0),false, ' ', false, ' ', 0, new ResourceSet(0,0,0,0,0,0,0), false, true, 1, 0, false));
		stub.bonusHarvestAction("0");
		assertEquals(p.getResourceSet().getWood(),1);
		assertEquals(p.getResourceSet().getStone(),1);
		assertEquals(p.getResourceSet().getServants(),1);	
		
		//not enough resources
		p.getResourceSet().setZero();
		stub.setTemporalBonus(new Bonus(new ResourceSet(0,0,0,0,0,0,0),false, ' ', false, ' ', 0, new ResourceSet(0,0,0,0,0,0,0), false, true, 0, 0, false));
		stub.bonusHarvestAction("5");
		assertEquals(p.getResourceSet().getWood(),0);
		assertEquals(p.getResourceSet().getStone(),0);
		assertEquals(p.getResourceSet().getServants(),0);
		
		//not Strong Enough
		stub.setTemporalBonus(new Bonus(new ResourceSet(0,0,0,0,0,0,0),false, ' ', false, ' ', 0, new ResourceSet(0,0,0,0,0,0,0), false, true, 0, 0, false));
		stub.bonusHarvestAction("0");
		assertEquals(p.getResourceSet().getWood(),0);
		assertEquals(p.getResourceSet().getStone(),0);
		assertEquals(p.getResourceSet().getServants(),0);
	}
	
	@Test
	public void testBonusProduction() throws RemoteException{
		
		p.getResourceSet().setZero();
		stub.bonusProductionAction("0", "0", "0", "0", "0", "0", "0");
		assertEquals(p.getResourceSet().getCoin(),0);
		assertEquals(p.getResourceSet().getMilitaryPoints(),0);
		
		//no bonus production available
		stub.setTemporalBonus(new Bonus(new ResourceSet(0,0,0,0,0,0,0),false, ' ', false, ' ', 0, new ResourceSet(0,0,0,0,0,0,0), false, false, 0, 0, false));
		stub.bonusProductionAction("0", "0", "0", "0", "0", "0", "0");
		assertEquals(p.getResourceSet().getCoin(),0);
		assertEquals(p.getResourceSet().getMilitaryPoints(),0);
	
		//notEnoughResources
		stub.setTemporalBonus(new Bonus(new ResourceSet(0,0,0,0,0,0,0),false, ' ', false, ' ', 0, new ResourceSet(0,0,0,0,0,0,0), true, false, 0, 0, false));
		stub.bonusProductionAction("5", "0", "0", "0", "0", "0", "0");
		assertEquals(p.getResourceSet().getCoin(),0);
		assertEquals(p.getResourceSet().getMilitaryPoints(),0);
	
		//notStrongEnough
		stub.setTemporalBonus(new Bonus(new ResourceSet(0,0,0,0,0,0,0),false, ' ', false, ' ', 0, new ResourceSet(0,0,0,0,0,0,0), true, false, 0, 0, false));
		stub.bonusProductionAction("0", "0", "0", "0", "0", "0", "0");
		assertEquals(p.getResourceSet().getCoin(),0);
		assertEquals(p.getResourceSet().getMilitaryPoints(),0);
		
		//BadInput
		stub.setTemporalBonus(new Bonus(new ResourceSet(0,0,0,0,0,0,0),false, ' ', false, ' ', 0, new ResourceSet(0,0,0,0,0,0,0), true, false, 4, 0, false));
		stub.bonusProductionAction("0", "0", "0", "0", "0", "-1", "0");
		assertEquals(p.getResourceSet().getCoin(),0);
		assertEquals(p.getResourceSet().getMilitaryPoints(),0);
		
		stub.setTemporalBonus(new Bonus(new ResourceSet(0,0,0,0,0,0,0),false, ' ', false, ' ', 0, new ResourceSet(0,0,0,0,0,0,0), true, false, 4, 0, false));
		stub.bonusProductionAction("0", "0", "0", "0", "0", "0", "0");
		assertEquals(p.getResourceSet().getCoin(),2);
		assertEquals(p.getResourceSet().getMilitaryPoints(),1);
	}
	
	@Test 
	public void testBonusRenouce() throws RemoteException{
		
		//BadInput
		stub.bonusRenounce("BONUSTOWERACTION");
		stub.bonusRenounce("COUNCILREQUIREMENT");
		stub.bonusRenounce("DOUBLECOUNCILREQUIREMENT");
		stub.bonusRenounce("TRIPLECOUNCILREQUIREMENT");
		stub.bonusRenounce("BONUSHARVESTACTION");
		stub.bonusRenounce("BONUSPRODUCTIONACTION");
		stub.showBoard();
		stub.showPlayers();
		
		stub.setTemporalBonus(new Bonus(new ResourceSet(0,0,0,0,0,0,0),false, ' ', false, ' ', 0, new ResourceSet(0,0,0,0,0,0,0), false, false, 0, 2, true));
		stub.bonusRenounce("COUNCILREQUIREMENT");
		assertTrue(stub.getTemporalBonus().getCouncilBonusesNumber()==2);	
	
		stub.setTemporalBonus(new Bonus(new ResourceSet(0,0,0,0,0,0,0),false, ' ', false, ' ', 0, new ResourceSet(0,0,0,0,0,0,0), false, false, 0, 2, false));
		stub.bonusRenounce("COUNCILREQUIREMENT");
		assertTrue(stub.getTemporalBonus().getCouncilBonusesNumber()==1);	
		
		stub.setTemporalBonus(new Bonus(new ResourceSet(0,0,0,0,0,0,0),false, ' ', false, ' ', 0, new ResourceSet(0,0,0,0,0,0,0), false, false, 0, 1, false));
		stub.bonusRenounce("DOUBLECOUNCILREQUIREMENT");
		assertTrue(stub.getTemporalBonus().getCouncilBonusesNumber()==1);
		
		stub.setTemporalBonus(new Bonus(new ResourceSet(0,0,0,0,0,0,0),false, ' ', false, ' ', 0, new ResourceSet(0,0,0,0,0,0,0), false, false, 0, 2, false));
		stub.bonusRenounce("DOUBLECOUNCILREQUIREMENT");
		assertTrue(stub.getTemporalBonus().getCouncilBonusesNumber()==2);
		
		stub.setTemporalBonus(new Bonus(new ResourceSet(0,0,0,0,0,0,0),false, ' ', false, ' ', 0, new ResourceSet(0,0,0,0,0,0,0), false, false, 0, 3, true));
		stub.bonusRenounce("DOUBLECOUNCILREQUIREMENT");
		assertTrue(stub.getTemporalBonus().getCouncilBonusesNumber()==1);
		
		stub.setTemporalBonus(new Bonus(new ResourceSet(0,0,0,0,0,0,0),false, ' ', false, ' ', 0, new ResourceSet(0,0,0,0,0,0,0), false, false, 0, 1, false));
		stub.bonusRenounce("TRIPLECOUNCILREQUIREMENT");
		assertTrue(stub.getTemporalBonus().getCouncilBonusesNumber()==1);
		
		stub.setTemporalBonus(new Bonus(new ResourceSet(0,0,0,0,0,0,0),false, ' ', false, ' ', 0, new ResourceSet(0,0,0,0,0,0,0), false, false, 0, 3, false));
		stub.bonusRenounce("TRIPLECOUNCILREQUIREMENT");
		assertTrue(stub.getTemporalBonus().getCouncilBonusesNumber()==3);
		
		stub.setTemporalBonus(new Bonus(new ResourceSet(0,0,0,0,0,0,0),false, ' ', false, ' ', 0, new ResourceSet(0,0,0,0,0,0,0), false, false, 0, 4, true));
		stub.bonusRenounce("TRIPLECOUNCILREQUIREMENT");
		assertTrue(stub.getTemporalBonus().getCouncilBonusesNumber()==1);
		
		stub.setTemporalBonus(new Bonus(new ResourceSet(0,0,0,0,0,0,0),false, ' ', true, 'Y', 0, new ResourceSet(0,0,0,0,0,0,0), false, false, 0, 1, false));
		stub.bonusRenounce("BONUSTOWERACTION");
		assertFalse(stub.getTemporalBonus().hasBonusAction());
				
		stub.setTemporalBonus(new Bonus(new ResourceSet(0,0,0,0,0,0,0),false, ' ', false, ' ', 0, new ResourceSet(0,0,0,0,0,0,0), false, true, 0, 1, false));
		stub.bonusRenounce("BONUSHARVESTACTION");
		assertFalse(stub.getTemporalBonus().hasBonusHarvest());
		
		
		stub.setTemporalBonus(new Bonus(new ResourceSet(0,0,0,0,0,0,0),false, ' ', false, ' ', 0, new ResourceSet(0,0,0,0,0,0,0), true, false, 0, 1, false));
		stub.bonusRenounce("BONUSPRODUCTIONACTION");
		assertFalse(stub.getTemporalBonus().hasBonusProduction());
	}
	
	@Test
	public void testTowerActionWithChoice() throws RemoteException{
		
		//BadInput (temporalMessage==null)
		stub.towerActionWithChoice("1");
		
		//Performed
		stub.setTemporalMessage("TOWERACTION;GREEN;0;WHITE;0");
		stub.towerActionWithChoice("1");
		assertEquals(p.getGreenCardList().size(),1);
		
		//NotStrongEnough
		p.getResourceSet().setZero();
		p.acquireResources(new ResourceSet(0,0,3,0,0,0,0));
		stub.setTemporalMessage("TOWERACTION;GREEN;3;WHITE;0");
		stub.towerActionWithChoice("1");
		assertEquals(p.getGreenCardList().size(),1);
		
		//Not performed (temporalBonus==null)
		p.getResourceSet().setZero();
		p.acquireResources(new ResourceSet(0,0,3,3,0,0,0));
		stub.setTemporalMessage("BONUSTOWERACTION;3;0;GREEN");
		stub.towerActionWithChoice("1");
		assertEquals(p.getGreenCardList().size(),1);
		
		//Performed
		stub.setTemporalMessage("BONUSTOWERACTION;3;1;GREEN");
		stub.setTemporalBonus(new Bonus(new ResourceSet(0,0,0,0,0,0,0),false, ' ', true, 'G', 0, new ResourceSet(0,0,0,0,0,0,0), false, false, 0, 0, false));
		stub.towerActionWithChoice("1");
		assertEquals(p.getGreenCardList().size(),2);
		
		//BadInput
		stub.setTemporalMessage("BONUSTOWERACTION;3;rqg;GREEN");
		stub.setTemporalBonus(new Bonus(new ResourceSet(0,0,0,0,0,0,0),false, ' ', true, 'G', 0, new ResourceSet(0,0,0,0,0,0,0), false, false, 0, 0, false));
		stub.towerActionWithChoice("1");
		assertEquals(p.getGreenCardList().size(),2);
		
		//NotEnoughResources
		p.getResourceSet().setZero();
		stub.setTemporalMessage("TOWERACTION;YELLOW;0;WHITE;0");
		stub.towerActionWithChoice("1");
		assertEquals(p.getYellowCardList().size(),0);		
	}
	
	@Test
	public void testLeaderPlay() throws FullGameException, IOException{
		
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		Player p= new Player(new SocketUser(null),4,"102W 104St 105C 103Se 100VP 100MP 100FP"
				,"117","4;","56;","30;","77;74;","116;100;101","102");
		this.game.getStatus().addPlayer(p);
		this.game.createBoardTest();
		this.game.newRound();
		this.stub = new GameActionImpl(this.game,p);
		p.setIsActive(false);
		stub.leaderPlay("1");
		assertEquals(p.getLeadersInHand().size(),2);
		assertEquals(p.getLeadersPlayed().size(),2);
		
		//BadInput
		stub.leaderPlay("a");
		assertEquals(p.getLeadersInHand().size(),2);
		assertEquals(p.getLeadersPlayed().size(),2);
		
	}
	
	@Test
	public void testLeaderDiscard() throws IOException, FullGameException{
		
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		Player p= new Player(new SocketUser(null),4,"102W 104St 105C 103Se 100VP 100MP 100FP"
				,"117","4;","56;","30;","77;74;","116;100;101","102");
		this.game.getStatus().addPlayer(p);
		this.game.createBoardTest();
		this.game.newRound();
		this.stub = new GameActionImpl(this.game,p);
		p.setIsActive(false);
		stub.leaderDiscard("1");		
		assertEquals(p.getLeadersInHand().size(),2);
		assertEquals(p.getLeadersPlayed().size(),1);
		
		//BadInput
		stub.leaderDiscard("a");
		assertEquals(p.getLeadersInHand().size(),2);
		assertEquals(p.getLeadersPlayed().size(),1);
	}
	
	@Test
	public void testPass() throws RemoteException, FullGameException{
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		this.p = game.addPlayer(new SocketUser(null));
		Player p1 = game.addPlayer(new SocketUser(null));
		p.setIsActive(false);
		p1.setIsActive(false);
		game.createBoardTest();
		game.newRound();
		game.getStatus().createExcommunicationChoices();
		this.stub = new GameActionImpl(game, p);
		p.setIsActive(false);
		//TODO giocatore del turno corrente difficile da trovare per chiamare il passo, non è detto
		//che il giocatore nello stub sia quello che può chiamare il passo		
	}
	
	@Test
	public void testLeaderResources() throws IOException, FullGameException{
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		Player p= new Player(new SocketUser(null),4,"102W 104St 105C 103Se 100VP 100MP 100FP"
				,"117","4;","56;","30;","77;74;","116;100;101","100;102");
		this.game.getStatus().addPlayer(p);
		this.game.createBoardTest();
		this.game.newRound();
		this.stub = new GameActionImpl(this.game,p);
		p.setIsActive(false);
		p.setColour(PlayerColour.BLUE);
		p.getResourceSet().setZero();
		stub.leaderResources();
		assertEquals(p.getResourceSet().getCoin(),3);
	}
	
	@Test
	public void testLeaderProduction() throws IOException, FullGameException{
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		Player p= new Player(new SocketUser(null),4,"102W 104St 105C 103Se 100VP 100MP 100FP"
				,"117","4;","56;","30;","77;74;","116;100;101","102;107");
		this.game.getStatus().addPlayer(p);
		this.game.createBoardTest();
		this.game.newRound();
		this.stub = new GameActionImpl(this.game,p);
		p.setIsActive(false);
		p.setColour(PlayerColour.BLUE);
		stub.leaderProduction();
		assertTrue(stub.getTemporalBonus().hasBonusProduction());
	}
	
	@Test
	public void testLeaderHarvest() throws IOException, FullGameException{
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		Player p= new Player(new SocketUser(null),4,"102W 104St 105C 103Se 100VP 100MP 100FP"
				,"117","4;","56;","30;","77;74;","116;100;101","102;106");
		this.game.getStatus().addPlayer(p);
		this.game.createBoardTest();
		this.game.newRound();
		this.stub = new GameActionImpl(this.game,p);
		p.setIsActive(false);
		p.setColour(PlayerColour.BLUE);
		stub.leaderHarvest();
		assertTrue(stub.getTemporalBonus().hasBonusHarvest());
	}
	
	@Test
	public void testLeaderBonusStrenght() throws IOException, FullGameException{
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		Player p= new Player(new SocketUser(null),4,"102W 104St 105C 103Se 100VP 100MP 100FP"
				,"117","4;","56;","30;","77;74;","116;100;101","102;113");
		this.game.getStatus().addPlayer(p);
		this.game.createBoardTest();
		this.game.newRound();
		this.stub = new GameActionImpl(this.game,p);
		p.setIsActive(false);
		p.setColour(PlayerColour.BLUE);
		stub.leaderFamilyBonus("WHITE");
		assertEquals(p.getFamiliar("WHITE").getStrength(),6);
	}
	
	
}
