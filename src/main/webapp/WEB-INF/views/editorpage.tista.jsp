<%@ taglib uri="/WEB-INF/tags.tld" prefix="t" %>

<html lang="es">
<head>
<meta charset="UTF-8">
<script src="http://code.jquery.com/jquery-2.2.4.js"
	type="text/javascript"></script>
<script src="https://cdn.ckeditor.com/4.8.0/full-all/ckeditor.js"></script>
<title>Editor de paginas</title>
<style type="text/css">
/* Minimal styling to center the editor in this sample */
</style>
</head>
<body>
	<table id="table-1">
		<tbody>
			<tr>
				<td class="tableEditor"><label for="title">Titulo:</label> <input
					type="text" id="title" name="title"
					value="<%out.print(session.getAttribute("title") == null ? "" : session.getAttribute("title"));%>"
					style="width: 90%; background-color: #999ad0">
				</td>
				<td class="tableEditor" rowspan="2">
					<t:rolesToggle/>
				</td>
			</tr>
			<tr>
				<td class="tableEditor"><label for="url">url:</label> <input
					type="text" id="url" name="url"
					value="<%out.print(session.getAttribute("url") == null ? "" : session.getAttribute("url"));%>"
					style="width: 90%; background-color: #999ad0">
				</td>
			</tr>
		</tbody>
	</table>
	<div>
		<textarea id="editor1">
			<%
				out.print(session.getAttribute("xmlData") == null ? "" : session.getAttribute("xmlData"));
			%>
		</textarea>
	</div>
</body>
<script>
	CKEDITOR.replace('editor1', {
		uiColor : '#898ac0',
		extraPlugins : 'autogrow',
		autoGrow_minHeight : 200,
		autoGrow_bottomSpace : 50,
		removePlugins : 'resize',
		element : 'span'
	});
</script>
<script type="text/javascript">
	var editor = CKEDITOR.instances.editor1;
	var body;
	var changed = false;
	$(function() {
		if (url.value.length == 0)
			url.value = window.location.pathname;
		//setOnLoad();
	});

	editor.on('change', onChange);

	function onChange() {
		changed = true;
		editor.removeEventListener("change", onChange, false);
	}

	editor.on('instanceReady', function() {
		body = CKEDITOR.instances.editor1.document.$.body;
		editor.commands.print.exec = printButton;
		body.style.backgroundColor = '#898ac0';
	});

	function printButton() {
		var xhttp = new XMLHttpRequest()
		$.get("editorPage", {
			title : title.value,
			url : url.value,
			xmlData : body.innerHTML,
			isForSave : true
		});

		changed = false;

		location.href = 'ama.tista';
	}

	window.onbeforeunload = function() {
		if (changed) {
			return "Se van a perder los cambios, estas seguro que deseas salir?";
		}
	};
</script>
</html>