<html>
<jsp:include page="../templates/Header.jsp" />
<body>
	<div>
		<%
			String name = (String) session.getAttribute("test");
			out.print("Hello User: You have entered the name: " + name);
		%>
	</div>
</body>
<jsp:include page="../templates/Footer.jsp" />
</html>