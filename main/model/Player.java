package model;

import java.util.*;

public class Player {
	
	private double balance;
	private List<Card> hand;
	public boolean fold = false;
	
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
	
	public void fold() {
		fold = true;
	}
	
	public void setFold(boolean boo) {
		fold = boo;
	}
	
	public boolean getFold() {
		return fold;
	}
	
	public void discardCard(int index) {
		hand.remove(index);
	}
	
	public void drawCard(Card card) {
		hand.add(card);
	}

}
