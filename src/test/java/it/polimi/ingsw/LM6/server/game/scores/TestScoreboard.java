package it.polimi.ingsw.LM6.server.game.scores;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NameNotFoundException;

import org.junit.Test;

import it.polimi.ingsw.LM6.server.game.scores.Record;
import it.polimi.ingsw.LM6.server.game.scores.Scoreboard;

public class TestScoreboard {
	Scoreboard globalScoreboard;
	Scoreboard scoreboard;
	
	/**	@author Emilio
	 */
	public TestScoreboard(){
		this.globalScoreboard = new Scoreboard();
		
		this.scoreboard = new Scoreboard();
		this.scoreboard.add(new Record(1, "Emilio", 0, 0, 0, 20, "0:00"));
		this.scoreboard.add(new Record(1, "Luca", 0, 0, 0, 10, "0:00"));
		this.scoreboard.add(new Record(1, "Matteo", 0, 0, 0, 15, "0:00"));
	}
	
	/**	@author Emilio
	 */
	@Test
	public void testApplyResults_AddByVPs(){
		Scoreboard results = this.scoreboard.applyResults();
		
		//Wins and losses setting test.
		this.scoreboard.getRecord(0).incrementWins();
		assertTrue(this.scoreboard.getRecord(0).equalTo(results.getRecord(0)));
		
		this.scoreboard.getRecord(1).incrementLosses();
		assertTrue(this.scoreboard.getRecord(1).equalTo(results.getRecord(2)));
		
		this.scoreboard.getRecord(2).incrementLosses();
		assertTrue(this.scoreboard.getRecord(2).equalTo(results.getRecord(1)));
		
		this.scoreboard.add(new Record(1, "CheBelliITestATardaNotte", 0, 0, 0, 20, "0:00"));
		
		//Draws and losses setting test.
		results = this.scoreboard.applyResults();
		
		this.scoreboard.getRecord(0).incrementDraws();
		assertTrue(this.scoreboard.getRecord(0).equalTo(results.getRecord(0)));
		
		this.scoreboard.getRecord(1).incrementLosses();
		assertTrue(this.scoreboard.getRecord(1).equalTo(results.getRecord(3)));
		
		this.scoreboard.getRecord(2).incrementLosses();
		assertTrue(this.scoreboard.getRecord(2).equalTo(results.getRecord(2)));
		
		this.scoreboard.getRecord(3).incrementDraws();
		assertTrue(this.scoreboard.getRecord(3).equalTo(results.getRecord(1)));
	}
	
	/**	@author Emilio
	 */
	@Test
	public void testFilterScoreboardByRanking(){
		Scoreboard result;
		
		//Standard input test
		result = this.scoreboard.filterScoreboardByRanking(1, 2);
		assertEquals(this.scoreboard.getRecord(0).toString(), result.getRecord(0).toString());
		assertEquals(this.scoreboard.getRecord(1).toString(), result.getRecord(1).toString());
		
		//Special input test (from = 0 && to = 0)
		result = this.scoreboard.filterScoreboardByRanking(0, 0);
		assertEquals(this.scoreboard.getRecord(0).toString(), result.getRecord(0).toString());
		assertEquals(this.scoreboard.getRecord(1).toString(), result.getRecord(1).toString());
		assertEquals(this.scoreboard.getRecord(2).toString(), result.getRecord(2).toString());
		
		//Invalid input test (from < 1)
		result = this.scoreboard.filterScoreboardByRanking(0, 3);
		assertEquals(this.scoreboard.getRecord(0).toString(), result.getRecord(0).toString());
		assertEquals(this.scoreboard.getRecord(1).toString(), result.getRecord(1).toString());
		assertEquals(this.scoreboard.getRecord(2).toString(), result.getRecord(2).toString());
		
		//Invalid input test (to > size)
		result = this.scoreboard.filterScoreboardByRanking(1, 4);
		assertEquals(this.scoreboard.getRecord(0).toString(), result.getRecord(0).toString());
		assertEquals(this.scoreboard.getRecord(1).toString(), result.getRecord(1).toString());
		assertEquals(this.scoreboard.getRecord(2).toString(), result.getRecord(2).toString());
		
		//Invalid input test (from < to)
		result = this.scoreboard.filterScoreboardByRanking(2, 1);
		assertEquals(this.scoreboard.getRecord(0).toString(), result.getRecord(0).toString());
		assertEquals(this.scoreboard.getRecord(1).toString(), result.getRecord(1).toString());
	}
	
	/**	@author Emilio
	 */
	@Test
	public void testAdd(){
		//Add test
		this.scoreboard.add(new Record(1, "GodSonar", 1, 1, 1, 20, "1:11"));
		assertEquals("1/GodSonar/1/1/1/20/1:11", this.scoreboard.getRecord(3).toString());
	}
	
	/**	@author Emilio
	 */
	@Test
	public void testRankFrom(){
		int i;
		
		//Illegal (lower) input test.
		scoreboard.rankFrom(-10);
		Scoreboard expected = new Scoreboard();
		expected.add(new Record(1, "Emilio", 0, 0, 0, 20, "0:00"));
		expected.add(new Record(2, "Luca", 0, 0, 0, 10, "0:00"));
		expected.add(new Record(3, "Matteo", 0, 0, 0, 15, "0:00"));
		
		for(i = 0; i < scoreboard.getSize(); i++)
			assertTrue(scoreboard.getRecord(i).equalTo(expected.getRecord(i)));
		
		//Legal input test.
		this.scoreboard.add(new Record(1, "Mottola", 0, 0, 0, 0, "0:00"));
		scoreboard.rankFrom(1);
		expected.add(new Record(4, "Mottola", 0, 0, 0, 0, "0:00"));
		
		for(i = 0; i < scoreboard.getSize(); i++)
			assertTrue(scoreboard.getRecord(i).equalTo(expected.getRecord(i)));
		
		//Illegal (upper) input test.
		this.scoreboard.add(new Record(1, "Campi", 0, 0, 0, 0, "0:00"));
		scoreboard.rankFrom(10);
		expected.add(new Record(5, "Campi", 0, 0, 0, 0, "0:00"));
		
		for(i = 0; i < scoreboard.getSize(); i++)
			assertTrue(scoreboard.getRecord(i).equalTo(expected.getRecord(i)));
	}
	
	/**	@author Emilio
	 */
	@Test
	public void testGetRecord(){
		assertEquals("1/Emilio/0/0/0/20/0:00", this.scoreboard.getRecord(0).toString());
	}
	
	/**	@author Emilio
	 */
	@Test
	public void testGetSize(){
		assertEquals(3, this.scoreboard.getSize());
	}
	
	/**	@author Emilio
	 */
	@Test
	public void testFindPlayerRecord(){
		Record result;
		
		try{
			result = this.scoreboard.findPlayerRecord("Emilio");
			assertEquals(this.scoreboard.getRecord(0).toString(), result.toString());
			
			result = this.scoreboard.findPlayerRecord("Zelda");
		} catch(NameNotFoundException e){
			//The exception was correctly thrown.
			return;
		}
		
		assertTrue(false);
	}
	
	/**	@author Emilio
	 */
	@Test
	public void testLoad_Update_AddByGames_Store(){
		Scoreboard reset = new Scoreboard();
		Scoreboard update = new Scoreboard();
		String expected;
		Record toCheck;
		int i;
		
		try{
			this.globalScoreboard.load();
			reset.load();
		} catch(IOException e){
			Logger.getLogger("Test_Logger").log(Level.INFO, "The testLoad failed due to configuration files issues.", e);
			assertTrue(false);
		}
		
		//Correct order test. The correct order has been manually set to correspond to the ranking.
		toCheck = this.globalScoreboard.getRecord(0);
		assertTrue("1/Name2/4/0/0/1000/2:00".equals(toCheck.toString()));
		
		toCheck = this.globalScoreboard.getRecord(1);
		assertTrue("2/Name5/2/2/0/200/2:00".equals(toCheck.toString()));
		
		toCheck = this.globalScoreboard.getRecord(2);
		assertTrue("3/Name4/2/2/1/300/2:00".equals(toCheck.toString()));
		
		toCheck = this.globalScoreboard.getRecord(3);
		assertTrue("4/Name1/2/2/2/200/2:00".equals(toCheck.toString()));
		
		toCheck = this.globalScoreboard.getRecord(4);
		assertTrue("5/Name3/2/1/0/200/2:00".equals(toCheck.toString()));
		
		//Record update test.
		this.globalScoreboard.update(new Record(1, "Name2", 1, 0, 0, 0, "1:00"));
		this.globalScoreboard.update(new Record(1, "Name1", 1, 0, 0, 0, "1:00"));
		this.globalScoreboard.update(new Record(1, "Name5", 1, 0, 0, 0, "1:00"));
		this.globalScoreboard.update(new Record(1, "Name1", 2, 0, 0, 0, "1:00"));
		
		//New Record update test.
		this.globalScoreboard.update(new Record(1, "Boh", 1, 1, 1, 20, "1:11"));
		this.globalScoreboard.update(new Record(1, "Elisa", 2, 1, 1, 20, "1:11"));
		
		//Scoreboard update test.
		update.add(new Record(1, "Name2", 0, 0, 0, 100, "1:00"));
		update.add(new Record(1, "Name1", 0, 1, 0, 100, "1:00"));
		this.globalScoreboard.update(update);
		
		assertTrue("1/Name1/5/3/2/300/5:00".equals(this.globalScoreboard.getRecord(0).toString()));
		assertTrue("2/Name2/5/0/0/1100/4:00".equals(this.globalScoreboard.getRecord(1).toString()));
		assertTrue("3/Name5/3/2/0/200/3:00".equals(this.globalScoreboard.getRecord(2).toString()));
		assertTrue("4/Name4/2/2/1/300/2:00".equals(this.globalScoreboard.getRecord(3).toString()));
		assertTrue("5/Name3/2/1/0/200/2:00".equals(this.globalScoreboard.getRecord(4).toString()));
		assertTrue("6/Elisa/2/1/1/20/1:11".equals(this.globalScoreboard.getRecord(5).toString()));
		assertTrue("7/Boh/1/1/1/20/1:11".equals(this.globalScoreboard.getRecord(6).toString()));
		
		//Store test.
		File scoreboardFile = new File(this.getClass().getResource("/it/polimi/ingsw/LM6/configuration/scoreboard.txt").getPath());
		FileReader f = null;
		
		do{
			try {
				f = new FileReader(scoreboardFile);
			} catch (FileNotFoundException e) {
				//This can't happen.
			}
		}while(f==null);
		
		BufferedReader b = new BufferedReader(f);
		String record = null;
		
		try{
			record = b.readLine();
			
			for(i = 0; record != null; i++){
				assertEquals(this.globalScoreboard.getRecord(i).toString(), record);
				record = b.readLine();			
			}
			
			b.close();
			f.close();
		} catch(IOException e){
			//Error closing files.
		}
		
		reset.store();
	}

	/**	@author Emilio
	 */
	@Test
	public void testToString(){
		String result = this.scoreboard.toString("-");
		String expected = "1/Emilio/0/0/0/20/0:00-1/Luca/0/0/0/10/0:00-1/Matteo/0/0/0/15/0:00-";
		
		assertEquals(expected, result);
	}
}
