package it.polimi.ingsw.LM6;
import java.io.IOException;

import it.polimi.ingsw.LM6.server.game.LorenzoIlMagnifico;
import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;


public class App 
{
    public static void main( String[] args )
    {
    	try{
    		
    		LorenzoIlMagnifico l=LorenzoIlMagnifico.loadGame("filename");
    		System.out.println(l.showPlayers(l.getPlayerByNickname("a")));
    		l.saveGame();
    		
    	}catch(IOException e){
    		System.out.println("err");
    	}
    	
    	
    	/*LorenzoIlMagnifico prova=null;
    	try{
			prova = new LorenzoIlMagnifico(false,4);
		} catch (BadInputException e) {
			System.out.println("Impossibile creare la partita");
		}*/
    }
}
