package chess_pieces;

import java.util.*;
import board_construction.Board;


public abstract class ChessPiece {

	protected final int piecePosition;
	protected final PieceColour pieceColour;
	protected final boolean isFirstMove;
	
	ChessPiece(final int piecePosition, final PieceColour pieceColour) {
		this.piecePosition = piecePosition;
		this.pieceColour = pieceColour;
		this.isFirstMove = false;
	}
	
	public PieceColour getPieceColour() {
		return this.pieceColour;
	}
	
	public int getPiecePosition() {
		return this.piecePosition;
	}
	
	public boolean isFirstMove() {
		return this.isFirstMove;
	}
	
	/* This is a very important method, which takes a given board and for the given piece it calculates its allowed moves */
	public abstract Collection<Move> determineAllowedMoves(final Board board);
	
	
	public enum PieceType {
		
		PAWN("P"),
		KNIGHT("N"),
		BISHOP("B"),
		ROOK("R"),
		QUEEN("Q"),
		KING("K");
		
		private String pieceName;
		
		private PieceType(String pieceName) {
			this.pieceName = pieceName;
		}
		
		/* When invoking the toString() method on each piece I just want to delegate to the individual piece type */
		@Override public String toString() {
			return this.pieceName;
		}
	}
}
