package it.polimi.ingsw.LM6.server.game.scores;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.LM6.server.game.scores.Record;

public class TestRecord {
	private Record record;
	
	/** @author Emilio
	 */
	public TestRecord(){
		this.record = new Record(1, "Emilio", 0, 0, 0, 0, "0:00");
	}
	
	/******PRODUCTORS_TESTS******/
	
	/** @author Emilio
	 */
	public void testParseRecord(){
		assertEquals(record, Record.parseRecord("1/Emilio/0/0/0/0/0:00"));
	}
	
	/******OBSERVERS_TESTS******/
	
	/** @author Emilio
	 */
	@Test
	public void testGetRanking(){
		assertEquals(1, record.getRanking());
	}
	
	/** @author Emilio
	 */
	@Test
	public void testGetNickname(){
		assertEquals("Emilio", record.getNickname());
	}
	
	/** @author Emilio
	 */
	@Test
	public void testGetWins(){
		assertEquals(0, record.getWins());
	}
	
	/** @author Emilio
	 */
	@Test
	public void testGetDraws(){
		assertEquals(0, record.getDraws());
	}
	
	/** @author Emilio
	 */
	@Test
	public void testGetLosses(){
		assertEquals(0, record.getLosses());
	}
	
	/** @author Emilio
	 */
	@Test
	public void testGetTotalVPs(){
		assertEquals(0, record.getTotalVPs());
	}
	
	/** @author Emilio
	 */
	@Test
	public void testGetTime(){
		assertEquals("0:00", record.getTime());
	}
	
	/** @author Emilio
	 */
	@Test
	public void testEqualTo(){
		//Null value test.
		assertFalse(record.equalTo(null));
		
		//First attribute inequality test.
		Record toCompare = new Record(3, "Emilio", 0, 0, 0, 0, "0:00");
		assertFalse(record.equalTo(toCompare));
		
		//Second attribute inequality test.
		toCompare = new Record(1, "Mr. Robot", 0, 0, 0, 0, "0:00");
		assertFalse(record.equalTo(toCompare));
		
		//Third attribute inequality test.
		toCompare = new Record(1, "Emilio", 1, 0, 0, 0, "0:00");
		assertFalse(record.equalTo(toCompare));
		
		//Fourth attribute inequality test.
		toCompare = new Record(1, "Emilio", 0, 1, 0, 0, "0:00");
		assertFalse(record.equalTo(toCompare));
		
		//Fifth attribute inequality test.
		toCompare = new Record(1, "Emilio", 0, 0, 1, 0, "0:00");
		assertFalse(record.equalTo(toCompare));
		
		//Sixth attribute inequality test.
		toCompare = new Record(1, "Emilio", 0, 0, 0, 1, "0:00");
		assertFalse(record.equalTo(toCompare));
		
		//Seventh attribute inequality test.
		toCompare = new Record(1, "Emilio", 0, 0, 0, 0, "0:01");
		assertFalse(record.equalTo(toCompare));
		
		//Whole record equality test.
		toCompare = new Record(1, "Emilio", 0, 0, 0, 0, "0:00");
		assertTrue(record.equalTo(toCompare));
	}
	
	/**	@author Emilio 
	 */
	@Test
	public void testCompareTo(){
		Record toCompare;
		
		//Wins > Wins test
		record.incrementWins();
		toCompare = new Record(1, "LUCADONDONI", 0, 0, 0, 0, "0:01");
		assertEquals(1, record.compareTo(toCompare));
		
		//Wins < Wins test
		toCompare = new Record(1, "LUCADONDONI", 2, 0, 0, 0, "0:01");
		assertEquals(-1, record.compareTo(toCompare));
		
		//Draws > Draws test
		record.incrementWins();
		record.incrementDraws();
		toCompare = new Record(1, "LUCADONDONI", 2, 0, 0, 0, "0:01");
		assertEquals(1, record.compareTo(toCompare));
		
		//Draws < Draws test
		toCompare = new Record(1, "LUCADONDONI", 2, 2, 0, 0, "0:01");
		assertEquals(-1, record.compareTo(toCompare));
		
		//Losses < Losses test
		record.incrementDraws();
		toCompare = new Record(1, "LUCADONDONI", 2, 2, 1, 0, "0:01");
		assertEquals(1, record.compareTo(toCompare));
		
		//Losses > Losses test
		record.incrementLosses();
		record.incrementLosses();
		toCompare = new Record(1, "LUCADONDONI", 2, 2, 1, 0, "0:01");
		assertEquals(-1, record.compareTo(toCompare));
		
		//TotalVPS > TotalVPS test
		toCompare = new Record(1, "LUCADONDONI", 2, 2, 2, -10, "0:01");
		assertEquals(1, record.compareTo(toCompare));
		
		//TotalVPS < TotalVPS test
		toCompare = new Record(1, "LUCADONDONI", 2, 2, 2, 10, "0:01");
		assertEquals(-1, record.compareTo(toCompare));
		
		//TotalVPS == TotalVPs test
		toCompare = new Record(1, "LUCADONDONI", 2, 2, 2, 0, "0:01");
		assertEquals(0, record.compareTo(toCompare));
	}
	
	/******MODIFIERS_TESTS******/
	
	/** @author Emilio
	 */
	@Test
	public void testUpdate(){
		//Wins, draws, losses, totalVPs and time (minutes units) update test
		int wins = 1;
		int draws = 1;
		int losses = 1;
		int totalVPs = 10;
		String time = "0:01";
		
		record.update(wins, draws, losses, totalVPs, time);
		Record expected = new Record(1, "Emilio", 1, 1, 1, 10, "0:01");	
		assertEquals(expected.getWins(), record.getWins());
		assertEquals(expected.getDraws(), record.getDraws());
		assertEquals(expected.getLosses(), record.getLosses());
		assertEquals(expected.getTotalVPs(), record.getTotalVPs());
		assertEquals(expected.getTime(), record.getTime());
		
		//Time (minutes tens) update test
		wins = 0;
		draws = 0;
		losses = 0;
		totalVPs = 0;
		time = "0:10";
		
		record.update(wins, draws, losses, totalVPs, time);
		expected = new Record(1, "Emilio", 1, 1, 1, 10, "0:11");
		assertEquals(expected.getWins(), record.getWins());
		assertEquals(expected.getDraws(), record.getDraws());
		assertEquals(expected.getLosses(), record.getLosses());
		assertEquals(expected.getTotalVPs(), record.getTotalVPs());
		assertEquals(expected.getTime(), record.getTime());
		
		//Time (from minutes to hours) update test
		time = "0:49";
		
		record.update(wins, draws, losses, totalVPs, time);
		expected = new Record(1, "Emilio", 1, 1, 1, 10, "1:00");
		assertEquals(expected.getWins(), record.getWins());
		assertEquals(expected.getDraws(), record.getDraws());
		assertEquals(expected.getLosses(), record.getLosses());
		assertEquals(expected.getTotalVPs(), record.getTotalVPs());
		assertEquals(expected.getTime(), record.getTime());
		
		//Time (hours) update test
		time = "1:00";
		
		record.update(wins, draws, losses, totalVPs, time);
		expected = new Record(1, "Emilio", 1, 1, 1, 10, "2:00");
		assertEquals(expected.getWins(), record.getWins());
		assertEquals(expected.getDraws(), record.getDraws());
		assertEquals(expected.getLosses(), record.getLosses());
		assertEquals(expected.getTotalVPs(), record.getTotalVPs());
		assertEquals(expected.getTime(), record.getTime());
	}
	
	/** @author Emilio
	 */
	@Test
	public void testSetRanking(){
		//Invalid ranking value test
		record.setRanking(-10);
		assertEquals(1, record.getRanking());
		
		//Valid ranking value test
		record.setRanking(10);
		assertEquals(10, record.getRanking());
	}
	
	/** @author Emilio
	 */
	@Test
	public void testIncrementWins(){
		record.incrementWins();
		assertEquals(1, record.getWins());
	}
	
	/** @author Emilio
	 */
	@Test
	public void testIncrementDraws(){
		record.incrementDraws();
		assertEquals(1, record.getDraws());
	}
	
	/** @author Emilio
	 */
	@Test
	public void testIncrementLosses(){
		record.incrementLosses();
		assertEquals(1, record.getLosses());
	}
	
	/******PARSERS_TESTS******/
	
	/** @author Emilio
	 */
	@Test
	public void testToString(){
		String expected = "1/Emilio/0/0/0/0/0:00";
		String result_s = record.toString();
		assertEquals(expected, result_s);
	}
	
	/** @author Emilio
	 */
	@Test
	public void testParseRecordTimeFormat(){
		//Minutes conversion test (Units)
		String result = Record.parseRecordTimeFormat(60000);
		String expected = "0:01";
		assertEquals(expected, result);
		
		//Minutes conversion test (Tens)
		result = Record.parseRecordTimeFormat(600000);
		expected = "0:10";
		assertEquals(expected, result);
		
		//Hours conversion test
		result = Record.parseRecordTimeFormat(3600000);
		expected = "1:00";
		assertEquals(expected, result);
		
		//Seconds approximation test
		result = Record.parseRecordTimeFormat(3633333);
		expected = "1:00";
		assertEquals(expected, result);
	}
}