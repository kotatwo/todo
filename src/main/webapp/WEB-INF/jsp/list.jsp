<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import = "java.util.List" %>
<%@ page import = "com.design_shinbi.model.entity.Task" %>

<%
	List<Task> list = (List<Task>)request.getAttribute("list");
%>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>TODO アプリ</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <script>
    function check(){
		const ret = confirm("削除してもよろしいか？");
		return ret;
        }
    </script>
  </head>
  <body>
	<h1>TODO</h1>
	<form method="get" action="task">
		<input type="hidden" name="method" value="new">
		<input type="submit" name="新規">
	</form>
	<table>
		<tr>
		  <th>内容</th>
		  <th>〆切</th>
		  <th>登録日時</th>
		  <th>操作</th>
	    </tr>
<%
	for(Task task : list){
		String deadline="";
		if(task.getDeadline() != null){
			deadline = task.getDeadline().toString();
		}	
%>
	<tr>
		<td><%= task.getContent() %></td>
		<td><%= deadline %></td>
		<td><%= task.getCreatedAt() %></td>
		<td class="flex">
		<form method="get" action="task">
		<input type="hidden" name="method" value="edit">
		<input type="hidden" name="id" value="<%= task.getId() %>">
		<input type="submit" value="編集">
		</form>
		<form method="post" action="task" onSubmit="return check()">
		<input type="hidden" name="method" value="delete">
		<input type="hidden" name="id" value="<%= task.getId() %>">
		<input type="submit" value="削除">
		</form>
		</td>
	</tr>
<% 
}
%>
	</table>
	
  </body>
</html>