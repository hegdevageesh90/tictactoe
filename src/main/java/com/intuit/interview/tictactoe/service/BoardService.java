package com.intuit.interview.tictactoe.service;

import com.intuit.interview.tictactoe.bo.Game;
import com.intuit.interview.tictactoe.dao.GamesDAO;
import com.intuit.interview.tictactoe.dto.api.response.StateResponse;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service Class for all board related functionalities.
 *
 * @author Vageesh Hegde
 */
@Service
@Getter
public class BoardService
{
	@Autowired
	private GamesDAO gamesDAO;

	public StateResponse getCurrentState(String gameId)
	{
		Game game = gamesDAO.getGameById(gameId);
		return new StateResponse(game.getBoard().boardPayload());
	}
}
