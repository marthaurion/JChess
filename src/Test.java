
public class Test {
	public static void main(String[] args) {
		Model m = new Model();
		Controller c = new Controller(m);
		Display d = new Display(m, c);
		
		m.attach(c);
		m.attach(d);
		
		d.initialize();
	}
}
