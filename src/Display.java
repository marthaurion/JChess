import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.GridLayout;


public class Display implements Observer {
	private Controller control;
	private Model model;
	private JFrame frame;
	private JPanel panel;
	
	public Display(Model m) {
		control = null;
		model = m;
	}
	
	public void update(Model m) {
		model = m;
		control.update(m);
		
		frame.remove(panel);
		
		panel = displayBoard();
		frame.add(panel, 0);
		frame.revalidate();
		frame.repaint();
	}
	
	public void makeController() {
		control = new Controller(model);
		model.attach(control);
	}
	
	public void endGame(PieceColor c) {
		JOptionPane.showMessageDialog(frame, "Game Over. "+c.toString()+" wins!");
		frame.dispose();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		//create a controller for the view
		makeController();
		
		frame = new JFrame();
		frame.setBounds(100, 100, 794, 505);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new FlowLayout());
		
		JPanel player = new JPanel();
		ChessButton resign = new ChessButton("Resign");
		resign.addActionListener(control);
		player.add(resign);
		
		panel = displayBoard();
		frame.add(panel);
		frame.add(player);
		frame.pack();
		frame.setVisible(true);
		model.waitMove();
	}
	
	public JPanel displayBoard() {
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(8, 8));
		
		Board b = model.getData();
		
		for(int i = 7; i >= 0; i--) {
			for(int j = 0; j < 8; j++) {
				ChessButton grid = new ChessButton(j, i);
				
				Piece temp = b.getPiece(j, i);
				String st = "";
				if(temp.getName().equals("None")) st = " ";
				else if(temp.getName().equals("Pawn")) st += temp.getColor().toString().substring(0,1)+"P";
				else st += temp.getColor().toString().substring(0,1)+temp.getID();
				
				grid.setText(st);
				grid.addActionListener(control);
				p.add(grid);
			}
		}
		
		return p;
	}
}
