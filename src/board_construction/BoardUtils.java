package board_construction;

/* I don't want this class being instantiated, it is simply a convenience class for storing useful constants
 * and methods which are static, and used in multiple classes in this project, to avoid cluttering the Board class. */
public class BoardUtils {

	public static final boolean[] FIRST_COLUMN = null;
	public static final boolean[] SECOND_COLUMN = null;
	public static final boolean[] SEVENTH_COLUMN = null;
	public static final boolean[] EIGHTH_COLUMN = null;
	
	private BoardUtils() {
		throw new RuntimeException("This class shouldn't be instantiated");
	}
	
	// We use this method to make sure that our move results in a legal board position (within board constraints)
	public static boolean isValidTileCoordinate(int coordinate) {
		return coordinate >= 0 && coordinate < 64;     
	}
}
