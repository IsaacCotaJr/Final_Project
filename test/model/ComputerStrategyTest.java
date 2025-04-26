package model;

import org.junit.Test;
import java.util.*;
import static org.junit.Assert.*;

public class ComputerStrategyTest {
    @Test
    public void testEasyStrategyReturnsValidMove() { // Bassam: test EasyStrategy returns valid move
        ComputerStrategy strategy = new EasyStrategy(); // Bassam: use easy strategy
        ArrayList<Card> hand = new ArrayList<Card>();
        hand.add(Card.getCard(Rank.ACE, Suit.HEARTS));
        hand.add(Card.getCard(Rank.KING, Suit.CLUBS));
        String move = strategy.decideMove(hand, 100, 10, 100);
        assertTrue(Arrays.asList("fold", "call", "raise").contains(move)); // Bassam: move must be one of the valid options
    }

    @Test
    public void testHardStrategyRaisesWithThreeOfAKind() { // Bassam: test HardStrategy raises with three of a kind
        ComputerStrategy strategy = new HardStrategy();
        ArrayList<Card> hand = new ArrayList<Card>();
        hand.add(Card.getCard(Rank.ACE, Suit.HEARTS));
        hand.add(Card.getCard(Rank.ACE, Suit.DIAMONDS));
        hand.add(Card.getCard(Rank.ACE, Suit.SPADES));
            
        String move = strategy.decideMove(hand, 100, 10, 100);
        assertEquals("raise", move); // Bassam: should raise with three of a kind
    }

    @Test
    public void testHardStrategyCallsWithPair() { // Bassam: test HardStrategy calls with a pair
        ComputerStrategy strategy = new HardStrategy();
        List<Card> hand = new ArrayList<Card>();
        hand.add(Card.getCard(Rank.ACE, Suit.HEARTS));
        hand.add(Card.getCard(Rank.ACE, Suit.DIAMONDS));
        hand.add(Card.getCard(Rank.KING, Suit.SPADES));
        
        String move = strategy.decideMove(hand, 100, 10, 100);
        assertEquals("call", move); // Bassam: should call with a pair
    }

    @Test
    public void testHardStrategyRandomOnNoPair() { // Bassam: test HardStrategy randomizes with no pair
        ComputerStrategy strategy = new HardStrategy();
        List<Card> hand = new ArrayList<Card>();
        hand.add(Card.getCard(Rank.DEUCE, Suit.HEARTS));
        hand.add(Card.getCard(Rank.KING, Suit.DIAMONDS));
        hand.add(Card.getCard(Rank.FIVE, Suit.CLUBS));
        
        String move = strategy.decideMove(hand, 100, 10, 100);
        assertTrue(Arrays.asList("call", "fold").contains(move)); // Bassam: move must be call or fold when no pair
    }
}
