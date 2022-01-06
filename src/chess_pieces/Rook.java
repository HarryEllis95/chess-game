package chess_pieces;

import java.util.*;
import board_construction.Board;
import board_construction.BoardUtils;
import board_construction.ChessTile;
import chess_pieces.ChessPiece.PieceType;

public class Rook extends ChessPiece {
	
	public Rook(final PieceColour pieceColour, final int piecePosition) {
		super(PieceType.ROOK, piecePosition, pieceColour, true);
	}
	
	public Rook(final PieceColour pieceColour, final int piecePosition, final boolean isFirstMove) {
		super(PieceType.ROOK, piecePosition, pieceColour, isFirstMove);
	}
	
	private final static int[] POTENTIAL_MOVE_COORDS = {-8, -1, 1, 8};
	// These represent potentially allowed relative offsets (there's 4 allowed vectors in which rook can move)
	
	@Override public String toString() {
		return this.pieceType.toString();
	}
	
	@Override public Rook movePiece(final Move move) {
		// Here we create the new rook, in the new location
		return new Rook(move.getMovedPiece().getPieceColour(), move.getDestinationCoordinate());
	}
	

	/* The logistics of this method is essentially the same as for the Bishop, so I have left it uncommented*/
	@Override public Collection<Move> determineAllowedMoves(Board board) {
		
		final List<Move> allowedMoves = new ArrayList<>();
		
		for(final int pos : POTENTIAL_MOVE_COORDS) {
			
			int potentialFinalCoord = this.piecePosition;
			
			while(BoardUtils.isValidTileCoordinate(potentialFinalCoord)) { // check we are still within board's constraints
				
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
							allowedMoves.add(new Move.MajorTakingMove(board, this, potentialFinalCoord, pieceAtDestination));
						}
						break;  						
					}
				}
			}
		}
		return Collections.unmodifiableList(allowedMoves);
	}
	
	
	/* The only real difference between this class and the Bishop class is the edge conditions to consider are 
	 * slightly different */
	private static boolean isFirstColumnExclusion(final int currentPosition, final int offset) {
		return BoardUtils.FIRST_COLUMN.get(currentPosition) && (offset == -1);
	}
	
	private static boolean isEighthColumnExclusion(final int currentPosition, final int offset) {
		return BoardUtils.EIGHTH_COLUMN.get(currentPosition) && (offset == 1);
	}
}