<%@page import="com.sunrays.javarefbook.model.UserModel"%>
<html>
<head>
<link type="text/css" href="css/form.css" rel="stylesheet" />
</head>
<body>
	<center>
		<jsp:include page="Header.jsp"></jsp:include>
		<fieldset>
			<legend>Welcome Page:</legend>
			<%
				UserModel model = (UserModel) session.getAttribute("user");
				if (model != null) {
			%>
			<H1>
				Welcome&nbsp;<%=model.getFirstName()%>&nbsp;! Now you are ready to
				access Application.
			</H1>
			<%
				}
			%>
		</fieldset>
		<jsp:include page="Footer.jsp"></jsp:include>
	</center>
</body>
</html>