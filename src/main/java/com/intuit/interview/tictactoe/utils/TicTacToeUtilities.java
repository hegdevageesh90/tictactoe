package com.intuit.interview.tictactoe.utils;

import com.intuit.interview.tictactoe.dto.api.request.GameBeginRequest;
import com.intuit.interview.tictactoe.dto.api.request.MoveRequest;
import com.intuit.interview.tictactoe.dto.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.UUID;

import static com.intuit.interview.tictactoe.constants.ErrorCode.VALIDATION_FAILURE;
import static com.intuit.interview.tictactoe.utils.Constants.VALIDATION_ERROR;

public class TicTacToeUtilities
{
	public String generateUUID()
	{
		return UUID.randomUUID().toString();
	}

	public void validateGameBeginRequest(GameBeginRequest request)
			throws ServiceException
	{
		if (request.getSize() % 2 == 0 || StringUtils
				.isEmpty(request.getPlayer().getName()) || StringUtils
				.isEmpty(request.getPlayer().getEmail()) || StringUtils
				.isEmpty(request.getPlayer().getGender().name())) {
			throw new ServiceException(VALIDATION_FAILURE, VALIDATION_ERROR);
		}
	}

	public void validateMoveRequest(MoveRequest request) throws ServiceException
	{
		if (StringUtils.isEmpty(request.getGameId()) || Objects
				.isNull(request.getPosition())) {
			throw new ServiceException(VALIDATION_FAILURE, VALIDATION_ERROR);
		}
	}
}
