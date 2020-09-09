package com.intuit.interview.tictactoe.bo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BoardTest
{
	@Test
	public void testConstructor()
	{
		Board actualBoard = new Board(3);
		assertEquals(3, actualBoard.getBoard().length);
		assertEquals(8, actualBoard.getSums().length);
		assertEquals(3, actualBoard.getSize());
		assertEquals(9, actualBoard.getNumberOfTurns());
	}

	@Test
	public void testConstructor2()
	{
		assertThrows(NegativeArraySizeException.class, () -> new Board(-1));
	}

	@Test
	public void testGetPosition()
	{
		assertEquals(PositionBO.EMPTY, (new Board(3)).getPosition(1, 1));
		assertThrows(ArrayIndexOutOfBoundsException.class,
				() -> (new Board(3)).getPosition(1, -1));
	}

	@Test
	public void testGameWon()
	{
		assertEquals(PositionBO.X, (new Board(0)).gameWon());
		assertEquals(PositionBO.EMPTY, (new Board(3)).gameWon());
	}

	@Test
	public void testIsDraw()
	{
		assertFalse((new Board(3)).isDraw());
	}

	@Test
	public void testEmpty()
	{
		assertTrue((new Board(3)).empty());
	}

	@Test
	public void testCheckIfMarkWinsGame() throws Exception
	{
		assertThrows(Exception.class,
				() -> (new Board(3)).checkIfMarkWinsGame(1, 3));
		assertThrows(Exception.class,
				() -> (new Board(3)).checkIfMarkWinsGame(-3, 1));
		assertFalse((new Board(3)).checkIfMarkWinsGame(0, 1));
		assertFalse((new Board(2)).checkIfMarkWinsGame(1, 1));
		assertFalse((new Board(3)).checkIfMarkWinsGame(1, 1));
		assertThrows(Exception.class,
				() -> (new Board(0)).checkIfMarkWinsGame(1, 1));
		assertThrows(Exception.class,
				() -> (new Board(3)).checkIfMarkWinsGame(1, -3));
	}
}

