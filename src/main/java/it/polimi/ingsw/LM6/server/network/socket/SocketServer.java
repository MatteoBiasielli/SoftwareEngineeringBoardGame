package it.polimi.ingsw.LM6.server.network.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import it.polimi.ingsw.LM6.server.game.debug.Debug;


public class SocketServer extends Thread{
	private ServerSocket socket;
	
	public void setup(ServerSocket socket_setup){
		this.socket = socket_setup;
	}
	
	/* Waits for Socket players' connection.
	 */
	public void run() {
		ExecutorService executor = Executors.newCachedThreadPool();
		Debug.print("SocketServer is ready. Waiting for connections...");
		
		while(true){
			try{
				Socket uSocket = socket.accept();
				this.handleRequest(executor, uSocket);
			} catch (IOException e){
				Debug.print("SocketServer connection closed.");
				break;
			}
		}
		executor.shutdown();
	}
	
	/* Creates a new thread to handle the connection request.
	 */
	private void handleRequest(ExecutorService executor, Socket uSocket) {
		SocketConnectionSetupHandler handler = new SocketConnectionSetupHandler();
		handler.setup(uSocket);
		executor.submit(handler);
	}
}