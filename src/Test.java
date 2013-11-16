
public class Test {
	public static void main(String[] args) {
		Board board = new Board();
		board.newGame();
		
		Display disp = new Display(board);
		disp.initialize();
	}
}
