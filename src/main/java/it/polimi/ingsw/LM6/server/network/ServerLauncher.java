package it.polimi.ingsw.LM6.server.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.LM6.server.game.debug.Debug;
import it.polimi.ingsw.LM6.server.network.rmi.IWelcomeImpl;
import it.polimi.ingsw.LM6.server.network.socket.SocketServer;

public class ServerLauncher{
	private static final String LOGGER = "ServerLauncher";
	private static final int SOCKET_PORT = 23456;
	private static final int REGISTRY_PORT = 8080;
	
	private static IWelcomeImpl welcome;
	
	/**	Sets up the NetworkData and creates a threads to accept SocketUsers. After that, it starts accepting RMIUsers.
	 * 
	 * 	@author Emilio
	 */
	public static void main(String args[]) {	
		//Debug.setDebug();
		
		try{
		
		NetworkData.getGlobalScoreboard().load();
		NetworkData.loadGamesData();
		Debug.print("NetworkData has been initialized.");
		
		ServerLauncher.startSocketServer();
		
		ServerLauncher.startRMIServer();
		
		} catch(IOException | AlreadyBoundException e){
			Logger.getLogger(LOGGER).log(Level.SEVERE, e.getMessage());
		}
	}
	
	/**	Sets the parameters of the SocketServer and starts the server.
	 * 	
	 * 	@author Emilio
	 */
	public static void startSocketServer(){
		SocketServer server = new SocketServer();
		ServerSocket sc = null;
		
		while(sc == null){
			try{
				sc = new ServerSocket(SOCKET_PORT);
			} catch (IOException e){
				System.out.println("ServerSocket setup failed. Trying again in 5 seconds...");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					Thread.currentThread().interrupt();
					Logger.getLogger("MyLogger").log(Level.SEVERE, e1.getMessage());
				}
			}
		}
		
		server.setup(sc);
		server.start();
	}
	
	/**	Sets the parameters of the RMIServer and starts the server.
	 * 	
	 * 	@author Emilio
	 */
	public static void startRMIServer() throws IOException, AlreadyBoundException{
		welcome = new IWelcomeImpl();
		Logger.getLogger("MyLogger").log(Level.CONFIG, InetAddress.getLocalHost().getHostAddress());
		System.setProperty("java.rmi.server.hostname",InetAddress.getLocalHost().getHostAddress());
		Registry registry = LocateRegistry.createRegistry(REGISTRY_PORT);
		registry.bind("IWelcome", welcome);
		Debug.print("RMIServer is ready. Waiting for connections...");
	}
}