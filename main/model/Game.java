
package model;

import java.util.*;
import java.util.Map.Entry;

public class Game {
	
	public Game() {
		// don't really need anything here
	}
	
	public ArrayList<Player> determineWinner(HashMap<Player, PokerHand> playerBestHands) {
		// TODO Auto-generated method stub
		ArrayList<Player> winners = new ArrayList<>();
		PokerHand bestHand = null;

		for (Entry<Player, PokerHand> entry : playerBestHands.entrySet()) {
			Player player = entry.getKey();
			PokerHand playerBestHand = entry.getValue();
			
			System.out.println(playerBestHand);

			if (bestHand == null || playerBestHand.compareTo(bestHand) > 0) {
				bestHand = playerBestHand;
				winners.clear();
				winners.add(player);
			} else if (playerBestHand.compareTo(bestHand) == 0) {
				winners.add(player);
			}
		}

		return winners;
	}
}