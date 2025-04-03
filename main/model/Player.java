

package model;

import java.util.*;

public class Player {
	
	private double balance;
	private List<Card> hand;
	
	public Player(double balance) {
		this.balance = balance;
	}
	
	public void setHand(List<Card> hand) {
		this.hand = hand;
	}
	
	public List<Card> getHand() {
		return hand;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public void addToBalance(double amount) {
		balance += amount;
	}
	
	public void subFromBalance(double amount) {
		balance -= amount;
	}

}
