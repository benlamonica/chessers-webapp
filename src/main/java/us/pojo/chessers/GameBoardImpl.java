package us.pojo.chessers;

//TODO Does not show when somebody joins

import us.pojo.chessers.piece.Circle;
import us.pojo.chessers.piece.GamePiece;
import us.pojo.chessers.piece.King;
import us.pojo.chessers.piece.Square;
import us.pojo.chessers.piece.Triangle;
import us.pojo.chessers.util.ChessersException;
import us.pojo.chessers.util.PositionHelper;

public class GameBoardImpl implements GameBoard {

	private int gameId = -1;
	
	private int turnNumber = 0;
	
	private String[] player = new String[2];
	
	private String[] king = {"",""};
	
	private int owner = GamePiece.WHITE;

	private GamePiece[][] board = new GamePiece[8][8];
	
	private String[] lastMove = {"",""};
	
	private boolean[] isNewInformationAvailable = {true, true};
	
	private boolean[] kingInCheck = {false, false};
	
	public GameBoardImpl(int gameId, String username, int color) {
		this.gameId = gameId;
		owner = color;
		player[owner] = username;
		initBoard();
	}
	
	public int registerPlayer(String username) {
		if (player[GamePiece.WHITE] == null) {
			player[GamePiece.WHITE] = username;
			setNewInfo();
			return GamePiece.WHITE;
		} else if (player[GamePiece.BLACK] == null) {
			player[GamePiece.BLACK] = username;
			setNewInfo();
			return GamePiece.BLACK;
		} else {
			throw new ChessersException("Game already has 2 players.");
		}
	}
	
	private void initBoard() {
		placePiece(new King(GamePiece.BLACK), "A8");
		placePiece(new Circle(GamePiece.BLACK), "A7");
		placePiece(new Circle(GamePiece.BLACK), "B8");
		placePiece(new Triangle(GamePiece.BLACK), "A6");
		placePiece(new Triangle(GamePiece.BLACK), "B7");
		placePiece(new Triangle(GamePiece.BLACK), "C8");
		placePiece(new Square(GamePiece.BLACK), "A5");
		placePiece(new Square(GamePiece.BLACK), "B6");
		placePiece(new Square(GamePiece.BLACK), "C7");
		placePiece(new Square(GamePiece.BLACK), "D8");

		placePiece(new King(GamePiece.WHITE), "H1");
		placePiece(new Circle(GamePiece.WHITE), "G1");
		placePiece(new Circle(GamePiece.WHITE), "H2");
		placePiece(new Triangle(GamePiece.WHITE), "F1");
		placePiece(new Triangle(GamePiece.WHITE), "G2");
		placePiece(new Triangle(GamePiece.WHITE), "H3");
		placePiece(new Square(GamePiece.WHITE), "E1");
		placePiece(new Square(GamePiece.WHITE), "F2");
		placePiece(new Square(GamePiece.WHITE), "G3");
		placePiece(new Square(GamePiece.WHITE), "H4");
	}
	
	public String getOwnerName() {
		return player[owner];
	}
	
	public boolean isGameAvailable() {
		return (player[0] == null || player[1] == null);
	}
	
	public int getGameId() {
		return gameId;
	}
	
	public String getLoser() {
		if (isGameOver()) {
			return player[getCurrentPlayer()];
		} else {
			return null;
		}
	}

	private int getTotalPeicesInPlay(int color) {
		int peices = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] != null && board[i][j].getColor() == color) {
					peices++;
				}
			}
		}

		return peices;
	}
	
	public int getTotalBlackPiecesInPlay() {
		return getTotalPeicesInPlay(GamePiece.BLACK);
	}

	public int getTotalWhitePiecesInPlay() {
		return getTotalPeicesInPlay(GamePiece.WHITE);
	}

	public String getWinner() {
		if (isGameOver()) {
			return player[getCurrentPlayer()==0?1:0];
		} else {
			return null;
		}
	}

	public int getCurrentPlayer() {
		return (turnNumber % 2); 
	}
	
	public boolean isGameOver() {
		return (isGameOver(GamePiece.WHITE) || isGameOver(GamePiece.BLACK));
	}
	
	private boolean isGameOver(int color) {
		boolean isKingDead = (king[color] == null);
		boolean isKingCheckMated = (kingInCheck[color] && getPiece(king[color]).getAvailableMoves(king[color], this).length == 0);
		boolean isKingInWinningCorner = ((color==GamePiece.WHITE?"A8":"H1").equals(king[color]));
		return (isKingDead || isKingCheckMated || isKingInWinningCorner);
	}

	public boolean isMovePossible(String from, String to) {
		GamePiece fromPiece = getPiece(from);
		String[] path = fromPiece.getPath(from, to);

		// path is null if there is no way that the piece can get there
		if (path == null) {
			return false;
		}
		
		GamePiece toPiece = getPiece(to);
		// if we're attacking, you can jump pieces
		if (toPiece != null && toPiece.getColor() != fromPiece.getColor() && fromPiece.getCaptureDistance() == path.length) {
			return true;
		}
		
		if (fromPiece instanceof King) {
			if (((King)fromPiece).isKingInCheck()) {
				return true;
			}
		}
		
		// make sure that there are no pieces blocking the path
		for (int i = 0; i < path.length; i++) {
			if (getPiece(path[i]) != null) {
				return false;
			}
		}
		
		return true;
	}

	public boolean movePiece(String from, String to) {
		if (from.length() != 2 || to.length() != 2) {
			return false;
		}

		if (!isMovePossible(from, to)) {
			return false;
		}
		
		GamePiece piece = getPiece(from);
		GamePiece removedPiece = getPiece(to);

		// make sure that the piece moving is allowed to
		if (piece.getColor() != getCurrentPlayer()) {
			return false;
		}
		
		boolean wasKingInCheck = kingInCheck[piece.getColor()];

		removePiece(from);
		removePiece(to);
		placePiece(piece, to);
		
		kingInCheck[GamePiece.WHITE] = kingInCheck(GamePiece.WHITE);
		kingInCheck[GamePiece.BLACK] = kingInCheck(GamePiece.BLACK);

		if (wasKingInCheck && kingInCheck[piece.getColor()]) {
			removePiece(from);
			removePiece(to);
			placePiece(piece, from);
			placePiece(removedPiece, to);
			
			throw new ChessersException("You must move your king out of check.");
		}
		
		if (piece instanceof King) {
			if (kingInCheck[piece.getColor()]) {
				removePiece(to);
				placePiece(piece, from);
				placePiece(removedPiece, to);
				kingInCheck[GamePiece.BLACK] = kingInCheck(GamePiece.BLACK);
				kingInCheck[GamePiece.WHITE] = kingInCheck(GamePiece.WHITE);
				throw new ChessersException("You can not move into check.");
			} else {
				king[piece.getColor()] = to;
			}
		}
		
		if (removedPiece instanceof King) {
			king[piece.getColor()] = null;
		}
		
		turnNumber++;
		lastMove = new String[] {from, to};
		setNewInfo();
		return true;
	}

	public void skipTurn() {
		lastMove = new String[] {"",""};
		turnNumber++;
		setNewInfo();
	}
	
	private void setNewInfo() {
		isNewInformationAvailable[GamePiece.WHITE] = true;
		isNewInformationAvailable[GamePiece.BLACK] = true;
	}
	
	public int getTurnNumber() {
		return turnNumber;
	}
	
	public boolean isKingInCheck(int color) {
		return kingInCheck[color];
	}
	
	private void placePiece(GamePiece piece, String position) {
		int column = PositionHelper.getColumn(position);
		int row = PositionHelper.getRow(position);
		
		if (board[column][row] != null) {
			throw new ChessersException("Board location already has a peice!");
		}
		
		if (piece instanceof King) {
			king[piece.getColor()] = position;
		}

		board[column][row] = piece;
	}

	public GamePiece getPiece(String position) {
		int column = PositionHelper.getColumn(position);
		int row = PositionHelper.getRow(position);
		
		return board[column][row];
	}
	
	private void removePiece(String position) {
		int column = PositionHelper.getColumn(position);
		int row = PositionHelper.getRow(position);

		board[column][row] = null;
	}
	
	public String getUsername(int color) {
		if (player[color] == null) {
			return "Nobody Yet";
		} else {
			return player[color];
		}
	}
	
	private boolean kingInCheck(int color) {
		GamePiece leftCorner = getPiece("H1");
		GamePiece rightCorner = getPiece("A8");
		
		// the king is not in check if it is about to end the game
		if (color == GamePiece.WHITE && rightCorner != null 
				&& rightCorner instanceof King 
				&& rightCorner.getColor() == color) {
			return false;
		}
		
		if (color == GamePiece.BLACK && leftCorner != null 
				&& leftCorner instanceof King
				&& leftCorner.getColor() == color) {
			return false;
		}
		
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				GamePiece piece = board[i][j];
				String position = PositionHelper.getPosition(i, j);
				if (piece != null) {
					if (piece.getColor() != color) {
						String[] moves = piece.getAvailableMoves(position, this);
						for (int k = 0; k < moves.length; k++) {
							if (moves[k].equals(king[color])) {
								return true;
							}
						}
					}
				}
			}
		}
		
		return false;
	}
	
	public String[] getLastMove() {
		return lastMove;
	}
	
	public boolean isNewInformationAvailable(int color) {
		if (isNewInformationAvailable[color]) {
			isNewInformationAvailable[color] = false;
			return true;
		} else {
			return false;
		}
	}
}
