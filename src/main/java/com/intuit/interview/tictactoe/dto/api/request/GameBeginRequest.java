package com.intuit.interview.tictactoe.dto.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.intuit.interview.tictactoe.dto.internal.Player;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameBeginRequest
{
	@JsonProperty("player")
	private Player player;

	@JsonProperty("size")
	private int size;
}
