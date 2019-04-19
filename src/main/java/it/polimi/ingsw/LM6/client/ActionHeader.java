package it.polimi.ingsw.LM6.client;

public enum ActionHeader {
	SETUPMENU("SETUPMENU"), INVALIDLOGIN("INVALIDLOGIN"), SCOREBOARD("SCOREBOARD"), MENU("MENU"), INFO("INFO"),
	BOARD("BOARD"), UPDATEBOARD("UPDATEBOARD"), PLAYERS("PLAYERS"), PLAYERSUPDATE("UPDATEPLAYERS"),
	BONUSACTIONREQUEST("BONUSACTIONREQUEST"), BONUSPRODUCTIONREQUEST("BONUSPRODUCTIONREQUEST"), 
	BONUSHARVESTREQUEST("BONUSHARVESTREQUEST"), BONUSCOUNCILREQUEST("BONUSCOUNCILREQUEST"), COSTCHOICEREQUEST("COSTCHOICEREQUEST"),
	EXCOMMCHOICEREQUEST("EXCOMMCHOICEREQUEST"), LEADERDRAFT("LEADERDRAFT"), TILESDRAFT("TILESDRAFT"), LORENZOILMAGNIFICO("LORENZOILMAGNIFICO"),
	FAMOFFERREQUEST("FAMOFFERREQUEST"), ENDGAME("ENDGAME");
	
	private String description;
	
	ActionHeader(String d){
		this.description=d;
	}
	
	public String getHeader(){
		return this.description;
	}
	
	
}
