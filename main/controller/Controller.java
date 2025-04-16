package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Model;
import model.Observer;

public class Controller implements ActionListener{
	private Model model;
	private GameView view; // Bassam
	//Bassam added GameView for a reference to the view
	public Controller(Model model, GameView view) {
		this.model = new Model();
		this.view = view; // Bassam
	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		if(command.equals("initDraw")) {
			// draw cards for player/computers using model
		} 
		else {
			
		}
	}
	
	public void addObserver(Observer observer) {
		model.registerObserver(observer);
	}
}

