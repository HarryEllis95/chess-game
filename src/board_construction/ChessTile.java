package board_construction;

import java.util.*;
import com.google.common.collect.ImmutableMap;
import chess_pieces.ChessPiece;


/* Create abstract class to define implementation details of the chess tiles. I want this to be immutable */
public abstract class ChessTile {

	private static final Map<Integer, EmptyTile> Empty_Tiles = createAllEmptyTiles();
	// now if we wanted the nth tile in the chess board, we can call Empty_Tiles.get(n-1) (see inside createTile() method)
	
	protected final int tileCoordinate;
	
	private ChessTile(int tileCoordinate) {  // private constructor for immutability 
		this.tileCoordinate = tileCoordinate;
	}
	
	private static Map<Integer, EmptyTile> createAllEmptyTiles() {
		final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
		for(int i=0; i<64; i++) {
			emptyTileMap.put(i, new EmptyTile(i));  // puts 64 empty tile objects into this map 
		}
		/* I don't want the map object to be mutable. A common way to achieve this is using Guava's ImmutableMap.
		 * I prefer this to using Collections.unmodifiableMap() although both can work here */
		return ImmutableMap.copyOf(emptyTileMap);
	}
	
	public static ChessTile createTile(final int tileCoordinate, final ChessPiece piece) {
		return piece != null ? new OccupiedTile(tileCoordinate, piece) : Empty_Tiles.get(tileCoordinate);
	}

	// abstract methods which must be implemented by the inheriting classes 
	public abstract boolean isTileOccupied();
	
	public abstract ChessPiece getPiece();
	
	
	/* Using a static inner class to define EmptyTile and ChessTile, instead of putting them in separate files. 
	 * Due to the nature of them being small simple classes related to the construction of the tiles, I felt this fitted 
	 * better. Made static to avoid them having inherited state from ChessTile. */
	public static final class EmptyTile extends ChessTile {
		
		private EmptyTile(final int coordinate) {  
			super(coordinate);
		}
		
		@Override public boolean isTileOccupied() {
			return false;
		}
		
		@Override public ChessPiece getPiece() {
			return null;
		}
	}	
	
	public static final class OccupiedTile extends ChessTile {
		
		private final ChessPiece pieceOnTile;
		
		private OccupiedTile(int tileCoordinate, ChessPiece pieceOnTile) {
			super(tileCoordinate);
			this.pieceOnTile = pieceOnTile;
		}
		
		@Override public boolean isTileOccupied() {
			return true;
		}
		
		@Override public ChessPiece getPiece() {
			return this.pieceOnTile;
		}
	}
}



