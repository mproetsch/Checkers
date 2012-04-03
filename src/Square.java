/** Represents a single square contained in Board
 * 
 * 
 * @author Matthew Proetsch
 * @version 0.9b
 */
public class Square {
    	
    /** The color that a square should be */
    enum BackgroundColor { DARKGRAY, LIGHTGRAY }

    
    
    /** The background color of this Square */
    private BackgroundColor squareColor;

    
    
    /** Whether or not this Square is occupied */
    private boolean occupied;

    
    
    /** The Piece that occupies this Square, may be NULL */
    private Piece occupant;

    
    /** Make a new Square with the specified background Board.Color
     *
     *  @param c        	The background color of this Square
     */
    public Square(BackgroundColor c) {
    	
        squareColor = c;
        occupied = false;
        occupant = null;
    }


    /** Return whether or not this Square is occupied */
    public boolean isOccupied() {
        return this.occupied;
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
}
