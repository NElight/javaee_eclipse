<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>request page</title>
</head>
<body>

<h1>request header example</h1>
<table width= "100%" border="1" align= "center">
	<tr bgcolor="#949494">
		<th>header name</th>
		<th>header value</th>
	</tr>
	
	<%
	Enumeration e = request.getHeaderNames();
	while (e.hasMoreElements()){
		String name = (String)e.nextElement();
		String value = request.getHeader(name);
		out.println("<tr><td>" + name + "</td><td>" + value + "</td></tr>");
	}
	
	%>
</table>

</body>
</html>