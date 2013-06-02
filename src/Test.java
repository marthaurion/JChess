import java.io.IOException;


public class Test {
	public static void main(String[] args) throws IOException {
		Model m = new Model(PieceColor.White);
		Display d = new Display(m);
		
		m.attach(d);
		
		d.initialize();
	}
}
