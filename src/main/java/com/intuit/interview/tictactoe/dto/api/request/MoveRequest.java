package com.intuit.interview.tictactoe.dto.api.request;

import com.intuit.interview.tictactoe.dto.internal.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MoveRequest
{
	private String gameId;
	private Position position;
}
