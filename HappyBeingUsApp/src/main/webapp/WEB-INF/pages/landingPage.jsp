<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Landing page</title>

<!-- Bootstrap Core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="css/half-slider.css" rel="stylesheet">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

<style type="text/css">
.left-inner-addon {
	position: relative;
}

.left-inner-addon input {
	padding-left: 30px;
}

.left-inner-addon i {
	position: absolute;
	padding: 10px 12px;
	pointer-events: none;
}

.right-inner-addon {
	position: relative;
}

.right-inner-addon input {
	padding-right: 30px;
}

.right-inner-addon i {
	position: absolute;
	right: 0px;
	padding: 10px 12px;
	pointer-events: none;
}
</style>
<script>
	$(document).ready(function() {
		$("checkbox").click(function() {
			$("MGEmail").toggle();
		});
	});
</script>
</head>

<body>

	<!-- Navigation -->
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">Happy Being Us</a>
			</div>
			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li><a href="#">About</a></li>
					<li><a href="#">Activities</a></li>
					<li><a href="#">Contact</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right navbar-hover-pink">
					<li><a href="#SignUp" data-toggle="modal"><span
							class="glyphicon glyphicon-user"></span> Sign Up</a></li>
					<li><a href="#Login" data-toggle="modal"><span
							class="glyphicon glyphicon-log-in"></span> Login</a></li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container -->
	</nav>

	<!-- Half Page Image Background Carousel Header -->
	<header id="myCarousel" class="carousel slide">
		<!-- Indicators -->
		<ol class="carousel-indicators">
			<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
			<li data-target="#myCarousel" data-slide-to="1"></li>
			<li data-target="#myCarousel" data-slide-to="2"></li>
		</ol>

		<!-- Wrapper for Slides -->
		<div class="carousel-inner">
			<div class="item active">
				<!-- Set the first background image using inline CSS below. -->
				<div class="fill"
					style="background-image: url('http://placehold.it/1900x1080&amp;text=Slide One');"></div>
				<div class="carousel-caption">
					<h2>Caption 1</h2>
				</div>
			</div>
			<div class="item">
				<!-- Set the second background image using inline CSS below. -->
				<div class="fill"
					style="background-image: url('http://placehold.it/1900x1080&amp;text=Slide Two');"></div>
				<div class="carousel-caption">
					<h2>Caption 2</h2>
				</div>
			</div>
			<div class="item">
				<!-- Set the third background image using inline CSS below. -->
				<div class="fill"
					style="background-image: url('http://placehold.it/1900x1080&amp;text=Slide Three');"></div>
				<div class="carousel-caption">
					<h2>Caption 3</h2>
				</div>
			</div>
		</div>

		<!-- Controls -->
		<a class="left carousel-control" href="#myCarousel" data-slide="prev">
			<span class="icon-prev"></span>
		</a> <a class="right carousel-control" href="#myCarousel"
			data-slide="next"> <span class="icon-next"></span>
		</a>

	</header>

	<!-- Page Content -->
	<div class="container">

		<div class="row">
			<div class="col-lg-12">
				<img src="Images/HomePageImage.png" style="margin-left: 150px" />
			</div>
		</div>

		<hr>

		<!-- Footer -->
		<footer>
			<div class="row" style="background-color: #FF5733; height: 20px;"></div>
			<div class="row">
				<div class="col-lg-12">
					<p>Copyright &copy; HappyBeingUs.com 2016</p>
					<img src="Images/Logo.png" align="right">
				</div>
			</div>
			<!-- /.row -->
		</footer>

	</div>

	<div class="modal fade" id="Login" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content col-lg-10">
				<form action="Login.nisha" method="post">
					<div class="modal-header">
						<h4>Login</h4>
					</div>
					<div class="modal-body">
						<div class="form-group left-inner-addon">
							<div class="col-lg-8">
								<i class="glyphicon glyphicon-user"></i> <input type="text"
									class="form-control" name="userName" placeholder="User name"
									required>
							</div>
						</div>
						<br></br>
						<div class="form-group left-inner-addon">
							<div class="col-lg-8">
								<i class="glyphicon glyphicon-lock"></i> <input type="password"
									class="form-control" name="password" placeholder="password"
									required>
							</div>
						</div>
						<br></br>

						<div class="col-lg-6">
							<a href="#ForgotPassword">Forgot Password</a>
						</div>
						<br></br>
					</div>
					<div class="modal-footer">
						<a class="btn btn-default" data-dismiss="modal">Cancel</a>
						<button class="btn btn-primary" type="submit">Login</button>

					</div>
				</form>
			</div>
		</div>
	</div>

	<div class="modal fade" id="SignUp" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content col-lg-10">

				<form:form action="signUp.action" method="post"
					modelAttribute="daughterRegistration">
					<div class="modal-header">
						<h4>Sign Up</h4>
					</div>

					<div class="modal-body">
						<div class="form-group">
							<div class="col-lg-5">
								<form:input type="text" path="daughter.firstName"
									class="form-control" name="Firstname" placeholder="First name"
									required="true" />
							</div>
							<div class="col-lg-5">
								<form:input type="text" path="daughter.lastName"
									class="form-control" name="Lastname" placeholder="Last Name"
									required="true" />
							</div>
							<br></br>
						</div>
						<div class="form-group left-inner-addon ">
							<div class="col-lg-10 ">
								<i class="glyphicon glyphicon-envelope"></i>
								<form:input type="email" path="daughter.email"
									class="form-control" name="emailID" placeholder="Email"
									required="true" />
							</div>
							<br></br>
						</div>
						<div class="form-group left-inner-addon ">
							<div class="col-lg-7 ">
								<i class="glyphicon glyphicon-calendar"></i>
								<form:input type="date" path="daughter.birthdate"
									class="form-control" name="birthDate" placeholder="Birth Date"
									required="true" />
							</div>
							<br></br>
						</div>
						<div id="MGEmail" class="form-group left-inner-addon">
							<div class="col-lg-10">
								<i class="glyphicon glyphicon-envelope"></i>
								<form:input type="email" path="daughter.mother.email"
									class="form-control" id="MomEmailID" name="momEmail"
									placeholder="EmailID of Mommy" required="true" />
							</div>
							<br></br>
						</div>
						<div class="form-group left-inner-addon">
							<div class="col-lg-10">
								<i class="glyphicon glyphicon-user"></i>
								<form:input type="text" path="username" class="form-control"
									name="userName" placeholder="User name" required="true" />
							</div>
							<br></br>
						</div>
						<div class="form-group left-inner-addon">
							<div class="col-lg-10">
								<i class="glyphicon glyphicon-lock"></i>
								<form:input type="password" path="password" class="form-control"
									name="password" placeholder="password" required="true" />
							</div>
						</div>
						<br></br>
						<div class="modal-footer">
							<a class="btn btn-default" data-dismiss="modal">Cancel</a> <input
								class="btn btn-primary" type="submit" value="Register" />

						</div>
					</div>
				</form:form>

				<!-- form action="SignUp.nisha" method="post">
					<div class="modal-header">
						<h4>Sign Up</h4>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<div class="col-lg-5">
								<input type="text" class="form-control" name="Firstname"
									placeholder="First name" required>
							</div>
							<div class="col-lg-5">
								<input type="text" class="form-control" name="Lastname"
									placeholder="Last Name" required>
							</div>
						</div>
						<br></br>
						<div class="form-group left-inner-addon ">
							<div class="col-lg-10 ">
								<i class="glyphicon glyphicon-envelope"></i> <input type="email"
									class="form-control" name="emailID" placeholder="Email"
									required>
							</div>
						</div>
						<br></br>
						<div class="form-group left-inner-addon ">
							<div class="col-lg-7 ">
								<i class="glyphicon glyphicon-calendar"></i> <input type="date"
									class="form-control" name="birthDate" placeholder="Birth Date"
									required> <input type="checkbox" name="check1" />
								Login as Mother
							</div>
						</div>
						<br></br>
						<div id="MGEmail" class="form-group left-inner-addon">
							<div class="col-lg-10 ">
								<i class="glyphicon glyphicon-envelope"></i> <input type="email"
									class="form-control" name="emailIDdaughter"
									placeholder="Email of Daughter" required>
							</div>
						</div>
						<br></br>
						<div id="MGEmail" class="form-group left-inner-addon">
							<div class="col-lg-10">
								<i class="glyphicon glyphicon-envelope"></i> <input type="email"
									class="form-control" id="MomEmailID"
									placeholder="EmailID of Mommy" required>
							</div>
						</div>
						<br></br>
						<div class="form-group left-inner-addon">
							<div class="col-lg-10">
								<i class="glyphicon glyphicon-user"></i> <input type="text"
									class="form-control" name="userName" placeholder="User name"
									required>
							</div>
						</div>
						<br></br>
						<div class="form-group left-inner-addon">
							<div class="col-lg-10">
								<i class="glyphicon glyphicon-lock"></i> <input type="password"
									class="form-control" name="password" placeholder="password"
									required>
							</div>
						</div>
					</div>
					<br></br>
					<div class="modal-footer">
						<a class="btn btn-default" data-dismiss="modal">Cancel</a>
						<button class="btn btn-primary" type="submit">Register</button>

					</div>
				</form-->
			</div>
		</div>
	</div>

	<!-- /.container -->

	<!-- jQuery -->
	<script src="js/jquery.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="js/bootstrap.min.js"></script>

	<!-- Script to Activate the Carousel -->
	<script>
		$('.carousel').carousel({
			interval : 5000
		//changes the speed
		})
	</script>

</body>

</html>
