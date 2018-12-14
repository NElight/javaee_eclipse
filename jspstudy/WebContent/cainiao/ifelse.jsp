<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<%! private int day = 4; %>

<h1>if else 控制语句</h1>
<% if (day == 1){ %>
	<p>今天 是周一</p>
<% } else { %>
	<p>今天不是周一</p>
<%} %>

</body>
</html>