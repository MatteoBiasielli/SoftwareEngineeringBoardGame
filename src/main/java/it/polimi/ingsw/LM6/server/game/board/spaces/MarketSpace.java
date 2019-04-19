package it.polimi.ingsw.LM6.server.game.board.spaces;

import java.util.ArrayList;

import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.game.bonuses.Bonus;
import it.polimi.ingsw.LM6.server.game.familiar.Familiar;

public class MarketSpace implements ActionSpace {
	private Bonus immediateBonus;
	private ArrayList<Player> player;
	private ArrayList<Familiar> familiar;
	private static final String SEPARATOR=":";
	private static final String SEPARATOR2="ù";

	MarketSpace(Bonus b){
		this.player=new ArrayList<>();
		this.familiar=new ArrayList<>();
		this.immediateBonus=b;
	}
	/**Sets Player and familiar fields
	 * @return the Bonus corresponding the space.
	*/
	@Override
	public Bonus placeFamiliar( Player p,  Familiar f) {
		this.player.add(p);
		this.familiar.add(f);
		f.setPlaced();
		return new Bonus(this.immediateBonus);
	}
	
	
	/**clears the MarketSpace and prepares it for a new turn*/
	public void newTurn() {
		this.player=new ArrayList<>();
		this.familiar=new ArrayList<>();
	}
	
	/** return a string version of the space*/
	@Override
	public String toString() {
		String app="";
		app+=immediateBonus.toString()+SEPARATOR2;
		if(!this.player.isEmpty()){
			int s=this.player.size();
			for(int i=0;i<s;i++){
				if(i!=0)
					app+="-";
				app+=player.get(i).getNickname()+SEPARATOR+"("+familiar.get(i).getColourString()+")";
			}
		}
		else
			app+=" ";
		return app;
	}
	
	/**@ return true if the space is occupied,
	 * false otherwise
	 */
	public Boolean isOccupied() {
		return !this.player.isEmpty();
	
	}
}
