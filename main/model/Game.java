
package model;

import java.util.*;
import java.util.Map.Entry;

public class Game {

	private final Scanner scanner = new Scanner(System.in);
	private final List<Player> players = new ArrayList<>();
	private double pot = 0.0;
	private final Map<Player, PokerHand> playerBestHands = new HashMap<>();

	public void start() {
		// TODO Auto-generated method stub
		System.out.print("How many players? ");
		int numPlayers = scanner.nextInt();
		for (int i = 0; i < numPlayers; i++) {
			players.add(new Player(100.00));
		}
		System.out.println();

		boolean playAgain = true;
		while (playAgain) {
			collectAnte();
			playGame();

			System.out.println();
			System.out.print("Play another game? <Yes or No> ");
			String playResponse = scanner.next();
			System.out.println();
			if (playResponse.equalsIgnoreCase("yes")) {
				playAgain = true;
			} else {
				playAgain = false;
			}
		}
	}

	private void collectAnte() {
		// TODO Auto-generated method stub
		setPot(1.0 * players.size());
		for (Player player : players) {
			player.subFromBalance(1.0);
		}

	}

	private void playGame() {
		// TODO Auto-generated method stub
		Deck deck = new Deck();
		deck.shuffle();
		
		for (Player player : players) {
			player.setFold(false); // reset fold state
			player.setHand(deck.dealPlayerCards());
		}
		
		double currentBet = 1.0; // minimum bet
		int activePlayers = players.size();

		for (Player player : players) {
			List<Card> playerCards = deck.dealPlayerCards();
			player.setHand(playerCards);
		}
		
		// first round of betting
		boolean bettingComplete = false;
		while (!bettingComplete) {
			bettingComplete = true;
			for (Player player : players) {
				if (player.getFold()) continue;

				System.out.println("Player " + (players.indexOf(player) + 1));
				System.out.println("Your current balance: $" + player.getBalance());
				System.out.println("What would you like to do? <Fold, Call, Raise>");
				String response = scanner.next();

				if (response.equalsIgnoreCase("fold")) {
					player.fold();
					activePlayers--;
				} else if (response.equalsIgnoreCase("call")) {
					player.subFromBalance(currentBet);
				} else if (response.equalsIgnoreCase("raise")) {
					System.out.println("How much would you like to raise? Minimum: $" + (currentBet + 1));
					double raiseAmount = scanner.nextDouble();
					if (raiseAmount <= currentBet) raiseAmount = currentBet + 1;
					player.subFromBalance(raiseAmount);
					currentBet = raiseAmount;
					bettingComplete = false; // continue to loop after a raise
				} else {
					System.out.println("Invalid option, folding.");
					player.fold();
					activePlayers--;
				}
			}
		}

		// the draw
		for (Player player : players) {
			if (player.getFold()) continue;

			System.out.println("Player " + (players.indexOf(player) + 1));
			System.out.println("Your hand: " + toString(player.getHand()));
			System.out.println("Would you like to discard cards? <Yes or No>");
			String response = scanner.next();

			if (response.equalsIgnoreCase("yes")) {
				scanner.nextLine();
				System.out.println("Enter up to three indices (space-separated) to discard:");
				String line = scanner.nextLine();
				String[] parts = line.split("\\s+");
				for (String part : parts) {
					try {
						int index = Integer.parseInt(part);
						player.discardCard(index);
					} catch (Exception e) {
						System.out.println("Invalid input: " + part);
					}
				}
				for (String part : parts) {
					player.drawCard(deck.draw());
				}
				System.out.println("Your new hand: " + toString(player.getHand()));
			}
		}

		// second round of betting
		currentBet = 0.0;
		boolean hasRaised = false;
		boolean secondBettingDone = false;
		List<Player> secondRoundPlayers = new ArrayList<>();
		for (Player p : players) {
			if (!p.getFold()) secondRoundPlayers.add(p);
		}

		while (!secondBettingDone) {
			secondBettingDone = true;

			for (Player player : secondRoundPlayers) {
				if (player.getFold()) {
					continue;
				}

				System.out.println("Player " + (players.indexOf(player) + 1));
				System.out.println("Your balance: $" + player.getBalance());

				if (!hasRaised) {
					System.out.println("What would you like to do? <Fold, Check, Raise>");
					String response = scanner.next();

					if (response.equalsIgnoreCase("fold")) {
						player.fold();
						activePlayers--;
					} else if (response.equalsIgnoreCase("check")) {
						continue;
					} else if (response.equalsIgnoreCase("raise")) {
						System.out.println("Enter raise amount:");
						currentBet = scanner.nextDouble();
						player.subFromBalance(currentBet);
						hasRaised = true;
						secondBettingDone = false;
						break; // restart loop
					} else {
						System.out.println("Invalid option, folding.");
						player.fold();
						activePlayers--;
					}
				} else {
					System.out.println("What would you like to do? <Fold, Call, Raise>");
					String response = scanner.next();

					if (response.equalsIgnoreCase("fold")) {
						player.fold();
						activePlayers--;
					} else if (response.equalsIgnoreCase("call")) {
						player.subFromBalance(currentBet);
					} else if (response.equalsIgnoreCase("raise")) {
						System.out.println("Enter new raise amount (minimum " + (currentBet + 1) + "):");
						double newRaise = scanner.nextDouble();
						if (newRaise <= currentBet) newRaise = currentBet + 1;
						player.subFromBalance(newRaise);
						currentBet = newRaise;
						secondBettingDone = false;
						break; // restart loop
					} else {
						System.out.println("Invalid option, folding.");
						player.fold();
						activePlayers--;
					}
				}
			}
		}

		// evaluate hands
		for (Player player : players) {
			if (player.getFold()) {
				continue;
			}

			List<Card> cards = new ArrayList<>(player.getHand());
			List<List<Card>> combinations = generateCombinations(cards, 5);
			PokerHand bestHand = evaluateBestHand(combinations);
			playerBestHands.put(player, bestHand);

			System.out.println("Player " + (players.indexOf(player) + 1) + ": $" + player.getBalance());
			System.out.println("    Best hand: " + bestHand + "    " + bestHand.evaluateHandRank());
		}

		determineWinner();

	}

	private void determineWinner() {
		// TODO Auto-generated method stub
		List<Player> winners = new ArrayList<>();
		PokerHand bestHand = null;

		for (Entry<Player, PokerHand> entry : playerBestHands.entrySet()) {
			Player player = entry.getKey();
			PokerHand playerBestHand = entry.getValue();

			if (bestHand == null || playerBestHand.compareTo(bestHand) > 0) {
				bestHand = playerBestHand;
				winners.clear();
				winners.add(player);
			} else if (playerBestHand.compareTo(bestHand) == 0) {
				winners.add(player);
			}
		}

		double winningsPerPlayer = pot / winners.size();
		for (Player winner : winners) {
			winner.addToBalance(winningsPerPlayer);
		}

		pot = 0.0;

		if (!winners.isEmpty()) {
			if (winners.size() == 1) {
				Player winner = winners.get(0);
				System.out.println("Winner: Player " + (players.indexOf(winner) + 1) + " $" + winner.getBalance());
				System.out.println(bestHand + "    " + bestHand.evaluateHandRank());
			} else {
				System.out.println("Winning hands (tie)");
				for (Player winner : winners) {
					System.out.println(bestHand + " " + bestHand.evaluateHandRank() + " Player "
							+ (players.indexOf(winner) + 1) + " $" + winner.getBalance());
				}

			}
		}

	}

	public String toString(List<Card> cards) {
		StringBuilder string = new StringBuilder();
		for (Card card : cards) {
			string.append(card).append(" ");
		}
		return string.toString().trim();
	}

	private PokerHand evaluateBestHand(List<List<Card>> combinations) {
		// TODO Auto-generated method stub
		PokerHand bestHand = null;

		for (List<Card> cards : combinations) {
			PokerHand currHand = new PokerHand(cards.get(0), cards.get(1), cards.get(2), cards.get(3), cards.get(4));
			if (bestHand == null || currHand.compareTo(bestHand) > 0) {
				bestHand = currHand;
			}
		}
		return bestHand;
	}

	private List<List<Card>> generateCombinations(List<Card> playerCards, int i) {
		// TODO Auto-generated method stub
		List<List<Card>> combinations = new ArrayList<>();
		combinationsHelper(playerCards, i, 0, new ArrayList<>(), combinations);
		return combinations;
	}

	private void combinationsHelper(List<Card> cards, int k, int start, List<Card> current, List<List<Card>> result) {
		// TODO Auto-generated method stub
		if (current.size() == k) {
			result.add(new ArrayList<>(current));
			return;
		}

		for (int i = start; i < cards.size(); i++) {
			current.add(cards.get(i));
			combinationsHelper(cards, k, i + 1, current, result);
			current.remove(current.size() - 1);

		}

	}

	public double getPot() {
		return pot;
	}

	public void setPot(double pot) {
		this.pot = pot;
	}

}
