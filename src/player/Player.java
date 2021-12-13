package player;

import java.util.Collection;

import board_construction.Board;
import board_construction.BoardTransform;
import chess_pieces.ChessPiece;
import chess_pieces.King;
import chess_pieces.Move;
import chess_pieces.PieceColour;

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
	
	
	public boolean isMoveAllowed(final Move move) {
		return this.allowedMoves.contains(move);
	}
	
	// Methods to check on the state of the game
	public boolean isInCheck() {
		return false;
	}
	
	public boolean isInCheckMate() {
		return false;
	}
	
	public boolean isInStalemate() {
		return false;
	}
	
	public boolean isCastled() {
		return false;
	}
	
	// When we make a move, we return a BoardTransform
	public BoardTransform makeMove(final Move move) {
		return null;
	}
	
	// abstract methods to be overriden in BlackPlayer and WhitePlayer classes
	public abstract Collection<ChessPiece> getActivePiece();
	
	public abstract PieceColour getColour();
	
	public abstract Player getOpponent();
	
	
	
}
