<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %><%response.setContentType("text/xml");%>
<chessers>
	<move isSuccessful="<bean:write name="isSuccessful"/>"
		from="<bean:write name="from"/>"
		to="<bean:write name="to"/>"
		piece="<bean:write name="piece"/>"
		color="<bean:write name="color"/>"
		message="<bean:write name="message"/>"/>
</chessers>