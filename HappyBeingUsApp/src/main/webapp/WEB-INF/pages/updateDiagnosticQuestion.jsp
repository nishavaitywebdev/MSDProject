<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isELIgnored="false"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
	<script
		src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
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
	.dropdown-menu {
		width: 300px !important;
		height: 400px !important;
	}
	.dropdown-menu {
		min-width: auto;
		width: 100%;
	}
	.btn-file2 {
		position: relative;
		overflow: hidden;
	}
	.btn-file2 input[type=file] {
		position: absolute;
		top: 0;
		right: 0;
		min-width: 100%;
		min-height: 100%;
		font-size: 100px;
		text-align: right;
		filter: alpha(opacity = 0);
		opacity: 0;
		outline: none;
		background: white;
		cursor: inherit;
		display: block;
	}
	</style>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert title here</title>
	<script>
	//	mcq template, update checkbox value with the input of user
		$(document).on('change', '#mcqOptions .option', function() {
			var num = this.id.split('_')[1];
			$('#mcqOptions #checkBox_'+num).val(this.name);
		});
		
		$(".goBack").on("click",function(e) {
		    e.preventDefault(); // cancel the link itself
		    $("#editForm").attr('action', this.href);
			$("#editForm").submit();
		  });
	
	//		adding more options inside the mcq template
		$(document).on('click', '#mcqMoreOptions', function() {
			var maxVal = $('#mcqMaxOptions').val();
			var nxtVal = parseInt(maxVal)+1;
			$('#mcqOptions').append(
				'<div class="mcqOption" id="mcqOption_'+nxtVal+'"  class="form-group"> '
					+ '<div class="row"> '
					+ '<div class="col-sm-1"> '
					+'<input type="Checkbox" name="correctAnswer" class="chkbx" id="checkBox_'+nxtVal+'" value="option_'+nxtVal+'" /> '
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
				$('#'+this.id+' .chkbx')[0].value = "option_"+initVal;
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

	<form:form action="updateDiagnosticQuestion.action" method="post" name="mcqForm"
			id="mcqForm" modelAttribute="adminActivity.activity"
			enctype="multipart/form-data">
				<div class="container">
					<h2></h2>
					<div class="form-group">
						<label for="comment">Question Content:</label>
						<form:textarea name="Question" path="activityText"
							class="form-control" rows="5" id="comment"
							placeholder="Enter Question Contents Here." required="true"></form:textarea>
					</div>

					<div id="mcqOptions">
						<c:forEach items="${adminActivity.answers}" var="answer">
							<div class="mcqOption" id="mcqOption_${answer.orderNo}" class="form-group">
								<div class="row">
									<div class="col-sm-1">
										<input type="Checkbox" name="correctAnswer" class="chkbx"
											id="checkBox_${answer.orderNo}" value="option_${answer.orderNo}"
											${answer.isCorrect?"checked":""} />
									</div> 
									<div class="col-sm-10">
										<input class="option form-control"
											name="option_${answer.orderNo}" id="option_${answer.orderNo}"
											value="${answer.answerText}"
											placeholder="Content for this choice" required />
									</div>
									<div class="col-sm-1">
										<button class="removeOption btn btn-primary btn_lg"
											id="removeOption_${answer.orderNo}" type="button">Remove</button>
									</div>
								</div>
							</div>
						</c:forEach>
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
				<form:input type="hidden" path="activityType.id" />
				<form:input type="hidden" path="id" />
		</form:form>
		<input type="hidden" id="mcqMaxOptions" value="3" />

</body>
</html>