package run_tests;

import static org.junit.Assert.*;

import org.junit.Test;

import board_construction.Board;


/* UNIT TESTS */

public class BoardTest {

	@Test
	public void initialBoard() {
		final Board board = Board.createBoard();
		assertEquals(board.currentPlayer().getAllowedMoves().size(), 20);
        assertEquals(board.currentPlayer().getOpponent().getAllowedMoves().size(), 20);
        assertFalse(board.currentPlayer().isInCheck());
        assertFalse(board.currentPlayer().isInCheckMate());
        assertFalse(board.currentPlayer().isCastled());
       // assertTrue(board.currentPlayer().isKingSideCastleCapable());
       // assertTrue(board.currentPlayer().isQueenSideCastleCapable());
        assertEquals(board.currentPlayer(), board.whitePlayer());
        assertEquals(board.currentPlayer().getOpponent(), board.blackPlayer());
        assertFalse(board.currentPlayer().getOpponent().isInCheck());
        assertFalse(board.currentPlayer().getOpponent().isInCheckMate());
        assertFalse(board.currentPlayer().getOpponent().isCastled());
      //  assertTrue(board.currentPlayer().getOpponent().isKingSideCastleCapable());
      //  assertTrue(board.currentPlayer().getOpponent().isQueenSideCastleCapable());
        assertTrue(board.whitePlayer().toString().equals("White"));
        assertTrue(board.blackPlayer().toString().equals("Black"));
	}
}
