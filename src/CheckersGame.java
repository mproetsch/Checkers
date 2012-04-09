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
	
	/** Keep track of the current turn */
	private Color currentTurn;
	
	private final int borderWidth = 1;
	
	/** The board which will store our game's state */
	private Board board;
	
	/** The number of checkers remaining for Black side */
	private int blackCheckersLeft;
	
	/** The number of checkers remaining for Red side */
	private int redCheckersLeft;

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
		superpanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		board = new Board();
		board.placeStartingPieces();
		board.redraw();
		
		addBoardToPanel(board, superpanel);
		
		
		frame.add(superpanel);
		frame.pack();
		
		frame.setVisible(true);
		
		currentTurn = Color.BLACK;
		
		redCheckersLeft = 12;
		blackCheckersLeft = 12;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		

		Square sel = (Square)e.getComponent();
		
		//Check to see if the user highlighted a piece that corresponds to their turn
		if(sel.isOccupied())
			if(sel.getOccupant().getColor() != currentTurn) {
				JOptionPane.showMessageDialog(null, "You can't play your opponent's piece!");
				return;
			}
		
		if(!sel.isOccupied() && selectedSquare == null) {
			//The user does not have a selected Piece, and has tried to select an empty location
			JOptionPane.showMessageDialog(null, "No piece at row " + sel.getRow() + ", col " + sel.getCol());
		}
		
		else if(!sel.isOccupied() && selectedSquare != null) {
			//The user is trying to make a move by moving from the selectedSquare to the one they just clicked
			//First check to see if their choice corresponds to a square in possibleMoves
			
			boolean found = false;
			boolean jumped = false;
			
			for(Square choice : possibleMoves) {
				if(choice.equals(sel)) {
					//Move found in the Vector of possible moves, so perform it
					jumped = board.move(selectedSquare, sel);
					found = true;
				}
			}
			
			if(jumped) {
				if(currentTurn == Color.BLACK) {
					redCheckersLeft--;
				}
				else {
					blackCheckersLeft--;
				}
				
				if(gameOver()) {
					JOptionPane.showMessageDialog(null, winner() + " wins!");
				}
			}
			
			if(found) {
				for(Square curr : possibleMoves) 
					curr.setHighlight(false);
				
				selectedSquare.setHighlight(false);
				selectedSquare = null;
				
				endTurn();
				}
			
			else if(!found) 
				//Tell the user the obvious: that they can't move there.
				JOptionPane.showMessageDialog(null, "Invalid move choice");
		}
		
		
		else if(sel.isOccupied() && selectedSquare == null) {
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
				
				JPanel pointlessPanelForBorders = new JPanel(new FlowLayout());
				pointlessPanelForBorders.setBorder(BorderFactory.createLineBorder(Color.BLACK,
																					borderWidth));
				pointlessPanelForBorders.add(sq);
				if(sq.getBackgroundColor() == Square.BackgroundColor.DARKGRAY)
					pointlessPanelForBorders.setBackground(Color.DARK_GRAY);
				else
					pointlessPanelForBorders.setBackground(Color.LIGHT_GRAY);
				p.add(pointlessPanelForBorders);
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
	
	
	
	public void endTurn() {
		if(currentTurn == Color.BLACK) {
			currentTurn = Color.RED;
		}
		else {
			currentTurn = Color.BLACK;
		}
	}
	
	public static void main(String[] args) {
		new CheckersGame();
	}
	

}
