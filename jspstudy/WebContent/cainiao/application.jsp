<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>application example</title>
</head>
<body>

<%
	Integer count = (Integer) application.getAttribute("count");
	if (count == null || count == 0){
		out.println("<h1>第一次访问</h1>");
		count = 0;
	}else {
		out.println("<h1>不是第一次访问了</h1>");
	}
	count ++;
	out.println("<h1>第" + count +"次访问</h1>");
	application.setAttribute("count", count);

%>

</body>
</html>