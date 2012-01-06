package us.pojo.chessers.piece;

public class Circle extends AbstractGamePiece {

	public Circle() {
		this(GamePiece.WHITE);
	}
	
	public Circle(int color) {
		super(color, 2, new int[][] {
				{1,-1,0}, //left
				{2,-1,0},
				{1,-1,-1}, //left-up
				{2,-1,-1},
				{1,0,-1}, //up
				{2,0,-1},
				{1,1,-1}, //right-up
				{2,1,-1},
				{1,1,0}, //right
				{2,1,0},
				{1,1,1}, //right-down
				{2,1,1},
				{1,0,1}, //down
				{2,0,1},
				{1,-1,1}, //left-down
				{2,-1,1}
			});
	}
	
	public String getName() {
		return "Circle";
	}

}
