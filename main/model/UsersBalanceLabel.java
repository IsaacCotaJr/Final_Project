package model;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class UsersBalanceLabel extends JLabel implements Observer{
	private double balance;
	
	public UsersBalanceLabel(double bal) {
		super("", JLabel.CENTER);
		this.setText("Remaining Balance: " + balance);
		this.setSize(250,100);
	}
	
	@Override
	public void update(double num, double num2, double num3) {
		// Shows user's current amount of "money"
		this.setText("Remaining Balance: " + num2);
	}
	
	public double getBalance() {
		return balance;
	}

}
