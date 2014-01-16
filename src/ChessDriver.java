import board.Board;
import view.BasicDisplay;



public class ChessDriver {
	public static void main(String[] args) {
		BasicDisplay disp = new BasicDisplay(new Board());
		disp.initialize();
	}
}
