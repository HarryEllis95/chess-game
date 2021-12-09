package chess_pieces;

import java.util.*;
import board_construction.Board;


public abstract class ChessPiece {

	protected final int piecePosition;
	protected final PieceColour pieceColour;
	
	ChessPiece(final int piecePosition, final PieceColour pieceColour) {
		this.piecePosition = piecePosition;
		this.pieceColour = pieceColour;
	}
	
	public PieceColour getPieceColour() {
		return this.pieceColour;
	}
	
	/* This is a very important method, which takes a given board and for the given piece it calculates its allowed moves */
	public abstract Collection<Move> determineAllowedMoves(final Board board);
}
