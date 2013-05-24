
public class Move {
	//this is the object that will be sent in a multiplayer form
	private Square source;
	private Square dest;
	
	public Move(Square src, Square d) {
		source = src;
		dest = d;
	}
	
	//get methods
	public Square getSource() {
		return source;
	}
	
	public Square getDest() {
		return dest;
	}
	
	public Piece movedPiece() {
		return source.getPiece();
	}
	
	//returns null if square is empty
	public Piece capPiece() {
		if(dest.hasPiece()) return dest.getPiece();
		else return null;
	}
	
	public String notation() {
		String st = "";
		st += source.getPiece().getID();
		st += source.getLocation();
		return st;
	}
}
