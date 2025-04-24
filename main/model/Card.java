/* Updated Card.java using Flyweight Pattern */
package model;

import java.util.HashMap;
import java.util.Map;

public class Card implements Comparable<Card> {

	private final Rank rank;
	private final Suit suit;

	// Flyweight store
	private static final Card[][] cardPool = new Card[Rank.values().length][Suit.values().length];

	// Private constructor for flyweight
	private Card(Rank rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
	}

	// Static initializer block to populate card pool
	static {
		for (Rank rank : Rank.values()) {
			for (Suit suit : Suit.values()) {
				cardPool[rank.ordinal()][suit.ordinal()] = new Card(rank, suit);
			}
		}
	}

	// Access method
	public static Card getCard(Rank rank, Suit suit) {
		return cardPool[rank.ordinal()][suit.ordinal()];
	}

	@Override
	public int compareTo(Card other) {
		return this.rank.compareTo(other.rank);
	}

	public boolean equals(Card other) {
		return rank == other.rank && suit == other.suit;
	}

	public Rank getRank() {
		return rank;
	}

	public Suit getSuit() {
		return suit;
	}

	public Object getValue() {
		return (Integer) rank.getValue();
	}

	private static final Map<Suit, String> suitSymbols = new HashMap<>();
	static {
		suitSymbols.put(Suit.CLUBS, "\u2663");
		suitSymbols.put(Suit.DIAMONDS, "\u2666");
		suitSymbols.put(Suit.HEARTS, "\u2665");
		suitSymbols.put(Suit.SPADES, "\u2660");
	}

	@Override
	public String toString() {
		String suitSymbol = suitSymbols.getOrDefault(suit, "");
		return rank.getValue() + suitSymbol;
	}
}