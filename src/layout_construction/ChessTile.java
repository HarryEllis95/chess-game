package layout_construction;



public abstract class ChessTile {

	int tileCoordinate;
	public ChessTile(int tileCoordinate) {
		this.tileCoordinate = this.tileCoordinate;
	}
	
	public abstract boolean isTileOccupied();
	
	public abstract ChessPiece getPiece();
	
	
	public static final class EmptyTile extends ChessTile {
		
		EmptyTile(int coordinate) {
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
		
		ChessPiece pieceOnTile;
		
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
