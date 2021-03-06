package com.intuit.interview.tictactoe.dto.api.request;

import com.intuit.interview.tictactoe.dto.internal.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Vageesh Hegde
 */
@AllArgsConstructor
@Getter
@Setter
public class MarkRequest
{
	private String gameId;
	private Position position;
}
