<%@page import="java.util.Date"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>auto refresh</title>
</head>
<body>

<h1>自动刷新实例</h1>
<%
	response.setIntHeader("refresh", 5);

	Calendar c = new GregorianCalendar();
	String am_pm;
	int hour = c.get(Calendar.HOUR);
	int minute = c.get(Calendar.MINUTE);
	int second = c.get(Calendar.SECOND);
	if (c.get(Calendar.AM_PM) == 0){
		am_pm = "am";
	}else {
		am_pm = "pm";
	}
	
	String time = hour + ":" + minute + ":" + second + am_pm;
	out.println(time);
	

%>

<br/>

<%
	Date d = new Date();
	out.println(d.getTime());

%>

</body>
</html>