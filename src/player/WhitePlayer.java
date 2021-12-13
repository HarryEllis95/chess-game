package player;

import java.util.Collection;

import board_construction.Board;
import chess_pieces.ChessPiece;
import chess_pieces.Move;
import chess_pieces.PieceColour;


public class WhitePlayer extends Player {
	
	public WhitePlayer(Board board, Collection<Move> whiteAllowedMoves, Collection<Move> blackAllowedMoves) {
		super(board, whiteAllowedMoves, blackAllowedMoves);
	}
	
	@Override public Collection<ChessPiece> getActivePiece() {
		return this.board.getWhitePieces();
	}
	
	@Override public PieceColour getColour() {
		return PieceColour.WHITE;
	}
	
	@Override public Player getOpponent() {
		return this.board.blackPlayer();
	}

}
