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
import java.util.Base64;
import java.util.Scanner;

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
	private Controller controller;
	private JPanel mainPanel;
	private JButton initDrawButton;
	private JButton playAgainButton; // Bassam
    private JButton exitButton; // Bassam
    private User u;
    
	public GameView(User user) {
        this.u = user;
        this.controller = new controller.Controller(new model.Model(u), this); // Pass reference to self
        this.setTitle("Poker Game");
        this.setSize(1400,800);
        this.setLayout(new BorderLayout());
        this.setUp();
    }
	
	private void setUp() {
        // Difficulty selection dropdown
        String[] difficulties = {"Easy", "Hard"};
        difficultyBox = new JComboBox<>(difficulties); // Bassam: create difficulty dropdown
        difficultyBox.setBounds(600, 250, 200, 40);
        difficultyBox.addActionListener(e -> selectedDifficulty = (String) difficultyBox.getSelectedItem()); // Bassam: update selected difficulty
        mainPanel.add(difficultyBox); // Bassam: add dropdown to UI
        // Hide end-of-game buttons at setup
        if (playAgainButton != null) playAgainButton.setVisible(false);
        if (exitButton != null) exitButton.setVisible(false);
		//setting up the main panel
		mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.BLACK);

        add(mainPanel);
        
        //setting up the draw button
      	initDrawButton = new JButton("Draw Cards"); 
      	initDrawButton.setActionCommand("initDraw");
      	initDrawButton.addActionListener(controller);
      	// Once this button is clicked, want it to disappear and set computer strategy
       initDrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initDrawButton.setVisible(false);
                // Bassam: set computer player strategy based on selected difficulty
                ComputerStrategy strategy = selectedDifficulty.equals("Easy") ? new EasyStrategy() : new HardStrategy(); // Bassam: choose strategy
                computerPlayer = new ComputerPlayer(100.0, strategy); // Bassam: create computer player
                difficultyBox.setEnabled(false); // Bassam: disable dropdown after game starts
            }
       });
      	initDrawButton.setBounds(600, 350, 200, 100);
      	mainPanel.add(initDrawButton);
		
      	playAgainButton = new JButton("Play Again");
		
		// Bassam
      	playAgainButton.setActionCommand("playAgain"); 
      	playAgainButton.addActionListener(new ActionListener () {
      		@Override
      		public void actionPerformed(ActionEvent e) {
      			setUp();
      		}
      	});
      	playAgainButton.setBounds(550, 450, 150, 50);
      	playAgainButton.setVisible(true);
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
      	exitButton.setVisible(true);
      	mainPanel.add(exitButton); 
		// Bassam
		
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
      	
      	JPanel playerHand = new JPanel();
      	playerHand.setLayout(new GridLayout(1,5));
      	for (int i = 0; i < 5; i++) {
      		CardLabel cl = new CardLabel(); 
      		controller.addObserver(cl);
      		playerHand.add(cl);
      	}
      	playerHand.setBounds(387, 500, 625, 200);
      	mainPanel.add(playerHand);
      	
      	// Add balance, bet amount labels here
      	
      	
      	
      	
		//adding a window listener for closing the app
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
				u.saveUserToFile();
			}
		});
		
		this.setVisible(true);
	}
	
	
	// Keep now for reference
//	String fileStart = "./main/model/CardPhotos/";
//    String file1 = fileStart + "ace" + "_of_" + "hearts" + ".png";
//    
//    for (String filename: files) {
//    	try {
//        	BufferedImage cardImage = ImageIO.read(new File(filename));
//                    
//            Image resizedImage = cardImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
//            ImageIcon resizedIcon = new ImageIcon(resizedImage);
//            JLabel cardLabel = new JLabel(resizedIcon);
//                    
//                 // Set the position of the image within the JLabel
//            cardLabel.setHorizontalAlignment(SwingConstants.CENTER); // Options: LEFT, CENTER, RIGHT
//            cardLabel.setVerticalAlignment(SwingConstants.BOTTOM);   // Options: TOP, CENTER, BOTTOM
//            
//            mainPanel.add(cardLabel);
//         } catch (IOException e) {
//                    e.printStackTrace();
//         }
//    }
}
