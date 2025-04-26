package model;

import java.util.List;

public class EasyStrategy implements ComputerStrategy {
    @Override
    public String decideMove(List<Card> hand, double pot, double currentBet, double balance) {
        // Easy: randomly fold, call, or raise (with small raise)
        double rand = Math.random();
        if (rand < 0.4) return "call";
        else if (rand < 0.7) return "fold";
        else return "raise";
    }
}
