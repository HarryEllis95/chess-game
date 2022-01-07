package ai;

import board_construction.Board;
import chess_pieces.Move;

public interface MoveTheory {

	Move execute(Board board, int depth);
	
}
