package model;

import java.util.List;

public interface ComputerStrategy {
    /**
     * Decide the move for the computer player.
     * @param hand The computer's current hand
     * @param communityCards Community cards (if any, for Texas Hold'em; can be empty for 5-card draw)
     * @param pot The current pot size
     * @param currentBet The current bet to call
     * @param balance The computer's balance
     * @return String representing the move: "fold", "call", "raise"
     */
    String decideMove(List<Card> hand, List<Card> communityCards, double pot, double currentBet, double balance);
}
