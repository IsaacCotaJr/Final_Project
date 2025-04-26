package model;

import javax.swing.JLabel;

public class PotLabel extends JLabel implements Observer{
	private double bet;
	
	public PotLabel() {
		super("", JLabel.CENTER);
		this.setText("Current Pot: " + 0.0);
		this.setSize(250,100);
	}
	
	@Override
	public void update(double num, double num2, double num3) {
		// Shows the current pot
		this.setText("Current Pot: " + num3);
	}

}
