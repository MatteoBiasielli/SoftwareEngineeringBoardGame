package it.polimi.ingsw.LM6.server.game.card;

import java.util.ArrayList;
import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.game.bonuses.PermanentBonusMalus;
import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;
import it.polimi.ingsw.LM6.server.game.exceptions.BadInputException;



public class ExcommunicationCard {
	private int number;
	private PermanentBonusMalus malus;
	private ArrayList<String> excommunicatedPlayers;
	private String description;
	private int era;
	private static final String SEPARATOR="!";
	
	
	
	public ExcommunicationCard(String[] app) {
		this.excommunicatedPlayers=new ArrayList<>();
		this.era=Integer.parseInt(app[0]);
		this.description=app[12];
		
		this.malus=new PermanentBonusMalus(Integer.parseInt(app[1]), Integer.parseInt(app[2]) ,Integer.parseInt(app[6]),Integer.parseInt(app[4]),
					Integer.parseInt(app[3]), Integer.parseInt(app[5]),Integer.parseInt(app[7]),Integer.parseInt(app[8]),
					Integer.parseInt(app[9]),Integer.parseInt(app[10]), app[11]);
		this.number=Integer.parseInt(app[13]);
	}
	

	/**@return a string version of the excommunicationCard, contaning the malus given and excommunicated players
	 * 
	 */
	@Override
	public String toString() {
		String app;
		app=malus.toString()+SEPARATOR+number+SEPARATOR;
		for(String s:this.excommunicatedPlayers)
			app+=s+SEPARATOR;
		return app;
	
	}
	
	/**Adds the player to the list of excommunicated players and
	 * gives the malus to the player
	 * @param p - the player
	 */
	public void excommunicatePlayer(Player p){
		p.acquirePermBonus(this.malus);
		excommunicatedPlayers.add(p.getNickname());
	}

	public String getDescription() {
		return description;
	}
	
	public ResourceSet costOfExcommunication(){
		switch(era){
			case 1:
					return new ResourceSet(0,0,0,0,0,0,3);
			case 2:
					return new ResourceSet(0,0,0,0,0,0,4);
			case 3: 
					return new ResourceSet(0,0,0,0,0,0,5);
			default: throw new BadInputException("bad input. Verify config files.");
		}
	}


	public int getNumber() {
		return this.number;
	}	
}
