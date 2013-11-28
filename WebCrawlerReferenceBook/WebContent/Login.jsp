<%@page import="java.util.*"%>
<html>
<head>
<link type="text/css" href="css/form.css" rel="stylesheet" />
</head>
<body>
	<center>


		<jsp:include page="Header.jsp"></jsp:include>

		<fieldset>
			<legend>Login Form:</legend>
			<%
				//Print Error messages if Input validaiton is fail
				ArrayList errors = (ArrayList) request.getAttribute("error");
				if (errors != null) {
					out.println("<font style='color:red'>");

					Iterator it = errors.iterator();
					while (it.hasNext()) {
						out.println(it.next());
					}
					out.println("</font>");
				}
			%>
			<%
				String message = (String) request.getAttribute("message");
				if (message != null) {
			%>
			<b> <%=message%>
			</b>
			<%
				}
			%>
			<form action="LoginCtl" class="cmxform" id="commentForm"
				method="post">

				<label for="input-one" class="float"><strong>Login:</strong></label>

				<br> <input type="text" class="inp-text" name="login"
					id="input-one" class="required" minlength=”1” size="20"
					minlength="2" required="required"> <br> <label
					for="input-one" class="float"><strong>Password:</strong></label> <br>
				<input type="password" class="inp-text" name="password"
					class="required" minlength=”1” size="20" required="required">
				<br>
				<p class="pp">
					<input type="submit" class="submit" name="operation"
						value="Sign In">
				</p>
		</fieldset>
		</form>
		<jsp:include page="Footer.jsp"></jsp:include></center>
	</center>
</body>
</html>