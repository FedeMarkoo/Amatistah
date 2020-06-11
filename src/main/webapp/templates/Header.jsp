<html>
<link rel="stylesheet" href="styles/bootstrap.css">
<link href="https://fonts.googleapis.com/css?family=Kaushan+Script"
	rel="stylesheet">
<link
	href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"
	rel="stylesheet">
<%@ taglib uri="/WEB-INF/tags.tld" prefix="t"%>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<meta name="google-signin-client_id"
	content="317003566468-7p8tl3p7ijdfjt7cmehfpb3depr5guet.apps.googleusercontent.com">
<script src="https://apis.google.com/js/platform.js" async="true" defer></script>
<link rel="stylesheet" href="styles/AmatistaStyles.css">
<head>
<title>AmaTista</title>
</head>
<link rel="icon" type="image/png" href="/file/Logo.png" />
<div class="logo" onclick="location.href='/ama.tista'"></div>

<div class="dropdown">
	<t:menu />
</div>

<t:userTag />
</html>

<script type="text/javascript">
	function editPage() {
		var actual = location.pathname.substr(10);
		location.href = "EditorPage?isForEdit=true&url=" + actual;
	}
</script>