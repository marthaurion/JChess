
public class Pawn implements Piece {
	private PieceColor color;
	
	public Pawn(PieceColor c) {
		color = c;
	}
	
    public int getValue() {
    	return 1;
    }
    
    public String getName() {
    	return "Pawn";
    }
    
    //pawns have no character code in algebraic notation
    public char getID() {
    	return ' ';
    }
    
    public PieceColor getColor() {
    	return color;
    }
    
    public boolean acceptVisitor(PieceVisitor p, Move m) {
    	return p.visitPawn(m);
    }
}
