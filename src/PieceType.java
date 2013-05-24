public enum PieceType {
	Pawn(1), Knight(3), Bishop(3), Rook(5), Queen(9), King(0);
	
	private int value;
	
	private PieceType(int v) {
		value = v;
	}
	
	//for the piece's material value
	public int getValue() {
		return value;
	}
	
	//for the piece's algebraic notation abbreviation
	public char getAbbrev() {
		//knight is the only piece where the abbreviation isn't the first letter 
		if(value == 3) return 'N';
		else return toString().charAt(0);
	}
}