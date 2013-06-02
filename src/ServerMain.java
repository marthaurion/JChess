import java.io.IOException;


public class ServerMain {
	public static void main(String[] args) throws IOException {
		System.out.println("Server Starting");
		GameServerBroker broker = new GameServerBroker("68.51.78.38");
		broker.listen();
	}
}
