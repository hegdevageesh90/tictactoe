package com.intuit.interview.tictactoe.dto.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Vageesh Hegde
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class State
{
	private List<String> cells;
}
