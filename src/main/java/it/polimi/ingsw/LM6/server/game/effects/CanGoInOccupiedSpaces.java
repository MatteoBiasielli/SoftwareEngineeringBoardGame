package it.polimi.ingsw.LM6.server.game.effects;

import it.polimi.ingsw.LM6.server.game.exceptions.*;

public class CanGoInOccupiedSpaces extends Effect{
	String description;
	public CanGoInOccupiedSpaces(String s){
		this.description=s;
	}
	@Override
	public String toString(){
		return this.description;
	}
	@Override
	public void applyCanGoInOccupiedSpaces() throws CanGoInOccupiedSpacesException{
		throw new CanGoInOccupiedSpacesException("You can place your familiars in occupied spaces.");
	}
}