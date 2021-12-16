package run_tests;

import board_construction.Board;
import gui.Table;

public class PlayGame {

	
	public static void main(String[] args) {
		
		Board board = Board.createBoard();
		System.out.println(board);
		
		Table table = new Table();
	}
}
