package model;

import java.util.*;

public class Player {
	
	private double balance;
	private ArrayList<Card> hand;
	public boolean fold = false;
	private boolean isHuman;
	
	public Player(double balance, boolean human) {
		this.balance = balance;
		this.isHuman = human;
	}

	public boolean isHuman(){
		return isHuman;
	}
	
	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}
	
	public ArrayList<Card> getHand() {
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

	@Override
	public String toString() {
		return "" + isHuman;
	}
}
