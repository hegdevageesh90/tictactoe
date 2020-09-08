package com.intuit.interview.tictactoe.dto.api.response;

import com.intuit.interview.tictactoe.dto.internal.BoardSize;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Vageesh Hegde
 */
@Getter
@Setter
public class BoardSizeResponse
{
	private String responseString;

	public BoardSizeResponse()
	{
		responseString = "Available sizes are : ";
		int count = 3;
		for (BoardSize boardSize : BoardSize.values())
		{
			responseString += boardSize.name() + "(" + count + "), ";
			count++;
		}
		responseString += "etc.";

	}
}
