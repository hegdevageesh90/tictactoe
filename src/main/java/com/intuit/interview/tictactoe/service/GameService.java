package com.intuit.interview.tictactoe.service;

import com.intuit.interview.tictactoe.bo.Board;
import com.intuit.interview.tictactoe.bo.Game;
import com.intuit.interview.tictactoe.bo.PositionBO;
import com.intuit.interview.tictactoe.dao.GamePlayerMapping;
import com.intuit.interview.tictactoe.dao.GamesDAO;
import com.intuit.interview.tictactoe.dao.PlayersDAO;
import com.intuit.interview.tictactoe.dto.api.request.GameBeginRequest;
import com.intuit.interview.tictactoe.dto.api.request.MarkRequest;
import com.intuit.interview.tictactoe.dto.api.response.GameBegunResponse;
import com.intuit.interview.tictactoe.dto.api.response.MarkResponse;
import com.intuit.interview.tictactoe.dto.exception.ServiceException;
import com.intuit.interview.tictactoe.dto.internal.Player;
import com.intuit.interview.tictactoe.dto.internal.Position;
import com.intuit.interview.tictactoe.utils.TicTacToeUtilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;

import static com.intuit.interview.tictactoe.bo.PositionBO.*;
import static com.intuit.interview.tictactoe.utils.ErrorCode.*;
import static com.intuit.interview.tictactoe.utils.Constants.*;

/**
 * Service Class for all Game related functionalities.
 *
 * @author Vageesh Hegde
 */
@Slf4j
@Service
public class GameService
{
	@Autowired
	private PlayersDAO playersDAO;

	@Autowired
	private GamesDAO gamesDAO;

	@Autowired
	private GamePlayerMapping gamePlayerMapping;

	@Autowired
	private TicTacToeUtilities utilities;

	public GameBegunResponse startAGame(GameBeginRequest request)
			throws ServiceException
	{
		utilities.validateGameBeginRequest(request);
		Game game;
		Player player = request.getPlayer();
		player.setId(utilities.generateUUID());
		playersDAO.registerPlayer(player);
		try {
			String gameId = utilities.generateUUID();
			Board board = new Board(request.getSize());
			game = new Game(gameId, board, request.getSize(), "");
			gamesDAO.registerGame(game);
			gamePlayerMapping.registerPlayerToGame(gameId, player.getId());
		} catch (Exception e) {
			log.error("Could not start game due to error : {}", e.getMessage());
			e.printStackTrace();
			throw new ServiceException(GAME_START_FAILURE, GAME_START_FAILED,
					e);
		}

		return new GameBegunResponse(GAME_STARTED, game.getGameId());
	}

	public MarkResponse registerUserMark(MarkRequest request)
			throws ServiceException
	{
		utilities.validateMarkRequest(request);
		Position position = request.getPosition();
		int[] markArr = new int[] { position.getRow(), position.getCol() };

		Game currentGame = gamesDAO.getGameById(request.getGameId());
		if (Objects.isNull(currentGame)) {
			log.error("{}", NO_GAME_ERROR);
			throw new ServiceException(NO_GAME, NO_GAME_ERROR);
		}
		Board board = currentGame.getBoard();

		try {
			board.setPosition(markArr[0], markArr[1], X);

			if (board.isDraw()) {
				currentGame.setWinner("Draw");
				return new MarkResponse(DRAW,
						currentGame.getBoard().boardPayload());
			}

		} catch (Exception e) {
			log.error("{}", e.getMessage());
			throw new ServiceException(PLAYER_MARK_FAILURE, PLAYER_MARK_NOK, e);
		}

		//human mark done. computer makes it mark now.
		try {
			computerMarkBoard(board);
		} catch (ServiceException e) {
			log.error("{}", e.getMessage());
			throw new ServiceException(COMPUTER_MARK_FAILURE, COMPUTER_MARK_NOK,
					e);
		}

		PositionBO winner = board.gameWon();

		if (winner != EMPTY) {
			currentGame.setWinner(winner.mark == 'X' ? "User" : "Computer");
			return new MarkResponse(
					winner.mark == 'X' ? "User" : "Computer" + GAME_WON,
					currentGame.getBoard().boardPayload());
		}

		if (board.isDraw()) {
			currentGame.setWinner("Draw");
			return new MarkResponse(DRAW,
					currentGame.getBoard().boardPayload());
		}

		return new MarkResponse(MARK_OK, currentGame.getBoard().boardPayload());
	}

	private void computerMarkBoard(Board board) throws ServiceException
	{
		int size = board.getSize();

		// Favor the middle slot if it is not taken yet.
		if (board.getBoard()[1][1] == EMPTY) {
			doMakeAMark(1, 1, board);
			return;
		}

		int largestIndex = 0;
		for (int i = 0; i < board.getSums().length; i++)
			if (board.getSums()[i] > board.getSums()[largestIndex])
				largestIndex = i;

		// If Computer is "winning", let's be greedy.
		int smallestIndex = 0;
		for (int i = 0; i < board.getSums().length; i++)
			if (board.getSums()[i] < board.getSums()[smallestIndex])
				smallestIndex = i;

		Random rand = new Random();
		if (board.getSums()[smallestIndex] < -(size / 2 + rand.nextInt(1))
				&& -(board.getSums()[smallestIndex]) > board
				.getSums()[largestIndex])
			largestIndex = smallestIndex;

			// Choose a random slot if sum of row/col/diag is not more than half the board size.
		else if (board.getSums()[largestIndex] < size / 2 + 1) {
			rand = new Random();
			try {
				doMakeAMark(rand.nextInt(size), rand.nextInt(size), board);
			} catch (Exception e) {
				doMakeAMark(rand.nextInt(size), rand.nextInt(size), board);
			}
			return;
		}

		if (largestIndex < board.getSize()) {
			// It's a row.
			PositionBO[] row = board.getBoard()[largestIndex];

			for (int pos = 0; pos < row.length; pos++) {
				if (row[pos] == EMPTY) {
					try {
						board.setPosition(largestIndex, pos, O);
						return;
					} catch (Exception e) {
						String message = "Computer failed to make it's mark";
						e.printStackTrace();
						log.error(message);
						throw new ServiceException(COMPUTER_MARK_FAILURE,
								message, e);
					}
				}
			}
		} else if (largestIndex >= size && largestIndex < 2 * size) {
			int col = largestIndex - size;

			for (int r = 0; r < size; r++) {
				try {
					board.setPosition(r, col, O);
					return;
				} catch (Exception e) {
					//Do nothing. We want to keep going.
				}
			}
		} else {
			if (largestIndex == size * 2) {
				for (int i = 0; i < size; i++) {
					try {
						board.setPosition(i, i, O);
						return;
					} catch (Exception e) {
						//Do nothing. We want to keep going.
					}
				}
			} else if (largestIndex == size * 2 + 1) {
				for (int r = 0, c = size - 1; r < size && c >= 0; r++, c--) {
					try {
						board.setPosition(r, c, O);
						return;
					} catch (Exception e) {
						//Do nothing. We want to keep going.
					}
				}
			}
		}

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				try {
					board.setPosition(i, j, O);
					return;
				} catch (Exception e) {
					//Do nothing. We want to keep going.
				}
			}
		}
	}

	public MarkResponse checkIfMarkWinsGame(MarkRequest request)
			throws ServiceException
	{

		Position position = request.getPosition();
		int[] markArr = new int[] { position.getRow(), position.getCol() };

		Game currentGame = gamesDAO.getGameById(request.getGameId());
		if (Objects.isNull(currentGame)) {
			log.error("{}", NO_GAME_ERROR);
			throw new ServiceException(NO_GAME, NO_GAME_ERROR);
		}
		Board board = currentGame.getBoard();

		try {
			return board.checkIfMarkWinsGame(markArr[0], markArr[1]) ?
					new MarkResponse(MARK_WINS_GAME,
							currentGame.getBoard().boardPayload()) :
					new MarkResponse(MARK_NOT_WINS_GAME,
							currentGame.getBoard().boardPayload());

		} catch (Exception e) {
			log.error("{}", e.getMessage());
			throw new ServiceException(PLAYER_MARK_FAILURE, PLAYER_MARK_NOK, e);
		}
	}

	private void doMakeAMark(int x, int y, Board board) throws ServiceException
	{
		try {
			board.setPosition(x, y, O);
		} catch (Exception e) {
			String message = "Computer failed to make it's mark";
			e.printStackTrace();
			log.error(message);
			throw new ServiceException(COMPUTER_MARK_FAILURE, message, e);
		}
	}
}
