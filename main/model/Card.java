package model;

import java.util.HashMap;
import java.util.Map;

public class Card implements Comparable<Card> {

	private final Rank rank;
	private final Suit suit;

	public Card(Rank rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
	}

	@Override
	public int compareTo(Card other) {
		return this.rank.compareTo(other.rank);

	}

	public boolean equals(Card other) {
		return rank == other.rank;
	}

	public Rank getRank() {
		// TODO Auto-generated method stub
		return rank;
	}

	public Suit getSuit() {
		// TODO Auto-generated method stub
		return suit;
	}

	public Object getValue() {
		// TODO Auto-generated method stub
		return (Integer) rank.getValue();
	}

	private static final Map<Suit, String> suitSymbols = new HashMap<>();
	static {
		suitSymbols.put(Suit.CLUBS, "\u2663");
		suitSymbols.put(Suit.DIAMONDS, "\u2666");
		suitSymbols.put(Suit.HEARTS, "\u2665");
		suitSymbols.put(Suit.SPADES, "\u2660");
	}

	public String toString() {
		// Use unicode values in the starter code to show the icons for the four suits.
		// For example '\u2663' prints as ♣
		String suitSymbol = suitSymbols.getOrDefault(suit, "");
		return rank.getValue() + suitSymbol;

	}
}