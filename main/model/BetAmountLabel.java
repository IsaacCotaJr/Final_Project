package model;

import javax.swing.JLabel;

public class BetAmountLabel extends JLabel implements Observer{

	public BetAmountLabel() {
		super("", JLabel.CENTER);
		this.setText("Bet Amount: 0");
		this.setSize(250,100);
	}
	
	@Override
	public void update() {
		// Shows the current bet
		
	}

}
