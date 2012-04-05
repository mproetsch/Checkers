import java.awt.Canvas;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.awt.*;
import java.util.Vector;

import javax.swing.*;

/** Creates a new game of Checkers and provides the GUI with an interface to access to the game's logic. Also creates a GUI frontend
 * 
 * 
 * @author Matthew Proetsch
 * @version 0.9b
 */



public class CheckersGame {
	
	private JFrame frame;
	private JPanel superpanel;
	
	private final int borderWidth = 1;
	
	
	/** The board which will store our game's state */
	private Board board;
	
	/** The number of checkers remaining for Black side */
	private int blackCheckersLeft = 15;
	
	/** The number of checkers remaining for Red side */
	private int redCheckersLeft = 15;

	/** Hold a reference to the currently selected Piece */
	private Piece selectedPiece;
	
	/** Constructor takes no arguments and forms a new game */
	public CheckersGame() {
		
		frame = new JFrame("JCheckers");
		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		
		board = new Board();
		board.placeStartingPieces();
		//board.redraw();
		
		frame.add(board);
		frame.pack();
		
		frame.setVisible(true);
		
	}
	

	
	/** Determine if a game has yet ended
	 * 
	 * @return 				True if game has ended, false otherwise
	 */
	public boolean gameOver() {
		if (this.blackCheckersLeft == 0 || this.redCheckersLeft == 0)
			return true;
		
		
		return false;
	}
	
	
	/** Return a String representation of the winner of the game
	 * 
	 * @return 				"Red" if Red won; "Black" if Black won
	 */
	public String winner() {
		if (this.gameOver()) {
			if(this.blackCheckersLeft == 0)
				return "Red";
			
			return "Black";
		}
		
		//Make Java happy
		return null;
	}
	
	
	/** Draws the current state of the game to the screen 
	 * 
	 * @param b 				The board to be drawn
	 * @param panel 			The panel on which to draw it
	 */
	public void drawBoard(Board b) {
		
		for(int i = 0; i < 8; i++) 
			for(int j = 0; j < 8; j++) {
				
				
				
			}
	}
	
	public static void main(String[] args) {
		new CheckersGame();
	}
	


}
