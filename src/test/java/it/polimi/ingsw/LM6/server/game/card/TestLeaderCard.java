package it.polimi.ingsw.LM6.server.game.card;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import it.polimi.ingsw.LM6.server.game.Player;
import it.polimi.ingsw.LM6.server.users.SocketUser;

public class TestLeaderCard {
	private Player test;
	private SocketUser userFake;
	private LeaderCard card;
	private LeaderCard card2;
	public TestLeaderCard(){
		userFake=new SocketUser(null);
		userFake.setNickname("aaa");
		try {
			this.test= new Player(userFake,4,"102W 104St 105C 103Se 100VP 17MP 100FP"
					,"117","4;","56;","30;","77;74;","100;101","102");
		} catch (IOException e) {
			e.printStackTrace();
		}
		card= new LeaderCard("Ludovico il Moro;0;0;0;0;0;0;0;false;2;2;2;2;0;0;0;0;0;0;0;0;false;false;false;0;0;0;0;0;fixedFiveBonus;PERM tutti i familiari colorati Str=5;109");
		card2= new LeaderCard("Lucrezia Borgia;0;0;0;0;0;0;0;true;0;0;0;0;0;0;0;0;0;0;0;0;false;false;false;0;2;2;2;0;none;PERM 2OFB 2BFB 2WFB;105");
	}
	@Test
	public void testToString(){
		assertTrue(card.toString().equals("Ludovico il Moro   PERM tutti i familiari colorati Str=5   REQUIREMENT=   GC=2  BC=2  YC=2  PC=2ù109"));
		assertTrue(card2.toString().equals("Lucrezia Borgia   PERM 2OFB 2BFB 2WFB   REQUIREMENT=sixCardReqù105"));
	}
	@Test
	public void testCanBeActvatedBy() throws IOException{
		assertTrue(!card2.canBeActivatedBy(test));
		this.test= new Player(userFake,4,"102W 104St 105C 103Se 100VP 17MP 100FP"
				,"117","4;","56;56;56;56;56;56","30;","77;74;","100;101","102");
		assertTrue(card2.canBeActivatedBy(test));
		this.test= new Player(userFake,4,"102W 104St 105C 103Se 100VP 17MP 100FP"
				,"117","4;","56;","30;30;30;30;30;30","77;74;","100;101","102");
		assertTrue(card2.canBeActivatedBy(test));
		this.test= new Player(userFake,4,"102W 104St 105C 103Se 100VP 17MP 100FP"
				,"117","4;4;4;4;4;4","56;","30;","77;74;","100;101","102");
		assertTrue(card2.canBeActivatedBy(test));
		this.test= new Player(userFake,4,"102W 104St 105C 103Se 100VP 17MP 100FP"
				,"117","4;","56;","30;","77;74;77;77;77;77","100;101","102");
		assertTrue(card2.canBeActivatedBy(test));
		this.test= new Player(userFake,4,"102W 104St 105C 103Se 100VP 17MP 100FP"
				,"117","4;","56;56","30;30","77;74;","100;101","102");
		assertTrue(!card.canBeActivatedBy(test));
		this.test= new Player(userFake,4,"102W 104St 105C 103Se 100VP 17MP 100FP"
				,"117","44;","56;","30;30","77;74;","100;101","102");
		assertTrue(!card.canBeActivatedBy(test));
		this.test= new Player(userFake,4,"102W 104St 105C 103Se 100VP 17MP 100FP"
				,"117","4;4","56;56","30;","77;74;","100;101","102");
		assertTrue(!card.canBeActivatedBy(test));
		this.test= new Player(userFake,4,"102W 104St 105C 103Se 100VP 17MP 100FP"
				,"117","4;4","56;56","30;30","77;","100;101","102");
		assertTrue(!card.canBeActivatedBy(test));
		this.test= new Player(userFake,4,"102W 104St 105C 103Se 100VP 17MP 100FP"
				,"117","4;4","56;56","30;30","77;74;","100;101","102");
		assertTrue(card.canBeActivatedBy(test));
		card= new LeaderCard("Ludovico il Moro;0;0;0;0;0;1;0;false;2;2;2;2;0;0;0;0;0;0;0;0;false;false;false;0;0;0;0;0;fixedFiveBonus;PERM tutti i familiari colorati Str=5;109");
		test.getResourceSet().setZero();
		assertTrue(!card.canBeActivatedBy(test));
	}
}
