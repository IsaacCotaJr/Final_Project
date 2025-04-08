package model;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class UsersBalanceLabel extends JLabel implements Observer{

	public UsersBalanceLabel() {
		super("", JLabel.CENTER);
		this.setText("Remaining Balance: 0");
		this.setSize(250,100);
	}
	
	@Override
	public void update() {
		// Shows user's current amount of "money"
		
	}

}
