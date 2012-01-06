package us.pojo.chessers.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import us.pojo.chessers.GameBoard;
import us.pojo.chessers.piece.GamePiece;
import us.pojo.chessers.util.PositionHelper;
import us.pojo.chessers.vh.GamePieceVH;

public class RefreshGame extends AbstractAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		DynaActionForm dynaForm = (DynaActionForm) form;
		GameBoard game = getGame(request);
		String updateOnly = dynaForm.getString("updateOnly");
		List pieces = new ArrayList();
		int color = ((Integer) request.getSession().getAttribute("color")).intValue();

		request.setAttribute("newInformation", Boolean.TRUE);
		
		// only send the update if something has changed.
		if ("yes".equals(updateOnly)) {
			HttpSession session = request.getSession();
			Integer lastUpdateReceived = (Integer) session.getAttribute("lastUpdateReceived");
			
			if (lastUpdateReceived == null) {
				lastUpdateReceived = new Integer(-1);
			}
			
			session.setAttribute("lastUpdateReceived", new Integer(game.getTurnNumber()));
			if (!game.isNewInformationAvailable(color)) {
				request.setAttribute("newInformation", Boolean.FALSE);
				return mapping.findForward("update");
			}
		}
		
		request.setAttribute("whiteUser", "Nobody");
		request.setAttribute("blackUser", "Nobody");
		request.setAttribute("whiteRemaining", "10");
		request.setAttribute("blackRemaining", "10");
		request.setAttribute("blackInCheck", "false");
		request.setAttribute("whiteInCheck", "false");
		request.setAttribute("currentUser", "Nobody");
		request.setAttribute("turnsTaken", "0");
		request.setAttribute("message","");
		request.setAttribute("lastFrom","");
		request.setAttribute("lastTo","");
		
		if (game != null) {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					String position = PositionHelper.getPosition(i, j);
					GamePiece piece = game.getPiece(position);
					if (piece != null) {
						pieces.add(new GamePieceVH(piece, position));
					}
				}
			}
			
			request.setAttribute("whiteUser", game.getUsername(GamePiece.WHITE));
			request.setAttribute("blackUser", game.getUsername(GamePiece.BLACK));
			request.setAttribute("whiteRemaining", String.valueOf(game.getTotalWhitePiecesInPlay()));
			request.setAttribute("blackRemaining", String.valueOf(game.getTotalBlackPiecesInPlay()));
			request.setAttribute("blackInCheck", String.valueOf(game.isKingInCheck(GamePiece.BLACK)));
			request.setAttribute("whiteInCheck", String.valueOf(game.isKingInCheck(GamePiece.WHITE)));
			request.setAttribute("turnsTaken", String.valueOf(game.getTurnNumber()));
			request.setAttribute("lastFrom",game.getLastMove()[0]);
			request.setAttribute("lastTo",game.getLastMove()[1]);
			
			String currentUser = game.getUsername(game.getCurrentPlayer());
			request.setAttribute("currentUser", (currentUser==null?"Nobody":currentUser));
			
			if (game.isGameOver()) {
				String message = game.getWinner() + " wins!";
				request.setAttribute("message", message);
			}
		}
		
		request.setAttribute("pieces", pieces);
				
		if ("yes".equals(updateOnly)) {
			return mapping.findForward("update");
		} else {
			request.getSession().setAttribute("lastUpdateReceived", new Integer(-1));
			return mapping.findForward("redraw");
		}
	}

}
