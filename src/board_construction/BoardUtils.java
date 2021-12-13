package board_construction;

/* I don't want this class being instantiated, it is simply a convenience class for storing useful constants
 * and methods which are static, and used in multiple classes in this project, to avoid cluttering the Board class. */
public class BoardUtils {

	// boolean arrays used for handling the column exclusions 
	public static final boolean[] FIRST_COLUMN = initialiseColumn(0);
	public static final boolean[] SECOND_COLUMN = initialiseColumn(1);
	public static final boolean[] SEVENTH_COLUMN = initialiseColumn(6);
	public static final boolean[] EIGHTH_COLUMN = initialiseColumn(7);
	
	public static final int NUM_TILES = 64;
	public static final int TILES_IN_ROW = 8;
	public static final int TILES_IN_COL = 8;
	
	public static final boolean[] SECOND_ROW = initialiseRow(8);  // tile id that begins the row
	public static final boolean[] SEVENTH_ROW = initialiseRow(48);
	
	
	private static boolean[] initialiseColumn(int columnNumber) {
		final boolean[] column = new boolean[NUM_TILES];
		do { 
			column[columnNumber] = true;
			columnNumber += TILES_IN_ROW;
		} while(columnNumber < NUM_TILES);
		/* What the above code does is for every tile in the first column, we set the corresponding boolean in the
		 * column boolean array to true. Note, if we're at a8, doing columnNumber += 8 takes us to a7, and so on */
		return column;
	}
	
	private static boolean[] initialiseRow(int rowNumber) {
		final boolean[] row = new boolean[NUM_TILES];
		do {
			row[rowNumber] = true;
			rowNumber++;
		} while(rowNumber % TILES_IN_ROW != 0);
		return row;
	}

	// We use this method to make sure that our move results in a legal board position (within board constraints)
	public static boolean isValidTileCoordinate(final int coordinate) {
		return coordinate >= 0 && coordinate < NUM_TILES;     
	}
	
	private BoardUtils() {
		throw new RuntimeException("This class shouldn't be instantiated");
	}
	

}
