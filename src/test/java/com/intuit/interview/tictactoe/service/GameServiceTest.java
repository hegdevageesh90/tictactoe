package com.intuit.interview.tictactoe.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalMatchers.or;
import static org.mockito.Mockito.isA;
import static org.mockito.Mockito.isNull;
import static org.mockito.Mockito.when;

import com.intuit.interview.tictactoe.bo.Board;
import com.intuit.interview.tictactoe.bo.Game;
import com.intuit.interview.tictactoe.dao.GamePlayerMapping;
import com.intuit.interview.tictactoe.dao.GamesDAO;
import com.intuit.interview.tictactoe.dao.PlayersDAO;
import com.intuit.interview.tictactoe.dto.api.request.GameBeginRequest;
import com.intuit.interview.tictactoe.dto.api.request.MarkRequest;
import com.intuit.interview.tictactoe.dto.api.response.GameBegunResponse;
import com.intuit.interview.tictactoe.dto.exception.ServiceException;
import com.intuit.interview.tictactoe.dto.internal.Player;
import com.intuit.interview.tictactoe.dto.internal.Position;
import com.intuit.interview.tictactoe.utils.TicTacToeUtilities;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = { GameService.class, GamePlayerMapping.class,
		GamesDAO.class, PlayersDAO.class, TicTacToeUtilities.class })
@ExtendWith(SpringExtension.class)
public class GameServiceTest
{
	@MockBean
	private GamePlayerMapping gamePlayerMapping;
	@Autowired
	private GameService gameService;
	@MockBean
	private GamesDAO gamesDAO;
	@MockBean
	private PlayersDAO playersDAO;
	@MockBean
	private TicTacToeUtilities ticTacToeUtilities;

	@Test
	public void testStartAGameThrowsException() throws ServiceException
	{
		when(this.ticTacToeUtilities.generateUUID()).thenReturn("foo");
		GameBeginRequest gameBeginRequest = new GameBeginRequest();
		gameBeginRequest.setSize(-1);
		gameBeginRequest.setPlayer(new Player());
		assertThrows(ServiceException.class,
				() -> this.gameService.startAGame(gameBeginRequest));
	}

	@Test
	public void testStartAGameSuccessfully() throws ServiceException
	{
		when(this.ticTacToeUtilities.generateUUID()).thenReturn("foo");
		GameBeginRequest gameBeginRequest = new GameBeginRequest();
		gameBeginRequest.setPlayer(new Player());
		GameBegunResponse actualStartAGameResult = this.gameService
				.startAGame(gameBeginRequest);
		assertEquals("Game begun successfully. Make your first mark",
				actualStartAGameResult.getMessage());
		assertEquals("foo", actualStartAGameResult.getGameId());
		assertEquals("foo", gameBeginRequest.getPlayer().getId());
	}

	@Test
	public void testRegisterUserMarkFails() throws ServiceException
	{
		when(this.gamesDAO.getGameById(or(isA(String.class), isNull())))
				.thenReturn(null);
		assertThrows(ServiceException.class, () -> this.gameService
				.registerUserMark(new MarkRequest("42", new Position(1, 1))));
	}

	@Test
	public void testRegisterUserMarkFails2() throws ServiceException
	{
		when(this.gamesDAO.getGameById(or(isA(String.class), isNull())))
				.thenReturn(new Game());
		MarkRequest markRequest = new MarkRequest("42", null);
		markRequest.setPosition(new Position(1, 1));
		assertThrows(ServiceException.class,
				() -> this.gameService.registerUserMark(markRequest));
	}

	@Test
	public void testRegisterUserMarkSucceeds() throws ServiceException
	{
		when(this.gamesDAO.getGameById(or(isA(String.class), isNull())))
				.thenReturn(new Game("42", new Board(3), 3, "winner"));
		MarkRequest markRequest = new MarkRequest("42", null);
		markRequest.setPosition(new Position(1, 1));
		assertEquals(
				"Mark was successful. Computer made it's mark. Make your next mark ",
				this.gameService.registerUserMark(markRequest).getMessage());
	}

	@Test
	public void testRegisterUserMarkSucceeds2() throws ServiceException
	{
		when(this.gamesDAO.getGameById(or(isA(String.class), isNull())))
				.thenReturn(new Game("E", new Board(3), 3, "winner"));
		assertEquals(
				"Mark was successful. Computer made it's mark. Make your next mark ",
				this.gameService.registerUserMark(
						new MarkRequest("42", new Position(1, 1)))
						.getMessage());
	}

	@Test
	public void testRegisterUserMarkFails3() throws ServiceException
	{
		when(this.gamesDAO.getGameById(or(isA(String.class), isNull())))
				.thenReturn(new Game("42", new Board(0), 3, "winner"));
		assertThrows(ServiceException.class, () -> this.gameService
				.registerUserMark(new MarkRequest("42", new Position(1, 1))));
	}

	@Test
	public void testRegisterUserMarkSucceeds3() throws ServiceException
	{
		when(this.gamesDAO.getGameById(or(isA(String.class), isNull())))
				.thenReturn(new Game("42", new Board(3), 3, "winner"));
		assertEquals(
				"Mark was successful. Computer made it's mark. Make your next mark ",
				this.gameService.registerUserMark(
						new MarkRequest("42", new Position(0, 1)))
						.getMessage());
	}

	@Test
	public void testRegisterUserMarkFails4() throws ServiceException
	{
		when(this.gamesDAO.getGameById(or(isA(String.class), isNull())))
				.thenReturn(new Game());
		assertThrows(ServiceException.class, () -> this.gameService
				.registerUserMark(new MarkRequest("42", new Position(1, 1))));
	}

	@Test
	public void testRegisterUserMarkSucceeds4() throws ServiceException
	{
		when(this.gamesDAO.getGameById(or(isA(String.class), isNull())))
				.thenReturn(new Game("42", new Board(3), 3, "winner"));
		assertEquals(
				"Mark was successful. Computer made it's mark. Make your next mark ",
				this.gameService.registerUserMark(
						new MarkRequest("42", new Position(1, 1)))
						.getMessage());
	}

	@Test
	public void testCheckIfMarkWinsGameSucceeds() throws ServiceException
	{
		when(this.gamesDAO.getGameById(or(isA(String.class), isNull())))
				.thenReturn(new Game("42", new Board(3), 3, "winner"));
		MarkRequest markRequest = new MarkRequest("42", null);
		markRequest.setPosition(new Position(1, 1));
		assertEquals("This mark cannot win the game for user",
				this.gameService.checkIfMarkWinsGame(markRequest).getMessage());
	}

	@Test
	public void testCheckIfMarkWinsGameFails() throws ServiceException
	{
		when(this.gamesDAO.getGameById(or(isA(String.class), isNull())))
				.thenReturn(new Game());
		assertThrows(ServiceException.class, () -> this.gameService
				.checkIfMarkWinsGame(
						new MarkRequest("42", new Position(1, 1))));
	}

	@Test
	public void testCheckIfMarkWinsGameFails2() throws ServiceException
	{
		when(this.gamesDAO.getGameById(or(isA(String.class), isNull())))
				.thenReturn(new Game("42", new Board(0), 3, "winner"));
		assertThrows(ServiceException.class, () -> this.gameService
				.checkIfMarkWinsGame(
						new MarkRequest("42", new Position(1, 1))));
	}

	@Test
	public void testCheckIfMarkWinsGameFails3() throws ServiceException
	{
		when(this.gamesDAO.getGameById(or(isA(String.class), isNull())))
				.thenReturn(null);
		assertThrows(ServiceException.class, () -> this.gameService
				.checkIfMarkWinsGame(
						new MarkRequest("42", new Position(1, 1))));
	}

	@Test
	public void testCheckIfMarkWinsGameFails4() throws ServiceException
	{
		when(this.gamesDAO.getGameById(or(isA(String.class), isNull())))
				.thenReturn(new Game());
		MarkRequest markRequest = new MarkRequest("42", null);
		markRequest.setPosition(new Position(1, 1));
		assertThrows(ServiceException.class,
				() -> this.gameService.checkIfMarkWinsGame(markRequest));
	}

	@Test
	public void testCheckIfMarkWinsGameSucceeds2() throws ServiceException
	{
		when(this.gamesDAO.getGameById(or(isA(String.class), isNull())))
				.thenReturn(new Game("42", new Board(3), 3, "winner"));
		assertEquals("This mark cannot win the game for user", this.gameService
				.checkIfMarkWinsGame(new MarkRequest("42", new Position(1, 1)))
				.getMessage());
	}
}

