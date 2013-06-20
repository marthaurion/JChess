import java.io.IOException;


public class Test {
	public static void main(String[] args) throws IOException {
		Board board = new Board(PieceColor.White);
		board.newGame();
	}
}
