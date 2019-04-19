package it.polimi.ingsw.LM6.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import it.polimi.ingsw.LM6.client.exceptions.OutputNotNecessaryException;


public class SocketClient implements IClient{
	private MessageHandler messageHandler;
	private Socket socket;
	Scanner in;
	PrintWriter out;
	
	protected SocketClient(MessageHandler a){
		this.messageHandler=a;
	}
	
	@Override
	public void startConnection(String ip){
		try {
			this.socket= new Socket(ip,23456);
			out = new PrintWriter(this.socket.getOutputStream());
			in = new Scanner(this.socket.getInputStream());	
		} catch (IOException e) {
			System.out.println("Connection error occurred.");
			System.exit(0);
		}
	}
	
	@Override
	public void sendToServer(String message){	
		out.println(message);
		out.flush();	
	}


	@Override
	public String getClientInput() throws OutputNotNecessaryException{	
			String output;
			String b = in.nextLine();
			output = this.messageHandler.handleMessage(b);
			return output;  
	}


	@Override
	public String getClientNickAndPass(){
		try {
			return this.messageHandler.handleMessage("INVALIDLOGIN; ");
		} catch (OutputNotNecessaryException e) {
			//It never happens.
		}
		return null;
	}
	
	
	
	
}
		


