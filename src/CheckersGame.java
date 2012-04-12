import java.awt.FlowLayout;
import java.awt.GridLayout;
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


public class CheckersGame implements MouseListener {
	
	/** The frame that will serve to holds the contents of our game */
	private JFrame frame;
	
	/** The panel that will hold our Board */
	private JPanel boardpanel;
	
	/** The label that will keep track of remaining pieces for each side */
	private JLabel piecesLabel;
	
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
		
		//display the interface
		CreateAndShowGUI();
		
		//set the initial turn
		currentTurn = Color.BLACK;
		
		//initial values for checkers
		redCheckersLeft = 12;
		blackCheckersLeft = 12;
		
		//show how many checkers are left
		updateStatus();
		
		//event-driven onward
	}
	
	
	public void CreateAndShowGUI() {
		
		frame = new JFrame("JCheckers");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new FlowLayout());
		
		frame.getContentPane().setLayout(
				new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		
		boardpanel = new JPanel(new GridLayout(8, 8));
		boardpanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		board = new Board();
		board.placeStartingPieces();
		
		piecesLabel = new JLabel(" ");
		piecesLabel.setHorizontalTextPosition(JLabel.LEFT);
		piecesLabel.setVerticalTextPosition(JLabel.BOTTOM);
		//piecesLabel.setSize(piecesLabel.getPreferredSize());
		
		addBoardToPanel(board, boardpanel);
		
		
		frame.add(boardpanel);
		frame.add(piecesLabel);
		frame.pack();
		
		//Resize the frame because for some reason it wants to cut off the last character of our JLabel
		Rectangle boundingRect = frame.getBounds();
		frame.setBounds(boundingRect.x, boundingRect.y, boundingRect.width + 5, boundingRect.height);
		
		frame.setVisible(true);


	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		

		Square sel = (Square)e.getComponent();
		
		//Check to see if the user highlighted a piece that corresponds to their turn
		if(sel.isOccupied() && sel.getOccupant().getColor() != currentTurn) {
			piecesLabel.setText("Ash! This isn't the time to use that!");
			return;
		}
		
		if(!sel.isOccupied() && selectedSquare == null) {
			//The user does not have a selected Piece, and has tried to select an empty location, so do nothing
		}
		
		else if(!sel.isOccupied() && selectedSquare != null) {
			//The user is trying to make a move by moving from the selectedSquare to the one they just clicked
			//First check to see if their choice corresponds to a square in possibleMoves
			
			boolean found = false;
			boolean jumped = false;
			
			for(Square choice : possibleMoves) {
				if(choice.equals(sel)) {
					//Move found in the Vector of possible moves, so perform it
					//First, store in a variable whether or not a jump was performed
					jumped = board.move(selectedSquare, sel);
					found = true;
				}
			}
			

			
			if(found) {
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
				
				for(Square curr : possibleMoves) 
					curr.setHighlight(false);
				
				selectedSquare.setHighlight(false);
				selectedSquare = null;
				
				endTurn();
				//Update the number of checkers left
				updateStatus();
				}
			
			else if(!found) 
				//Tell the user the obvious: that they can't move there.
				piecesLabel.setText("Can't let you do that, Dave");
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
	
	


	
	/** Determine if a game has yet ended
	 * 
	 * @return 				True if game has ended, false otherwise
	 */
	public boolean gameOver() {
		if (this.blackCheckersLeft == 0 || this.redCheckersLeft == 0)
			return true;
		
		
		return false;
	}
	
	/** Update the text of piecesLeft to a string representation of the number of pieces left for both sides */
	public void updateStatus() {
		piecesLabel.setText("Red pieces left: " + redCheckersLeft + "             Black pieces left: " + blackCheckersLeft);
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
	
	
	/** Switch turns at the end of the current player's turn */
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
