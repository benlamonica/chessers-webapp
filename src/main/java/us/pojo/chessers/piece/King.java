package us.pojo.chessers.piece;

import java.util.ArrayList;
import java.util.List;

import us.pojo.chessers.GameBoard;
import us.pojo.chessers.util.PositionHelper;

public class King extends AbstractGamePiece {

	private boolean isInCheck = false;
	
	public King() {
		this(GamePiece.WHITE);
	}
	
	public King(int color) {
		super(color, 1, new int[][] {
				{1,-1,0},
				{1,-1,1},
				{1,1,0},
				{1,1,-1},
				{1,0,-1},
				{1,1,1},
				{1,0,1},
				{1,-1,-1}
			});
	}
	
	public String getName() {
		return "King";
	}

	public String[] getAvailableMoves(String from, GameBoard game) {
		String[] availableMoves = null;
		
		isInCheck = game.isKingInCheck(color);
		
		if (isInCheck) {
			List moves = new ArrayList();
			for (int i = 0; i < paths.length; i++) {
				int x = PositionHelper.getColumn(from) + paths[i][1];
				int y = PositionHelper.getRow(from) + paths[i][2];
				boolean isAdjacent = true;
				
				while (x >= 0 && x < 8 && y >= 0 && y < 8) {
					String position = PositionHelper.getPosition(x,y);
					GamePiece piece = game.getPiece(position);
					
					// only allow empty spaces, or adjacent spaces with enemies
					if (piece != null && piece.getColor() != color && isAdjacent) {
						moves.add(position);
					} else if (piece == null) {
						moves.add(position);
						break;
					}
					
					x += paths[i][1];
					y += paths[i][2];
					isAdjacent = false;
				}
			}
			availableMoves = (String[]) moves.toArray(new String[0]);
		} else {
			availableMoves = super.getAvailableMoves(from, game);
		}
		
		return availableMoves;
	}

	public String[] getPath(String from, String to) {
		return new String[] {to};
	}
	
	public boolean isKingInCheck() {
		return isInCheck;
	}
}
