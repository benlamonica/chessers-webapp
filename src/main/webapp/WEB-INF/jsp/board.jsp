<%@page import="us.pojo.chessers.util.PositionHelper"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<html>
<head>
	<title>Chessers</title>
	<link rel="stylesheet" type="text/css" href="style.css"/>
	<script type="text/javascript" src="board.js"></script>
</head>
<body onLoad="requestUpdate();">
<table width="700">
<tr><td>
<table class="board" width="400" height="400">
<%
	for(int i = 0; i < 8; i++) {
%>	<tr>
<%
		for(int j = 0; j < 8; j++) {
			boolean isGreen = ((i*8+j+i%2)%2==0);
			String position = PositionHelper.getPosition(i, j);
%>		<td width="50" height="50" id="<%=position%>" onClick="clicky(this)" class="<%=isGreen?"g":"w"%>"><img src="images/clear.gif" width="50" height="50"/></td>
<%
		}
%>	</tr>
<%
	}
%>
</table>
</td><td valign="top" width="100%">
<table style="text-align: center;" cellspacing="0" cellpadding="2" width="100%">
	<tr style="font-size: small;">
		<td class="u">Color</td>
		<td class="u">Name</td>
		<td class="u">Pieces</td>
	</tr>
	<tr class="white">
		<td style="border-left: 1px solid black;">White</td>
		<td id="whiteUser"><bean:write name="whiteUser"/></td>
		<td id="whiteRemaining"><bean:write name="whiteRemaining"/></td>
	</tr>
	<tr class="black">
		<td>Black</td>
		<td id="blackUser"><bean:write name="blackUser"/></td>
		<td id="blackRemaining" style="border-right: 1px solid black;"><bean:write name="blackRemaining"/></td>
	</tr>
	<tr>
		<td colspan="3" id="turnsTaken">Turns Taken: <bean:write name="turnsTaken"/></td>
	</tr>
	<tr><td colspan="3">&nbsp;</td></tr>
	<tr>
		<td colspan="3" id="currentUser"><bean:write name="currentUser"/></td>
	</tr>
	<tr><td colspan="3">&nbsp;</td></tr>
	<tr>
		<td colspan="3" id="checkWarning">&nbsp;</td>
	</tr>
	<tr><td colspan="3" id="gameOverMessage">&nbsp;</td></tr>
	<tr>
		<td colspan="3" id="skipTurn"><input type="submit" value="Skip Turn" onClick="skipTurn(); return false;"/></td>
	</tr>
</table>
</td></tr></table>
<a href="rules.html">Rules</a>
</body>
</html>