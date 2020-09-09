package com.intuit.interview.tictactoe.bo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PositionBOTest
{
	@Test
	public void testSwitchPlayer()
	{
		assertEquals(PositionBO.X, PositionBO.switchPlayer(PositionBO.O));
		assertEquals(PositionBO.EMPTY, PositionBO.switchPlayer(PositionBO.EMPTY));
		assertEquals(PositionBO.O, PositionBO.switchPlayer(PositionBO.X));
	}
}

