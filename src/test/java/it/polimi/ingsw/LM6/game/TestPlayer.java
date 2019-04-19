package it.polimi.ingsw.LM6.game;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.game.board.PersonalBonusTile;
import it.polimi.ingsw.LM6.server.game.bonuses.Bonus;
import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;
import it.polimi.ingsw.LM6.server.game.card.*;
import it.polimi.ingsw.LM6.server.game.familiar.FamiliarColor;
import it.polimi.ingsw.LM6.server.users.*;

public class TestPlayer {
	private Player test;
	private SocketUser userFake;
	public TestPlayer(){
		userFake=new SocketUser(null);
		userFake.setNickname("aaa");
		try {
			this.test= new Player(userFake,4,"102W 104St 105C 103Se 100VP 100MP 100FP"
					,"117","4;","56;","30;","77;74;","100;101","102");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testReload(){
		try {
			Player p= new Player(userFake,4,"102W 104St 105C 103Se 100VP 100MP 100FP"
					,"117","4;","56;","30;","77;74;","100;101","102");
			assertTrue(p.getNickname().equals("aaa"));
			assertTrue(p.getResourceSet().equals(new ResourceSet(102,104,105,103,100,100,100)));
			assertTrue(p.getPersonalTile().getNumber()==117);
			for(GreenCard c:p.getGreenCardList())
				assertTrue(c.getNumber()==4);
			for(BlueCard c:p.getBlueCardList())
				assertTrue(c.getNumber()==56);
			for(YellowCard c:p.getYellowCardList())
				assertTrue(c.getNumber()==30);
			for(PurpleCard c:p.getPurpleCardList())
				assertTrue(c.getNumber()==77 || c.getNumber()==74);
			for(LeaderCard c:p.getLeadersInHand())
				assertTrue(c.getNumber()==100 || c.getNumber()==101);
			for(LeaderCard c:p.getLeadersPlayed())
				assertTrue(c.getNumber()==102);
			p= new Player(userFake,4," ","117"," "," "," "," "," "," ");
			assertTrue(p.getResourceSet().isEmpty());
			assertTrue(p.getBlueCardList().isEmpty());
			assertTrue(p.getGreenCardList().isEmpty());
			assertTrue(p.getLeadersInHand().isEmpty());
			assertTrue(p.getLeadersPlayed().isEmpty());
			assertTrue(p.getPurpleCardList().isEmpty());
			assertTrue(p.getYellowCardList().isEmpty());
		} catch (IOException e) {
			//DO NOTHING
		}
	
	}
	@Test
	public void testGetGreenNumbers(){
		assertTrue(test.getGreenCardsNumbers().equals("4;"));
		try {
			Player p= new Player(userFake,4," ","117"," "," "," "," "," "," ");
			assertTrue(p.getGreenCardsNumbers().equals(" "));
		} catch (IOException e) {
			//DO NOTHING
		}
	}
	@Test
	public void testGetBlueNumbers(){
		assertTrue(test.getBlueCardsNumbers().equals("56;"));
		try {
			Player p= new Player(userFake,4," ","117"," "," "," "," "," "," ");
			assertTrue(p.getBlueCardsNumbers().equals(" "));
		} catch (IOException e) {
			//DO NOTHING
		}
	}
	@Test
	public void testGetYellowNumbers(){
		assertTrue(test.getYellowCardsNumbers().equals("30;"));
		try {
			Player p= new Player(userFake,4," ","117"," "," "," "," "," "," ");
			assertTrue(p.getYellowCardsNumbers().equals(" "));
		} catch (IOException e) {
			//DO NOTHING
		}
	}
	@Test
	public void testGetPurpleNumbers(){
		assertTrue(test.getPurpleCardsNumbers().equals("77;74;"));
		try {
			Player p= new Player(userFake,4," ","117"," "," "," "," "," "," ");
			assertTrue(p.getPurpleCardsNumbers().equals(" "));
		} catch (IOException e) {
			//DO NOTHING
		}
	}
	@Test
	public void testGetLeadersNumbers(){
		assertTrue(test.getLeaderCardsNumbers(test.getLeadersInHand()).equals("100;101;"));
		try {
			Player p= new Player(userFake,4," ","117"," "," "," "," "," "," ");
			assertTrue(p.getLeaderCardsNumbers(p.getLeadersInHand()).equals(" "));
		} catch (IOException e) {
			//DO NOTHING
		}
	}
	@Test
	public void testToString(){
		assertTrue(test.toString(test).equals("aaa!102W 104St 105C 103Se 100VP 100MP 100FP !4ùCava di Ghiaiaù ù2St ù2St  Str=4-!30ùTagliapietreù2St 1C ù2VP ù1St =>3C  OR 2St =>5C  Str=3-!56ùBadessaù3C ù1FP   Action on E:4  Disc= ù -!77ùCampagna MilitareùReq=3MP Cost=2MP ù3C ù5VP -74ùRiparare la Chiesaù1W 1St 1C ù1FP ù5VP -!0. Michelangelo Buonarroti   UPT ricevi 3C   REQUIREMENT=10St ù100-1. Ludovico III Gonzaga   UPT ricevi 1CB   REQUIREMENT=15Se ù101-!Sandro Botticelli   UPT ricevi 2MP 1VP   REQUIREMENT=10W ù102-!PRODUCTION=2C 1MP |HARVEST=1W 1St 1Se ù117!"));
	}
	@Test
	public void testGetUser(){
		assertTrue(test.getUser()==this.userFake);
	}
	@Test
	public void testSetResetOffer(){
		ResourceSet offer=new ResourceSet(1,1,1,1,1,1,1);
		test.setOffer(offer);
		assertTrue(test.getFamiliarOffer()==offer);
		test.resetFamiliarOffer();
		assertTrue(test.getFamiliarOffer().isEmpty());
		test.setOffer(null);
		assertTrue(test.getFamiliarOffer().isEmpty());
	}
	@Test(expected=it.polimi.ingsw.LM6.server.game.exceptions.BadInputException.class)
	public void testGetFamiliar(){
		assertTrue(test.getFamiliar("ORANGE").getColour()==FamiliarColor.ORANGE);
		assertTrue(test.getFamiliar("WHITE").getColour()==FamiliarColor.WHITE);
		assertTrue(test.getFamiliar("UNCOLOURED").getColour()==FamiliarColor.UNCOLOURED);
		assertTrue(test.getFamiliar(null).getColour()==FamiliarColor.ORANGE);
	}
	@Test
	public void testActivateDeactivatePlayer(){
		test.activatePlayer();
		assertTrue(test.isActive());
		test.deactivatePlayer();
		assertTrue(!test.isActive());
	}
	@Test
	public void testAcquireResourcesFromBonus(){
		test.getResourceSet().setZero();
		test.acquireResources(new ResourceSet(1,2,3,4,5,6,7));
		test.acquireResourcesFromBonus(new Bonus(new ResourceSet(1,2,3,4,5,6,8)));
		assertTrue(test.getResourceSet().equals(new ResourceSet(2,4,6,8,10,12,15)));
	}
	@Test
	public void testSetIUser(){
		Player p;
		try {
			p = new Player(userFake,4," ","117"," "," "," "," "," "," ");
			IUser nuovo=new SocketUser(null);
			p.setIUser(nuovo);
			assertTrue(p.getUser()==nuovo);
		} catch (IOException e) {
			assertTrue(false);
		}
		
	}
	@Test
	public void setIsConnected(){
		test.setIsConnected(true);
		assertTrue(test.isConnected());
		test.setIsConnected(false);
		assertTrue(!test.isConnected());
	}
	@Test
	public void testGetSetResetDraftChoice(){
		test.setDraftChoice(1);
		assertTrue(test.getDraftChoice()==1);
		test.setDraftChoice(2);
		assertTrue(test.getDraftChoice()==2);
		test.resetDraftChoice();
		assertTrue(test.getDraftChoice()==0);
	}
	@Test
	public void testSetIsOfferPhaseWinner(){
		test.setIsOfferPhaseWinner(true);
		assertTrue(test.isOfferPhaseWinner());
		test.setIsOfferPhaseWinner(false);
		assertTrue(!test.isOfferPhaseWinner());
	}
	@Test
	public void testGetSetResetExcommResulr(){
		test.setExcommunicationResult();
		assertTrue(!test.hasBeenExcommunicated());
		test.resetExcommunicationResult();
		assertTrue(test.hasBeenExcommunicated());
	}
	@Test
	public void testSetBonusTile(){
		PersonalBonusTile pbt=new PersonalBonusTile(new ResourceSet(), new ResourceSet(), 117);
		test.setPersonalTile(pbt);
		assertTrue(test.getPersonalTile()==pbt);
	}
		
}
