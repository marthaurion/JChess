
public class PlayerAction {
	private Move move;
	private String action;
	private PieceColor color;
	
	public PlayerAction(PieceColor c) {
		move = null;
		action = null;
		color = c;
	}
	
	public PieceColor getColor() {
		return color;
	}
	
	public void setMove(Move m) {
		move = m;
	}
	
	public Move getMove() {
		return move;
	}
	
	public String getAction() {
		return action;
	}
	
	public void setResign() {
		action = "Resign";
	}
	
	public String toString() {
		if(action == null) {
			return move.toString();
		}
		else return action+"|"+color.toString();
	}
	
	public static PlayerAction fromString(String st) {
		String[] toks = st.split("\\|");
		if(toks[0].equals("Resign")) {
			PlayerAction temp = new PlayerAction(PieceColor.fromString(toks[1]));
			temp.setResign();
			return temp;
		}
		else {
			PlayerAction temp = new PlayerAction(PieceColor.None);
			temp.setMove(Move.fromString(st));
			return temp;
		}
		
	}
}
