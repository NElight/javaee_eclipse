<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>switch case实例</title>
</head>
<body>
<%! private int day = 3; %>

<% 
	switch (day){
	case 0:
		out.println("星期日");
		break;
	case 3:
		out.println("星期三");
		break;
	default:
		out.println("whatever");
	}
%>
</body>
</html>