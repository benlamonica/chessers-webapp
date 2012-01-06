package us.pojo.chessers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts.util.LabelValueBean;

public class ChessersController {
	private static Map games = new HashMap();

	private static int nextGameId = 0;
	
	public static int createGame(String username, int startingColor) {
		int nextId = getNextGameId();
		games.put(new Integer(nextId) , new GameBoardImpl(nextId, username, startingColor));
		return nextId;
	}
	
	public static Collection getAvailableGamesList() {
		List gameList = new ArrayList();
		
		Iterator iter = games.keySet().iterator();
		while (iter.hasNext()) {
			Integer gameId = (Integer) iter.next();
			GameBoard game = (GameBoard) games.get(gameId);
			if (game.isGameAvailable()) {
				gameList.add(new LabelValueBean(game.getOwnerName(), String.valueOf(game.getGameId())));
			}
		}
		
		return gameList;
	}
	
	public static GameBoard getGame(Integer gameId) {
		return (GameBoard) games.get(gameId);
	}
	
	public synchronized static int getNextGameId() {
		return nextGameId++;
	}
	
}
