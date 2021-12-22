package chess_pieces;

import java.util.ArrayList;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import board_construction.Board;
import board_construction.BoardUtils;
import board_construction.ChessTile;
import chess_pieces.ChessPiece.PieceType;

public class Queen extends ChessPiece {

	public Queen(PieceColour pieceColour, final int piecePosition) {
		super(PieceType.QUEEN, piecePosition, pieceColour, true);
	}
	
	public Queen(PieceColour pieceColour, final int piecePosition, final boolean isFirstMove) {
		super(PieceType.QUEEN, piecePosition, pieceColour, isFirstMove);
	}
	
	@Override public String toString() {
		return PieceType.QUEEN.toString();
	}
	
	private final static int[] POTENTIAL_MOVE_COORDS = {-9, -8, -7, -1, 1, 7, 8, 9};  // union of Rook and Bishop
	
	
	@Override public Queen movePiece(final Move move) {
		// Here we create the new queen, in the new location
		return new Queen(move.getMovedPiece().getPieceColour(), move.getDestinationCoordinate());
	}
	
	
	/* Having coded the classes governing the mechanics of the Rook and Bishop, the Queen class is then straightforward
	 * as it is simply a union of those two piece's moves. */	
	@Override public Collection<Move> determineAllowedMoves(Board board) {
	    
	    final Collection<Move> equivalentRookMoves = new Rook(this.getPieceColour(), 
	    		this.getPiecePosition()).determineAllowedMoves(board);
	    final Collection<Move> equivalentBishopMoves = new Bishop(this.getPieceColour(), 
	    		this.getPiecePosition()).determineAllowedMoves(board);
	    
	    final List<Move> allowedMoves = new ArrayList<>(equivalentRookMoves);
	    allowedMoves.addAll(equivalentBishopMoves);

	    return Collections.unmodifiableList(allowedMoves);
	}
	
	
	/*  */
	private static boolean isFirstColumnExclusion(final int currentPosition, final int offset) {
		return BoardUtils.FIRST_COLUMN.get(currentPosition) && (offset == -1 || offset == -9 || offset == 7);
	}
	
	private static boolean isEighthColumnExclusion(final int currentPosition, final int offset) {
		return BoardUtils.EIGHTH_COLUMN.get(currentPosition) && (offset == -7 || offset == 1 || offset == 9);
	}
}


/* CHECK SOME POTENTIAL ISSUES WITH THIS CLASS */