
public class GameServerGame {
	private GameServerThread p1;
	private GameServerThread p2;
	private GameServerThread current;
	
	public GameServerGame(GameServerThread t1, GameServerThread t2) {
		p1 = t1;
		p2 = t2;
		p1.addGame(this);
		p2.addGame(this);
		p1.start();
		p2.start();
		System.out.println("Player threads started");
		current = p1;
	}
	
	public synchronized boolean canMove(String s, GameServerThread t) {
		if(current == t) {
			current = t.getOpponent();
			current.oppMove(s);
			return true;
		}
		return false;
	}
}
