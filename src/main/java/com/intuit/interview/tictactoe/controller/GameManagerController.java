package com.intuit.interview.tictactoe.controller;

import com.intuit.interview.tictactoe.dto.api.request.BoardSizeRequest;
import com.intuit.interview.tictactoe.dto.api.request.GameBeginRequest;
import com.intuit.interview.tictactoe.dto.api.response.BoardSizeResponse;
import com.intuit.interview.tictactoe.dto.api.response.GameBegunResponse;
import com.intuit.interview.tictactoe.dto.api.response.ServiceResponse;
import com.intuit.interview.tictactoe.dto.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequestMapping("${tictactoe.manager.api.path}")
public class GameManagerController
{
	@PostMapping("/startGame")
	@ResponseStatus(value = CREATED)
	public ServiceResponse<GameBegunResponse> startGame(
			@RequestBody
					GameBeginRequest request) throws ServiceException
	{
		return new ServiceResponse<>();
	}

	@PostMapping("/setSize")
	@ResponseStatus(value = OK)
	public ServiceResponse<String> setBoardSize(
			@RequestBody
					BoardSizeRequest request) throws ServiceException
	{
		return new ServiceResponse<>();
	}

	@GetMapping("/getSizes")
	@ResponseStatus(value = OK)
	public ServiceResponse<BoardSizeResponse> getBoardSizes()
			throws ServiceException
	{
		return new ServiceResponse<>();
	}
}
