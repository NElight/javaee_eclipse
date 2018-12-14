<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>life.jsp</title>
</head>
<body>
<%!
	private int initVar = 0;
	private int serviceVar = 0;
	private int destoryVar = 0;
%>

<%!
	public void jspInit(){
		initVar++;
		System.out.println("jspInit(): jsp被初始化了" + initVar + "次");
	}

	public void jspDestory(){
		destoryVar++;
		System.out.println("jspDestory(): jsp 被销毁了" + destoryVar + "次");
	}
%>

<%
	serviceVar++;
	System.out.println("_jspService(): jsp共响应了" + serviceVar +"次");
	String content1 = "初始化次数：" + initVar;
	String content2 = "响应客户请求次数" + serviceVar;
	String content3 = "销毁次数：" + destoryVar;

%>

<h1>jsp生命周期</h1>
<h2><%= content1 %></h2>
<h2><%= content2 %></h2>
<h2><%= content3 %></h2>
</body>
</html>