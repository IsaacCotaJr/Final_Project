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

public class View {
	private Controller controller;
	private UserRegistry uR;
	private User u;
	private GameView game;

	public View() {
		this.uR = new UserRegistry();
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
		System.out.println("Welcome "+ u.getUName().replace("_", " ") +"!");
		System.out.println("Your current Balance is: " + u.getBalance());
		System.out.println("Would you like to play a game of Poker? (Yes or no)");
		
		Scanner scanner = new Scanner(System.in);
		String ans = "";
		
		while (!(ans.equalsIgnoreCase("yes")) || !(ans.equalsIgnoreCase("yes"))){
			ans = scanner.nextLine();
			
			if(ans.equalsIgnoreCase("yes")) {
				// should start the game
				game = new GameView(u);
			}
			else if(ans.equalsIgnoreCase("no")) {
				break;
				
			}
		}
		
		System.out.println("Thank you for using our game!");
		scanner.close();
		//u.saveUserToFile(); // writes any changes the user had in their balance to their file once the program ends
	}
}
