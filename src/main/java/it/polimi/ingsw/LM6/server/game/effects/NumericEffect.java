package it.polimi.ingsw.LM6.server.game.effects;

import it.polimi.ingsw.LM6.server.game.Action;
import it.polimi.ingsw.LM6.server.game.board.TowerCardColor;
import it.polimi.ingsw.LM6.server.game.familiar.FamiliarColor;

public class NumericEffect extends Effect{
	private int harvestBonus;
	private int productionBonus;
	private int purpleActionBonus;
	private int yellowActionBonus;
	private int greenActionBonus;
	private int blueActionBonus;
	private int bonusWhiteFamiliar;
	private int bonusBlackFamiliar;
	private int bonusOrangeFamiliar;
	private int bonusUncolouredFamiliar;
	
	public NumericEffect(int harvestBonus, int productionBonus ,int purpleActionBonus ,int yellowActionBonus,
				int greenActionBonus, int blueActionBonus,int bonusWhiteFamiliar,int bonusBlackFamiliar,
				int bonusOrangeFamiliar,int bonusUncolouredFamiliar){
		this.harvestBonus=harvestBonus;
		this.productionBonus=productionBonus;
		this.purpleActionBonus=purpleActionBonus;
		this.yellowActionBonus=yellowActionBonus;
		this.greenActionBonus=greenActionBonus;
		this.blueActionBonus=blueActionBonus;
		this.bonusWhiteFamiliar=bonusWhiteFamiliar;
		this.bonusBlackFamiliar=bonusBlackFamiliar;
		this.bonusOrangeFamiliar=bonusOrangeFamiliar;
		this.bonusUncolouredFamiliar=bonusUncolouredFamiliar;
	}
	@Override
	public String toString(){
		String app="";
		if(this.harvestBonus!=0)
			app+="HB=" + Integer.toString(this.harvestBonus)+" ";
		if(this.productionBonus!=0)
			app+="PB=" + Integer.toString(this.productionBonus)+" ";
		if(this.purpleActionBonus!=0)
			app+="PAB=" + Integer.toString(this.purpleActionBonus)+" ";
		if(this.yellowActionBonus!=0)
			app+="YAB=" + Integer.toString(this.yellowActionBonus)+" ";
		if(this.greenActionBonus!=0)
			app+="GAB=" + Integer.toString(this.greenActionBonus)+" ";
		if(this.blueActionBonus!=0)
			app+="BAB=" + Integer.toString(this.blueActionBonus)+" ";
		if(this.bonusWhiteFamiliar!=0)
			app+="WFB=" + Integer.toString(this.bonusWhiteFamiliar)+" ";
		if(this.bonusBlackFamiliar!=0)
			app+="BFB=" + Integer.toString(this.bonusBlackFamiliar)+" ";
		if(this.bonusOrangeFamiliar!=0)
			app+="BOF=" + Integer.toString(this.bonusOrangeFamiliar)+" ";
		if(this.bonusUncolouredFamiliar!=0)
			app+="BUF=" + Integer.toString(this.bonusUncolouredFamiliar)+" ";
		return app;
	}
	@Override
	public void applyOn(Action action){
		if(("HARVESTACTION").equals(action.getType()) || ("BONUSHARVESTACTION").equals(action.getType()))
			action.setStrength(action.getActionStrength()+this.harvestBonus);
		else if(("PRODUCTIONACTION").equals(action.getType()) || ("BONUSPRODUCTIONACTION").equals(action.getType()))
			action.setStrength(action.getActionStrength()+this.productionBonus);
		else if(action.getTowerCardColor()!=null && action.getTowerCardColor()==TowerCardColor.BLUE)
			action.setStrength(action.getActionStrength()+this.blueActionBonus);
		else if(action.getTowerCardColor()!=null && action.getTowerCardColor()==TowerCardColor.GREEN)
			action.setStrength(action.getActionStrength()+this.greenActionBonus);
		else if(action.getTowerCardColor()!=null && action.getTowerCardColor()==TowerCardColor.YELLOW)
			action.setStrength(action.getActionStrength()+this.yellowActionBonus);
		else if(action.getTowerCardColor()!=null && action.getTowerCardColor()==TowerCardColor.PURPLE)
			action.setStrength(action.getActionStrength()+this.purpleActionBonus);
		if(action.getFamiliar().getColour()==FamiliarColor.BLACK)
			action.setStrength(action.getActionStrength()+this.bonusBlackFamiliar);
		else if(action.getFamiliar().getColour()==FamiliarColor.ORANGE)
			action.setStrength(action.getActionStrength()+this.bonusOrangeFamiliar);
		else if(action.getFamiliar().getColour()==FamiliarColor.WHITE)
			action.setStrength(action.getActionStrength()+this.bonusWhiteFamiliar);
		else if(action.getFamiliar().getColour()==FamiliarColor.UNCOLOURED)
			action.setStrength(action.getActionStrength()+this.bonusUncolouredFamiliar);
	}
}
