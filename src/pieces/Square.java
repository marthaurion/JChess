package pieces;

/**
 * Abstraction for a location on the board.
 * @author marthaurion
 *
 */
public class Square {
    private char file; //columns marked a-h
    private int rank; //rows marked 1-8
    private int x;
    private int y;
    
    /**
     * Initialize the location.
     * @param x The x-coordinate for the location. 
     * @param y The y-coordinate for the location.
     */
    public Square(int x, int y) {
        this.x = x;
        this.y = y;
        //these are just for display
        rank = y+1;
        file = (char)('a'+x);
    }
    
    /**
     * Get the location's algebraic notation.
     * @return String with algebraic notation.
     */
    public String getNotation() {
    	String st = "";
    	st = st+file+rank;
    	return st;
    }
    
    /**
     * Get the file name for location (a-h).
     * @return Character with file name.
     */
    public char getFile() {
    	return file;
    }
    
    /**
     * Get the rank number for location (1-8).
     * @return Integer rank.
     */
    public int getRank() {
    	return rank;
    }
    
    /**
     * Get the x-coordinate of the location.
     * @return The x-coordinate.
     */
    public int getX() {
    	return x;
    }
    
    /**
     * Get the y-coordinate of the location.
     * @return The y-coordinate.
     */
    public int getY() {
    	return y;
    }
}
