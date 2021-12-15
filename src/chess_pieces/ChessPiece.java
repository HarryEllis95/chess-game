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
	
	// equals and hashCode overrides 
	@Override public boolean equals(final Object other) {
		if(this == other) {
			return true;
		}
		if(!(other instanceof ChessPiece)) {
			return false;
		}
		final ChessPiece otherPiece = (ChessPiece) other;
		// The following returns the conditions required for pieces to be considered equals
		return piecePosition == otherPiece.getPiecePosition() && pieceType == otherPiece.getPieceType() &&
				pieceColour == otherPiece.getPieceColour() && isFirstMove == otherPiece.isFirstMove();
	}
	
	@Override public int hashCode() {
		int result = pieceType.hashCode();
		result = 31 * result + pieceColour.hashCode();
		result = 31 * result + piecePosition;
		result = 31 * result + (isFirstMove ? 1 : 0);
		return result;
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
	
	public abstract ChessPiece movePiece(Move move);
	
	
	public enum PieceType {
		
		PAWN("P") {
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},
		KNIGHT("N") {
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},
		BISHOP("B") {
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},
		ROOK("R") {
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return true;
			}
		},
		QUEEN("Q") {
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},
		KING("K") {
			@Override
			public boolean isKing() {
				return true;
			}

			@Override
			public boolean isRook() {
				return false;
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

		public abstract boolean isRook();
	}
}
