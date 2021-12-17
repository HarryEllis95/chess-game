package chess_pieces;

import board_construction.Board;
import board_construction.Board.Builder;

public abstract class Move {

	final Board board;
	final ChessPiece movedPiece;
	final int finalCoordinate;
	
	public static final Move NULL_MOVE = new NullMove();
	
	private Move(final Board board, final ChessPiece movedPiece, final int finalCoordinate) {
		this.board = board;
		this.movedPiece = movedPiece;
		this.finalCoordinate = finalCoordinate;
	}
	
	// equals and hashCode overrides 
	@Override public boolean equals(final Object other) {
		if(this == other) {
			return true;
		}
		if(!(other instanceof Move)) {
			return false;
		}
		final Move otherMove = (Move) other;  // cast is fine, as checked above
		// definition of an equals move 
		return getDestinationCoordinate() == otherMove.getDestinationCoordinate() &&
				getMovedPiece().equals(otherMove.getMovedPiece());
	}
	
	@Override public int hashCode() {
		int result = 1;
		result = 31 + this.finalCoordinate;
		result = 31 + this.movedPiece.hashCode();
		return result;
		
	}
	
	
	public int getCurrentCoordinate() {
		return this.getMovedPiece().getPiecePosition();
	}
	
	public int getDestinationCoordinate() {
		return this.finalCoordinate;
	}
	
	public ChessPiece getMovedPiece() {
		return this.movedPiece;
	}
	
	public boolean isAttacking() {
		return false;
	}
	
	public boolean isCastlingMove() {
		return false;
	}
	
	public ChessPiece getAttackedPiece() {
		return null;
	}
	

	public Board execute() {
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


	/* Define a bunch of subclasses to handle different move mechanics */
	public static class NonTakingMove extends Move {
		public NonTakingMove(final Board board, final ChessPiece movedPiece, final int finalCoordinate) {
			super(board, movedPiece, finalCoordinate);
		        }
	      @Override public boolean equals(final Object other) {
	            return this == other || other instanceof NonTakingMove && super.equals(other);
		}
	      @Override public int hashCode() {
	    	  return super.hashCode();
		}
	}
	
	public static class TakingMove extends Move {
		final ChessPiece takenPiece;
		public TakingMove(final Board board, final ChessPiece movedPiece, final int finalCoordinate, 
				final ChessPiece takenPiece) {
			super(board, movedPiece, finalCoordinate);
			this.takenPiece = takenPiece;
		}
		
		@Override public boolean equals(final Object other) {
			if(this == other) {
				return true;
			}
			if(!(other instanceof TakingMove)) {
				return false;
			}
			final TakingMove otherTakingMove = (TakingMove) other;
			return super.equals(otherTakingMove) && getAttackedPiece().equals(otherTakingMove.getAttackedPiece());
		}
		

		
		@Override public Board execute() {
			return null;
		}
		@Override public boolean isAttacking() {
			return true;
		}
		@Override public ChessPiece getAttackedPiece() {
			return this.takenPiece;
		}
	}
	
	
    /* Moves relevant to pawns */
	public static final class PawnMove extends Move {
		public PawnMove(final Board board, final ChessPiece movedPiece, final int finalCoordinate) {
			super(board, movedPiece, finalCoordinate);
		}
	}
	
	public static class PawnTakingMove extends TakingMove {
		public PawnTakingMove(final Board board, final ChessPiece movedPiece, final int finalCoordinate,
				final ChessPiece takenPiece) {
			super(board, movedPiece, finalCoordinate, takenPiece);
		}
	}
	
	public static final class PawnEnPassantTakingMove extends PawnTakingMove {
		public PawnEnPassantTakingMove(final Board board, final ChessPiece movedPiece, final int finalCoordinate,
				final ChessPiece takenPiece) {
			super(board, movedPiece, finalCoordinate, takenPiece);
		}
	}
	
	public static final class PawnJumpMove extends Move {
		public PawnJumpMove(final Board board, final ChessPiece movedPiece, final int finalCoordinate) {
			super(board, movedPiece, finalCoordinate);
		}
		
		@Override public Board execute() {
			final Builder builder = new Builder();
			for(final ChessPiece piece : this.board.currentPlayer().getActivePiece()) {
				if(!this.movedPiece.equals(piece)) {
					builder.setPiece(piece);
				}
			}
			for(final ChessPiece piece : this.board.currentPlayer().getOpponent().getActivePiece()) {
				builder.setPiece(piece);
			}
			final Pawn movedPawn = (Pawn)this.movedPiece.movePiece(this);
			/* main difference in this execute method compared to that of a standard move is setting the 
			 * EnPassent, which is a possible taking moving during a pawn jump */
			builder.setPiece(movedPawn);
			builder.setEnPassentPawn(movedPawn);
			builder.setMoveMaker(this.board.currentPlayer().getOpponent().getColour());
			return builder.build();
		}
	}
	
	
	/* Moves relevant to castling */
	static abstract class CastleMove extends Move {
		protected final Rook castleRook;
		protected final int castleRookStart;
		protected final int castleRookDestination;
		
		public CastleMove(final Board board, final ChessPiece movedPiece, final int finalCoordinate,
				final Rook castleRook, final int castleRookStart, final int castleRookDestination) {
			super(board, movedPiece, finalCoordinate);
			this.castleRook = castleRook;
			this.castleRookStart = castleRookStart;
			this.castleRookDestination = castleRookDestination;
		}
		
		public Rook getCastleRook() {
			return this.castleRook;
		}
		
		@Override public boolean isCastlingMove() {
			return true;
		}
		
		@Override public Board execute() {
			final Builder builder = new Builder();
			for(final ChessPiece piece : this.board.currentPlayer().getActivePiece()) {
				if(!this.movedPiece.equals(piece) && !this.castleRook.equals(piece)) {
					builder.setPiece(piece);
				}
			}
			for(final ChessPiece piece : this.board.currentPlayer().getOpponent().getActivePiece()) {
				builder.setPiece(piece);
			}
			builder.setPiece(this.movedPiece.movePiece(this));
			builder.setPiece(new Rook(this.castleRook.getPieceColour(), this.castleRookDestination));
			builder.setMoveMaker(this.board.currentPlayer().getOpponent().getColour());
			return builder.build();
		}
	}
	
	
	public static final class KingSideCastleMove extends CastleMove {
		public KingSideCastleMove(final Board board, final ChessPiece movedPiece, final int finalCoordinate,
				final Rook castleRook, final int castleRookStart, final int castleRookDestination) {
			super(board, movedPiece, finalCoordinate, castleRook, castleRookStart, castleRookDestination);
		}
		
		@Override public String toString() {
			return "O-O";
		}
		
	}
	
	
	public static final class QueenSideCastleMove extends CastleMove {
		public QueenSideCastleMove(final Board board, final ChessPiece movedPiece, final int finalCoordinate,
				final Rook castleRook, final int castleRookStart, final int castleRookDestination) {
			super(board, movedPiece, finalCoordinate, castleRook, castleRookStart, castleRookDestination);
		}
		
		@Override public String toString() {
			return "O-O-O";
		}
	}
	
	
	public static final class NullMove extends Move {
		public NullMove() {
			super(null, null, -1);
			}
		public Board execute() {
			throw new RuntimeException("cannot execute null move!");
		}
	}
	
	public static class MoveFactory {
		private MoveFactory() {
			throw new RuntimeException("Not to be instantiated!");
		}
		public static Move createMove(final Board board, final int currentCoordinate, final int finalCoordinate) {
			for(final Move move : board.getAllAllowedMoves()) {
				if(move.getCurrentCoordinate() == currentCoordinate && move.getDestinationCoordinate() == finalCoordinate) {
					return move;
				}
			}
			return NULL_MOVE;
		}
	}
	
	
	
}
