package it.polimi.ingsw.LM6.server.game.states;

import java.util.ArrayList;
import it.polimi.ingsw.LM6.server.game.Action;
import it.polimi.ingsw.LM6.server.game.board.spaces.CouncilSpace;
import it.polimi.ingsw.LM6.server.game.bonuses.Bonus;
import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;
import it.polimi.ingsw.LM6.server.game.exceptions.BadInputException;
public class CouncilState {
	private ArrayList<CouncilSpace> councilSpaces;
	private int strCondition;
	private static final String SEPARATOR="!";
	
	public CouncilState(){
		this.strCondition=1;
		this.councilSpaces=new ArrayList<>();
	}
	
	/**@return a string version of the councilState
	 * 
	 */
	@Override
	public String toString(){
		String app="";
		app="Str=" + Integer.toString(this.strCondition)+ SEPARATOR+ 
			new Bonus(new ResourceSet(0,0,1,0,0,0,0), false, 'N',  false,'N', 0,null, false,false, 0, 1,false).toString()+SEPARATOR;
		for(CouncilSpace csp:councilSpaces){
			app+=csp.toString()+"-";
		}
		return app;
	}
	
	/**cleans the Council, setting it ready for a new turn
	 * 
	 */
	public void newTurn() {
		this.councilSpaces=new ArrayList<>();	
	}
	
	/** Creates the councilSpace for the placement and adds it to the list
	 * @return the immediate Bonus (1 coin and 1 CB)
	 * @param acrion - the action object containing the data rquire to perform the action
	 */
	public Bonus placeFamiliar(Action action){
		CouncilSpace app=new CouncilSpace();
		this.councilSpaces.add(app);
		return app.placeFamiliar(action.getPlayer(), action.getFamiliar());
	}
	public int getStrCondition() {
		return this.strCondition;
	}
	public ArrayList<CouncilSpace> getSpaces() {
		return this.councilSpaces;
	}
}
