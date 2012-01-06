package us.pojo.chessers.util;

public class PositionHelper {

	public static int getColumn(String position) {
		return position.toUpperCase().charAt(0) - 'A';
	}
	
	public static int getRow(String position) {
		return Integer.parseInt(position.substring(1)) - 1;
	}
	
	public static String getPosition(int column, int row) {
		String position = String.valueOf(((char)(column + 'A')));
		position += (row + 1);
		return position;
	}
	
}
