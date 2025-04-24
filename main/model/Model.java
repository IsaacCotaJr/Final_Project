package model;

import java.util.ArrayList;
import java.util.List;

public class Model {
	// This will be where the game is, i.e. the Deck, Card, Hand, and other instances of classes will be here
	
	private ArrayList<Observer> observers;
	private Deck deck;
	private User user;
	private double betAmount;
	private double userBal;
	private ArrayList<Player> players;
	
	public Model(User u) {
		this.user = u;
		this.observers = new ArrayList<Observer>();
		this.players = new ArrayList<Player>();
		for (int i = 0; i < 2; i++) {
			players.add(new Player(100.00, false));
		}
		players.add(new Player(100.00, true));
		this.deck = new Deck();
	}

	public void addComputerPlayer(int index, ComputerPlayer cPlayer) {
		players.set(index, cPlayer);
	}
	
	public void registerObserver(Observer observer) {
		this.observers.add(observer);
	}
	
	public void deregisterObserver(Observer observer) {
		this.observers.remove(observer);
	}
	
	// isaac
	public void shuffleAndDeal() {
		// shuffle deck, deal cards, notify observers
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
			
			// if last five cards, are player cards, set visible
			if (i >= 0) { // change to 10, seeing comp hands now
				cl.showFace();
			}
		}
		notifyObservers();
		
	}
	
	public ArrayList<Card> drawNewCards(boolean[] toReplace) {
	    ArrayList<Card> newCards = new ArrayList<>();
	    Player user = players.get(players.size() - 1); // user is last
	    List<Card> hand = user.getHand();

	    for (int i = 0; i < 5; i++) {
	    	int a  = i + 10;
	        if (toReplace[i]) {
	        	CardLabel cl = (CardLabel) observers.get(a);
	            Card newCard = deck.draw();
	            cl.setCardType(newCard);
	            newCards.add(newCard);
	            hand.set(i, newCard); // replace in player's hand
	        }
	    }

	    user.setHand(hand); // update hand
	    notifyObservers();
	    return newCards;
	}

	// for testing
		public ArrayList<Observer> getObservers(){
			return this.observers;
		}
		public ArrayList<Player> getPlayers(){
			return this.players;
		}
	// isaac
	
	/* PRIVATE METHODS */
	private void notifyObservers() {
		for(Observer o : observers) {
			o.update(betAmount, userBal);
		}
	}
}
