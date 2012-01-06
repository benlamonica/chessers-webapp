package us.pojo.chessers.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import us.pojo.chessers.ChessersController;

public class Chessers extends AbstractAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Collection gameList = ChessersController.getAvailableGamesList();
		gameList.add(new LabelValueBean("No Game Selected","-1"));
		request.setAttribute("games", gameList);
		return mapping.findForward(SUCCESS_KEY);
	}

}
