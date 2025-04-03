
package model;

import java.util.*;

public class Deck {

	private final List<Card> cards;

	public Deck() {
		cards = new ArrayList<>();
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				cards.add(new Card(rank, suit));
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
		// TODO Auto-generated method stub
		List<Card> playerCards = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			playerCards.add(cards.remove(0));
		}
		return playerCards;
	}

}
