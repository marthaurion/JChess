package board;
import pieces.Piece;
import pieces.PieceColor;
import pieces.PieceFactory;




public class Move {
	//this is the object that will be sent in a multiplayer form
	private Piece source;
	private Piece dest;
	
	public Move(Piece s, Piece d) {
		source = s;
		dest = d;
	}
	
	//get methods
	public Piece getSource() {
		return source;
	}
	
	public Piece getDest() {
		return dest;
	}
	
	public String notation() {
		StringBuilder st = new StringBuilder();
		st.append(source.getID());
		st.append(source.getLocation().getNotation());
		return st.toString();
	}
	
	//for passing the message as a string
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
	
	//takes a string-ified move and converts it into a Move object
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
