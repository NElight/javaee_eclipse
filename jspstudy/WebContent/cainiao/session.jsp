<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	Date d = new Date(session.getCreationTime());
	Date lastAccTime = new Date(session.getLastAccessedTime());
	
	String title ="not first time";
	String useridKey = "userid";
	String userid = "张松";
	Integer visitCount = new Integer(0);
	String countKey = "visitCount";
	
	if (session.isNew()){
		title = "first time";
		session.setAttribute(useridKey, userid);
		session.setAttribute(countKey, visitCount);
	}else {
		visitCount = (Integer)session.getAttribute(countKey);
		visitCount += 1;
		session.setAttribute(countKey, visitCount);
		userid = (String) session.getAttribute(useridKey);
	}
%>    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>session example</title>
</head>
<body>

<table align = "center" border = 2>
	<tr>
		<td>id</td>
		<td><%= session.getId() %></td>
	</tr>
	<tr>
		<td>userid</td>
		<td><%= userid %></td>
	</tr>
	<tr>
		<td>visitcount</td>
		<td><%= visitCount %></td>
	</tr>
	<tr>
		<td>create time</td>
		<td><%= d.toLocaleString() %></td>
	</tr>
	<tr>
		<td>last visit time</td>
		<td><%= lastAccTime.toLocaleString() %></td>
	</tr>
</table>


</body>
</html>