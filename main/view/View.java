package view;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Controller;
import model.*;
import database.*;

public class View extends JFrame{
	private Controller controller;
	private UserRegistry uR;
	private User u;

	public View() {
		this.uR = new UserRegistry();
		this.controller = new Controller(new Model());
		this.setTitle("MVC Demo");
		this.setSize(400,400);
		//this.setUp(); // WILL CALL WHEN GAME RUN
	}
	
	private void setUp() {
		// The game
		
		//setting up the main panel
		JPanel mainPanel = new JPanel();
		this.add(mainPanel);
		
		//setting up the switch button
		JButton incButton = new JButton("increment");
		incButton.setActionCommand("inc");
		//incButton.addActionListener(controller);
		mainPanel.add(incButton);
		
		//setting up the count button
		JButton decButton = new JButton("decrement");
		decButton.setActionCommand("dec");
		//decButton.addActionListener(controller);
		mainPanel.add(decButton);
		
		//adding a window listener for closing the app
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		View view = new View();
		boolean isLoggedIn = view.login();
		if (isLoggedIn) {
			view.run();
		}
	}
	
	public boolean login() {
		Scanner scanner = new Scanner(System.in);
		int command = 0;
		boolean loggedIn = true;
		
		while (command < 3) {
			System.out.println("Welcome User!\nWould you like to login, or create a new account?");
			System.out.println("1. Login");
			System.out.println("2. Create a new account");
			
			System.out.println("Please enter the integer of the command you'd like to use: ");
			System.out.println("Or enter a negative integer to exit");
			
			command = getValidIntegerInput(scanner);
			scanner.nextLine();
			
			if (command < 0) {
				System.out.println("You have exitted successfully.");
				loggedIn = false;
				break;
			}
			if (command == 1) {
				System.out.println("What is your user name?");
				String uName = scanner.nextLine();
				String noSpaces = uName.replace(" ", "_");
				// Since I plan on using the user name as part of a unique file name, need to replace spaces
				
				if(uR.checkIfUserExists(noSpaces)) {
					User potentialUser = uR.getUser(noSpaces);
					
					System.out.println("What is your password?");
					
					int count = 0;
					boolean flag = false;
					while (count<=3) {
						String pwd = scanner.nextLine();
						try {
							if (potentialUser.getHashedPassword().equals(hashedToString(pwd, potentialUser.getSalt()))) {
								System.out.println("Succesfully logged in!");
								this.u = potentialUser;
								u.fillBalanceFromTxt();
								flag = true;
								break;
							}
							else if (count == 2){
								System.out.println("You have one more chance to enter the correct password, try again.");
								count++;
							}
							else if (count == 3) {
								count++;
							}
							else {
								System.out.println("Incorrect password for this user name, try again.");
								count++;
							}
							
						} catch (NoSuchAlgorithmException e) {
							e.printStackTrace();
						}
					}
					// since the break within the inner count<3 while loop only breaks out of that one,
					// need to break again if the user successfully logged in
					if (flag) {
						break;
					}
					// User entered wrong password 3 times, should kick them back to start
					else {
						System.out.println("You entered the wrong password 3 times, you will now be taken "
								+ "back to the starting message.");
						command = 0;
					}
				}
				else {
					System.out.println("This user name does not exist.");
					System.out.println("Create an account with this user name, or check that"
							+ " the spelling is correct (User name is case sensitive).");
				}
			}
			else if (command == 2) {
				System.out.println("What user name would you like to use?");
				boolean flag = true;
				String userName = "";
				
				while(flag) {
					String uName = scanner.nextLine();
					String noSpaces = uName.replace(" ", "_");
					// Since I plan on using the user name as part of a unique file name, need to replace spaces
					if(uR.checkIfUserExists(noSpaces)) {
						System.out.println("This user name is taken, please choose another one.");
					}
					else {
						userName = noSpaces;
						flag = false;
					}
				}
				System.out.println("What password would you like to use?");
				String pWord = scanner.nextLine();
				this.u = new User(userName,pWord, 100.0);
				uR.addUser(u);
				break;
			}
			else {
				System.out.println("Invalid input. Please choose an integer 1 or 2");
				command = 0;
			}
		}
		return loggedIn;
	}
	
	private byte[] hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        byte[] hashedPassword = md.digest(password.getBytes());
        return hashedPassword;
    }
	
	private String hashedToString(String password, byte[] s) throws NoSuchAlgorithmException {
        byte[] hashedPassword = hashPassword(password, s);
        byte[] saltAndHash = new byte[s.length + hashedPassword.length];
        System.arraycopy(s, 0, saltAndHash, 0, s.length);
        System.arraycopy(hashedPassword, 0, saltAndHash, s.length, hashedPassword.length);
        return Base64.getEncoder().encodeToString(saltAndHash);
    }
	
	private static int getValidIntegerInput(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter an integer 1 - 17.");
            scanner.next();
        }
        return scanner.nextInt();
    }
	
	public void run() {
		// display user's information, ask if want to play game or log out
	}
	
}
