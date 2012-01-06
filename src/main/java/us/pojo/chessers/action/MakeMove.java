package us.pojo.chessers.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import us.pojo.chessers.GameBoard;
import us.pojo.chessers.piece.GamePiece;
import us.pojo.chessers.util.ChessersException;

public class MakeMove extends AbstractAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionForm dynaForm = (DynaActionForm)form;
		HttpSession session = request.getSession();
		Integer color = (Integer) session.getAttribute("color");
		String from = dynaForm.getString("from");
		String to = dynaForm.getString("to");
		String skipTurn = dynaForm.getString("skipTurn");
		GameBoard game = getGame(request);
		GamePiece piece = game.getPiece(from);
		
		request.setAttribute("color", "white");
		request.setAttribute("piece", "0");
		request.setAttribute("to", "");
		request.setAttribute("from", "");
		request.setAttribute("isSuccessful", "false");
		request.setAttribute("message","Please wait for the other player to finish.");
		
		if (!game.isGameOver()) {
			// only allow a move if it's the player's turn.
			if (game.getCurrentPlayer() == color.intValue()) {
				boolean isSuccessful = false;
				String message = "";
	
				if (skipTurn.equals("false")) {
					try {
						isSuccessful = game.movePiece(from, to);
					} catch (ChessersException e) {
						isSuccessful = false;
						message = e.getMessage();
					}
		
					if (isSuccessful) {
						request.setAttribute("to", to);
						request.setAttribute("from", from);
						request.setAttribute("color", String.valueOf(piece.getColor()==GamePiece.WHITE?"white":"black"));
						request.setAttribute("piece", String.valueOf(piece.getName().charAt(0)));
					}
				} else {
					game.skipTurn();
				}
			
				request.setAttribute("message", message);
				request.setAttribute("isSuccessful", new Boolean(isSuccessful));
			}
		} else {
			request.setAttribute("message","The game is over! Start a new game if you want to take another move.");
		}
		
		return mapping.findForward(SUCCESS_KEY);
	}

}
