<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	String s = "http://www.runoob.com";
	response.setStatus(response.SC_MOVED_TEMPORARILY);
	response.setHeader("Location", s);
%>	

</body>
</html>