<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Admin Diagnostic Questions</title>

<!-- Bootstrap Core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- jQuery -->
<script src="js/jquery.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="js/bootstrap.min.js"></script>

<!-- <link rel="stylesheet" -->
<!-- 	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"> -->
<!-- <script -->
<!-- 	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script> -->
<!-- <script -->
<!-- 	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script> -->
<style>
body {
	counter-reset: section;
}

h6:before {
	counter-increment: section;
	content: counter(section);
}
/* Remove the navbar's default margin-bottom and rounded borders */
.navbar {
	margin-bottom: 0;
	border-radius: 0;
}
/* Add a gray background color and some padding to the footer */
/*footer {
	background-color: #f2f2f2;
	padding: 25px;
}*/
.topic_holder .topic_name:after {
	/* symbol for "opening" panels */
	font-family: 'Glyphicons Halflings';
	/* essential for enabling glyphicon */
	content: "\e114"; /* adjust as needed, taken from bootstrap.css */
	float: right; /* adjust as needed */
	color: grey; /* adjust as needed */
}

.topic_holder .topic_name.collapsed:after {
	/* symbol for "collapsed" panels */
	content: "\e080"; /* adjust as needed, taken from bootstrap.css */
}

.topic-container {
	position: fixed;
	top: 50px;
	width: 100%;
	height: 200px;
	z-index: 50;
}
/* .btn .btn-default .dropdown-toggle{
	width:100%;
}
.dropdown-menu {
	width: 100%	
} */
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
	$(document).ready(function() {
		// 		Ajax for renaming the topic name
		$("#changeTopicName").click(function() {
			topicName = $('#renameTopic input[name=renameTopicName]').val();
			topicId = $('#renameTopic input[name=renameTopicId]').val();
			$("#loadingDiv").modal("toggle");
			$("#renameTopic").modal("toggle");
			$.ajax({
				type : "POST",
				url : "renameTopic.action",
				data : "topicName=" + topicName + "&topicId=" + topicId,
				success : function(data) {
					$("#loadingDiv").modal("toggle");
					$("#topic_name_" + topicId)[0].innerHTML = topicName;
					$('#' + topicId).attr('name', topicName);
				}
			});
		});

		$('.Topics').on('click', function(e) {

			$(this).parent().addClass('active');
			var id = $(this)[0].id.split('#')[1];
			$('.topiccontentcontainer').each(function() {
				$(this).css("display", "none");
			});
			$('#' + id).css("display", "block");
			e.preventDefault(); // cancel the link itself
		});

		
		$(".nav li").click(function() {
			$(".nav li").removeClass('active');
			$(this).addClass('active');
			$('.nav-tabs a:first').tab('show')

		});

		$('.nav-tabs li:first-child a').tab('show');
		
		$(".goBack").on("click",function(e) {
		    e.preventDefault(); // cancel the link itself
		    $("#editForm").attr('action', this.href);
			$("#editForm").submit();
		  });

	});
</script>

</head>
<body>

	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#myNavbar">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				
				<!-- <a class="navbar-brand" href="#" class="goBack"> -->
			</div>
			<div class="collapse navbar-collapse" id="myNavbar">
				<ul class="nav navbar-nav">
					<li><a href="adminLoadHome.action" class="goBack">Admin</a></li>	
					
				</ul>
				<ul class="nav navbar-nav">
					<li class="active"><a href="adminDiagnostic.action">Diagnostic Questions</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="adminLogout.action"><span
							class="glyphicon glyphicon-log-out"></span> Logout </a></li>
				</ul>
			</div>
		</div>
	</nav>

	<div>
		<div class="jumbotron">
			<div class="container text-center">
				<h2>Diagnostic Questions</h2>
			</div>
		</div>
	</div>

	

	<div class="container-fluid bg-3 text-left">

		<div class="row">
			<div class="col-sm-12">

				<table class="table table-bordered table-hover">
					<c:choose>
						<c:when test="${fn:length(diagnosticQuestions)>0}">
							<c:set var="questionno" value="0" scope="page" />
							<thead>
								<tr>
									<th>Question No.</th>
									<th>Question Text</th>
									<th>Answer Text</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${diagnosticQuestions}" var="diagnostic">
									<c:set var="questionno" value="${questionno + 1}" scope="page" />
									<tr>
										<!-- <div class="Question With Answers"> -->
										<td><c:out value="${questionno}." /></td>
										<td>${diagnostic.activity.activityText}</td>
										<td><c:choose>
												<c:when test="${fn:length(diagnostic.answers)>0}">
													<c:set var="count" value="0" scope="page" />
													<c:forEach items="${diagnostic.answers}"
														var="diagnosticanswer">
														<c:set var="count" value="${count + 1}" scope="page" />
														<c:out value="${count}." />
												${diagnosticanswer.answerText}  
												</br>
														</hr>
													</c:forEach>
												</c:when>
											</c:choose></td>
										<td>
											
											<a class="btn btn-success" id="${diagnostic.activity.id}"
											href="editDiagnosticQuestion.action?id=${diagnostic.activity.id}">
												<span class="glyphicon glyphicon-pencil"></span>
										</a> <!--Delete Diagnostic Question--> <a
											class="btn btn-danger"
											id="deleteId_${diagnostic.activity.id}" role="button"
											onclick="deleteDiagnosticQuestion(this)"> <span
												class="glyphicon glyphicon-trash"></span>
										</a>
										</td>
										
								</c:forEach>
						</c:when>
					</c:choose>
				</table>
				<a class="btn btn-success" href="redirectToAddDQ.action"> Add
					Question </a>
					
				</div>
		</div>	





















		</div>
				<!-- Loading image Under progress -->
				<div id="loadingDiv" class="modal">
					<img alt="loading" src="Images/loading.gif">
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
								<h4 class="modal-title">Do you really want to remove this question ?</h4>
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
			
		
		<form name="editForm" id="editForm" action="#" method="post">
			<input type="hidden" id="id" name="id" value="" />
		</form>

		
		

			

			<!-- 		Add Admin  END -->
			<!-- Footer -->
			<%@ include file="footer.jsp"%>
			<%-- 	<jsp:include page="footer.jsp"> --%>

			<!-- jQuery -->
			<script src="js/jquery.js"></script>

			<!-- Bootstrap Core JavaScript -->
			<script src="js/bootstrap.min.js"></script>
			<script>
				$("#adminUname")
						.change(
								function() {
									userName = $('#adminUname').val();
									$("#loadingDiv").modal("toggle");
									$("#usernameMsg")[0].innerHTML = "Checking username availability.";
									$
											.ajax({
												type : "POST",
												url : "checkUsernameAvailability.action",
												data : "userName=" + userName,
												success : function(data) {
													$("#loadingDiv").modal(
															"toggle");
													$("#usernameMsg")[0].innerHTML = data;

													if (data
															.indexOf('available') == -1) {
														$("#adminUname")
																.val('');
													} else {

													}
												}
											})
								});

				$("#newUserName")
						.change(
								function() {
									userName = $('#newUserName').val();
									$("#loadingDiv").modal("toggle");
									$("#newusernameMsg")[0].innerHTML = "Checking username availability.";
									$
											.ajax({
												type : "POST",
												url : "checkUsernameAvailability.action",
												data : "userName=" + userName,
												success : function(data) {
													$("#loadingDiv").modal(
															"toggle");
													$("#newusernameMsg")[0].innerHTML = data;

													if (data
															.indexOf('available') == -1) {
														$("#newUserName").val(
																'');
													} else {

													}
												}
											})
									//			    });

									// 				$("#adminEmail").change(function() {
									// 					email = $('#adminEmail').val();
									// 					$("#loadingDiv").modal("toggle");
									// 					$("#adminEmailMsg")[0].innerHTML = "";
									// 					$.ajax({
									// 			            type : "POST",
									// 			            url : "checkEmailExists.action",
									// 			            data : "email=" + email,
									// 			            success : function(data) {
									// 			            	$("#loadingDiv").modal("toggle");
									// 			            	$("#adminEmailMsg")[0].innerHTML = data;

									// 			            	if(data != ""){
									// 			            		$("#adminEmail").val('');
									// 			            	}else{

									// 			            	}
									// 			            }
									// 			        })
								});
			</script>
</body>
</html>
