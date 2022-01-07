package ai;

import board_construction.Board;

/* 
 * 
 */
public interface BoardEvaluator {

	// By convention, the most positive the number is - the most white is winning, by convention
	int evaluate(Board board, int depth);
	
}
