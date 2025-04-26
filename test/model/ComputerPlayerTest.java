package model;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

class ComputerPlayerTest {
	ComputerPlayer cp1 = new ComputerPlayer(100.0, new EasyStrategy());
	
	@Test
	void testDecideMove() {
        String move = cp1.decideMove(100, 10);
        assertTrue(Arrays.asList("fold", "call", "raise").contains(move)); // Bassam: move must be one of the valid options
	}
	
	@Test
	void setStrategyTest() {
		cp1.setStrategy(new HardStrategy());
	}

}
