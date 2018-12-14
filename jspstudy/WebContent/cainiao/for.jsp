<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<h1>for 循环</h1>

<% for (int fontsize = 1; fontsize < 10; fontsize ++){ %>
	<font color= "green" size = <%=fontsize %> >我是一只小菜鸟</font>
	<br/>
<%} %>

</body>
</html>