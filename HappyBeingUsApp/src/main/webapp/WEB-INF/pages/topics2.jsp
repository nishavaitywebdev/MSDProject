<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Quiz Page</title>

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
	function editContainer(id) {
		console.log("The id fetched is: "+id);
		var form = document.getElementById("editForm");
		form.action = "editActivityContainer.action";
		form.children.namedItem("id").value = id;
		form.submit();
	}
	function renameTopic(button) {
		var topicName = button.name;
		var topicId = button.id;
		$('#renameTopic input[name=renameTopicName]').val(topicName);
		$('#renameTopic input[name=renameTopicId]').val(topicId);
		$("#renameTopic").modal("toggle");
	}

	function addContainer(button) {
		var topicId = button.id.split("-")[1];
		$('#topicId').val(topicId);
	}

	function deleteTopic(deletedTag){

		var deleteId = deletedTag.id.split("_")[1];
		var topicNotEmpty = $("#topicNotEmpty_"+deleteId).val();

		if(topicNotEmpty=="true"){
			$("#warningDialog").modal("toggle");
		}else{
			var form = document.getElementById("confirmationForm");
			form.action = "deleteTopic.action";
			$("#deletableId").val(deleteId);
			$("#confirmationDialog").modal("toggle");
		}
	}

	function deleteActivityContainer(deletedTag){

		var deleteId = deletedTag.id.split("_")[1];
		var form = document.getElementById("confirmationForm");
		form.action = "deleteActivityContainer.action";
		$("#deletableId").val(deleteId);
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
					$('#'+topicId).attr('name', topicName);
				}
			});
		});
		
		
		$('.Topics').on('click', function(e){
			 
			$(this).parent().addClass('active');
			var id = $(this)[0].id.split('#')[1];
			$('.topiccontentcontainer').each(function(){ 
				$(this).css("display", "none");
			});
			$('#'+id).css("display", "block");
			e.preventDefault(); // cancel the link itself
		});
		

		$(".goBack").on("click",function(e) {
			e.preventDefault(); // cancel the link itself
			$("#editForm").attr('action', this.href);
			$("#editForm").submit();
		  });
		$(".nav li").click(function() {
			$(".nav li").removeClass('active');
			$(this).addClass('active');
			$('.nav-tabs a:first').tab('show')
		 
		}); 
		

		
		$('.nav-tabs li:first-child a').tab('show');  

	});
</script>

</head>
<body>

	<%@ include file="header.jsp"%>

	<div class="container">
		<div class="jumbotron" style="">
			<h1>My Topics</h1>
		</div>
	</div>


	<div class="container-fluid bg-3 text-left">

		<div class="row">
			<div class="col-sm-12">



				<!-- Making changes here -->
				<div class="container">
					<ul class="nav nav-pills nav-justified">
						<c:choose>
							<c:when test="${fn:length(topics)>0}">
								<c:forEach items="${topics}" var="topic" varStatus="topicNo">

									<li role="presentation"><a href="#"
										id="#${topic.topicName}" class="Topics" data-toggle="tab">
											${topic.topicName} </a></li>

								</c:forEach>
							</c:when>

						</c:choose>
					</ul>

					</br> </br>

					<div class="tab-content" class="tab-pane fade in active">
						<c:forEach items="${topics}" var="topic" varStatus="topicNo">

							<div id="${topic.topicName}" class="topiccontentcontainer"
								style="display: none">
								<%-- <p>${topic}</p> --%>
								<table class="table table-striped table-bordered">
									
									<c:if test="${fn:length(topic.activityContainers) > 0}">
									<tr>
										<th>Sr. No</th>
										<th>Activities Assigned</th>
										<th>Progress</th>
									</tr>
									</c:if>
									<c:if test="${fn:length(topic.activityContainers) == 0}">
										<h2 style="font-size: 200%;margin-left: 50px;">No Activities Assigned Currently</h2>
									</c:if>
									<tbody>



										<c:forEach items="${topic.activityContainers}"
											var="activityContainer" varStatus="currCount">
											
											<tr>
												<td><h6></h6></td>
												<td>
												<c:if
														test="${fn:length(activityContainer.activities) > 0}">
														
														<a
															id="${topic.id}_${activityContainer.activityContainerId}"
															class="btn ${currCount.index+1 <= topic.completedActContainers+1?"
															btn-danger":"btn-danger disabled"}" role="button">
															${activityContainer.containerName} </a>

													</c:if> <c:if
														test="${fn:length(activityContainer.activities) == 0}">
														<a
															id="${topic.id}_${activityContainer.activityContainerId}"
															class="btn ${currCount.index <= topic.completedActContainers+1?"
															btn-danger disabled":"btn-danger disabled"}" role="button">
															${activityContainer.containerName} </a>
													</c:if></td>
												<td>
												<c:if test="${fn:length(activityContainer.activities) == 0}">
														<p>No activities inside this activity container</p>
												</c:if>
												<c:if test="${fn:length(activityContainer.activities) > 0}">
														

														<div class="progress-bar progress-bar-success"
															role="progressbar"
															style="width: ${topic.progress}%;color:black">${topic.progress}
															% Complete</div>
													</c:if>

												</td>
											</tr>
										</c:forEach>






									</tbody>


								</table>

							</div>
						</c:forEach>

					</div>

				</div>

				<form id=form method="post" action="getActivitypage.action">
					<input type="hidden" id=actcon name=actcon />
				</form>
				<script>
		$(".btn").on("click", function() {
			$("#actcon").val(this.id);
			$("#form").submit();
		})
	</script>
				<%@ include file="footer.jsp"%>
				<!-- jQuery -->
				<script src="js/jquery.js"></script>

				<!-- Bootstrap Core JavaScript -->
				<script src="js/bootstrap.min.js"></script>
</body>
</html>
