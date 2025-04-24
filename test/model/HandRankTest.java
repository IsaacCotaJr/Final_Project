package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class HandRankTest {

	@Test
	public void testGetValue() {
		assertEquals(1, HandRank.HIGH_CARD.getHandRankValue());
		assertEquals(2, HandRank.PAIR.getHandRankValue());
		assertEquals(3, HandRank.TWO_PAIR.getHandRankValue());
		assertEquals(4, HandRank.THREE_OF_A_KIND.getHandRankValue());
		assertEquals(5, HandRank.STRAIGHT.getHandRankValue());
		assertEquals(6, HandRank.FLUSH.getHandRankValue());
		assertEquals(7, HandRank.FULL_HOUSE.getHandRankValue());
		assertEquals(8, HandRank.FOUR_OF_A_KIND.getHandRankValue());
		assertEquals(9, HandRank.STRAIGHT_FLUSH.getHandRankValue());
		assertEquals(10, HandRank.ROYAL_FLUSH.getHandRankValue());

	}
	
}
