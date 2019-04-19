package it.polimi.ingsw.LM6.server.game.board.spaces;

import java.util.ArrayList;

import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.game.bonuses.Bonus;
import it.polimi.ingsw.LM6.server.game.familiar.Familiar;

public class ProductionSpace implements ActionSpace {
	private ArrayList<Player> player;
	private ArrayList<Familiar> familiar;
	private final String SEPARATOR=":";
	
	
	public ProductionSpace(){
		this.player=new ArrayList<>();
		this.familiar=new ArrayList<>();
	}
	/**sets player and familiar fields. Sets teh familiar's status to placed
	 * @return null. this return is useless
	 */
	@Override
	public Bonus placeFamiliar( Player p,  Familiar f) {
		player.add(p);
		familiar.add(f);
		f.setPlaced();
		return null;
	}
	
	/** @return a string version of the space 
	 * 
	 */
	@Override
	public String toString() {
		String app="";
		boolean used=false;
		int s=player.size();
		for(int i=0;i<s;i++){
			used=true;
			app+=player.get(i).getNickname()+SEPARATOR+"("+familiar.get(i).getColourString()+")"+"-";
		}
		if(!used)
			return " ";
		return app;
	
	}
	
	/**Clears the space, setting ready for a new turn
	 * 
	 */
	public void newTurn(){
		this.player=new ArrayList<>();
		this.familiar=new ArrayList<>();
	}
	
	/**@return true if the space is occupied, false otherwise
	 * 
	 */
	public Boolean isOccupied(){
		return !this.player.isEmpty();
	}
	
	
	public ArrayList<Player> getPlayers(){
		return this.player;
	}
	public ArrayList<Familiar> getFamiliars(){
		return this.familiar;
	}
}
