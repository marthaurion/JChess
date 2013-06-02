public interface Piece {
	public boolean acceptVisitor(PieceVisitor v, Move m);
	public Square getLocation();
	public void setLocation(Square s);
	public String getNotation();
    public int getValue();
    public String getName();
    public char getID();
    public PieceColor getColor();
}