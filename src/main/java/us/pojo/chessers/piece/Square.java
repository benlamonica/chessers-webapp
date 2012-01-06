package us.pojo.chessers.piece;

public class Square extends AbstractGamePiece {

	public Square() {
		this(GamePiece.WHITE);
	}
	
	public Square(int color) {
		super(color, 4, new int[][] {
				{1,-1,0},
				{2,-1,0},
				{3,-1,0},
				{4,-1,0},
				{1,0,-1},
				{2,0,-1},
				{3,0,-1},
				{4,0,-1},
				{1,1,0},
				{2,1,0},
				{3,1,0},
				{4,1,0},
				{1,0,1},
				{2,0,1},
				{3,0,1},
				{4,0,1}}
			);
	}

	public String getName() {
		return "Square";
	}

}
