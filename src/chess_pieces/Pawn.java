package chess_pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import board_construction.Board;
import board_construction.BoardUtils;
import chess_pieces.ChessPiece.PieceType;

public class Pawn extends ChessPiece {

	public Pawn(PieceColour pieceColour, final int piecePosition) {
		super(PieceType.PAWN, piecePosition, pieceColour);
	}
	
	@Override public String toString() {
		return PieceType.PAWN.toString();
	}

	private final static int[] POTENTIAL_MOVE_COORDS = {8, 16};
	
	
	@Override public Pawn movePiece(final Move move) {
		// Here we create the new pawn, in the new location
		return new Pawn(move.getMovedPiece().getPieceColour(), move.getDestinationCoordinate());
	}
	

	@Override public Collection<Move> determineAllowedMoves(Board board) {

		final List<Move> allowedMoves = new ArrayList<>();

		for (final int pos : POTENTIAL_MOVE_COORDS) {

			final int potentialFinalCoord = this.piecePosition + (this.getPieceColour().getDirection() * pos);
			// This ensures that for white we apply offset -8 and for black we apply 8
			
			if(!BoardUtils.isValidTileCoordinate(potentialFinalCoord)) {
				continue;
			}
			/* If you're moving one tile forward, and the tile you're moving to is not occupied then we're going
			 * so say */
			if(pos == 8 && !board.getTile(potentialFinalCoord).isTileOccupied()) {
				allowedMoves.add(new Move.NonTakingMove(board, this, pos));
				
			} else if(pos == 16 && this.isFirstMove() && 
					(BoardUtils.SECOND_RANK[this.piecePosition] && this.getPieceColour().isBlack()) || 
					(BoardUtils.SEVENTH_RANK[this.piecePosition] && this.getPieceColour().isWhite())) {
				
				
			}
		}
		return Collections.unmodifiableList(allowedMoves);
	}
	
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset, final PieceColour colour) {
	    return (candidateOffset == 7 && (BoardUtils.FIRST_COLUMN[currentPosition] && colour.isBlack())) ||
	            (candidateOffset == 9 && (BoardUtils.FIRST_COLUMN[currentPosition] && colour.isWhite()));
	}

	private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset, final PieceColour colour){
	    return (candidateOffset == 7 && (BoardUtils.EIGHTH_COLUMN[currentPosition] && colour.isWhite())) ||
	            (candidateOffset == 9 && (BoardUtils.EIGHTH_COLUMN[currentPosition] && colour.isBlack()));
	}
	
}


