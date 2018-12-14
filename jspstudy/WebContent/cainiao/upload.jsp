<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>upload file</title>
</head>
<body>

<form action="/jspstudy/uploadfile" method = "post" enctype="multipart/form-data">
	<input type="file" name = "uploadfile" style="border:'1',color:'red'"/>
	<br/>
	<br/>
	<input type = "submit" value = "上传" />
</form>


</body>
</html>