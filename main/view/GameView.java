package view;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Scanner;
import javax.swing.JTextField;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

import controller.Controller;
import model.*;
import database.*;

public class GameView extends JFrame{
    private JComboBox<String> difficultyBox; // Bassam: dropdown for selecting difficulty
    private String selectedDifficulty = "Easy"; // Bassam: stores selected difficulty
    private ComputerPlayer computerPlayer; // Bassam: holds computer player instance
	private ComputerPlayer computerPlayer2;
    private JTextField raiseField;

    private UsersBalanceLabel balance;
    private BetAmountLabel betAmount;
    private PotLabel potLabel;
    private JLabel instLabel;
    // number to keep track of how many times bet placed
 	int count = 0;

	private Controller controller;
	private JPanel mainPanel = new JPanel();
	private JButton initDrawButton;
	private JButton playAgainButton; // Bassam
    private JButton exitButton; // Bassam
    private User u;
    // isaac
    private JButton drawPhaseButton = new JButton(); // draw phase
    private boolean[] cardsSelected = new boolean[5]; // to track card selection
    private ArrayList<CardLabel> playerCardLabels = new ArrayList<>(); // reference to player hand card labels
    private boolean canSelectCards = false; // player can't select cards
    private JPanel playerHand;
    // isaac
    
	public GameView(User user) {
        this.u = user;
        this.controller = new controller.Controller(new model.Model(u), this); // Pass reference to self
        this.setTitle("Poker Game");
        this.setSize(1400,800);
        this.setLayout(new BorderLayout());
        this.setUp();
    }

	
	private void setUp() {
		// number to keep track of how many times bet placed
		count = 0;
		
        // Difficulty selection dropdown
        String[] difficulties = {"Easy", "Hard"};
        difficultyBox = new JComboBox<>(difficulties); // Bassam: create difficulty dropdown
        difficultyBox.setBounds(600, 250, 200, 40);
        difficultyBox.addActionListener(e -> selectedDifficulty = (String) difficultyBox.getSelectedItem()); // Bassam: update selected difficulty
        
        // send difficulty to controller to create ComputerPlayers
        controller.createCompPlayers(selectedDifficulty);
        
        mainPanel.add(difficultyBox); // Bassam: add dropdown to UI
        // Hide end-of-game buttons at setup
        if (playAgainButton != null) playAgainButton.setVisible(false);
        if (exitButton != null) exitButton.setVisible(false);
		
        //setting up the main panel
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.BLACK);

        add(mainPanel);
        
        // Add balance, bet amount, pot labels here
      	balance = new UsersBalanceLabel(u.getBalance());
      	balance.setBounds(1000, 600, 400, 50);
      	balance.setForeground(Color.WHITE);
      	mainPanel.add(balance);
      	
      	betAmount = new BetAmountLabel();
      	betAmount.setBounds(500, 300, 400, 50);
      	betAmount.setForeground(Color.WHITE);
      	mainPanel.add(betAmount);
      	
      	potLabel = new PotLabel();
      	potLabel.setBounds(1000, 300, 400, 50);
      	potLabel.setForeground(Color.WHITE);
      	mainPanel.add(potLabel);
      	
      	// Label to give instructions
      	instLabel = new JLabel();
      	instLabel.setBounds(500, 250, 400, 50);
      	instLabel.setForeground(Color.WHITE);
      	instLabel.setVisible(false);
      	mainPanel.add(instLabel);
      	
      	// --- Betting panel ---  Dwij
      	JPanel betPanel = new JPanel();
      	betPanel.setLayout(null);
      	betPanel.setBounds(500, 350, 400, 100);

      	// Fold button
      	JButton foldBtn = new JButton("Fold");
      	foldBtn.setActionCommand("bet_fold");
      	foldBtn.setBounds(0,0,80,30);
      	foldBtn.addActionListener(controller);
      	betPanel.add(foldBtn);

      	// Check button (only on second round before any raise)
      	JButton checkBtn = new JButton("Check");
      	checkBtn.setActionCommand("bet_check");
      	checkBtn.setBounds(90,0,80,30);
      	checkBtn.addActionListener(controller);
      	checkBtn.addActionListener(new ActionListener() {
      		// make bet Panel invisible and disable
			@Override
			public void actionPerformed(ActionEvent e) {
				betPanel.setVisible(false);
				betPanel.setEnabled(false);
				// make second draw button visible
				drawPhaseButton.setVisible(true);
				
				//update count
				if (count == 1) {
					gameOver();
				}
				else {
					// make instLabel visible
					instLabel.setText("Select the cards to discard, then click Draw New Cards");
					instLabel.setVisible(true);
					count++;
				}
			}
      	});
      	betPanel.add(checkBtn);

      	// Raise field + button
      	JTextField raiseField = new JTextField("0");
      	raiseField.setBounds(0,40,80,30);
      	betPanel.add(raiseField);
      	JButton raiseBtn = new JButton("Bet");
      	raiseBtn.setActionCommand("bet_raise");
      	raiseBtn.setBounds(90,40,80,30);
      	raiseBtn.addActionListener(controller);
      	raiseBtn.addActionListener(new ActionListener() {
      		// make bet Panel invisible and disable
			@Override
			public void actionPerformed(ActionEvent e) {
				betPanel.setVisible(false);
				betPanel.setEnabled(false);
				// make second draw button visible
				drawPhaseButton.setVisible(true);
				
				//update count
				if (count == 1) {
					gameOver();
				}
				else {
					// make instLabel visible
					instLabel.setText("Select the cards to discard, then click Draw New Cards");
					instLabel.setVisible(true);
					count++;
				}
			}
      	});
      	betPanel.add(raiseBtn);

      	// expose getter for the field
      	this.raiseField = raiseField;
      	
      	betPanel.setVisible(false); // will be set visible at appropriate times

      	mainPanel.add(betPanel);
        
        //setting up the draw button
      	initDrawButton = new JButton("Draw Cards"); 
      	initDrawButton.setActionCommand("initDraw");
      	initDrawButton.addActionListener(controller);
      	// Once this button is clicked, want it to disappear and set computer strategy
       initDrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initDrawButton.setVisible(false);
                difficultyBox.setEnabled(false); // Bassam: disable dropdown after game starts
                difficultyBox.setVisible(false);
                
                // make betting panel visible
                betPanel.setVisible(true);
                
                // make instLabel visible
				instLabel.setText("Choose whether to bet, call, check, or fold");
				instLabel.setVisible(true);
            }
       });
      	initDrawButton.setBounds(600, 350, 200, 100);
      	mainPanel.add(initDrawButton);
      	
      	// isaac
      	drawPhaseButton = new JButton("Draw New Cards");
      	drawPhaseButton.setVisible(false);     // hide draw button until betting finishes
      	drawPhaseButton.setBounds(600, 350, 200, 50);
      	mainPanel.add(drawPhaseButton);
      	
        
      	//bettingComplete(); // call after first betting round is complete (first betting round would go above this line)
      	// add listener to perform draw
      	drawPhaseButton.addActionListener(new ActionListener() {
      	    @Override
      	    public void actionPerformed(ActionEvent e) {
      	        performDrawPhase();
      	        // set betPanel visible again
      	        betPanel.setVisible(true);
      	        
      	        // make instLabel visible
				instLabel.setText("Choose whether to bet, call, check, or fold");
				instLabel.setVisible(true);
      	    }
      	});
      	// isaac
		
      	playAgainButton = new JButton("Play Again");
		// Bassam
      	playAgainButton.setActionCommand("playAgain");
      	playAgainButton.addActionListener(controller);
//      	playAgainButton.addActionListener(new ActionListener () {
//      		@Override
//      		public void actionPerformed(ActionEvent e) {
//      			resetGame();
//      		}
//      	});
      	playAgainButton.setBounds(550, 450, 150, 50);
      	playAgainButton.setVisible(false);
      	mainPanel.add(playAgainButton);

      	exitButton = new JButton("Exit");
      	exitButton.setActionCommand("exit");
      	exitButton.addActionListener(controller);
      	exitButton.addActionListener(new ActionListener() {
      		@Override
      		public void actionPerformed(ActionEvent e) {
      			u.saveUserToFile();
      		}
      	});
      	exitButton.setBounds(750, 450, 150, 50);
      	exitButton.setVisible(false);
      	mainPanel.add(exitButton); 
		// Bassam
		
      	JPanel playerHand = new JPanel(); // isaac commented this out to make it private variable in GameView to make it accessible to other functions
      	playerHand.setLayout(new GridLayout(1,5));
      	for (int i = 0; i < 5; i++) {
      		CardLabel cl = new CardLabel(); 
      		controller.addObserver(cl);
      		
      		//isaac
      		final int index = i;
      		
      		// show selected (clicked) cards
      		cl.addMouseListener(new java.awt.event.MouseAdapter() {
      	        public void mouseClicked(java.awt.event.MouseEvent evt) {
      	            cardsSelected[index] = !cardsSelected[index];
      	            cl.setBorder(cardsSelected[index] ? BorderFactory.createLineBorder(Color.YELLOW, 3) : null);
      	        }
      	    });

      	    playerCardLabels.add(cl);
      	    playerHand.add(cl);
      	    // isaac
      	}
      	
      	playerHand.setBounds(387, 500, 625, 200);
      	mainPanel.add(playerHand);
      	
      	JPanel compHand1 = new JPanel();
      	compHand1.setLayout(new GridLayout(1,5));
      	for (int i = 0; i < 5; i++) {
      		CardLabel cl = new CardLabel();
      		controller.addObserver(cl);
      		compHand1.add(cl);
      	}
      	compHand1.setBounds(0, 0, 625, 200);
      	mainPanel.add(compHand1);
      	
      	JPanel compHand2 = new JPanel();
      	compHand2.setLayout(new GridLayout(1,5));
      	for (int i = 0; i < 5; i++) {
      		CardLabel cl = new CardLabel(); 
      		controller.addObserver(cl);
      		compHand2.add(cl);
      	}
      	compHand2.setBounds(775, 0, 625, 200);
      	mainPanel.add(compHand2);
      	
      	// add labels to observers
      	controller.addObserver(balance);
      	controller.addObserver(betAmount);
      	controller.addObserver(potLabel);

		//adding a window listener for closing the app
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
				u.saveUserToFile();
			}
		});
		
		this.setVisible(true);
	}
	
	// Should decide if player won or lost, change balance accordingly in model
	public void gameOver() {
		showEndOptions();
		String s = controller.gameOver();
		
		if(s.equalsIgnoreCase("won")) {
			// make instLabel visible
			instLabel.setText("You Won!");
			instLabel.setVisible(true);
		}
		else {
			// make instLabel visible
			instLabel.setText("You Lost!");
			instLabel.setVisible(true);
		}
	}
	
	// Bassam: Show end-of-game options (Play Again, Exit)
	public void showEndOptions() {
	    // Hide main game controls
	    if (initDrawButton != null) initDrawButton.setVisible(false);
	    if (drawPhaseButton != null) drawPhaseButton.setVisible(false);
	    if (difficultyBox != null) difficultyBox.setEnabled(false);
	    // Show Play Again and Exit buttons
	    if (playAgainButton != null) playAgainButton.setVisible(true);
	    if (exitButton != null) exitButton.setVisible(true);
	}

	// Bassam: Reset the game for a new round
	public void resetGame() {
		// This is disabling draw cards showing, will fix
		
	    //Remove all components from the main panel
	    if (mainPanel != null) {
	        mainPanel.removeAll();
	        mainPanel.revalidate();
	        mainPanel.repaint();
	    }
	    // Reset state variables as needed
	    canSelectCards = false;
	    for (int i = 0; i < cardsSelected.length; i++) cardsSelected[i] = false;
	    playerCardLabels.clear();
	    //empty Observers
		controller.emptyObservers();
		// Hide end-of-game buttons
	    if (playAgainButton != null) playAgainButton.setVisible(false);
	    if (exitButton != null) exitButton.setVisible(false);
	    // Re-initialize the game UI
	    setUp();
	}
	
	private void performDrawPhase() {
	    ArrayList<Card> newCards = controller.drawNewCards(cardsSelected);

	    for (int i = 0; i < 5; i++) {
	        if (cardsSelected[i]) {
	            CardLabel cl = playerCardLabels.get(i);
	            cl.setCardType(newCards.remove(0)); // replace selected card
	            cl.setBorder(null);
	            cardsSelected[i] = false;
	        }
	    }

	    drawPhaseButton.setVisible(false); // only one draw allowed
	}
	
	private void bettingComplete() {
	    canSelectCards = true; // enable card selection
	    //setupCardLabels();     // re-setup the labels with click listeners
	    drawPhaseButton.setVisible(true); // now they can draw
	}
	
	public String getRaiseFieldText() {
	    return raiseField.getText();
	}
}
