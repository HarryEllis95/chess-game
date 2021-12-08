package layout_construction;

import java.util.*;
import com.google.common.collect.ImmutableMap;

/* Create abstract class to define implementation details of the chess tiles. I want this to be immutable */
public abstract class ChessTile {

	private static final Map<Integer, EmptyTile> Empty_Tiles = createAllEmptyTiles();
	protected final int tileCoordinate;
	
	ChessTile(int tileCoordinate) {
		this.tileCoordinate = tileCoordinate;
	}
	
	private static Map<Integer, EmptyTile> createAllEmptyTiles() {
		final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
		for(int i=0; i<64; i++) {
			emptyTileMap.put(i, new EmptyTile(i));  // puts 64 empty tile objects into this map 
		}
		// I don't want the map object to be mutable. A common way to achieve this is using Guava's Immutable Map
		return ImmutableMap.copyOf(emptyTileMap);
	}

	public abstract boolean isTileOccupied();
	
	public abstract ChessPiece getPiece();
	
	/* Using a static inner class to define EmptyTile and ChessTile, instead of putting them in seperates files. 
	 * Due to the nature of them being small simple classes related to the construction of the tiles, I felt this fitted 
	 * better. Made static to avoid them having inherited state from tile. */
	public static final class EmptyTile extends ChessTile {
		
		EmptyTile(final int coordinate) {
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
		
		OccupiedTile(int tileCoordinate, ChessPiece pieceOnTile) {
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



