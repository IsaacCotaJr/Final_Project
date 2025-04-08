package model;

import java.util.ArrayList;

public class Model {
	private int number;
	private ArrayList<Observer> observers;
	
	public Model() {
		this.observers = new ArrayList<Observer>();
	}
	
	public void increment() {
		this.number++;
		notifyObservers();
	}
	
	public void decrement() {
		this.number--;
		notifyObservers();
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
