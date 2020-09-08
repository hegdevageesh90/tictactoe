package com.intuit.interview.tictactoe.bo;

/**
 * Position BO : to keep track of the positions
 *
 * @author Vageesh Hegde
 */
public enum PositionBO
{
	EMPTY(0, ' '), X(1, 'X'), O(-1, 'O');

	public int value;
	public char mark;

	PositionBO(int value, char mark)
	{
		this.value = value;
		this.mark = mark;
	}

	//switches the turn
	public static PositionBO switchPlayer(PositionBO m)
	{
		if (m == X)
			return O;
		else if (m == O)
			return X;
		return EMPTY;
	}
}
