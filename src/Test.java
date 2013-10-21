import java.util.Scanner;

public class Test {
	public static void main(String[] args) {
		Board board = new Board();
		board.newGame();
		
		Scanner scan = new Scanner(System.in);
		int sx, sy, dx, dy;
		Move move;
		
		while(board.gameState() == 2) {
			System.out.println("Current turn: "+board.getTurn());
			board.printBoard();
			System.out.println();
			sx = Integer.parseInt(scan.nextLine());
			sy = Integer.parseInt(scan.nextLine());
			dx = Integer.parseInt(scan.nextLine());
			dy = Integer.parseInt(scan.nextLine());
			
			move = new Move(board.getPiece(sx, sy), board.getPiece(dx, dy));
			if(!board.tryMove(move)) {
				System.out.println("Invalid move.");
				continue;
			}
			else board.makeMove(move);
			
			System.out.println();
		}
		
		//check game state to find out who won
		int state = board.gameState();
		if(state == -1) System.out.println("Black wins!");
		else if(state == 0) System.out.println("It's a tie!");
		else if(state == 1) System.out.println("White wins!");
		
		
		scan.close();
	}
}
