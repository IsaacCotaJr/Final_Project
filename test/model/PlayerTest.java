package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class PlayerTest {

	Player p = new Player(100, true);
	Player comp = new Player(100, false);
	
	@Test
	void isHumanTrue() {
		assertTrue(p.isHuman());
		assertTrue(p.toString().equals("true"));
	}
	
	@Test
	void isHumanFalse() {
		assertFalse(comp.isHuman());
	}
	
	@Test
	void setHandTest() {
		ArrayList<Card> hand = new ArrayList<Card>();
		p.setHand(hand);
	}
	
	@Test
	void getHandTest() {
		ArrayList<Card> hand = new ArrayList<Card>();
		p.setHand(hand);
		List<Card> h = p.getHand();
	}

	@Test
	void getBalanceTest() {
		assertTrue(p.getBalance() - 100 < 0.001); // need to test doubles differently
	}
	
	@Test
	void addToBalanceTest() {
		p.addToBalance(10);
		assertTrue(p.getBalance() - 110 < 0.001); // need to test doubles differently
	}
	
	@Test
	void subFromBalanceTest() {
		p.subFromBalance(10);
		assertTrue(p.getBalance() - 90 < 0.001); // need to test doubles differently
	}
	
	@Test
	void foldTest() {
		p.fold();
		assertTrue(p.getFold());
	}
	
	@Test
	void setFoldTest() {
		p.setFold(false);
		assertFalse(p.getFold());
	}
	
	@Test
	void discardCardTest() {
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(Card.getCard(Rank.ACE, Suit.CLUBS));
		p.setHand(hand);
		
		p.discardCard(0);
		assertTrue(p.getHand().size() == 0);
	}
	
	@Test
	void drawCardTest() {
		ArrayList<Card> hand = new ArrayList<Card>();
		p.setHand(hand);
		
		p.drawCard(Card.getCard(Rank.TEN, Suit.HEARTS));
		assertTrue(p.getHand().size() == 1);
	}
}
