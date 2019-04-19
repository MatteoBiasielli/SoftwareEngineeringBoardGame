package it.polimi.ingsw.LM6.server.game.bonuses;

import it.polimi.ingsw.LM6.server.game.exceptions.BadInputException;

public class ResourceSet {
	private int wood;
	private int stone;
	private int coin;
	private int servants;
	private int militaryPoints;
	private int victoryPoints;
	private int faithPoints;
	
	public ResourceSet(){
		this.setZero();
	}
	public ResourceSet(int wood, int stone, int coin, int servants, int mp, int vp,int faith){
		if(wood<0 || stone<0 || coin<0)
			throw new BadInputException("Negative numbers are not accepted");
		if( servants<0 || mp<0 || vp<0 || faith<0)
			throw new BadInputException("Negative numbers are not accepted");
		this.wood=wood;
		this.stone=stone;
		this.coin=coin;
		this.servants=servants;
		this.victoryPoints=vp;
		this.militaryPoints=mp;
		this.faithPoints=faith;
	}


	public ResourceSet(ResourceSet cost) {
		if(cost==null){
			this.setZero();
		}
		else
		{
			this.wood=cost.wood;
			this.stone=cost.stone;
			this.coin=cost.coin;
			this.servants=cost.servants;
			this.victoryPoints=cost.victoryPoints;
			this.militaryPoints=cost.militaryPoints;
			this.faithPoints=cost.faithPoints;
		}
	}


	/**sums two resource sets
	 * @param rs - the resource set that ahs to be summed up with the caller object
	 * @return a new resourceSet, containing the sum of the resources
	 */
	public ResourceSet sum( ResourceSet rs) {
		return new ResourceSet(this.wood+rs.wood, this.stone+rs.stone, this.coin+rs.coin,this.servants+rs.servants,
					this.militaryPoints+rs.militaryPoints,this.victoryPoints+rs.victoryPoints,this.faithPoints+ rs.faithPoints);
	}
	
	
	/**subtracts two resource sets. If there are <0 values after the operation, they're set =0 
	 * @param rs - the resource set that ahs to be subtracted to the caller object
	 * @return a new resourceSet, containing the difference of the resources
	 */
	public ResourceSet sub( ResourceSet rs) {
		int w=0;
		int s=0;
		int c=0;
		int se=0;
		int mp=0;
		int vp=0;
		int fp=0;
		if(this.wood-rs.wood>0)
			w=this.wood-rs.wood;
		if(this.stone-rs.stone>0)
			s=this.stone-rs.stone;
		if(this.coin-rs.coin>0)
			c=this.coin-rs.coin;
		if(this.servants-rs.servants>0)
			se=this.servants-rs.servants;
		if(this.victoryPoints-rs.victoryPoints>0)
			vp=this.victoryPoints-rs.victoryPoints;
		if(this.militaryPoints-rs.militaryPoints>0)
			mp=this.militaryPoints-rs.militaryPoints;
		if(this.faithPoints-rs.faithPoints>0)
			fp=this.faithPoints-rs.faithPoints;
		return new ResourceSet(w,s,c,se,mp,vp,fp);
	}
	
	/**sums two resource sets. The result goes in the caller object
	 * @param rs - the resource set that ahs to be summed up with the caller object
	 */
	public void internalSum(ResourceSet rs){
		this.wood+=rs.wood;
		this.stone+=rs.stone;
		this.coin+=rs.coin;
		this.servants+=rs.servants;
		this.victoryPoints+=rs.victoryPoints;
		this.militaryPoints+=rs.militaryPoints;
		this.faithPoints+=rs.faithPoints;
	}
	
	/**subtracts two resource sets. The result goes in the caller object
	 * @param rs - the resource set that ahs to be subtracted to with the caller object
	 */
	public void internalSub(ResourceSet rs){
		if(this.wood-rs.wood>0)
			this.wood-=rs.wood;
		else 
			this.wood=0;
		
		if(this.stone-rs.stone>0)
			this.stone-=rs.stone;
		else 
			this.stone=0;
		
		if(this.coin-rs.coin>0)
			this.coin-=rs.coin;
		else 
			this.coin=0;
		
		if(this.servants-rs.servants>0)
			this.servants-=rs.servants;
		else 
			this.servants=0;
		
		if(this.victoryPoints-rs.victoryPoints>0)
			this.victoryPoints-=rs.victoryPoints;
		else 
			this.victoryPoints=0;
		
		if(this.militaryPoints-rs.militaryPoints>0)
			this.militaryPoints-=rs.militaryPoints;
		else 
			this.militaryPoints=0;
		
		if(this.faithPoints-rs.faithPoints>0)
			this.faithPoints-=rs.faithPoints;
		else 
			this.faithPoints=0;
	}
	
	/**multiplies the Resource set values with given number m
	 * @param m - an integer value that will multiply the ResourceSet
	 * @throws BadInputException if m is <0
	 * @return a new ResourceSet, representing the initial ResourceSet*m
	 */
	public ResourceSet multiply( int m){
		if(m<0)
			throw new BadInputException("Negative inputs not accepted");
		return new ResourceSet(this.wood*m,this.stone*m,this.coin*m,this.servants*m, this.militaryPoints*m, this.victoryPoints*m, this.faithPoints*m);
	}
	
	/** sets all the values to zero
	 * 
	 */
	public void setZero(){
		this.wood=0;
		this.stone=0;
		this.servants=0;
		this.coin=0;
		this.faithPoints=0;
		this.militaryPoints=0;
		this.victoryPoints=0;
	}
	
	/**@return true if all the caller object's values are greater than the values of the one given as a parameter,
	 * false otherwise
	 */
	public Boolean contains( ResourceSet rs) {
		return this.wood>=rs.wood && this.stone>=rs.stone && this.coin>=rs.coin && this.servants>=rs.servants
				&& this.militaryPoints>=rs.militaryPoints && this.victoryPoints>=rs.victoryPoints && this.faithPoints>=rs.faithPoints;
	}
	
	/**@return true if the ResourceSet given as parameter contains exactly the same resources of the caller object
	 * @param rs - a resource set
	 */
	public Boolean equals(ResourceSet rs){
		if(rs==null)
			return false;
		if(this.wood!=rs.wood)
			return false;
		if(this.stone!=rs.stone)
			return false;
		if(this.coin!=rs.coin)
			return false;
		if(this.servants!=rs.servants)
			return false;
		if(this.militaryPoints!=rs.militaryPoints)
			return false;
		if(this.victoryPoints!=rs.victoryPoints)
			return false;
		if(this.faithPoints!=rs.faithPoints)
			return false;
		return true;
	}
	
	/**@return a string version of the resource set
	 * 
	 */
	@Override
	public String toString() {
		String result = "";
		if(this.wood>0)
			result+= Integer.toString(this.wood) + "W ";
		if(this.stone>0)
			result+= Integer.toString(this.stone) + "St ";
		if(this.coin>0)
			result+= Integer.toString(this.coin) + "C ";
		if(this.servants>0)
			result+= Integer.toString(this.servants) + "Se ";
		if(this.victoryPoints>0)
			result+= Integer.toString(this.victoryPoints)+ "VP ";
		if(this.militaryPoints>0)
			result+= Integer.toString(this.militaryPoints) + "MP ";
		if(this.faithPoints>0)
			result+= Integer.toString(this.faithPoints) + "FP ";
		if(result!="")
			return result;
		else
			return " ";
	}
	
	public int getTotalMaterialResources(){
		return this.coin + this.servants + this.stone + this.wood;
	}
	
	public int getFaithPoints(){
		return this.faithPoints;
	}
	public int getVictoryPoints(){
		return this.victoryPoints;
	}
	public int getWood(){
		return this.wood;
	}
	public int getStone(){
		return this.stone;
	}

	public int getMilitaryPoints() {
		return this.militaryPoints;
	}

	public int getCoin(){
		return this.coin;
	}
	public int getServants() {
		return this.servants;
	}
	
	public void resetFaithPoints(){
		this.faithPoints = 0;
	}
	public boolean isEmpty(){
			return this.equals(new ResourceSet(0,0,0,0,0,0,0));
	}
	
	
	/** parses a string to ResourceSet, if possible
	 * 
	 * @param s - a string representing the resource set
	 * @return a resourceSet containing the values contained in the string
	 * @author matteo
	 */
	public static ResourceSet parseResourceSet(String s){
		if(s==null || (" ").equals(s))
			return new ResourceSet(0,0,0,0,0,0,0);
		int w=0;
		int st=0;
		int c=0;
		int se=0;
		int mp=0;
		int vp=0;
		int fp=0;
		String[] app=s.split(" ");
		for(int i=0;i<app.length;i++){
			int value=0;
			int j;
			for(j=0;j<app[i].length();j++){
				if(app[i].charAt(j)>='0' && app[i].charAt(j)<='9')
					value=value*10+Integer.parseInt(Character.toString(app[i].charAt(j)));
				else
					break;
			}
			if(app[i].charAt(j)=='W')
				w=value;
			else if(app[i].charAt(j)=='C')
				c=value;
			else if(app[i].charAt(j)=='S' && app[i].charAt(j+1)=='t')
				st=value;
			else if(app[i].charAt(j)=='S' && app[i].charAt(j+1)=='e')
				se=value;
			else if(app[i].charAt(j)=='M')
				mp=value;
			else if(app[i].charAt(j)=='V')
				vp=value;
			else if(app[i].charAt(j)=='F')
				fp=value;
			
		}
		
		
		return new ResourceSet(w,st,c,se,mp,vp,fp);
	}
}
