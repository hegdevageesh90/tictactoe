package com.intuit.interview.tictactoe.service;

import com.intuit.interview.tictactoe.bo.Board;
import com.intuit.interview.tictactoe.bo.Game;
import com.intuit.interview.tictactoe.bo.PositionBO;
import com.intuit.interview.tictactoe.dao.GamePlayerMapping;
import com.intuit.interview.tictactoe.dao.GamesDAO;
import com.intuit.interview.tictactoe.dao.PlayersDAO;
import com.intuit.interview.tictactoe.dto.api.request.GameBeginRequest;
import com.intuit.interview.tictactoe.dto.api.request.MoveRequest;
import com.intuit.interview.tictactoe.dto.api.response.GameBegunResponse;
import com.intuit.interview.tictactoe.dto.api.response.MoveResponse;
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
import static com.intuit.interview.tictactoe.constants.ErrorCode.*;
import static com.intuit.interview.tictactoe.utils.Constants.*;

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

	public MoveResponse registerUserMark(MoveRequest request)
			throws ServiceException
	{
		utilities.validateMoveRequest(request);
		Position position = request.getPosition();
		int[] moveArr = new int[] { position.getRow(), position.getCol() };

		Game currentGame = gamesDAO.getGameById(request.getGameId());
		if (Objects.isNull(currentGame)) {
			log.error("{}", NO_GAME_ERROR);
			throw new ServiceException(NO_GAME, NO_GAME_ERROR);
		}
		Board board = currentGame.getBoard();

		try {
			board.setPosition(moveArr[0], moveArr[1], X);

			if (board.isDraw()) {
				currentGame.setWinner("Draw");
				return new MoveResponse(DRAW,
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
			return new MoveResponse(
					winner.mark == 'X' ? "User" : "Computer" + GAME_WON,
					currentGame.getBoard().boardPayload());
		}

		if (board.isDraw()) {
			currentGame.setWinner("Draw");
			return new MoveResponse(DRAW,
					currentGame.getBoard().boardPayload());
		}

		return new MoveResponse(MARK_OK, currentGame.getBoard().boardPayload());
	}

	private void computerMarkBoard(Board board) throws ServiceException
	{
		// For the sake of making the game easier, if O starts, it will select a random position.
		int size = board.getSize();
		if (board.empty()) {
			Random rand = new Random();
			doMakeAMove(rand.nextInt(size), rand.nextInt(size), board);
			return;
		}

		// Favor the middle slot if it is not taken yet.
		if (board.getBoard()[1][1] == EMPTY) {
			doMakeAMove(1, 1, board);
			return;
		}

		int largest = 0;
		for (int i = 0; i < board.getSums().length; i++)
			if (board.getSums()[i] > board.getSums()[largest])
				largest = i;

		// If Computer is "winning", let's be greedy.
		int smallest = 0;
		for (int i = 0; i < board.getSums().length; i++)
			if (board.getSums()[i] < board.getSums()[smallest])
				smallest = i;

		Random rand = new Random();
		if (board.getSums()[smallest] < -(size / 2 + rand.nextInt(1))
				&& -(board.getSums()[smallest]) > board.getSums()[largest])
			largest = smallest;

			// Choose a random slot if sum of row/col/diag is not more than half the board size.
		else if (board.getSums()[largest] < size / 2 + 1) {
			rand = new Random();
			try {
				doMakeAMove(rand.nextInt(size), rand.nextInt(size), board);
			} catch (Exception e) {
				doMakeAMove(rand.nextInt(size), rand.nextInt(size), board);
			}

			return;
		}

		if (largest < board.getSize()) {
			// It's a row.
			PositionBO[] row = board.getBoard()[largest];

			for (int pos = 0; pos < row.length; pos++) {
				if (row[pos] == EMPTY) {
					try {
						board.setPosition(largest, pos, O);
						return;
					} catch (Exception e) {
						String message = "Computer failed to make it's move";
						e.printStackTrace();
						log.error(message);
						throw new ServiceException(COMPUTER_MARK_FAILURE,
								message, e);
					}
				}
			}
		} else if (largest >= size && largest < 2 * size) {
			int col = largest - size;

			for (int r = 0; r < size; r++) {
				try {
					board.setPosition(r, col, O);
					return;
				} catch (Exception e) {
					//Do nothing. We want to keep going.
				}
			}
		} else {
			if (largest == size * 2) {
				for (int i = 0; i < size; i++) {
					try {
						board.setPosition(i, i, O);
						return;
					} catch (Exception e) {
						//Do nothing. We want to keep going.
					}
				}
			} else if (largest == size * 2 + 1) {
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

	private void doMakeAMove(int x, int y, Board board) throws ServiceException
	{
		try {
			board.setPosition(x, y, O);
		} catch (Exception e) {
			String message = "Computer failed to make it's move";
			e.printStackTrace();
			log.error(message);
			throw new ServiceException(COMPUTER_MARK_FAILURE, message, e);
		}
	}
}
