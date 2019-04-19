package it.polimi.ingsw.LM6.server.game.board.spaces;

import java.util.ArrayList;

import it.polimi.ingsw.LM6.server.game.Action;
import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.game.bonuses.*;
import it.polimi.ingsw.LM6.server.game.exceptions.NotEnoughResourcesException;
import it.polimi.ingsw.LM6.server.game.exceptions.NotStrongEnoughException;
import it.polimi.ingsw.LM6.server.game.states.CouncilState;

public class Council {
	CouncilState state;
	
	
	public Council(){
		this.state=new CouncilState();
	}
	
	/**@param action contains all the data required to perform the action
	 * 
	 * @return true if, considering the given number of servants, the familiar f 
	 *      of the Player p is Strong enough to be placed on the council,
	 *		 false otherwise
	 */
	public Boolean strongEnough(Action action) {
		return action.getActionStrength()>=this.state.getStrCondition();	
	}
	
	
	/** @return a string version of the Council
	 * 
	 */
	@Override
	public String toString() {
		return this.state.toString();
	
	}
	
	
	/**@return a list of the players that placed a familiar in the council.
	 * The list is ordered, from the player that first placed a Familiar in the council to the last one.
	 */
	public ArrayList<Player> getPlayersInCouncil(){
		ArrayList<Player> players=new ArrayList<>();
		ArrayList<CouncilSpace> spacesUsed=this.state.getSpaces();
		for(CouncilSpace csp: spacesUsed)
			if(!players.contains(csp.getPlayer()))
				players.add(csp.getPlayer());
		return players;
	}
	
	/**Clears the council, setting it ready for a new turn.
	 */
	public void newTurn() {
		this.state.newTurn();
	}
	
	
	/**Allocates a new council Space and calls its placeFamiliar method.
	 * 
	 * @param action contains all the data required to perform the action
	 * @throws NotStrongEnoughException if the familiar if not strong enough.
	 * @throws NotEnoughResourcesException if the player doesn't have the required resources
	 * 
	 * @return the Bonus gained from the action
	 */
	public Bonus placeFamiliar(Action action) throws NotStrongEnoughException, NotEnoughResourcesException{
		action.getPlayer().getPermanentBonusMalus().applyModifier(action);
		if(!this.strongEnough(action))
			throw new NotStrongEnoughException("Your familiar's value is too low to perform this action");
		ResourceSet cost=new ResourceSet(0,0,0,action.getServants(),0,0,0);
		action.getPlayer().pay(cost);
		return this.state.placeFamiliar(action);
	}
}
