package us.pojo.chessers.piece;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import us.pojo.chessers.GameBoard;
import us.pojo.chessers.util.PositionHelper;

public abstract class AbstractGamePiece implements GamePiece {

	protected final int color;
	
	protected int[][] paths;
	
	protected Map threatBy = new HashMap();
	
	protected int captureDistance;
	
	protected AbstractGamePiece(int color, int captureDistance, int[][] paths) {
		this.color = color;
		this.paths = paths;
		this.captureDistance = captureDistance;
	}
	
	public abstract String getName();

	public String[] getAvailableMoves(String from, GameBoard game) {
		String[] availableMoves = getAvailableMoves(from, paths);
		GamePiece selectedPiece = game.getPiece(from);
		HashMap grid = new HashMap();
		List moves = new ArrayList();
		
		for (int i = 0; i < availableMoves.length; i++) {
			String[] steps = selectedPiece.getPath(from, availableMoves[i]);
			
			if (game.getTurnNumber() < 2 && steps.length > 2) {
				continue;
			}
			
			if (!isPathObstructed(steps, game)) {
				addMoves(steps, grid);
			}

			if (steps.length == selectedPiece.getCaptureDistance()) {
				String to = steps[selectedPiece.getCaptureDistance()-1];
				GamePiece target = game.getPiece(to);
				if (target != null && selectedPiece.getColor() != target.getColor()) {
					grid.put(to, to);
				}
			}
//			GamePiece target = game.getPiece(availableMoves[i]);
//			if (target != null) {
//				if(selectedPiece.getColor() == target.getColor()) {
//					continue;
//				} else {
//					isAttacking = true;
//				}
//			}
			
		}

		
		for (Iterator iter = grid.keySet().iterator(); iter.hasNext();) {
			moves.add((String) iter.next());
		}
		
		return (String[]) moves.toArray(new String[0]);
	}
	
	public String[] getPath(String from, String to) {
		return executePath(from, to, paths);
	}

	public int getColor() {
		return color;
	}
	
	protected String[] executePath(String from, String to, int[][] paths) {
		List path = new ArrayList();
		int startColumn = PositionHelper.getColumn(from);
		int startRow = PositionHelper.getRow(from);
		int stopColumn = PositionHelper.getColumn(to);
		int stopRow = PositionHelper.getRow(to);
		boolean foundPath = false;
		
		for (int i = 0; i < paths.length; i++) {
			int x = startColumn;
			int y = startRow;
			path.clear();
			for (int j = 0; j < paths[i][0]; j++) {
				x += paths[i][1];
				y += paths[i][2];
				path.add(PositionHelper.getPosition(x, y));
			}
			
			if (x == stopColumn && y == stopRow) {
				foundPath = true;
				break;
			}
		}
		
		if (foundPath) {
			return (String[]) path.toArray(new String[path.size()]);
		} else {
			return null;
		}
	}
	
	protected String[] getAvailableMoves(String from, int[][] paths) {
		List possibleMoves = new ArrayList();
		int column = PositionHelper.getColumn(from);
		int row = PositionHelper.getRow(from);
		
		for (int i = 0; i < paths.length; i++) {
			int x = column + (paths[i][0] * paths[i][1]);
			int y = row + (paths[i][0] * paths[i][2]);
			
			if (x >= 0 && x < 8 && y >= 0 && y < 8) {
				possibleMoves.add(PositionHelper.getPosition(x, y));
			}
		}

		return (String[]) possibleMoves.toArray(new String[possibleMoves.size()]);
	}
	
	private boolean isPathObstructed(String[] steps, GameBoard game) {
		boolean isPathObstructed = false;
		for (int j = 0; j < steps.length; j++) {
			if(game.getPiece(steps[j]) != null) {
				isPathObstructed = true;
				break;
			}
		}
	
		return isPathObstructed;
	}
	
	private void addMoves(String[] steps, Map grid) {
		for (int j = 0; j < steps.length; j++) {
			grid.put(steps[j], steps[j]);
		}
	}

	public int getCaptureDistance() {
		return captureDistance;
	}
}
