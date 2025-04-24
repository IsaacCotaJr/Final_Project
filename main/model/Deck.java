/* Updated Deck.java to use Card.getCard */
package model;

import java.util.*;

public class Deck {

	private final List<Card> cards;

	public Deck() {
		cards = new ArrayList<>();
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				cards.add(Card.getCard(rank, suit)); // Use flyweight access method
			}
		}
	}

	public List<Card> getDeck() {
		return cards;
	}

	public List<Card> shuffle() {
		Collections.shuffle(cards);
		return cards;
	}

	public List<Card> dealPlayerCards() {
		List<Card> playerCards = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			playerCards.add(cards.remove(0));
		}
		return playerCards;
	}

	public Card draw() {
		return cards.remove(0);
	}
} 
