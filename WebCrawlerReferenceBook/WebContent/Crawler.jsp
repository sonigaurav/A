<%@page import="com.sunrays.javarefbook.model.UserModel"%>
<%@page import="com.sunrays.javarefbook.model.URLModel"%>
<%@page import="com.sunrays.javarefbook.model.CrawlerModel"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<html>
<body>

	<center>

		<jsp:include page="Header.jsp"></jsp:include>
		</br> <a href="Crawler.jsp" id="caplink"><p id="cap">Web Crawler</p></a><br>
		<%
			String error = (String) request.getAttribute("error");
			if (error != null) {
		%>
		<b> <font color="red"><%=error%></font>
		</b>
		<%
			}
		%>
		<form action="CrawlerCtl" onsubmit="return vali(this)" method="post">
			<table>
				<tr>
					<td></td>
					<td align="center"><font color="red"><div id="print"></div>
					</font></td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="text" value="Everything is Here.."
						name="keyword"
						style="font-style: italic; color: #666; width: 500px"></td>
					<td><input type="submit" value="Search" name="operation"
						class="submit"></td>
				</tr>

			</table>
		</form>
		<br>

		<%
			List list = (List) request.getAttribute("crawledData");

			if (list != null) {
				Iterator iterator = list.iterator();
				while (iterator.hasNext()) {
					URLModel model = (URLModel) iterator.next();
					CrawlerModel crawlerModel = model.getCrawlerModel();
		%>

		<div align="left" style="margin-left: 100px">
			<h4>
				<%=crawlerModel.getTitle()%>
			</h4>
			<strong><a
				href="CrawlerCtl?operation=UpdatePageRank&id=<%=model.getId()%>"
				target="_blank"><%=model.getUrl()%></a> </strong>
			<p>
				<%=crawlerModel.getDescription()%>
			</p>
		</div>
		<br>
		<%
			}
			}
		%>
		<jsp:include page="Footer.jsp"></jsp:include></center>
</body>
</html>