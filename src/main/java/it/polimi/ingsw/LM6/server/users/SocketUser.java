package it.polimi.ingsw.LM6.server.users;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.game.SocketPlayerGameListener;

public class SocketUser extends IUser {
	private Socket userSocket;
	
	/******CONSTRUCTORS******/
	
	/** @author Emilio
	 */
	public SocketUser(Socket userSocket){
		this.userSocket = userSocket;
	}
	
	/******GETTERS******/
	
	/** @author Emilio
	 */
	public Socket getSocket() {
		return this.userSocket;
	}
	
	/******SETTERS******/
	
	/** @author Emilio
	 */
	public void setSocket(Socket userSocket) {
		this.userSocket = userSocket;
	}
	
	/******MORE_METHODS******/
	
	/**	Receives the nextLine from the SocketUser.
	 * 
	 * 	@return The String received as a nextLine.
	 * 
	 * 	@author Emilio
	 */
	@SuppressWarnings("resource")
	public String nextLine(){
		Scanner in;
		
		try {
			in = new Scanner(userSocket.getInputStream());
			
			return in.nextLine();
		} catch(IOException e){
			System.err.print(e.getMessage());
		}
		
		return null;
	}
	
	/** @author Emilio
	 */
	@Override
	public void send(String m) {
		PrintWriter out;
		try {
			out = new PrintWriter(this.userSocket.getOutputStream());
			out.println(m);
			out.flush();
		} catch (IOException e) {
			System.err.println("The message could not be sent.");
		}
	}
	
	@Override
	public void listenTo(Player player){
		System.out.println("Creating and starting listener.");
		SocketPlayerGameListener listener = new SocketPlayerGameListener(game, player, userSocket);
		listener.start();
		player.setIsConnected(true);
  }
}