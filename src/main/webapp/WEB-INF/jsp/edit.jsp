<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="com.design_shinbi.model.entity.Task"%>

<%
Task task = (Task) request.getAttribute("task");
String message = (String) request.getAttribute("message");

String submit = "追加";
String method = "add";
String content = "";
String deadline = "";

if (task != null) {
	method = "update";
	submit = "更新";
	content = task.getContent();
	if (task.getDeadline() != null) {
		deadline = task.getDeadline().toString();
	}
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TODO アプリ</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
	<h1>TODO</h1>
	<%
	if (message != null) {
	%>
	<div id="error"><%=message%></div>
	<%
	}
	%>
	<form method="post" action="task">
		<input type="hidden" name="method" value="<%=method%>">
		<%
		if (task != null) {
		%>
		<input type="hidden" name="id" value="<%=task.getId()%>">
		<%
		}
		%>

		<table>
			<tr>
				<th>内容</th>
				<td><input type="text" name="content" class="input_content"
					value="<%=content%>"></td>
			</tr>
			<tr>
				<th>〆切</th>
				<td><input type="date" name="deadline" value="<%=deadline%>"></td>
			</tr>
		</table>
		<input type="submit" value="<%=submit%>">
	</form>
</body>
</html>