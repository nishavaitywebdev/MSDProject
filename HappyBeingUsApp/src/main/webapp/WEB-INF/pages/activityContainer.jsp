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
	function editActivity(id){
		
		var form = document.getElementById("editForm");
		form.action="editActivity.action";
		form.children.namedItem("id").value=id;
		form.submit();
	}
</script>
</head>
<body>
	<table border="1">
		<tr>
			<td><b>Activity Container Name</b></td>
			<td><b>Action</b></td>
		</tr>
		<tr>
			<td>${activityContainer.containerName}</td>
			<td><a href="#">Edit</a>&nbsp &nbsp <a href="#">Delete</a></td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td><b>Activity Name</b></td>
			<td><b>Action</b></td>
		</tr>
		<c:forEach items="${activityContainer.activities}" var="activity">
			<tr>
				<td></td>
				<td></td>
				<td>${activity.activityText}</td>
				<td><a id="${activity.id}" href="#" onclick="editActivity(id)">Edit</a>&nbsp
					&nbsp <a href="#">Delete</a></td>
			</tr>
		</c:forEach>
	</table>

	<form name="editForm" id="editForm" action="#" method="post">
		<inupt type="hidden" name="id" value="" />
	</form>
</body>
</html>