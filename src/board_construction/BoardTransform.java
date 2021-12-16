package board_construction;

import chess_pieces.Move;
import player.MoveStatus;

/* This class is used to represent the transition of the board after a move is made, and the information carried over
 * between the two board states.                */
public class BoardTransform {
	
	private final Board transitionBoard;
	private final Move move;
	private final MoveStatus moveStatus;
	
	public BoardTransform(final Board transitionBoard, final Move move, final MoveStatus moveStatus) {
		this.transitionBoard = transitionBoard;
		this.move = move;
		this.moveStatus = moveStatus;
	}
	
	public MoveStatus getMoveStatus() {
		return this.moveStatus;
	}
	
	public Board getTransformedBoard() {
		return this.transitionBoard;
	}

}
