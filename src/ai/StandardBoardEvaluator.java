package ai;

import board_construction.Board;
import chess_pieces.ChessPiece;
import player.Player;

public class StandardBoardEvaluator implements BoardEvaluator {

	@Override public int evaluate(final Board board, final int depth) {
		/* I want to get the score from white and substract it from the score of black. If white has an advantage then 
		 * the score I get back will be a positive and if black has an advantage then the score I get back will be negative */
		int val = scorePlayer(board, board.whitePlayer(), depth) - scorePlayer(board, board.blackPlayer(), depth);
		return val;
	}
	
	private int scorePlayer(final Board board, final Player player, final int depth) {
		return pieceValue(player);
	}
	
	private static int pieceValue(final Player player) {
		int pieceValueScore = 0;
		for(final ChessPiece piece : player.getActivePiece()) {
			pieceValueScore += piece.getPieceValue();  
			// See ChessPiece class to understand the piece vales we retrieve here
		}
		return pieceValueScore;  
	}
	
	
	

}
