package pieces;
public class Square {
    private char file; //columns marked a-h
    private int rank; //rows marked 1-8
    private int x;
    private int y;
    
    //construct with array indices?
    public Square(int x, int y) {
        this.x = x;
        this.y = y;
        //these are just for display
        rank = y+1;
        file = (char)('a'+x);
    }
    
    //returns location in algebraic notation
    public String getNotation() {
    	String st = "";
    	st = st+file+rank;
    	return st;
    }
    
    //get functions
    public char getFile() {
    	return file;
    }
    
    public int getRank() {
    	return rank;
    }
    
    public int getX() {
    	return x;
    }
    
    public int getY() {
    	return y;
    }
}
