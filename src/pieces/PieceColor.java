package pieces;

/**
 * Enumeration for piece colors. Possible obtions are white, black, and none.
 * @author marthaurion
 *
 */
public enum PieceColor {
	Black, White, None;
	
	/**
	 * Creates a PieceColor from a string.
	 * @param s Input string for PieceColor.
	 * @return Appropriate PieceColor.
	 */
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
	
	/**
	 * Returns the opposite color for PieceColor.
	 * @param c Input PieceColor to find opposite.
	 * @return Opposite PieceColor of input.
	 */
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
