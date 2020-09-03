package com.intuit.interview.tictactoe.dto.exception;

import com.intuit.interview.tictactoe.constants.ErrorCode;
import lombok.Getter;

@Getter
public class ServiceException extends Exception
{
	private static final long serialVersionUID = 4838434696661279327L;

	private final ErrorCode errorCode;

	public ServiceException(ErrorCode code, String msg, Throwable cause)
	{
		super(msg, cause);
		this.errorCode = code;
	}

	public ServiceException(ErrorCode code, String msg)
	{
		super(msg);
		this.errorCode = code;
	}
}
