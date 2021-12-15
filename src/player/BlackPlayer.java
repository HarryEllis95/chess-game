package player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import board_construction.Board;
import board_construction.ChessTile;
import chess_pieces.ChessPiece;
import chess_pieces.Move;
import chess_pieces.PieceColour;
import chess_pieces.Rook;

public class BlackPlayer extends Player {

	public BlackPlayer(Board board, Collection<Move> whiteAllowedMoves, Collection<Move> blackAllowedMoves) {
		super(board, blackAllowedMoves, whiteAllowedMoves);
	}
	
	@Override public Collection<ChessPiece> getActivePiece() {
		return this.board.getBlackPieces();
	}
	
	@Override public PieceColour getColour() {
		return PieceColour.BLACK;
	}
	
	@Override public Player getOpponent() {
		return this.board.whitePlayer();
	}
	
	@Override
	protected Collection<Move> calculateCastles(Collection<Move> playersAllowed, Collection<Move> opponentsAllowed) {
		final List<Move> castles = new ArrayList<>();
		
		// make required checks involved in castling  
		if(this.playersKing.isFirstMove() && !this.isInCheck()) {
			// king side castle - checking rook positions 
			if(!this.board.getTile(5).isTileOccupied() && !this.board.getTile(6).isTileOccupied()) {
				final ChessTile rookTile = this.board.getTile(7);
				// make sure rook has not moved
				if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
					// check for attacks on tiles which disallow castling 
					if(Player.calculateAttacksOnTile(5, opponentsAllowed).isEmpty() &&
							Player.calculateAttacksOnTile(6, opponentsAllowed).isEmpty() && 
							rookTile.getPiece().getPieceType().isRook()) {
						
						castles.add(new Move.KingSideCastleMove(this.board, this.playersKing, 6, 
								(Rook)rookTile.getPiece(), rookTile.getTileCoordinate(), 5));
					}
				}
			}
			// queen side castle
			if(!this.board.getTile(1).isTileOccupied() && !this.board.getTile(2).isTileOccupied() &&
					!this.board.getTile(3).isTileOccupied()) {
				final ChessTile rookTile = this.board.getTile(0);
				if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
					
					castles.add(new Move.KingSideCastleMove(this.board, this.playersKing, 2, 
							(Rook)rookTile.getPiece(), rookTile.getTileCoordinate(), 3));
				}
			}
		}
		return Collections.unmodifiableList(castles);
	}
}
