package it.polimi.ingsw.LM6.server.game.board;

public enum TowerCardColor {	
	GREEN(0, "green"),BLUE(1, "blue"),YELLOW(2, "yellow"),PURPLE(3, "purple");
	private int towerNumber;
	private String description;
	TowerCardColor(int i, String d){
		this.towerNumber=i;
		this.description=d;
	}
	public int getNumber(){
		return towerNumber;
	}
}
