package model;

public class ComputerPlayer extends Player {
    private ComputerStrategy strategy;

    public ComputerPlayer(double balance, ComputerStrategy strategy) {
        super(balance, false); // not human
        this.strategy = strategy;
    }

    public void setStrategy(ComputerStrategy strategy) {
        this.strategy = strategy;
    }

    public String decideMove(double pot, double currentBet) {
        return strategy.decideMove(getHand(), pot, currentBet, getBalance());
    }
}
