package it.polimi.ingsw.LM6.server.game.states;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.game.exceptions.FullGameException;
import it.polimi.ingsw.LM6.server.game.states.GameState;
import it.polimi.ingsw.LM6.server.users.SocketUser;

public class TestGameState {
	GameState state;
	
	public TestGameState(){
		this.state = new GameState(4);
	}
	
	@Test
	public void testIncrementCurrentTurn(){
		int turn = state.getRound();
		state.incrementCurrentRound();
		assertEquals(turn + 1, state.getRound());
	}
	
	@Test
	public void testIncrementCurrentEra(){
		int turn = state.getEra();
		state.incrementCurrentEra();
		assertEquals(turn + 1, state.getEra());
	}
	
	@Test
	public void testIncrementCurrentPlayerIndex(){
		int turn = state.getCurrentPlayerIndex();
		state.incrementCurrentPlayerIndex();
		assertEquals(turn + 1, state.getCurrentPlayerIndex());
	}
	
	@Test
	public void testResetCurrentPlayerIndex(){
		state.incrementCurrentPlayerIndex();
		state.resetCurrentPlayerIndex();
		assertEquals(0, state.getCurrentPlayerIndex());
	}
	
	@Test
	public void testHasReachedMinimumNumberOfPlayers(){
		try {
			state.addPlayer(new Player(new SocketUser(null), null, 4));
			state.addPlayer(new Player(new SocketUser(null), null, 4));
			assertTrue(state.hasReachedTheMinimumNumberOfPlayers());
			
		} catch (FullGameException e) {
			//it never happens
		}
	}
	
	@Test
	public void testHasReachedMaximumNumberOfPlayers(){
		try {
			state.addPlayer(new Player(new SocketUser(null), null, 4));
			state.addPlayer(new Player(new SocketUser(null), null, 4));
			state.addPlayer(new Player(new SocketUser(null), null, 4));
			state.addPlayer(new Player(new SocketUser(null), null, 4));
			assertTrue(state.hasReachedTheMaximumNumberOfPlayers());
			
		} catch (FullGameException e) {
			//it never happens
		}
	}
	
	@Test
	public void testSetFamiliar(){
		try {
			state.addPlayer(new Player(new SocketUser(null), null, 4));
			state.addPlayer(new Player(new SocketUser(null), null, 4));
			state.setFamiliarsValues(2, 3, 4, 5);
			assertEquals(state.getPlayers()[0].getFamiliar("WHITE").getStrength(), 4);
			assertEquals(state.getPlayers()[0].getFamiliar("BLACK").getStrength(), 2);			
		} catch (FullGameException e) {
			//it never happens
		}
	}
	
	@Test
	public void testGetTotalPlayersTurnPerRound(){
		try {
			state.addPlayer(new Player(new SocketUser(null), null, 4));
			state.addPlayer(new Player(new SocketUser(null), null, 4));
			state.defineFamiliarsPerPlayer();
			assertEquals(state.getTotalPlayersTurnPerRound(), 8);
			
		} catch (FullGameException e) {
			//it never happens
		}		
	}
	
	@Test
	public void testExcommunicationPhase(){
		assertFalse(state.isExcomunicationPhase());
		state.setExcomunicationPhase();
		assertTrue(state.isExcomunicationPhase());
		state.resetExcomunicationPhase();
		assertFalse(state.isExcomunicationPhase());
	}
	
	@Test
	public void testCreateRoundArray(){
		try {
			state.addPlayer(new Player(new SocketUser(null), null, 4));
			state.addPlayer(new Player(new SocketUser(null), null, 4));
			state.addPlayer(new Player(new SocketUser(null), null, 4));
			state.defineFamiliarsPerPlayer();
			state.createRoundArray();
			assertEquals(state.getRoundArray().length, 12);			
			state.addPlayer(new Player(new SocketUser(null), null, 4));
			state.addPlayer(new Player(new SocketUser(null), null, 4));
			assertEquals(state.getRoundArray().length, 16);
		} catch (FullGameException e) {
			//it never happens
		}		
	}
	
	@Test
	public void testCurrentTurnPlayer(){
		try {
			Player a = new Player(new SocketUser(null), null, 4);
			state.addPlayer(a);
			state.addPlayer(new Player(new SocketUser(null), null, 4));
			state.addPlayer(new Player(new SocketUser(null), null, 4));
			state.defineFamiliarsPerPlayer();
			state.createRoundArray();
			assertEquals(state.currentTurnPlayer(), a);
		} catch (FullGameException e) {
			//it never happens
		}		
	}
	

	@Test
	public void testGetNumberOfFamiliarPerPlayer(){
		try {
			state.addPlayer(new Player(new SocketUser(null), null, 4));
			state.addPlayer(new Player(new SocketUser(null), null, 4));
			assertEquals(state.getNumberOfFamiliarsPerPlayer(), 4);			
		} catch (FullGameException e) {
			//it never happens
		}
	}
	
	@Test
	public void testGetNumberOfPlayers(){
		try {
			assertEquals(state.getNumberOfPlayers(), 0);		
			state.addPlayer(new Player(new SocketUser(null), null, 4));
			state.addPlayer(new Player(new SocketUser(null), null, 4));
			assertEquals(state.getNumberOfPlayers(), 2);	
			state.addPlayer(new Player(new SocketUser(null), null, 4));
			assertEquals(state.getNumberOfPlayers(), 3);	
		} catch (FullGameException e) {
			//it never happens
		}
	}
	
	@Test
	public void testApplyTurnModifiers(){
		try {
			Player a = new Player(new SocketUser(null), null, 4);
			state.addPlayer(a);
			state.addPlayer(new Player(new SocketUser(null), null, 4));
			state.defineFamiliarsPerPlayer();
			state.createRoundArray();
			state.applyTurnOrderModifiers();
			assertEquals(a, state.getPlayers()[0]);
		} catch (FullGameException e) {
			//it never happens
		}
	}
	
	@Test
	public void testCreateExcommunicationChoices(){
		try {
			state.addPlayer(new Player(new SocketUser(null), null, 4));
			state.addPlayer(new Player(new SocketUser(null), null, 4));
			state.createExcommunicationChoices();
			assertEquals(state.getExcommChoices().length, 2);
			assertFalse(state.getExcommChoices()[0]);
		} catch (FullGameException e) {
			//it never happens
		}
	}
	
	@Test
	public void testCreateRoundArray5Players(){
		GameState stato = new GameState(5);
		try {
			stato.addPlayer(new Player(new SocketUser(null), null, 4));
			stato.addPlayer(new Player(new SocketUser(null), null, 4));
			stato.addPlayer(new Player(new SocketUser(null), null, 4));
			stato.addPlayer(new Player(new SocketUser(null), null, 4));
			stato.addPlayer(new Player(new SocketUser(null), null, 4));
			stato.defineFamiliarsPerPlayer();
			stato.createRoundArray();
			
		} catch (FullGameException e) {
			//it never happens
		}
	}
	@Test
	public void getNumberOfConnectedUsers(){
		try {
			state.addPlayer(new Player(new SocketUser(null), null, 4));
			state.addPlayer(new Player(new SocketUser(null), null, 4));
			state.addPlayer(new Player(new SocketUser(null), null, 4));
			assertEquals(state.getNumberOfConnectedUsers(), 3);			
		} catch (FullGameException e) {
			//it never happens
		}
	}
	
	
}
