package it.polimi.ingsw.LM6.client;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface UserOperationStub extends Remote{
	

	public void setOptionalMessage(String s, ActionAllowed act) throws RemoteException;
	/**
	 * It adds a new actionAllowed in the buffer on the client with a setUpMenu actionHeader inside.
	 * @param s is put in the variable optionalInfo in the object actionAllowed
	 * @throws RemoteException if there are connection problems
	 */
	public void setSetUpMenu(String s) throws RemoteException;
	/**
	 * It adds a new actionAllowed in the buffer on the client with a scoreBoard actionHeader inside.
	 * @param s is put in the variable optionalInfo in the object actionAllowed
	 * @throws RemoteException if there are connection problems
	 */
	public void setScoreboard(String s) throws RemoteException;
	/**
	 * It adds a new actionAllowed in the buffer on the client with a loginRequest actionHeader inside.
	 * @param s is put in the variable optionalInfo in the object actionAllowed
	 * @throws RemoteException if there are connection problems
	 */
	public void setLoginRequest(String s) throws RemoteException;
	/**
	 * It adds a new actionAllowed in the buffer on the client with a Menu actionHeader inside.
	 * @param s is put in the variable optionalInfo in the object actionAllowed
	 * @throws RemoteException if there are connection problems
	 */
	public void setMenu(String s) throws RemoteException;
	/**
	 * It adds a new actionAllowed in the buffer on the client with a info actionHeader inside.
	 * @param s is put in the variable optionalInfo in the object actionAllowed
	 * @throws RemoteException if there are connection problems
	 */
	public void setInfo(String s) throws RemoteException;
	/**
	 * It adds a new actionAllowed in the buffer on the client with a board actionHeader inside.
	 * @param s is put in the variable optionalInfo in the object actionAllowed
	 * @throws RemoteException if there are connection problems
	 */
	public void setBoard(String s) throws RemoteException;
	/**
	 * It adds a new actionAllowed in the buffer on the client with a updateBoard actionHeader inside.
	 * @param s is put in the variable optionalInfo in the object actionAllowed
	 * @throws RemoteException if there are connection problems
	 */
	public void setUpdateBoard(String s) throws RemoteException;
	/**
	 * It adds a new actionAllowed in the buffer on the client with a players actionHeader inside.
	 * @param s is put in the variable optionalInfo in the object actionAllowed
	 * @throws RemoteException if there are connection problems
	 */
	public void setPlayers(String s) throws RemoteException;
	/**
	 * It adds a new actionAllowed in the buffer on the client with a playersUpdate actionHeader inside.
	 * @param s is put in the variable optionalInfo in the object actionAllowed
	 * @throws RemoteException if there are connection problems
	 */
	public void setPlayersUpdate(String s) throws RemoteException;
	/**
	 * It adds a new actionAllowed in the buffer on the client with a bonusAction actionHeader inside.
	 * @param s is put in the variable optionalInfo in the object actionAllowed
	 * @throws RemoteException if there are connection problems
	 */
	public void setBonusAction(String s) throws RemoteException;
	/**
	 * It adds a new actionAllowed in the buffer on the client with a bonusProduction actionHeader inside.
	 * @param s is put in the variable optionalInfo in the object actionAllowed
	 * @throws RemoteException if there are connection problems
	 */
	public void setBonusProduction(String s) throws RemoteException;
	/**
	 * It adds a new actionAllowed in the buffer on the client with a bonusHarvest actionHeader inside.
	 * @param s is put in the variable optionalInfo in the object actionAllowed
	 * @throws RemoteException if there are connection problems
	 */
	public void setBonusHarvest(String s) throws RemoteException;
	/**
	 * It adds a new actionAllowed in the buffer on the client with a bonusCouncilRequest actionHeader inside.
	 * @param s is put in the variable optionalInfo in the object actionAllowed
	 * @throws RemoteException if there are connection problems
	 */
	public void setBonusCouncilRequest(String s) throws RemoteException;
	/**
	 * It adds a new actionAllowed in the buffer on the client with a costChoiceRequest actionHeader inside.
	 * @param s is put in the variable optionalInfo in the object actionAllowed
	 * @throws RemoteException if there are connection problems
	 */
	public void setCostChoiceRequest(String s) throws RemoteException;
	/**
	 * It adds a new actionAllowed in the buffer on the client with a excommunicationChoiceRequest actionHeader inside.
	 * @param s is put in the variable optionalInfo in the object actionAllowed
	 * @throws RemoteException if there are connection problems
	 */
	public void setExcommunication(String s) throws RemoteException;
	/**
	 * It adds a new actionAllowed in the buffer on the client with a leaderDraftChoiceRequest actionHeader inside.
	 * @param s is put in the variable optionalInfo in the object actionAllowed
	 * @throws RemoteException if there are connection problems
	 */
	public void setLeaderDraft(String s) throws RemoteException;
	/**
	 * It adds a new actionAllowed in the buffer on the client with a tilesDraftChoiceRequest actionHeader inside.
	 * @param s is put in the variable optionalInfo in the object actionAllowed
	 * @throws RemoteException if there are connection problems
	 */
	public void setTilesDraft(String s) throws RemoteException;
	/**
	 * It adds a new actionAllowed in the buffer on the client with a lorenzoIlMagnificoChoiceRequest actionHeader inside.
	 * @param s is put in the variable optionalInfo in the object actionAllowed
	 * @throws RemoteException if there are connection problems
	 */
	public void setLorenzoIlMagnifico(String s) throws RemoteException;
	public boolean poke() throws RemoteException;
	public void setTerminated() throws RemoteException;
	public void setFamiliarOffer(String s) throws RemoteException;
	public void setEndGame(String s) throws RemoteException;
}