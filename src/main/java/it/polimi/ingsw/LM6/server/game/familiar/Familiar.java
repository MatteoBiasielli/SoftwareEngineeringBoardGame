package it.polimi.ingsw.LM6.server.game.familiar;

import it.polimi.ingsw.LM6.server.game.Player;

public class Familiar {
	private Player owner;
	private FamiliarColor colour;
	private Boolean isPlaced;
	private int value;
	
	public Familiar(Player p, FamiliarColor fcol){
		this.owner=p;
		this.colour=fcol;
		this.isPlaced=false;
		this.value=-100;
	}
	
	public Player getOwner() {
		return owner;
	
	}
	public String getColourString(){
		return this.colour.getColourString();
	}
	public int getStrength() {
		return this.value;
	
	}
	public void setStrength(int s){
		value=s;
	}
	public Boolean isPlaced(){
		return isPlaced;
	}
	
	
	/**sets isPlaced to true
	 * must be called when the familiar is placed in an actionSpace
	 * It Indicates that it cannot be placed before a new turn starts
	 */
	public void setPlaced() {
		isPlaced=true;
	}
	
	
	/**sets isPlaced to false
	 * must be called when a new turn starts
	 * It indicates that the familiar is available to be placed
	 */
	public void resetPlaced(){
		isPlaced=false;
	}
	public FamiliarColor getColour() {
		return this.colour;
	}
}
