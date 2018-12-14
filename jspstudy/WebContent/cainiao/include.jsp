<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<h1>include 实例</h1>
<jsp:include page="date.jsp" flush = "true"></jsp:include>

<jsp:useBean id="yangzengguang" class = "com.yzg.Person">
	<jsp:setProperty name = "yangzengguang" property = "age" value = "1" />
	<jsp:setProperty name = "yangzengguang" property = "name" value = "杨增光" />
</jsp:useBean>

<h1>useBean 实例</h1>
<p>姓名： <jsp:getProperty property="name" name="yangzengguang"/> </p>
<p>年龄：<jsp:getProperty property="age" name = "yangzengguang" /> </p>

<h1>jsp element</h1>
<jsp:element name="abc">
	<jsp:attribute name="attr">
		attr的属性值
	</jsp:attribute>
	<jsp:body>
		xml的主体元素
	</jsp:body>
</jsp:element>


</body>
</html>