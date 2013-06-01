import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class GameServerThread extends Thread {
	private Socket p1;
	private Socket p2;
	
	public GameServerThread(Socket s1, Socket s2) {
		super("GameServerThread");
		p1 = s1;
		p2 = s2;
	}
	
	public void run() {
		try {
			PrintWriter out1 = new PrintWriter(p1.getOutputStream(), true);
			BufferedReader in1 = new BufferedReader(new InputStreamReader(p1.getInputStream()));
			PrintWriter out2 = new PrintWriter(p1.getOutputStream(), true);
			BufferedReader in2 = new BufferedReader(new InputStreamReader(p1.getInputStream()));
			
			String line = "";
			
			//just blindly sends through unless it gets a stopping case
			while(line != null && !line.contains("Resign")) {
				line = null;
				while(line == null) {
					line = in1.readLine();
				}
				System.out.println(line);
				out2.write(line);
				line = null;
				while(line == null) {
					line = in2.readLine();
				}
				
				if(line.contains("Resign")) {
					break;
				}
				System.out.println(line);
				out1.write(line);
				line = in1.readLine();
			}
			
			out1.close();
			out2.close();
			in1.close();
			in2.close();
			p1.close();
			p2.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
