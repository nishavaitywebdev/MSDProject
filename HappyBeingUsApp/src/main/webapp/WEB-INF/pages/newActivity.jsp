<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Upload Activity here</title>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js">
	
</script>
<script>
	$(document).ready(function() {
		$('#activityTemplate').on('change', function() {
			var template_id = $('#activityTemplate').val();
			$("#activityTemplate > option").each(function() {
				if (template_id == this.value) {
					$("#"+this.value).css("display", "block");
				} else {
					$("#"+this.value).css("display", "none");
				}
			});
		});
	});
</script>
</head>
<body>
	<label>Template type : </label>

	<select name="activityTemplate" id="activityTemplate">
		<option value="-1">--SELECT--</option>
		<c:forEach items="${activityTemplates}" var="template">
			<option value="template_${template.id}">${template.templateName}</option>
		</c:forEach>
	</select>

	<div id="templateContainer">
		<c:forEach items="${activityTemplates}" var="template">
			<c:if test="${template.id==3}">
				<form:form action="addActivity.action" method="post"
					modelAttribute="activity">
					<div id="template_${template.id}" style="display: none">
						<p>Type the question below</p>
						<form:input style="width: 300px" type="text" name="Question"
							path="activityText" />
						<p>Type the Options below</p>
						<input type="Checkbox" name="Answer1" /><input type="text"
							name="Option1" /><br> <input type="Checkbox" name="Answer2" /><input
							type="text" name="Option2" /><br> <input type="Checkbox"
							name="Answer3" /><input type="text" name="Option3" /><br>
						<input type="Checkbox" name="Answer4" /><input type="text"
							name="Option4" /><br> <input type="Checkbox" name="Answer5" /><input
							type="text" name="Option5" /><br> <br></br>
						<button type="submit">Upload</button>
						<form:input type="hidden" path="activityType.id" />
						<form:input type="hidden" path="activityTemplate.id"
							value="${template.id}" />
						<form:input type="hidden" path="activityContainer.activityContainerId" />

					</div>
				</form:form>
			</c:if>
			<c:if test="${template.id==2}">
				<form:form action="addActivity.action" method="post"
					modelAttribute="activity">
					<div id="template_${template.id}" style="display: none">
						<p>Type the question below</p>
						<form:input style="width: 300px" type="text" name="Question"
							path="activityText" />
						<p>Type the Options below</p>
						<input type="Checkbox" name="Answer1" /><input type="text"
							name="Option1" /><br> <input type="Checkbox" name="Answer2" /><input
							type="text" name="Option2" /><br> <br></br>
						<button type="submit">Upload</button>
						<form:input type="hidden" path="activityType.id" />
						<form:input type="hidden" path="activityTemplate.id"
							value="${template.id}" />
						<form:input type="hidden" path="activityContainer.activityContainerId" />

					</div>
				</form:form>
			</c:if>
		</c:forEach>
	</div>

</body>
</html>