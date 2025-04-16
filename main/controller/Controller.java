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
	// Bassam
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

    if (command.equals("initDraw")) {
        model.shuffleAndDeal();       // Draw cards
        model.notifyObservers();      // Update UI
        view.showEndOptions();        // Show play again / exit buttons
    } 
    else if (command.equals("playAgain")) {
        view.resetGame();             // Reset everything for a new round
    } 
    else if (command.equals("exit")) {
        System.exit(0);               // Exit the program
    }
}
	
	public void addObserver(Observer observer) {
		model.registerObserver(observer);
	}
}

