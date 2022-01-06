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
	
	private final static int[] POTENTIAL_MOVE_COORDS = {-9, -8, -7, -1, 1, 7, 8, 9};  // union of Rook and Bishop
	
	@Override public String toString() {
		return this.pieceType.toString();
	}
	
	@Override public Queen movePiece(final Move move) {
		// Here we create the new queen, in the new location
		return new Queen(move.getMovedPiece().getPieceColour(), move.getDestinationCoordinate());
	}
	
	
	/* Having coded the classes governing the mechanics of the Rook and Bishop, the Queen class is then straightforward
	 * to understand as it is simply a union of those two piece's moves. */	
	@Override public Collection<Move> determineAllowedMoves(Board board) {
		
        final List<Move> allowedMoves = new ArrayList<>();
        
        for (final int pos : POTENTIAL_MOVE_COORDS) {
            int potentialFinalCoord = this.piecePosition;
            while (BoardUtils.isValidTileCoordinate(potentialFinalCoord)) {
                if (isFirstColumnExclusion(potentialFinalCoord, pos) ||
                		isEighthColumnExclusion(potentialFinalCoord, pos)) {
                    break;
                }
                potentialFinalCoord += pos;
                if (BoardUtils.isValidTileCoordinate(potentialFinalCoord)) {
    				final ChessTile potentialFinalTile = board.getTile(potentialFinalCoord);
    				if(!potentialFinalTile.isTileOccupied()) {
                    	allowedMoves.add(new Move.NonTakingMove(board, this, potentialFinalCoord));  
    				} else {
    					final ChessPiece pieceAtDestination = potentialFinalTile.getPiece();
                        final PieceColour pieceAtDestinationAllegiance = pieceAtDestination.getPieceColour();
                        if (this.pieceColour != pieceAtDestinationAllegiance) {
    						allowedMoves.add(new Move.MajorTakingMove(board, this, potentialFinalCoord, pieceAtDestination));
                        }
                        break;
                    }
                }
            }
        }
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