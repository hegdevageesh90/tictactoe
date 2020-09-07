package com.intuit.interview.tictactoe.bo;

import com.intuit.interview.tictactoe.dto.api.response.State;
import lombok.Getter;

import java.util.*;

import static com.intuit.interview.tictactoe.bo.PositionBO.EMPTY;

@Getter
public class Board
{
	private final int size;
	private int numberOfTurns;

	//2D array representing game board.
	private PositionBO[][] board;

	//Array representing current sums on the rows, columns, and diagonals of the game board.
	private int[] sums;

	//CTOR
	public Board(int size)
	{
		this.size = size;
		numberOfTurns = this.size * this.size;

		board = new PositionBO[this.size][this.size];

		for (PositionBO[] row : board) {
			Arrays.fill(row, EMPTY);
		}

		sums = new int[2 * this.size + 2];
	}

	public PositionBO getPosition(int row, int col)
	{
		return board[row][col];
	}

	//Set a marker at specified location.
	public void setPosition(int row, int col, PositionBO positionBO)
			throws Exception
	{
		if (row >= size || row < 0) {
			throw new Exception("Invalid move: row outside of board range.");
		}

		if (col >= size || col < 0) {
			throw new Exception("Invalid move: column outside of board range.");
		}

		if (board[row][col] != EMPTY) {
			throw new Exception(
					"Invalid move: selected location is already marked.");
		}

		board[row][col] = positionBO;

		sums[row] += positionBO.value;
		sums[size + col] += positionBO.value;

		// Main diagonal.
		if (row == col)
			sums[2 * size] += positionBO.value;

		if (size - col - 1 == row)
			sums[2 * size + 1] += positionBO.value;
	}

	//Checks to see if any player has won the game.
	public PositionBO gameWon()
	{
		for (int s : sums) {
			if (s == size) {
				return PositionBO.X;
			} else if (s == -size) {
				return PositionBO.O;
			}
		}

		return EMPTY;
	}

	public boolean isDraw()
	{
		boolean isDraw = true;
		for (PositionBO[] positionBOS : board) {
			for (PositionBO positionBO : positionBOS) {
				if (Objects.equals(positionBO, EMPTY)) {
					isDraw = false;
					break;
				}
			}
		}
		return isDraw;
	}

	//Gets the raw array representing the current game board
	public PositionBO[][] getBoard()
	{
		return board;
	}

	public boolean empty()
	{
		for (PositionBO[] row : board) {
			for (PositionBO m : row) {
				if (m != EMPTY) {
					return false;
				}
			}
		}
		return true;
	}

	public State boardPayload()
	{
		List<String> cells = new ArrayList<>();
		for (PositionBO[] positionBOS : board) {
			String rowCells = "";
			for (PositionBO positionBO : positionBOS) {
				rowCells += positionBO.mark == ' ' ? "E" : positionBO.mark;
			}
			cells.add(rowCells);
		}
		return new State(cells);
	}

	public int[] getSums()
	{
		return sums;
	}
}
