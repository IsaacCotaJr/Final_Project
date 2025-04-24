package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import model.Card;
import model.Model;
import model.Observer;

public class Controller implements ActionListener{
	private Model model;
    private view.GameView view;
    public Controller(Model model, view.GameView view) {
        this.model = model;
        this.view = view;
    }
	// Bassam
	public void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();

    if (command.equals("initDraw")) {
        model.shuffleAndDeal();       // Draw cards
        //model.notifyObservers();      // Update UI
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

	// isaac
	public ArrayList<Card> drawNewCards(boolean[] toReplace) {
		return model.drawNewCards(toReplace);
	}
	
	public void shuffleAndDeal() {
		model.shuffleAndDeal();
	}

	public void addComputerPlayer(int index, ComputerPlayer cPlayer) {
		model.addComputerPlayer(index, cPlayer);
	}
	// isaac
}

