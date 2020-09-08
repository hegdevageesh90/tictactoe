package com.intuit.interview.tictactoe.dao;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * DAO for Game <-> Player mapping. Is an intersection between Game and Player
 * tables
 *
 * @author Vageesh Hegde
 */
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
