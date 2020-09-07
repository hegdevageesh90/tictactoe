package com.intuit.interview.tictactoe.controller;

import com.intuit.interview.tictactoe.dto.api.request.GameBeginRequest;
import com.intuit.interview.tictactoe.dto.api.response.BoardSizeResponse;
import com.intuit.interview.tictactoe.dto.api.response.GameBegunResponse;
import com.intuit.interview.tictactoe.dto.api.response.ServiceResponse;
import com.intuit.interview.tictactoe.dto.exception.ServiceException;
import com.intuit.interview.tictactoe.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequestMapping("${tictactoe.manager.api.path}")
public class GameManagerController
{
	@Autowired
	private GameService gameService;

	@PostMapping("/startGame")
	@ResponseStatus(value = CREATED)
	public ServiceResponse<GameBegunResponse> startGame(
			@RequestBody GameBeginRequest request) throws ServiceException
	{
		GameBegunResponse gameBegunResponse = gameService.startAGame(request);
		return new ServiceResponse<>(gameBegunResponse);
	}

	@GetMapping("/getSizes")
	@ResponseStatus(value = OK)
	public ServiceResponse<BoardSizeResponse> getBoardSizes()
			throws ServiceException
	{
		BoardSizeResponse boardSizeResponse = new BoardSizeResponse();
		return new ServiceResponse<>(boardSizeResponse);
	}
}
