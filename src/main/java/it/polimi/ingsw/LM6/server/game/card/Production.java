package it.polimi.ingsw.LM6.server.game.card;

import it.polimi.ingsw.LM6.server.game.Action;
import it.polimi.ingsw.LM6.server.game.bonuses.Bonus;
import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;
@FunctionalInterface
public interface Production {
	public Bonus activate(Action a, ResourceSet cost);
	@Override
	public String toString();
}
