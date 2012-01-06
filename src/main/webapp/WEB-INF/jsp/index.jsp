<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<style>
	#a {
		background: #005500;
	}
	#b {
		background: #009900;
		color: white;
		font-family: Arial, Helvetica, Sans-serif;
		font-size: 18pt;
		font-weight: bold;
	}
</style>
<div align="center">
<span id="b"><span id="a">&nbsp;C&nbsp;</span>&nbsp;H&nbsp;<span id="a">&nbsp;E&nbsp;</span>&nbsp;S&nbsp;<span id="a">&nbsp;S&nbsp;</span>&nbsp;E&nbsp;<span id="a">&nbsp;R&nbsp;</span>&nbsp;S&nbsp;</span><br />
<i>... the exciting NEW game of skill</i><br />
<i><span style="color: #0600A7;">more simple than chess</span> / <span style="color: #005500;">more stimulating than checkers</span> / more fun than anything</i><br />
<i style="font-size: 10pt;">for Laura Beth</i>
<br />
<br /> 
<html:form action="/CreateGame" method="post">
<table style="background: #DDFFDD; border: 1px solid green;">
	<tr><td>Your Name:</td>
		<td><html:text property="username"/></td>
	</tr>
	<tr><td>Piece Color:</td>
		<td><html:select property="color">
			<html:option value="1">Black</html:option>
			<html:option value="0">White</html:option>
			</html:select></td>
	</tr>
	<tr>
		<td colspan="2" align="center"><html:submit value="Create Game"/></td>
	</tr>
</table>
</html:form>
<b>~ or ~</b><br /><br />
<html:form action="/JoinGame" method="post">
<table style="background: #DDDDDD; border: 1px solid gray;">
	<tr><td>Your Name:</td>
		<td><html:text property="username"/></td>
	</tr>
	<tr><td>Play Against:</td>
		<td><html:select property="gameId">
			<html:optionsCollection name="games"/>
			</html:select></td>
	</tr>
	<tr>
		<td colspan="2" align="center"><html:submit value="Join Game"/></td>
	</tr>
</table>
</html:form>
<a href="rules.html">Rules</a>
</div>
