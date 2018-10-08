<%@page import="com.sun.xml.internal.bind.v2.schemagen.xmlschema.Import"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<br>hello world 菜鸟</br>

<%!
	int i = 0;
	int j = 1;
	
%>

<%--  this is zhushi --%>

<p>
	今天的日期是：  <%= (new java.text.SimpleDateFormat("YYYY-MM-dd HH:mm:ss")).format(new Date()) %>
</p>

<%
	out.println("你这个菜鸟的 ip is " + request.getRemoteAddr());
%>

</body>
</html>