<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Bootstrap Example</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</head>
<body>
	<form:form action="dg.action" method="post">
		<c:forEach items="${activityAnswers}" var="activityAnswer">
			<div class="container">
				<h2>${activityAnswer.activity.orderNo}</h2>
				<p>${activityAnswer.activity.activityText}</p>
					<c:forEach items="${activityAnswer.answers}" var="answer">
						<div class="radio">
							<label><input type="radio" name="scores[${activityAnswer.activity.orderNo}]"
								value="${answer.orderNo}">${answer.answerText}</label>
						</div>
					</c:forEach>
			</div>
		</c:forEach>
		<div class="modal-footer"> <input
				class="btn btn-primary" type="submit" value="submit" />

		</div>
	</form:form>

</body>
</html>