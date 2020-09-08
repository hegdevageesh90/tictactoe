package com.intuit.interview.tictactoe.dto.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Vageesh Hegde
 */
@AllArgsConstructor
@Getter
@Setter
public class GameBegunResponse
{
	private String message;
	private String gameId;
}
