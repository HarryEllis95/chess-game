package player;

import java.util.Collection;

import board_construction.Board;
import chess_pieces.ChessPiece;
import chess_pieces.Move;

public class BlackPlayer extends Player {

	public BlackPlayer(Board board, Collection<Move> whiteAllowedMoves, Collection<Move> blackAllowedMoves) {
		super(board, blackAllowedMoves, whiteAllowedMoves);
	}
	
	@Override public Collection<ChessPiece> getActivePiece() {
		return this.board.getBlackPieces();
	}
}
