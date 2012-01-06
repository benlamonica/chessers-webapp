<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%response.setContentType("text/xml");%>
<chessers>
	<moves>
		<logic:iterate name="moves" id="move"><move position="<bean:write name="move"/>"/></logic:iterate>
	</moves>
</chessers>