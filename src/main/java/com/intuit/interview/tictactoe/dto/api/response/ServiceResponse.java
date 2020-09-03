package com.intuit.interview.tictactoe.dto.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import static java.util.Objects.isNull;

@Getter
@Setter
public class ServiceResponse<T>
{
	public static final String OK = "OK";

	public static final String ERROR = "ERROR";

	@JsonProperty("status")
	private String status;

	@JsonProperty("payload")
	private T payload;

	@JsonProperty("error")
	private ServiceError error;

	public ServiceResponse()
	{
	}

	public ServiceResponse(String status, T payload)
	{
		this.status = status;
		this.payload = payload;
	}

	public ServiceResponse(T payload)
	{
		this.status = OK;
		this.payload = payload;
	}

	public ServiceResponse(T payload, ServiceError error)
	{
		status = isNull(error) ? OK : ERROR;
		this.payload = payload;
		this.error = error;
	}
}
