package com.intuit.interview.tictactoe;

import com.intuit.interview.tictactoe.dao.GamePlayerMapping;
import com.intuit.interview.tictactoe.dao.GamesDAO;
import com.intuit.interview.tictactoe.dao.PlayersDAO;
import com.intuit.interview.tictactoe.utils.TicTacToeUtilities;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TicTacToeConfiguration
{
	@Bean
	public TicTacToeUtilities utilities()
	{
		return new TicTacToeUtilities();
	}

	@Bean
	public GamesDAO gamesDAO()
	{
		return new GamesDAO();
	}

	@Bean
	public PlayersDAO playersDAO()
	{
		return new PlayersDAO();
	}

	@Bean
	public GamePlayerMapping gamePlayerMapping()
	{
		return new GamePlayerMapping();
	}
}
