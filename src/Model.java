import java.io.IOException;
import java.util.ArrayList;


public class Model {
	private Board board;
	private ArrayList<Observer> obs;
	private PieceVisitor visitor;
	
	public Model(PieceColor c) throws IOException {
		board = new Board(c);
		board.newGame();
		obs = new ArrayList<Observer>();
		visitor = new PieceVisitor(this);
	}
	
	public void attach(Observer o) {
		obs.add(o);
	}
	
	public Board getData() {
		return board;
	}
	
	public void notifyObs() {
		//update every observer
		for(int i = 0; i < obs.size(); i++) {
			Observer temp = obs.get(i);
			temp.update(this);
			obs.set(i, temp);
		}
		
		//update the visitor
		visitor.update(this);
	}
	
	public boolean tryMove(Move m) {
		return board.tryMove(m, visitor);
	}
	
	public void makeMove(Move m) throws IOException {
		board.makeMove(m);
		notifyObs();
	}
	
	public void endGame(PieceColor c) {
		System.out.println("Game Over");
		for(int i = 0; i < obs.size(); i++) {
			obs.get(i).endGame(c);
		}
	}
}
