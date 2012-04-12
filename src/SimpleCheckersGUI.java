import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.awt.*;
import java.util.Vector;

import javax.swing.*;

/** Creates a new instance of Board and draws a necessary graphical interface
 *  for the user to select possible moves. Provides options to exit and to start a new game
 * 
 * 
 * @author Matthew Proetsch
 * @version 0.9b
 */


public class SimpleCheckersGUI implements MouseListener,
									ActionListener {
	
	/** The frame that will serve to holds the contents of our game */
	private JFrame frame;
	
	/** The panel that will hold our Board */
	private JPanel boardpanel;
	
	/** The label that will keep track of remaining pieces for each side */
	private JLabel piecesLabel;
	
	/** Menubar containing Exit and New Game options */
	private JMenuBar menubar;
	
	/** File menu */
	private JMenu fileMenu;
	
	/** New Game menu item */
	private JMenuItem newGame;
	
	/** Exit menu item */
	private JMenuItem exit;
	
	/** Keep track of the current turn */
	private Color currentTurn;
	
	/** Border width between squares in the game board */
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
	public SimpleCheckersGUI() {
		
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
	
	/** Set up the visual interface to the game */
	public void CreateAndShowGUI() {
		
		//Set up the window information
		frame = new JFrame("SimpleCheckersGUI - Matthew Proetsch");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new FlowLayout());
		
		frame.getContentPane().setLayout(
				new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		
		//Give our Board a visual representation
		boardpanel = new JPanel(new GridLayout(8, 8));
		boardpanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		board = new Board();
		board.placeStartingPieces();
		
		//Keep track of how many pieces are left
		piecesLabel = new JLabel(" ");
		piecesLabel.setHorizontalTextPosition(JLabel.LEFT);
		piecesLabel.setVerticalTextPosition(JLabel.BOTTOM);
		
		//Add the menubar to the window
		menubar = new JMenuBar();
		fileMenu = new JMenu("File");
		
		newGame = new JMenuItem("New Game");
		newGame.addActionListener(this);
		
		exit = new JMenuItem("Exit");
		exit.addActionListener(this);
		
		fileMenu.add(newGame);
		fileMenu.add(exit);
		menubar.add(fileMenu);

		//Add our board to boardpanel and add everything to the window
		addBoardToPanel(board, boardpanel);
		frame.add(boardpanel);
		frame.add(piecesLabel);
		frame.setJMenuBar(menubar);
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
					
				}
				
				for(Square curr : possibleMoves) 
					curr.setHighlight(false);
				
				selectedSquare.setHighlight(false);
				selectedSquare = null;
				
				endTurn();
				//Update the number of checkers left
				updateStatus();
				
				
				//See if that move ended the game
				String winningStr = winner();
				if(winningStr != null) {
					int restart = JOptionPane.showConfirmDialog(null, winningStr + " Do you want to start a new game?", "New Game?", JOptionPane.YES_NO_OPTION);
					
					if(restart == JOptionPane.YES_OPTION)
						restartGame();
				}
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

	//Must implement as per MouseListener
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == newGame) {
			restartGame();
		}
		else if(e.getSource() == exit) {
			frame.setVisible(false);
			frame.dispose();
		}
		
	}
	
	
	public void addBoardToPanel(Board b, JPanel p) {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				Square sq = b.getSquare(i, j);
				sq.addMouseListener(this);
				
				JPanel PanelForBorders = new JPanel(new FlowLayout());
				PanelForBorders.setBorder(BorderFactory.createLineBorder(Color.BLACK,
																					borderWidth));
				PanelForBorders.add(sq);
				if(sq.getBackgroundColor() == Square.BackgroundColor.DARKGRAY)
					PanelForBorders.setBackground(Color.DARK_GRAY);
				else
					PanelForBorders.setBackground(Color.LIGHT_GRAY);
				p.add(PanelForBorders);
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
	
	
	/** Find out, if the game is over, who won and how that side won
	 * 
	 * @return 				A String containing the side which won, as well as how they won (took other side's pieces, other side could make no more moves)
	 */
	public String winner() {
		
		//Check first ending condition: one side loses all pieces
		if(blackCheckersLeft == 0)
			return "Red has won by taking Black's pieces!";
			
		if(redCheckersLeft == 0)
			return "Black has won by taking Red's pieces!";
		
		
		//Check second ending condition: one side cannot move its remaining pieces
		boolean redCanMove = false;
		boolean blackCanMove = false;
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				
				//Get all possible moves for all pieces currently on the board
				if(board.getSquare(i, j).isOccupied()) {
					Vector<Square> potentialMoves = board.getPossibleMoves(board.getSquare(i, j).getOccupant());
					
					if(! potentialMoves.isEmpty()) {
						//The potentialMoves Vector contains at least one square, so that side is capable of making a move
						//Find out what the color of the piece that can make the move is, then set its <color>CanMove var to true
						
						if(board.getSquare(i, j).getOccupant().getColor() == Color.black)
							blackCanMove = true;
						else
							redCanMove = true;
						
					}
				}
			}
		}
		
		if(redCanMove && !blackCanMove) {
			return "Red wins since Black can make no more moves!";
		}
		else if(blackCanMove && !redCanMove) {
			return "Black wins since Red can make no more moves!";
		}
		else if(!redCanMove && !blackCanMove) {
			return "Neither side can make a move!";
		}
		
		//None of the above cases hold true, so the game is not over yet
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
	
	/** End the game and start anew by resetting everything */
	public void restartGame() {
		
		frame.setVisible(false);
		selectedSquare = null;
		
		frame.remove(boardpanel);
		boardpanel = new JPanel(new GridLayout(8, 8));
		boardpanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		board = new Board();
		board.placeStartingPieces();
		
		addBoardToPanel(board, boardpanel);
		frame.add(boardpanel, 0);
		
		redCheckersLeft = 12;
		blackCheckersLeft = 12;
		
		currentTurn = Color.BLACK;
		
		updateStatus();
		frame.pack();
		frame.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		new SimpleCheckersGUI();
	}



	

}
