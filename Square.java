public class Square {
    private char file; //columns marked a-h
    private int rank; //rows marked 1-8
    private Piece piece; //null if empty
    
    //construct with array indices?
    public Square(Piece p, int x, int y) {
        piece = p;
        rank = x;
        file = (char)('a'+y);
    }
    
    public String getLocation() {
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
    
    public Piece getPiece() {
    	return piece;
    }
    
    //deletes piece on square
    public void removePiece() {
    	piece = null;
    }
    
    //puts new piece on square
    public void addPiece(Piece p) {
    	piece = p;
    }
    
    //checks if square is occupied
    public boolean hasPiece() {
    	return (piece == null);
    }
}
