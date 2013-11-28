<%@page import="java.util.*"%>
<html>
<head>
<link type="text/css" href="css/form.css" rel="stylesheet" />
</head>
<body>
	<center>
		<jsp:include page="Header.jsp"></jsp:include>

		<fieldset>
			<legend>User Registration Form:</legend>
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

			<form action="UserRegCtl" class="cmxform" id="commentForm"
				method="post">
				<label for="input-one" class="float"><strong>First
						Name:</strong></label> <br> <input type="text" class="inp-text"
					name="firstName" required="required"> <br> <label
					for="input-one" class="float"><strong>Last Name:</strong></label> <br>
				<input type="text" class="inp-text" name="lastName"
					required="required"> <br> <label for="input-one"
					class="float"><strong>Login:</strong></label> <br> <input
					type="text" class="inp-text" name="login" required="required">
				<br> <label for="input-one" class="float"><strong>Password:</strong></label>
				<br> <input type="password" class="inp-text" name="password"
					required="required"> <br> <input type="submit"
					class="submit" name="operation" value="Sign Up">
			</form>
		</fieldset>
		<jsp:include page="Footer.jsp"></jsp:include>
	</center>
</body>
</html>