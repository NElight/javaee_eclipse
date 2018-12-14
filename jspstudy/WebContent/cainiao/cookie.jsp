<%@page import="com.sun.xml.internal.bind.v2.runtime.Name"%>
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
	Cookie[] cookies = null;
	cookies = request.getCookies();
	if (cookies != null){
		for (Cookie e:cookies){
			out.println("cookie name:" + e.getName());
			out.println("cookie value:" + e.getValue());
		}
	}else {
		out.println("no cookie");
	}
	
	Cookie c = new Cookie("name", "abcdefg");
	c.setMaxAge(120);
	response.addCookie(c);

	
%>

</body>
</html>