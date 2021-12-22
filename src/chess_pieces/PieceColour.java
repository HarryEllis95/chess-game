package chess_pieces;

import board_construction.BoardUtils;
import player.BlackPlayer;
import player.Player;
import player.WhitePlayer;

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
		@Override
		public Player choosePlayer(final WhitePlayer whitePlayer, final BlackPlayer blackPlayer) {
			return whitePlayer;
		}
		@Override
		public boolean isPawnPromotionSquare(int pos) {
			return BoardUtils.EIGHTH_RANK.get(pos);
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
		@Override
		public Player choosePlayer(final WhitePlayer whitePlayer, final BlackPlayer blackPlayer) {
			return blackPlayer;
		}
		@Override
		public boolean isPawnPromotionSquare(int pos) {
			return BoardUtils.FIRST_RANK.get(pos);
		}
	};
	
	public abstract int getDirection();    
	// this method is relevant for pawns which have a defined movement direction dependent on piece colour
	
	public abstract boolean isWhite();
	public abstract boolean isBlack();

	public abstract Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer);
	
	public abstract boolean isPawnPromotionSquare(int pos);
}
