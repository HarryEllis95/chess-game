package player;

import java.util.Collection;

import board_construction.Board;
import chess_pieces.ChessPiece;
import chess_pieces.Move;


public class WhitePlayer extends Player {
	
	public WhitePlayer(Board board, Collection<Move> whiteAllowedMoves, Collection<Move> blackAllowedMoves) {
		super(board, whiteAllowedMoves, blackAllowedMoves);
	}
	
	@Override public Collection<ChessPiece> getActivePiece() {
		return this.board.getWhitePieces();
	}

}
