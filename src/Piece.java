/* Matthew Proetsch
 * COP3330 Section 0001
 * Piece.java: see Javadoc comments for details
 */
import java.awt.*;

/** A Piece on the game board that can be either red or black
 *  Pieces are taken into account when redrawing a Square on the game board
 *  and when a particular side runs out of pieces, the game is over
 * 
 * @author Matthew Proetsch
 * @version 0.9b
 */
public class Piece {
	
	
	/** The current row of the game board that this Piece resides on */
	private int row;
	
	/** The current column of the game board that this Piece resides on */
	private int col;
	
	/** The Color of this Piece */
	public Color color;
	
	
	/**Initialize a new Piece with the given color and at the given position
	 * 
	 * @param c			The color of the new Piece
	 * @param row		The row of the game board this piece lives on
	 * @param col		The column of the game board this piece lives on
	 */
	public Piece(Color c, int row, int col) {

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
	public Color getColor() {
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

	
	
	/** Get the String representation of this Piece
	 * 
	 * @return					The String representation of this Piece
	 */
	public String toString() {
		
		StringBuilder s = new StringBuilder();
		
		if(this.color == Color.BLACK)
			s.append("Black ");
		
		else
			s.append("Red ");
		
		s.append("piece at row " + Integer.toString(this.getRow()) + 
				 ", col " + Integer.toString(this.getCol()));
		
		return s.toString();
	}

}
