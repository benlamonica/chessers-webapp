package us.pojo.chessers.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import us.pojo.chessers.ChessersController;
import us.pojo.chessers.GameBoard;

public abstract class AbstractAction extends Action {

	protected final String SUCCESS_KEY = "success";
	protected final String FAILURE_KEY = "failure";
	protected final String CANCEL_KEY = "cancel";
	
	public abstract ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception;
	
	protected void addMessage(HttpServletRequest request, String key) {
		if (key != null && key.trim().length() != 0) {
			ActionMessages messages = getMessages(request);
			if (messages == null) {
				messages = new ActionMessages();
				request.setAttribute(Globals.MESSAGE_KEY, messages);
			}
			
			messages.add(key, new ActionMessage(key));
			saveMessages(request, messages);
		}
	}
	
	protected void addError(HttpServletRequest request, String key) {
		if (key != null && key.trim().length() != 0) {
			ActionMessages errors = getErrors(request);
			if (errors == null) {
				errors = new ActionMessages();
				request.setAttribute(Globals.ERROR_KEY, errors);
			}
			
			errors.add(key, new ActionMessage(key));
			saveErrors(request, errors);
		}
	}
	
	protected GameBoard getGame(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Integer gameId = (Integer)session.getAttribute("gameId");
		return ChessersController.getGame(gameId);
	}
}
