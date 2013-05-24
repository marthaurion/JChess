import java.util.ArrayList;
import java.util.List;

public class Piece {
    private PieceType type;
    private PieceColor color;
    //piece shouldn't have to know its square
    
    //piece gets constructed based on notation character
    public Piece(PieceType t, PieceColor c) {
        type = t;
        color = c;
    }
    
    //get functions
    public int getValue() {
    	return type.getValue();
    }
    
    public String getName() {
    	return type.toString();
    }
    
    public char getID() {
    	return type.getAbbrev();
    }
    
    public PieceColor getColor() {
    	return color;
    }
    
    public List<Square> legalMoves(Square source) {
    	List<Square> list = new ArrayList<Square>();
    	
    	
    	return list;
    }
}