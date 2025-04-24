package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

public class CardTest {

	@Test
	public void testGetters() {

		Card c1 = Card.getCard(Rank.DEUCE, Suit.CLUBS);
		Card c2 = Card.getCard(Rank.DEUCE, Suit.DIAMONDS);
		Card c3 = Card.getCard(Rank.THREE, Suit.CLUBS);
		Card c4 = Card.getCard(Rank.THREE, Suit.DIAMONDS);

		assertEquals(Rank.DEUCE, c1.getRank());
		assertEquals(Suit.CLUBS, c1.getSuit());
		assertEquals(2, c1.getValue());

		assertTrue(Rank.DEUCE == c2.getRank());
		assertTrue(Suit.DIAMONDS == c2.getSuit());
		assertEquals(2, c1.getValue());

		assertTrue(Rank.THREE == c3.getRank());
		assertTrue(Suit.CLUBS == c3.getSuit());
		assertEquals(3, c3.getValue());

		assertTrue(Rank.THREE == c4.getRank());
		assertTrue(Suit.DIAMONDS == c4.getSuit());
		assertEquals(3, c4.getValue());
	}

	@Test
	public void testCompareTo() {
		Card c1 = Card.getCard(Rank.DEUCE, Suit.CLUBS);
		Card c2 = Card.getCard(Rank.THREE, Suit.DIAMONDS);

		assertTrue(c1.compareTo(c2) < 0);
		assertTrue(c2.compareTo(c1) > 0);
		assertTrue(c1.compareTo(c1) == 0);
	}

	@Test
	public void testEquals() {
		Card c1 = Card.getCard(Rank.DEUCE, Suit.CLUBS);
		Card c2 = Card.getCard(Rank.THREE, Suit.DIAMONDS);
		Card c3 = Card.getCard(Rank.DEUCE, Suit.CLUBS);
		Card c4 = Card.getCard(Rank.THREE, Suit.DIAMONDS);

		assertFalse(c1.equals(c2));
		assertTrue(c1.equals(c3));
		assertTrue(c2.equals(c4));
	}

	@Test
	public void testEqualsOnTheHighSide() {
		Card c1 = Card.getCard(Rank.ACE, Suit.CLUBS);
		Card c2 = Card.getCard(Rank.KING, Suit.DIAMONDS);
		Card c3 = Card.getCard(Rank.ACE, Suit.CLUBS);
		Card c4 = Card.getCard(Rank.KING, Suit.DIAMONDS);

		assertFalse(c1.equals(c2));
		assertTrue(c1.equals(c3));
		assertTrue(c2.equals(c4));
	}

}
