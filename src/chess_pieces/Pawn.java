package chess_pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;

import board_construction.Board;
import board_construction.BoardUtils;

public class Pawn extends ChessPiece {

	public Pawn(PieceColour pieceColour, final int piecePosition) {
		super(piecePosition, pieceColour);
	}

	private final static int[] POTENTIAL_MOVE_COORDS = {8, 16};

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
					(BoardUtils.SECOND_ROW[this.piecePosition] && this.getPieceColour().isBlack()) || 
					(BoardUtils.SEVENTH_ROW[this.piecePosition] && this.getPieceColour().isWhite())) {
				
				
			}
		}
		return ImmutableList.copyOf(allowedMoves);
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


