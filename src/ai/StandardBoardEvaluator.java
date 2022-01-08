package ai;

import board_construction.Board;
import chess_pieces.ChessPiece;
import player.Player;

public class StandardBoardEvaluator implements BoardEvaluator {

	private static final int CHECK_BONUS = 40;
	private static final int CHECK_MATE_BONUS = 5000;
	private static final int DEPTH_BONUS = 100;
	private static final int CASTLE_BONUS = 60;
	
	@Override public int evaluate(final Board board, final int depth) {
		/* I want to get the score from white and substract it from the score of black. If white has an advantage then 
		 * the score I get back will be a positive and if black has an advantage then the score I get back will be negative */
		int val = scorePlayer(board, board.whitePlayer(), depth) - scorePlayer(board, board.blackPlayer(), depth);
		return val;
	}
	
	private int scorePlayer(final Board board, final Player player, final int depth) {
		return pieceValue(player) + 
				mobility(player) + 
				check(player) + 
				checkmate(player, depth);
	}
	
	/* mobility is used to represent how many legal moves (options) does the player have in the current position */
	private static int mobility(Player player) {
		return player.getAllowedMoves().size();
	}
	
	private static int check(final Player player) {
		return player.getOpponent().isInCheck() ? CHECK_BONUS : 0;
	}
	
	private static int checkmate(final Player player, final int depth) {
		/* Notice that CHECK_MATE_BONUS is weighted by depthBonus, as the less depth we need to traverse to 
		 * find the check mate, the more significant and advantage that side has and so we need to give a greater
		 * value to represent this.	 */
		return player.getOpponent().isInCheckMate() ? CHECK_MATE_BONUS * depthBonus(depth) : 0;
	}
	
	private static int castled(Player player) {
		return player.isCastled() ? CASTLE_BONUS : 0;
	}
	
	private static int pieceValue(final Player player) {
		int pieceValueScore = 0;
		for(final ChessPiece piece : player.getActivePiece()) {
			pieceValueScore += piece.getPieceValue();  
			// See ChessPiece class to understand the piece vales we retrieve here
		}
		return pieceValueScore;  
	}
	
	private static int depthBonus(int depth) {
		return depth == 0 ? 1 : DEPTH_BONUS * depth;
	}
	
	
	

}
