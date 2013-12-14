package pieces;
import board.Board;

/**
 * A class I created for creating Piece objects from certain parameters.
 * This class may be useless and I need to re-evaluate it later.
 * @author marthaurion
 *
 */
public class PieceFactory {

	/**
	 * Creates a piece based on a string saying what kind of piece.
	 * Only supports None, Pawn, Knight, Bishop, Rook, Queen, and King.
	 * @param s Input String indicating piece type.
	 * @param x The x-coordinate of the new Piece.
	 * @param y The y-coordinate of the new Piece.
	 * @param color Input PieceColor of the new Piece.
	 * @param board Input Board object to hold the new Piece.
	 * @return Piece object for new piece or null if invalid string.
	 */
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
	
    /**
     * Copies a piece.
     * @param p Input Piece to be copied.
     * @param b Board object for the new Piece (doesn't have to be the same as the input Piece).
     * @return Copy of input Piece. This should be a new object.
     */
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
