package it.polimi.ingsw.LM6.server.network;

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

import it.polimi.ingsw.LM6.server.game.LorenzoIlMagnifico;
import it.polimi.ingsw.LM6.server.game.debug.Debug;
import it.polimi.ingsw.LM6.server.game.scores.Scoreboard;
import it.polimi.ingsw.LM6.server.users.IUser;

public class NetworkData {
	private static Vector<IUser> users = new Vector<>();
	private static Vector<LorenzoIlMagnifico> games = new Vector<>();
	private static Vector<LorenzoIlMagnifico> savedGames = new Vector <>();
	private static Scoreboard globalScoreboard = new Scoreboard();
	private static Vector<Integer> gameIDs = new Vector<>();
	
	private static final String LOGGER = "NetworkData";
	private static final String PATH = "/it/polimi/ingsw/LM6/configuration/";
	private static final String FILENAME = "maxgameid.txt";
	
	/******CONSTRUCTORS******/
	
	/**	This private constructor avoids the initialization of an object from this utility class.
	 * 
	 *  @author Emilio	
	 */
	private NetworkData(){
		
	}
	
	/******GETTERS******/
	
	/** @author Emilio
	 */
	public static Vector<IUser> getUsers(){
		return users;
	}
	
	/** @author Emilio
	 */
	public static Vector<LorenzoIlMagnifico> getGames(){
		return games;
	}
	
	/** @author Emilio
	 */
	public static Vector<LorenzoIlMagnifico> getSavedGames(){
		return savedGames;
	}
	
	/** @author Emilio
	 */
	public static Scoreboard getGlobalScoreboard(){
		return globalScoreboard;
	}
	
	/******MODIFIERS******/
	
	/** Returns an ID integer number that can be assigned to a game to allow it to be saved.
	 * 	In doing so, it also updates the max ID if needed.
	 * 
	 * 	@return The ID integer number to assign.
	 * 
	 * 	@throws IOException When the update of max ID fails
	 * 
	 * 	@author Emilio
	 */
	public static int getID() throws IOException{
		boolean maxIDToBeUpdated = true;
		int i;
		
		for(i = 0; i < gameIDs.size(); i++)
			if(gameIDs.elementAt(i) != i){
				maxIDToBeUpdated = false;
				break;
			}
		
		gameIDs.add(i, i);
		
		if(maxIDToBeUpdated)
			NetworkData.updateMaxID(i);
		
		return i;
	}
	
	/******LOAD/STORE******/
	
	/** Loads the latest games backup files to prevent total loss of games data due to server crash. 
	 *  To do that, the 	
	 * 
	 *  @throws IOException When it can't access the file where the max game ID is stored.
	 *  
	 *  @author Emilio
	 */
	public static void loadGamesData() throws IOException{
		File maxGameIDFile = new File(NetworkData.class.getClass().getResource(PATH).getPath() + FILENAME);
		int maxID = 0;
		int updateCount = 0;
		FileReader f = null;
		String maxIDString = null;
		int i;
		LorenzoIlMagnifico gameToLoad;
		
		try{
			if(!maxGameIDFile.exists())
				maxGameIDFile.createNewFile();
			
			f = new FileReader(maxGameIDFile);
			BufferedReader b = new BufferedReader(f);
			
			maxIDString = b.readLine();
			if(maxIDString != null)
				maxID = Integer.parseInt(maxIDString);
			
			f.close();
			b.close();
		} catch(IOException e){
			Logger.getLogger(LOGGER).log(Level.WARNING, e.getMessage());
		}
		
		for(i = 0; i <= maxID; i++)
			try{
				gameToLoad = LorenzoIlMagnifico.loadGame(Integer.toString(i));
				savedGames.add(gameToLoad);
				gameIDs.add(i);
				updateCount = 0;
			} catch(FileNotFoundException e){
				updateCount++;
				continue;
			}
		
		if(updateCount != 0)
			NetworkData.updateMaxID(maxID - updateCount);
	}
	
	/** Updates the value of max ID stored in the related file.
	 * 
	 * 	@param The new integer value of max ID.
	 * 
	 * 	@throws IOException When it can't access the file where max ID is stored.
	 * 
	 * 	@author Emilio
	 */
	public static void updateMaxID(int value) throws IOException{
		File maxGameIDFile = new File(new NetworkData().getClass().getResource(PATH).getPath() + FILENAME);
		boolean flag = false;
		
		do{
			try {
				FileOutputStream fos = new FileOutputStream(maxGameIDFile); 
				PrintWriter out = new PrintWriter(fos);
				
				out.println(Integer.toString(value));
				Debug.print("Overwriting maxID.");
				flag = true;
				
				out.close();
				fos.close();
			} catch (FileNotFoundException e) {
				Logger.getLogger(LOGGER).log(Level.INFO, e.getMessage());
				maxGameIDFile.createNewFile();
				continue;		
			} catch (IOException e){
				Logger.getLogger(LOGGER).log(Level.WARNING, e.getMessage());
			}
		} while(!flag);
	}
}