package com.intuit.interview.tictactoe.dao;

import com.intuit.interview.tictactoe.bo.Game;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

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

	public List<Game> getGames()
	{
		return games;
	}
}
