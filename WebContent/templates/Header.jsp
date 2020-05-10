<html>
<style><%@include file="/styles/AmatistaStyles.css"%></style>
<head>
<title>AmaTista</title>
</head>
<link rel="icon" 
      type="image/png" 
      href="/Amatista/file/Logo.png" />
<img alt="Image" src="/Amatista/file/Logo.png" width="160" height="160"
	class="logo">
<%
	String name = (String) session.getAttribute("menuButtons");
	out.print(name);
%>
</html>
<script type="text/javascript">
	function editPage(){
		var actual = location.pathname.substr(10);
		location.href="EditorPage?isForEdit=true&url="+actual;
	}
</script>