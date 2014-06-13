import java.io.IOException;

import controller.Player;

public class ChessDriver {
	public static void main(String[] args) throws IOException {
		@SuppressWarnings("unused")
		//Player play = new Player(true); //use this line for online game testing
		Player play = new Player(false); //use this line for local game esting
	}
}
