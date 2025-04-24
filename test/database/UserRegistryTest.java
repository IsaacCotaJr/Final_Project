package database;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.User;

class UserRegistryTest {

UserRegistry uR = new UserRegistry();
	
	@Test
	void addUserandCheckIfUserExistsTrueTest(){
		uR.addUser(new User("Isaac_Cota", "helloWorld", 100));
		assertTrue(uR.checkIfUserExists("Isaac_Cota"));
	}
	
	@Test
	void checkIfUserExistsFalseTest() {
		assertFalse(uR.checkIfUserExists("John_Doe"));
	}
	
	@Test
	void getUserTest() {
		uR.addUser(new User("Isaac_Cota", "helloWorld", 100));
		User user = uR.getUser("Isaac_Cota");
		assertEquals(user.getUName(), "Isaac_Cota");
	}

}
