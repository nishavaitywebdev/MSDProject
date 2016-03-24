<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin Home Page</title>

<script type="text/javascript">
	function editContainer(id){
		
		var form = document.getElementById("editForm");
		form.action="editActivityContainer.action";
		form.children.namedItem("id").value=id;
		form.submit();
	}
</script>
</head>
<body>
	<table border="1">
		<tr>
			<td><b>Topic Name</b></td>
			<td><b>Action</b></td>
		</tr>
		<c:forEach items="${topics}" var="topic">
		<tr>
			<td>${topic.topicName}</td>
			<td><a href="#">Edit</a>&nbsp &nbsp <a href="#">Delete</a></td>
		</tr>
			<tr>
				<td></td>
				<td></td>
				<td><b>Block Name</b></td>
				<td><b>Action</b></td>
			</tr>
		<c:forEach items="${topic.activityContainers}" var="activityContainer">
			<tr>
				<td></td>
				<td></td>
				<td>${activityContainer.containerName}</td>
				<td><a id="${activityContainer.activityContainerId}" href="#" onclick="editContainer(id)">Edit</a>&nbsp &nbsp <a href="#">Delete</a></td>
			</tr>
		</c:forEach>
		</c:forEach>
	</table>
	
	<form name="editForm" id="editForm" action="#" method="post">
		<input type="hidden" name="id" value=""/>
	</form>
</body>
</html>