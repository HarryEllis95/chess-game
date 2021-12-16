package gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import static javax.swing.SwingUtilities.isRightMouseButton;
import static javax.swing.SwingUtilities.isLeftMouseButton;

import board_construction.Board;
import board_construction.BoardTransform;
import board_construction.BoardUtils;
import board_construction.ChessTile;
import chess_pieces.ChessPiece;
import chess_pieces.Move;


public class Table {

	
	private final JFrame gameFrame;
	private final BoardPanel boardPanel;
	private Board chessBoard;
	
	private ChessTile sourceTile;
	private ChessTile destinationTile;
	private ChessPiece humanMovedPiece;
	
	private final Color lightTileColour = Color.decode("#FFFACD");;
	private final Color darkTileColour = Color.decode("#593E1A");
	
	private final static Dimension OUTER_FRAME_SIZE = new Dimension(600,600);
	private final static Dimension BOARD_PANEL_SIZE = new Dimension(400, 350);
	private final static Dimension TILE_PANEL_SIZE = new Dimension(10, 10);
	private static String defaultPieceImagesPath = "C:\\Users\\44784\\Documents\\Programming\\Java\\Projects\\ChessGame\\chess-game\\pieces\\";
	
	public Table() {
		this.gameFrame = new JFrame("PlayGame");
		this.gameFrame.setLayout(new BorderLayout());
		final JMenuBar tableMenuBar = createTableMenuBar();	
		this.gameFrame.setJMenuBar(tableMenuBar);
		this.gameFrame.setSize(OUTER_FRAME_SIZE);
		
		this.chessBoard = Board.createBoard();
		
		this.boardPanel = new BoardPanel();
		this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
		this.gameFrame.setVisible(true);
	}
	
	private JMenuBar createTableMenuBar() {
		final JMenuBar tableMenuBar = new JMenuBar();
		tableMenuBar.add(createFileMenu());
		return tableMenuBar;
	}
	
	private JMenu createFileMenu() {
		final JMenu fileMenu = new JMenu("File");
		final JMenuItem openPGN = new JMenuItem("Load PGN File");
		openPGN.addActionListener(new ActionListener() {  // define anonymous class 
			
			@Override public void actionPerformed(ActionEvent e) {
				System.out.println("open up that pgn file!");
			}
		});
		fileMenu.add(openPGN);
		final JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		fileMenu.add(exitMenuItem);
		
		return fileMenu;
	}
	
	
	private class BoardPanel extends JPanel {
		
		final List<TilePanel> boardTiles;
		
		BoardPanel() {
			super(new GridLayout(8, 8));
			this.boardTiles = new ArrayList<>();
			for(int i=0; i<BoardUtils.NUM_TILES; i++) {
				final TilePanel tilePanel = new TilePanel(this, i);
				this.boardTiles.add(tilePanel);
				add(tilePanel);
			}
			setPreferredSize(BOARD_PANEL_SIZE);
			validate();
		}
		
		public void drawBoard(final Board board) {
			removeAll();
			for(final TilePanel tilePanel : boardTiles) {
				tilePanel.drawTile(board);
				add(tilePanel);
			}
			validate();
			repaint();
		}
	}
	
	
	private class TilePanel extends JPanel {
		
		private final int tileId;
		
		TilePanel(final BoardPanel boardPanel, final int tileId) {
			super(new GridBagLayout());
			this.tileId = tileId;
			setPreferredSize(TILE_PANEL_SIZE);
			assignTileColour();
			assignTilePieceIcon(chessBoard);
			
			addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(isRightMouseButton(e)) {  // check if event is a right mouse click, which I don't want to do anything
						
						sourceTile = null;
						destinationTile = null;
						humanMovedPiece = null;
						
					} else if(isLeftMouseButton(e)) {  // check if event is a left mouse click - moving a piece 
						
						// first click - select source tile
						if(sourceTile == null) {
							sourceTile = chessBoard.getTile(tileId);
							humanMovedPiece = sourceTile.getPiece();
							if(humanMovedPiece == null) {
								sourceTile = null;
							}
						} else {
							// second click
							destinationTile = chessBoard.getTile(tileId);
							final Move move = Move.MoveFactory.createMove(chessBoard, sourceTile.getTileCoordinate(),
									destinationTile.getTileCoordinate());
							final BoardTransform transform = chessBoard.currentPlayer().makeMove(move);
							if(transform.getMoveStatus().isDone()) {
								chessBoard = transform.getTransformedBoard();
							}
							sourceTile = null;
							destinationTile = null;
							humanMovedPiece = null;
						}
						SwingUtilities.invokeLater(new Runnable() {
							@Override public void run() {  // define thread tast
								boardPanel.drawBoard(chessBoard);
							}
						});
					}			
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
			});
			
			validate();
		}
		
		private void assignTilePieceIcon(final Board board) {
			this.removeAll();
			if(board.getTile(this.tileId).isTileOccupied()) {
				try {
					final BufferedImage image = ImageIO.read(new File(defaultPieceImagesPath + 
							board.getTile(this.tileId).getPiece().getPieceColour().toString().substring(0, 1) +
							board.getTile(this.tileId).getPiece().toString() + ".gif"));
					add(new JLabel(new ImageIcon(image)));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		public void drawTile(final Board board) {
			assignTileColour();
			assignTilePieceIcon(board);
			validate();
			repaint();
		}
		
		private void assignTileColour() {
			// workings out tile id's corresponding to dark or light tile colour - simple algorithm 
			if(BoardUtils.FIRST_RANK[this.tileId] || BoardUtils.THIRD_RANK[this.tileId] ||
					BoardUtils.FIFTH_RANK[this.tileId] || BoardUtils.SEVENTH_RANK[this.tileId]) {
				setBackground(this.tileId % 2 == 0 ? lightTileColour : darkTileColour);
			} else if (BoardUtils.SECOND_RANK[this.tileId] || BoardUtils.FOURTH_RANK[this.tileId] ||
					BoardUtils.SIXTH_RANK[this.tileId] || BoardUtils.EIGHTH_RANK[this.tileId]) {
				setBackground(this.tileId % 2 != 0 ? lightTileColour : darkTileColour);
			}
			
		}
	}
}
