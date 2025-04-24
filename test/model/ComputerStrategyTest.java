package model;

import org.junit.Test;
import java.util.*;
import static org.junit.Assert.*;

public class ComputerStrategyTest {
    @Test
    public void testEasyStrategyReturnsValidMove() { // Bassam: test EasyStrategy returns valid move
        ComputerStrategy strategy = new EasyStrategy(); // Bassam: use easy strategy
        List<Card> hand = Arrays.asList(new Card("Hearts", "A"), new Card("Clubs", "K"));
        String move = strategy.decideMove(hand, new ArrayList<>(), 100, 10, 100);
        assertTrue(Arrays.asList("fold", "call", "raise").contains(move)); // Bassam: move must be one of the valid options
    }

    @Test
    public void testHardStrategyRaisesWithThreeOfAKind() { // Bassam: test HardStrategy raises with three of a kind
        ComputerStrategy strategy = new HardStrategy();
        List<Card> hand = Arrays.asList(
            Card.getCard(null, null)
            new Card("Clubs", "A"),
            new Card("Diamonds", "A")
        );
        String move = strategy.decideMove(hand, new ArrayList<>(), 100, 10, 100);
        assertEquals("raise", move); // Bassam: should raise with three of a kind
    }

    @Test
    public void testHardStrategyCallsWithPair() { // Bassam: test HardStrategy calls with a pair
        ComputerStrategy strategy = new HardStrategy();
        List<Card> hand = Arrays.asList(
            new Card("Hearts", "A"),
            new Card("Clubs", "A"),
            new Card("Diamonds", "K")
        );
        String move = strategy.decideMove(hand, new ArrayList<>(), 100, 10, 100);
        assertEquals("call", move); // Bassam: should call with a pair
    }

    @Test
    public void testHardStrategyRandomOnNoPair() { // Bassam: test HardStrategy randomizes with no pair
        ComputerStrategy strategy = new HardStrategy();
        List<Card> hand = Arrays.asList(
            new Card("Hearts", "2"),
            new Card("Clubs", "5"),
            new Card("Diamonds", "K")
        );
        String move = strategy.decideMove(hand, new ArrayList<>(), 100, 10, 100);
        assertTrue(Arrays.asList("call", "fold").contains(move)); // Bassam: move must be call or fold when no pair
    }
}
