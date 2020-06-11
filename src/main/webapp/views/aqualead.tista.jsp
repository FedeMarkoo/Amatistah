<html>
<title>Aqualead</title>
<body>
	<div>
		<%
			String name = (String) session.getAttribute("test");
			out.print("Hello User: You have entered the name: " + name);
		%>
	</div>
</body>
</html>