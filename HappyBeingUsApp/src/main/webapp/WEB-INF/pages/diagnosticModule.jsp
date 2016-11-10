<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Diagnostic Questions</title>
<!-- Bootstrap Core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- jQuery -->
<script src="js/jquery.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="js/bootstrap.min.js"></script>

<style>
body {
    counter-reset: section;
}

h6:before {
    counter-increment: section;
    content: counter(section);
}
h6:after{
	counter-reset: section;
}
</style>
<script type="text/javascript">
function deleteDiagnosticQuestion(deletedTag){
	
	console.log("Reached delete diagnostic");
	
	
	
	var deleteId = deletedTag.id.split("_")[1];
	console.log(deleteId);
	var form = document.getElementById("confirmationForm");
	form.action = "deleteDiagnosticQuestion.action";
	console.log("id is: "+deleteId);
	$("#deletableId").val(deleteId);
	$("#confirmationDialog").modal("toggle");
	

}


</script>






</head>
<body>
<div class="container-fluid">
<div class="row">
			<div class="col-sm-12">
			
			<c:choose>
				<c:when test="${fn:length(diagnosticQuestions)>0}"> 
					<c:forEach items="${diagnosticQuestions}" var="diagnostic">
						<div class="Question With Answers">
								
							<h2>Question ${diagnostic.activity.orderNo} 
							<!--Edit Diagnostic Question-->
							<a class="btn btn-success" 
								id="${diagnostic.activity.id}"
								href="editDiagnosticQuestion.action?id=${diagnostic.activity.id}">
								<span class="glyphicon glyphicon-pencil"></span></a> 
							
							<!--Delete Diagnostic Question-->	
							<a class="btn btn-danger"
									id="deleteId_${diagnostic.activity.id}"
									role="button" onclick="deleteDiagnosticQuestion(this)"> 
									<span class="glyphicon glyphicon-trash"></span>
							</a>
							</h2>
							<!-- Load Question Content -->
							<h3>${diagnostic.activity.activityText}</h3>
							
							<!-- Now load answers from answers list -->
							<h3> Answers </h3>
							<c:choose> 
										<c:when test="${fn:length(diagnostic.answers)>0}">
											<c:set var="count" value="0" scope="page" />
											<c:forEach items="${diagnostic.answers}" var="diagnosticanswer">
												<c:set var="count" value="${count + 1}" scope="page"/>
												<c:out value="${count}" />
												${diagnosticanswer.answerText}  
												</br>									
											</c:forEach>
									</c:when>
						   </c:choose>
							
							
						</div>
						


					</c:forEach>
					
			
				
			
				</c:when>
			</c:choose>
			<a class="btn btn-success" href="redirectToAddDQ.action">
						Add Question </a>
			
			</div>
			
			
			
			
			</div>
			
			 <!-- 			Confirmation dialog before delete START -->
			<div class="modal fade" id="confirmationDialog" role="dialog">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<h4 class="modal-title">Please confirm!</h4>
						</div>
						<form id="confirmationForm" name="confirmationForm" method="post">
							<div class="modal-body">
								<h4 class="modal-title">Do you really want to remove this?</h4>
								<input type="hidden" class="form-control" id="deletableId"  name="deletableId" />
							</div>
							<div class="modal-footer">
								<a class="btn btn-default" data-dismiss="modal">No</a>
								<input type="submit" class="btn btn-success" role="button" value="Yes"/>
							</div>
						</form>
					</div>
				</div>
			</div>
			
			
			
			
			
</div>	
</div>








</body>
</html>