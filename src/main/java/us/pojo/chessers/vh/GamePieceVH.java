package us.pojo.chessers.vh;

import us.pojo.chessers.piece.GamePiece;

public class GamePieceVH {

	private String color;

	private String position;

	private String name;

	public GamePieceVH(GamePiece piece, String position) {
		this.color = piece.getColor() == GamePiece.WHITE ? "white" : "black";
		this.position = position;
		this.name = piece.getName();
	}

	public String getColor() {
		return color;
	}

	public String getName() {
		return name;
	}

	public String getPosition() {
		return position;
	}
}
