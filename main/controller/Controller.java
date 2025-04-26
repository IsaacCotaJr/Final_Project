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
	    } 
	    
	    //Dwij
	    else if (command.equals("bet_fold")) {
	    	// since player folds, can just end game
	    	view.resetGame();
	    	model.resetGame();
	        //model.placeBet("fold", 0);
	        //model.advanceTurn();
	    }
	    else if (command.equals("bet_call")) {
	        model.placeBet("call", 0, 0);
	        model.advanceTurn();
	    }
	    else if (command.equals("bet_check")) {
	        model.placeBet("check", 0, 0);
	        model.advanceTurn();
	    }
	    else if (command.equals("bet_raise")) {
	        double raiseAmt = Double.parseDouble(view.getRaiseFieldText());
	        model.placeBet("raise", raiseAmt, 0);
	        model.advanceTurn();
	    }
	    //Dwij
	    else if (command.equals("playAgain")) {
	        view.resetGame();             // Reset everything for a new round
	        model.startBettingRound();
	    } 
	    else if (command.equals("exit")) {
	        System.exit(0);               // Exit the program
	    }
	}
	
	public void emptyObservers() {
		model.emptyObservers();
	}
	
	public void addObserver(Observer observer) {
		model.registerObserver(observer);
	}
	
	public void createCompPlayers(String diff) {
		model.createCompPlayers(diff);
	}

	// isaac
	public ArrayList<Card> drawNewCards(boolean[] toReplace) {
		return model.drawNewCards(toReplace);
	}
	
	public void shuffleAndDeal() {
		model.shuffleAndDeal();
	}
	
	public String gameOver() {
		return model.gameOver();
	}
}

