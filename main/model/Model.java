package model;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private ArrayList<Observer> observers;
    private Deck deck;
    private User user;
    private double betAmount;
    private double userBal;
    private ArrayList<Player> players;

    // New betting fields
    private int currentPlayerIndex;
    private double pot;
    private double currentBet;
    private boolean firstBettingRound;

    public Model(User u) {
        this.user = u;
        this.observers = new ArrayList<Observer>();
        this.players = new ArrayList<Player>();
        for (int i = 0; i < 2; i++) {
            players.add(new Player(100.00, false));
        }
        players.add(new Player(100.00, true));
        this.deck = new Deck();

        // Initialize betting state
        this.betAmount = 0;
        this.userBal   = players.get(players.size() - 1).getBalance();
        startBettingRound();
    }

    /*** New betting methods ***/
    public void startBettingRound() {
        this.currentPlayerIndex  = 0;
        this.pot                 = 0;
        this.currentBet          = 0;
        this.firstBettingRound   = true;
        this.betAmount           = currentBet;
        this.userBal             = players.get(players.size() - 1).getBalance();
        notifyObservers();
    }

    public void placeBet(String action, double amount) {
        Player p = players.get(currentPlayerIndex);
        switch (action) {
            case "fold":
                p.setFold(true);
                break;
            case "call":
                p.subFromBalance(currentBet);
                pot += currentBet;
                break;
            case "raise":
                if (amount <= currentBet) {
                    amount = currentBet + 1;
                }
                currentBet = amount;
                p.subFromBalance(amount);
                pot += amount;
                break;
            case "check":
                // no change to pot or balances
                break;
        }
        // update what observers see
        this.betAmount = currentBet;
        this.userBal   = players.get(players.size() - 1).getBalance();
        notifyObservers();
    }

    public void advanceTurn() {
        int total = players.size();
        currentPlayerIndex = (currentPlayerIndex + 1) % total;

        // skip any folded players
        for (int i = 0; i < total; i++) {
            if (!players.get(currentPlayerIndex).getFold()) {
                break;
            }
            currentPlayerIndex = (currentPlayerIndex + 1) % total;
        }

        // if we've wrapped back to index 0, end the current betting round
        if (currentPlayerIndex == 0) {
            if (firstBettingRound) {
                firstBettingRound = false;
            }
            // else: both betting rounds are done
        }

        notifyObservers();
    }

    public boolean isFirstBettingRound() {
        return firstBettingRound;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public double getPot() {
        return pot;
    }

    public double getCurrentBet() {
        return currentBet;
    }
    /*** End new betting methods ***/

    public void addComputerPlayer(int index, ComputerPlayer cPlayer) {
        players.set(index, cPlayer);
    }

    public void registerObserver(Observer observer) {
        this.observers.add(observer);
    }

    public void deregisterObserver(Observer observer) {
        this.observers.remove(observer);
    }

    public void shuffleAndDeal() {
        deck.shuffle();
        ArrayList<Card> allPlayerCards = new ArrayList<Card>();
        for (Player player : players) {
            List<Card> hand = deck.dealPlayerCards();
            player.setHand(hand);
            allPlayerCards.addAll(hand);
        }
        for (int i = 0; i < 15; i++) {
            Card c = allPlayerCards.get(i);
            CardLabel cl = (CardLabel) observers.get(i);
            System.out.println(c);
            cl.setCardType(c);
            cl.showFace();
        }
        notifyObservers();
    }

    public ArrayList<Card> drawNewCards(boolean[] toReplace) {
        ArrayList<Card> newCards = new ArrayList<>();
        Player user = players.get(players.size() - 1);
        List<Card> hand = user.getHand();

        for (int i = 0; i < 5; i++) {
            int obsIndex = i + 10;
            if (toReplace[i]) {
                CardLabel cl = (CardLabel) observers.get(obsIndex);
                Card newCard = deck.draw();
                cl.setCardType(newCard);
                newCards.add(newCard);
                hand.set(i, newCard);
            }
        }

        user.setHand(hand);
        notifyObservers();
        return newCards;
    }

    // for testing
    public ArrayList<Observer> getObservers() {
        return this.observers;
    }
    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    /* PRIVATE METHODS */
    private void notifyObservers() {
        for (Observer o : observers) {
            o.update(betAmount, userBal);
        }
    }
}
