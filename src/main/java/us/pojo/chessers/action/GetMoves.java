package us.pojo.chessers.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import us.pojo.chessers.GameBoard;
import us.pojo.chessers.piece.GamePiece;

public class GetMoves extends AbstractAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		GameBoard game = getGame(request);
		String position = ((DynaActionForm)form).getString("position");
		GamePiece selectedPiece = game.getPiece(position);
		Integer color = (Integer)request.getSession().getAttribute("color");
		List moves = new ArrayList();
		
		if (!game.isGameOver()) {
			if (selectedPiece != null && selectedPiece.getColor() == color.intValue()) {
				String[] availableMoves = selectedPiece.getAvailableMoves(position, game);
	
				for (int i = 0; i < availableMoves.length; i++) {
					moves.add(availableMoves[i]);
				}
			}
		}
		
		request.setAttribute("moves", moves);
		
		return mapping.findForward(SUCCESS_KEY);
	}


}
