package us.pojo.chessers.util;

import junit.framework.TestCase;

public class PositionHelperTest extends TestCase {

	public void testShouldConvertCharacterAndReturnNumericColumnFromPosition() throws Exception {
		assertEquals("getColumn did not return the correct number", 0, PositionHelper.getColumn("A1"));
		assertEquals("getColumn did not return the correct number", 1, PositionHelper.getColumn("B2"));
		assertEquals("getColumn did not return the correct number", 2, PositionHelper.getColumn("C3"));
		assertEquals("getColumn did not return the correct number", 3, PositionHelper.getColumn("D4"));
		assertEquals("getColumn did not return the correct number", 4, PositionHelper.getColumn("E5"));
		assertEquals("getColumn did not return the correct number", 5, PositionHelper.getColumn("F6"));
		assertEquals("getColumn did not return the correct number", 6, PositionHelper.getColumn("G7"));
		assertEquals("getColumn did not return the correct number", 7, PositionHelper.getColumn("H8"));
	}
	
	public void testShouldConvertAsciiNumberToIntegerFromPosition() throws Exception {
		assertEquals("getRow did not return the correct number", 0, PositionHelper.getRow("A1"));
		assertEquals("getRow did not return the correct number", 1, PositionHelper.getRow("B2"));
		assertEquals("getRow did not return the correct number", 2, PositionHelper.getRow("C3"));
		assertEquals("getRow did not return the correct number", 3, PositionHelper.getRow("D4"));
		assertEquals("getRow did not return the correct number", 4, PositionHelper.getRow("E5"));
		assertEquals("getRow did not return the correct number", 5, PositionHelper.getRow("F6"));
		assertEquals("getRow did not return the correct number", 6, PositionHelper.getRow("G7"));
		assertEquals("getRow did not return the correct number", 7, PositionHelper.getRow("H8"));
	}
	
	public void testShouldReturnPositionFromColumnAndRowIntegers() throws Exception {
		assertEquals("did not return the correct position", "A1", PositionHelper.getPosition(0, 0));
		assertEquals("did not return the correct position", "B2", PositionHelper.getPosition(1, 1));
		assertEquals("did not return the correct position", "C3", PositionHelper.getPosition(2, 2));
		assertEquals("did not return the correct position", "D4", PositionHelper.getPosition(3, 3));
		assertEquals("did not return the correct position", "E5", PositionHelper.getPosition(4, 4));
		assertEquals("did not return the correct position", "F6", PositionHelper.getPosition(5, 5));
		assertEquals("did not return the correct position", "G7", PositionHelper.getPosition(6, 6));
		assertEquals("did not return the correct position", "H8", PositionHelper.getPosition(7, 7));
	}
}
