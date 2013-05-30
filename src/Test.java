
public class Test {
	public static void main(String[] args) {
		Model m = new Model();
		Display d = new Display(m);
		
		m.attach(d);
		
		d.initialize();
	}
}
