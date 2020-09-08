package com.intuit.interview.tictactoe.dto.internal;

import com.intuit.interview.tictactoe.bo.PositionBO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Vageesh Hegde
 */
@AllArgsConstructor
@Getter
@Setter
public class PositionsAndScores
{
	private Integer score;
	private PositionBO point;
}
