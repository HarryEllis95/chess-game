package board_construction;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* I don't want this class being instantiated, it is simply a convenience class for storing useful constants
 * and methods which are static, and used in multiple classes in this project, to avoid cluttering the Board class. */
public class BoardUtils {

	public static final int NUM_TILES = 64;
	public static final int TILES_IN_ROW = 8;
	public static final int TILES_IN_COL = 8;
	
	// boolean arrays used for handling the column exclusions 
	public static final List<Boolean> FIRST_COLUMN = initialiseColumn(0);
	public static final List<Boolean> SECOND_COLUMN = initialiseColumn(1);
	public static final List<Boolean> SEVENTH_COLUMN = initialiseColumn(6);
	public static final List<Boolean> EIGHTH_COLUMN = initialiseColumn(7);
	
	public static final List<Boolean> EIGHTH_RANK = initialiseRow(56);    // tile id that begins the row
	public static final List<Boolean> SEVENTH_RANK = initialiseRow(48);  
	public static final List<Boolean> SIXTH_RANK = initialiseRow(40);
	public static final List<Boolean> FIFTH_RANK = initialiseRow(32);
	public static final List<Boolean> FOURTH_RANK = initialiseRow(24);
	public static final List<Boolean> THIRD_RANK = initialiseRow(16);
	public static final List<Boolean> SECOND_RANK = initialiseRow(8);
	public static final List<Boolean> FIRST_RANK = initialiseRow(0);
	
	public static final List<String> ALGEBRAIC_NOTATION = initAlgebraicNotation();
	public static final Map<String, Integer> POSITION_TO_COORDINATE = initPositionToCoordinateMap();

	
	
	private static List<Boolean> initialiseColumn(int columnNumber) {
        final Boolean[] column = new Boolean[NUM_TILES];
        for(int i = 0; i < column.length; i++) {
            column[i] = false;
        }
        do {
            column[columnNumber] = true;
            columnNumber += TILES_IN_ROW;
        } while(columnNumber < NUM_TILES);
		/* What the above code does is for every tile in the first column, we set the corresponding boolean in the
		 * column boolean array to true. Note, if we're at a8, doing columnNumber += 8 takes us to a7, and so on */
		return  Collections.unmodifiableList(Arrays.asList((column)));
	}
	
	private static List<Boolean> initialiseRow(int rowNumber) {
	    final Boolean[] row = new Boolean[NUM_TILES];
        for(int i = 0; i < row.length; i++) {
            row[i] = false;
        }
        do {
            row[rowNumber] = true;
            rowNumber++;
        } while(rowNumber % TILES_IN_ROW != 0);
        return Collections.unmodifiableList(Arrays.asList(row));
	}
	
	/* These next 2 methods are useful in the toString() method of the move class, where they are used to get tile index
	 * into typical algebraic chess tile notation  */
	private static List<String> initAlgebraicNotation() {
        return Collections.unmodifiableList(Arrays.asList(
                "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8",
                "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
                "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
                "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
                "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
                "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
                "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
                "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1"));
    }
	
	private static Map<String, Integer> initPositionToCoordinateMap() {
		final Map<String, Integer> positionToCoordinate = new HashMap<>();
		for(int i = 0; i < NUM_TILES; i++) {
			positionToCoordinate.put(ALGEBRAIC_NOTATION.get(i), i);
		}
		return Collections.unmodifiableMap(positionToCoordinate);
	}

	// We use this method to make sure that our move results in a legal board position (within board constraints)
	public static boolean isValidTileCoordinate(final int coordinate) {
		return coordinate >= 0 && coordinate < NUM_TILES;     
	}
	
	private BoardUtils() {
		throw new RuntimeException("This class shouldn't be instantiated");
	}
	
	public static int getCoordinateAtPosition(final String pos) {
		return POSITION_TO_COORDINATE.get(pos);
	}
	
	public static String getPositionAtCoordinate(final int coordinate) {
		return ALGEBRAIC_NOTATION.get(coordinate);
	}
	

	

}
