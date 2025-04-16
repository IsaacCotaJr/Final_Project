package model;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class CardLabel extends JLabel implements Observer{
	private boolean showFace;
	private int rank;
	private String suit;
	
	public CardLabel() {
		this.showFace = false;
		try {
			// initialize as the back of a card
        	BufferedImage cardImage = ImageIO.read(new File("./main/model/CardPhotos" + "/PokerCardBack.png"));
                    
            Image resizedImage = cardImage.getScaledInstance(125, 200, Image.SCALE_SMOOTH);
            ImageIcon resizedIcon = new ImageIcon(resizedImage);
            this.setIcon(resizedIcon);
            
         } catch (IOException e) {
                    e.printStackTrace();
         }
	}
	
	public void showFace() {
		this.showFace = true;
	}
	
	public void setCardType(Card card) {
		rank = card.getRank().getValue();
		suit = card.getSuit().toString().toLowerCase();
	}
	
	@Override
	public void update() {
		String fileStart = "./main/model/CardPhotos";
		// Change the image
		if (showFace) {
			try {
				// initialize as the back of a card
	        	BufferedImage cardImage = ImageIO.read(new File(fileStart + "/" + rank + "_of_" + suit + ".png"));
	                    
	            Image resizedImage = cardImage.getScaledInstance(125, 200, Image.SCALE_SMOOTH);
	            ImageIcon resizedIcon = new ImageIcon(resizedImage);
	            this.setIcon(resizedIcon);
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
		else {
			// just the back of the card
			// since is initialized as the back of the card, do nothing
		}
	}

}
