package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class UserTest {

	@Test 
	void createUserTest(){
		User user = new User("icota", "HELLOworld",100);
		assertEquals("icota",user.getUName());
		assertNotNull(user.getHashedPassword());
		
	}
	
	@Test
	void fillBalanceTest() {
		User kHarris = new User("Kamala_Harris", "yes", 100.0);
		kHarris.fillBalanceFromTxt();
		assertTrue(kHarris.getBalance() - 100 < 0.00001);
	}
	
	@Test
	void getUNameTest() {
		User dTrump = new User("Donald_Trump");
		assertEquals(dTrump.getUName(), "Donald_Trump");
	}
	
	@Test
	void getHashedPasswordTest() {
		User dTrump = new User("Donald_Trump");
		assertEquals(dTrump.getHashedPassword(), "aRxf8h/hbdVloF6QLkxB67FSyLbRvrsZQNdzg4q7TmMm6FVnxhOG1AmE4vvvKlSt");
	}
	
	@Test
	void getSaltStringTest() {
		User dTrump = new User("Donald_Trump");
		assertEquals(dTrump.getSaltString(), "aRxf8h/hbdVloF6QLkxB6w==");
	}
	
	@Test
	void setBalanceTest() {
		User jdVance = new User("Vance", "no", 100.0);
		jdVance.setBalance(100000000.0);
		assertTrue(jdVance.getBalance() - 100000000.0 < 0.0001);
	}
	
	@Test
	void saveUserTest() {
		User jBiden = new User("Joe_Biden", "abc", 100);
		jBiden.saveUserToFile();
		User jBiden2 = new User("Joe_Biden");
		jBiden2.fillBalanceFromTxt();
		assertEquals(jBiden2.getBalance(), 100);
	}

}
