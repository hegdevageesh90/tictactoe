package com.intuit.interview.tictactoe.dto.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.intuit.interview.tictactoe.dto.exception.ServiceException;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.Objects;

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

	public ServiceResponse(T payload, ServiceException exception)
	{
		status = isNull(exception) ? OK : ERROR;
		this.payload = payload;
		this.error = new ServiceError(exception.getErrorCode().name(),
				exception.getMessage(), Objects.isNull(exception.getCause()) ?
				null :
				Collections.singletonList(exception.getCause().getMessage()));
	}
}
