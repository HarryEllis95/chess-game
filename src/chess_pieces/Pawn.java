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
		super(PieceType.PAWN, piecePosition, pieceColour, true);
	}
	
	public Pawn(PieceColour pieceColour, final int piecePosition, final boolean isFirstMove) {
		super(PieceType.PAWN, piecePosition, pieceColour, isFirstMove);
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
			/* This first if clause handles single tile pawn moves, where relative offset is 8 (1 tile forwards) */
			if(pos == 8 && !board.getTile(potentialFinalCoord).isTileOccupied()) {
				allowedMoves.add(new Move.NonTakingMove(board, this, pos));
			/* On a pawns first move it can jump 2 tiles, which is handled here */ 
			} else if(pos == 16 && this.isFirstMove() && 
					((BoardUtils.SECOND_RANK[this.piecePosition] && this.getPieceColour().isBlack()) || 
					(BoardUtils.SEVENTH_RANK[this.piecePosition] && this.getPieceColour().isWhite()))) {
				final int behindPotentialFinalCoord = this.piecePosition + (this.pieceColour.getDirection() * 8);
				// Make sure the pawn is in it's starting position and the tile it's jumping to is not occupied
				if(!board.getTile(behindPotentialFinalCoord).isTileOccupied() && 
						!board.getTile(potentialFinalCoord).isTileOccupied()) {
					allowedMoves.add(new Move.PawnJumpMove(board, this, potentialFinalCoord));
				}
			}
				/* Here I deal with the edge cases. This feels like a lot of boolean logic cramped
				 * together but I didn't manage to get the Exclusion methods working as I did with the other pieces	 */
			else if(pos == 7  && !((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.getPieceColour().isWhite()) ||
						BoardUtils.FIRST_COLUMN[this.piecePosition] && this.getPieceColour().isBlack())) {
					if(board.getTile(potentialFinalCoord).isTileOccupied()) {
						final ChessPiece pieceAtDestination = board.getTile(potentialFinalCoord).getPiece();
						if(this.pieceColour != pieceAtDestination.getPieceColour()) {
							
							allowedMoves.add(new Move.PawnTakingMove(board, this, potentialFinalCoord, pieceAtDestination));
						}
					}
				}
				else if(pos == 9 && !((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.getPieceColour().isWhite()) ||
						BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.getPieceColour().isBlack())){
					if(board.getTile(potentialFinalCoord).isTileOccupied()) {
						final ChessPiece pieceAtDestination = board.getTile(potentialFinalCoord).getPiece();
						if(this.pieceColour != pieceAtDestination.getPieceColour()) {
							
							allowedMoves.add(new Move.PawnTakingMove(board, this, potentialFinalCoord, pieceAtDestination));
						}
					}
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


