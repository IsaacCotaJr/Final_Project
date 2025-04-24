package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SuitTest {

	@Test
	  public void testSuitNamesAreInOfTypeSuit() {
	    assertTrue(Suit.CLUBS instanceof Suit);
	    assertTrue(Suit.DIAMONDS instanceof Suit);
	    assertTrue(Suit.HEARTS instanceof Suit);
	    assertTrue(Suit.SPADES instanceof Suit);
	  }

	  @Test
	  public void testSuitWithTheBuiltInMethodValues() {
	    String result = "";
	    for (Suit suit : Suit.values())
	      result += suit + "_";
	    assertEquals("CLUBS_DIAMONDS_HEARTS_SPADES_", result);
	  }
	
}
