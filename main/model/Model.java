package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Model {
    private ArrayList<Observer> observers;
    private Deck deck;
    private User user;
    private double userBal;
    private ArrayList<Player> players;
    private Game g = new Game(); //used to determine best hands 
    private final HashMap<Player, PokerHand> playerHands = new HashMap<>();

    // New betting fields
    private double pot;
    private double currentBet;
    private boolean firstBettingRound;

    public Model(User u) {
        this.user = u;
        this.observers = new ArrayList<Observer>();
        this.players = new ArrayList<Player>();
        this.userBal = user.getBalance();

        players.add(new Player(userBal, true)); // add human player, comp players added later
        // Initialize betting state
        
        startBettingRound();
    }
    
    public void createCompPlayers(String diff) {
    	String selectedDifficulty = diff;
    	ComputerStrategy strategy = selectedDifficulty.equals("Easy") ? new EasyStrategy() : new HardStrategy(); // Bassam: choose strategy
    	for (int i = 0; i < 2; i++) {
            players.add(new ComputerPlayer(100.00, strategy));
        }
    }

    /*** New betting methods ***/
    public void startBettingRound() {
        this.pot                 = 0;
        this.currentBet          = 0;
        this.firstBettingRound   = true;
        this.userBal             = players.get(0).getBalance();
        notifyObservers();
    }

    public void placeBet(String action, double amount, int index) {
        Player p = players.get(index);
        switch (action) {
            case "fold":
                p.setFold(true);
                break;
            case "call":
                p.subFromBalance(this.currentBet);
                this.pot += this.currentBet;
                break;
            case "raise":
                if (amount < this.currentBet) {
                    amount = this.currentBet;
                }
                this.currentBet = amount;
                p.subFromBalance(amount);
                this.pot += amount;
                break;
            case "check":
                // no change to pot or balances
                break;
        }
        // update what observers see
        this.userBal = players.get(0).getBalance();
        notifyObservers();
    }

    public void advanceTurn() {
        int total = players.size();
        
//        // if we've wrapped back to index 0, end the current betting round
//        if (currentPlayerIndex == 0) {
//            if (firstBettingRound) {
//                firstBettingRound = false;
//            }
//            // else: both betting rounds are done
//        }

        // go through computer players
        for (int i = 1; i < total; i++) {
            if (!players.get(i).getFold()) {
            	ComputerPlayer p = (ComputerPlayer) players.get(i);
            	String s = p.decideMove(this.pot, this.currentBet);
            	placeBet(s, 10.0, i);
            }
            notifyObservers();
        }
    }
    /*** End new betting methods ***/

    public void registerObserver(Observer observer) {
        this.observers.add(observer);
    }

    public void deregisterObserver(Observer observer) {
        this.observers.remove(observer);
    }
    
    public void emptyObservers() {
    	ArrayList<Observer> toDeregister = new ArrayList<Observer>();
    	for (Observer o: observers) {
    		toDeregister.add(o);
    	}
    	for (Observer ob: toDeregister) {
    		deregisterObserver(ob);
    	}
    }

    public void resetGame() {
    	notifyObservers(); // not sure this does anything, just in case
    }
    
    public void shuffleAndDeal() {
    	this.deck = new Deck(); // so that a new deck is created every game
        deck.shuffle();
        ArrayList<Card> allPlayerCards = new ArrayList<Card>();
        for (Player player : players) {
            ArrayList<Card> hand = deck.dealPlayerCards();
            player.setHand(hand);
            allPlayerCards.addAll(hand);
        }
        for (int i = 0; i < 15; i++) {
            Card c = allPlayerCards.get(i);
            CardLabel cl = (CardLabel) observers.get(i);
            cl.setCardType(c);
            cl.showFace();
        }
        notifyObservers();
    }

    public ArrayList<Card> drawNewCards(boolean[] toReplace) {
        ArrayList<Card> newCards = new ArrayList<>();
        Player pl = players.get(0);
        ArrayList<Card> hand = pl.getHand();

        for (int i = 0; i < 5; i++) {
            if (toReplace[i]) {
                CardLabel cl = (CardLabel) observers.get(i);
                Card newCard = deck.draw();
                cl.setCardType(newCard);
                newCards.add(newCard);
                hand.set(i, newCard);
            }
        }

        pl.setHand(hand);
        notifyObservers();
        return newCards;
    }
    
    public String gameOver() {
    	String result = "";
    	ArrayList<Player> winners = new ArrayList<Player>();
    	// Go through players, see which had best hand
    	for (Player p : players) {
    		if (p.getFold()) {
    			continue;
    		}

    		PokerHand ph = new PokerHand(p.getHand());
    		playerHands.put(p, ph);
    	}
    	
    	winners = g.determineWinner(playerHands);
    	
    	// add winnings to balance
		for (Player win : winners) {
			win.addToBalance(pot/(winners.size()));
		}
		
		if (winners.contains(players.get(0))) {
			// human player won, return that they won
			// update user object's balance
			result = "won";
			user.setBalance(players.get(0).getBalance());
			user.saveUserToFile();
		}
		else {
			// human player lost, return that they won
			// update user object's balance
			result = "lost";
			user.setBalance(players.get(0).getBalance());
			user.saveUserToFile();
		}
    	return result;
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
            o.update(currentBet, userBal, pot);
        }
    }
}
