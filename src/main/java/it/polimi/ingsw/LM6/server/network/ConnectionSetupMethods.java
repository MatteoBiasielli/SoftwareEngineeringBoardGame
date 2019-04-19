package it.polimi.ingsw.LM6.server.network;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NameNotFoundException;

import it.polimi.ingsw.LM6.server.game.LorenzoIlMagnifico;
import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.game.debug.Debug;
import it.polimi.ingsw.LM6.server.game.exceptions.BadInputException;
import it.polimi.ingsw.LM6.server.game.exceptions.FullGameException;
import it.polimi.ingsw.LM6.server.game.scores.Record;
import it.polimi.ingsw.LM6.server.game.scores.Scoreboard;
import it.polimi.ingsw.LM6.server.network.exception.InvalidRequestException;
import it.polimi.ingsw.LM6.server.network.exception.OldInstanceException;
import it.polimi.ingsw.LM6.server.users.IUser;

public class ConnectionSetupMethods {
	private static final String LOGGER = "ConnectionSetupMethods";
	private static final String FILENAME = "userslogin.txt";
	private static final String PATH = "/it/polimi/ingsw/LM6/configuration/";
	private static String path=ConnectionSetupMethods.class.getResource(PATH).getPath() + FILENAME;
	
	/** Manages the player's authentication procedure, interpreting the return value of the verifyLogin method.
	 * 	
	 * 	@param user - The IUser who's logging in.
	 * 	@param message - The SetupMessage which contains the user's username and password.
	 * 
	 * 	@throws IOException
	 */
	public synchronized static void userAuthenticationProcedure(IUser user, SetupMessage message) throws IOException{
		SetupMessage excMessage;
		int result;
		
		String username = message.getInfo();
		String password = message.getOptionalInfo();

		result = verifyLogin(username, password);
		switch (result) {
		case 2:
			ConnectionSetupMethods.registerUser(username, password);
			user.setNickname(username);
			break;
		case 1:
			user.setNickname(username);
			break;
		default:
			excMessage = SetupMessage.buildInvalidLogin("Wrong password.");
			excMessage.send(user);
		}
		
	}
	
	/** Registers the user into the usersdata.txt file. From now on, he/she will be able to
	 * 	register his/her game statistics into the scoreboard.txt file.
	 * 	
	 * 	@param nickname - The nickname to register.
	 * 	@param password - The password to register.
	 * 
	 *  @author Emilio
	 */
	public static void registerUser(String nickname, String password) throws IOException{
		File userslogin = new File(path);
		boolean flag = false;
		
		do{
			try {
				FileOutputStream fos = new FileOutputStream(userslogin, true); 
				PrintWriter out = new PrintWriter(fos);
				out.println(nickname + ";" + password);
				flag = true;
				
				out.close();
				fos.close();
			} catch (FileNotFoundException e) {
				Logger.getLogger(LOGGER).log(Level.INFO, e.getMessage());
				userslogin.createNewFile();
			} catch (IOException e){
				Logger.getLogger(LOGGER).log(Level.SEVERE, e.getMessage());
			}
		} while(!flag);
	}
	
	/** Verifies the player's login.
	 * 
	 * 	@param nickname - The nickname to look for.
	 * 	@param password - The password that should match with the nickname.
	 * 
	 *  @return 1, if the player's login is successful. | 0, if the player's login fails. | 2, if the player is logging in for the first time.
	 *  		
	 *  @author Emilio		
	 */
	private static synchronized int verifyLogin(String nickname, String password) throws IOException{
		File userslogin = new File(path);
		int result = 2;
		FileReader f = null;
		String line;
		
		do{
			try {
				f = new FileReader(userslogin);
			} catch (FileNotFoundException e) {
				Logger.getLogger(LOGGER).log(Level.WARNING, e.getMessage());
				userslogin.createNewFile();
			}
		}while(f == null);
		
		Debug.print("File opened.");
		BufferedReader b = new BufferedReader(f);

		try {
			line = b.readLine();
			while (line != null) {
				String[] userdata = line.split(";");
				
				if (nickname.equals(userdata[0])) {
					if (password.equals(userdata[1]))
						result = 1;
					else
						result = 0;
					
					break;
				}
				line = b.readLine();
			}
			b.close();
			f.close();
		} catch (IOException e) {
			Logger.getLogger(LOGGER).log(Level.SEVERE, e.getMessage());
		}
		return result;
	}
	
	/**	Sends the requested records to the user, according to the given ranking gap.
	 * 
	 * 	@param user - The IUser to send the records to.
	 * 	@param from - The ranking starting value.
	 * 	@param to - The ranking ending value.
	 * 
	 * 	@author Emilio
	 */
	public static void showScoreboard(IUser user, int from, int to){
		Scoreboard scoreboard = NetworkData.getGlobalScoreboard().filterScoreboardByRanking(from, to);
		SetupMessage setupMenu = SetupMessage.buildSetupMenu(scoreboard.toString("%n"), scoreboard.toString("!"));
		setupMenu.send(user);
	}
	
	/**	Sends the requested record to the user, according to the given nickname.
	 * 
	 * 	@param user - The IUser to send the record to.
	 * 	@param nicknameOfPlayerToFind - The nickname of the record to find.
	 * 
	 * 	@author Emilio
	 */
	public static void showRecord(IUser user, String nicknameOfPlayerToFind) {
		SetupMessage setupMenu;
		
		try{
			Record toFind = NetworkData.getGlobalScoreboard().findPlayerRecord(nicknameOfPlayerToFind);
			setupMenu = SetupMessage.buildSetupMenu(toFind.toString(), toFind.toString() + "!");
		} catch(NameNotFoundException e){
			setupMenu = SetupMessage.buildSetupMenu("No record found.", "404");
		}
		
		setupMenu.send(user);
	}
	
	/** Manages the user's addition to the game.
	 * 	If the user is not on the users list, then he/she is connecting for the first time or after having
	 * 	correctly completed a game. In this case, if there is no avaiable game, a new game is created according to
	 * 	the settings specified by the IUser; otherwise, he/she is added to the only avaiable game.
	 * 	If the user is on the users list, it means he has been previously disconnected. In this case, the player is
	 * 	added to the game he was playing before.
	 * 	
	 * 	@param user - The IUser to add to the game.
	 * 	@param startNewGame - The SocketSetupMessage sent by the IUser user, which contains the game settings.
	 * 
	 * 	@throws InvalidRequestException, if the user is already connected and currently active in an unfinished game.
	 * 	@throws OldInstanceException, if the user is already connected in a finished game.
	 * 
	 * 	@author Emilio
	 */
	public static synchronized void startNewGame(IUser user, SetupMessage startNewGame) throws InvalidRequestException, OldInstanceException{
		int maxNumOfPlayers = Integer.parseInt(startNewGame.getOptionalInfo());
		boolean usingAdvRules;
		
		if("1".equals(startNewGame.getInfo()))
			usingAdvRules = true;
		else
			usingAdvRules = false;
		
		if(ConnectionSetupMethods.lookForSavedGame(user))
			return;
		
		for(IUser myUser : NetworkData.getUsers()){
			if(user.equals(myUser)){
				LorenzoIlMagnifico myGame = myUser.getGame();
				
				if(!myGame.isFinished()){
					if(!myGame.findPlayer(myUser).isConnected()){
						user.reconnectAs(myUser);
						return;
					}
					else
						throw new InvalidRequestException("This user is already playing the game.");
				}
				else
					throw new OldInstanceException("An old instance of the user is still connected to a finished game.");	
			}
		}
		if(NetworkData.getGames().isEmpty() || NetworkData.getGames().lastElement().isStarted()){
				
			try {
				LorenzoIlMagnifico game = new LorenzoIlMagnifico(usingAdvRules, maxNumOfPlayers, Integer.toString(NetworkData.getID()));
				NetworkData.getGames().addElement(game);
			} catch (BadInputException e) {
				Logger.getLogger(LOGGER).log(Level.WARNING, "An error occurred while creating the game.");
			} catch(IOException e){
				Logger.getLogger(LOGGER).log(Level.SEVERE, e.getMessage());
			}
		}
		LorenzoIlMagnifico lastGame = NetworkData.getGames().lastElement();
			
		try{
			Debug.print("Adding player.");
			Player player = lastGame.addPlayer(user);
			NetworkData.getUsers().add(user);
				
			user.listenTo(player);
			
			Debug.print("Checking start condition.");
			lastGame.checkStartCondition();
		} catch(FullGameException e){
			try{
				LorenzoIlMagnifico game = new LorenzoIlMagnifico(usingAdvRules, maxNumOfPlayers, Integer.toString(NetworkData.getID()));
				NetworkData.getGames().add(game);
				ConnectionSetupMethods.startNewGame(user, startNewGame);
			} catch(BadInputException e1){
				Logger.getLogger(LOGGER).log(Level.WARNING, e.getMessage());
			} catch(IOException e2){
				Logger.getLogger(LOGGER).log(Level.SEVERE, e.getMessage());
			}
		}
	}
	
	/**	Checks if the given user was playing a saved game.
	 * 
	 * 	@param user - The IUser to look for.
	 * 
	 * 	@return true if he was playing a saved game. | false otherwise.
	 * 
	 * 	@throws InvalidRequestException - If the user is already playing the game.
	 */
	private static boolean lookForSavedGame(IUser user) throws InvalidRequestException{
		Player playerFound;
		
		for(LorenzoIlMagnifico game : NetworkData.getSavedGames()){
			Debug.print(Integer.toString(NetworkData.getSavedGames().size()));
			try{
				playerFound = game.findPlayer(user);
			} catch(NoSuchElementException e){
				Debug.print(e.getMessage());
				continue;
			}
			if(!playerFound.isConnected()){
				user.reconnectAs(playerFound.getUser());
				game.checkResumeCondition();
			}
			else
				throw new InvalidRequestException("This user is already playing the game.");
			
			return true;
		}
		return false;
	}
	
	/**	Completes the logout procedure, forcing the user to make a new login.
	 * 
	 * 	@param user
	 * 
	 * 	@author Emilio
	 */
	public static void logout(IUser user){
		SetupMessage message = SetupMessage.buildInvalidLogin("Logout successful.");
		message.send(user);
	}
	
	/**	Manages the game start procedure by correctly reacting to the exception that may be launched.
	 * 
	 * 	@param user - The user who wishes to start a new game.
	 * 	@param message - The message containing the user's game preferences.
	 * 
	 * 	@return true, if the procedure succeeds. | false, otherwise.
	 * 
	 * 	@author Emilio
	 */
	public static boolean manageGameStart(IUser user, SetupMessage message){
		try {
			ConnectionSetupMethods.startNewGame(user, message);
			return true;
		} catch (InvalidRequestException e) {	
			message = SetupMessage.buildInvalidLogin(e.getMessage());
			message.send(user);
			user.setNickname(null);
		} catch (OldInstanceException e){
			message = SetupMessage.buildInvalidLogin(e.getMessage());
			message.send(user);
			user.setNickname(null);
		}
		return false;
	}
}
