package com.intuit.interview.tictactoe.bo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class GameTest
{
	@Test
	public void testSetBoard()
	{
		Board board = new Board(3);
		Game game = new Game();
		game.setBoard(board);
		assertSame(board, game.getBoard());
	}

	@Test
	public void testSetGameId()
	{
		Game game = new Game();
		game.setGameId("42");
		assertEquals("42", game.getGameId());
	}

	@Test
	public void testSetSize()
	{
		Game game = new Game();
		game.setSize(3);
		assertEquals(3, game.getSize());
	}

	@Test
	public void testSetWinner()
	{
		Game game = new Game();
		game.setWinner("winner");
		assertEquals("winner", game.getWinner());
	}
}

