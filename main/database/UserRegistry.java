package database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import model.User;

public class UserRegistry {
	// key String is the username of the user
		private HashMap<String, User> Users;

		public UserRegistry() {
			this.Users = new HashMap<String, User>();
			
			// Go through users/users.txt to get existing user data, add to the UserRegistry
			try {
				String fp = "./main/database/users/users.txt";
				BufferedReader in = new BufferedReader(new FileReader(fp));
				
				ArrayList<String[]> users = new ArrayList<String[]>();
				String line;
				// Goes through users.txt to get name of each user
	            while ((line = in.readLine()) != null) {
	                String[] l = line.split(" ");
	                users.add(l);
	            }
	            
	            // for each user, create a User object and add to registry
	            for (String[] user: users) {
	            	String uName = user[0];
	            	
	            	User u = new User(uName);
	            	// calls the special constructor that reads from the file
	            	
	            	Users.put(uName, u);
	            }
	            
	            in.close();
			} 
			catch (FileNotFoundException e) {
				e.printStackTrace();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// Used when creating a new User
		public void addUser(User u) {
			try {
		        String folderPath = "./main/database/users";
		        String fileName = u.getUName() + ".txt"; // since we have unique usernames, just UName is enough
		        
		        File folder = new File(folderPath);
		        
		        // Create a File object for the file
		        File file = new File(folder, fileName);
		        file.createNewFile();
		        // Only create the file, will write to it later within the User class
		        // once the library has been fleshed out
		        
		        // Should also write to username.txt and users.txt
		        String content = u.getUName() + "," + u.getHashedPassword() + "," + 
		        		u.getSaltString();
		        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
		            writer.write(content);
		            writer.newLine();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		        
		        // for users.txt
		        String fp = "./main/database/users/users.txt";
		        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fp, true))) {
		            writer.write(u.getUName());
		            writer.newLine();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		        
		        Users.put(u.getUName(), u);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
		
		public boolean checkIfUserExists(String userName) {
			boolean userExists = false;
			for (String uName : Users.keySet()) {
				if(uName.equals(userName)) {
					userExists = true;
				}
			}
			return userExists;
		}
		
		public User getUser(String userName) {
			return Users.get(userName);
		}
}
