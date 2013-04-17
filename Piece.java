public class Piece {
    private String name;
    private char id;
    private int value;
    //piece shouldn't have to know its square
    
    //piece gets constructed based on notation character
    public Piece(char c) {
        id = c;
        if(c == 'p') {
            name = "Pawn";
            value = 1;
        }
        else if(c == 'n') {
            name = "Knight";
            value = 3;
        }
        else if(c == 'b') {
            name = "Bishop";
            value = 3;
        }
        else if(c == 'r') {
            name = "Rook";
            value = 5;
        }
        else if(c == 'q') {
            name = "Queen";
            value = 9;
        }
        else if(c == 'k') {
            name = "King";
            value = 0; //not sure how to work the infinity
        }
    }
    
    //get functions
    public int getValue() {
    	return value;
    }
    
    public String getName() {
    	return name;
    }
    
    public char getID() {
    	return id;
    }
    
    
    //move function
}