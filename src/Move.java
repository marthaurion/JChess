
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
		st.append(Integer.toString(source.getLocation().getX()));
		st.append(",");
		st.append(Integer.toString(source.getLocation().getY()));
		st.append("|");
		st.append(Integer.toString(dest.getLocation().getX()));
		st.append(",");
		st.append(Integer.toString(dest.getLocation().getY()));
		
		return st.toString();
	}
}
