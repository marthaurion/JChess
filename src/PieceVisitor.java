import java.util.ArrayList;


public class PieceVisitor {
	
	//returns whether a move is legal
	public boolean visitPawn(Move m, Pawn p) {
		//first do basic checks
		if(!checkPiece(m, p)) return false;
		
		ArrayList<Square> legal = legalMoves(p);
		
		//false if there are no legal moves
		if(legal.size() < 1) return false;
		
		return true;
	}
	
	private ArrayList<Square> legalMoves(Pawn p) {
		ArrayList<Square> list = new ArrayList<Square>();
		//hard code the case where pawns are on starting location
		if(p.getColor() == PieceColor.Black &&
				p.getLocation().getY() == 6) {
			list.add(new Square(p.getLocation().getX(), p.getLocation().getY()-2));
		}
		if(p.getColor() == PieceColor.White &&
				p.getLocation().getY() == 1) {
			list.add(new Square(p.getLocation().getX(), p.getLocation().getY()+2));
		}
		
		return list;
	}
	
	//checks to make sure the source piece is the same as the piece calling visit
	private boolean checkPiece(Move m, Piece p) {
		Piece temp = m.getSource();
		//if the source is a null piece return false
		if(temp.getName().equals("None")) {
			return false;
		}
		//checks if pieces are the same type
		if(!temp.getName().equals(p.getName())) {
			return false;
		}
		//checks if the locations are the same
		if(!temp.getNotation().equals(p.getNotation())) {
			return false;
		}
		//checks if colors of the pieces are the same
		if(temp.getColor() != p.getColor()) {
			return false;
		}
		
		return true;
	}
}
