package it.polimi.ingsw.LM6.server.game;

import it.polimi.ingsw.LM6.server.game.bonuses.Bonus;

public class ResponseBuilder {
	private ResponseBuilder(){
		//UNNECESSARY. ADDED TO HIDE THE PUBLIC IMPLICIT ONE
	}
	/**
	 * This method encapsulates a reply for the client when see that there is a bonus tower action in the bonus b
	 * @param b is the bonus owned by the player
	 * @return the reply witch will be send to client
	 */
	public static String buildBonusActionResponse(Bonus b){
		String tmp;
		String s;
		if(b.getBonusActionTarget()=='G'){
			tmp = new String("GREEN");
			s = new String("You got a green bonus action of value "+b.getBonusActionStrength());
		}
		else if(b.getBonusActionTarget()=='B'){
			tmp = new String("BLUE");
			s = new String("You got a blue bonus action of value "+b.getBonusActionStrength());
		}	
		else if(b.getBonusActionTarget()=='Y'){
			tmp = new String("YELLOW");
			s = new String("You got a yellow bonus action of value "+b.getBonusActionStrength());
		}
		else if(b.getBonusActionTarget()=='P'){
			tmp = new String ("PURPLE");
			s = new String("You got a purple bonus action of value "+b.getBonusActionStrength());
		}
		else {
			tmp= new String ("EVERY");
			s = new String("You got a bonus action of value "+b.getBonusActionStrength());
		}
		return "BONUSACTIONREQUEST"+";"+s+";"+tmp;
	}
	
	/**
	 * This method encapsulates a reply for the client when see that there is a bonus production in the bonus b
	 * @param p is necessary to get the number of yellow cards owned by that player
	 * @param b is the bonus
	 * @return the reply witch will be send to client
	 */
	public static String buildBonusProductionResponse(Player p, Bonus b){
		Integer k = p.getYellowCardList().size();
		String tmp = k.toString();
		return "BONUSPRODUCTIONREQUEST"+";"+"You got a bonus production of value "+b.getBonusPHStrength()+";"+tmp;
	}
	
	/**
	 * This method encapsulates a reply for the client when see that there is a bonus harvest in the bonus b
	 * @param b is the bonus
	 * @return the reply witch will be send to client
	 */
	public static String buildBonusHarvestResponse(Bonus b){
		return "BONUSHARVESTREQUEST"+";"+"You got a bonus harvest of value "+b.getBonusPHStrength();
	}
	
	/**
	 * This method encapsulates a reply for the client when see that there is at least one council bonus in the bonus b
	 * @param b is the bonus
	 * @return a different reply for the client depending on the number of council bonus
	 */
	public static String buildBonusCouncilResponse(Bonus b){
		String mess="BONUSCOUNCILREQUEST";
		if(b.getCouncilBonusesNumber()>=3 && b.mustBeDifferent())
			return mess +";"+"You got three different council privileges."+";"+"3";
		if(b.getCouncilBonusesNumber()==2 && b.mustBeDifferent())
			return mess+";"+"You got two different council privileges."+";"+"2";
		
		return mess+";"+"You got a council privilege."+";"+"1";
		
	}
	
	/**
	 *Sends the dynamic menu to the client looking at his performed actions this turn, game rules and leader cards
	 *owned.
	 * @param s is the optional message 
	 * @param player
	 * @param actionDone is a boolean witch says if the player has or not do an action this turn.
	 * @param game
	 */
	static public void SendMenu(String s, Player player, boolean actionDone, LorenzoIlMagnifico game){
		int numberOfYellowCard = player.getYellowCardList().size();
		String tmp ="MENU;"+s+";";
		
		if(!actionDone)
			tmp = tmp + "1;1;1;1;1;";
		else 
			tmp = tmp +"0;0;0;0;0;";
		if(game.hasAdvancedRules() && player.getLeadersInHand().size()>0)
			tmp = tmp + "1;1;";
		else
			tmp = tmp + "0;0;";
		
		tmp = tmp+"1;1;1;";
		
		if(player.getLeadersPlayed().size()==0)
			tmp = tmp + "0;0;0;0;";
		else
			tmp = tmp + "1;1;1;1;";
		
		tmp = tmp + numberOfYellowCard;
		
		player.send(tmp);
	}
	
	/**
	 * Used to have the player doesn't lost his/her bonus action by some error like cost or servants number.
	 * @param s is the player message, source of error
	 * @param exceptionText
	 * @param player
	 * @param temporalBonus is the player bonus witch may contains a bonus action
	 * @param actionDone is a boolean witch says if the player has or not do an action this turn.
	 * @param game
	 */
	static public void handleException(String s, String exceptionText, Player player, Bonus temporalBonus, boolean actionDone, LorenzoIlMagnifico game){
		String[] tmp;
		tmp = s.split(";");
		
		if(tmp[0].compareTo("BONUSTOWERACTION")==0){
			if(temporalBonus.getBonusActionTarget()=='E')
				player.send("BONUSACTIONREQUEST;"+exceptionText+";EVERY");
			else
				player.send("BONUSACTIONREQUEST;"+exceptionText+";"+tmp[3]);
		}
		else if(tmp[0].compareTo("BONUSPRODUCTIONACTION")==0)
			player.send("BONUSPRODUCTIONREQUEST;"+exceptionText+";"+player.getYellowCardList().size());
		else if(tmp[0].compareTo("BONUSHARVESTACTION")==0)
			player.send("BONUSHARVESTREQUEST;"+exceptionText);
		else if(tmp[0].compareTo("BONUSTOWERACTIONWITHCHOICE")==0){
			if(temporalBonus.getBonusActionTarget()=='E')
				player.send("BONUSACTIONREQUEST;"+exceptionText+";EVERY");
			else
				player.send("BONUSACTIONREQUEST;"+exceptionText+";"+tmp[3]);
		}
		else
			SendMenu(exceptionText, player, actionDone, game);
	}
}
