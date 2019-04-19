package it.polimi.ingsw.LM6.client;

import java.util.Scanner;

import it.polimi.ingsw.LM6.server.game.debug.Debug;

public class ClientLauncher {
	/**
	 * Main about the client. it has the client chose a type of connection, then create CLIHandler and start the client
	 * game thread in case of socket connection. in case of rmi connection...
	 * @param args
	 */
	public static void main(String[] args){
		//Debug.setDebug();
		MessageHandler messageHandler;
		Scanner in = new Scanner(System.in);
		//Insert ip
		System.out.printf("%s", "IP:");
		String ip =in.nextLine();
		while(!isIP(ip)){
			//re-insert ip if the string does not represent an ip address
			System.out.printf("%s", "IP:");
			ip=in.nextLine();
		}
		String choice;
		System.out.println("Choose the connection technology: Socket/RMI [S/R]");
		choice = in.nextLine();
		//verify that "choice" is one of the allowed characters
		while (choice.compareTo("S")!=0 && choice.compareTo("R")!=0){
			System.out.println("Invalid input. Try again.");
			choice = in.nextLine();
		}
		
		
		System.out.println("Choose the interface: CLI/GUI [C/G]");
		String choice1 = in.nextLine();
		//verify that "choice" is one of the allowed characters
		while (choice1.compareTo("G")!=0 && choice1.compareTo("C")!=0){
			System.out.println("Invalid input. Try again.");
			choice1 = in.nextLine();
		}
		
		/*creating the message handler (GUI/CLI) 
		 */
		if(("G").equals(choice1))
			messageHandler = new GUIHandler();
		else
			messageHandler = new CLIHandler();
		
		/*connection setup*/
		if(choice.compareTo("S")==0){
			IClient userSocket = new SocketClient(messageHandler);
			//this method set the socket's attribute of the client
			userSocket.startConnection(ip);
			ClientSender sender = new ClientSender(userSocket);
			sender.start(); 
		}
		else {	
			IClient userRMI = new RMIClient(messageHandler);
			userRMI.startConnection(ip);
			ClientSender sender = new ClientSender(userRMI);
			sender.start();
		}
		return;	
	}
	
	/** checks that the given string represents an ip address
	 * 
	 * @param s - the string
	 * @return true if the string represents an ip address, false otherwise
	 */
	private static boolean isIP(String s){
		return s.matches("^[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}$");
	}
}
