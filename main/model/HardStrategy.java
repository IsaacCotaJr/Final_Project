package model;

import java.util.List;

public class HardStrategy implements ComputerStrategy {
    @Override
    public String decideMove(List<Card> hand, List<Card> communityCards, double pot, double currentBet, double balance) {
        // Hard: call/raise with strong hands, fold with weak hands
        int pairs = countPairs(hand);
        boolean hasThree = hasThreeOfAKind(hand);
        if (hasThree || pairs >= 2) {
            return "raise";
        } else if (pairs == 1) {
            return "call";
        } else {
            return (Math.random() < 0.5) ? "call" : "fold";
        }
    }

    private int countPairs(List<Card> hand) {
        int[] ranks = new int[15];
        for (Card c : hand) {
            ranks[c.getRank().getValue()]++;
        }
        int pairs = 0;
        for (int count : ranks) {
            if (count == 2) pairs++;
        }
        return pairs;
    }

    private boolean hasThreeOfAKind(List<Card> hand) {
        int[] ranks = new int[15];
        for (Card c : hand) {
            ranks[c.getRank().getValue()]++;
        }
        for (int count : ranks) {
            if (count == 3) return true;
        }
        return false;
    }
}
