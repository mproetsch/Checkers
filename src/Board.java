import java.awt.GridLayout;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;
import javax.swing.*;


/** Stores the game board and relevant information, such as which pieces are on
 * which square
 *
 * @author Matthew Proetsch
 * @version 0.9b
 */
@SuppressWarnings("serial")
public class Board extends JPanel {

    /** Number of rows */
    private static final int rows = 8;
    /** Number of columns */
    private static final int cols = 8;
    /** An array of Squares that represents the game board */
    private Square[][] gameBoard;


    /** Constructor takes no args and produces a Board of size rows x cols with alternating background colors */
    public Board() {
    	
    	this.setLayout(new GridLayout(8, 8));
		
		
    	gameBoard = new Square[rows][cols];
    	
    	//Set up the game board with alternating colors
    	boolean lastcolor = false;
    	for(int i = 0; i < rows; i++) {
    		for(int j = 0; j < cols; j++) {
    			
    			if(lastcolor)
    				gameBoard[i][j] = new Square(Square.BackgroundColor.DARKGRAY, i, j);
    			else
    				gameBoard[i][j] = new Square(Square.BackgroundColor.LIGHTGRAY, i, j);
    			
    			//Toggle lastcolor
    			lastcolor = !lastcolor;
    		}
    		
    		lastcolor = !lastcolor;
    	}
    	
    	//Add each element in gameBoard to this JPanel
    	for(int i = 0; i < rows; i++)
    		for(int j = 0; j < rows; j++)
    			this.add(gameBoard[i][j]);
    	
    	//Implement a f*cking MouseListener

    	
    }
    
    

    
    /** Check to see if a position in this Board is in bounds
     * 
     * @param row			The row to be checked
     * @param col			The column to be checked
     * @return				True if in bounds, false if not
     */
    public static boolean inBounds(int row, int col) {
    	if(row >= 0 && row < rows &&
    		col >= 0 && col < cols)
    		
    		return true;
    	
    	
    	return false;
    	
    }
    
    /** Have this Board update itself */
    public void redraw() {
    	
    	for(int i = 0; i < rows; i++)
    		for(int j = 0; j < cols; j++) {
    			
    			Square sq = this.getSquare(i, j);
    			sq.paint(sq.getGraphics());
    		}
    }
    
    
    /** Returns the given square at (row, col) or null if out-of-bounds
     * 
     * @param row		The row at which the square should be
     * @param col		The column at which the square should be
     * @return			The square at (row, col)
     */
    public Square getSquare(int row, int col) {
        if(inBounds(row, col))
        	return gameBoard[row][col];
        
        
        return null;
    }
    
    /** Fill this Board with Red pieces on top, and Black pieces on bottom */
    public void placeStartingPieces() {
    	
		//Have the Red side on top, Black side on bottom
		//Establish the Red side first
		boolean put = true;
		for(int row = 0; row < 3; row++) {
			for(int col = 0; col < 8; col++) {
				
				if(put) {
					this.getSquare(row, col).setOccupant(new Piece(Piece.Color.RED, row, col));
					put = false;
				}
				
				//We want to alternate putting and not putting a new Square in the grid
				else
					put = true;
		
			}
		
			put = !put;
    	}
		
		//Now establish the Black side
		put = false;
		for(int row = 5; row < 8; row++) {
			for(int col = 0; col < 8; col++) {
				
				if(put) {
					this.getSquare(row, col).setOccupant(new Piece(Piece.Color.BLACK, row, col));
					put = false;
				}
				
				else
					put = true;
				
			}
				
			put = !put;
		}
    }
    
    
	/** Find all possible Squares to which this piece can move
	 * 
	 * @param p 				Piece for which moves should be found
	 * @param b 				Board for which we check moves
	 * 
	 * @return					A Vector of Squares to which this piece can move
	 */
	public Vector<Square> getPossibleMoves(Piece p) {
		/*Possible moves include up-left, up-right, down-left, down-right
		 * This corresponds to (row-- col--), (row-- col++),
		 * 						(row++ col--), (row++ col++) respectively
		 */
		
		Vector<Square> possibleMoves = new Vector<Square>();
		Piece.Color pColor = p.getColor();
		
		int row = p.getRow();
		int col = p.getCol();
		
		//Check moves to the top-left of this piece
		if(Board.inBounds(row-1, col-1)) {
			
			if(!this.getSquare(row-1, col-1).isOccupied())
				possibleMoves.add(this.getSquare(row-1, col-1));
		
			//if square is occupied, and the color of the Piece in square is
			//not equal to the piece whose moves we are checking, then
			//check to see if we can make the jump by checking
			//the next square in the same direction
			else
				if(Board.inBounds(row-2, col-2))
					
					if(!this.getSquare(row-2, col-2).isOccupied() &&
						(this.getSquare(row-1, col-1).getOccupant().getColor() != pColor))
						
						possibleMoves.add(this.getSquare(row-2, col-2));
			
		}
		
		//Check moves to the top-right of this piece
		if(Board.inBounds(row-1, col+1)) {
			
			if(!this.getSquare(row-1, col+1).isOccupied())
				possibleMoves.add(this.getSquare(row-1, col+1));
		
			else
				if(Board.inBounds(row-2, col+2))
					
					if(!this.getSquare(row-2, col+2).isOccupied() && 
						(this.getSquare(row-1, col+1).getOccupant().getColor() != pColor))
							
						possibleMoves.add(this.getSquare(row-2, col+2));
		}
		
		//check moves to the bottom-left of this piece
		if(Board.inBounds(row+1, col-1)) {
			
			if(!this.getSquare(row+1, col-1).isOccupied())
				possibleMoves.add(this.getSquare(row+1, col-1));
			
			
			
			else
				if(Board.inBounds(row+2, col-2))
					
					if(!this.getSquare(row+2, col-2).isOccupied() && 
						(this.getSquare(row+1, col-1).getOccupant().getColor() != pColor))
							
						possibleMoves.add(this.getSquare(row+2, col-2));
		}
		
		//check moves to the bottom-right of this piece
		if(Board.inBounds(row+1, col+1)) {
			
			if(!this.getSquare(row+1, col+1).isOccupied())
				possibleMoves.add(this.getSquare(row+1, col+1));
		
			else
				if(Board.inBounds(row+2, col+2))
					
					if(!this.getSquare(row+2, col+2).isOccupied() && 
						(this.getSquare(row+1, col+1).getOccupant().getColor() != pColor))
							
						possibleMoves.add(this.getSquare(row+2, col+2));
		}
		
		
		return possibleMoves;
		
	}
    
    
}
