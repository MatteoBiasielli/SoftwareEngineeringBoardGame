package it.polimi.ingsw.LM6.server.users;

import it.polimi.ingsw.LM6.server.game.LorenzoIlMagnifico;
import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.network.NetworkData;

public abstract class IUser {
	protected String nickname;
	protected LorenzoIlMagnifico game;
	
	/******GETTERS******/
	
	/** @author Emilio
	 */
	public String getNickname(){
		return this.nickname;
	}
	
	/** @author Emilio
	 */
	public LorenzoIlMagnifico getGame(){
		return this.game;
	}
	
	/******SETTERS******/
	
	/** @author Emilio
	 */
	public void setNickname(String nickname){
		this.nickname = nickname;
	}
	
	/** @author Emilio
	 */
	public void setGame(LorenzoIlMagnifico game){
		this.game = game;
	}
	
	/******MORE_METHODS******/
	
	/**	Compares the IUser on which it is called with the one passed as a parameter.
	 * 
	 * 	@param user - The IUser to compare.
	 * 
	 * 	@return true, if they have the same nickname. | false, if they have different nicknames or one of them is null.
	 * 
	 * 	@author Emilio
	 */
	public boolean equals(IUser user){
		if(user==null)
			return false;
		return this.nickname.equals(user.nickname);
	}
	
	/**
	 * It looks at the header of the String m, then calls the corresponding method on the client's remote object (UserOperationStub)
	 * saved on the server.
	 * @param m is the string sent to client
	 */
	public abstract void send(String m);
	
	/**	Substitutes the IUser user in the game he/she is in with the IUser on which it is called.
	 * 
	 * 	@param user - The IUser to substitute.
	 * 
	 * 	@author Emilio
	 */
	public void reconnectAs(IUser user){
		this.game = user.getGame();
		Player myPlayer = null;
			
		myPlayer = this.game.findPlayer(user);
		myPlayer.setIUser(this);
		
		NetworkData.getUsers().remove(user);
		NetworkData.getUsers().add(this);

		this.listenTo(myPlayer);
		this.send("INFO; ;GAMESTART");
	}
	
	public abstract void listenTo(Player player);
}