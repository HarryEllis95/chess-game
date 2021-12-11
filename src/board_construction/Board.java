package board_construction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;

import chess_pieces.*;

public class Board {
	
	private final List<ChessTile> chessBoard;
	private final Collection<ChessPiece> whitePieces;
	private final Collection<ChessPiece> blackPieces;
	
	private Board(Builder builder) {
		this.chessBoard = buildGameBoard(builder);
		this.whitePieces = calculateActivePieces(this.chessBoard, PieceColour.WHITE);
		this.blackPieces = calculateActivePieces(this.chessBoard, PieceColour.BLACK);
	}
	
	private Collection<ChessPiece> calculateActivePieces(List<ChessTile> chessBoard, PieceColour colour) {
		final List<ChessPiece> activePieces = new ArrayList<>();
		for(final ChessTile tile : chessBoard) {
			if(tile.isTileOccupied()) {
				final ChessPiece piece = tile.getPiece();
				if(piece.getPieceColour() == colour) {
					activePieces.add(piece);
				}
			}
		}
		return null;
	}

	public ChessTile getTile(final int tileCoordinate) {
		return chessBoard.get(tileCoordinate);
	}
	
	
	/* This method is used to populate a list of tiles from 0->63, representing the chess board.
	 * */
	private static List<ChessTile> buildGameBoard(final Builder builder) {
		final ChessTile[] tiles = new ChessTile[BoardUtils.NUM_TILES];
		for(int i=0; i<BoardUtils.NUM_TILES; i++) {
			tiles[i] = ChessTile.createTile(i, builder.boardConfig.get(i));
		}
		return ImmutableList.copyOf(tiles);
	}
	
	public static Board createBoard() {
		final Builder builder = new Builder();
		// Construct black pieces
		builder.setPiece(new Rook(PieceColour.BLACK, 0));
		builder.setPiece(new Knight(PieceColour.BLACK, 1));
		builder.setPiece(new Bishop(PieceColour.BLACK, 2));
		builder.setPiece(new Queen(PieceColour.BLACK, 3));
		builder.setPiece(new King(PieceColour.BLACK, 4));
		builder.setPiece(new Bishop(PieceColour.BLACK, 5));
		builder.setPiece(new Knight(PieceColour.BLACK, 6));
		builder.setPiece(new Rook(PieceColour.BLACK, 7));
		for(int i=8; i<16; i++) {
			builder.setPiece(new Pawn(PieceColour.BLACK, i));
		}
		// Construct white pieces
		for(int i=48; i<56; i++) {
			builder.setPiece(new Pawn(PieceColour.WHITE, i));
		}
		builder.setPiece(new Rook(PieceColour.WHITE, 56));
		builder.setPiece(new Knight(PieceColour.WHITE, 57));
		builder.setPiece(new Bishop(PieceColour.WHITE, 58));
		builder.setPiece(new Queen(PieceColour.WHITE, 59));
		builder.setPiece(new King(PieceColour.WHITE, 60));
		builder.setPiece(new Bishop(PieceColour.WHITE, 61));
		builder.setPiece(new Knight(PieceColour.WHITE, 62));
		builder.setPiece(new Rook(PieceColour.WHITE, 63));
		
		builder.setMoveMaker(PieceColour.WHITE);
		return builder.build();
	}
	
	
	// Here I will utilise the builder design pattern - used to build board 
	public static class Builder {
		
		Map<Integer, ChessPiece> boardConfig;
		PieceColour nextToMove;
		
		public Builder() {
		}
		
		public Builder setPiece(final ChessPiece piece) {
			this.boardConfig.put(piece.getPiecePosition(), piece);
			return this;
		}
		public Builder setMoveMaker(final PieceColour nextToMove) {
			this.nextToMove = nextToMove;
			return this;
		}
		
		public Board build() {
			return new Board(this);
		}
	}
}
