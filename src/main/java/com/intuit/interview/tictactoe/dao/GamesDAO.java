package com.intuit.interview.tictactoe.dao;

import com.intuit.interview.tictactoe.bo.Game;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO for Games table.
 * Currently using in-memory DS.
 *
 * @author Vageesh Hegde
 */
public class GamesDAO
{
	private List<Game> games;

	@PostConstruct
	public void init()
	{
		games = new ArrayList<>();
	}

	public void registerGame(Game game)
	{
		games.add(game);
	}

	public Game getGameById(String gameId)
	{
		Optional<Game> currentGame = games.stream()
				.filter(game -> game.getGameId().equalsIgnoreCase(gameId))
				.findFirst();
		return currentGame.orElse(null);
	}

	public List<Game> getGames()
	{
		return games;
	}
}
