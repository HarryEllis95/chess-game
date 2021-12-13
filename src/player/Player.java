package player;

import java.util.Collection;

import board_construction.Board;
import chess_pieces.ChessPiece;
import chess_pieces.King;
import chess_pieces.Move;

public abstract class Player {

	protected final Board board;
	protected final King playersKing;
	protected final Collection<Move> allowedMoves;
	
	Player(final Board board, final Collection<Move> allowedMoves, final Collection<Move> opponentMoves) {
		this.board = board;
		this.playersKing = establishKing();
		this.allowedMoves = allowedMoves;
	}
	
	// Use this method to check for a king, without a king we have no chess game!
	private King establishKing() {
		for(final ChessPiece piece : getActivePiece()) {
			if(piece.getPieceType().isKing()) {
				return (King) piece;
			}
		}
		throw new RuntimeException("Game is invalid - no King!");
	}
	
	public abstract Collection<ChessPiece> getActivePiece();
	
}
