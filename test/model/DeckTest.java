package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.List;

import org.junit.Test;

public class DeckTest {

	@Test
	public void testGetDeck() {
		Deck deck = new Deck();
		assertEquals(52, deck.getDeck().size());
	}
	
	@Test
	public void testShuffle() {
		Deck deck = new Deck();
		Card first = deck.getDeck().getFirst();
		deck.shuffle();
		assertNotEquals(first, deck.getDeck().getFirst());
	}
	
	@Test
	public void testDealPlayerCards() {
		Deck deck = new Deck();
		List<Card> hand = deck.dealPlayerCards();
		assertEquals(5, hand.size());
		assertEquals(47, deck.getDeck().size());
	}
	
	@Test
	public void testDraw() {
		Deck deck = new Deck();
		List<Card> hand = deck.dealPlayerCards();
		assertEquals(5, hand.size());
		hand.add(deck.draw());
		assertEquals(6, hand.size());
		hand.add(deck.draw());
		assertEquals(7, hand.size());
		
	}
}
