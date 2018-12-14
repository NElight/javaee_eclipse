<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	Date d = new Date(session.getCreationTime());
	Date lastAccTime = new Date(session.getLastAccessedTime());
	
	String useridKey = "userid";
	String userid = "yangzg";
	Integer visitCount = new Integer(0);
	String countKey = "";

%>    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>