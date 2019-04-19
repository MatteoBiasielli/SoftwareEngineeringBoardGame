package it.polimi.ingsw.LM6.server.game.effects;

import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.game.exceptions.AlreadyUsedBonusException;
import it.polimi.ingsw.LM6.server.game.familiar.Familiar;
import it.polimi.ingsw.LM6.server.game.familiar.FamiliarColor;

public class OneSixFamiliarPerTurn extends Effect{
	private String description;
	private boolean isUsed;
	public OneSixFamiliarPerTurn(String description){
		this.description=description;
		this.isUsed=false;
	}
	@Override
	public String toString(){
		return this.description;
	}
	@Override
	public boolean applyOneSixFamiliarPerTurn(Player p, Familiar f) throws AlreadyUsedBonusException{
		if(!this.isUsed){
			if(f.getColour()==FamiliarColor.UNCOLOURED)
				throw new AlreadyUsedBonusException("You must raise a coloured familiar's value.");
			this.isUsed=true;
			f.setStrength(6);
			return true;
		}
		else
			throw new AlreadyUsedBonusException("This bonus has already been used");
	}
	@Override
	public void resetEveryTurnBonuses(){
		this.isUsed=false;
	}

}