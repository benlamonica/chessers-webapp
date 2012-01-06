package us.pojo.chessers.piece;

import junit.framework.TestCase;
import us.pojo.chessers.GameBoard;
import us.pojo.chessers.GameBoardImpl;

public class KingTest extends TestCase {

	King target = new King();
	GameBoard game = null;
	
	protected void setUp() {
		game = new GameBoardImpl(1, "Username", GamePiece.WHITE);
	}

// re-write with jMock later
//	public void testShouldReturnOnlyMovesThatAreOnTheBoard() throws Exception {
//		game.placePiece(target, "A8");
//		String moves[] = target.getAvailableMoves("A8", game);
//		assertEquals(2, moves.length);
//		assertEquals("B8", moves[0]);
//		assertEquals("A7", moves[1]);
//	}
//	
//	public void testShouldReturnAllMovesIfPieceIsNotNearBoarder() throws Exception {
//		game.placePiece(target, "C4");
//		String moves[] = target.getAvailableMoves("C4", game);
//		assertEquals(4, moves.length);
//		assertEquals("B4", moves[0]);
//		assertEquals("D4", moves[1]);
//		assertEquals("C3", moves[2]);
//		assertEquals("C5", moves[3]);
//	}
	
//	public void testShouldReturnNullIfKingCanNotGetToDesiredPosition() throws Exception {
//		assertNull("path should be null, can't get there!", target.getPath("A3", "A1"));
//	}
	
	public void testShouldReturnThePathThePieceWillTakeIfItCanGetToDesiredPosition() throws Exception {
		String[] path = target.getPath("A3", "A2");
		assertNotNull("path can not be null", path);
		assertEquals("path is longer than expected", 1, path.length);
		assertEquals("path terminates in unexpected place", "A2", path[0]);
	}
}
