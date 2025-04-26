package model;

import javax.swing.JLabel;

public class BetAmountLabel extends JLabel implements Observer{
	private double bet;
	
	public BetAmountLabel() {
		super("", JLabel.CENTER);
		this.setText("Bet Amount: " + 0.0);
		this.setSize(250,100);
	}
	
	@Override
	public void update(double num, double num2, double num3) {
		// Shows the current bet
		this.setText("Bet Amount: " + num);
	}

}
