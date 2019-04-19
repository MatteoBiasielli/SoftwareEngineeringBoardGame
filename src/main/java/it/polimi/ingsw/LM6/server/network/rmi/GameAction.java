//Needs fixing later
package it.polimi.ingsw.LM6.server.network.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface GameAction extends Remote{
	
	/**
	 * It takes the board by calling the corresponding method on the game, then send it to the client
	 * @throws RemoteException if there are connection problems
	 */
	public void showBoard() throws RemoteException;
	/**
	 * It takes the players tiles by calling the corresponding method on the game, then send it to the client
	 * @throws RemoteException if there are connection problems
	 */
	public void showPlayers() throws RemoteException;
	/**
	 * It set the player's excommunication choice in the game only if it wouldn't be excommunicated.
	 * @param s if the player's choice
	 * @throws RemoteException if there are connection problems
	 */
	public void setExcommunicationResult(String s) throws RemoteException;
	
	/**
	 * It take the client's choices then builds an action and calls the corresponding method on the game. The bonus taken by the action is
	 * temporally saved in a temporal bonus on the server.
	 * @param towerColour is a player's choice
	 * @param floor is a player's choice
	 * @param family is a player's choice
	 * @param servants is a player's choice
	 * @throws RemoteException if there are connection problems.
	 */
	public void towerAction(String towerColour, String floor, String family, String servants) throws RemoteException;
	
	/**
	 * It take the choice of the client, then, after assembling the previous string, builds an action and calls the corresponding method on the
	 * game.
	 * @param number is a player's choice
	 * @throws RemoteException if there are connection problems.
	 */
	public void towerActionWithChoice(String number) throws RemoteException;
	
	/**
	 *  It take the client's choices then builds an action and calls the corresponding method on the game. The bonus taken by the action is
	 * temporally saved in a temporal bonus on the server.
	 * @param place is a player's choice
	 * @param family is a player's choice
	 * @param servants is a player's choice
	 * @throws RemoteException if there are connection problems
	 */
	public void marketAction(String place, String family, String servants) throws RemoteException;
	
	/**
	 * It take every single player's card choice for production then builds an action and calls the corresponding method on the game. 
	 * The bonus taken by that action will be saved in a temporal bonus variable on the server.
	 * @param family is a player's choice
	 * @param servants is a player's choice
	 * @param floor is a player's choice
	 * @param c1 is the choice about the first yellow card owned (activate or not)
	 * @param c2 is the choice about the second yellow card owned (activate or not)
	 * @param c3 is the choice about the third yellow card owned (activate or not)
	 * @param c4 is the choice about the fourth yellow card owned (activate or not)
	 * @param c5 is the choice about the fifth yellow card owned (activate or not)
	 * @param c6 is the choice about the sixth yellow card owned (activate or not)
	 * @throws RemoteException if there are connection problems
	 */
	public void productionAction(String family, String servants, String floor, String c1, String c2, String c3, String c4, String c5, String c6) throws RemoteException;
	/**
	 * It takes the player's choices about this action then builds an action and calls the corresponding method on the game.
	 * The bonus taken by that action will be saved in a temporal bonus variable on the server.
	 * @param family is a player's choice
	 * @param slaves is a player's choice
	 * @param floor is a player's choice
	 * @throws RemoteException if there are connection problems.
	 */
	public void harvestAction(String family, String slaves, String floor) throws RemoteException;
	/**
	 * It takes the player's choices about this action then builds an action and calls the corresponding method on the game.
	 * The bonus taken by that action will be saved in a temporal bonus variable on the server.
	 * @param family is a player's choice
	 * @param servants is a player's choice
	 * @throws RemoteException if there are connection problems
	 */
	public void councilAction(String family, String servants) throws RemoteException;
	/**
	 *  It takes the player's choices about this action then builds an action and calls the corresponding method on the game.
	 * The bonus taken by that action will be saved in a temporal bonus variable on the server.
	 * @param number is a player's choice
	 * @throws RemoteException if there are connection problems
	 */
	public void leaderDiscard(String number) throws RemoteException;
	/**
	 *  It takes the player's choices about this action then builds an action and calls the corresponding method on the game.
	 * The bonus taken by that action will be saved in a temporal bonus variable on the server.
	 * @param number is a player choice
	 * @throws RemoteException if there are connection problems
	 */
	public void councilRequirement(String number) throws RemoteException;
	/**
	 *  It takes the player's choices about this action then builds an action and calls the corresponding method on the game.
	 * The bonus taken by that action will be saved in a temporal bonus variable on the server.
	 * @param number1 is the first choice about the council requirement
	 * @param number2 is the second one
	 * @throws RemoteException if there are connection problems
	 */
	public void doubleCouncilRequirement(String number1, String number2) throws RemoteException;
	/**
	 * it take the player's choices about the triple council requirement, then builds an action and calls the corresponding
	 * method on the game
	 * @param number1 is the first choice about the council requirement
	 * @param number2 is the second one
	 * @param number3 is the third one
	 * @throws RemoteException if there are connection problems
	 */
	public void tripleCouncilRequirement(String number1, String number2, String number3) throws RemoteException;
	/**
	 * It takes the player's choices about this action then builds an action and calls the corresponding method on the game.
	 * The bonus taken by that action will be saved in a temporal bonus variable on the server.
	 * @param slaves is a player's choice
	 * @param floor is a player's choice
	 * @param towerColour is a player's choice
	 * @throws RemoteException if there are connection problems
	 */
	public void bonusTowerAction(String slaves, String floor, String towerColour) throws RemoteException;
	/**
	 * It takes the player's choices about this action then builds an action and calls the corresponding method on the game.
	 * The bonus taken by that action will be saved in a temporal bonus variable on the server.
	 * @param servants is a player's choice
	 * @throws RemoteException if there are connection problems
	 */
	public void bonusHarvestAction(String servants) throws RemoteException;
	/**
	 * It take every single player's card choice for production then builds an action and calls the corresponding method on the game. 
	 * The bonus taken by that action will be saved in a temporal bonus variable on the server.
	 * @param servants is a player's choice
	 * @param c1 is the choice about the first yellow card owned (activate or not)
	 * @param c2 is the choice about the second yellow card owned (activate or not)
	 * @param c3 is the choice about the third yellow card owned (activate or not)
	 * @param c4 is the choice about the fourth yellow card owned (activate or not)
	 * @param c5 is the choice about the fifth yellow card owned (activate or not)
	 * @param c6 is the choice about the sixth yellow card owned (activate or not)
	 * @throws RemoteException if there are connection problems
	 */
	public void bonusProductionAction(String servants, String c1, String c2, String c3, String c4, String c5, String c6) throws RemoteException;
	/**
	 * it just calls playerShift method on the game. It also set the player as an active player and gives the resource in the temporal bonus to the player.
	 * @throws RemoteException if there are connection problems.
	 */
	public void pass() throws RemoteException;
	/**
	 * It takes the number of the leader card that the player decided to play, then calls the corresponding method on
	 * the game
	 * @param number is a player's choice
	 * @throws RemoteException if there are connection problems
	 */
	public void leaderPlay(String number) throws RemoteException;
	/**
	 * This method is called only if the player has previously played the card "Lorenzo deMedici". It takes the number
	 * of the leader card in bound that the player has decided to copy the permanent effect.
	 * @param number is the player's choice
	 * @throws RemoteException if there are some connection problems
	 */
	public void lorenzoIlMagnificoChoice(String number) throws RemoteException;
	/**
	 * It will be invoked if the player would obtain the resources given by a leader it owns in bound. it calls the 
	 * corresponding method on the game.
	 * @throws RemoteException if there are connection problems
	 */
	public void leaderResources() throws RemoteException;
	/**
	 * It will be invoked if the player would obtain the bonus production given by a leader it owns in bound. it calls the
	 * corresponding method on the game.
	 * @throws RemoteException if there are connection problems
	 */
	public void leaderProduction() throws RemoteException;
	/**
	 * It will be invoked if the player would obtain the bonus harvest given by a leader it owns in bound. it calls the
	 * corresponding method on the game.
	 * @throws RemoteException if there are connection problems
	 */
	public void leaderHarvest() throws RemoteException;
	/**
	 * It will be invoked if the player would obtain the bonus on a familiar given by a leader it owns in bound. it calls the
	 * corresponding method on the game.
	 * @throws RemoteException if there are connection problems
	 */
	public void leaderFamilyBonus(String colour) throws RemoteException;
	/**
	 * It will be invoked if the player takes a choice in the leader draft phase. it calls the
	 * corresponding method on the game.
	 * @throws RemoteException if there are connection problems
	 */
	public void leaderDraftChoice(String number) throws RemoteException;
	/**
	 * It will be invoked if the player takes a choice in the tiles draft phase. it calls the
	 * corresponding method on the game.
	 * @throws RemoteException if there are connection problems
	 */
	public void tilesDraftChoice(String number) throws RemoteException;
	/**
	 * It will be invoked if the player would renounce for one or more bonuses it owns. it calls the
	 * corresponding method on the game.
	 * @throws RemoteException if there are connection problems
	 */
	public void bonusRenounce(String s) throws RemoteException;
	
	public void setFamiliarOffer(String a, String b, String c, String d) throws RemoteException;
}
