package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Model;
import model.Observer;

public class Controller implements ActionListener{
	private Model model;

	public Controller(Model model) {
		this.model = new Model();
	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		if(command.equals("initDraw")) {
			// draw cards for player/computers using model
		} 
		else {
			
		}
	}
	
	public void addObserver(Observer observer) {
		model.registerObserver(observer);
	}
}

