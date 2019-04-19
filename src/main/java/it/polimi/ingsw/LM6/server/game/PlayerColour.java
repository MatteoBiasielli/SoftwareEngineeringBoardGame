package it.polimi.ingsw.LM6.server.game;

public enum PlayerColour {
	YELLOW("y"), RED("r"), BLUE("b"), GREEN("g"), PURPLE("p");
	
	private String description;
	

	PlayerColour(String d){
		description=d;
	}
	public String getColourString(){
		return this.description;
	}
}
