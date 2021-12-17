package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import com.google.common.primitives.Ints;

import chess_pieces.ChessPiece;
import chess_pieces.Move;
import gui.Table.MoveLog;

/* This class is built to handle how taking moves are displayed in the GUI, a lot of which is simple gui theory  */
public class TakenPiecesDisplay extends JPanel {

	private final JPanel northPanel;
	private final JPanel southPanel;
	
	private static final Color PANEL_COLOUR = Color.decode("0xFDFE6");
	private static final Dimension TAKEN_PIECES_DIMENSION = new Dimension(40, 40);
	private static final EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);
	
	private static String defaultPieceImagesPath = 
			"C:\\Users\\44784\\Documents\\Programming\\Java\\Projects\\ChessGame\\chess-game\\pieces\\";
	
	public TakenPiecesDisplay() {
		super(new BorderLayout());
		this.setBackground(PANEL_COLOUR);
		this.setBorder(PANEL_BORDER);
		
		this.northPanel = new JPanel(new GridLayout(8, 2));
		this.northPanel.setBackground(PANEL_COLOUR);
		this.southPanel = new JPanel(new GridLayout(8, 2));
		this.southPanel.setBackground(PANEL_COLOUR);
		
		this.add(this.northPanel, BorderLayout.NORTH);
		this.add(this.southPanel, BorderLayout.SOUTH);
		setPreferredSize(TAKEN_PIECES_DIMENSION); 
	}
	
	public void redo(final MoveLog moveLog) {
		this.southPanel.removeAll();
		this.northPanel.removeAll();
		
		final List<ChessPiece> whiteTakenPieces = new ArrayList<>();
		final List<ChessPiece> blackTakenPieces = new ArrayList<>();
		
		for(final Move move : moveLog.getMoves()) {
			if(move.isAttacking()) {
				final ChessPiece takenPiece = move.getAttackedPiece();
				if(takenPiece.getPieceColour().isWhite()) {
					whiteTakenPieces.add(takenPiece);
				} else if(takenPiece.getPieceColour().isBlack()) {
					blackTakenPieces.add(takenPiece);
				} else {
					throw new RuntimeException("error occured");
				}
			}
		}
		
		// implement a comparator to sort the pieces based on piece value
		Collections.sort(whiteTakenPieces, new Comparator<ChessPiece>() {
			@Override public int compare(ChessPiece p1, ChessPiece p2) {
				return Ints.compare(p1.getPieceValue(), p2.getPieceValue());
			}
		});
		
		Collections.sort(whiteTakenPieces, new Comparator<ChessPiece>() {
			@Override public int compare(ChessPiece p1, ChessPiece p2) {
				return Ints.compare(p1.getPieceValue(), p2.getPieceValue());
			}
		});
		
		for(final ChessPiece takenPiece : whiteTakenPieces) {
			try {
				System.out.println(defaultPieceImagesPath + 
						takenPiece.getPieceColour().toString().substring(0, 1) + "" + takenPiece.toString());
				final BufferedImage image = ImageIO.read(new File(defaultPieceImagesPath + 
						takenPiece.getPieceColour().toString().substring(0, 1) + "" + takenPiece.toString()));
				final ImageIcon ico = new ImageIcon(image);
				final JLabel imageLabel = new JLabel();
			} catch(final IOException e) {
				e.printStackTrace();
			}
		}
		
		for(final ChessPiece takenPiece : blackTakenPieces) {
			try {
				final BufferedImage image = ImageIO.read(new File(defaultPieceImagesPath + 
						takenPiece.getPieceColour().toString().substring(0, 1) + "" + takenPiece.toString()));
				final ImageIcon ico = new ImageIcon(image);
				final JLabel imageLabel = new JLabel();
			} catch(final IOException e) {
				e.printStackTrace();
			}
		}
		
		validate();
		
	}
}
	
	
	

