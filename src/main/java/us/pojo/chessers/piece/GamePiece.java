package us.pojo.chessers.piece;

import us.pojo.chessers.GameBoard;

public interface GamePiece {
	public final int WHITE = 0;
	public final int BLACK = 1;
	String getName();
	String[] getAvailableMoves(String from, GameBoard game);
	int getColor();
	String[] getPath(String from, String to);
	int getCaptureDistance();
}
