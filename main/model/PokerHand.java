
package model;

import java.util.*;

/*
 * When sorted, a list of PokerHand objects will be in ascending order.
 */

public class PokerHand implements Comparable<PokerHand> {

	private final List<Card> cards;

	public PokerHand(Card c1, Card c2, Card c3, Card c4, Card c5) {
		// TODO: Build class PokerHand, a week long project
		this.cards = Arrays.asList(c1, c2, c3, c4, c5);

	}

	@Override
	public String toString() {
		StringBuilder output = new StringBuilder();
		for (Card card : cards) {
			output.append(card).append(" ");
		}

		return output.toString().trim();

	}

	@Override
	public int compareTo(PokerHand o) {
		// TODO Auto-generated method stub
		HandRank thisHandRank = evaluateHandRank();
		HandRank otherHandRank = o.evaluateHandRank();

		if (thisHandRank.ordinal() > otherHandRank.ordinal()) {
			return 1;
		} else if (thisHandRank.ordinal() < otherHandRank.ordinal()) {
			return -1;
		} else {
			if (thisHandRank == HandRank.ROYAL_FLUSH) {
				// Ends in a tie
				return 0;
			} else if (thisHandRank == HandRank.STRAIGHT_FLUSH) {
				Rank thisTop = getHighest();
				Rank otherTop = o.getHighest();

				return thisTop.compareTo(otherTop);
			} else if (thisHandRank == HandRank.FOUR_OF_A_KIND) {

				Rank thisFourKindRank = findFourOfAKindRank();
				Rank otherFourKindRank = o.findFourOfAKindRank();

				int fourOfAKindComparison = thisFourKindRank.compareTo(otherFourKindRank);
				if (fourOfAKindComparison != 0) {
					return fourOfAKindComparison;
				} else {

					return compareKickers(o);
				}
			} else if (thisHandRank == HandRank.FULL_HOUSE) {
				Rank thisTripsRank = findTripsRank();
				Rank otherTripsRank = o.findTripsRank();

				int tripsComparison = thisTripsRank.compareTo(otherTripsRank);
				if (tripsComparison != 0) {
					return tripsComparison;
				}

				Rank thisPairRank = findPairRank();
				Rank otherPairRank = o.findPairRank();

				return thisPairRank.compareTo(otherPairRank);
			} else if (thisHandRank == HandRank.FLUSH || thisHandRank == HandRank.STRAIGHT) {
				Rank thisTop = getHighest();
				Rank otherTop = o.getHighest();

				int topComparison = thisTop.compareTo(otherTop);

				if (topComparison != 0) {
					return topComparison;
				}

				return findHighestCard(o);

			} else if (thisHandRank == HandRank.THREE_OF_A_KIND) {
				Rank thisTrips = findTripsRank();
				Rank otherTrips = o.findTripsRank();

				int tripsComparison = thisTrips.compareTo(otherTrips);
				if (tripsComparison != 0) {
					return tripsComparison;
				}

				Rank thisKicker = findHigherKickerThreeKind();
				Rank otherKicker = o.findHigherKickerThreeKind();

				int kickerComparison = thisKicker.compareTo(otherKicker);
				if (kickerComparison != 0) {
					return kickerComparison;
				}
				return findHighestCard(o);

			} else if (thisHandRank == HandRank.TWO_PAIR) {
				Rank[] thisPairs = findTwoPairs();
				Rank[] otherPairs = o.findTwoPairs();

				int higherPairComparison = thisPairs[1].compareTo(otherPairs[1]);
				if (higherPairComparison != 0) {
					return higherPairComparison;
				}

				int lowerPairComparison = thisPairs[0].compareTo(otherPairs[0]);
				if (lowerPairComparison != 0) {
					return lowerPairComparison;
				}

				return compareKickers(o);

			} else if (thisHandRank == HandRank.PAIR) {
				// Put hands in order high to low
				// Work way down until tie broken
				// Highest non-tie card wins

				Rank thisPairRank = findPairRank();
				Rank otherPairRank = o.findPairRank();
				int pairComparison = thisPairRank.compareTo(otherPairRank);
				if (pairComparison != 0) {
					return pairComparison;
				}

				for (int i = 0; i < cards.size(); i++) {
					Rank thisRank = (Rank) cards.get(i).getRank();
					Rank otherRank = (Rank) o.cards.get(i).getRank();

					int comparison = thisRank.compareTo(otherRank);

					if (comparison != 0) {
						return comparison;
					}
				}
				return 0;

			} else {
				return findHighestCard(o);
			}
		}

	}

	private int findHighestCard(PokerHand o) {
		// Put hands in order high to low
		// Work way down until tie broken
		// Highest non-tie card wins
		Collections.sort(cards, Collections.reverseOrder());
		Collections.sort(o.cards, Collections.reverseOrder());

		for (int i = 0; i < cards.size(); i++) {
			Rank thisRank = (Rank) cards.get(i).getRank();
			Rank otherRank = (Rank) o.cards.get(i).getRank();

			int comparison = thisRank.compareTo(otherRank);

			if (comparison != 0) {
				return comparison;
			}
		}
		return 0;

	}

	private Rank findHigherKickerThreeKind() {
		// TODO Auto-generated method stub
		Map<Rank, Integer> rankCount = new HashMap<>();
		for (Card card : cards) {
			Rank rank = (Rank) card.getRank();
			rankCount.put(rank, rankCount.getOrDefault(rank, 0) + 1);
		}

		Rank threeOfAKind = null;
		for (Rank rank : rankCount.keySet()) {
			if (rankCount.get(rank) == 3) {
				threeOfAKind = rank;
				break;
			}

		}
		List<Rank> kickers = new ArrayList<>();
		for (Card card : cards) {
			Rank rank = (Rank) card.getRank();
			if (rank != threeOfAKind) {
				kickers.add(rank);
			}
		}

		Collections.sort(kickers, Collections.reverseOrder());
		return kickers.get(0);

	}

	private Rank findPairRank() {
		// TODO Auto-generated method stub
		int[] rankCount = new int[Rank.values().length];
		for (Card card : cards) {
			Rank rank = (Rank) card.getRank();
			rankCount[rank.ordinal()]++;
		}

		for (int i = 0; i < rankCount.length; i++) {
			if (rankCount[i] == 2) {
				return Rank.values()[i];
			}
		}
		return null;
	}

	private Rank getHighest() {
		// TODO Auto-generated method stub
		Collections.sort(cards);
		return (Rank) cards.get(cards.size() - 1).getRank();
	}

	private Rank findFourOfAKindRank() {
		// TODO Auto-generated method stub
		Collections.sort(cards);
		for (int i = 0; i < cards.size() - 3; i++) {
			if (cards.get(i).getRank() == cards.get(i + 1).getRank()
					&& cards.get(i).getRank() == cards.get(i + 2).getRank()
					&& cards.get(i).getRank() == cards.get(i + 3).getRank()) {
				return (Rank) cards.get(i).getRank();
			}
		}
		return null;
	}

	private Rank findTripsRank() {
		// TODO Auto-generated method stub
		for (int i = 0; i < cards.size() - 2; i++) {
			if (cards.get(i).getRank() == cards.get(i + 1).getRank()
					&& cards.get(i).getRank() == cards.get(i + 2).getRank()) {
				return (Rank) cards.get(i).getRank();
			}
		}
		return null;
	}

	private int compareKickers(PokerHand o) {
		// TODO Auto-generated method stub
		List<Rank> thisKickers = getKickers();
		List<Rank> otherKickers = o.getKickers();

		for (int i = 0; i < thisKickers.size(); i++) {
			int kickerComparison = thisKickers.get(i).compareTo(otherKickers.get(i));
			if (kickerComparison != 0) {
				return kickerComparison;
			}
		}
		return 0;
	}

	private List<Rank> getKickers() {
		// TODO Auto-generated method stub
		List<Rank> kickers = new ArrayList<>();
		Rank[] pairs = findTwoPairs();

		for (Card card : cards) {
			if (card.getRank() != pairs[0] && card.getRank() != pairs[1]) {
				kickers.add((Rank) card.getRank());
			}
		}
		Collections.sort(kickers, Collections.reverseOrder());

		return kickers;
	}

	private Rank[] findTwoPairs() {
		// TODO Auto-generated method stub
		Rank[] pairs = new Rank[2];
		int count = 0;
		for (int i = 0; i < cards.size() - 1; i++) {
			if (cards.get(i).getRank() == cards.get(i + 1).getRank()) {
				pairs[count++] = (Rank) cards.get(i).getRank();
				i++;
			}
		}
		return pairs;
	}

	public HandRank evaluateHandRank() {
		// TODO Auto-generated method stub
		if (isRoyalFlush()) {
			return HandRank.ROYAL_FLUSH;
		} else if (isStraightFlush()) {
			return HandRank.STRAIGHT_FLUSH;
		} else if (isFourOfAKind()) {
			return HandRank.FOUR_OF_A_KIND;
		} else if (isFullHouse()) {
			return HandRank.FULL_HOUSE;
		} else if (isFlush()) {
			return HandRank.FLUSH;
		} else if (isStraight()) {
			return HandRank.STRAIGHT;
		} else if (isThreeOfAKind()) {
			return HandRank.THREE_OF_A_KIND;
		} else if (isTwoPair()) {
			return HandRank.TWO_PAIR;
		} else if (isPair()) {
			return HandRank.PAIR;
		} else {
			return HandRank.HIGH_CARD;
		}
	}

	private boolean isPair() {
		// TODO Auto-generated method stub
		for (int i = 0; i < cards.size() - 1; i++) {
			for (int j = i + 1; j < cards.size(); j++) {
				if (cards.get(i).getRank() == cards.get(j).getRank()) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isTwoPair() {
		// TODO Auto-generated method stub
		int count = 0;
		for (int i = 0; i < cards.size() - 1; i++) {
			for (int j = i + 1; j < cards.size(); j++) {
				if (cards.get(i).getRank() == cards.get(j).getRank()) {
					count++;

				}
			}
		}

		return (count == 2);
	}

	private boolean isThreeOfAKind() {
		// TODO Auto-generated method stub
		for (int i = 0; i < cards.size() - 2; i++) {
			for (int j = i + 1; j < cards.size() - 1; j++) {
				for (int k = j + 1; k < cards.size(); k++) {
					if (cards.get(i).getRank() == cards.get(j).getRank()
							&& cards.get(j).getRank() == cards.get(k).getRank()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean isStraight() {
		// TODO Auto-generated method stub
		Collections.sort(cards);

		// Wheel
		if (cards.get(0).getRank() == Rank.DEUCE && cards.get(1).getRank() == Rank.THREE
				&& cards.get(2).getRank() == Rank.THREE && cards.get(3).getRank() == Rank.FOUR
				&& cards.get(4).getRank() == Rank.FIVE && cards.get(5).getRank() == Rank.ACE) {
			return true;
		}

		for (int i = 0; i < cards.size() - 1; i++) {
			if ((Integer) cards.get(i + 1).getValue() - (Integer) cards.get(i).getValue() != 1) {
				return false;
			}
		}
		return true;
	}

	private boolean isFlush() {
		// TODO Auto-generated method stub
		Suit firstSuit = (Suit) cards.get(0).getSuit();

		for (int i = 1; i < cards.size(); i++) {
			if (cards.get(i).getSuit() != firstSuit) {
				return false;
			}
		}
		return true;
	}

	private boolean isFullHouse() {
		// TODO Auto-generated method stub
		Map<Rank, Integer> rankCount = new HashMap<>();
		for (Card card : cards) {
			Rank rank = (Rank) card.getRank();
			rankCount.put(rank, rankCount.getOrDefault(rank, 0) + 1);
		}

		boolean threeOfAKind = false;
		boolean pair = false;

		for (int count : rankCount.values()) {
			if (count == 3) {
				threeOfAKind = true;
			} else if (count == 2) {
				pair = true;
			}
		}
		return threeOfAKind && pair;

	}

	private boolean isFourOfAKind() {
		// TODO Auto-generated method stub
		for (int i = 0; i < cards.size() - 3; i++) {
			for (int j = i + 1; j < cards.size() - 2; j++) {
				for (int k = j + 1; k < cards.size() - 1; k++) {
					for (int l = k + 1; l < cards.size(); l++) {
						if (cards.get(i).getRank() == cards.get(j).getRank()
								&& cards.get(j).getRank() == cards.get(k).getRank()
								&& cards.get(k).getRank() == cards.get(l).getRank()) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private boolean isStraightFlush() {
		// TODO Auto-generated method stub
		return isStraight() && isFlush();
	}

	private boolean isRoyalFlush() {
		// TODO Auto-generated method stub
		if (!isStraightFlush()) {
			return false;
		}

		Rank firstCard = (Rank) cards.get(0).getRank();
		Rank lastCard = (Rank) cards.get(4).getRank();

		return firstCard == Rank.TEN && lastCard == Rank.ACE;
	}

}