package it.polimi.ingsw.LM6.server.game.scores;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NameNotFoundException;

public class Scoreboard {
	private static final String PATH = "/it/polimi/ingsw/LM6/configuration/";
	private static final String FILENAME = "scoreboard.txt";
	private static final Logger LOGGER = Logger.getLogger("it.polimi.ingsw.LM6.server.game.scores.Scoreboard");
	
	private Vector<Record> scoreboard;
	
	/******CONSTRUCTORS******/
	
	public Scoreboard(){
		this.scoreboard = new Vector<Record>();
	}
	
	/******PRODUCTORS******/
	
	/**	It returns a scoreboard which is updated with the results of the game, by setting the winner and the losers.
	 * 
	 * 	@return A Scoreboard which contains the final results of the game.
	 * 
	 * 	@author Emilio
	 */
	public Scoreboard applyResults(){
		Scoreboard orderedScoreboard = new Scoreboard();
		int maxNumOfVPs = -1;
		int count = 0;
		
		for(Record record : this.scoreboard){
			if(record.getTotalVPs() > maxNumOfVPs){
				maxNumOfVPs = record.getTotalVPs();
				count = 1;
			}
			else if(record.getTotalVPs() == maxNumOfVPs)
				count++;
		}
		
		for(Record record : this.scoreboard){
			if(record.getTotalVPs() == maxNumOfVPs){
				if(count > 1)
					record.incrementDraws();
				else
					record.incrementWins();
				
				orderedScoreboard.addByVPs(record);
			}
			else{
				record.incrementLosses();
				orderedScoreboard.addByVPs(record);
			}
		}
		
		return orderedScoreboard;
	}
	
	/** Returns a new Scoreboard containing the records with ranking between from (included) and to (included).
	 * 
	 * 	@param from - Starting ranking position.
	 * 	@param to - Ending ranking position.
	 * 
	 * 	@return this, if from and to are both equal to 0 | A new Scoreboard according to the parameters, otherwise.
	 * 
	 * 	@author Emilio
	 */
	public synchronized Scoreboard filterScoreboardByRanking(int from, int to) {
		int start = from;
		int end = to;
		Scoreboard filteredScoreboard = new Scoreboard();
		int i;
		
		if(start == 0 && to == 0){
			start = 1;
			end = scoreboard.size();
		}
		else{
			if(start > end){
				i = start;
				start = end;
				end = i;
			}
			if(start < 1)
				start = 1;
			if(end > scoreboard.size())
				end = scoreboard.size();
			
		}
		for(i = start - 1; i < end; i++){
			filteredScoreboard.add(this.scoreboard.elementAt(i));
		}
		
		return filteredScoreboard;
		
	}
	
	/******MODIFIERS******/
	
	/**	Updates the scoreboard with the one give as a parameter.
	 * 	This is done by calling the update method on each record of the given scoreboard.
	 * 	At the end, it stores the updated scoreboard in the scoreboard.text file.
	 * 
	 * 	@param updates - The scoreboard to use as an update.
	 * 
	 * 	@author Emilio
	 */
	public void update(Scoreboard updates){
		for(Record record : updates.scoreboard)
			this.update(record);
		
		this.store();
	}
	
	/**	Updates the scoreboard with the given Record.
	 * 	If a record with the same nickname is already present in the scoreboard, the two record will be merged calling the update
	 * 	method in the Record class.
	 * 	If no record with the same nickname is already present, the record will be added to the scoreboard.
	 * 
	 * 	@param updates - The record to us as an update.
	 * 
	 * 	@author Emilio
	 */
	public synchronized void update(Record updates){
		//Updating records.
		boolean found = false;
		boolean ordered = false;
		Record toUpdate = null;
		int i;
		
		for(i = 0; i < scoreboard.size(); i++){
			
			//If the record to update is found...
			if(updates.getNickname().equals(scoreboard.elementAt(i).getNickname())){
				scoreboard.elementAt(i).update(updates.getWins(), updates.getDraws(), updates.getLosses(), updates.getTotalVPs(), updates.getTime());
				toUpdate = scoreboard.elementAt(i);
				
				//If the record is on top of the scoreboard or the previous record has the same number of wins...
				if(i == 0 || toUpdate.compareTo(scoreboard.elementAt(i - 1)) <= 0)
					ordered = true;
				else
					scoreboard.remove(i);
				
				found = true;
				break;
			}
		}
			
		if(found){
			
			while(!ordered){
				
				//If we reached the top without finding a record with a number of VPs equal to the updated one's, it means that it has the maximum number of victories.
				if(i == 0){
					scoreboard.add(i, toUpdate);
					this.rankFrom(i);
					ordered = true;
				}
				else if(toUpdate.compareTo(scoreboard.elementAt(i - 1)) == 1)
					i--;
				else{
					scoreboard.add(i, toUpdate);
					this.rankFrom(i);
					ordered = true;	
				}
			}
		}
		else
			this.addByGames(updates);
	}
	
	/**	Adds the given Record to the Scoreboard.
	 * 
	 * 	@param The Record to add.
	 * 
	 * 	@author Emilio
	 */
	public void add(Record record){
		this.scoreboard.add(record);
	}
	
	/**	Adds the given Record to the Scoreboard, respecting a descending order based on the number of wins.
	 * 
	 * 	@param record - The Record to add.
	 * 
	 * 	@author Emilio
	 */
	private void addByGames(Record record){
		int i;
		
		if(scoreboard.isEmpty()){
			scoreboard.add(record);
		}
		else{
			for(i = 0; i < scoreboard.size(); i++){
				if(record.compareTo(scoreboard.elementAt(i)) == 1)
						break;
			}
			scoreboard.add(i, record);
			this.rankFrom(i);
		}
	}
	
	/**	Adds the given Record to the Scoreboard, respecting a descending order based on the number of VPs.
	 * 
	 * 	@param record - The Record to add.
	 * 
	 * 	@author Emilio
	 */
	private void addByVPs(Record record){
		int i;
		
		if(scoreboard.isEmpty())
			scoreboard.add(record);
		else{
			for(i = 0; i < scoreboard.size(); i++){
				if(record.getTotalVPs() > scoreboard.elementAt(i).getTotalVPs())
					break;
			}
			scoreboard.add(i, record);
			this.rankFrom(i);
		}
	}
	
	/**	Sets the proper ranking to each Record in the Scoreboard, starting from the Record at position i.
	 * 
	 * 	@param ranking - The position to start the ranking from.
	 * 
	 * 	@author Emilio
	 */
	public void rankFrom(int ranking){
		int from = ranking;
		int i;
		
		if(from < 1)
			from = 1;
		else if(from > scoreboard.size())
			from = scoreboard.size();
		
		for(i = from - 1; i < scoreboard.size(); i++)
			scoreboard.elementAt(i).setRanking(i + 1);
	}
	
	/******OBSERVERS******/
	
	/** @author Emilio
	 */
	public Record getRecord(int i){
		Record toGet = this.scoreboard.elementAt(i);
		
		return Record.parseRecord(toGet.toString());
	}
	
	/**	@author Emilio
	 */
	public int getSize(){
		return this.scoreboard.size();
	}
	
	/**	Finds the record related to the player with the given nickname.
	 * 
	 * 	@param nickname - The nickname related to the player whose record is to be found.
	 * 
	 * 	@return The record of the player with the nickname given as a parameter.
	 * 
	 * 	@throws NameNotFoundException, when there is no record matching the given nickname.
	 * 
	 * 	@author Emilio
	 */
	public synchronized Record findPlayerRecord(String nickname) throws NameNotFoundException{
		for(Record record : this.scoreboard)
			if(record.getNickname().equals(nickname))
				return record;
		
		throw new NameNotFoundException();
	}

	/******LOAD/STORE******/
	
	/**	Allows for the global scoreboard to be loaded from the predefined file.
	 * 	While loading the records, it also makes sure that the Scoreboard is ordered, to avoid problems due to scoreboard.txt file unintended edits.
	 * 
	 * 	@author Emilio
	 */
	public synchronized void load() throws IOException{
		File scoreboardFile = new File(this.getClass().getResource(PATH).getPath() + FILENAME);
		FileReader f = null;
		do{
			try {
				f = new FileReader(scoreboardFile);
			} catch (FileNotFoundException e) {
				LOGGER.log(Level.WARNING, "The scoreboard file was not found.", e);
				scoreboardFile.createNewFile();
			}
		}while(f==null);
		
		BufferedReader b = new BufferedReader(f);
		String record = null;
		
		try{
			record = b.readLine();
			
			while(record != null){
				this.addByGames(Record.parseRecord(record));
				record = b.readLine();			
			}
			
			b.close();
			f.close();
		} catch(IOException e){
			LOGGER.log(Level.WARNING, "Error closing FileReader or BufferedReader.", e);
		}
	}
	
	/**	Allows for the global scoreboard to be stored in the predefined file.
	 * 
	 * 	@author Emilio
	 */
	public synchronized void store(){
		File scoreboardFile = new File(this.getClass().getResource(PATH).getPath() + FILENAME);
		boolean flag = false;
		
		do{
			try {
				FileOutputStream fos = new FileOutputStream(scoreboardFile, false); 
				PrintWriter out = new PrintWriter(fos);
				out.printf(this.toString("%n"));
				flag = true;
				
				out.close();
				fos.close();
			} catch (FileNotFoundException e) {
				LOGGER.log(Level.SEVERE, "When executing store, no scoreboard file was found.", e);
				do{
					try{
						scoreboardFile.createNewFile();
					} catch(IOException e2){
						LOGGER.log(Level.SEVERE, "Scoreboard file could not be created.", e2);
					}
				} while(!scoreboardFile.exists());
			} catch (IOException e){
				LOGGER.log(Level.WARNING, "Error closing FileOutputStream or PrintWriter.", e);
			}
		} while(!flag);
	}
	
	/******PARSERS******/
	
	/**	Turns the Scoreboard into a String, which is returned.
	 * 
	 * 	@return The Scoreboard turned into a String.
	 * 
	 * 	@author Emilio
	 */
	public String toString(String separator){
		String result = "";
		
		for(Record record : this.scoreboard)
			result += record.toString() + separator;
		
		return result;
	}
}