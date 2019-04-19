package it.polimi.ingsw.LM6.client;

import it.polimi.ingsw.LM6.client.exceptions.OutputNotNecessaryException;
@FunctionalInterface
public interface MessageHandler {
	/**
	 * This method works as a handler to all message received from the server
	 * @param message is the string received from the server
	 * @return a string as a reply for the server
	 * @throws OutputNotNecessaryException if the output is unnecessaryas a server reply
	 */
	public String handleMessage(String message) throws OutputNotNecessaryException;
}
