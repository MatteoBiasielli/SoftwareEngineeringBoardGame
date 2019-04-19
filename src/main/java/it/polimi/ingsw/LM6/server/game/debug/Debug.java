package it.polimi.ingsw.LM6.server.game.debug;

public class Debug {
	private static Boolean isDebugActive=false;
	private Debug(){
		//NEVER CALLED
	}
	public static void setDebug(){
		isDebugActive=true;
	}
	public static void print(String s){
		if(isDebugActive)
			System.out.println(s);
	}
}
