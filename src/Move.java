
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
	
	public static Move fromString(String s) {
		String[] toks = s.split("\\|");
		String[] src = toks[1].split(",");
		int x = Integer.parseInt(src[0]);
		int y = Integer.parseInt(src[1]);
		
		System.out.println(toks[0]+" "+x+" "+y+" "+toks[2]);
		Piece p1 = createPiece(toks[0], x, y, PieceColor.fromString(toks[2]));
		
		String[] d = toks[4].split(",");
		x = Integer.parseInt(d[0]);
		y = Integer.parseInt(d[1]);
		
		
		System.out.println(toks[3]+" "+x+" "+y+" "+toks[5]);
		Piece p2 = createPiece(toks[3], x, y, PieceColor.fromString(toks[5])); 
		
		
		return new Move(p1, p2);
	}
	
	private static Piece createPiece(String s, int x, int y, PieceColor c) {
		if(s.equals("None")) {
			return new NoPiece(x, y);
		}
		else if(s.equals("Pawn")) {
			return new Pawn(x, y, c);
		}
		else if(s.equals("Knight")) {
			return new Knight(x, y, c);
		}
		else if(s.equals("Bishop")) {
			return new Bishop(x, y, c);
		}
		else if(s.equals("Rook")) {
			return new Rook(x, y, c);
		}
		else if(s.equals("Queen")) {
			return new Queen(x, y, c);
		}
		else if(s.equals("King")) {
			return new King(x, y, c);
		}
		else return null;
	}
}
