package it.polimi.ingsw.LM6.server.game;

import java.rmi.RemoteException;
import it.polimi.ingsw.LM6.server.game.board.TowerCardColor;
import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;
import it.polimi.ingsw.LM6.server.game.exceptions.BadInputException;
import it.polimi.ingsw.LM6.server.game.familiar.Familiar;
import it.polimi.ingsw.LM6.server.game.familiar.FamiliarColor;

//this class requires also the int's array for the production card choices
public class Action {
	private String type;
	private Player p;
	private Familiar f;
	private TowerCardColor color;
	private int floorTowerMarket;
	private int servants;
	private int[] productionChoices;
	
	private ResourceSet discount;
	private int actionStrength;
	
	private boolean whichCostRelevant;
	private int whichCost;
	
	
	public Action(Player p){
		this.p=p;
	}
	
	/**
	 * @throws RemoteException 
	 * This method works as a constructor of an action object using the parameter message received from a client.
	 * @param message is the string already splitted received by clients
	 * @param p is the player who have sent the message
	 * @param r is the resourceSet (if exists) owned by the player if he has some discount on action
	 * @throws BadInputException if at least parameter of the string "message" doesn't match with the header.(the header is checked too)
	 * @throws  
	 */
	public Action(String[] message, Player p, ResourceSet r){
		this.discount=new ResourceSet(0,0,0,0,0,0,0);
		ActionBuildController controller = new ActionBuildController(this);
		int[] tmp;
		Familiar f1;
		int tmp2;
		
		this.type=message[0];
		this.p=p;
		
		if(message[0].compareTo("TOWERACTION")==0){
			controller.checkSetTowerColour(message[1]);
			tmp2 = controller.checkNumberFour(message[2]);
			this.floorTowerMarket = tmp2;
			tmp2 = controller.checkFamily(message[3]);
			f1 = this.p.getFamiliar(tmp2);
			this.f=f1;
			tmp2 = controller.checkSlaves(message[4]);
			this.servants = tmp2;		
			if(r!=null)
				this.discount = this.discount.sum(r);
		}
		
		if(message[0].compareTo("MARKETACTION")==0){
			tmp2 = controller.checkNumberFour(message[1]);
			this.floorTowerMarket = tmp2;
			tmp2 = controller.checkFamily(message[2]);
			f1 = this.p.getFamiliar(tmp2);
			this.f = f1;
			tmp2 = controller.checkSlaves(message[3]);
			this.servants = tmp2;
		}
		
		if(message[0].compareTo("HARVESTACTION")==0){
			tmp2 = controller.checkFamily(message[1]);
			f1 = this.p.getFamiliar(tmp2);
			this.f = f1;
			tmp2 = controller.checkSlaves(message[2]);
			this.servants = tmp2;
			tmp2 = controller.checkNumberTwo(message[3]);
			this.floorTowerMarket = tmp2;
		}
		
		if(message[0].compareTo("PRODUCTIONACTION")==0){
			tmp2 = controller.checkFamily(message[1]);
			f1 = this.p.getFamiliar(tmp2);
			this.f = f1;
			tmp2 = controller.checkSlaves(message[2]);
			this.servants = tmp2;
			tmp2 = controller.checkNumberTwo(message[3]);
			this.floorTowerMarket = tmp2;
			tmp = controller.checkProductionChoices(message,4);
			this.productionChoices = tmp;
			
		}
		
		if(message[0].compareTo("COUNCILACTION")==0){
			tmp2 = controller.checkFamily(message[1]);
			f1 = this.p.getFamiliar(tmp2);
			this.f = f1;
			tmp2 = controller.checkSlaves(message[2]);
			this.servants = tmp2;
		}
		
		if(message[0].compareTo("COUNCILREQUIREMENT")==0){
			tmp2 = controller.checkNumberFive(message[1]);
			this.floorTowerMarket = tmp2;
		}
		
		if(message[0].compareTo("DOUBLECOUNCILREQUIREMENT")==0){
			tmp = controller.checkDoubleCouncilBonus(message);
			this.productionChoices = tmp;
		}
		
		if(message[0].compareTo("TRIPLECOUNCILREQUIREMENT")==0){
			tmp = controller.checkTripleCouncilBonus(message);
			this.productionChoices = tmp;
		}
		
		if(message[0].compareTo("LEADERDISCARD")==0){
			tmp2 = controller.checkNumberFour(message[1]);
			this.floorTowerMarket = tmp2;
		}
		
		if(message[0].compareTo("LEADERPLAY")==0){
			tmp2 = controller.checkNumberFour(message[1]);
			this.floorTowerMarket = tmp2;
		}
				
		if(message[0].compareTo("BONUSTOWERACTION")==0){
			f1 = new Familiar(this.p, FamiliarColor.FAKE);
			this.f = f1;
			this.f.setStrength(Integer.parseInt(message[4]));
			tmp2 = controller.checkSlaves(message[1]);
			this.servants = tmp2;
			tmp2 = controller.checkNumberFour(message[2]);
			this.floorTowerMarket = tmp2;
			controller.checkSetTowerColour(message[3]);
			if(r!=null)
				this.discount = this.discount.sum(r);
		}
		
		if(message[0].compareTo("BONUSHARVESTACTION")==0){
			f1 = new Familiar(this.p, FamiliarColor.FAKE);
			this.f = f1;
			this.f.setStrength(Integer.parseInt(message[2]));
			tmp2 = controller.checkSlaves(message[1]);
			this.servants = tmp2;
			this.floorTowerMarket=1;
		
		}
		
		if(message[0].compareTo("BONUSPRODUCTIONACTION")==0){
			f1 = new Familiar(this.p, FamiliarColor.FAKE);
			this.f = f1;
			this.f.setStrength(Integer.parseInt(message[message.length-1]));
			tmp2 = controller.checkSlaves(message[1]);
			this.servants = tmp2;
			tmp = controller.checkProductionChoices(message, 2);
			this.productionChoices = tmp;
			this.floorTowerMarket=1;
		}
		
		if(message[0].compareTo("TOWERACTIONWITHCHOICE")==0){
			controller.checkSetTowerColour(message[1]);
			tmp2 = controller.checkNumberFour(message[2]);
			this.floorTowerMarket = tmp2;
			tmp2 = controller.checkFamily(message[3]);
			f1 = this.p.getFamiliar(tmp2);
			this.f=f1;
			tmp2 = controller.checkSlaves(message[4]);
			this.servants = tmp2;
			tmp2 = controller.checkNumberTwo(message[5]);
			this.whichCostRelevant= true;
			this.whichCost = tmp2;
			if(r!=null)
				this.discount = this.discount.sum(r);
		}
		
		if(message[0].compareTo("BONUSTOWERACTIONWITHCHOICE")==0){
			f1 = new Familiar(this.p, FamiliarColor.FAKE);
			f1.setStrength(Integer.parseInt(message[5]));
			this.f = f1;
			tmp2 = controller.checkSlaves(message[1]);
			this.servants = tmp2;
			tmp2 = controller.checkNumberFour(message[2]);
			this.floorTowerMarket = tmp2;
			controller.checkSetTowerColour(message[3]);
			tmp2 = controller.checkNumberTwo(message[4]);
			this.whichCostRelevant=true;
			this.whichCost = tmp2;
			if(r!=null)
				this.discount = this.discount.sum(r);
		}
		
	}

	
	public String getType(){
		return this.type;
	}
	public Player getPlayer(){
		return this.p;
	}
	public Familiar getFamiliar(){
		return this.f;
	}
	public TowerCardColor getTowerCardColor(){
		return this.color;
	}
	public int getFloorTowerMarket(){
		return this.floorTowerMarket;
	}
	public int getServants(){
		return this.servants;
	}
	public ResourceSet getDiscount(){
		return this.discount;
	}
	public int getActionStrength(){
		return this.actionStrength;
	}
	public void setStrength(int s){
		this.actionStrength=s;
	}
	public int getCost(){
		return this.whichCost;
	}
	public boolean isWhichCostRelevant(){
		return this.whichCostRelevant;
	}


	public void setServants(int i) {
		this.servants=i;		
	}
  
	public int getProductionChoice(int t){
		return this.productionChoices[t];
	}

	public int[] getProductionChoices(){
		return this.productionChoices;
	}
  
	/**
	 * this method works as an adapter between the color insert by the client and the color as a enum object
	 * @param s is the color insert by the player (already checked)
	 */
	protected void setTowerColour(String s){
		if(s.compareTo("GREEN")==0)
			this.color=TowerCardColor.GREEN;
		else if(s.compareTo("BLUE")==0)
			this.color=TowerCardColor.BLUE;
		else if(s.compareTo("YELLOW")==0)
			this.color=TowerCardColor.YELLOW;
		else this.color=TowerCardColor.PURPLE;
	}
}


