package com.intuit.interview.tictactoe.bo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Game
{
	private String gameId;
	private Board board;
	private int size;
	private String winner;
}
