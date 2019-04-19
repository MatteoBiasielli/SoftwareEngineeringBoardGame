package it.polimi.ingsw.LM6.client;

public class ActionAllowed {
	
	private ActionHeader act;
	private String opionalInfo;
	
	/**
	 *
	 * @return the string composed by the header taken by the actionHeader and the optionalInfo
	 */
	public String buildMessage(){
		return this.act.getHeader()+";"+this.opionalInfo;
	}
	
	public void setSetupMenu(){
		this.act=ActionHeader.SETUPMENU;
	}
	
	public void setInvalidLogin(){
		this.act=ActionHeader.INVALIDLOGIN;
	}
	
	public void setScoreBoard(){
		this.act=ActionHeader.SCOREBOARD;
	}
	
	public void setInfo(){
		this.act = ActionHeader.INFO;
	}
	
	public void setMenu(){
		this.act = ActionHeader.MENU;
	}
	
	public void setOptionalInfo(String m){
		this.opionalInfo=m;
	}
	
	public void setBoard(){
		this.act = ActionHeader.BOARD;
	}
	
	public void setUpdateBoard(){
		this.act = ActionHeader.UPDATEBOARD;
	}
	
	public void setPlayers(){
		this.act = ActionHeader.PLAYERS;
	}
	
	public void setUpdatePlayers(){
		this.act = ActionHeader.PLAYERSUPDATE;
	}
	
	public void setBonusActionRequest(){
		this.act = ActionHeader.BONUSACTIONREQUEST;
	}
	
	public void setBonusProductionRequest(){
		this.act = ActionHeader.BONUSPRODUCTIONREQUEST;
	}
	
	public void setBonusHarvestRequest(){
		this.act = ActionHeader.BONUSHARVESTREQUEST;
	}
	
	public void setBonusCouncilRequest(){
		this.act = ActionHeader.BONUSCOUNCILREQUEST;
	}
	
	public void setCostChoiceRequest(){
		this.act = ActionHeader.COSTCHOICEREQUEST;
	}
	
	public void setExcommChoiceRequest(){
		this.act = ActionHeader.EXCOMMCHOICEREQUEST;
	}
	
	public void setLeaderDraft(){
		this.act = ActionHeader.LEADERDRAFT;
	}
	
	public void setTilesDraft(){
		this.act = ActionHeader.TILESDRAFT;
	}
	
	public void setLorenzoIlMagnifico(){
		this.act = ActionHeader.LORENZOILMAGNIFICO;
	}
	
	public void setFamiliarOffer(){
		this.act = ActionHeader.FAMOFFERREQUEST;
	}
	
	public void setEndGame(){
		this.act = ActionHeader.ENDGAME;
	}
}
