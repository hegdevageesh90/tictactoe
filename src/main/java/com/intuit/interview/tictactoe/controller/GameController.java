package com.intuit.interview.tictactoe.controller;

import com.intuit.interview.tictactoe.dto.api.request.MoveRequest;
import com.intuit.interview.tictactoe.dto.api.response.MoveResponse;
import com.intuit.interview.tictactoe.dto.api.response.ServiceResponse;
import com.intuit.interview.tictactoe.dto.exception.ServiceException;
import com.intuit.interview.tictactoe.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequestMapping("${tictactoe.manager.api.path}")
public class GameController
{
	@Autowired
	private GameService gameService;

	@PostMapping("/makeMove")
	@ResponseStatus(value = OK)
	public ServiceResponse<MoveResponse> makeAMove(
			@RequestBody
					MoveRequest request) throws ServiceException
	{
		MoveResponse moveResponse = gameService.registerUserMark(request);
		return new ServiceResponse<>(moveResponse);
	}
}
