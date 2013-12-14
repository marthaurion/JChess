package board;
import pieces.Piece;
import pieces.PieceColor;
import pieces.PieceFactory;



/**
 * Abstraction for passing around each chess Move.
 * There are multiple ways this can be used.
 * The toString/fromString methods are available for passing the moves online.
 * Also, can pass the moves between objects.
 * @author marthaurion
 *
 */
public class Move {
	//this is the object that will be sent in a multiplayer form
	private Piece source;
	private Piece dest;
	
	/**
	 * Create a new move from two input pieces.
	 * @param s Input source piece.
	 * @param d Input destination piece.
	 */
	public Move(Piece s, Piece d) {
		source = s;
		dest = d;
	}
	
	//get methods
	
	/**
	 * Get the source piece.
	 * @return Piece object for the source of the move.
	 */
	public Piece getSource() {
		return source;
	}
	
	/**
	 * Get the destination piece.
	 * @return Piece object for the destination of the move.
	 */
	public Piece getDest() {
		return dest;
	}
	
	/**
	 * Converts the move into algebraic notation.
	 * @return Algebraic notation for this Move object.
	 */
	public String notation() {
		StringBuilder st = new StringBuilder();
		st.append(source.getID());
		st.append(source.getLocation().getNotation());
		return st.toString();
	}
	
	
	/**
	 * Returns a string with metadata about the Move object.
	 * Useful for passing a message about the Move between computers.
	 */
	public String toString() {
		StringBuilder st = new StringBuilder();
		st.append(source.getName());
		st.append("|");
		st.append(Integer.toString(source.getLocation().getX()));
		st.append(",");
		st.append(Integer.toString(source.getLocation().getY()));
		st.append("|");
		st.append(source.getColor().toString());
		st.append("|");
		st.append(dest.getName());
		st.append("|");
		st.append(Integer.toString(dest.getLocation().getX()));
		st.append(",");
		st.append(Integer.toString(dest.getLocation().getY()));
		st.append("|");
		st.append(dest.getColor().toString());
		
		return st.toString();
	}
	
	/**
	 * Takes a string-ified move and converts it into a Move object.
	 * @param str String with the move metadata
	 * @param board The Board object that the Move is being created for.
	 * @return Move object for the input string.
	 */
	public static Move fromString(String str, Board board) {
		String[] toks = str.split("\\|"); //each element should be a square location
		String[] src = toks[1].split(","); //parse the source square
		int x = Integer.parseInt(src[0]);
		int y = Integer.parseInt(src[1]);
		
		Piece p1 = PieceFactory.createPiece(toks[0], x, y, PieceColor.fromString(toks[2]), board);
		
		String[] dest = toks[4].split(","); //parse the destination square
		x = Integer.parseInt(dest[0]);
		y = Integer.parseInt(dest[1]);
		
		Piece p2 = PieceFactory.createPiece(toks[3], x, y, PieceColor.fromString(toks[5]), board); 
		
		Move m = new Move(p1, p2);
		return m;
	}
}
