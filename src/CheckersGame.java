/** Creates a new game of Checkers and provides the GUI with an interface to access to the game's logic
 * 
 * 
 * @author Matthew Proetsch
 * @version 0.9b
 */



public class CheckersGame {
	
	/** The board which will store our game's state */
	private Board board;
	
	/** Constructor takes no arguments and forms a new game */
	public CheckersGame() {
		
		board = new Board();
		
		//Have the Red side on top, Black side on bottom
		//Establish the Red side first
		boolean put = false;
		for(int row = 0; row < 3; row++) 
			for(int col = 0; col < 8; col++) {
				
				if(put) 
					board.getSquare(row, col).setOccupant(new Piece(Piece.Color.RED, row, col));
				
				else
					put = !put;
		
			}
		
		//Now establish the Black side
		put = true;
		for(int row = 5; row < 8; row++)
			for(int col = 0; col < 8; col++) {
				
				if(put)
					board.getSquare(row, col).setOccupant(new Piece(Piece.Color.BLACK, row, col));
				
				
				else
					put = !put;
			}
		
	}
	
	
	public boolean canMove(Square from, Square to) {
		
		
	}
	

}
