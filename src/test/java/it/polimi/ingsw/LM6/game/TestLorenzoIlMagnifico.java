package it.polimi.ingsw.LM6.game;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import it.polimi.ingsw.LM6.server.game.Action;
import it.polimi.ingsw.LM6.server.game.LorenzoIlMagnifico;
import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.game.bonuses.Bonus;
import it.polimi.ingsw.LM6.server.game.bonuses.PermanentBonusMalus;
import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;
import it.polimi.ingsw.LM6.server.game.exceptions.AlreadyOccupiedException;
import it.polimi.ingsw.LM6.server.game.exceptions.AlreadyUsedBonusException;
import it.polimi.ingsw.LM6.server.game.exceptions.AlreadyUsedFamiliarException;
import it.polimi.ingsw.LM6.server.game.exceptions.AnotherFamiliarSameColourException;
import it.polimi.ingsw.LM6.server.game.exceptions.CannotGoOnMarketException;
import it.polimi.ingsw.LM6.server.game.exceptions.DoubleCostException;
import it.polimi.ingsw.LM6.server.game.exceptions.FullGameException;
import it.polimi.ingsw.LM6.server.game.exceptions.InvalidActionException;
import it.polimi.ingsw.LM6.server.game.exceptions.LeaderException;
import it.polimi.ingsw.LM6.server.game.exceptions.LorenzoIlMagnificoException;
import it.polimi.ingsw.LM6.server.game.exceptions.NotEnoughMilitaryPointsException;
import it.polimi.ingsw.LM6.server.game.exceptions.NotEnoughResourcesException;
import it.polimi.ingsw.LM6.server.game.exceptions.NotStrongEnoughException;
import it.polimi.ingsw.LM6.server.game.exceptions.SixCardsException;
import it.polimi.ingsw.LM6.server.users.SocketUser;

public class TestLorenzoIlMagnifico {
	LorenzoIlMagnifico reloadedGame;
	LorenzoIlMagnifico game;
	public TestLorenzoIlMagnifico(){
		try {
			this.reloadedGame = LorenzoIlMagnifico.loadGame("filename1");
		} catch (IOException e) {
			//NOT VERIFIED
		}
	}
	@Test 
	public void testPlaceOnTower() throws FullGameException{
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		Player p1 = game.addPlayer(new SocketUser(null));
		Player p2 = game.addPlayer(new SocketUser(null));
		p1.setIsActive(false);
		p2.setIsActive(false);
		this.game.createBoardTest();
		this.game.newRound();
		p1.acquireResources(new ResourceSet(0,0,0,2,0,0,0));
		p2.acquireResources(new ResourceSet(0,0,0,2,0,0,0));
		String tmp = new String("TOWERACTION;GREEN;0;UNCOLOUR;2");
		String[] tmp2 = tmp.split(";");
		Action act = new Action(tmp2, p1, new ResourceSet(0,0,0,0,0,0,0));
		try {
			Bonus b = this.game.placeOnTower(act);
			assertTrue(b!=null);
		} catch (AlreadyUsedFamiliarException | AnotherFamiliarSameColourException | AlreadyOccupiedException
				| NotStrongEnoughException | NotEnoughResourcesException | NotEnoughMilitaryPointsException
				| SixCardsException | DoubleCostException e) {
			//it never happens
		}
	}
	
	@Test (expected=it.polimi.ingsw.LM6.server.game.exceptions.AnotherFamiliarSameColourException.class)
	public void testPlaceOnTowerAnotherFamiliarException() throws AnotherFamiliarSameColourException, FullGameException{
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		Player p1 = game.addPlayer(new SocketUser(null));
		Player p2 = game.addPlayer(new SocketUser(null));
		p1.setIsActive(false);
		p2.setIsActive(false);
		this.game.createBoardTest();
		this.game.newRound();
		p1.acquireResources(new ResourceSet(0,0,0,3,0,0,0));
		p2.acquireResources(new ResourceSet(0,0,0,3,0,0,0));
		String tmp = new String("TOWERACTION;GREEN;0;BLACK;0");
		String tmp3 = new String("TOWERACTION;GREEN;1;WHITE;3");
		String[] tmp2 = tmp.split(";");
		String[] tmp4 = tmp3.split(";");
		Action act = new Action(tmp2, p1, new ResourceSet(0,0,0,0,0,0,0));
		Action act2 = new Action(tmp4, p1, new ResourceSet(0,0,0,0,0,0,0));
		try {
			this.game.placeOnTower(act);
			this.game.placeOnTower(act2);	
		} catch (AlreadyUsedFamiliarException | AlreadyOccupiedException
				| NotStrongEnoughException | NotEnoughResourcesException | NotEnoughMilitaryPointsException
				| SixCardsException | DoubleCostException e) {
			//it never happens
		}
	}
	
	@Test (expected=it.polimi.ingsw.LM6.server.game.exceptions.NotStrongEnoughException.class)
	public void testPlaceOnTowerNotStrongEnoughException() throws FullGameException, NotStrongEnoughException{
		LorenzoIlMagnifico games = new LorenzoIlMagnifico(false, 4, "ucgd");
		Player p1 = games.addPlayer(new SocketUser(null));
		Player p2 = games.addPlayer(new SocketUser(null));
		p1.setIsActive(false);
		p2.setIsActive(false);
		games.createBoardTest();
		games.newRound();
		p1.getResourceSet().setZero();
		p1.acquireResources(new ResourceSet(10,10,10,10,10,10,10));
		String tmp = new String("TOWERACTION;GREEN;3;WHITE;0");
		String[] tmp2 = tmp.split(";");
		Action act = new Action(tmp2, p1, new ResourceSet(0,0,0,0,0,0,0));
		try {
			games.placeOnTower(act);			
		} catch (AlreadyUsedFamiliarException | AnotherFamiliarSameColourException | AlreadyOccupiedException
				| NotEnoughResourcesException | NotEnoughMilitaryPointsException
				| SixCardsException | DoubleCostException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	@Test (expected=it.polimi.ingsw.LM6.server.game.exceptions.AlreadyOccupiedException.class)
	public void testPlaceOnTowerAlreadyOccupiedException() throws FullGameException, AlreadyOccupiedException{
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		Player p1 = game.addPlayer(new SocketUser(null));
		Player p2 = game.addPlayer(new SocketUser(null));
		p1.setIsActive(false);
		p2.setIsActive(false);
		this.game.createBoardTest();
		this.game.newRound();
		p1.acquireResources(new ResourceSet(0,0,0,2,0,0,0));
		String tmp = new String("TOWERACTION;GREEN;0;UNCOLOUR;2");
		String[] tmp2 = tmp.split(";");
		String tmp3 = new String("TOWERACTION;GREEN;0;WHITE;0");
		String[] tmp4 = tmp3.split(";");
		Action act = new Action(tmp2, p1, new ResourceSet(0,0,0,0,0,0,0));
		Action act2 = new Action(tmp4, p1, new ResourceSet(0,0,0,0,0,0,0));		
		try {
			this.game.placeOnTower(act);
			this.game.placeOnTower(act2);
		} catch (AlreadyUsedFamiliarException | AnotherFamiliarSameColourException | NotStrongEnoughException | NotEnoughResourcesException | NotEnoughMilitaryPointsException
				| SixCardsException | DoubleCostException e) {
			//it never happens
		}
	}
	
	@Test
	public void testHarvestAction(){
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		try{
			Player p1 = game.addPlayer(new SocketUser(null));
			Player p2 = game.addPlayer(new SocketUser(null));
			p1.setIsActive(false);
			p2.setIsActive(false);
			this.game.createBoardTest();
			this.game.newRound();
			String tmp = new String("HARVESTACTION;WHITE;0;1");
			String[] tmp2 = tmp.split(";");
			Action act = new Action(tmp2, p1, new ResourceSet(0,0,0,0,0,0,0));
			try {
				Bonus b = this.game.placeFamiliarHarvest(act);
				assertTrue(b!=null);
			} catch (AlreadyUsedFamiliarException | NotStrongEnoughException | AnotherFamiliarSameColourException
					| AlreadyOccupiedException | NotEnoughResourcesException e) {
				//it never happens
			}
		} catch(FullGameException e){
			// it never happens
		}
	}
	
	@Test (expected=it.polimi.ingsw.LM6.server.game.exceptions.AlreadyUsedFamiliarException.class)
	public void testHarvestActionAlreadyUsedFamiliarException() throws AlreadyUsedFamiliarException{
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		try{
			Player p1 = game.addPlayer(new SocketUser(null));
			Player p2 = game.addPlayer(new SocketUser(null));
			p1.setIsActive(false);
			p2.setIsActive(false);
			this.game.createBoardTest();
			this.game.newRound();
			p1.acquireResources(new ResourceSet(0,0,0,6,0,0,0));
			String tmp = new String("HARVESTACTION;WHITE;3;1");
			String[] tmp2 = tmp.split(";");
			String tmp3 = new String("HARVESTACTION;WHITE;3;1");
			String[] tmp4 = tmp3.split(";");
			Action act = new Action(tmp2, p1, new ResourceSet(0,0,0,0,0,0,0));
			Action act2 = new Action(tmp4, p1, new ResourceSet(0,0,0,0,0,0,0));
			try {
				this.game.placeFamiliarHarvest(act);
				this.game.placeFamiliarHarvest(act2);
			} catch (NotStrongEnoughException | AnotherFamiliarSameColourException
					| AlreadyOccupiedException | NotEnoughResourcesException e) {
				System.out.println(e.getMessage());
			}
		} catch(FullGameException e){
			// it never happens
		}
	}
	
	@Test (expected=it.polimi.ingsw.LM6.server.game.exceptions.NotStrongEnoughException.class)
	public void testHarvestActionNotStrongEnoughException() throws NotStrongEnoughException{
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		try{
			Player p1 = game.addPlayer(new SocketUser(null));
			Player p2 = game.addPlayer(new SocketUser(null));
			p1.setIsActive(false);
			p2.setIsActive(false);
			this.game.createBoardTest();
			this.game.newRound();
			String tmp = new String("HARVESTACTION;UNCOLOUR;0;1");
			String[] tmp2 = tmp.split(";");
			Action act = new Action(tmp2, p1, new ResourceSet(0,0,0,0,0,0,0));
			try {
				this.game.placeFamiliarHarvest(act);				
			} catch (AlreadyUsedFamiliarException | AnotherFamiliarSameColourException
					| AlreadyOccupiedException | NotEnoughResourcesException e) {
				//it never happens
			}
		} catch(FullGameException e){
			// it never happens
		}
	}	

	@Test (expected=it.polimi.ingsw.LM6.server.game.exceptions.AnotherFamiliarSameColourException.class)
	public void testHarvestActionAnotherFamiliarSameColourException() throws AnotherFamiliarSameColourException{
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		try{
			Player p1 = game.addPlayer(new SocketUser(null));
			Player p2 = game.addPlayer(new SocketUser(null));
			Player p3 = game.addPlayer(new SocketUser(null));
			Player p4 = game.addPlayer(new SocketUser(null));
			p1.setIsActive(false);
			p2.setIsActive(false);
			p3.setIsActive(false);
			p4.setIsActive(false);
			this.game.createBoardTest();
			this.game.newRound();
			p1.getResourceSet().setZero();
			p1.acquireResources(new ResourceSet(0,0,0,6,0,0,0));
			String tmp = new String("HARVESTACTION;WHITE;3;2");
			String[] tmp2 = tmp.split(";");
			String tmp3 = new String("HARVESTACTION;BLACK;3;2");
			String[] tmp4 = tmp3.split(";");
			Action act = new Action(tmp2, p1, new ResourceSet(0,0,0,0,0,0,0));
			Action act2 = new Action(tmp4, p1, new ResourceSet(0,0,0,0,0,0,0));
			
			try {
				this.game.placeFamiliarHarvest(act);	
				this.game.placeFamiliarHarvest(act2);
			} catch (AlreadyUsedFamiliarException | AlreadyOccupiedException | NotEnoughResourcesException | NotStrongEnoughException e) {
				System.out.println(e.getMessage());
			}
		} catch(FullGameException e){
			// it never happens
		}
	}
	
	@Test (expected=it.polimi.ingsw.LM6.server.game.exceptions.AlreadyOccupiedException.class)
	public void testHarvestActionAlreadyOccupiedException() throws AlreadyOccupiedException{
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		try{
			Player p1 = game.addPlayer(new SocketUser(null));
			Player p2 = game.addPlayer(new SocketUser(null));
			p1.setIsActive(false);
			p2.setIsActive(false);
			this.game.createBoardTest();
			this.game.newRound();
			String tmp = new String("HARVESTACTION;ORANGE;0;1");
			String[] tmp2 = tmp.split(";");
			String tmp3 = new String("HARVESTACTION;WHITE;0;1");
			String[] tmp4 = tmp3.split(";");
			Action act = new Action(tmp2, p1, new ResourceSet(0,0,0,0,0,0,0));
			Action act2 = new Action(tmp4, p1, new ResourceSet(0,0,0,0,0,0,0));
			try {
				this.game.placeFamiliarHarvest(act);
				this.game.placeFamiliarHarvest(act2);
			} catch (AlreadyUsedFamiliarException | AnotherFamiliarSameColourException
					| NotEnoughResourcesException | NotStrongEnoughException e) {
				System.out.println(e.getMessage());
			}
		} catch(FullGameException e){
			// it never happens
		}
	}	
	
	@Test 
	public void testProductionAction(){
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		try{
			Player p1 = game.addPlayer(new SocketUser(null));
			Player p2 = game.addPlayer(new SocketUser(null));
			p1.setIsActive(false);
			p2.setIsActive(false);
			this.game.createBoardTest();
			this.game.newRound();
			String tmp = new String("PRODUCTIONACTION;WHITE;0;1;0;0;0;0;0;0");
			String[] tmp2 = tmp.split(";");
			Action act = new Action(tmp2, p1, new ResourceSet(0,0,0,0,0,0,0));
			try {
				Bonus b = this.game.placeFamiliarProduction(act);
				assertTrue(b!=null);
			} catch (AlreadyUsedFamiliarException | NotStrongEnoughException | AnotherFamiliarSameColourException
					| AlreadyOccupiedException | NotEnoughResourcesException e) {
				//it never happens
			}
		} catch(FullGameException e){
			// it never happens
		}
	}
	
	@Test (expected=it.polimi.ingsw.LM6.server.game.exceptions.AlreadyUsedFamiliarException.class)
	public void testProductionActionAlreadyUsedFamiliarException() throws AlreadyUsedFamiliarException{
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		try{
			Player p1 = game.addPlayer(new SocketUser(null));
			Player p2 = game.addPlayer(new SocketUser(null));
			p1.setIsActive(false);
			p2.setIsActive(false);
			this.game.createBoardTest();
			this.game.newRound();
			String tmp = new String("PRODUCTIONACTION;WHITE;0;1;0;0;0;0;0;0");
			String[] tmp2 = tmp.split(";");
			Action act = new Action(tmp2, p1, new ResourceSet(0,0,0,0,0,0,0));
			String tmp3 = new String("PRODUCTIONACTION;WHITE;0;1;0;0;0;0;0;0");
			String[] tmp4 = tmp3.split(";");
			Action act2 = new Action(tmp4, p1, new ResourceSet(0,0,0,0,0,0,0));
			try {
				this.game.placeFamiliarProduction(act);
				this.game.placeFamiliarProduction(act2);
			} catch (NotStrongEnoughException | AnotherFamiliarSameColourException
					| AlreadyOccupiedException | NotEnoughResourcesException e) {
				//it never happens
			}
		} catch(FullGameException e){
			// it never happens
		}
	}
	
	@Test (expected=it.polimi.ingsw.LM6.server.game.exceptions.AnotherFamiliarSameColourException.class)
	public void testProductionActionAnotherFamiliarSameColourException() throws AnotherFamiliarSameColourException{
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		try{
			Player p1 = game.addPlayer(new SocketUser(null));
			Player p2 = game.addPlayer(new SocketUser(null));
			Player p3 = game.addPlayer(new SocketUser(null));
			Player p4 = game.addPlayer(new SocketUser(null));
			p1.setIsActive(false);
			p2.setIsActive(false);
			p3.setIsActive(false);
			p4.setIsActive(false);
			this.game.createBoardTest();
			this.game.newRound();
			p1.getResourceSet().setZero();
			p1.acquireResources(new ResourceSet(0,0,0,6,0,0,0));
			String tmp = new String("PRODUCTIONACTION;WHITE;3;2;0;0;0;0;0;0");
			String[] tmp2 = tmp.split(";");
			String tmp3 = new String("PRODUCTIONACTION;ORANGE;3;2;0;0;0;0;0;0");
			String[] tmp4 = tmp3.split(";");
			Action act = new Action(tmp2, p1, new ResourceSet(0,0,0,0,0,0,0));
			Action act2 = new Action(tmp4, p1, new ResourceSet(0,0,0,0,0,0,0));
			
			try {
				this.game.placeFamiliarProduction(act);	
				this.game.placeFamiliarProduction(act2);
			} catch (AlreadyUsedFamiliarException | AlreadyOccupiedException | NotEnoughResourcesException | NotStrongEnoughException e) {
				System.out.println(e.getMessage());
			}
		} catch(FullGameException e){
			// it never happens
		}
	}
	
	@Test (expected=it.polimi.ingsw.LM6.server.game.exceptions.NotStrongEnoughException.class)
	public void testProductionActionNotStrongEnoughException() throws NotStrongEnoughException{
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		try{
			Player p1 = game.addPlayer(new SocketUser(null));
			Player p2 = game.addPlayer(new SocketUser(null));
			p1.setIsActive(false);
			p2.setIsActive(false);
			this.game.createBoardTest();
			this.game.newRound();
			String tmp = new String("PRODUCTIONACTION;UNCOLOUR;0;1;0;0;0;0;0;0");
			String[] tmp2 = tmp.split(";");
			Action act = new Action(tmp2, p1, new ResourceSet(0,0,0,0,0,0,0));
			try {
				this.game.placeFamiliarHarvest(act);				
			} catch (AlreadyUsedFamiliarException | AnotherFamiliarSameColourException
					| AlreadyOccupiedException | NotEnoughResourcesException e) {
				//it never happens
			}
		} catch(FullGameException e){
			// it never happens
		}
	}
	
	@Test (expected=it.polimi.ingsw.LM6.server.game.exceptions.AlreadyOccupiedException.class)
	public void testProductionActionAlreadyOccupiedException() throws AlreadyOccupiedException{
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		try{
			Player p1 = game.addPlayer(new SocketUser(null));
			Player p2 = game.addPlayer(new SocketUser(null));
			p1.setIsActive(false);
			p2.setIsActive(false);
			this.game.createBoardTest();
			this.game.newRound();
			String tmp = new String("PRODUCTIONACTION;WHITE;0;1;0;0;0;0;0;0");
			String[] tmp2 = tmp.split(";");
			String tmp3 = new String("PRODUCTIONACTION;ORANGE;0;1;0;0;0;0;0;0");
			String[] tmp4 = tmp3.split(";");
			Action act = new Action(tmp2, p1, new ResourceSet(0,0,0,0,0,0,0));
			Action act2 = new Action(tmp4, p1, new ResourceSet(0,0,0,0,0,0,0));
			try {
				this.game.placeFamiliarProduction(act);
				this.game.placeFamiliarProduction(act2);
			} catch (AlreadyUsedFamiliarException | AnotherFamiliarSameColourException
					| NotEnoughResourcesException | NotStrongEnoughException e) {
				//it never happens
			}
		} catch(FullGameException e){
			// it never happens
		}
	}
	
	@Test
	public void testCouncilAction(){
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		try{
			Player p1 = game.addPlayer(new SocketUser(null));
			Player p2 = game.addPlayer(new SocketUser(null));
			p1.setIsActive(false);
			p2.setIsActive(false);
			this.game.createBoardTest();
			this.game.newRound();
			String tmp = new String("COUNCILACTION;WHITE;0");
			String[] tmp2 = tmp.split(";");
			Action act = new Action(tmp2, p1, new ResourceSet(0,0,0,0,0,0,0));
			try {
				Bonus b = this.game.placeOnCouncil(act);
				assertTrue(b!=null);
			} catch (NotStrongEnoughException | AlreadyUsedFamiliarException | NotEnoughResourcesException e) {
				//it never happens
			}
		} catch(FullGameException e){
			// it never happens
		}
	}
	
	@Test (expected=it.polimi.ingsw.LM6.server.game.exceptions.NotStrongEnoughException.class)
	public void testCouncilActionNotStrongEnoughException() throws NotStrongEnoughException{
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		try{
			Player p1 = game.addPlayer(new SocketUser(null));
			Player p2 = game.addPlayer(new SocketUser(null));
			p1.setIsActive(false);
			p2.setIsActive(false);
			this.game.createBoardTest();
			this.game.newRound();
			String tmp = new String("COUNCILACTION;UNCOLOUR;0");
			String[] tmp2 = tmp.split(";");
			Action act = new Action(tmp2, p1, new ResourceSet(0,0,0,0,0,0,0));
			try {
				this.game.placeOnCouncil(act);
			} catch (AlreadyUsedFamiliarException | NotEnoughResourcesException e) {
				//it never happens
			}
		} catch(FullGameException e){
			// it never happens
		}
	}
	
	@Test (expected=it.polimi.ingsw.LM6.server.game.exceptions.AlreadyUsedFamiliarException.class)
	public void testCouncilActionAlreadyUsedFamiliarException() throws AlreadyUsedFamiliarException{
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		try{
			Player p1 = game.addPlayer(new SocketUser(null));
			Player p2 = game.addPlayer(new SocketUser(null));
			p1.setIsActive(false);
			p2.setIsActive(false);
			this.game.createBoardTest();
			this.game.newRound();
			String tmp = new String("COUNCILACTION;WHITE;0");
			String[] tmp2 = tmp.split(";");
			Action act = new Action(tmp2, p1, new ResourceSet(0,0,0,0,0,0,0));
			String tmp3 = new String("COUNCILACTION;WHITE;0");
			String[] tmp4 = tmp3.split(";");
			Action act2 = new Action(tmp4, p1, new ResourceSet(0,0,0,0,0,0,0));
			try {
				this.game.placeOnCouncil(act);
				this.game.placeOnCouncil(act2);
			} catch (NotEnoughResourcesException | NotStrongEnoughException e) {
				//it never happens
			}
		} catch(FullGameException e){
			// it never happens
		}
	}
	
	@Test (expected=it.polimi.ingsw.LM6.server.game.exceptions.NotEnoughResourcesException.class)
	public void testCouncilActionNotEnoughResourcesException() throws NotEnoughResourcesException{
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		try{
			Player p1 = game.addPlayer(new SocketUser(null));
			Player p2 = game.addPlayer(new SocketUser(null));
			p1.setIsActive(false);
			p2.setIsActive(false);
			this.game.createBoardTest();
			this.game.newRound();
			p1.getResourceSet().setZero();
			String tmp = new String("COUNCILACTION;WHITE;1");
			String[] tmp2 = tmp.split(";");
			Action act = new Action(tmp2, p1, new ResourceSet(0,0,0,0,0,0,0));
			try {
				this.game.placeOnCouncil(act);
			} catch (NotStrongEnoughException | AlreadyUsedFamiliarException e) {
				//it never happens
			}
		} catch(FullGameException e){
			// it never happens
		}
	}
	
	@Test
	public void testMarketAction(){
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		try{
			Player p1 = game.addPlayer(new SocketUser(null));
			Player p2 = game.addPlayer(new SocketUser(null));
			p1.setIsActive(false);
			p2.setIsActive(false);
			this.game.createBoardTest();
			this.game.newRound();
			String tmp = new String("MARKETACTION;1;WHITE;0");
			String[] tmp2 = tmp.split(";");
			Action act = new Action(tmp2, p1, new ResourceSet(0,0,0,0,0,0,0));
			Bonus b = this.game.placeOnMarket(act);
			assertTrue(b!=null);
		} catch(FullGameException | NotStrongEnoughException | AlreadyUsedFamiliarException | NotEnoughResourcesException | AlreadyOccupiedException | CannotGoOnMarketException e){
			// it never happens
		}
	}
	
	@Test (expected=it.polimi.ingsw.LM6.server.game.exceptions.NotStrongEnoughException.class)
	public void testMarketActionNotStrongEnoughException() throws NotStrongEnoughException{
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		try{
			Player p1 = game.addPlayer(new SocketUser(null));
			Player p2 = game.addPlayer(new SocketUser(null));
			p1.setIsActive(false);
			p2.setIsActive(false);
			this.game.createBoardTest();
			this.game.newRound();
			String tmp = new String("MARKETACTION;1;UNCOLOUR;0");
			String[] tmp2 = tmp.split(";");
			Action act = new Action(tmp2, p1, new ResourceSet(0,0,0,0,0,0,0));
			Bonus b = this.game.placeOnMarket(act);
			assertTrue(b!=null);
		} catch(FullGameException | AlreadyUsedFamiliarException | NotEnoughResourcesException | AlreadyOccupiedException | CannotGoOnMarketException e){
			// it never happens
		}
	}
	
	@Test (expected=it.polimi.ingsw.LM6.server.game.exceptions.AlreadyUsedFamiliarException.class)
	public void testMarketActionAlreadyUsedFamiliarException() throws AlreadyUsedFamiliarException{
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		try{
			Player p1 = game.addPlayer(new SocketUser(null));
			Player p2 = game.addPlayer(new SocketUser(null));
			p1.setIsActive(false);
			p2.setIsActive(false);
			this.game.createBoardTest();
			this.game.newRound();
			String tmp = new String("MARKETACTION;1;WHITE;0");
			String[] tmp2 = tmp.split(";");
			Action act = new Action(tmp2, p1, new ResourceSet(0,0,0,0,0,0,0));
			String tmp3 = new String("MARKETACTION;1;WHITE;0");
			String[] tmp4 = tmp3.split(";");
			Action act2 = new Action(tmp4, p1, new ResourceSet(0,0,0,0,0,0,0));
			this.game.placeOnMarket(act);
			this.game.placeOnMarket(act2);
		} catch(FullGameException | NotEnoughResourcesException | AlreadyOccupiedException | CannotGoOnMarketException | NotStrongEnoughException e){
			// it never happens
		}
	}
	
	@Test (expected=it.polimi.ingsw.LM6.server.game.exceptions.AlreadyOccupiedException.class)
	public void testMarketActionAlreadyOccupiedException() throws AlreadyOccupiedException{
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		try{
			Player p1 = game.addPlayer(new SocketUser(null));
			Player p2 = game.addPlayer(new SocketUser(null));
			p1.setIsActive(false);
			p2.setIsActive(false);
			this.game.createBoardTest();
			this.game.newRound();
			String tmp = new String("MARKETACTION;1;WHITE;0");
			String[] tmp2 = tmp.split(";");
			Action act = new Action(tmp2, p1, new ResourceSet(0,0,0,0,0,0,0));
			String tmp3 = new String("MARKETACTION;1;ORANGE;0");
			String[] tmp4 = tmp3.split(";");
			Action act2 = new Action(tmp4, p1, new ResourceSet(0,0,0,0,0,0,0));
			this.game.placeOnMarket(act);
			this.game.placeOnMarket(act2);
		} catch(FullGameException | NotEnoughResourcesException | CannotGoOnMarketException | NotStrongEnoughException | AlreadyUsedFamiliarException e){
			// it never happens
		}
	}
	
	@Test (expected=it.polimi.ingsw.LM6.server.game.exceptions.NotEnoughResourcesException.class)
	public void testMarketActionNotEnoughResourcesException() throws NotEnoughResourcesException{
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		try{
			Player p1 = game.addPlayer(new SocketUser(null));
			Player p2 = game.addPlayer(new SocketUser(null));
			p1.setIsActive(false);
			p2.setIsActive(false);
			this.game.createBoardTest();
			this.game.newRound();
			p1.getResourceSet().setZero();
			String tmp = new String("MARKETACTION;1;WHITE;2");
			String[] tmp2 = tmp.split(";");
			Action act = new Action(tmp2, p1, new ResourceSet(0,0,0,0,0,0,0));
			this.game.placeOnMarket(act);
		} catch(FullGameException | CannotGoOnMarketException | NotStrongEnoughException | AlreadyUsedFamiliarException | AlreadyOccupiedException e){
			// it never happens
		}
	}
	
	
	@Test (expected=it.polimi.ingsw.LM6.server.game.exceptions.CannotGoOnMarketException.class)
	public void testMarketActionCannotGoOnMarketException() throws CannotGoOnMarketException{
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		try{
			Player p1 = game.addPlayer(new SocketUser(null));
			Player p2 = game.addPlayer(new SocketUser(null));
			p1.setIsActive(false);
			p2.setIsActive(false);
			this.game.createBoardTest();
			this.game.newRound();
			p1.getResourceSet().setZero();
			String tmp = new String("MARKETACTION;1;WHITE;0");
			String[] tmp2 = tmp.split(";");
			Action act = new Action(tmp2, p1, new ResourceSet(0,0,0,0,0,0,0));
			p1.acquirePermBonus(new PermanentBonusMalus(0,0,0,0,0,0,0,0,0,0,"cannotUseMarketMalus"));
			this.game.placeOnMarket(act);
		} catch(FullGameException | NotStrongEnoughException | AlreadyUsedFamiliarException | AlreadyOccupiedException | NotEnoughResourcesException e){
			// it never happens
		}
	}
	
	@Test 
	public void testBonusProductionAction() {
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		try{
			Player p1 = game.addPlayer(new SocketUser(null));
			Player p2 = game.addPlayer(new SocketUser(null));
			p1.setIsActive(false);
			p2.setIsActive(false);
			this.game.createBoardTest();
			this.game.newRound();
			p1.getResourceSet().setZero();
			p1.acquireResources(new ResourceSet(0,0,0,1,0,0,0));
			String tmp = new String("BONUSPRODUCTIONACTION;1;0;0;0;0;0;0;0");
			String[] tmp2 = tmp.split(";");
			Action act = new Action(tmp2, p1, new ResourceSet(0,0,0,0,0,0,0));
			Bonus b = this.game.placeFamiliarProduction(act);
			assertTrue(b!=null);
		} catch(FullGameException | AlreadyUsedFamiliarException | NotStrongEnoughException | AnotherFamiliarSameColourException | AlreadyOccupiedException | NotEnoughResourcesException e){
			// it never happens
		}
	}
	
	@Test (expected=it.polimi.ingsw.LM6.server.game.exceptions.NotEnoughResourcesException.class)
	public void testBonusProductionActionNotEnoughResourcesException() throws NotEnoughResourcesException {
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		try{
			Player p1 = game.addPlayer(new SocketUser(null));
			Player p2 = game.addPlayer(new SocketUser(null));
			p1.setIsActive(false);
			p2.setIsActive(false);
			this.game.createBoardTest();
			this.game.newRound();
			p1.getResourceSet().setZero();
			p1.acquireResources(new ResourceSet(0,0,0,0,0,0,0));
			String tmp = new String("BONUSPRODUCTIONACTION;1;0;0;0;0;0;0;0");
			String[] tmp2 = tmp.split(";");
			Action act = new Action(tmp2, p1, new ResourceSet(0,0,0,0,0,0,0));
			Bonus b = this.game.placeFamiliarProduction(act);
			assertTrue(b!=null);
		} catch(FullGameException | AlreadyUsedFamiliarException | AnotherFamiliarSameColourException | AlreadyOccupiedException | NotStrongEnoughException e){
			//it never happens
		}
	}
	
	@Test (expected=it.polimi.ingsw.LM6.server.game.exceptions.NotStrongEnoughException.class)
	public void testBonusProductionActionNotStrongEnoughException() throws NotStrongEnoughException{
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		try{
			Player p1 = game.addPlayer(new SocketUser(null));
			Player p2 = game.addPlayer(new SocketUser(null));
			p1.setIsActive(false);
			p2.setIsActive(false);
			this.game.createBoardTest();
			this.game.newRound();
			p1.getResourceSet().setZero();
			p1.acquireResources(new ResourceSet(0,0,0,0,0,0,0));
			String tmp = new String("BONUSPRODUCTIONACTION;0;0;0;0;0;0;0");
			String[] tmp2 = tmp.split(";");
			Action act = new Action(tmp2, p1, new ResourceSet(0,0,0,0,0,0,0));
			Bonus b = this.game.placeFamiliarProduction(act);
			assertTrue(b!=null);
			assertEquals(act.getProductionChoice(3),0);
		} catch(FullGameException | AlreadyUsedFamiliarException | AnotherFamiliarSameColourException | AlreadyOccupiedException | NotEnoughResourcesException e){
			//it never happens
		}
	}
	
	@Test 
	public void testBonusHarvestAction() {
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		try{
			Player p1 = game.addPlayer(new SocketUser(null));
			Player p2 = game.addPlayer(new SocketUser(null));
			p1.setIsActive(false);
			p2.setIsActive(false);
			this.game.createBoardTest();
			this.game.newRound();
			p1.getResourceSet().setZero();
			p1.acquireResources(new ResourceSet(0,0,0,1,0,0,0));
			String tmp = new String("BONUSHARVESTACTION;1"+";0");
			String[] tmp2 = tmp.split(";");
			Action act = new Action(tmp2, p1, new ResourceSet(0,0,0,0,0,0,0));
			Bonus b = this.game.placeFamiliarHarvest(act);
			assertTrue(b!=null);
		} catch(FullGameException | AlreadyUsedFamiliarException | NotStrongEnoughException | AnotherFamiliarSameColourException | AlreadyOccupiedException | NotEnoughResourcesException e){
			// it never happens
		}
	}
	
	@Test (expected=it.polimi.ingsw.LM6.server.game.exceptions.NotStrongEnoughException.class)
	public void testBonusHarvestActionNotStrongEnoughException() throws NotStrongEnoughException {
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		try{
			Player p1 = game.addPlayer(new SocketUser(null));
			Player p2 = game.addPlayer(new SocketUser(null));
			p1.setIsActive(false);
			p2.setIsActive(false);
			this.game.createBoardTest();
			this.game.newRound();
			p1.getResourceSet().setZero();
			p1.acquireResources(new ResourceSet(0,0,0,0,0,0,0));
			String tmp = new String("BONUSHARVESTACTION;0"+";0");
			String[] tmp2 = tmp.split(";");
			Action act = new Action(tmp2, p1, new ResourceSet(0,0,0,0,0,0,0));
			Bonus b = this.game.placeFamiliarHarvest(act);
			assertTrue(b!=null);
		} catch(FullGameException | AlreadyUsedFamiliarException | AnotherFamiliarSameColourException | AlreadyOccupiedException | NotEnoughResourcesException e){
			// it never happens
		}
	}
	
	@Test (expected=it.polimi.ingsw.LM6.server.game.exceptions.NotEnoughResourcesException.class)
	public void testBonusHarvestActionNotEnoughResourcesException() throws NotEnoughResourcesException {
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		try{
			Player p1 = game.addPlayer(new SocketUser(null));
			Player p2 = game.addPlayer(new SocketUser(null));
			p1.setIsActive(false);
			p2.setIsActive(false);
			this.game.createBoardTest();
			this.game.newRound();
			p1.getResourceSet().setZero();
			p1.acquireResources(new ResourceSet(0,0,0,0,0,0,0));
			String tmp = new String("BONUSHARVESTACTION;1"+";0");
			String[] tmp2 = tmp.split(";");
			Action act = new Action(tmp2, p1, new ResourceSet(0,0,0,0,0,0,0));
			Bonus b = this.game.placeFamiliarHarvest(act);
			assertTrue(b!=null);
		} catch(FullGameException | AlreadyUsedFamiliarException | AnotherFamiliarSameColourException | AlreadyOccupiedException | NotStrongEnoughException e){
			// it never happens
		}
	}
	
	@Test 
	public void testGetCouncilBonus(){
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		try {
			Player p1 = game.addPlayer(new SocketUser(null));
			p1.setIsActive(false);
			this.game.createBoardTest();
			this.game.newRound();
			String tmp = new String("COUNCILREQUIREMENT;5");
			String[] tmp2 = tmp.split(";");
			Action act = new Action(tmp2, p1, new ResourceSet(0,0,0,0,0,0,0));
			try {
				Bonus b = game.getCouncilBonus(act);
				ResourceSet res = b.getResourceSetFor(p1);
				assertEquals(res.getStone(),0);
				assertEquals(res.getWood(),0);
				tmp = new String("DOUBLECOUNCILREQUIREMENT;1;2");
				tmp2 = tmp.split(";");
				act = new Action(tmp2, p1, new ResourceSet(0,0,0,0,0,0,0));
				b = game.getCouncilBonus(act);
				res = b.getResourceSetFor(p1);
				assertEquals(res.getStone(),1);
				assertEquals(res.getWood(),1);
				assertEquals(res.getServants(),2);
				assertEquals(act.getProductionChoice(1),2);
				tmp = new String("TRIPLECOUNCILREQUIREMENT;1;2;3");
				tmp2 = tmp.split(";");
				act = new Action(tmp2, p1, new ResourceSet(0,0,0,0,0,0,0));
				b = game.getCouncilBonus(act);
				res = b.getResourceSetFor(p1);
				assertEquals(res.getStone(),1);
				assertEquals(res.getWood(),1);
				assertEquals(res.getServants(),2);
				assertEquals(res.getMilitaryPoints(),0);
				assertEquals(act.getProductionChoice(2),3);
			} catch (InvalidActionException e) {
				// it never happens
			}
		} catch (FullGameException e) {
			//it never happens
		}
	}
	
	@Test (expected=it.polimi.ingsw.LM6.server.game.exceptions.AlreadyUsedBonusException.class)
	public void testGetEveryTurnResourcesException() throws AlreadyUsedBonusException{
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		try {
			Player p1 = game.addPlayer(new SocketUser(null));
			p1.setIsActive(false);
			this.game.createBoardTest();
			this.game.newRound();
			this.game.getEveryTurnResources(p1);
		} catch (FullGameException e) {
			//it never happens
		}
	}
	
	@Test (expected=it.polimi.ingsw.LM6.server.game.exceptions.AlreadyUsedBonusException.class)
	public void testGetEveryTurnHarvestException() throws AlreadyUsedBonusException{
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		try {
			Player p1 = game.addPlayer(new SocketUser(null));
			p1.setIsActive(false);
			this.game.createBoardTest();
			this.game.newRound();
			this.game.getEveryTurnHarvest(p1);
		} catch (FullGameException e) {
			//it never happens
		}
	}	
	
	
	@Test 
	public void testPlaceOnTowerAlreadyOccupiedTower() throws FullGameException{
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		Player p1 = game.addPlayer(new SocketUser(null));
		Player p2 = game.addPlayer(new SocketUser(null));
		p1.setIsActive(false);
		p2.setIsActive(false);
		this.game.createBoardTest();
		this.game.newRound();
		p1.getResourceSet().setZero();
		p2.getResourceSet().setZero();
		p1.acquireResources(new ResourceSet(0,0,0,2,0,0,0));
		p2.acquireResources(new ResourceSet(0,0,0,3,0,0,0));
		String tmp = new String("TOWERACTION;GREEN;0;UNCOLOUR;2");
		String[] tmp2 = tmp.split(";");
		Action act = new Action(tmp2, p1, new ResourceSet(0,0,0,0,0,0,0));
		String tmp3 = new String("TOWERACTION;GREEN;1;UNCOLOUR;3");
		String[] tmp4 = tmp3.split(";");
		Action act2 = new Action(tmp4, p2, new ResourceSet(0,0,0,0,0,0,0));
		try {
			this.game.placeOnTower(act);
			Bonus b = game.placeOnTower(act2);
			assertTrue(b!=null);
		} catch (AlreadyUsedFamiliarException | AnotherFamiliarSameColourException | AlreadyOccupiedException
				| NotStrongEnoughException | NotEnoughResourcesException | NotEnoughMilitaryPointsException
				| SixCardsException | DoubleCostException e) {
			//it never happens
		}
	}
	
	@Test 
	public void testBonusTowerAction() throws FullGameException{
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		Player p1 = game.addPlayer(new SocketUser(null));
		p1.setIsActive(false);
		this.game.createBoardTest();
		this.game.newRound();
		String tmp = new String("BONUSTOWERACTION;0;0;GREEN;2");
		String[] tmp2 = tmp.split(";");
		Action act = new Action(tmp2, p1, new ResourceSet(0,0,0,0,0,0,0));
		try {
			Bonus b = this.game.placeOnTower(act);
			assertTrue(b!=null);
		} catch (AlreadyUsedFamiliarException | AnotherFamiliarSameColourException | AlreadyOccupiedException
				| NotStrongEnoughException | NotEnoughResourcesException | NotEnoughMilitaryPointsException
				| SixCardsException | DoubleCostException e) {
			// it never happens
		}
	}
	
	@Test
	public void testTowerActionWithChoice() throws FullGameException{
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		Player p1 = game.addPlayer(new SocketUser(null));
		p1.setIsActive(false);
		this.game.createBoardTest();
		this.game.newRound();
		String tmp = new String("TOWERACTIONWITHCHOICE;GREEN;0;WHITE;0;1");
		String[] tmp2 = tmp.split(";");
		Action act = new Action(tmp2, p1, new ResourceSet(0,0,0,0,0,0,0));
		try {
			Bonus b = this.game.placeOnTower(act);
			assertTrue(b!=null);
			assertEquals(act.getCost(),1);
			assertTrue(act.isWhichCostRelevant());
		} catch (AlreadyUsedFamiliarException | AnotherFamiliarSameColourException | AlreadyOccupiedException
				| NotStrongEnoughException | NotEnoughResourcesException | NotEnoughMilitaryPointsException
				| SixCardsException | DoubleCostException e) {
			// it never happens
		}
	}
	
	@Test
	public void testBonusTowerActionWithChoice() throws FullGameException{
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		Player p1 = game.addPlayer(new SocketUser(null));
		p1.setIsActive(false);
		this.game.createBoardTest();
		this.game.newRound();
		String tmp = new String("BONUSTOWERACTIONWITHCHOICE;0;0;GREEN;1;1");
		String[] tmp2 = tmp.split(";");
		Action act = new Action(tmp2, p1, new ResourceSet(0,0,0,0,0,0,0));
		try {
			Bonus b = this.game.placeOnTower(act);
			assertTrue(b!=null);
			assertEquals(act.getCost(),1);
			assertTrue(act.isWhichCostRelevant());
		} catch (AlreadyUsedFamiliarException | AnotherFamiliarSameColourException | AlreadyOccupiedException
				| NotStrongEnoughException | NotEnoughResourcesException | NotEnoughMilitaryPointsException
				| SixCardsException | DoubleCostException e) {
			// it never happens
		}
	}
	
	@Test (expected=it.polimi.ingsw.LM6.server.game.exceptions.LeaderException.class)
	public void testLeaderDiscard() throws FullGameException, LeaderException{
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		Player p1 = game.addPlayer(new SocketUser(null));
		p1.setIsActive(false);
		this.game.createBoardTest();
		this.game.newRound();
		String tmp = new String("LEADERDISCARD;1");
		String[] tmp2 = tmp.split(";");
		Action act = new Action(tmp2, p1, new ResourceSet(0,0,0,0,0,0,0));
		this.game.discardLeaderCard(act);		
	}
	
	@Test (expected=it.polimi.ingsw.LM6.server.game.exceptions.LeaderException.class)
	public void testLeaderPlay() throws FullGameException, LeaderException, LorenzoIlMagnificoException{
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		Player p1 = game.addPlayer(new SocketUser(null));
		p1.setIsActive(false);
		this.game.createBoardTest();
		this.game.newRound();
		this.game.playLeaderCard(p1,1);		
	}
	@Test
	public void testLeaderDiscard1() throws FullGameException, LeaderException, IOException{
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		Player p= new Player(new SocketUser(null),4,"102W 104St 105C 103Se 100VP 100MP 100FP"
				,"117","4;","56;","30;","77;74;","100;101","102");
		p.setIsActive(false);
		this.game.createBoardTest();
		this.game.newRound();
		String tmp = new String("LEADERDISCARD;0");
		String[] tmp2 = tmp.split(";");
		Action act = new Action(tmp2, p, new ResourceSet(0,0,0,0,0,0,0));
			Bonus b = this.game.discardLeaderCard(act);		
			assertTrue(b!=null);
	}

	@Test(expected= IOException.class)
	public void testReload() throws IOException{
		assertTrue(reloadedGame.getStatus().getNumberOfPlayers()==3);
		assertTrue(reloadedGame.getPlayerByNickname("a")!=null);
		assertTrue(reloadedGame.getPlayerByNickname("b")!=null);
		assertTrue(reloadedGame.getPlayerByNickname("c")!=null);
		assertTrue(reloadedGame.getStatus().getEra()==2);
		assertTrue(reloadedGame.getPlayerByNickname("a").getResourceSet().equals(ResourceSet.parseResourceSet("102W 102St 102C 103Se 100VP 100MP 100FP")));
		assertTrue(reloadedGame.getPlayerByNickname("b").getResourceSet().equals(ResourceSet.parseResourceSet("102W 104St 105C 103Se 100VP 100MP 100FP")));
		assertTrue(reloadedGame.getPlayerByNickname("c").getResourceSet().equals(ResourceSet.parseResourceSet("102W 101St 106C 97Se 132VP 98MP ")));
		assertTrue(reloadedGame.getGameBoard().getExcommunicationCard(1).getNumber()==125);
		assertTrue(reloadedGame.getGameBoard().getExcommunicationCard(2).getNumber()==133);
		assertTrue(reloadedGame.getGameBoard().getExcommunicationCard(3).getNumber()==139);
		assertTrue(reloadedGame.getPlayerByNickname("a").getBlueCardsNumbers().equals("50;"));
		assertTrue(reloadedGame.getPlayerByNickname("a").getGreenCardsNumbers().equals(" "));
		assertTrue(reloadedGame.getPlayerByNickname("a").getYellowCardsNumbers().equals(" "));
		assertTrue(reloadedGame.getPlayerByNickname("a").getPurpleCardsNumbers().equals(" "));
		assertTrue(reloadedGame.getPlayerByNickname("b").getBlueCardsNumbers().equals(" "));
		assertTrue(reloadedGame.getPlayerByNickname("b").getGreenCardsNumbers().equals("4;"));
		assertTrue(reloadedGame.getPlayerByNickname("b").getYellowCardsNumbers().equals(" "));
		assertTrue(reloadedGame.getPlayerByNickname("b").getPurpleCardsNumbers().equals(" "));
		assertTrue(reloadedGame.getPlayerByNickname("c").getBlueCardsNumbers().equals("56;"));
		assertTrue(reloadedGame.getPlayerByNickname("c").getGreenCardsNumbers().equals(" "));
		assertTrue(reloadedGame.getPlayerByNickname("c").getYellowCardsNumbers().equals("30;"));
		assertTrue(reloadedGame.getPlayerByNickname("c").getPurpleCardsNumbers().equals("77;74;"));
		reloadedGame= LorenzoIlMagnifico.loadGame("filenadfhdfhme");
	}
	@Test
	public void testGiveResourcesToPlayer() throws IOException{
		Player test= reloadedGame.getPlayerByNickname("a");
		test.getResourceSet().setZero();
		reloadedGame.giveResourcesToPlayer(test, new Bonus(new ResourceSet(1,2,3,4,5,6,7)));
		assertTrue(test.getResourceSet().equals(new ResourceSet(1,2,3,4,5,6,7)));
		
		
	}

	@Test
	public void testIsStarted() throws IOException{
		assertTrue(!reloadedGame.isStarted());
	}
	@Test
	public void testIsFinished() throws IOException{
		assertTrue(!reloadedGame.isFinished());
	}
	@Test
	public void testIsPersonalBonusTilesPhase() throws IOException{
		assertTrue(!reloadedGame.isPersonalBonusTilesPhase());
	}
	@Test
	public void testIsLeaderDraftPhase() throws IOException{
		assertTrue(!reloadedGame.isLeaderDraftPhase());
	}
	@Test
	public void testIsFamiliarOfferPhase() throws IOException{
		assertTrue(!reloadedGame.isFamiliarOfferPhase());
	}
	@Test
	public void testGetEveryTurnResources() throws IOException, AlreadyUsedBonusException{
		SocketUser userFake=new SocketUser(null);
		userFake.setNickname("aaa");
		Player p= new Player(userFake,4,"102W 104St 105C 103Se 100VP 100MP 100FP"
				,"117","4;","56;","30;","77;74;","100;101","102");
		Bonus b= reloadedGame.getEveryTurnResources(p);
		assertTrue(b.getResourceSetFor(p).equals(new ResourceSet(0,0,0,0,2,1,0)));
		
	}
	@Test
	public void testGetEveryTurnHarvest() throws IOException, AlreadyUsedBonusException{
		SocketUser userFake=new SocketUser(null);
		userFake.setNickname("aaa");
		Player p= new Player(userFake,4,"102W 104St 105C 103Se 100VP 100MP 100FP"
				,"117","4;","56;","30;","77;74;","100;101","106");
		Bonus b= reloadedGame.getEveryTurnHarvest(p);
		assertTrue(b.hasBonusHarvest());
		
	}

	
	@Test 
	public void testPlayLeaderCard() throws IOException, LeaderException, LorenzoIlMagnificoException{
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		Player p= new Player(new SocketUser(null),4,"102W 104St 105C 103Se 100VP 100MP 100FP"
				,"117","4;","56;","30;","77;74;","100;101","102");
		p.setIsActive(false);
		this.game.createBoardTest();
		this.game.newRound();
		this.game.playLeaderCard(p, 0);		
		assertEquals(p.getLeadersInHand().size(),1);
		assertEquals(p.getLeadersPlayed().size(),2);
	}
	
	@Test 
	public void testProductionWithCards() throws IOException, LeaderException, LorenzoIlMagnificoException, AlreadyUsedFamiliarException, NotStrongEnoughException, AnotherFamiliarSameColourException, AlreadyOccupiedException, NotEnoughResourcesException{
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		Player p= new Player(new SocketUser(null),4,"102W 104St 105C 103Se 100VP 100MP 100FP"
				,"117","4;","56;","30;","77;74;","100;101","102");
		p.setIsActive(false);
		this.game.createBoardTest();
		this.game.newRound();
		p.getResourceSet().setZero();
		p.acquireResources(new ResourceSet(0,1,0,3,0,0,0));
		String tmp = new String("BONUSPRODUCTIONACTION;3;1;0;0;0;0;0");
		String[] tmp2 = tmp.split(";");
		Action act = new Action(tmp2, p, new ResourceSet(0,0,0,0,0,0,0));
		Bonus b = this.game.placeFamiliarProduction(act);
		ResourceSet res = b.getResourceSetFor(p);
		assertEquals(res.getCoin(),5);
		assertEquals(res.getMilitaryPoints(),1);		
	}
	
	@Test 
	public void testHarvestWithCards() throws IOException, LeaderException, LorenzoIlMagnificoException, AlreadyUsedFamiliarException, NotStrongEnoughException, AnotherFamiliarSameColourException, AlreadyOccupiedException, NotEnoughResourcesException{
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		Player p= new Player(new SocketUser(null),4,"102W 104St 105C 103Se 100VP 100MP 100FP"
				,"117","4;","56;","30;","77;74;","100;101","102");
		p.setIsActive(false);
		this.game.createBoardTest();
		this.game.newRound();
		p.getResourceSet().setZero();
		p.acquireResources(new ResourceSet(0,0,0,4,0,0,0));
		String tmp = new String("BONUSHARVESTACTION;4;0");
		String[] tmp2 = tmp.split(";");
		Action act = new Action(tmp2, p, new ResourceSet(0,0,0,0,0,0,0));
		Bonus b = this.game.placeFamiliarHarvest(act);
		ResourceSet res = b.getResourceSetFor(p);
		assertEquals(res.getStone(),3);
		assertEquals(res.getServants(),1);
		assertEquals(res.getWood(),1);
	}
	
	@Test (expected=it.polimi.ingsw.LM6.server.game.exceptions.LorenzoIlMagnificoException.class)
	public void testPlayLorenzoIlMagnifico() throws IOException, LeaderException, LorenzoIlMagnificoException, FullGameException{
		this.game = new LorenzoIlMagnifico(false, 4, "ucgd");
		Player p1= new Player(new SocketUser(null),4,"102W 104St 105C 103Se 100VP 100MP 100FP"
				,"117","4;","56;","30;","77;74;","116;100;101","102");
		Player p= new Player(new SocketUser(null),4,"102W 104St 105C 103Se 100VP 100MP 100FP"
				,"117","4;","56;","30;","77;74;","116;100;101","102");
		game.getStatus().addPlayer(p);
		game.getStatus().addPlayer(p1);
		this.game.createBoardTest();
		this.game.newRound();
		this.game.playLeaderCard(p, 0);	
	}
	

	@Test
	public void testGetEveryTurnProduction() throws IOException, AlreadyUsedBonusException{
		SocketUser userFake=new SocketUser(null);
		userFake.setNickname("aaa");
		Player p= new Player(userFake,4,"102W 104St 105C 103Se 100VP 100MP 100FP"
				,"117","4;","56;","30;","77;74;","100;101","107");
		Bonus b= reloadedGame.getEveryTurnProduction(p);
		assertTrue(b.hasBonusProduction());
		
	}
	
	@Test (expected=it.polimi.ingsw.LM6.server.game.exceptions.AlreadyUsedBonusException.class)
	public void testOneSixFamiliar() throws IOException, AlreadyUsedBonusException{
		SocketUser userFake=new SocketUser(null);
		userFake.setNickname("aaa");
		Player p= new Player(userFake,4,"102W 104St 105C 103Se 100VP 100MP 100FP"
				,"117","4;","56;","30;","77;74;","100;101","113");
		reloadedGame.applyOneSixFamiliarPerTurn(p, p.getFamiliar(0));
		assertTrue(p.getFamiliar(0).getStrength()==6);
		reloadedGame.applyOneSixFamiliarPerTurn(p, p.getFamiliar(0));		
	}
	@Test
	public void testShowBoard(){
		String board=this.reloadedGame.showBoard();
		assertTrue(board!=null);
	}
	@Test
	public void testShowPlayers() throws IOException{
		SocketUser userFake=new SocketUser(null);
		userFake.setNickname("aaa");
		Player p= new Player(userFake,4,"102W 104St 105C 103Se 100VP 100MP 100FP"
				,"117","4;","56;","30;","77;74;","100;101","113");
		String players=this.reloadedGame.showPlayers(p);
		assertTrue(players!=null && (players.split(";")).length==5);
	}
	@Test
	public void testGetPlayer(){
		this.reloadedGame.getStatus().defineFamiliarsPerPlayer();
		this.reloadedGame.getStatus().createRoundArray();
		assertTrue(this.reloadedGame.getPlayer(2)==this.reloadedGame.getPlayerByNickname("a"));
	}

}
