<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin Home Page</title>
</head>
<body>
	<table border="1">
		<tr>
			<td>Topic Name</td>
			<td>Action</td>
		</tr>
		<c:forEach items="${topics}" var="topic">
		<tr>
			<td>${topic.topicName}</td>	
			<td><a href="#">Edit</a>&nbsp &nbsp <a href="#">Delete</a></td>
		</tr>
		</c:forEach>
	</table>
</body>
</html>