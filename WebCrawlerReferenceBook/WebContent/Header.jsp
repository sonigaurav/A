<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.sunrays.javarefbook.model.UserModel"%>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<title>Sunrays</title>
<link type="text/css" href="menu.css" rel="stylesheet" />
<script type="text/javascript" src="jquery.js"></script>
<script type="text/javascript" src="menu.js"></script>
<script type="text/javascript" src="js/jquery.clearfield.js"></script>
<script type="text/javascript" src="js/jquery.validate.js"></script>
<script>
	$(document).ready(function() {
		// $(':text').mousedown(
		// function(){
		$(':text').clearField();
		//  }
		// );
	});
</script>


</head>
<body>

	<style type="text/css">
* {
	margin: 0;
	padding: 0;
}

#menu {
	top: 0px;
	margin-left: 175px;
	margin-right: 185px;
}
</style>

	<div id="menu">
		<ul class="menu">
			<%
				UserModel model = (UserModel) session.getAttribute("user");
				if (model != null) {
			%>
			<li><a href="CrawlerCtl"><span>Home</span></a></li>
			<li><a href="CrawlerCtl" class="parent"><span>Welcome:&nbsp;<%=model.getFirstName()%></span></a>
				<div>
					<ul>
						<li><a href="URLListCtl.do"><span>URL List</span></a></li>
						<li><a href="LogoutCtl"><span>Logout</span></a></li>
					</ul>
				</div></li>
			<%
				} else {
			%>
			<li><a href="CrawlerCtl" class="parent"><span>Home</span></a>
				<div>
					<ul>
						<li><a href="UserRegCtl"><span>Create Account</span></a></li>
					</ul>
				</div></li>
			<li><a href="Login.jsp"><span>SignIn</span></a></li>
			<%
				}
			%>
			<li><a href="AddURLCtl.do"><span>Add Url</span></a></li>
			<li><a href="http://home.sunrays.co.in" target="_blank"><span>Help</span></a></li>
			<li class="last"><a href="http://home.sunrays.co.in/contact-us"
				target="_blank"><span>Contact Us</span></a></li>
		</ul>
		<ul id="logoUL">
			<span><img src="images/customLogo.png" width="170" height="75"></span>
		</ul>
	</div>
</body>
</html>