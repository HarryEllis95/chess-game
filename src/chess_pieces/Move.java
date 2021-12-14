package chess_pieces;

import board_construction.Board;

public abstract class Move {

	final Board board;
	final ChessPiece movedPiece;
	final int finalCoordinate;
	
	private Move(final Board board, final ChessPiece movedPiece, final int finalCoordinate) {
		this.board = board;
		this.movedPiece = movedPiece;
		this.finalCoordinate = finalCoordinate;
	}
	
	public int getDestinationCoordinate() {
		return this.finalCoordinate;
	}
	
	public abstract Board execute();
	
	
	public static final class NonTakingMove extends Move {
		public NonTakingMove(final Board board, final ChessPiece movedPiece, final int finalCoordinate) {
			super(board, movedPiece, finalCoordinate);
		}
		@Override public Board execute() {
			return null;
		}
	}
	
	public static final class TakingMove extends Move {
		final ChessPiece takenPiece;
		public TakingMove(final Board board, final ChessPiece movedPiece, final int finalCoordinate, final ChessPiece takenPiece) {
			super(board, movedPiece, finalCoordinate);
			this.takenPiece = takenPiece;
		}
		@Override public Board execute() {
			return null;
		}
	}

	
	
}
