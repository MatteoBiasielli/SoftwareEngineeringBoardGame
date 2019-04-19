package it.polimi.ingsw.LM6.server.game.board.spaces;

import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.game.bonuses.Bonus;
import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;
import it.polimi.ingsw.LM6.server.game.card.Card;
import it.polimi.ingsw.LM6.server.game.familiar.Familiar;

public class TowerSpace implements ActionSpace {
	private ResourceSet immediateBonus;
	private Boolean isUsed;
	private Familiar familiar;
	private Player player;
	private int strCondition;
	private Card associatedCard;
	private static final String SEPARATOR="ì";
	private final String SEPARATOR2="ù";
	
	public TowerSpace(String app){
		this.player=null;
		this.familiar=null;
		this.isUsed=false;
		this.associatedCard=null;
		String[] appSplit=app.split(";");
		int position=Integer.parseInt(appSplit[0]);
		if(position==3)
			this.strCondition=7;
		else if(position==2)
			this.strCondition=5;
		else if(position==1)
			this.strCondition=3;
		else
			this.strCondition=1;
		this.immediateBonus=new ResourceSet(Integer.parseInt(appSplit[1]),Integer.parseInt(appSplit[2]),Integer.parseInt(appSplit[3])
				,Integer.parseInt(appSplit[4]),Integer.parseInt(appSplit[5]),Integer.parseInt(appSplit[6]),0);
	}


	
	@Override
	public Bonus placeFamiliar( Player p,  Familiar f) {
		this.isUsed=true;
		this.player=p;
		this.familiar=f;
		f.setPlaced();
		this.associatedCard.giveCardTo(p);
		Bonus b=this.associatedCard.getImmediateBonus();
		this.associatedCard=null;
		return b;
	
	}
	
	/**@return true if the space has already been used,
	 * false otherwise
	 */
	public Boolean isUsed() {
		return isUsed;
	
	}
	
	
	/**called when a new turn starts,clears the tower space 
	 * */
	public void newTurn(){
		this.isUsed=false;
		this.familiar=null;
		this.player=null;
		this.associatedCard=null;
		
	}
	
	
	/**associates a new card to the space
	 * called by TowerState.newTurn()
	 * @param c - the card that has to be associated with the space
	 */
	public void associateNewCard( Card c) {
		this.associatedCard=c;
	}
	
	
	/**@return a string version of the space
	 * 
	 */
	@Override
	public String toString() {
		String app="";
		
		if(this.associatedCard!=null){
			app+=this.associatedCard.toString();
			app+=SEPARATOR;
		}
		else
			app+=" "+SEPARATOR;
		app+="Str="+ Integer.toString(this.strCondition) +SEPARATOR2+this.immediateBonus.toString();
		if(this.isUsed)
			app+=SEPARATOR2+this.player.getNickname()+SEPARATOR2+"("+this.familiar.getColourString()+")";
		else
			app+=SEPARATOR2+" ";
		return app;
	
	}

	public Player getPlayer() {
		return this.player;
	}
	public int getStrCondition() {
		return this.strCondition;
	}
	public ResourceSet getImmediateBonus(){
		return this.immediateBonus;
	}

	public Card getAssociatedCard() {
		return this.associatedCard;
	}

	public Familiar getFamiliar() {
		return this.familiar;
	}
}
