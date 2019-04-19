package it.polimi.ingsw.LM6.server.game.card;

import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.game.bonuses.Bonus;
import it.polimi.ingsw.LM6.server.game.bonuses.PermanentBonusMalus;
import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;
import it.polimi.ingsw.LM6.server.game.effects.BonusHarvest;
import it.polimi.ingsw.LM6.server.game.effects.BonusProduction;
import it.polimi.ingsw.LM6.server.game.effects.EveryTurnBonus;

public class LeaderCard {
	private int number;
	private ResourceSet resourcesRequired;
	private int greenCardRequirement;
	private int yellowCardRequirement;
	private int blueCardRequirement;
	private int purpleCardRequirement;
	private PermanentBonusMalus permanentBonus;
	private String name;
	private String description;
	private Boolean sixCardReq;
	private static final String SEPARATOR="ù";
	
	
	public LeaderCard(String s){
		String[] app=s.split(";");
		this.name=app[0];
		this.resourcesRequired=new ResourceSet(Integer.parseInt(app[1]),Integer.parseInt(app[2]),Integer.parseInt(app[3]),
				Integer.parseInt(app[4]),Integer.parseInt(app[5]),Integer.parseInt(app[7]),Integer.parseInt(app[6]));
		this.sixCardReq=Boolean.parseBoolean(app[8]);
		this.greenCardRequirement=Integer.parseInt(app[9]);
		this.yellowCardRequirement=Integer.parseInt(app[10]);
		this.blueCardRequirement=Integer.parseInt(app[11]);
		this.purpleCardRequirement=Integer.parseInt(app[12]);
		Bonus everyTurnBonus=new Bonus(new ResourceSet(Integer.parseInt(app[13]),Integer.parseInt(app[14]),Integer.parseInt(app[15]),
				Integer.parseInt(app[16]),Integer.parseInt(app[17]),Integer.parseInt(app[19]),Integer.parseInt(app[18])),
				false,'N', false,'N', 0, new ResourceSet(0,0,0,0,0,0,0), Boolean.parseBoolean(app[22]),Boolean.parseBoolean(app[23]), 
				Integer.parseInt(app[24]), Integer.parseInt(app[20]),Boolean.parseBoolean(app[21]));
		
		this.permanentBonus=new PermanentBonusMalus(0, 0 ,0 ,0, 0, 0,Integer.parseInt(app[26]),Integer.parseInt(app[25]),
					Integer.parseInt(app[27]),Integer.parseInt(app[28]), app[29]);
		if(!everyTurnBonus.isEmpty()){
			if(!everyTurnBonus.hasEmptyResourceSet() || everyTurnBonus.getCouncilBonusesNumber()>0)
				this.permanentBonus.add(new EveryTurnBonus(everyTurnBonus, "everyTurnBonus"));
			if(everyTurnBonus.hasBonusHarvest())
				this.permanentBonus.add(new BonusHarvest(everyTurnBonus, "bonusHarvest"));
			if(everyTurnBonus.hasBonusProduction())
				this.permanentBonus.add(new BonusProduction(everyTurnBonus, "bonusProduction"));
		}
			
		this.description=app[30];
		this.number=Integer.parseInt(app[31]);
		
	}
	
	/**@return true if the player respects the activation conditions,
	 * false otherwise
	 */
	public Boolean canBeActivatedBy( Player p) {
		if(this.sixCardReq && (p.getBlueCardList().size()==6 || p.getYellowCardList().size()==6)) 
			return true;
		else if(this.sixCardReq && (p.getGreenCardList().size()==6 || p.getPurpleCardList().size()==6))
			return true;
		else if(this.sixCardReq)
			return false;
		if(!p.getResourceSet().contains(this.resourcesRequired))
			return false;
		if(!(p.getGreenCardList().size()>=this.greenCardRequirement))
			return false;
		if(!(p.getBlueCardList().size()>=this.blueCardRequirement))
			return false;
		if(!(p.getYellowCardList().size()>=this.yellowCardRequirement))
			return false;
		if(!(p.getPurpleCardList().size()>=this.purpleCardRequirement))
			return false;
		
		return true;
	}
	
	
	/**gives the bonus to the player
	 *@param p - the player that is activating the leader card 
	 */
	public void activate( Player p) {
		p.acquirePermBonus(this.permanentBonus);
	}
	
	
	/** @return a string version of the leader
	 * 
	 */
	@Override
	public String toString() {
		String app= name+"   "+description + "   REQUIREMENT=";
		if(this.sixCardReq)
			app+="sixCardReq";
		else{
			app+=this.resourcesRequired.toString();
			if(this.greenCardRequirement!=0)
				app+="  GC="+this.greenCardRequirement;
			if(this.blueCardRequirement!=0)
				app+="  BC="+this.blueCardRequirement;
			if(this.yellowCardRequirement!=0)
				app+="  YC="+this.yellowCardRequirement;
			if(this.purpleCardRequirement!=0)
				app+="  PC="+this.purpleCardRequirement;
		}
		app+=SEPARATOR+this.number;
		
		return app;
	}

	public String getName() {
		return this.name;
	}

	public int getNumber() {
		return this.number;
	}
}
