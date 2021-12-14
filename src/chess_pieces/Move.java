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
	
	public ChessPiece getMovedPiece() {
		return this.movedPiece;
	}
	
	public abstract Board execute();
	
	
	public static final class NonTakingMove extends Move {
		public NonTakingMove(final Board board, final ChessPiece movedPiece, final int finalCoordinate) {
			super(board, movedPiece, finalCoordinate);
		}
		
		@Override public Board execute() {
			final Board.Builder builder = new Board.Builder();
			/* We now traverse through all of the current player's pieces and for all of the pieces that aren't 
			 * the moved piece we simply place them on the new board - no change */
			for(final ChessPiece piece : this.board.currentPlayer().getActivePiece()) {
				if(!this.movedPiece.equals(piece)) {
				builder.setPiece(piece);
				}
			}
			/* We now do the same for the opposing pieces, clearly without the need for the if statement as
			 * they don't have a moving piece   */
			for(final ChessPiece piece : this.board.currentPlayer().getOpponent().getActivePiece()) {
				builder.setPiece(piece);
			}
			// Now set the piece for the moving piece, to represent the move, then set move maker to opponent's pieces
			builder.setPiece(this.movedPiece.movePiece(this));
			builder.setMoveMaker(this.board.currentPlayer().getOpponent().getColour());
			return builder.build();  
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
