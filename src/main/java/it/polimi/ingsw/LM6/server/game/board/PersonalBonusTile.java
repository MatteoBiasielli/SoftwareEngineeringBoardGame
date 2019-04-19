package it.polimi.ingsw.LM6.server.game.board;

import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;

public class PersonalBonusTile {
	private ResourceSet productionSet;
	private ResourceSet harvestSet;
	private int number;
	private static final String SEPARATOR="ù";
	
	public PersonalBonusTile(ResourceSet hr, ResourceSet pr, int n){
		this.harvestSet=hr;
		this.productionSet=pr;
		this.number=n;
	}
	public PersonalBonusTile(String s){
		String[] app=s.split(";");
		this.harvestSet=new ResourceSet(Integer.parseInt(app[0]),Integer.parseInt(app[1]),Integer.parseInt(app[2])
				,Integer.parseInt(app[3]),Integer.parseInt(app[4]),Integer.parseInt(app[5]),Integer.parseInt(app[6]));
		this.productionSet=new ResourceSet(Integer.parseInt(app[7]),Integer.parseInt(app[8]),Integer.parseInt(app[9])
				,Integer.parseInt(app[10]),Integer.parseInt(app[11]),Integer.parseInt(app[12]),Integer.parseInt(app[13]));
		this.number=Integer.parseInt(app[14]);
	}
	public ResourceSet getProductionSet() {
		return productionSet;
	
	}
	
	public ResourceSet getHarvestSet() {
		return harvestSet;
	
	}
	@Override
	public String toString() {
		return "PRODUCTION="+this.productionSet.toString()+"|"+"HARVEST="+this.harvestSet.toString() + SEPARATOR+number;
	
	}
	public int getNumber(){
		return this.number;
	}
}
