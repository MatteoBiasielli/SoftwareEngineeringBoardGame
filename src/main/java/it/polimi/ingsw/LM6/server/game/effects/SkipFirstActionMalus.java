package it.polimi.ingsw.LM6.server.game.effects;

import it.polimi.ingsw.LM6.server.game.Player;

public class SkipFirstActionMalus extends Effect{
	String description;
	public SkipFirstActionMalus(String s){
		this.description=s;
	}
	@Override
	public String toString(){
		return this.description;
	}
	@Override
	public void applyOn(Player p, Player[] turnOrder){
		int s=turnOrder.length;
		for(int i=0;i<s;i++){
			if(p==turnOrder[i]){
				for(int j=i;j<s-1;j++){
					turnOrder[j]=turnOrder[j+1];
				}
				turnOrder[s-1]=p;
				break;
			}
		}
	}
}