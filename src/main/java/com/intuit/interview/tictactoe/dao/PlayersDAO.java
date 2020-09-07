package com.intuit.interview.tictactoe.dao;

import com.intuit.interview.tictactoe.dto.internal.Player;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

public class PlayersDAO
{
	private Set<Player> players;

	@PostConstruct
	public void init()
	{
		players = new HashSet<>();
	}

	public void registerPlayer(Player player)
	{
		players.add(player);
	}

	public Set<Player> getPlayers()
	{
		return players;
	}
}
