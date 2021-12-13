package board_construction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chess_pieces.*;
import player.BlackPlayer;
import player.WhitePlayer;


public class Board {
	
	private final List<ChessTile> chessBoard;
	private final Collection<ChessPiece> whitePieces;
	private final Collection<ChessPiece> blackPieces;
	
	private final WhitePlayer whitePlayer;
	private final BlackPlayer blackPlayer;
	
	private Board(Builder builder) {
		this.chessBoard = buildGameBoard(builder);
		this.whitePieces = calculateActivePieces(this.chessBoard, PieceColour.WHITE);
		this.blackPieces = calculateActivePieces(this.chessBoard, PieceColour.BLACK);
		
		final Collection<Move> whiteStandardAllowedMoves = determineAllowedMoves(this.whitePieces);
		final Collection<Move> blackStandardAllowedMoves = determineAllowedMoves(this.blackPieces);
		
		this.whitePlayer = new WhitePlayer(this, whiteAllowedMoves, blackAllowedMoves);
		this.blackPlayer = new BlackPlayer(this, whiteAllowedMoves, blackAllowedMoves);
	}
	
	/*The override of toString method is used to print the board in a ascii text way */
		@Override public String toString() {
		final StringBuilder builder = new StringBuilder();
		for(int i=0; i < BoardUtils.NUM_TILES; i++) {
			final String tileText = this.chessBoard.get(i).toString();
			builder.append(String.format("%3s", tileText));
			if((i+1) % BoardUtils.TILES_IN_ROW == 0) {
				builder.append("\n");
			}
		}
		return builder.toString();
	}
		
		
		public Collection<ChessPiece> getBlackPieces() {
			return this.blackPieces;
		}
		public Collection<ChessPiece> getWhitePieces() {
			return this.whitePieces;
		}
		
	
	private Collection<Move> determineAllowedMoves(final Collection<ChessPiece> pieces) {
		final List<Move> allowedMoves = new ArrayList<>();
		for(final ChessPiece piece : pieces) {
			allowedMoves.addAll(piece.determineAllowedMoves(this));
		}
		return Collections.unmodifiableList(allowedMoves);
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
		return Collections.unmodifiableList(activePieces);
	}
	

	public ChessTile getTile(final int tileCoordinate) {
		return chessBoard.get(tileCoordinate);
	}
	
	
	/* This method is used to populate a list of tiles from 0->63, representing the chess board.  */
	private static List<ChessTile> buildGameBoard(final Builder builder) {
		final ChessTile[] tiles = new ChessTile[BoardUtils.NUM_TILES];
		for(int i=0; i<BoardUtils.NUM_TILES; i++) {
			tiles[i] = ChessTile.createTile(i, builder.boardConfig.get(i));
			/* Here we are using boardConfig to get the chess piece, if there is one on tile i, which we pass into the 
			 * createTile method. We will get either a new OccupiedTile or if no piece is on the tile, so null is passed, 
			 * we get an empty tile (see ChessTile.createAllEmptyTiles() method)        */
			
			
		}
		return Collections.unmodifiableList(Arrays.asList(tiles));
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
			this.boardConfig = new HashMap<>();
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
