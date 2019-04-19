package it.polimi.ingsw.LM6.server.game;

import it.polimi.ingsw.LM6.server.game.exceptions.BadInputException;

public class ActionBuildController {
	
	private static final String ERR_MESSAGE = "Invalid request to the server.";
	
	private Action a;
	
	protected ActionBuildController(Action a){
		this.a=a;
	}
	
	/**
	 * This method works as a controller of the client's input sent to server about the tower color
	 * @param s is the string witch have to match to a tower color
	 * @throws BadInputException if the input is not allowed
	 */
	protected void checkSetTowerColour(String s){
		if(s.compareTo("GREEN")==0)
			this.a.setTowerColour(s);
		else if(s.compareTo("BLUE")==0)
			this.a.setTowerColour(s);
		else if(s.compareTo("YELLOW")==0)
			this.a.setTowerColour(s);
		else if(s.compareTo("PURPLE")==0)
			this.a.setTowerColour(s);
		else throw new BadInputException(ERR_MESSAGE);
	}
	
	/**
	 * This method works as a controller of the client's input sent to server about the family color
	 * @param s is the string witch have to match to a family color
	 * @return corresponding number of the family color on the game
	 * @throws BadInputException if the input is not allowed
	 */
	protected int checkFamily(String s){
		if(s.compareTo("BLACK")==0)
			return 0;
		if(s.compareTo("ORANGE")==0)
			return 1;
		if(s.compareTo("WHITE")==0)
			return 2;
		if(s.compareTo("UNCOLOUR")==0)
			return 3;
		throw new BadInputException(ERR_MESSAGE);
	}
	
	/**
	 * This method works as a controller of the client's input sent to server about a number input
	 * @param s is the string sent by the client
	 * @return an int version of the string s
	 * @throws BadInputException if the parameter is not allowed
	 */
	protected int checkNumberFour(String s){
		if(s.compareTo("0")==0)
			return 0;
		if(s.compareTo("1")==0)
			return 1;
		if(s.compareTo("2")==0)
			return 2;
		if(s.compareTo("3")==0)
			return 3;
		throw new BadInputException(ERR_MESSAGE);
	}
	
	/**
	 * This method works as a controller of the client's input sent to server about the number of slaves
	 * @param s is a string witch have to match with a number
	 * @return the int version of the string s
	 * @throws BadInputException is the parameter is not allowed or is less than zero
	 */
	protected int checkSlaves(String s){
		
		try{
			int tmp = Integer.parseInt(s);
			if(tmp>=0)
				return tmp;
			throw new BadInputException(ERR_MESSAGE);
		}
		catch(NumberFormatException e){
			throw new BadInputException(ERR_MESSAGE);
		}
	}
	
	/**
	 * This method works as a controller of the client's input sent to server about a number input
	 * @param s is the string witch have to match to a number
	 * @return the int version of the string s
	 * @throws BadInputException if the string isn't allowed
	 */
	protected int checkNumberTwo(String s){
		if(s.compareTo("1")==0)
			return 1;
		if(s.compareTo("2")==0)
			return 2;
		throw new BadInputException(ERR_MESSAGE);
	}
	
	/**
	 * This method works as a controller of the client's input sent to server about the production choices insert by the clients
	 * @param s is the string witch have received by the server already splitted
	 * @param x is the starting point in the array of the production choices
	 * @return an int[] witch contain the corresponding int value of the choices
	 * @throws BadInputException if at least on choice is not allowed or is less than zero
	 */
	protected int[] checkProductionChoices(String[] s, int x){
		int i;
		int[] output = new int[6];
		for(i=x;i<(x+6);i++){
			try {
				output[i-x]=Integer.parseInt(s[i]);
				if(output[i-x]<0)
					throw new BadInputException(ERR_MESSAGE);				
			}
			catch (NumberFormatException e){
				throw new BadInputException(ERR_MESSAGE);
			}
		}
		return output;
	}
	
	/**
	 * This method works as a controller of the client's input sent to server about a number input
	 * @param s is the string witch have to match to a number
	 * @return the corresponding int of a string s
	 * @throws BadInputException if the string s is not allowed
	 */
	protected int checkNumberFive(String s){
		if(s.compareTo("1")==0)
			return 1;
		else if(s.compareTo("2")==0)
			return 2;
		else if(s.compareTo("3")==0)
			return 3;
		else if(s.compareTo("4")==0)
			return 4;
		else if(s.compareTo("5")==0)
			return 5;
		throw new BadInputException(ERR_MESSAGE);
	}
	
	/**
	 * This method works as a controller of the client's input sent to server about the double council bonus choices
	 * @param s is the string received by the server already splitted
	 * @return an int[] witch contains the two choices
	 * @throws BadInputException if at least one choices is not allowed
	 */
	protected int[] checkDoubleCouncilBonus(String[] s){
		int a;
		int b;
		a = this.checkNumberFive(s[1]);
		b = this.checkNumberFive(s[2]);
		if(a==b)
			throw new BadInputException(ERR_MESSAGE);
		int[] output = new int[2];
		output[0]=a;
		output[1]=b;
		return output;
	}
	
	/**
	 * This method works as a controller of the client's input sent to server about the triple council bonus choices
	 * @param s is the string received by the server already splitted
	 * @return an int[] witch contains the three player's choice
	 * @throws BadInputException if at least one choice is not allowed
	 */
	protected int[] checkTripleCouncilBonus(String[] s){
		int a;
		int b;
		int c;
		a = this.checkNumberFive(s[1]);
		b = this.checkNumberFive(s[2]);
		c = this.checkNumberFive(s[3]);
		if(a==b|| b==c || a==c)
			throw new BadInputException(ERR_MESSAGE);
		int[] output = new int[3];
		output[0]=a;
		output[1]=b;
		output[2]=c;
		return output;
	}
}
