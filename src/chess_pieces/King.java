package chess_pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import board_construction.Board;
import board_construction.BoardUtils;
import board_construction.ChessTile;
import chess_pieces.ChessPiece.PieceType;

public class King extends ChessPiece {
	
	public King(PieceColour pieceColour, final int piecePosition) {
		super(PieceType.KING, piecePosition, pieceColour);
	}
	
	@Override public String toString() {
		return PieceType.KING.toString();
	}
	
	private final static int[] POTENTIAL_MOVE_COORDS = {-9, -8, -7, -1, 1, 7, 8, 9};
	
	
	@Override public King movePiece(final Move move) {
		// Here we create the new king, in the new location
		return new King(move.getMovedPiece().getPieceColour(), move.getDestinationCoordinate());
	}
	
	
	@Override public Collection<Move> determineAllowedMoves(Board board) {
		
		final List<Move> allowedMoves = new ArrayList<>();
		
		for(final int pos : POTENTIAL_MOVE_COORDS) {
			
			int potentialFinalCoord = this.piecePosition + pos;
			
			if(isFirstColumnExclusion(this.piecePosition, pos) || isEighthColumnExclusion(this.piecePosition, pos)) {
				continue;
			}
			
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
				}
			}
		}
		return Collections.unmodifiableList(allowedMoves);
	}
	
	private static boolean isFirstColumnExclusion(final int currentPosition, final int offset) {
		return BoardUtils.FIRST_COLUMN[currentPosition] && ((offset == -9) || (offset == -1) || (offset == 7)); 
	}
	
	private static boolean isEighthColumnExclusion(final int currentPosition, final int offset) {
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && ((offset == -7) || (offset == 1) || (offset == 9)); 
	}
	

}
