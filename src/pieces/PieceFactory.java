package pieces;
import board.Board;




public class PieceFactory {

	//creates a pieces based on a string saying what kind of piece
	public static Piece createPiece(String s, int x, int y, PieceColor color, Board board) {
		if(s.equals("None")) {
			return new NoPiece(x, y);
		}
		else if(s.equals("Pawn")) {
			return new Pawn(x, y, color, board);
		}
		else if(s.equals("Knight")) {
			return new Knight(x, y, color, board);
		}
		else if(s.equals("Bishop")) {
			return new Bishop(x, y, color, board);
		}
		else if(s.equals("Rook")) {
			return new Rook(x, y, color, board);
		}
		else if(s.equals("Queen")) {
			return new Queen(x, y, color, board);
		}
		else if(s.equals("King")) {
			return new King(x, y, color, board);
		}
		else return null;
	}
	
    //returns a new piece with the same attributes as the input piece
    public static Piece createPiece(Piece p, Board b) {
    	int x = p.getLocation().getX();
    	int y = p.getLocation().getY();
    	PieceColor c = p.getColor();
    	
    	if(p.getName().equals("None")) return new NoPiece(x, y);
    	else if(p.getName().equals("Pawn")) return new Pawn(x, y, c, b);
    	else if(p.getName().equals("Knight")) return new Knight(x, y, c, b);
    	else if(p.getName().equals("Bishop")) return new Bishop(x, y, c, b);
    	else if(p.getName().equals("King")) return new King(x, y, c, b);
    	else if(p.getName().equals("Queen")) return new Queen(x, y, c, b);
    	else if(p.getName().equals("Rook")) return new Rook(x, y, c, b);
    	
    	return null;
    }
	
}
