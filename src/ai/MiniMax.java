package ai;

import board_construction.Board;
import board_construction.BoardTransform;
import chess_pieces.Move;

/* The ai in my chess engine will implement the co-recursive minimax (minmax) algorithm. This is based around a tactic
 *  in which individuals attempt either to minimise their own maximum losses or to reduce the most an opponent will gain. 
 * 
 * There is lots of material which explains this but the following video is one which I think outlines
 * the concept clearly: https://www.youtube.com/watch?v=KU9Ch59-4vw                                        */

public class MiniMax implements MoveTheory {
	
	private final BoardEvaluator boardEvaluator;
	
	public MiniMax() {
		this.boardEvaluator = new StandardBoardEvaluator;
	}
	
	@Override public String toString() {
		return "MiniMax";
	}
	
	
	@Override public Move execute(Board board, int depth) {
		
		final long startTime = System.currentTimeMillis();
		Move bestMove = null;
		
		int highestSeenValue = Integer.MIN_VALUE;
		int lowestSeenValue = Integer.MAX_VALUE;
		int currentValue;
		
		System.out.println(board.currentPlayer() + " CALCULATING with a depth of : " + depth);
		
		int numMoves = board.currentPlayer().getAllowedMoves().size();
		
		for(final Move move : board.currentPlayer().getAllowedMoves()) {
			/* What I say here is that if the board you came from the current player was white, then your next move
			 * will be a minimising move. Remembering white is the 'maximising' player, black is the 'minimising' player */
			final BoardTransform boardTransform = board.currentPlayer().makeMove(move);
			if(boardTransform.getMoveStatus().isDone()) {
				// Begin recursion, using ternary operator to determine which method we need to call 
				currentValue = board.currentPlayer().getColour().isWhite() ? 
						min(boardTransform.getTransformedBoard(), depth - 1) : max(boardTransform.getTransformedBoard(), depth - 1);
				
				if(board.currentPlayer().getColour().isWhite() && currentValue >= highestSeenValue) {
					highestSeenValue = currentValue;
					bestMove = move;
				} else if(board.currentPlayer().getColour().isBlack() && currentValue <= lowestSeenValue) {
					lowestSeenValue = currentValue;
					bestMove = move;
				}
			}
		}
		long executionTime = System.currentTimeMillis() - startTime;
		return bestMove;
	}
	
	
	// Notice the recursive behaviour - min calls max and max calls min. 
	public int min(final Board board, final int depth) {
		if(depth == 0) {
			return this.boardEvaluator.evaluate(board, depth);
		}
		int lowestSeenValue = Integer.MAX_VALUE;  // We give this an initial arbitrarily high value
		/* Now go through and 'score' the allowed moves on current board. We return that lowest valued move.
		 * This is why lowestSeenValue must start large, so we get values of lower values than it returned */
		for(final Move move : board.currentPlayer().getAllowedMoves()) {
			final BoardTransform boardTransform = board.currentPlayer().makeMove(move);
			if(boardTransform.getMoveStatus().isDone()) {
				final int currentValue = max(boardTransform.getTransformedBoard(), depth - 1);
				if(currentValue <= lowestSeenValue) {
					lowestSeenValue = currentValue;
				}
			}
		}
		return lowestSeenValue;
	}
	
	public int max(final Board board, final int depth) {
		if(depth == 0) {
			return this.boardEvaluator.evaluate(board, depth);
		}
		int highestSeenValue = Integer.MIN_VALUE;
		for(final Move move : board.currentPlayer().getAllowedMoves()) {
			final BoardTransform boardTransform = board.currentPlayer().makeMove(move);
			if(boardTransform.getMoveStatus().isDone()) {
				final int currentValue = min(boardTransform.getTransformedBoard(), depth - 1);
				if(currentValue >= highestSeenValue) {
				 highestSeenValue = currentValue;
				}
			}
		}
		return highestSeenValue;
	}

}
