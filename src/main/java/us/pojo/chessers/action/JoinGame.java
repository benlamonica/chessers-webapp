package us.pojo.chessers.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import us.pojo.chessers.ChessersController;
import us.pojo.chessers.GameBoard;

public class JoinGame extends AbstractAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		DynaActionForm dynaForm = (DynaActionForm) form;
		String username = dynaForm.getString("username");
		Integer gameId = (Integer) dynaForm.get("gameId");
		GameBoard game = ChessersController.getGame(gameId);
		
		int color = game.registerPlayer(username);
		
		HttpSession session = request.getSession();
		session.setAttribute("gameId", gameId);
		session.setAttribute("color", new Integer(color));
		
		return mapping.findForward(SUCCESS_KEY);
	}
}
