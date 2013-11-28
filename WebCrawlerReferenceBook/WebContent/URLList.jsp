<%@page import="com.sunrays.javarefbook.model.URLModel"%>
<%@page import="java.util.List"%>
<%@page import="com.sunrays.javarefbook.model.UserModel"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<html>
<head>
<link type="text/css" href="css/form.css" rel="stylesheet" />

<style type="text/css">
table,tr,td {
	border-collapse: collapse;
	border: 1px solid;
	border-spacing: 10px;
}
</style>

</head>
<body>
	<CENTER>
		<jsp:include page="Header.jsp"></jsp:include>
		<fieldset>
			<legend>URL List:</legend>
			<table border="1">
				<%
					UserModel userModel = new UserModel();
					String userName = null;
					String error = (String) request.getAttribute("error");
					if (error != null) {
				%>
				<h4><%=error%></h4>
				<%
					}
				%>
				<%
					String message = (String) request.getAttribute("message");
					if (message != null) {
				%>
				<h4><%=message%></h4>
				<%
					}
				%>
				<%
					List urlList = (List) request.getAttribute("urlList");

					if (urlList != null) {
				%>

				<tr>
					<th>URL</th>
					<th>Crawling Word</th>
					<th>User</th>
				</tr>
				<%
					Iterator iterator = urlList.iterator();
						while (iterator.hasNext()) {
							URLModel urlModel = (URLModel) iterator.next();
							userModel = userModel.findByPK(urlModel.getUser_id());
							userName = userModel.getLogin();
				%>
				<tr>
					<td><a href="<%=urlModel.getUrl()%>" target="_blank"><%=urlModel.getUrl()%></a></td>
					<td><%=urlModel.getCrawlingWord()%></td>
					<td><%=userModel.getLogin()%></td>
				</tr>
				<%
					}
					} else {
				%>
				<h2>You don't have any URL.</h2>
				<%
					}
				%>
			</table>
		</fieldset>
		<jsp:include page="Footer.jsp"></jsp:include>
	</CENTER>
</body>
</html>