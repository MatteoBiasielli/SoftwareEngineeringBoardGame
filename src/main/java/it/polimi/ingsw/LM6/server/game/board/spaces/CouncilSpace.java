package it.polimi.ingsw.LM6.server.game.board.spaces;

import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.game.bonuses.Bonus;
import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;
import it.polimi.ingsw.LM6.server.game.familiar.Familiar;

public class CouncilSpace implements ActionSpace {
	private Player player;
	private Familiar familiar;
	private Bonus immediateBonus;
	private static final String SEPARATOR=":";
	
	public CouncilSpace(){
		this.player=null;
		this.familiar=null;
		this.immediateBonus=new Bonus(new ResourceSet(0,0,1,0,0,0,0), false, 'N',  false,'N', 0,null, false,false, 0, 1,false);
	}
	
	/**Sets the player and familiar. Sets the famiiar to placed status
	 * @return immediateBonus, which consists basically in one Council Bonus and a coin.
	 */
	@Override
	public Bonus placeFamiliar( Player p,  Familiar f) {
		this.player=p;
		this.familiar=f;
		f.setPlaced();
		return immediateBonus;
	
	}
	
	/** @return a string version of the space, containing data about who is placed are and which familiar the former has placed
	 * 
	 */
	@Override
	public String toString() {
		String app="";
		if(this.player!=null){	
			app+=this.player.getNickname();
		}
		if(this.familiar!=null){	
			app+=SEPARATOR+"("+this.familiar.getColourString()+")";
		}
		return app;
	}
	
	/**@return the Player placed here. Notice that CouncilSpace cannot exist if a player is not placed here, so it never returns a null pointer.
	 * 
	 */
	public Player getPlayer(){
		return this.player;
	}
}
