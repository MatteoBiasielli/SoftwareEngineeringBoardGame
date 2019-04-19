package it.polimi.ingsw.LM6.server.network.socket;

import java.io.IOException;
import java.net.Socket;

import it.polimi.ingsw.LM6.server.game.debug.Debug;
import it.polimi.ingsw.LM6.server.network.ConnectionSetupMethods;
import it.polimi.ingsw.LM6.server.network.SetupMessage;
import it.polimi.ingsw.LM6.server.network.exception.InvalidFormatException;
import it.polimi.ingsw.LM6.server.network.exception.InvalidRequestException;
import it.polimi.ingsw.LM6.server.network.exception.OldInstanceException;
import it.polimi.ingsw.LM6.server.users.SocketUser;

public class SocketConnectionSetupHandler extends Thread {
	private Socket userSocket;

	/** Manages the initial phase of a socket user connection to the game. Also
	 * 	keeps track of existing games and connected users.
	 * 	
	 * 	@author Emilio
	 */
	public void run() {
		SocketUser user = new SocketUser(userSocket);
		SetupMessage message = null;
		boolean gameIsCreated = false;
		SetupMessage setupMenu;
		
		//Authentication phase.
		do {
			String rawMessage = user.nextLine();
			Debug.print(rawMessage);
			
			try {
				message = SetupMessage.interpret(rawMessage, ";");
				
				if (!message.isLoginRequest()){
					message = SetupMessage.buildInvalidLogin("Unespected request.");
					message.send(user);
				}
				
				ConnectionSetupMethods.userAuthenticationProcedure(user, message);
				
			} catch (InvalidFormatException e) {
				message = SetupMessage.buildInvalidLogin(e.getMessage());
				message.send(user);
			} catch (IOException e) {
				message = SetupMessage.buildInvalidLogin("Login failed due to system failure.");
				message.send(user);
			}
			
			if(user.getNickname() == null)
				continue;
		
			setupMenu = SetupMessage.buildSetupMenu(" ", " ");
			setupMenu.send(user);

			//Pregame menu phase.
			do {
				try {					
					message = SocketConnectionSetupHandler.preGameMenu(user);
	
				} catch (InvalidRequestException | InvalidFormatException e) {
					setupMenu = SetupMessage.buildSetupMenu(e.getMessage(), " ");
					setupMenu.send(user);
				}
				
			} while (!message.isStartNewGame() && !message.isLogoutRequest());
			
			if(message.isLogoutRequest()){
				ConnectionSetupMethods.logout(user);
				user.setNickname(null);
				continue;
			}
		
			//New game creation.
			gameIsCreated = ConnectionSetupMethods.manageGameStart(user, message);
			
		} while(!gameIsCreated);
			
		Debug.print("Shutting ConnectionSetupHandler down...");
	}

	/** Sets the current parameters to correctly handle the connection request.
	 * 
	 *  @param userSocket_setup - The Socket associated with the connecting user.
	 *  
	 *  @author Emilio
	 */
	public void setup(Socket userSocket_setup) {
		this.userSocket = userSocket_setup;
	}
	
	/**	Allows the IUser to choose between watching the scoreboard and starting a new game.
	 * 	
	 * 	@param in - The Scanner associated with the IUser.
	 * 	@param out - The PrintWriter associated with the IUser.
	 * 	
	 * 	@return The startNewGame SocketSetupMessage sent by the IUser.
	 * 
	 * 	@throws InvalidRequestException, when an unexpected message is received.
	 * 	
	 * 	@author Emilio
	 */
	private static SetupMessage preGameMenu(SocketUser user) throws InvalidRequestException {
		SetupMessage message;
		
		do {
			String rawMessage = user.nextLine();
			Debug.print(rawMessage);
			
			message = SetupMessage.interpret(rawMessage, ";");
			
			if (!message.isShowScoreboard() && !message.isStartNewGame() && !message.isShowRecord() && !message.isLogoutRequest())
				throw new InvalidRequestException("Unespected message received.");
			else if (message.isShowScoreboard()) {
				int from = Integer.parseInt(message.getInfo());
				int to = Integer.parseInt(message.getOptionalInfo());
				
				ConnectionSetupMethods.showScoreboard(user, from, to);
			}
			else if (message.isShowRecord()){
				String playerToFind = message.getInfo();
				
				ConnectionSetupMethods.showRecord(user, playerToFind);
			}
			
		} while (!message.isStartNewGame() && !message.isLogoutRequest());

		return message;
	}

}