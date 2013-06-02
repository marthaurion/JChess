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
}
