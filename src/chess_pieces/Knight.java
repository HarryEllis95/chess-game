package chess_pieces;

import java.util.*;

import board_construction.Board;
import board_construction.ChessTile;
import chess_pieces.ChessPiece.PieceType;
import board_construction.BoardUtils;


public class Knight extends ChessPiece {
	
	/* When considering the legal moves for a knight, there are (at most) 8 potential positions to move to from
	 * current position. */
	private final static int[] POTENTIAL_MOVE_COORDS = {-17, -15, -10, -6, 6, 10, 15, 17};
	/* These position offsets ares simple to work out when we consider the order as an 8x8 grid numbered 1-64,
	 * think what the relative resulting change in board number would be after a possible knight move. */
	
	public Knight(final PieceColour pieceColour, final int piecePosition) {
		super(PieceType.KNIGHT, piecePosition, pieceColour, true);
	}
	
	public Knight(final PieceColour pieceColour, final int piecePosition, final boolean isFirstMove) {
		super(PieceType.KNIGHT, piecePosition, pieceColour, isFirstMove);
	}
	
	@Override public String toString() {
		return this.pieceType.toString();
	}
	
	@Override public Knight movePiece(final Move move) {
		// Here we create the new knight, in the new location
		return new Knight(move.getMovedPiece().getPieceColour(), move.getDestinationCoordinate());
	}
	
	
	@Override public Collection <Move> determineAllowedMoves(Board board) {
		
		int potentialFinalCoord;
		final List<Move> allowedMoves = new ArrayList<>();
		
		// loop through the potential position offsets
		for(final int pos : POTENTIAL_MOVE_COORDS) {
			
			potentialFinalCoord = this.piecePosition + pos;  // apply offset to piece position 
			
			// Check if the result of applying the offset results in allowed board position
			if(BoardUtils.isValidTileCoordinate(potentialFinalCoord)) {  
				
				// Now need to deal with the edge constraints (can't move off or around the board to get to a tile - see below)
				if(isFirstColumnExclusion(this.piecePosition, pos) || isSecondColumnExclusion(this.piecePosition, pos) ||
						isSeventhColumnExclusion(this.piecePosition, pos) || 
						isEighthColumnExclusion(this.piecePosition, pos)) {
					continue;
				}
				
				final ChessTile potentialFinalTile = board.getTile(potentialFinalCoord);		
				
				if(!potentialFinalTile.isTileOccupied()) {
					allowedMoves.add(new Move.NonTakingMove(board, this, potentialFinalCoord));      
					// allowed move as the tile the knight is moving to is not occupied
					
				} else {
					/* Even if the tile is occupied, of course the move might still be allowed if the piece is of 
					 * opposing colour, and therefore the knight would take this piece, so we must check this */
					final ChessPiece pieceAtDestination = potentialFinalTile.getPiece();
					final PieceColour pieceColour = pieceAtDestination.getPieceColour();
					
					if(this.pieceColour != pieceColour) {
						allowedMoves.add(new Move.MajorTakingMove(board, this, potentialFinalCoord, pieceAtDestination));
					}
				}
			}
		}
		return Collections.unmodifiableList(allowedMoves);
	}
	
	/* I now need to deal with scenarios where the move might not be allowed as we would be moving off/around the board to get
	 * to a tile. These can be worked out my considering the columns in which a knight could potentially move around the board
	 * with a move (which doesn't break the isValidTileCoordinate condition), e.g columns 1,2,7,8, and what the 
	 * relative offsets which could cause that would be. */
	private static boolean isFirstColumnExclusion(final int currentPosition, final int offset) {
		return BoardUtils.FIRST_COLUMN.get(currentPosition) && ((offset == -17) || (offset == -10) || (offset == 6) || (offset == 15)); 
	}
	
	private static boolean isSecondColumnExclusion(final int currentPosition, final int offset) {
		return BoardUtils.SECOND_COLUMN.get(currentPosition) && ((offset == -10) || (offset == 6)); 
	}
	
	private static boolean isSeventhColumnExclusion(final int currentPosition, final int offset) {
		return BoardUtils.SEVENTH_COLUMN.get(currentPosition) && ((offset == -6) || (offset == 10));
	}
	
	private static boolean isEighthColumnExclusion(final int currentPosition, final int offset) {
		return BoardUtils.EIGHTH_COLUMN.get(currentPosition) && ((offset == -15) || (offset == -6) || (offset == 10) || (offset == 17));
	}
	
	/* Note - another, and potentially better way the above could be done is to use the absolute value of the difference 
	 *        between the columns to determine if the move is really valid. The knight can only move at most two columns
	 *        away, so the absolute value of the difference between the possible position and current position could be used.
	 *        I've stuck to my original code as it works fine, but this is another option to consider
	 */
}

