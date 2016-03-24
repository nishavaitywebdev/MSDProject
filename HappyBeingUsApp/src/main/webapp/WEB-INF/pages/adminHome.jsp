<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Admin Home Page</title>

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
/* Remove the navbar's default margin-bottom and rounded borders */
.navbar {
	margin-bottom: 0;
	border-radius: 0;
}

/* Add a gray background color and some padding to the footer */
footer {
	background-color: #f2f2f2;
	padding: 25px;
}

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
</style>


<script type="text/javascript">
	function editContainer(id) {

		var form = document.getElementById("editForm");
		form.action = "editActivityContainer.action";
		form.children.namedItem("id").value = id;
		form.submit();
	}
	
	function renameTopic(button){
		
		var topicName = button.name;
		var topicId = button.id;
		
		$('#renameTopic input[name=renameTopicName]').val(topicName);
		$('#renameTopic input[name=renameTopicId]').val(topicId);
	}
	
	

	$(document).ready(function() {

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
	            	$("#topic_name_"+topicId)[0].innerHTML=topicName;
	            	
	            }
	        });
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
				<a class="navbar-brand" href="#">Admin</a>
			</div>
			<div class="collapse navbar-collapse" id="myNavbar">
				<ul class="nav navbar-nav">
					<li class="active"><a href="#">Topics and Blocks</a></li>
					<li><a href="#">Add New Admin</a></li>
					<li><a href="#">Detect Inactive Users</a></li>
					<li><a href="#">Statistics</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="#"><span class="glyphicon glyphicon-log-in"></span>
							Logout</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<div>
		<div class="jumbotron">
			<div class="container text-center">
				<h1>Topics and Blocks</h1>
				<p>Add-Remove-Edit Topics and Blocks, all at one place.</p>
			</div>
		</div>
	</div>

	<div class="container-fluid bg-3 text-left">

		<div class="row">
			<div class="col-sm-8">
				<c:choose>
					<c:when test="${fn:length(topics)>0}">
						<c:forEach items="${topics}" var="topic" varStatus="topicNo">
							<div class="jumbotron">
								<div class="topic_holder">
									<h2>
										<span
											class="topic_name ${topicNo.index+1 == 1?'':'collapsed'}"
											data-toggle="collapse" data-target="#container_for_${topic.id}"
											id="topic_name_${topic.id}">${topic.topicName}</span>

										<button type="button" class="btn btn-success"
											data-toggle="modal" data-target="#renameTopic"
											id="${topic.id}" name="${topic.topicName}"
											onclick="renameTopic(this)">Rename</button>
										<a href="#" class="btn btn-danger" role="button">Delete</a>
									</h2>
								</div>
								<div
									class="panel-collapse collapse ${topicNo.index+1 == 1?'in':''}"
									id="container_for_${topic.id}">
									<table class="table table-hover">
										<thead>
											<tr>
												<th></th>
												<th></th>
												<th></th>
												<th>Last Date Modified</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${topic.activityContainers}"
												var="activityContainer">
												<tr>
													<td><a href="#" class="btn btn-primary" role="button">${activityContainer.containerName}</a></td>
													<td><a href="#" class="btn btn-success" role="button"
														id="${activityContainer.activityContainerId}"
														onclick="editContainer(id)">Edit</a></td>
													<td><a href="#" class="btn btn-danger" role="button">Delete</a></td>
													<td>3/9/2016</td>
												</tr>
											</c:forEach>
											<tr>
												<td></td>
												<td></td>
												<td><a href="#" class="btn btn-warning" role="button">Add
														New Activity </a></td>
											</tr>
										</tbody>

									</table>
								</div>
							</div>
						</c:forEach>
					</c:when>
					<c:otherwise>
					<div class="jumbotron">
						<h2>No topics available right now. You might want to add topics first.</h2>
					</div>
					</c:otherwise>
				</c:choose>
			</div>
			<!-- 			<div class="col-sm-4"></div> -->
				<div class="modal fade" id="renameTopic" role="dialog">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h4 class="modal-title">Rename Topic</h4>
							</div>
							<div class="modal-body">
								<input type="text" class="form-control" id="renameTopicName"  name="renameTopicName" placeholder="Enter new topic name" />
								<input type="hidden" name="renameTopicId" id="renameTopicId" />
							</div>
							<div class="modal-footer">
								<input type="button" id="changeTopicName" class="btn btn-success" role="button" value="Change Name!"/>
							</div>
						</div>
					</div>
				</div>
<!-- 			Loading image Under progress -->
		<div id="loadingDiv" class="modal">
		<img alt="loading" src="Images/loading.gif">
		</div>
		</div>
	</div>
	<form name="editForm" id="editForm" action="#" method="post">
		<input type="hidden" id="id" name="id" value="" />
	</form>
	<div class="container-fluid bg-3 text-right">

		<div class="row">
			<div class="col-sm-8">
				<a href="#" class="btn btn-warning" role="button">Add New Topic</a>

			</div>
		</div>
	</div>
	<footer class="container-fluid text-center">
		<p>Designed By Group 11 3/9/2016</p>
	</footer>
</body>
</html>
