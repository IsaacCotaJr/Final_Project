package model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

public class ModelTest {

	@Test
	public void testRegisterAndDeregisterObserver() {
		User u = new User("isaac", "pass", 100.00);
		Model m = new Model(u);
		CardLabel cl = new CardLabel();
		m.registerObserver(cl);
		assertEquals(1, m.getObservers().size());
		m.deregisterObserver(cl);
		assertEquals(0, m.getObservers().size());
	}
//	
//	@Test
//	public void testShuffleAndDeal(){
//		User u = new User("isaac", "pass", 100.00);
//		Model m = new Model(u);
//		for (int i = 0; i < 5; i++) {
//			m.registerObserver(new CardLabel());
//		}
//		m.shuffleAndDeal();
//		ArrayList<Player> players = m.getPlayers();
//		assertEquals(players.get(0).getHand().size(), 5);
//		}
}
