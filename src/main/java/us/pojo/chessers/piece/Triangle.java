package us.pojo.chessers.piece;

public class Triangle extends AbstractGamePiece {

	public Triangle() {
		this(GamePiece.WHITE);
	}
	
	public Triangle(int color) {
		super(color, 3, new int[][] {
				{1,-1,-1},
				{2,-1,-1},
				{3,-1,-1},
				{1,1,-1},
				{2,1,-1},
				{3,1,-1},
				{1,1,1},
				{2,1,1},
				{3,1,1},
				{1,-1,1},
				{2,-1,1},
				{3,-1,1}}
			);
	}
	
	public String getName() {
		return "Triangle";
	}

}
