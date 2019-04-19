package it.polimi.ingsw.LM6.server.game.familiar;

public enum FamiliarColor {
	WHITE("w"),BLACK("b"),ORANGE("o"), UNCOLOURED("u"), FAKE("f");
	
	private String description;
	
	FamiliarColor(String d){
		description=d;
	}
	public String getColourString(){
		return this.description;
	}
}
