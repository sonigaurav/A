<%@page import="com.sunrays.javarefbook.model.UserModel"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<html>
<head>
<link type="text/css" href="css/form.css" rel="stylesheet" />
</head>
<body>
	<CENTER>
		<jsp:include page="Header.jsp"></jsp:include>
		<fieldset>
			<legend>Add URL Form:</legend>
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
			<h4><%=message%></h4>
			<%
				}
			%>
			<form action="AddURLCtl.do" class="cmxform" id="commentForm"
				method="post">
				<label for="input-one" class="float"><strong>URL:</strong></label> <br>
				<input type="url" name="url" class="inp-text" required="required"><br>
				<label for="input-one" class="float"><strong>Title:</strong></label>
				<br> <input type="text" name="title" class="inp-text"
					required="required"><br> <label for="input-one"
					class="float"><strong>Keyword:</strong></label> <br> <input
					type="text" name="keyword" class="inp-text" required="required"><br>
				<label for="input-one" class="float"><strong>Description:</strong></label>
				<br> <input type="text" name="description" class="inp-text"
					required="required"><br> <input type="submit"
					name="operation" value="Add URL" class="submit">
			</form>
		</fieldset>
		<jsp:include page="Footer.jsp"></jsp:include>
	</CENTER>
</body>
</html>