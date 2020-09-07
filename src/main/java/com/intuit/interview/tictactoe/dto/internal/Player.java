package com.intuit.interview.tictactoe.dto.internal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player
{
	@JsonIgnore
	private String id;

	@JsonProperty("name")
	private String name;

	@JsonProperty("gender")
	private Gender gender;

	@JsonProperty("email")
	private String email;
}
