package chess_pieces;

import java.util.*;

import com.google.common.collect.ImmutableList;

import board_construction.Board;
import board_construction.BoardUtils;
import board_construction.ChessTile;


public class Bishop extends ChessPiece {
	
	public Bishop(int piecePosition, PieceColour pieceColour) {
		super(piecePosition, pieceColour);
	}
	
	private final static int[] POTENTIAL_MOVE_COORDS = {-9, -7, 7, 9};
	// These represent potentially allowed relative offsets (there's 4 allowed vectors in which bishop can move)
	
	@Override public Collection<Move> determineAllowedMoves(Board board) {
		
		final List<Move> allowedMoves = new ArrayList<>();
		
		for(final int pos : POTENTIAL_MOVE_COORDS) {
			
			int potentialFinalCoord = this.piecePosition;
			
			while(BoardUtils.isValidTileCoordinate(potentialFinalCoord)) {  // checks we're within the board's constraints
				
				// Edge constraint tests, see methods at the bottom of this class
				if(isFirstColumnExclusion(potentialFinalCoord, pos) || isEighthColumnExclusion(potentialFinalCoord, pos)) {
					break;
				}
				
				potentialFinalCoord += pos;  // apply offset to the position
				
				if(BoardUtils.isValidTileCoordinate(potentialFinalCoord)) {
					final ChessTile potentialFinalTile = board.getTile(potentialFinalCoord);		
					
					if(!potentialFinalTile.isTileOccupied()) {
						allowedMoves.add(new Move.NonTakingMove(board, this, potentialFinalCoord));      
						// allowed move as the tile the Bishop is moving to is not occupied
						
					} else {
						/* Even if the tile is occupied, of course the move might still be allowed if the piece is of 
						 * opposing colour, and therefore the Bishop would take this piece. The following checks this */
						final ChessPiece pieceAtDestination = potentialFinalTile.getPiece();
						final PieceColour pieceColour = pieceAtDestination.getPieceColour();
						
						if(this.pieceColour != pieceColour) {
							allowedMoves.add(new Move.TakingMove(board, this, potentialFinalCoord, pieceAtDestination));
						}
						break;  
						/* Once the Bishop reaches a tile with an opposing piece, it will stop and remain at the tile,
						 * therefore we break out of this loop */
						
					}
				}
			}
		}
		return ImmutableList.copyOf(allowedMoves);
	}
	
	
	/* Similar to with Knight, there are some edge cases which bypass the above checks but are still not allowed.
	 * For example, consider if you're at a4 and you apply the +7 offset. You'd want this to take you to b3 but instead
	 * it takes you to h4. I deal with the edge cases as shown below. */
	private static boolean isFirstColumnExclusion(final int currentPosition, final int offset) {
		return BoardUtils.FIRST_COLUMN[currentPosition] && (offset == -9 || offset ==7);
	}
	
	private static boolean isEighthColumnExclusion(final int currentPosition, final int offset) {
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && (offset == -7 || offset ==9);
	}

}
