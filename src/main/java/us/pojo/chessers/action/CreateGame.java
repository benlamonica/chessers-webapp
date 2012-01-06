package us.pojo.chessers.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import us.pojo.chessers.ChessersController;

public class CreateGame extends AbstractAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		DynaActionForm dynaForm = (DynaActionForm) form;
		int color = ((Integer) dynaForm.get("color")).intValue();
		String username = dynaForm.getString("username");
		int gameId = ChessersController.createGame(username, color);
		
		HttpSession session = request.getSession();
		session.setAttribute("gameId", new Integer(gameId));
		session.setAttribute("color", new Integer(color));
		
		return mapping.findForward(SUCCESS_KEY);
	}

}
