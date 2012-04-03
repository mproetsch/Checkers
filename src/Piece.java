/** A Piece on the game board that can be either red or black
 * 
 * 
 * @author patriotpie
 * @version 0.9b
 */

public class Piece {

	
	/** The current row of the game board that this Piece resides on */
	private int row;
	
	/** The current column of the game board that this Piece resides on */
	private int col;
	
	/** Possible Piece colors */
	enum Color { RED, BLACK }
	
	/** The Color of this Piece */
	private Piece.Color color;
	
	/**Initialize a new Piece with the give color
	 * 
	 * @param c			The color of the new Piece
	 * @param row		The row of the game board this piece lives on
	 * @param col		The column of the game board this piece lives on
	 */
	public Piece(Piece.Color c, int row, int col) {

		color = c;
		this.row = row;
		this.col = col;
		
	}
	
	/** Get the row of the game board that this Piece resides on
	 * 
	 * @return		The row of the game board this Piece resides on
	 */
	public int getRow() {
		return row;
	}
	
	/** Get the color of this piece
	 * 
	 * @return 				The color of this piece
	 */
	public Piece.Color getColor() {
		return color;
	}
	
	/** Get the column of the game board that this Piece resides on
	 * 
	 * @return		The column of the game board that this Piece resides on
	 */
	public int getCol() {
		return col;
	}
	
	/** Give this Piece a new location to live on in the game board
	 * 
	 * @param row		The new row for this piece to live on
	 * @param col		The new column for this piece to live on
	 */
	public void setLoc(int row, int col) {
		this.row = row;
		this.col = col;
	}

}
