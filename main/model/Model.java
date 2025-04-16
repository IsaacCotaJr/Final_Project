package model;

import java.util.ArrayList;

public class Model {
	// This will be where the game is, i.e. the Deck, Card, Hand, and other instances of classes will be here
	
	private ArrayList<Observer> observers;
	private Deck deck;
	
	public Model() {
		this.observers = new ArrayList<Observer>();
	}
	
	public void registerObserver(Observer observer) {
		this.observers.add(observer);
	}
	
	public void deregisterObserver(Observer observer) {
		this.observers.remove(observer);
	}
	
	/* PRIVATE METHODS */
	private void notifyObservers() {
		for(Observer o : observers) {
			o.update();
		}
	}
}
