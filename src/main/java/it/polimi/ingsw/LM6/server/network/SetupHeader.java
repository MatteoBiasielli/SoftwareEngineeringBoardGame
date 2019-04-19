package it.polimi.ingsw.LM6.server.network;

public enum SetupHeader {
	LOGINREQUEST("LOGINREQUEST"), LOGOUTREQUEST("LOGOUTREQUEST"), SETUPMENU("SETUPMENU"), SHOWSCOREBOARD("SHOWSCOREBOARD"), SHOWRECORD("SHOWRECORD"), STARTNEWGAME("STARTNEWGAME"), INVALIDLOGIN("INVALIDLOGIN");
	private String description;
	
	private SetupHeader(String description){
		this.description = description;
	}
	
	public String getDescription(){
		return description;
	}
}