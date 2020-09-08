package com.intuit.interview.tictactoe.dto.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Vageesh Hegde
 */
@Getter
@Setter
@AllArgsConstructor
public class ServiceError
{
	@JsonProperty( "code")
	private String code;

	@JsonProperty("message")
	private String message;

	@JsonProperty("errors")
	private List<String> errors;

	public ServiceError()
	{
	}

	public ServiceError(String code, List<String> errors)
	{
		this.code = code;
		this.errors = errors;
	}
}
