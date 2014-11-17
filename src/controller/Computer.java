package controller;

import pieces.PieceColor;
import board.Board;
import board.Move;

public class Computer {
	private Board board;
	
	public Computer(Board b) {
		board = b;
	}
	
	
	public Move generateMove() {
		return board.getRandomMove(PieceColor.Black);
	}
}
