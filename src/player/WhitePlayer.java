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


public class WhitePlayer extends Player {
	
	public WhitePlayer(Board board, Collection<Move> whiteAllowedMoves, Collection<Move> blackAllowedMoves) {
		super(board, whiteAllowedMoves, blackAllowedMoves);
	}
	
	@Override public Collection<ChessPiece> getActivePiece() {
		return this.board.getWhitePieces();
	}
	
	@Override public PieceColour getColour() {
		return PieceColour.WHITE;
	}
	
	@Override public Player getOpponent() {
		return this.board.blackPlayer();
	}

	@Override
	protected Collection<Move> calculateCastles(Collection<Move> playersAllowed, Collection<Move> opponentsAllowed) {
		final List<Move> castles = new ArrayList<>();
		
		// make required checks involved in castling  
		if(this.playersKing.isFirstMove() && !this.isInCheck()) {
			// first, white king side castle - checking rook positions 
			if(!this.board.getTile(61).isTileOccupied() && !this.board.getTile(62).isTileOccupied()) {
				final ChessTile rookTile = this.board.getTile(63);
				// make sure rook has not moved
				if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
					// check for attacks on tiles which disallow castling 
					if(Player.calculateAttacksOnTile(61, opponentsAllowed).isEmpty() &&
							Player.calculateAttacksOnTile(62, opponentsAllowed).isEmpty() && 
							rookTile.getPiece().getPieceType().isRook()) {
						
						castles.add(new Move.KingSideCastleMove(this.board, this.playersKing, 62, 
								(Rook)rookTile.getPiece(), rookTile.getTileCoordinate(), 61));
					}
				}
			}
			// white queen side castle
			if(!this.board.getTile(59).isTileOccupied() && !this.board.getTile(58).isTileOccupied() &&
					!this.board.getTile(57).isTileOccupied()) {
				final ChessTile rookTile = this.board.getTile(56);
				if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
					if(Player.calculateAttacksOnTile(58, opponentsAllowed).isEmpty() && 
							Player.calculateAttacksOnTile(59, opponentsAllowed).isEmpty() && 
							rookTile.getPiece().getPieceType().isRook()) {
					
					castles.add(new Move.QueenSideCastleMove(this.board, this.playersKing, 58, 
							(Rook)rookTile.getPiece(), rookTile.getTileCoordinate(), 59));
					}
				}
			}
		}
		return Collections.unmodifiableList(castles);
	}

}
