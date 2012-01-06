package us.pojo.chessers;

import java.util.HashMap;

import junit.framework.TestCase;

public class StaticTest extends TestCase {

	private static final HashMap map = new HashMap();
	
	static {
		map.put("1", new Integer(1));
		map.put("2", new Integer(2));
		map.put("3", new Integer(3));
	}
	
	public void testStaticInitializer() {
		Integer one = (Integer) map.get("1");
		assertEquals(1, one.intValue());
		map.put("1", new Integer(5));
		assertEquals(5, ((Integer) map.get("1")).intValue());
	}
}
