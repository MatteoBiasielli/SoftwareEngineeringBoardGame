package it.polimi.ingsw.LM6.client;

import java.rmi.RemoteException;

import it.polimi.ingsw.LM6.client.exceptions.OutputNotNecessaryException;

public interface IClient {
	
	/**
	 * It takes the client's message and send it to the server in a different way if the client is an Rmi one or a Socket One
	 * @param message is the message that will be send
	 * @throws RemoteException if there are connection problems
	 */
	public void sendToServer(String message) throws RemoteException;
	/**
	 * It takes all client inputs looking at a message received from the server.
	 * @return client's input choices encapsulated with a corresponding header
	 * @throws OutputNotNecessaryException if the client input is not necessary to be sent to the server
	 */
	public String getClientInput() throws OutputNotNecessaryException;
	/**
	 * It works to have the client start talking with the server.
	 * @return Client username and password encapsulated with a corresponding header.
	 */
	public String getClientNickAndPass();
	/**
	 * It works to start client's connection. With a socket client it performs a "new Socket", with a RMI client it performs a "LookUp" invocation.
	 * @param a is the IP destination for socket client, the name of binded object for the RMI client.
	 */
	public void startConnection(String a);
}

