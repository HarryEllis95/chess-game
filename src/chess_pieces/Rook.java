package chess_pieces;

import java.util.*;

import com.google.common.collect.ImmutableList;

import board_construction.Board;
import board_construction.BoardUtils;
import board_construction.ChessTile;

public class Rook extends ChessPiece {
	
	public Rook(int piecePosition, PieceColour pieceColour) {
		super(piecePosition, pieceColour);
	}
	
	private final static int[] POTENTIAL_MOVE_COORDS = {-8, -1, 1, 8};

	/* The logistics of this method is essentially the same as for the Bishop, so I have left it uncommented*/
	@Override public Collection<Move> determineAllowedMoves(Board board) {
		
		final List<Move> allowedMoves = new ArrayList<>();
		
		for(final int pos : POTENTIAL_MOVE_COORDS) {
			
			int potentialFinalCoord = this.piecePosition;
			
			while(BoardUtils.isValidTileCoordinate(potentialFinalCoord)) { 
				
				if(isFirstColumnExclusion(potentialFinalCoord, pos) || isEighthColumnExclusion(potentialFinalCoord, pos)) {
					break;
				}
				
				potentialFinalCoord += pos;  
				
				if(BoardUtils.isValidTileCoordinate(potentialFinalCoord)) {
					final ChessTile potentialFinalTile = board.getTile(potentialFinalCoord);		
					
					if(!potentialFinalTile.isTileOccupied()) {
						allowedMoves.add(new Move.NonTakingMove(board, this, potentialFinalCoord));      
						
					} else {
						final ChessPiece pieceAtDestination = potentialFinalTile.getPiece();
						final PieceColour pieceColour = pieceAtDestination.getPieceColour();
						
						if(this.pieceColour != pieceColour) {
							allowedMoves.add(new Move.TakingMove(board, this, potentialFinalCoord, pieceAtDestination));
						}
						break;  						
					}
				}
			}
		}
		return ImmutableList.copyOf(allowedMoves);
	}
	
	
	/* The only real difference between this class and the Bishop class is the edge conditions to consider are 
	 * slightly different */
	private static boolean isFirstColumnExclusion(final int currentPosition, final int offset) {
		return BoardUtils.FIRST_COLUMN[currentPosition] && (offset == -1);
	}
	
	private static boolean isEighthColumnExclusion(final int currentPosition, final int offset) {
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && (offset == 1);
	}
}