<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%response.setContentType("text/xml");%>
<chessers>
	<newInformation value="<bean:write name="newInformation"/>"/>
	<logic:equal name="newInformation" value="true">
	<players>
		<player color="white" name="<bean:write name="whiteUser"/>" pieceCount="<bean:write name="whiteRemaining"/>" isInCheck="<bean:write name="whiteInCheck"/>"/>
		<player color="black" name="<bean:write name="blackUser"/>" pieceCount="<bean:write name="blackRemaining"/>" isInCheck="<bean:write name="blackInCheck"/>"/>
	</players>
	<turn turnsTaken="<bean:write name="turnsTaken"/>" currentTurn="<bean:write name="currentUser"/>" message="<bean:write name="message"/>" lastFrom="<bean:write name="lastFrom"/>" lastTo="<bean:write name="lastTo"/>"/>
	<pieces>
		<logic:iterate name="pieces" id="piece"><piece name="<bean:write name="piece" property="name"/>" color="<bean:write name="piece" property="color"/>" position="<bean:write name="piece" property="position"/>"/>
		</logic:iterate>
	</pieces>
	</logic:equal>
</chessers>