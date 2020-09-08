package com.intuit.interview.tictactoe.controller;

import com.intuit.interview.tictactoe.dto.api.request.MarkRequest;
import com.intuit.interview.tictactoe.dto.api.response.MarkResponse;
import com.intuit.interview.tictactoe.dto.api.response.ServiceResponse;
import com.intuit.interview.tictactoe.dto.api.response.StateResponse;
import com.intuit.interview.tictactoe.dto.exception.ServiceException;
import com.intuit.interview.tictactoe.service.BoardService;
import com.intuit.interview.tictactoe.service.GameService;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

/**
 * REST Controller class that exposes Game related APIs
 *
 * @author Vageesh Hegde
 */
@Slf4j
@RestController
@RequestMapping("${tictactoe.game.api.path}")
public class GameController
{
	@Autowired
	private GameService gameService;

	@Autowired
	private BoardService boardService;

	@PostMapping("/makeMark")
	@ResponseStatus(value = OK)
	public ServiceResponse<MarkResponse> makeAMark(
			@RequestBody
					MarkRequest request)
	{
		return Try.ofCallable(() -> {
			MarkResponse markResponse = gameService.registerUserMark(request);
			return new ServiceResponse<>(markResponse);
		}).getOrElseGet(e -> new ServiceResponse<MarkResponse>(null,
				(ServiceException) e));
	}

	@GetMapping("/currentState/{gameId}")
	@ResponseStatus(value = OK)
	public ServiceResponse<StateResponse> getCurrentState(
			@PathVariable("gameId") String gameId)
	{
		return Try.ofCallable(() -> {
			StateResponse stateResponse = boardService.getCurrentState(gameId);
			return new ServiceResponse<>(stateResponse);
		}).getOrElseGet(e -> new ServiceResponse<StateResponse>(null,
				(ServiceException) e));
	}

	@PostMapping("/checkWin")
	@ResponseStatus(value = OK)
	public ServiceResponse<MarkResponse> checkWin(
			@RequestBody
					MarkRequest request)
	{
		return Try.ofCallable(() -> {
			MarkResponse markResponse = gameService.checkIfMarkWinsGame(request);
			return new ServiceResponse<>(markResponse);
		}).getOrElseGet(e -> new ServiceResponse<MarkResponse>(null,
				(ServiceException) e));
	}
}
