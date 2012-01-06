package us.pojo.chessers;

import us.pojo.chessers.piece.GamePiece;

public interface GameBoard {
	int getGameId();
	boolean isMovePossible(String from, String to);
	boolean movePiece(String from, String to);
	GamePiece getPiece(String position);
	boolean isGameOver();
	String getWinner();
	String getLoser();
	String getUsername(int color);
	int getTotalWhitePiecesInPlay();
	int getTotalBlackPiecesInPlay();
	boolean isGameAvailable();
	String getOwnerName();
	int getCurrentPlayer();
	int getTurnNumber();
	int registerPlayer(String username);
	boolean isKingInCheck(int color);
	void skipTurn();
	String[] getLastMove();
	boolean isNewInformationAvailable(int color);
}
