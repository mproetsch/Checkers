import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.*;

/** Represents a single square contained in Board
 * 
 * 
 * @author Matthew Proetsch
 * @version 0.9b
 */
@SuppressWarnings("serial")
public class Square extends Canvas {
    	
    /** The color that a square should be */
    enum BackgroundColor { DARKGRAY, LIGHTGRAY }

    
    /** The background color of this Square */
	private BackgroundColor bgColor;
    
    /** Whether or not this Square is selected */
    private boolean selected = false;
    
    /** Whether or not this Square is occupied */
    private boolean occupied;
    
    
    /** The Piece that occupies this Square, may be NULL */
    private Piece occupant;

    
    
    /** The row of the game board that this square represents */
    private int row;
    
    /** The column of the game board that this square represents */
    private int col;

    
    /** Make a new Square with the specified background Board.Color
     *
     *  @param c        	The background color of this Square
     */
    public Square(BackgroundColor c, int myrow, int mycol) {
    	
    	this.setSize(64, 64);
    	
    	if(c == BackgroundColor.DARKGRAY)
    		this.setBackground(Color.DARK_GRAY);
    	else
    		this.setBackground(Color.LIGHT_GRAY);
    	
        bgColor = c;
        occupied = false;
        occupant = null;
        
        this.row = myrow;
        this.col = mycol;
        
    }

    
    

    /** Return whether or not this Square is occupied
     * 
     * @return 					Whether or not this Square is selected
     */
    public boolean isOccupied() {
        return this.occupied;
    }
    
    /** Return whether or not this Square is selected
     * 
     * @return					Whether or not this Square is selected
     */
    public boolean isSelected() {
    	return this.selected;
    }
    
    /** Get the row of the game board that this square represents
     * 
     * @return 			The row on the game board represented by this Square
     */
    public int getRow() {
    	return this.row;
    }
    
    /** Get the column of the game board that this square represents
     * 
     * @return 			The column on the game board represented by this Square
     */
    public int getCol() {
    	return this.col;
    }
    
    /** Get the background color of this Square */
    public Square.BackgroundColor getBackgroundColor() {
    	return this.bgColor;
    }
    
    /** Get the piece that occupies this Square
     * 
     * @return				The piece that occupies this Square, if any
     */
    public Piece getOccupant() {
    	if(this.isOccupied())
    		return this.occupant;
    	
    	return null;
    }
    
    
    
    
    
    
    /** Set the selection status of this Square
     * 
     * @param s 					Whether or not this Square is selected
     */
    public void setSelected(boolean s) {
    	
    	this.selected = s;
    }
    
    /** Set whether or not this Square is highlighted
     * 
     * @param doHighlight 			Whether or not this square should be highlighted
     */
    public void setHighlight(boolean doHighlight) {
    	
    	Graphics g = this.getGraphics();
    	
		this.paint(g);
    	if(doHighlight) {
    		//Draw a yellow rect around the border of this Square and
    		//and highlight where a piece may belong
    		
	    	g.setColor(Color.YELLOW);
	    	g.drawRect(0, 0, 63, 63);
	    	
	    	g.drawOval(5, 5, 45, 45);
    	}
    	
    }

    /** Set this piece to become occupied or free
     *
     * @param occ       	True if piece should be occupied, false otherwise
     */
    private void setOccupied(boolean occ) {
        this.occupied = occ;
    }

    /** Set the occupant of this Square
     *
     * @param visitor       The Piece that should now reside here
     */
    public void setOccupant(Piece visitor) {
    	if(visitor != null) {
    		
    		this.occupant = visitor;
    		this.setOccupied(true);
    		
    	}
    	
    	else {
    		
    		this.occupant = null;
    		this.setOccupied(false);
    		
    	}
    }
    
    
    
    
    
    @Override
	public void paint(Graphics g) {
		
		//Set the Canvas' background color equal to the Square's bgcolor
		if(this.getBackgroundColor() == Square.BackgroundColor.DARKGRAY) 
			this.setBackground(Color.DARK_GRAY);
		else
			this.setBackground(Color.LIGHT_GRAY);
		
		//Either draw a square or clear the rectangle
		if(this.isOccupied()) {
			
			Piece.Color pieceColor = occupant.getColor();
			
			if(pieceColor == Piece.Color.RED)
				g.setColor(Color.RED);
			else
				g.setColor(Color.BLACK);
			
			g.fillOval(10, 10, 44, 44);
		}
		
		else
			g.clearRect(0, 0, 64, 64);
			
	}
    

    
}
