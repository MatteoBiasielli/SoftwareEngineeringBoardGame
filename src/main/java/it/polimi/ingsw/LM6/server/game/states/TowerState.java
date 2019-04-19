package it.polimi.ingsw.LM6.server.game.states;

import java.util.ArrayList;

import it.polimi.ingsw.LM6.server.game.board.TowerCardColor;
import it.polimi.ingsw.LM6.server.game.board.spaces.TowerSpace;
import it.polimi.ingsw.LM6.server.game.card.Card;
import it.polimi.ingsw.LM6.server.game.debug.Debug;

public class TowerState {
	private TowerSpace[] spaces;
	private TowerCardColor color;
	private Card[] cards;
	private static final int NUMBER_OF_CARDS=24;
	private static final int NUMBER_OF_SPACES=4;
	
	public TowerState(ArrayList<Card> cards, ArrayList<TowerSpace> spaces, TowerCardColor col){
		this.color=col;
		this.cards=new Card[NUMBER_OF_CARDS];
		for(int i=0;i<NUMBER_OF_CARDS;i++){
			this.cards[i]=cards.get(i);
		}
		this.spaces=new TowerSpace[NUMBER_OF_SPACES];
		for(int i=0;i<NUMBER_OF_SPACES;i++){
			this.spaces[NUMBER_OF_SPACES-1-i]=spaces.get(i);
		}
	}
	
	/**shuffles the cards in the Tower's deck, keeping them ordered by era (first 8 cards= 1st ERA, second 8 cards =2nd ERA, third 8 cards=3rd ERA)
	 * 
	 */
	public void shuffleCards(){
		shuffleCardsSet(0);
		shuffleCardsSet(1);
		shuffleCardsSet(2);
	}
	
	/** shuffles a set of cards (1-8, 9-16, 17-24)
	 * 
	 * @param set - the set that had to be shuffled
	 */
	private void shuffleCardsSet(int set){
		Boolean[] app=new Boolean[8];
		Card[] card=new Card[8];	
		for(int i=0;i<8;i++)
			app[i]=false;
		for(int i=0;i<8;i++){
			int rand;
			do{
				rand=(int) (Math.random()*8.0);
				if(rand==8)
					rand=7;
			}while(app[rand]);
			card[rand]=cards[i+8*set];
			app[rand]=true;
		}
		for(int i=0;i<8;i++)
			cards[i+8*set]=card[i];
	}
	
	/**prints all the cards only if the Debug mode is set
	 * 
	 */
	public void printCards(){
		for(int i=0;i<NUMBER_OF_CARDS;i++){
			Debug.print(cards[i].toString());
		}
	}
	
	/**@return a string version of the TowerState
	 * 
	 */
	@Override
	public String toString(){
		String app="";
		for(int i=0;i<NUMBER_OF_SPACES;i++){
			app+=spaces[i].toString();
			if(i!=NUMBER_OF_SPACES-1)
				app+="!";
		}
		return app;
	}
	
	/**prepares the TowerState for a new turn, cleaning all the spaces and associating them new cards
	 * @param i - the starting turn number
	 */
	public void newTurn(int i) {
		for(int j=0;j<NUMBER_OF_SPACES;j++)
			spaces[j].newTurn();
		for(int j=0;j<NUMBER_OF_SPACES;j++)
			spaces[j].associateNewCard(cards[(i-1)*4+j]);		
	}
	
	
	public TowerSpace[] getSpaces(){
		return this.spaces;
	}
	public TowerSpace getSpace(int i){
		return spaces[i];
	}
	public TowerCardColor getColor(){
		return this.color;
	}
	public Card[] getCard(){
		return this.cards;
	}
}
