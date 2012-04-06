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


@SuppressWarnings("serial")
public class CheckersGame extends JPanel implements MouseListener {
	
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
	private Square selectedSquare;
	
	/** Maintain a Vector<Square> that contains the possible next moves */
	private Vector<Square> possibleMoves;
	
	/** Constructor takes no arguments and forms a new game */
	public CheckersGame() {
		
		frame = new JFrame("JCheckers");
		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		superpanel = new JPanel(new GridLayout(8, 8));
		
		board = new Board();
		board.placeStartingPieces();
		board.redraw();
		
		addBoardToPanel(board, superpanel);
		
		
		frame.add(superpanel);
		frame.pack();
		
		frame.setVisible(true);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		

		Square sel = (Square)e.getComponent();
		
		
		if(!sel.isOccupied() && selectedSquare == null) {
			//The user does not have a selected Piece, and has tried to select an empty location
			JOptionPane.showMessageDialog(null, "No piece at row " + sel.getRow() + ", col " + sel.getCol());
		}
		
		else if(!sel.isOccupied() && selectedSquare != null) {
			//The user is trying to make a move by moving from the selectedSquare to the one they just clicked
			//First check to see if their choice corresponds to a square in possibleMoves
			
			boolean found = false;
			for(Square choice : possibleMoves) {
				if(choice.equals(sel)) {
					//Perform move
					boolean jumped = board.move(selectedSquare, sel);
					found = true;
				}
			}
			
			if(found) {
				for(Square curr : possibleMoves) 
					curr.setHighlight(false);
				selectedSquare.setHighlight(false);
				selectedSquare = null;
				}
			
			if(!found) 
				//Tell the user the obvious: that they can't move there.
				JOptionPane.showMessageDialog(null, "Invalid move choice");
		}
		
		
		else if(selectedSquare == null) {
			//There is currently no square selected, so proceed to highlight all possible moves
			selectedSquare = sel;
			selectedSquare.setHighlight(true);
		
			possibleMoves = board.getPossibleMoves(selectedSquare.getOccupant());
			for(Square highlight : possibleMoves)
				highlight.setHighlight(true);
			
		}
		
		else if(sel.equals(selectedSquare)) {
			//The user has deselected the current square
			selectedSquare.setHighlight(false);
			
			for(Square unHighlight : possibleMoves)
				unHighlight.setHighlight(false);
			
			selectedSquare = null;
		}
		
		else if(!sel.equals(selectedSquare)) {
			//The user has clicked on a different square than the one currently selected
			selectedSquare.setHighlight(false);
			
			for(Square unHighlight : possibleMoves)
				unHighlight.setHighlight(false);
			
			//Reset selectedSquare to the one currently under the cursor
			selectedSquare = sel;
			selectedSquare.setHighlight(true);
			possibleMoves = board.getPossibleMoves(selectedSquare.getOccupant());
			for(Square Highlight : possibleMoves)
				Highlight.setHighlight(true);
			
		}
		
	}

	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	
	public void addBoardToPanel(Board b, JPanel p) {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				Square sq = b.getSquare(i, j);
				sq.addMouseListener(this);
				p.add(sq);
			}
		}
	}
	
	
	public void CreateAndShowGUI() {

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
	
	
	public static void main(String[] args) {
		new CheckersGame();
	}
	


}
