package com.intuit.interview.tictactoe.dto.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MoveResponse
{
	private String message;
	private State currentState;
}
