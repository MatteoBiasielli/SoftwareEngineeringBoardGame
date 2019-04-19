package it.polimi.ingsw.LM6.server.game.card;

import it.polimi.ingsw.LM6.server.game.Action;
import it.polimi.ingsw.LM6.server.game.bonuses.Bonus;
import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;

public class ExchangeProduction implements Production {
	private ResourceSet cost;
	private Bonus gain;
	
	public ExchangeProduction(Bonus prod, ResourceSet cost){
		this.gain=prod;
		this.cost=cost;
	}
	
	/**@return a string version or the production
	 * 
	 */
	@Override
	public String toString() {
		return this.cost.toString()+"=>"+this.gain.toString();
	}
	
	
	/**activates the production.
	 * adds the cost to the cost parameter
	 * @param a - the action object containing the data about the action that has to be done
	 * @param cost - a ResourceSet representing a cumulative cost. At the end of the procedure it is increased by the eventual cost of the production. If teh production doesn't ahve a cost, it remains untouched
	 * @return the real bonus obtained from the production
	 */
	@Override
	public Bonus activate(Action a, ResourceSet cost) {
		cost.internalSum(this.cost);
		Bonus ris=new Bonus();
		return ris.sum(gain,a.getPlayer());
	
	}
}
