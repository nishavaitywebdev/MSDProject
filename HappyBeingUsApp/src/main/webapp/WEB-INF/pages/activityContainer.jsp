<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin Home Page</title>

	<!-- jQuery -->	
	<script src="js/jquery.js"></script>

<script type="text/javascript">
	
	$(document).ready(function() {
		
		$("#addNewActivity").click(function() {
// 			var id = $("#addNewActivity").attr("name");
			$("#editForm").attr('action', 'newActivityLink.action');
			$('#id').val($("#addNewActivity").attr("name"));
// 			var form = $("#editForm")[0];
// 			form.children.namedItem("id").value=id;
			$("#editForm").submit();
		});
	});

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
				<td><a id="${activity.id}" href="#" onclick="editActivity(id)">Edit</a>&nbsp &nbsp <a href="#">Delete</a></td>
			</tr>
		</c:forEach>
	</table>
	
	<a href="#" id="addNewActivity" name="${activityContainer.activityContainerId}">Add New Activity</a>
	
	<form name="editForm" id="editForm" action="#" method="post">
		<input type="hidden" id="id" name="id" value=""/>
	</form>
</body>
</html>