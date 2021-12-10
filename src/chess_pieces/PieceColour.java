package chess_pieces;


/* Makes sense to use an enum to define the piece colour, as we just have 2 constant instances here,
 * with some defined behaviour    */
public enum PieceColour {

	WHITE {
		public int getDirection() {
			return -1;
		}
		public boolean isBlack() {
			return false;
		}
		public boolean isWhite() {
			return true;
		}
	}, 
	
	BLACK {
		public int getDirection() {
			return 1;
		}
		public boolean isBlack() {
			return true;
		}
		public boolean isWhite() {
			return false;
		}
	};
	
	public abstract int getDirection();    
	// this method is relevant for pawns which have a defined movement direction dependent on piece colour
	
	public abstract boolean isWhite();
	public abstract boolean isBlack();
}
