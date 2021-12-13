package chess_pieces;

import java.util.*;
import board_construction.Board;


public abstract class ChessPiece {

	protected final PieceType pieceType;
	protected final int piecePosition;
	protected final PieceColour pieceColour;
	protected final boolean isFirstMove;
	
	ChessPiece(final PieceType pieceType, final int piecePosition, final PieceColour pieceColour) {
		this.pieceType = pieceType;
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
	
	public PieceType getPieceType() {
		return this.pieceType;
	}
	
	/* This is a very important method, which takes a given board and for the given piece it calculates its allowed moves */
	public abstract Collection<Move> determineAllowedMoves(final Board board);
	
	
	public enum PieceType {
		
		PAWN("P") {
			@Override
			public boolean isKing() {
				return false;
			}
		},
		KNIGHT("N") {
			@Override
			public boolean isKing() {
				return false;
			}
		},
		BISHOP("B") {
			@Override
			public boolean isKing() {
				return false;
			}
		},
		ROOK("R") {
			@Override
			public boolean isKing() {
				return false;
			}
		},
		QUEEN("Q") {
			@Override
			public boolean isKing() {
				return false;
			}
		},
		KING("K") {
			@Override
			public boolean isKing() {
				return true;
			}
		};
		
		private String pieceName;
		
		private PieceType(String pieceName) {
			this.pieceName = pieceName;
		}
		
		/* When invoking the toString() method on each piece I just want to delegate to the individual piece type */
		@Override public String toString() {
			return this.pieceName;
		}
		
		public abstract boolean isKing();
	}
}
