package model;

import javax.swing.JLabel;

public class BetAmountLabel extends JLabel implements Observer{
	private double bet;
	
	public BetAmountLabel() {
		super("", JLabel.CENTER);
		this.setText("Bet Amount: " + bet);
		this.setSize(250,100);
	}
	
	@Override
	public void update(double num, double num2) {
		// Shows the current bet
		this.setText("Bet Amount: " + num);
	}

}
