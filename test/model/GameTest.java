package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

class GameTest {
	HashMap<Player, PokerHand> hm = new HashMap<Player, PokerHand>();
	Game g = new Game();
	@Test
	void testWinDetermineWinner() {
		Player p1 = new Player(100.0, false);
		Player p2 = new Player(100.0, false);
		
		ArrayList<Card> l1 = new ArrayList<Card>();
		l1.add(Card.getCard(Rank.ACE, Suit.CLUBS));
		l1.add(Card.getCard(Rank.ACE, Suit.DIAMONDS));
		l1.add(Card.getCard(Rank.ACE, Suit.SPADES));
		l1.add(Card.getCard(Rank.ACE, Suit.HEARTS));
		l1.add(Card.getCard(Rank.DEUCE, Suit.CLUBS));
		PokerHand ph1 = new PokerHand(l1);
		
		ArrayList<Card> l2 = new ArrayList<Card>();
		l2.add(Card.getCard(Rank.DEUCE, Suit.CLUBS));
		l2.add(Card.getCard(Rank.THREE, Suit.DIAMONDS));
		l2.add(Card.getCard(Rank.FOUR, Suit.SPADES));
		l2.add(Card.getCard(Rank.EIGHT, Suit.HEARTS));
		l2.add(Card.getCard(Rank.DEUCE, Suit.CLUBS));
		PokerHand ph2 = new PokerHand(l2);
		
		hm.put(p1, ph1);
		hm.put(p2, ph2);
		
		assertEquals(p1, g.determineWinner(hm).get(0));
	}

	@Test
	void testTieDetermineWinner() {
		Player p1 = new Player(100.0, false);
		Player p2 = new Player(100.0, false);
		
		ArrayList<Card> l1 = new ArrayList<Card>();
		l1.add(Card.getCard(Rank.ACE, Suit.CLUBS));
		l1.add(Card.getCard(Rank.ACE, Suit.DIAMONDS));
		l1.add(Card.getCard(Rank.ACE, Suit.SPADES));
		l1.add(Card.getCard(Rank.ACE, Suit.HEARTS));
		l1.add(Card.getCard(Rank.EIGHT, Suit.CLUBS));
		PokerHand ph1 = new PokerHand(l1);
		
		ArrayList<Card> l2 = new ArrayList<Card>();
		l2.add(Card.getCard(Rank.ACE, Suit.CLUBS));
		l2.add(Card.getCard(Rank.ACE, Suit.DIAMONDS));
		l2.add(Card.getCard(Rank.ACE, Suit.SPADES));
		l2.add(Card.getCard(Rank.EIGHT, Suit.HEARTS));
		l2.add(Card.getCard(Rank.ACE, Suit.CLUBS));
		PokerHand ph2 = new PokerHand(l2);
		
		hm.put(p1, ph1);
		hm.put(p2, ph2);
		
		assertEquals(2, g.determineWinner(hm).size());
	}
}
