package it.polimi.ingsw.LM6.server.game.bonuses;

import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.game.exceptions.BadInputException;

public class Bonus {
	private ResourceSet resources;
	private Boolean isVariable;
	private char forNumberOf;
	
	private Boolean hasBonusAction;
	private char bonusActionIsOn;
	private int bonusActionStrength;
	private ResourceSet bonusActionDiscount;
	
	private Boolean hasBonusProduction;
	private Boolean hasBonusHarvest;
	private int bonusPHStrength;
	
	private int councilBonusNumber;
	private Boolean different;
	
	public Bonus(){
			this.setZero();
	}
	public Bonus(ResourceSet r){
		this.setZero();
		this.setResourceSet(r);
	}
	public Bonus(ResourceSet res, boolean varFlag, char varOn, boolean bAction,char bActOn, int bActStr,ResourceSet disc,
				boolean bProd,boolean bHarv, int bPHStr, int councilB,boolean diff){
		if(varFlag && varOn!='G' && varOn!='Y'&& varOn!='B'&& varOn!='P'&& varOn!='M' || 
				bAction&&(bActOn!='G'&& bActOn!='Y'&& bActOn!='B'&& bActOn!='P'&& bActOn!='E' || bActStr<0)
				 || (bProd || bHarv)&& bPHStr<0 || councilB<0)
			throw new BadInputException("Bad Input");
		this.resources=res;
		this.isVariable=varFlag;
		this.forNumberOf=varOn;
		this.hasBonusAction=bAction;
		this.bonusActionIsOn=bActOn;
		this.bonusActionStrength=bActStr;
		this.bonusActionDiscount=disc;
		this.hasBonusProduction=bProd;
		this.hasBonusHarvest=bHarv;
		this.councilBonusNumber=councilB;
		this.different=diff;
		this.bonusPHStrength=bPHStr;
	}
	public Bonus(int n){
		this.setZero();
		this.setCouncilBonuses(n);
	}
	public Bonus(Bonus b) {
		this.resources=new ResourceSet(b.resources);
		this.isVariable=b.isVariable;
		this.forNumberOf=b.forNumberOf;
		this.hasBonusAction=b.hasBonusAction;
		this.bonusActionIsOn=b.bonusActionIsOn;
		this.bonusActionStrength=b.bonusActionStrength;
		this.bonusActionDiscount=new ResourceSet(b.bonusActionDiscount);
		this.hasBonusProduction=b.hasBonusProduction;
		this.hasBonusHarvest=b.hasBonusHarvest;
		this.bonusPHStrength=b.bonusPHStrength;
		this.councilBonusNumber=b.councilBonusNumber;
		this.different=b.different;
	}

	
	/**sums two bonuses
	 * sums the resource sets and merges other values, assuming that they never get in conflict
	 * @param b - the bonus that is goign to be added to this one
	 * @param p - the player that owns the b bonus
	 * @return a new bonus, repesenting the "sum" of the two bonuses
	 */
	public Bonus sum( Bonus b, Player p) {
		Bonus ris;
		ResourceSet res=this.getResourceSetFor(p).sum(b.getResourceSetFor(p));
		char bActOn='N';
		if(this.bonusActionIsOn!='N')
			bActOn=this.bonusActionIsOn;
		if(b.getBonusActionTarget()!='N')
			bActOn=b.getBonusActionTarget();
		ResourceSet disc = new ResourceSet(0,0,0,0,0,0,0);
		if(this.bonusActionDiscount==null)
			disc=b.getBonusActionDiscount();
		else if(b.getBonusActionDiscount()==null)
			disc=this.bonusActionDiscount;
		else if(this.bonusActionDiscount.contains(b.getBonusActionDiscount()))
			disc=this.bonusActionDiscount;
		else if(b.getBonusActionDiscount().contains(this.bonusActionDiscount))
			disc=b.getBonusActionDiscount();
		boolean risHasBAction=this.hasBonusAction || b.hasBonusAction();
		ris=new Bonus(res,false, 'N',  risHasBAction ,bActOn,
					Math.max(this.bonusActionStrength, b.getBonusActionStrength()), disc,
					b.hasBonusProduction()||this.hasBonusProduction, b.hasBonusHarvest()||this.hasBonusHarvest,
					Math.max(this.bonusPHStrength, b.getBonusPHStrength()),
					this.councilBonusNumber+b.getCouncilBonusesNumber(), this.different||b.mustBeDifferent());
		return ris;
	}
	
	/**@return the resource set that the player p has to receive from this bonus.
	 * Basically it returns the resource set contained in the bonus if it's not variable, and calculates the real ResourceSet if it's variable
	 *@param p - the player that needs to receive the resources 
	 */
	public ResourceSet getResourceSetFor(Player p){
		if(!this.isVariable)
			return this.resources;
		ResourceSet ris=null;
		if(this.forNumberOf=='G')
			ris= this.resources.multiply(p.getGreenCardList().size());
		else if(this.forNumberOf=='Y')
			ris= this.resources.multiply(p.getYellowCardList().size());
		else if(this.forNumberOf=='B')
			ris= this.resources.multiply(p.getBlueCardList().size());
		else if(this.forNumberOf=='P')
			ris= this.resources.multiply(p.getPurpleCardList().size());
		else if(this.forNumberOf=='M')
			ris= this.resources.multiply((int)(p.getResourceSet().getMilitaryPoints()/2));
		return ris;
	}
	
	/** sets the bonus' resource set to a specified ResourceSet
	 * 
	 * @param rs - the new ResourceSet
	 */
	
	public void setResourceSet(ResourceSet rs){
		this.resources=rs;
		this.isVariable=false;
		this.forNumberOf='N';
	}
	
	/** @return a string version fot he bonus
	 * 
	 */
	@Override
	public String toString(){
		String app=this.resources.toString();
		if(this.isVariable)
			app="("+app+")x"+ Character.toString(this.forNumberOf);
		if(this.hasBonusAction){
			app=app+"  Action on "+ Character.toString(this.bonusActionIsOn)+":"+ Integer.toString(this.bonusActionStrength);
			if(this.bonusActionDiscount!=null){
				app+="  Disc="+this.bonusActionDiscount.toString();
			}
		}
		if(this.hasBonusProduction)
			app=app+ "  BonusProd:"+Integer.toString(this.bonusPHStrength);
		if(this.hasBonusHarvest)
			app=app+ "  BonusHarv:"+Integer.toString(this.bonusPHStrength);
		if(this.councilBonusNumber>0){
			app=app+"  CB="+Integer.toString(this.councilBonusNumber);
			if(this.different)
				app=app+" different";
		}
		return app;
	}
	
	
	/**sets all the values to 0,false or insignificant
	 * 
	 */
	public void setZero(){
		this.resources=new ResourceSet(0,0,0,0,0,0,0);
		this.isVariable=false;
		this.forNumberOf='N';
		this.hasBonusAction=false;
		this.bonusActionIsOn='N';
		this.bonusActionStrength=0;
		this.bonusActionDiscount=new ResourceSet(0,0,0,0,0,0,0);
		this.hasBonusProduction=false;
		this.hasBonusHarvest=false;
		this.bonusPHStrength=0;
		this.councilBonusNumber=0;
		this.different=false;
	}
	
	/**sets the number of council bonuses to n
	 * 
	 * @param n - the new number of council bonuses
	 */
	private void setCouncilBonuses(int n){
		this.councilBonusNumber=n;
		this.different=false;
	}
	
	public Boolean hasBonusAction(){
		return hasBonusAction;
	}
	public char getBonusActionTarget(){
		return bonusActionIsOn;
	}
	public int getBonusActionStrength(){
		return bonusActionStrength;
	}
	public ResourceSet getBonusActionDiscount(){
		return bonusActionDiscount;
	}	
	public Boolean hasBonusProduction(){
		return hasBonusProduction;
	}
	public Boolean hasBonusHarvest(){
		return hasBonusHarvest;
	}
	public int getBonusPHStrength(){
		return bonusPHStrength;
	}
	public int getCouncilBonusesNumber(){
		return councilBonusNumber;
	}
	public Boolean mustBeDifferent(){
		return different;
	}
	
	/**
	 * This method attempt to remove a bonus action (tower).
	 * @throws BadInputException if the bonus can't be removed.
	 */
	public void removeBonusAction(){
		if(!this.hasBonusAction)
			throw new BadInputException("No bonus action to remove.");
		this.hasBonusAction=false;
	}
	/**
	 *  This method attempt to remove a bonus production.
	 * @throws BadInputException if the bonus can't be removed.
	 */
	public void removeBonusProduction()  {
		if(!this.hasBonusProduction)
			throw new BadInputException("No bonus production to remove.");
		this.hasBonusProduction=false;
	}
	/**
	 *  This method attempt to remove a bonus harvest.
	 * @throws BadInputException if the bonus can't be removed
	 */
	public void removeBonusHarvest() {
		if(!this.hasBonusHarvest)
			throw new BadInputException("No bonus harvest to remove.");
		this.hasBonusHarvest=false;
	}
	/**
	 *  This method attempt to remove a single council bonus
	 * @throws BadInputException if the bonus can't be removed
	 */
	public void removeCouncilBonus(){
		if(this.getCouncilBonusesNumber()==0 || (this.getCouncilBonusesNumber()==2 && this.mustBeDifferent()) ||(this.getCouncilBonusesNumber()==3 && this.mustBeDifferent()))
			throw new BadInputException("No councile privilege to renounce to.");
		this.councilBonusNumber--;
	}
	/**
	 * This method attempt to remove a double council bonus
	 * @throws BadInputException if the bonus can't be removed
	 */
	public void removeDoubleCouncilBonus(){
		if(this.getCouncilBonusesNumber()<=1 || !this.mustBeDifferent())
			throw new BadInputException("No double councile privilege to renounce to.");
		this.councilBonusNumber=this.councilBonusNumber-2;
		this.different=false;
	}
	/**
	 * This method attempt to remove a triple council bonus
	 * @throws BadInputException if the bonus can't be removed
	 */
	public void removeTripleCouncilBonus(){
		if(this.getCouncilBonusesNumber()<=2 || !this.mustBeDifferent())
			throw new BadInputException("No triple councile privilege to renounce to.");
		this.councilBonusNumber=this.councilBonusNumber-3;
		this.different=false;
	}
	
	public ResourceSet getbonusActionDiscount(){
		return this.bonusActionDiscount;
	}
	public boolean isEmpty() {
		if(!this.resources.equals(new ResourceSet(0,0,0,0,0,0,0)) || this.hasBonusAction || this.hasBonusHarvest
				||this.hasBonusProduction)
			return false;
		if(this.councilBonusNumber>0)
			return false;
		return true;
	}
	public boolean hasEmptyResourceSet(){
		return this.resources.isEmpty();
	}
}
