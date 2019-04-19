package it.polimi.ingsw.LM6.server.game.bonuses;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.LM6.server.game.Action;
import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.game.effects.BonusHarvest;
import it.polimi.ingsw.LM6.server.game.effects.BonusProduction;
import it.polimi.ingsw.LM6.server.game.effects.Effect;
import it.polimi.ingsw.LM6.server.game.effects.EveryTurnBonus;
import it.polimi.ingsw.LM6.server.game.exceptions.AlreadyUsedBonusException;
import it.polimi.ingsw.LM6.server.game.exceptions.CanGoInOccupiedSpacesException;
import it.polimi.ingsw.LM6.server.game.exceptions.CannotGoOnMarketException;
import it.polimi.ingsw.LM6.server.game.exceptions.FullGameException;
import it.polimi.ingsw.LM6.server.game.exceptions.NoVPException;
import it.polimi.ingsw.LM6.server.game.states.GameState;
import it.polimi.ingsw.LM6.server.users.SocketUser;

public class TestPermanentBonusMalus {
	PermanentBonusMalus numeric=new PermanentBonusMalus(1,1,1,1,1,1,1,1,1,1,"none");
	PermanentBonusMalus numericAndOther=new PermanentBonusMalus(1,0,0,0,0,0,0,0,0,0,"noActionSpaceBonus");
	@Test
	public void TestMerge(){
		this.numeric.merge(this.numericAndOther);
		assertEquals(this.numeric.getEffects().size(),3);
	}
	@Test
	public void TestGetEffects(){
		assertEquals(this.numeric.getEffects().size(),1);
	}
	@Test
	public void TestApplyModifier(){
		GameState gs=new GameState(3);
		try {
			gs.addPlayer(new Player(new SocketUser(null),null,4));
			gs.addPlayer(new Player(new SocketUser(null),null,4));
			gs.addPlayer(new Player(new SocketUser(null),null,4));
		} catch (FullGameException e) {
			//never verified
		}
		gs.defineFamiliarsPerPlayer();
		gs.createRoundArray();
		PermanentBonusMalus skipFirstTurn=new PermanentBonusMalus(0,0,0,0,0,0,0,0,0,0,"skipFirstActionMalus");
		skipFirstTurn.applyModifier(gs.getPlayers()[0], gs.getRoundArray());
		Player[] newArray=gs.getRoundArray();
		Player[] players=gs.getPlayers();
		int n=gs.getNumberOfPlayers();
		for(int i=0;i<newArray.length-1;i++){
			assertEquals(players[(i+1)%n],newArray[i]);
		}
		assertEquals(players[0],newArray[newArray.length-1]);
	}
	@Test
	public void testApplyDiscountOnBlue(){
		PermanentBonusMalus pbm=new PermanentBonusMalus(0,0,0,0,0,0,0,0,0,0,"oneCoinDiscountOnBlue");
		ResourceSet res=new ResourceSet(1,1,1,1,1,1,1);
		pbm.applyDiscountOnBlue(res);
		assertTrue(new ResourceSet(1,1,0,1,1,1,1).equals(res));
		pbm.merge(new PermanentBonusMalus(0,0,0,0,0,0,0,0,0,0,"threeCoinsDiscount"));
		
		res=new ResourceSet(1,1,4,1,1,1,1);
		pbm.applyDiscountOnBlue(res);
		assertTrue(new ResourceSet(1,1,0,1,1,1,1).equals(res));
	}
	@Test
	public void testApplyDiscountOnYellow(){
		PermanentBonusMalus pbm=new PermanentBonusMalus(0,0,0,0,0,0,0,0,0,0,"woodOrStoneDiscountOnYellow");
		
		ResourceSet res=new ResourceSet(1,1,1,1,1,1,1);
		pbm.applyDiscountOnYellow(1,res);
		assertTrue(new ResourceSet(0,1,1,1,1,1,1).equals(res));
		
		res=new ResourceSet(1,1,1,1,1,1,1);
		pbm.applyDiscountOnYellow(2,res);
		assertTrue(new ResourceSet(1,0,1,1,1,1,1).equals(res));
		
		pbm.merge(new PermanentBonusMalus(0,0,0,0,0,0,0,0,0,0,"threeCoinsDiscount"));
		res=new ResourceSet(1,1,3,1,1,1,1);
		pbm.applyDiscountOnYellow(2,res);
		assertTrue(new ResourceSet(1,0,0,1,1,1,1).equals(res));
	}
	@Test(expected=it.polimi.ingsw.LM6.server.game.exceptions.CannotGoOnMarketException.class)
	public void testApplyMarketMaluses() throws CannotGoOnMarketException{
		PermanentBonusMalus pbm=new PermanentBonusMalus(0,0,0,0,0,0,0,0,0,0,"cannotUseMarketMalus");
		pbm.applyMarketMaluses();
	}
	@Test
	public void testApplyModifier(){
		PermanentBonusMalus pbm=new PermanentBonusMalus(0,0,0,0,0,0,0,0,0,0,"oneCoinMalus");
		pbm.merge(new PermanentBonusMalus(0,0,0,0,0,0,0,0,0,0,"oneMPMalus"));
		pbm.merge(new PermanentBonusMalus(0,0,0,0,0,0,0,0,0,0,"oneRockWoodMalus"));
		pbm.merge(new PermanentBonusMalus(0,0,0,0,0,0,0,0,0,0,"oneServantMalus"));
		Player p=new Player(new SocketUser(null),null,4);
		Bonus b=new Bonus(new ResourceSet(1,1,1,1,1,1,1));
		pbm.applyModifier(new Action(p),b);
		assertTrue(b.getResourceSetFor(p).equals(new ResourceSet(0,0,0,0,0,1,1)));
	}
	@Test(expected=it.polimi.ingsw.LM6.server.game.exceptions.NoVPException.class)
	public void testGreenVPMalus() throws NoVPException{
		PermanentBonusMalus pbm=new PermanentBonusMalus(0,0,0,0,0,0,0,0,0,0,"noGreenVP");
		pbm.applyGreenVPMalus();
	}
	@Test(expected=it.polimi.ingsw.LM6.server.game.exceptions.NoVPException.class)
	public void testBlueVPMalus() throws NoVPException{
		PermanentBonusMalus pbm=new PermanentBonusMalus(0,0,0,0,0,0,0,0,0,0,"noBlueVP");
		pbm.applyBlueVPMalus();
	}
	@Test(expected=it.polimi.ingsw.LM6.server.game.exceptions.NoVPException.class)
	public void testPurpleVPMalus() throws NoVPException{
		PermanentBonusMalus pbm=new PermanentBonusMalus(0,0,0,0,0,0,0,0,0,0,"noPurpleVP");
		pbm.applyPurpleVPMalus();
	}
	@Test
	public void testApplyVPNumericalMaluses(){
		PermanentBonusMalus pbm=new PermanentBonusMalus(0,0,0,0,0,0,0,0,0,0,"loseOneVPEveryFive");
		Player p=new Player(new SocketUser(null),null,4);
		p.getResourceSet().setZero();
		p.acquireResources(new ResourceSet(10,10,10,10,10,10,10));
		pbm.applyVPNumericalMaluses(p);
		assertTrue(new ResourceSet(10,10,10,10,10,8,10).equals(p.getResourceSet()));
		
		pbm=new PermanentBonusMalus(0,0,0,0,0,0,0,0,0,0,"loseOneVPEveryMP");
		p.getResourceSet().setZero();
		p.acquireResources(new ResourceSet(10,10,10,10,10,10,10));
		pbm.applyVPNumericalMaluses(p);
		assertTrue(new ResourceSet(10,10,10,10,10,0,10).equals(p.getResourceSet()));
		
		pbm=new PermanentBonusMalus(0,0,0,0,0,0,0,0,0,0,"loseOneVPEveryRes");
		p.getResourceSet().setZero();
		p.acquireResources(new ResourceSet(10,10,10,10,10,10,10));
		pbm.applyVPNumericalMaluses(p);
		assertTrue(new ResourceSet(10,10,10,10,10,0,10).equals(p.getResourceSet()));
		
		pbm=new PermanentBonusMalus(0,0,0,0,0,0,0,0,0,0,"loseOneVPEveryWoodStoneYellowCost");
		p.getResourceSet().setZero();
		p.acquireResources(new ResourceSet(10,10,10,10,10,10,10));
		pbm.applyVPNumericalMaluses(p);
		assertTrue(new ResourceSet(10,10,10,10,10,10,10).equals(p.getResourceSet()));
	}
	@Test
	public void testApplyNoActionSpaceBonus(){
		PermanentBonusMalus pbm=new PermanentBonusMalus(0,0,0,0,0,0,0,0,0,0,"noActionSpaceBonus");
		ResourceSet rs=new ResourceSet(10,10,10,10,10,10,10);
		pbm.applyNoActionSpaceBonus(rs);
		assertTrue(rs.isEmpty());
	}
	@Test
	public void testApplyFaithBonus(){
		PermanentBonusMalus pbm=new PermanentBonusMalus(0,0,0,0,0,0,0,0,0,0,"faithBonus");
		Player p=new Player(new SocketUser(null),null,4);
		p.getResourceSet().setZero();
		p.acquireResources(new ResourceSet(10,10,10,10,10,10,10));
		pbm.applyFaithBonus(p);
		assertTrue(new ResourceSet(10,10,10,10,10,15,10).equals(p.getResourceSet()));
	}
	@Test(expected=it.polimi.ingsw.LM6.server.game.exceptions.CanGoInOccupiedSpacesException.class)
	public void testCanGoInOccupiedSpacesVPMalus() throws CanGoInOccupiedSpacesException{
		PermanentBonusMalus pbm=new PermanentBonusMalus(0,0,0,0,0,0,0,0,0,0,"canGoInOccupiedSpaces");
		pbm.applyCanGoInOccupiedSpaces();
	}
	@Test
	public void testApplyMilitaryReqBonus(){
		PermanentBonusMalus pbm=new PermanentBonusMalus(0,0,0,0,0,0,0,0,0,0,"noMilitaryRequirements");
		ResourceSet rs=new ResourceSet(10,10,10,10,10,10,10);
		pbm.applyMilitaryRequirementBonuses(rs);
		assertTrue(rs.isEmpty());
	}
	@Test
	public void testApplyNoTowerCoinsCost(){
		PermanentBonusMalus pbm=new PermanentBonusMalus(0,0,0,0,0,0,0,0,0,0,"noTowerCoinsCost");
		ResourceSet rs=new ResourceSet(10,10,10,10,10,10,10);
		pbm.applyNoTowerCoinsCost(rs);
		assertTrue(rs.isEmpty());
	}
	@Test(expected=it.polimi.ingsw.LM6.server.game.exceptions.AlreadyUsedBonusException.class)
	public void testGetEveryTurnResources() throws AlreadyUsedBonusException{
		PermanentBonusMalus pbm=new PermanentBonusMalus(0,0,0,0,0,0,0,0,0,0,"none");
		pbm.add(new EveryTurnBonus(new Bonus(new ResourceSet(1,2,3,4,5,0,0)),"everyTurnBonus"));
		Player p=new Player(new SocketUser(null),null,4);
		Bonus result=pbm.getEveryTurnResources(p);
		assertTrue(result.getResourceSetFor(p).equals(new ResourceSet(1,2,3,4,5,0,0)));
		result=pbm.getEveryTurnResources(p);
	}
	@Test(expected=it.polimi.ingsw.LM6.server.game.exceptions.AlreadyUsedBonusException.class)
	public void testGetEveryTurnProduction() throws AlreadyUsedBonusException{
		PermanentBonusMalus pbm=new PermanentBonusMalus(0,0,0,0,0,0,0,0,0,0,"none");
		pbm.add(new BonusProduction(new Bonus(null, false, 'N', false,'N', 0,null,
				true,false, 1,0,false),"everyTurnProduction"));
		Player p=new Player(new SocketUser(null),null,4);
		Bonus result=pbm.getEveryTurnProduction(p);
		assertTrue(result.hasBonusProduction());
		result=pbm.getEveryTurnProduction(p);
	}
	@Test(expected=it.polimi.ingsw.LM6.server.game.exceptions.AlreadyUsedBonusException.class)
	public void testGetEveryTurnHarvest() throws AlreadyUsedBonusException{
		PermanentBonusMalus pbm=new PermanentBonusMalus(0,0,0,0,0,0,0,0,0,0,"none");
		pbm.add(new BonusHarvest(new Bonus(null, false, 'N', false,'N', 0,null,
				false,true, 1,0,false),"everyTurnHarvest"));
		Player p=new Player(new SocketUser(null),null,4);
		Bonus result=pbm.getEveryTurnHarvest(p);
		assertTrue(result.hasBonusHarvest());
		result=pbm.getEveryTurnHarvest(p);
	}
	@Test
	public void testApplyDiscountOnPurple(){
		PermanentBonusMalus pbm=new PermanentBonusMalus(0,0,0,0,0,0,0,0,0,0,"threeCoinsDiscount");
		ResourceSet res=new ResourceSet(1,1,4,1,1,1,1);
		pbm.applyDiscountOnPurple(res);
		assertTrue(new ResourceSet(1,1,1,1,1,1,1).equals(res));
	}
	@Test(expected=it.polimi.ingsw.LM6.server.game.exceptions.AlreadyUsedBonusException.class)
	public void testOneSixFamiliar() throws AlreadyUsedBonusException{
		PermanentBonusMalus pbm=new PermanentBonusMalus(0,0,0,0,0,0,0,0,0,0,"oneSixFamiliarPerTurn");
		Player p=new Player(new SocketUser(null),null,4);
		pbm.applyOneSixFamiliarPerTurn(p, p.getFamiliar(0));
		assertTrue(p.getFamiliar(0).getStrength()==6);
		pbm.applyOneSixFamiliarPerTurn(p, p.getFamiliar(0));
	}
	@Test
	public void testFixedFiveBonus(){
		PermanentBonusMalus pbm=new PermanentBonusMalus(0,0,0,0,0,0,0,0,0,0,"fixedFiveBonus");
		Player p=new Player(new SocketUser(null),null,4);
		pbm.applyFixedFiveBonus(p);
		assertTrue(p.getFamiliar(0).getStrength()==5 && p.getFamiliar(1).getStrength()==5
				&& p.getFamiliar(2).getStrength()==5 && p.getFamiliar(3).getStrength()==0);
	}
	@Test
	public void testDoubleWoodStoneCoinServantsFromCards(){
		PermanentBonusMalus pbm=new PermanentBonusMalus(0,0,0,0,0,0,0,0,0,0,"doubleWoodStoneCoinsServantsFromCards");
		Player p=new Player(new SocketUser(null),null,4);
		Action a=new Action(p);
		Bonus b=new Bonus(new ResourceSet(1,1,1,1,1,1,1));
		pbm.applyOnCardsBonus(a, b);
		assertTrue(b.getResourceSetFor(p).equals(new ResourceSet(2,2,2,2,1,1,1)));
	}
	@Test
	public void testDoubleSevantMalus(){
		Player p=new Player(new SocketUser(null),null,4);
		Action a=new Action("TOWERACTION;GREEN;2;BLACK;3".split(";"),p,new ResourceSet(0,0,0,0,0,0,0));
		PermanentBonusMalus pbm=new PermanentBonusMalus(0,0,0,0,0,0,0,0,0,0,"doubleServantMalus");
		p.setFamiliarsValues(0, 0, 0, 0);
		pbm.applyModifier(a);
		assertTrue(a.getActionStrength()==1);
		
	}
	@Test
	public void testNumericalMaluses(){
		Player p=new Player(new SocketUser(null),null,4);
		Action a=new Action("TOWERACTION;GREEN;2;BLACK;3".split(";"),p,new ResourceSet(0,0,0,0,0,0,0));
		PermanentBonusMalus pbm=new PermanentBonusMalus(1,1,1,1,1,1,1,1,1,1,"none");
		p.setFamiliarsValues(0, 0, 0, 0);
		pbm.applyModifier(a);
		assertTrue(a.getActionStrength()==5);
		
		a=new Action("TOWERACTION;BLUE;2;WHITE;3".split(";"),p,new ResourceSet(0,0,0,0,0,0,0));
		pbm.applyModifier(a);
		assertTrue(a.getActionStrength()==5);
		
		a=new Action("TOWERACTION;YELLOW;2;ORANGE;3".split(";"),p,new ResourceSet(0,0,0,0,0,0,0));
		pbm.applyModifier(a);
		assertTrue(a.getActionStrength()==5);
		
		a=new Action("TOWERACTION;PURPLE;2;UNCOLOUR;3".split(";"),p,new ResourceSet(0,0,0,0,0,0,0));
		pbm.applyModifier(a);
		assertTrue(a.getActionStrength()==5);
		
		a=new Action("PRODUCTIONACTION;UNCOLOUR;3;1;0;0;0;0;0;0".split(";"),p,new ResourceSet(0,0,0,0,0,0,0));
		pbm.applyModifier(a);
		assertTrue(a.getActionStrength()==5);
		a=new Action("HARVESTACTION;UNCOLOUR;3;1".split(";"),p,new ResourceSet(0,0,0,0,0,0,0));
		pbm.applyModifier(a);
		assertTrue(a.getActionStrength()==5);
	}
	@Test
	public void testEffect() throws CannotGoOnMarketException, NoVPException, CanGoInOccupiedSpacesException, AlreadyUsedBonusException{
		Effect e=new Effect();
		Player p=new Player(new SocketUser(null),null,4);
		ResourceSet b=new ResourceSet(1,1,1,1,1,1,1);
		p.getResourceSet().setZero();
		p.acquireResources(new ResourceSet(10,10,10,10,10,10,10));
		Action a=new Action("TOWERACTION;GREEN;2;BLACK;3".split(";"),p,new ResourceSet(0,0,0,0,0,0,0));
		p.setFamiliarsValues(0, 0, 0, 0);
		ResourceSet rs=new ResourceSet(1,1,1,1,1,1,1);
		e.applyOn(a);
		assertTrue(a.getActionStrength()==0);
		e.applyDiscountOnBlue(rs);
		assertTrue(rs.equals(new ResourceSet(1,1,1,1,1,1,1)));
		e.applyDiscountOnYellow(1,rs);
		assertTrue(rs.equals(new ResourceSet(1,1,1,1,1,1,1)));
		e.applyDiscountOnPurple(rs);
		assertTrue(rs.equals(new ResourceSet(1,1,1,1,1,1,1)));
		e.applyMarketMaluses();
		e.applyBlueVPMalus();
		e.applyGreenVPMalus();
		e.applyPurpleVPMalus();
		e.applyVPNumericalMaluses(p);
		assertTrue(p.getResourceSet().getVictoryPoints()==10);
		e.applyNoActionSpaceBonus(b);
		assertTrue(b.equals(new ResourceSet(1,1,1,1,1,1,1)));
		e.applyFaithBonus(p);
		assertTrue(p.getResourceSet().getVictoryPoints()==10);
		e.applyCanGoInOccupiedSpaces();
		e.applyMilitaryRequirementBonuses(b);
		assertTrue(b.equals(new ResourceSet(1,1,1,1,1,1,1)));
		e.applyNoTowerCoinsCost(b);
		assertTrue(b.equals(new ResourceSet(1,1,1,1,1,1,1)));
		e.applyFixedFiveBonus(p);
		assertTrue(p.getFamiliar(0).getStrength()==0 && p.getFamiliar(1).getStrength()==0
				&& p.getFamiliar(2).getStrength()==0 && p.getFamiliar(3).getStrength()==0);
		Bonus bon=e.getEveryTurnResources(p);
		assertTrue(bon.isEmpty());
		e.resetEveryTurnBonuses();
		bon=e.getEveryTurnResources(p);
		assertTrue(bon.isEmpty());
		bon=e.getEveryTurnHarvest();
		assertTrue(bon.isEmpty());
		bon=e.getEveryTurnProduction();
		assertTrue(bon.isEmpty());
		assertFalse(e.applyOneSixFamiliarPerTurn(p, p.getFamiliar(0)));
	}
}
