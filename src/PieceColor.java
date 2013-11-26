public enum PieceColor {
	Black, White, None;
	
	public static PieceColor fromString(String s) {
		if(s.equals("None")) {
			return PieceColor.None;
		}
		else if(s.equals("White")) {
			return PieceColor.White;
		}
		else {
			return PieceColor.Black;
		}
	}
	
	public static PieceColor opposite(PieceColor c) {
		if(c == PieceColor.White) {
			return PieceColor.Black;
		}
		else if(c == PieceColor.Black) {
			return PieceColor.White;
		}
		else return PieceColor.None;
	}
}
