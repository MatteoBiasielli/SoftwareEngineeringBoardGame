package it.polimi.ingsw.LM6.server.game.board.spaces;

import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.game.bonuses.Bonus;
import it.polimi.ingsw.LM6.server.game.familiar.Familiar;

@FunctionalInterface
public interface ActionSpace {
	
	/**Allows to place the familiar in an actionSpace
	 * @param f - familair to be placed
	 * @param p - player that is placing the familiar
	 */
	public Bonus placeFamiliar(Player p, Familiar f);
}
