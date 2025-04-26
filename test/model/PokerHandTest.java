package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class PokerHandTest {

    private Card c(Rank rank, Suit suit) {
        return Card.getCard(rank, suit);
    }

    @Test
    public void testToString() {
        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
            c(Rank.ACE, Suit.SPADES),
            c(Rank.KING, Suit.HEARTS),
            c(Rank.QUEEN, Suit.CLUBS),
            c(Rank.JACK, Suit.DIAMONDS),
            c(Rank.TEN, Suit.SPADES)
        ));
        PokerHand hand = new PokerHand(cards);
        String repr = hand.toString();
        assertTrue(repr.contains("14") || repr.contains("A"));
    }

    @Test
    public void testRoyalFlush() {
        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
            c(Rank.TEN, Suit.HEARTS),
            c(Rank.JACK, Suit.HEARTS),
            c(Rank.QUEEN, Suit.HEARTS),
            c(Rank.KING, Suit.HEARTS),
            c(Rank.ACE, Suit.HEARTS)
        ));
        PokerHand rf = new PokerHand(cards);
        assertEquals(HandRank.ROYAL_FLUSH, rf.evaluateHandRank());
        assertEquals(0, rf.compareTo(rf));
    }

    @Test
    public void testStraightFlushComparison() {
        ArrayList<Card> lowList = new ArrayList<>(Arrays.asList(
            c(Rank.FIVE, Suit.CLUBS),
            c(Rank.SIX, Suit.CLUBS),
            c(Rank.SEVEN, Suit.CLUBS),
            c(Rank.EIGHT, Suit.CLUBS),
            c(Rank.NINE, Suit.CLUBS)
        ));
        ArrayList<Card> highList = new ArrayList<>(Arrays.asList(
            c(Rank.SIX, Suit.DIAMONDS),
            c(Rank.SEVEN, Suit.DIAMONDS),
            c(Rank.EIGHT, Suit.DIAMONDS),
            c(Rank.NINE, Suit.DIAMONDS),
            c(Rank.TEN, Suit.DIAMONDS)
        ));
        PokerHand lowSF = new PokerHand(lowList);
        PokerHand highSF = new PokerHand(highList);
        assertEquals(HandRank.STRAIGHT_FLUSH, lowSF.evaluateHandRank());
        assertTrue(highSF.compareTo(lowSF) > 0);
        assertTrue(lowSF.compareTo(highSF) < 0);
    }

    @Test
    public void testFourOfAKindAndKicker() {
        ArrayList<Card> list2 = new ArrayList<>(Arrays.asList(
            c(Rank.DEUCE, Suit.HEARTS),
            c(Rank.DEUCE, Suit.SPADES),
            c(Rank.DEUCE, Suit.CLUBS),
            c(Rank.DEUCE, Suit.DIAMONDS),
            c(Rank.ACE, Suit.HEARTS)
        ));
        ArrayList<Card> list3 = new ArrayList<>(Arrays.asList(
            c(Rank.THREE, Suit.HEARTS),
            c(Rank.THREE, Suit.SPADES),
            c(Rank.THREE, Suit.CLUBS),
            c(Rank.THREE, Suit.DIAMONDS),
            c(Rank.DEUCE, Suit.HEARTS)
        ));
        PokerHand four2 = new PokerHand(list2);
        PokerHand four3 = new PokerHand(list3);
        assertEquals(HandRank.FOUR_OF_A_KIND, four2.evaluateHandRank());
        assertTrue(four3.compareTo(four2) > 0);

        ArrayList<Card> kA = new ArrayList<>(Arrays.asList(
            c(Rank.DEUCE, Suit.HEARTS),
            c(Rank.DEUCE, Suit.SPADES),
            c(Rank.DEUCE, Suit.CLUBS),
            c(Rank.DEUCE, Suit.DIAMONDS),
            c(Rank.ACE, Suit.DIAMONDS)
        ));
        ArrayList<Card> kK = new ArrayList<>(Arrays.asList(
            c(Rank.DEUCE, Suit.HEARTS),
            c(Rank.DEUCE, Suit.SPADES),
            c(Rank.DEUCE, Suit.CLUBS),
            c(Rank.DEUCE, Suit.DIAMONDS),
            c(Rank.KING, Suit.SPADES)
        ));
        PokerHand four2kA = new PokerHand(kA);
        PokerHand four2kK = new PokerHand(kK);
        assertTrue(four2kA.compareTo(four2kK) > 0);
    }

    @Test
    public void testFullHouse() {
        ArrayList<Card> fhList1 = new ArrayList<>(Arrays.asList(
            c(Rank.SEVEN, Suit.HEARTS),
            c(Rank.SEVEN, Suit.SPADES),
            c(Rank.SEVEN, Suit.CLUBS),
            c(Rank.FIVE, Suit.HEARTS),
            c(Rank.FIVE, Suit.SPADES)
        ));
        ArrayList<Card> fhList2 = new ArrayList<>(Arrays.asList(
            c(Rank.SIX, Suit.HEARTS),
            c(Rank.SIX, Suit.SPADES),
            c(Rank.SIX, Suit.CLUBS),
            c(Rank.ACE, Suit.HEARTS),
            c(Rank.ACE, Suit.SPADES)
        ));
        PokerHand fh1 = new PokerHand(fhList1);
        PokerHand fh2 = new PokerHand(fhList2);
        assertEquals(HandRank.FULL_HOUSE, fh1.evaluateHandRank());
        assertTrue(fh1.compareTo(fh2) > 0);

        ArrayList<Card> fhList3 = new ArrayList<>(Arrays.asList(
            c(Rank.SEVEN, Suit.HEARTS),
            c(Rank.SEVEN, Suit.SPADES),
            c(Rank.SEVEN, Suit.CLUBS),
            c(Rank.KING, Suit.HEARTS),
            c(Rank.KING, Suit.SPADES)
        ));
        PokerHand fh3 = new PokerHand(fhList3);
        assertTrue(fh3.compareTo(fh1) > 0);
    }

    @Test
    public void testFlushAndStraightComparison() {
        ArrayList<Card> flushList1 = new ArrayList<>(Arrays.asList(
            c(Rank.ACE, Suit.CLUBS),
            c(Rank.KING, Suit.CLUBS),
            c(Rank.EIGHT, Suit.CLUBS),
            c(Rank.FOUR, Suit.CLUBS),
            c(Rank.DEUCE, Suit.CLUBS)
        ));
        ArrayList<Card> flushList2 = new ArrayList<>(Arrays.asList(
            c(Rank.ACE, Suit.DIAMONDS),
            c(Rank.QUEEN, Suit.DIAMONDS),
            c(Rank.NINE, Suit.DIAMONDS),
            c(Rank.FIVE, Suit.DIAMONDS),
            c(Rank.THREE, Suit.DIAMONDS)
        ));
        PokerHand flush1 = new PokerHand(flushList1);
        PokerHand flush2 = new PokerHand(flushList2);
        assertEquals(HandRank.FLUSH, flush1.evaluateHandRank());
        assertTrue(flush1.compareTo(flush2) > 0);

        ArrayList<Card> strList1 = new ArrayList<>(Arrays.asList(
            c(Rank.FIVE, Suit.HEARTS),
            c(Rank.SIX, Suit.SPADES),
            c(Rank.SEVEN, Suit.HEARTS),
            c(Rank.EIGHT, Suit.SPADES),
            c(Rank.NINE, Suit.HEARTS)
        ));
        ArrayList<Card> strList2 = new ArrayList<>(Arrays.asList(
            c(Rank.FOUR, Suit.CLUBS),
            c(Rank.FIVE, Suit.HEARTS),
            c(Rank.SIX, Suit.CLUBS),
            c(Rank.SEVEN, Suit.SPADES),
            c(Rank.EIGHT, Suit.CLUBS)
        ));
        PokerHand straight1 = new PokerHand(strList1);
        PokerHand straight2 = new PokerHand(strList2);
        assertEquals(HandRank.STRAIGHT, straight1.evaluateHandRank());
        assertTrue(straight1.compareTo(straight2) > 0);
    }

    @Test
    public void testThreeOfAKind() {
        ArrayList<Card> threeList1 = new ArrayList<>(Arrays.asList(
            c(Rank.FIVE, Suit.HEARTS),
            c(Rank.FIVE, Suit.SPADES),
            c(Rank.FIVE, Suit.CLUBS),
            c(Rank.ACE, Suit.HEARTS),
            c(Rank.KING, Suit.DIAMONDS)
        ));
        ArrayList<Card> threeList2 = new ArrayList<>(Arrays.asList(
            c(Rank.FOUR, Suit.HEARTS),
            c(Rank.FOUR, Suit.SPADES),
            c(Rank.FOUR, Suit.CLUBS),
            c(Rank.ACE, Suit.CLUBS),
            c(Rank.KING, Suit.HEARTS)
        ));
        PokerHand threeA5 = new PokerHand(threeList1);
        PokerHand threeA4 = new PokerHand(threeList2);
        assertEquals(HandRank.THREE_OF_A_KIND, threeA5.evaluateHandRank());
        assertTrue(threeA5.compareTo(threeA4) > 0);

        ArrayList<Card> k2 = new ArrayList<>(Arrays.asList(
            c(Rank.FIVE, Suit.HEARTS),
            c(Rank.FIVE, Suit.SPADES),
            c(Rank.FIVE, Suit.CLUBS),
            c(Rank.DEUCE, Suit.HEARTS),
            c(Rank.THREE, Suit.DIAMONDS)
        ));
        ArrayList<Card> k3 = new ArrayList<>(Arrays.asList(
            c(Rank.FIVE, Suit.HEARTS),
            c(Rank.FIVE, Suit.SPADES),
            c(Rank.FIVE, Suit.CLUBS),
            c(Rank.THREE, Suit.CLUBS),
            c(Rank.FOUR, Suit.SPADES)
        ));
        PokerHand threeA5k2 = new PokerHand(k2);
        PokerHand threeA5k3 = new PokerHand(k3);
        assertTrue(threeA5k3.compareTo(threeA5k2) > 0);
    }

    @Test
    public void testTwoPair() {
        ArrayList<Card> tpList1 = new ArrayList<>(Arrays.asList(
            c(Rank.KING, Suit.HEARTS),
            c(Rank.KING, Suit.DIAMONDS),
            c(Rank.QUEEN, Suit.HEARTS),
            c(Rank.QUEEN, Suit.SPADES),
            c(Rank.ACE, Suit.CLUBS)
        ));
        ArrayList<Card> tpList2 = new ArrayList<>(Arrays.asList(
            c(Rank.JACK, Suit.CLUBS),
            c(Rank.JACK, Suit.SPADES),
            c(Rank.TEN, Suit.CLUBS),
            c(Rank.TEN, Suit.DIAMONDS),
            c(Rank.ACE, Suit.DIAMONDS)
        ));
        PokerHand tp1 = new PokerHand(tpList1);
        PokerHand tp2 = new PokerHand(tpList2);
        assertEquals(HandRank.TWO_PAIR, tp1.evaluateHandRank());
        assertTrue(tp1.compareTo(tp2) > 0);

        ArrayList<Card> tpList1k9 = new ArrayList<>(Arrays.asList(
            c(Rank.KING, Suit.HEARTS),
            c(Rank.KING, Suit.DIAMONDS),
            c(Rank.QUEEN, Suit.HEARTS),
            c(Rank.QUEEN, Suit.SPADES),
            c(Rank.NINE, Suit.CLUBS)
        ));
        PokerHand tp1k9 = new PokerHand(tpList1k9);
        assertTrue(tp1.compareTo(tp1k9) > 0);
    }

    @Test
    public void testOnePair() {
        ArrayList<Card> pList1 = new ArrayList<>(Arrays.asList(
            c(Rank.ACE, Suit.HEARTS),
            c(Rank.ACE, Suit.SPADES),
            c(Rank.KING, Suit.CLUBS),
            c(Rank.QUEEN, Suit.HEARTS),
            c(Rank.JACK, Suit.DIAMONDS)
        ));
        ArrayList<Card> pList2 = new ArrayList<>(Arrays.asList(
            c(Rank.KING, Suit.HEARTS),
            c(Rank.KING, Suit.SPADES),
            c(Rank.ACE, Suit.CLUBS),
            c(Rank.QUEEN, Suit.SPADES),
            c(Rank.JACK, Suit.CLUBS)
        ));
        PokerHand p1 = new PokerHand(pList1);
        PokerHand p2 = new PokerHand(pList2);
        assertEquals(HandRank.PAIR, p1.evaluateHandRank());
        assertTrue(p1.compareTo(p2) > 0);
    }

    @Test
    public void testHighCard() {
        ArrayList<Card> hList1 = new ArrayList<>(Arrays.asList(
            c(Rank.ACE, Suit.HEARTS),
            c(Rank.KING, Suit.SPADES),
            c(Rank.NINE, Suit.CLUBS),
            c(Rank.FOUR, Suit.HEARTS),
            c(Rank.DEUCE, Suit.DIAMONDS)
        ));
        ArrayList<Card> hList2 = new ArrayList<>(Arrays.asList(
            c(Rank.KING, Suit.CLUBS),
            c(Rank.QUEEN, Suit.DIAMONDS),
            c(Rank.TEN, Suit.HEARTS),
            c(Rank.FIVE, Suit.SPADES),
            c(Rank.THREE, Suit.CLUBS)
        ));
        PokerHand h1 = new PokerHand(hList1);
        PokerHand h2 = new PokerHand(hList2);
        assertEquals(HandRank.HIGH_CARD, h1.evaluateHandRank());
        assertTrue(h1.compareTo(h2) > 0);
    }
}
