package com.intuit.interview.tictactoe.dao;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

public class GamePlayerMapping
{
	private Map<String, String> activeGamesMap;

	@PostConstruct
	public void init()
	{
		activeGamesMap = new HashMap<>();
	}

	public void registerPlayerToGame(String gameId, String playerId)
	{
		activeGamesMap.put(playerId, gameId);
	}
}
