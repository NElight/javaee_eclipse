<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>param example</title>
</head>
<body>

<%!
	public String decodePostStr(String str) throws Exception{
		return new String(str.getBytes("ISO-8859-1"), "UTF-8");
	
	}

%>

<table width = "50%" border = "1" align = "center">

<tr>
	<th>param name</th>
	<th>param value</th>
</tr>

<%
	Enumeration e = request.getParameterNames();
	while (e.hasMoreElements()){
		String name = (String)e.nextElement();
		String value = request.getParameter(name);
		out.println("<tr><td>" + decodePostStr(name) + "</td><td>" + decodePostStr(value) + "</td></tr>");
	}
%>
</table>

<form action= "param.jsp" method="post">
参数1：<input type="text" name = "param1">
<br/>
参数2:<input type = "text" name = "param2">
<br/>
<input type="checkbox" name = "google" checked = "checked"> google &nbsp &nbsp
<input type = "checkbox" name= "baidu"> baidu
<input type="submit" value = "提交">
</form>


</body>
</html>