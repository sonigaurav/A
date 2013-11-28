<html>
<head>
<meta charset="utf-8" />
<script language="javascript" type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script language="javascript" type="text/javascript"
	src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.10.0/jquery.validate.min.js"></script>
<script>
	$(function() {
		$("#mytestform")
				.validate(
						{
							rules : {
								aname : {
									required : true,
									minlength : 4,
									maxlength : 20
								},
								lname : {
									required : true,
									minlength : 4,
									maxlength : 20
								}
							},
							messages : {
								aname : {
									required : "Dude, enter a name",
									minlength : $
											.format("Keep typing, at least {0} characters required!"),
									maxlength : $
											.format("Whoa! Maximum {0} characters allowed!")
								},
								lname : {
									required : "Dude, enter a Lastname",
									minlength : $
											.format("Keep typing, at least {0} characters required!"),
									maxlength : $
											.format("Whoa! Maximum {0} characters allowed!")
								}
							}
						});
	});
</script>

</head>
<body>
	<form id="mytestform" name="" method="get" action="">
		<p>
			<label for="aname">Name:&nbsp;</label> <input name="aname"
				class="required" minlength=”4” size="20" /> <label for="aname">LastName:&nbsp;</label>
			<input name="lname" class="required" minlength=”4” size="20" /> <input
				name="operation" type="submit" value="Submit" />
		</p>
	</form>

</body>
</html>