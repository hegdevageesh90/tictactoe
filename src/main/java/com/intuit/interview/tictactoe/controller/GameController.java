package com.intuit.interview.tictactoe.controller;

import com.intuit.interview.tictactoe.dto.api.request.MoveRequest;
import com.intuit.interview.tictactoe.dto.api.response.MoveResponse;
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
	public ServiceResponse<MoveResponse> makeAMark(
			@RequestBody MoveRequest request)
	{
		return Try.ofCallable(() -> {
			MoveResponse moveResponse = gameService.registerUserMark(request);
			return new ServiceResponse<>(moveResponse);
		}).getOrElseGet(e -> new ServiceResponse<MoveResponse>(null,
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
	public ServiceResponse<MoveResponse> checkWin(
			@RequestBody MoveRequest request)
	{
		return Try.ofCallable(() -> {
			MoveResponse moveResponse = gameService.checkIfMarkWinsGame(request);
			return new ServiceResponse<>(moveResponse);
		}).getOrElseGet(e -> new ServiceResponse<MoveResponse>(null,
				(ServiceException) e));
	}
}
