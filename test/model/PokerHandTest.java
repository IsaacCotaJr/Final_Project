package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Card;
import model.HandRank;
import model.PokerHand;
import model.Rank;
import model.Suit;


public class PokerHandTest {

    private Card c(Rank rank, Suit suit) {
        return Card.getCard(rank, suit);
    }

    @Test
    public void testToString() {
        PokerHand hand = new PokerHand(
            c(Rank.ACE, Suit.SPADES),
            c(Rank.KING, Suit.HEARTS),
            c(Rank.QUEEN, Suit.CLUBS),
            c(Rank.JACK, Suit.DIAMONDS),
            c(Rank.TEN, Suit.SPADES)
        );
        String repr = hand.toString();
        assertTrue(repr.contains("14") || repr.contains("A"));
    }

    @Test
    public void testRoyalFlush() {
        PokerHand rf = new PokerHand(
            c(Rank.TEN, Suit.HEARTS),
            c(Rank.JACK, Suit.HEARTS),
            c(Rank.QUEEN, Suit.HEARTS),
            c(Rank.KING, Suit.HEARTS),
            c(Rank.ACE, Suit.HEARTS)
        );
        assertEquals(HandRank.ROYAL_FLUSH, rf.evaluateHandRank());
        assertEquals(0, rf.compareTo(rf));
    }

    @Test
    public void testStraightFlushComparison() {
        PokerHand lowSF = new PokerHand(
            c(Rank.FIVE, Suit.CLUBS),
            c(Rank.SIX, Suit.CLUBS),
            c(Rank.SEVEN, Suit.CLUBS),
            c(Rank.EIGHT, Suit.CLUBS),
            c(Rank.NINE, Suit.CLUBS)
        );
        PokerHand highSF = new PokerHand(
            c(Rank.SIX, Suit.DIAMONDS),
            c(Rank.SEVEN, Suit.DIAMONDS),
            c(Rank.EIGHT, Suit.DIAMONDS),
            c(Rank.NINE, Suit.DIAMONDS),
            c(Rank.TEN, Suit.DIAMONDS)
        );
        assertEquals(HandRank.STRAIGHT_FLUSH, lowSF.evaluateHandRank());
        assertTrue(highSF.compareTo(lowSF) > 0);
        assertTrue(lowSF.compareTo(highSF) < 0);
    }

    @Test
    public void testFourOfAKindAndKicker() {
        PokerHand four2 = new PokerHand(
            c(Rank.DEUCE, Suit.HEARTS),
            c(Rank.DEUCE, Suit.SPADES),
            c(Rank.DEUCE, Suit.CLUBS),
            c(Rank.DEUCE, Suit.DIAMONDS),
            c(Rank.ACE, Suit.HEARTS)
        );
        PokerHand four3 = new PokerHand(
            c(Rank.THREE, Suit.HEARTS),
            c(Rank.THREE, Suit.SPADES),
            c(Rank.THREE, Suit.CLUBS),
            c(Rank.THREE, Suit.DIAMONDS),
            c(Rank.DEUCE, Suit.HEARTS)
        );
        assertEquals(HandRank.FOUR_OF_A_KIND, four2.evaluateHandRank());
        assertTrue(four3.compareTo(four2) > 0);
        PokerHand four2kA = new PokerHand(
            c(Rank.DEUCE, Suit.HEARTS),
            c(Rank.DEUCE, Suit.SPADES),
            c(Rank.DEUCE, Suit.CLUBS),
            c(Rank.DEUCE, Suit.DIAMONDS),
            c(Rank.ACE, Suit.DIAMONDS)
        );
        PokerHand four2kK = new PokerHand(
            c(Rank.DEUCE, Suit.HEARTS),
            c(Rank.DEUCE, Suit.SPADES),
            c(Rank.DEUCE, Suit.CLUBS),
            c(Rank.DEUCE, Suit.DIAMONDS),
            c(Rank.KING, Suit.SPADES)
        );
        assertTrue(four2kA.compareTo(four2kK) > 0);
    }

    @Test
    public void testFullHouse() {
        PokerHand fh1 = new PokerHand(
            c(Rank.SEVEN, Suit.HEARTS),
            c(Rank.SEVEN, Suit.SPADES),
            c(Rank.SEVEN, Suit.CLUBS),
            c(Rank.FIVE, Suit.HEARTS),
            c(Rank.FIVE, Suit.SPADES)
        );
        PokerHand fh2 = new PokerHand(
            c(Rank.SIX, Suit.HEARTS),
            c(Rank.SIX, Suit.SPADES),
            c(Rank.SIX, Suit.CLUBS),
            c(Rank.ACE, Suit.HEARTS),
            c(Rank.ACE, Suit.SPADES)
        );
        assertEquals(HandRank.FULL_HOUSE, fh1.evaluateHandRank());
        assertTrue(fh1.compareTo(fh2) > 0); // trips 7 > 6

        // same trips, different pair
        PokerHand fh3 = new PokerHand(
            c(Rank.SEVEN, Suit.HEARTS),
            c(Rank.SEVEN, Suit.SPADES),
            c(Rank.SEVEN, Suit.CLUBS),
            c(Rank.KING, Suit.HEARTS),
            c(Rank.KING, Suit.SPADES)
        );
        assertTrue(fh3.compareTo(fh1) > 0); // pair K > 5
    }

    @Test
    public void testFlushAndStraightComparison() {
        PokerHand flush1 = new PokerHand(
            c(Rank.ACE, Suit.CLUBS),
            c(Rank.KING, Suit.CLUBS),
            c(Rank.EIGHT, Suit.CLUBS),
            c(Rank.FOUR, Suit.CLUBS),
            c(Rank.DEUCE, Suit.CLUBS)
        );
        PokerHand flush2 = new PokerHand(
            c(Rank.ACE, Suit.DIAMONDS),
            c(Rank.QUEEN, Suit.DIAMONDS),
            c(Rank.NINE, Suit.DIAMONDS),
            c(Rank.FIVE, Suit.DIAMONDS),
            c(Rank.THREE, Suit.DIAMONDS)
        );
        assertEquals(HandRank.FLUSH, flush1.evaluateHandRank());
        assertTrue(flush1.compareTo(flush2) > 0);

        PokerHand straight1 = new PokerHand(
            c(Rank.FIVE, Suit.HEARTS),
            c(Rank.SIX, Suit.SPADES),
            c(Rank.SEVEN, Suit.HEARTS),
            c(Rank.EIGHT, Suit.SPADES),
            c(Rank.NINE, Suit.HEARTS)
        );
        PokerHand straight2 = new PokerHand(
            c(Rank.FOUR, Suit.CLUBS),
            c(Rank.FIVE, Suit.CLUBS),
            c(Rank.SIX, Suit.CLUBS),
            c(Rank.SEVEN, Suit.CLUBS),
            c(Rank.EIGHT, Suit.CLUBS)
        );
        assertEquals(HandRank.STRAIGHT, straight1.evaluateHandRank());
        assertTrue(straight1.compareTo(straight2) > 0);
    }

    @Test
    public void testThreeOfAKind() {
        PokerHand threeA5 = new PokerHand(
            c(Rank.FIVE, Suit.HEARTS),
            c(Rank.FIVE, Suit.SPADES),
            c(Rank.FIVE, Suit.CLUBS),
            c(Rank.ACE, Suit.HEARTS),
            c(Rank.KING, Suit.DIAMONDS)
        );
        PokerHand threeA4 = new PokerHand(
            c(Rank.FOUR, Suit.HEARTS),
            c(Rank.FOUR, Suit.SPADES),
            c(Rank.FOUR, Suit.CLUBS),
            c(Rank.ACE, Suit.CLUBS),
            c(Rank.KING, Suit.HEARTS)
        );
        assertEquals(HandRank.THREE_OF_A_KIND, threeA5.evaluateHandRank());
        assertTrue(threeA5.compareTo(threeA4) > 0);

        PokerHand threeA5k2 = new PokerHand(
            c(Rank.FIVE, Suit.HEARTS),
            c(Rank.FIVE, Suit.SPADES),
            c(Rank.FIVE, Suit.CLUBS),
            c(Rank.DEUCE, Suit.HEARTS),
            c(Rank.THREE, Suit.DIAMONDS)
        );
        PokerHand threeA5k3 = new PokerHand(
            c(Rank.FIVE, Suit.HEARTS),
            c(Rank.FIVE, Suit.SPADES),
            c(Rank.FIVE, Suit.CLUBS),
            c(Rank.THREE, Suit.CLUBS),
            c(Rank.FOUR, Suit.SPADES)
        );
        assertTrue(threeA5k3.compareTo(threeA5k2) > 0);
    }

    @Test
    public void testTwoPair() {
        PokerHand tp1 = new PokerHand(
            c(Rank.KING, Suit.HEARTS),
            c(Rank.KING, Suit.DIAMONDS),
            c(Rank.QUEEN, Suit.HEARTS),
            c(Rank.QUEEN, Suit.SPADES),
            c(Rank.ACE, Suit.CLUBS)
        );
        PokerHand tp2 = new PokerHand(
            c(Rank.JACK, Suit.CLUBS),
            c(Rank.JACK, Suit.SPADES),
            c(Rank.TEN, Suit.CLUBS),
            c(Rank.TEN, Suit.DIAMONDS),
            c(Rank.ACE, Suit.DIAMONDS)
        );
        assertEquals(HandRank.TWO_PAIR, tp1.evaluateHandRank());
        assertTrue(tp1.compareTo(tp2) > 0);

        PokerHand tp1k9 = new PokerHand(
            c(Rank.KING, Suit.HEARTS),
            c(Rank.KING, Suit.DIAMONDS),
            c(Rank.QUEEN, Suit.HEARTS),
            c(Rank.QUEEN, Suit.SPADES),
            c(Rank.NINE, Suit.CLUBS)
        );
        assertTrue(tp1.compareTo(tp1k9) > 0);
    }

    @Test
    public void testOnePair() {
        PokerHand p1 = new PokerHand(
            c(Rank.ACE, Suit.HEARTS),
            c(Rank.ACE, Suit.SPADES),
            c(Rank.KING, Suit.CLUBS),
            c(Rank.QUEEN, Suit.HEARTS),
            c(Rank.JACK, Suit.DIAMONDS)
        );
        PokerHand p2 = new PokerHand(
            c(Rank.KING, Suit.HEARTS),
            c(Rank.KING, Suit.SPADES),
            c(Rank.ACE, Suit.CLUBS),
            c(Rank.QUEEN, Suit.SPADES),
            c(Rank.JACK, Suit.CLUBS)
        );
        assertEquals(HandRank.PAIR, p1.evaluateHandRank());
        assertTrue(p1.compareTo(p2) > 0);
    }

    @Test
    public void testHighCard() {
        PokerHand h1 = new PokerHand(
            c(Rank.ACE, Suit.HEARTS),
            c(Rank.KING, Suit.SPADES),
            c(Rank.NINE, Suit.CLUBS),
            c(Rank.FOUR, Suit.HEARTS),
            c(Rank.DEUCE, Suit.DIAMONDS)
        );
        PokerHand h2 = new PokerHand(
            c(Rank.KING, Suit.CLUBS),
            c(Rank.QUEEN, Suit.DIAMONDS),
            c(Rank.TEN, Suit.HEARTS),
            c(Rank.FIVE, Suit.SPADES),
            c(Rank.THREE, Suit.CLUBS)
        );
        assertEquals(HandRank.HIGH_CARD, h1.evaluateHandRank());
        assertTrue(h1.compareTo(h2) > 0);
    }
}
