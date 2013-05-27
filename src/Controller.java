import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Controller implements ActionListener, Observer {
	private Model model;
	private int sourceX;
	private int sourceY;
	
	public Controller(Model m) {
		sourceX = -1;
		sourceY = -1;
		model = m;
	}
	
	public void update(Model m) {
		model = m;
	}
	
	public void endGame(PieceColor c) {
		return;
	}

	public void actionPerformed(ActionEvent e) {
		ChessButton source = (ChessButton)e.getSource();
		if(source.getText().equals("Resign")) {
			//end the game
		}
		else {
			//if there is no source, set this to be the source
			if(sourceX == -1 && sourceY == -1) {
				sourceX = source.getMyX();
				sourceY = source.getMyY();
			}
			//otherwise create the move and send to the model
			else {
				int x = source.getMyX();
				int y = source.getMyY();
				
				//if the same button is clicked, cancel the selection
				if(sourceX == x && sourceY == y) {
					sourceX = -1;
					sourceY = -1;
				}
				else {
					Piece src = model.getData().getPiece(sourceX, sourceY);
					Piece dest = model.getData().getPiece(x, y);
					Move m = new Move(src, dest);
					boolean flag = model.tryMove(m);
					//reset now that the move has been registered
					sourceX = -1;
					sourceY = -1;
					
					//make the move if legal
					if(flag) {
						
						//win condition
						if(dest.getName().equals("King")) {
							//end game
							model.endGame(src.getColor());
						}
						
						else model.makeMove(m);
					}
				}
			}
		}
	}
}
