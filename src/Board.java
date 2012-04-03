/** Stores the game board and relevant information, such as which pieces are on
 * which square
 *
 * @author Matthew Proetsch
 * @version 0.9b
 */
public class Board {

    /** Number of rows */
    private final int rows = 8;
    /** Number of columns */
    private final int cols = 8;
    /** An array of Squares that represents the game board */
    private Square[][] gameBoard;


    /** Constructor takes no args and produces a Board of size rows x cols with alternating background colors */
    public Board() {
    	gameBoard = new Square[rows][cols];
    	
    	//Set up the game board with alternating colors
    	boolean lastcolor = false;
    	for(int i = 0; i < rows; i++) 
    		for(int j = 0; j < cols; j++) {
    			
    			if(lastcolor)
    				gameBoard[i][j] = new Square(Square.BackgroundColor.DARKGRAY);
    			else
    				gameBoard[i][j] = new Square(Square.BackgroundColor.LIGHTGRAY);
    			
    			//Switch lastcolor
    			lastcolor = !lastcolor;
    		}
    	
    }
    
    /** Check to see if a call to this Board is in bounds
     * 
     * @param row			The row to be checked
     * @param col			The column to be checked
     * @return				True if in bounds, false if not
     */
    public boolean inBounds(int row, int col) {
    	if(row >= 0 && row < rows &&
    		col >= 0 && col < cols)
    		
    		return true;
    	
    	
    	return false;
    	
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
    
    
}
