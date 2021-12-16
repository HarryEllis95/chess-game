package player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import board_construction.Board;
import board_construction.BoardTransform;
import chess_pieces.ChessPiece;
import chess_pieces.King;
import chess_pieces.Move;
import chess_pieces.PieceColour;


public abstract class Player {

	protected final Board board;
	protected final King playersKing;
	protected final Collection<Move> allowedMoves;
	private final boolean isInCheck;
	
	Player(final Board board, final Collection<Move> allowedMoves, final Collection<Move> opponentMoves) {
		this.board = board;
		this.playersKing = establishKing();
		allowedMoves.addAll(calculateCastles(allowedMoves, opponentMoves));
		this.allowedMoves =  Collections.unmodifiableCollection(allowedMoves);
		/* What the following says is that - does the opponents moves attack the current plays kings position,
		 * and get all those different attacks. If that is not empty, e.g there is a move which attacks the king,
		 * that means that current player is in check.   */
		this.isInCheck = !(Player.calculateAttacksOnTile(this.playersKing.getPiecePosition(), opponentMoves).isEmpty());
	}
	
	public King getPlayerKing() {
		return this.playersKing;
	}
	
	public Collection<Move> getAllowedMoves() {
		return this.allowedMoves;
	}
	
	
	protected static Collection<Move> calculateAttacksOnTile(int piecePosition, Collection<Move> moves) {
		final List<Move> attackMoves = new ArrayList<>();
		/* Here we're looping through each one of the opponents moves and see if the final coordinate 
		 * of that move is an attacking move. 	 */
		for(final Move move : moves) {
			if(piecePosition == move.getDestinationCoordinate()) {
				attackMoves.add(move);
			}
		}
		return Collections.unmodifiableList(attackMoves);
	}
	
	protected boolean hasEscapeMoves() {
		/* To calculate if the king can escape, we go through each of the players legal moves and check if those
		 * moves result in the kings safety. If not, then the king must be in checkmate.       */
		for(final Move move : this.allowedMoves) {
			final BoardTransform transformation = makeMove(move);
			if(transformation.getMoveStatus().isDone()) {
				return true;
			}
		}
		return false;
	}
	
	// Use this method to check for a king, without a king we have no chess game!
	private King establishKing() {
		for(final ChessPiece piece : getActivePiece()) {
			if(piece.getPieceType().isKing()) {
				return (King) piece;
			}
		}
		throw new RuntimeException("Game is invalid - no King!");
	}
	
	
	public boolean isMoveAllowed(final Move move) {
		return this.allowedMoves.contains(move);
	}
	
	// Methods to check on the state of the game
	public boolean isInCheck() {
		return this.isInCheck;
	}
	
	public boolean isInCheckMate() {
		return this.isInCheck && !hasEscapeMoves();
	}
	
	public boolean isInStalemate() {
		return !this.isInCheck && !hasEscapeMoves();
	}
	
	public boolean isCastled() {
		return false;
	}
	
	// When we make a move, we return a BoardTransform - This is an important method!
	public BoardTransform makeMove(final Move move) {
		// If the move is illegal, we don't transform the board
		if(!isMoveAllowed(move)) {
			return new BoardTransform(this.board, move, MoveStatus.ILLEGAL_MOVE);
		}
		final Board transformBoard = move.execute();  // make the move - return transformed board
		// we now ask if there are any attacks on the players king as a result of the move - this isn't allowed
		final Collection<Move> kingAttacks = Player.calculateAttacksOnTile(
				transformBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(), 
				transformBoard.currentPlayer().getAllowedMoves());
		
		if(!kingAttacks.isEmpty()) {
			return new BoardTransform(this.board, move, MoveStatus.PLAYER_IN_CHECK);
		}
		
		return new BoardTransform(transformBoard, move, MoveStatus.DONE);
	}
	
	// abstract methods to be overridden in BlackPlayer and WhitePlayer classes
	public abstract Collection<ChessPiece> getActivePiece();
	public abstract PieceColour getColour();
	public abstract Player getOpponent();
	protected abstract Collection<Move> calculateCastles(Collection<Move> playerAllowed, Collection<Move> opponentAllowed);
	
}
