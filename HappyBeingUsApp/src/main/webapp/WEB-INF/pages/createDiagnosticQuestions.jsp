<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Diagnostic Module</title>
	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<style>
		/* Remove the navbar's default margin-bottom and rounded borders */
		.navbar {
			margin-bottom: 0;
			border-radius: 0;
		}
		/* Add a gray background color and some padding to the footer */
		.jumbotron {
			background-color: orange;
		}
		.inv {
			display: none;
		}
	</style>
	<script>
	/* $(".goBack").on("click",function(e) {
	    e.preventDefault(); // cancel the link itself
	    $("#editForm").attr('action', this.href);
		$("#editForm").submit();
	  }); */
//	mcq template, update checkbox value with the input of user
	$(document).on('change', '#mcqOptions .option', function() {
		var num = this.id.split('_')[1];
		$('#mcqOptions #checkBox_'+num).val(this.name);
	});
	// Adding more options inside the mcq template
	$(document).on('click', '#mcqMoreOptions', function() {
		var maxVal = $('#mcqMaxOptions').val();
		var nxtVal = parseInt(maxVal)+1;
		$('#mcqOptions').append(
				'<div class="mcqOption" id="mcqOption_'+nxtVal+'"  class="form-group"> '
					+ '<div class="row"> '
					+ '<div class="col-sm-1"> '
					+'<input type="Checkbox" name="correctAnswer" class="chkbx" id="checkBox_'+nxtVal+'" /> '
					+'</div> '
					+'<div class="col-sm-10"> '
					+'<input class="option  form-control" type="text" name="option_'+nxtVal+'" id="option_'+nxtVal+'" placeholder="Content for this choice" required/> '
					+'</div>'
					+'<div class="col-sm-1">'
					+'<button class="removeOption btn btn-primary btn_lg" id="removeOption_'+nxtVal+'" type="button">Remove</button><br> '
					+'</div>'
				+'</div>');
		$('#mcqMaxOptions').val(nxtVal);
		if(nxtVal == 5){
			$('#mcqMoreOptions').css("display", "none");
		}
	});
	
	//		mcq template, remove option
	$(document).on('click', "#mcqOptions .removeOption", function() {
		var removeId = this.id.split("_")[1];
		$('#mcqOptions #mcqOption_'+removeId).remove();
		var maxVal = $('#mcqMaxOptions').val();
		var newVal = parseInt(maxVal)-1;
		var initVal = 0;
		$("#mcqOptions .mcqOption").each(function() {
			initVal = initVal +1;
			this.id = "mcqOption_"+initVal;
			$('#'+this.id+' .chkbx')[0].id = "checkBox_"+initVal;
			$('#'+this.id+' .option')[0].id = "option_"+initVal;
			$('#'+this.id+' .option')[0].name = "option_"+initVal;
			$('#'+this.id+' .removeOption')[0].id = "removeOption_"+initVal;
		});
		$('#mcqMoreOptions').css("display", "block");
		$('#mcqMaxOptions').val(newVal);
	});
	
	</script>
</head>
<body>

<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#myNavbar">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#" class="goBack">Admin Home</a>
			</div>
			<div class="collapse navbar-collapse" id="myNavbar">
				<ul class="nav navbar-nav">
 					<li class="active"><a href="#" class="goBack">Modules and Blocks</a></li> 
					<li><a data-toggle="modal" id ="addAdmin" href="#addNewAdmin">Add New Admin</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="adminLogout.action"><span class="glyphicon glyphicon-log-out"></span> Logout </a></li>
				</ul>
			</div>
		</div>
	</nav>
	
	<div class="jumbotron">
		<div class="container text-center">
			<h2>Add Diagnostic Question</h2>
		</div>
	</div>
	
	
	<form action="addDiagnosticQuestion.action" method="post" name="mcqForm"
						id="mcqForm"
						enctype="multipart/form-data">
							<div class="container">
								<h2></h2>
								<div class="form-group">
									<label for="comment">Question Content:</label>
									<textarea name="Question" path="activityText"
										class="form-control" rows="5" id="commentMCQ"
										placeholder="Enter Question Contents Here."></textarea>
								</div>

								<div id="mcqOptions">
								
									<div class="mcqOption" id="mcqOption_1" class="form-group">
										<div class="row">
											<div class="col-sm-1">
												<input type="Checkbox" name="correctAnswer" class="chkbx"
													id="checkBox_1" />
											</div> 
											<div class="col-sm-10">
												<input class="option form-control"
													type="text" name="option_1" id="option_1"
													placeholder="Content for this choice" required />
											</div>
											<div class="col-sm-1">
												<button class="removeOption btn btn-primary btn_lg"
													id="removeOption_1" type="button">Remove</button>
											</div>
										</div>
									</div>
									<div class="mcqOption" id="mcqOption_2" class="form-group">
										<div class="row">
											<div class="col-sm-1">
												<input type="Checkbox" name="correctAnswer" class="chkbx"
													id="checkBox_2" />
											</div> 
											<div class="col-sm-10">
												<input class="option form-control"
													type="text" name="option_2" id="option_2"
													placeholder="Content for this choice" required />
											</div>
											<div class="col-sm-1">
												<button class="removeOption btn btn-primary btn_lg"
													id="removeOption_2" type="button">Remove</button>
											</div>
										</div>
									</div>
									
								<div class="mcqOption" id="mcqOption_3" class="form-group">
									<div class="row">
										<div class="col-sm-1">
											<input type="Checkbox" name="correctAnswer" class="chkbx"
												id="checkBox_3" />
										</div> 
										<div class="col-sm-10">
											<input class="option form-control"
												type="text" name="option_3" id="option_3"
												placeholder="Content for this choice" required />
										</div>
										<div class="col-sm-1">
											<button class="removeOption btn btn-primary btn_lg"
												id="removeOption_3" type="button">Remove</button>
										</div>
									</div>
								</div>
								</div>
								<tr>
									<td><br /></td>
								</tr>
								<div class="container">
									<div class="row">
										<div class="col-sm-6">
								
											<div class="text-left">
												<button id="mcqMoreOptions" type="button"
													class="btn btn-primary btn_lg">Add more options</button>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="text-right">
												<button type="submit" class="btn btn-primary btn_lg">Add</button>
											</div>
										</div>
									</div>
								</div>
							</div>
							<tr>
								<td><br /></td>
							</tr>
							<div class="jumbotron">
								<footer class="container-fluid text-right"> </footer>
							</div>
							<%-- <form:input type="hidden" path="activityType.id" />
							<form:input type="hidden" path="activityTemplate.id"
								value="${template.id}" />
							<form:input type="hidden"
								path="activityContainer.activityContainerId" /> --%>
					</form>
					<input type="hidden" id="mcqMaxOptions" value="3" />
					<!-- Footer -->
	<%@ include file="footer.jsp" %>
</body>
</html>