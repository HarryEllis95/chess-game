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
	
	private final static int[] POTENTIAL_MOVE_COORDS = {8, 16, 7, 9};
	
	/* Currently in my chess engine, pawns will be automatically promoted to a queen. Implementing the ability for the user
	 * to choose the promotion piece in the gui is a fair amount of extra code for such a rare circumstance, so this is not 
	 * something I have coded yet, for simplicity. I plan on implementing this in the near future.  */
	public ChessPiece getPromotionPiece() {
		return new Queen(this.pieceColour, this.piecePosition, false);
	}
	
	@Override public String toString() {
		return this.pieceType.toString();
	}
	
	@Override public Pawn movePiece(final Move move) {
		// Here we create the new pawn, in the new location
		return new Pawn(move.getMovedPiece().getPieceColour(), move.getDestinationCoordinate());
	}
	

	@Override public Collection<Move> determineAllowedMoves(Board board) {

		final List<Move> allowedMoves = new ArrayList<>();

		for (final int pos : POTENTIAL_MOVE_COORDS) {

			// This ensures that for white we apply offset -8 and for black we apply +8 for white
			int potentialFinalCoord = this.piecePosition + (this.pieceColour.getDirection() * pos);
			
			if(!BoardUtils.isValidTileCoordinate(potentialFinalCoord)) {
				continue;
			}
			
			/* This first if clause handles single tile pawn moves, where relative offset is 8 (1 tile forwards) */
			if(pos == 8 && !board.getTile(potentialFinalCoord).isTileOccupied()) {
				
				if(this.pieceColour.isPawnPromotionSquare(potentialFinalCoord)) {
					/* The PawnPromotion move will 'wrap' the PawnMove */
					allowedMoves.add(new Move.PawnPromotion(new Move.PawnMove(board, this, potentialFinalCoord)));
				} else { 
					allowedMoves.add(new Move.PawnMove(board, this, potentialFinalCoord));
				}
				
			/* On a pawns first move it can jump 2 tiles, a 'pawn jump', which is handled with the following logic.
			 * Also notice the logic dealing with the edge cases - this feels like a lot of boolean logic cramped
			 * together but I didn't manage to get the Exclusion methods working as I did with the other pieces */ 
			} else if(pos == 16 && this.isFirstMove() && 
					((BoardUtils.SECOND_RANK.get(this.piecePosition) && this.pieceColour.isBlack()) || 
					(BoardUtils.SEVENTH_RANK.get(this.piecePosition) && this.pieceColour.isWhite()))) {
				final int behindPotentialFinalCoord = this.piecePosition + (this.pieceColour.getDirection() * 8);
				// Make sure the pawn is in it's starting position and the tile it's jumping to is not occupied
				if(!board.getTile(behindPotentialFinalCoord).isTileOccupied() && 
						!board.getTile(potentialFinalCoord).isTileOccupied()) {
					allowedMoves.add(new Move.PawnJumpMove(board, this, potentialFinalCoord));
				}
				
			} else if(pos == 7  && !((BoardUtils.EIGHTH_COLUMN.get(this.piecePosition) && this.pieceColour.isWhite()) ||
						BoardUtils.FIRST_COLUMN.get(this.piecePosition) && this.pieceColour.isBlack())) {
					if(board.getTile(potentialFinalCoord).isTileOccupied()) {
						
						final ChessPiece pieceAtDestination = board.getTile(potentialFinalCoord).getPiece();
						
						if(this.pieceColour != pieceAtDestination.getPieceColour()) {
							
							if (this.pieceColour.isPawnPromotionSquare(potentialFinalCoord)) {
								allowedMoves.add(new Move.PawnPromotion(
										new Move.PawnTakingMove(board, this, potentialFinalCoord, pieceAtDestination)));
							} else {
								allowedMoves.add(new Move.PawnTakingMove(board, this, potentialFinalCoord, pieceAtDestination));
							}
						}
						/* Here I deal with the case where we have an EnPassant Pawn */
					} else if(board.getEnPassantPawn() != null && board.getEnPassantPawn().getPiecePosition()
							== (this.piecePosition + (-this.pieceColour.getDirection()))) {
							final ChessPiece pieceOnPos = board.getEnPassantPawn();					
							if(this.pieceColour != pieceOnPos.getPieceColour()) {
								allowedMoves.add(new Move.PawnEnPassantTakingMove(board, this, potentialFinalCoord, pieceOnPos));
							}
						}
					} else if(pos == 9 && !((BoardUtils.FIRST_COLUMN.get(this.piecePosition) && this.pieceColour.isWhite()) ||
						BoardUtils.EIGHTH_COLUMN.get(this.piecePosition) && this.pieceColour.isBlack())) {
						if(board.getTile(potentialFinalCoord).isTileOccupied()) {
						
							final ChessPiece pieceAtDestination = board.getTile(potentialFinalCoord).getPiece();
						
							if(this.pieceColour != pieceAtDestination.getPieceColour()) {			
								if(this.pieceColour.isPawnPromotionSquare(potentialFinalCoord)) {
									allowedMoves.add(new Move.PawnPromotion(
											new Move.PawnTakingMove(board, this, potentialFinalCoord, pieceAtDestination)));
								} else {
									allowedMoves.add(new Move.PawnTakingMove(board, this, potentialFinalCoord, pieceAtDestination));
								}
							}
						} else if(board.getEnPassantPawn() != null) {
						
						if(board.getEnPassantPawn().getPiecePosition() == (this.piecePosition - 
								(-this.pieceColour.getDirection()))) {						
							final ChessPiece pieceOnPos = board.getEnPassantPawn();
							
							if(this.pieceColour != pieceOnPos.getPieceColour()) {
								allowedMoves.add(new Move.PawnEnPassantTakingMove(board, this, potentialFinalCoord, pieceOnPos));
							}
						}
					}
				}
			}
		return Collections.unmodifiableList(allowedMoves);
	}
	
	
	
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset, final PieceColour colour) {
	    return (candidateOffset == 7 && (BoardUtils.FIRST_COLUMN.get(currentPosition) && colour.isBlack())) ||
	            (candidateOffset == 9 && (BoardUtils.FIRST_COLUMN.get(currentPosition) && colour.isWhite()));
	}

	private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset, final PieceColour colour){
	    return (candidateOffset == 7 && (BoardUtils.EIGHTH_COLUMN.get(currentPosition) && colour.isWhite())) ||
	            (candidateOffset == 9 && (BoardUtils.EIGHTH_COLUMN.get(currentPosition) && colour.isBlack()));
	}
	
}


