<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Admin NEW Activity </title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
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
            min-width:auto;
            width:100%;
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
            filter: alpha(opacity=0);
            opacity: 0;
            outline: none;
            background: white;
            cursor: inherit;
            display: block;
            }
</style> 

<script>
	$(document).ready(function() {
// 		dropdown for all the templates.
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
	
//		mcq template, update checkbox value with the input of user
	$(document).on('change', '#mcqOptions .option', function() {
		var num = this.id.split('_')[1];
		$('#mcqOptions #checkBox_'+num).val(this.name);
	});
	

//		adding more options inside the mcq template
	$(document).on('click', '#mcqMoreOptions', function() {
		var maxVal = $('#mcqMaxOptions').val();
		var nxtVal = parseInt(maxVal)+1;
		$('#mcqOptions').append(
				'<div class="mcqOption" id="mcqOption_'+nxtVal+'"  class="form-group"> '
					+'<input type="Checkbox" name="correctAnswer" class="chkbx" id="checkBox_'+nxtVal+'" /> ' 
					+'<input class="option  form-control" type="text" name="option_'+nxtVal+'" id="option_'+nxtVal+'" placeholder="Content for this choice" required/> '
					+'<button class="removeOption btn btn-primary btn_lg" id="removeOption_'+nxtVal+'" type="button">Remove</button><br> ' 
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
	
// 	Image upload messages
	function imageUploadMsg() {
	    var fileHolder = document.getElementById("imageFile");
	    var msg = "";
	    if ('files' in fileHolder) {
	        if (fileHolder.files.length == 0) {
	            msg = "Browse from computer";
	        } else {
	            var file = fileHolder.files[0];
	            if ('name' in file) {
	                msg += "File Name: " + file.name + "<br>";
	            }
	            if ('size' in file) {
	                msg += "File Size: " + file.size + " bytes <br>";
	            }
	        }
	    } else {
	        if (fileHolder.value == "") {
	            msg += "Browse from computer";
	        } else {
	            msg += "The files property is not supported by your browser!";
	            msg += "<br>The path of the selected file: " + fileHolder.value;
	            // If the browser does not support the files property, it will return the path of the selected file instead.
	        }
	    }
	    document.getElementById("imageUploadMsg").innerHTML = msg;
	}

// 	Video upload messages
	function videoUploadMsg() {
	    var fileHolder = document.getElementById("videoFile");
	    var msg = "";
	    if ('files' in fileHolder) {
	        if (fileHolder.files.length == 0) {
	            msg = "Browse from computer";
	        } else {
	            var file = fileHolder.files[0];
	            if ('name' in file) {
	                msg += "File Name: " + file.name + "<br>";
	            }
	            if ('size' in file) {
	                msg += "File Size: " + file.size + " bytes <br>";
	            }
	        }
	    } else {
	        if (fileHolder.value == "") {
	            msg += "Browse from computer";
	        } else {
	            msg += "The files property is not supported by your browser!";
	            msg += "<br>The path of the selected file: " + fileHolder.value;
	            // If the browser does not support the files property, it will return the path of the selected file instead.
	        }
	    }
	    document.getElementById("videoUploadMsg").innerHTML = msg;
	}
	

// 	Flip Card 1 upload messages
	function card1UploadMsg() {
	    var fileHolder = document.getElementById("card1File");
	    var msg = "";
	    if ('files' in fileHolder) {
	        if (fileHolder.files.length == 0) {
	            msg = "Browse from computer";
	        } else {
	            var file = fileHolder.files[0];
	            if ('name' in file) {
	                msg += "File Name: " + file.name + "<br>";
	            }
	            if ('size' in file) {
	                msg += "File Size: " + file.size + " bytes <br>";
	            }
	        }
	    } else {
	        if (fileHolder.value == "") {
	            msg += "Browse from computer";
	        } else {
	            msg += "The files property is not supported by your browser!";
	            msg += "<br>The path of the selected file: " + fileHolder.value;
	            // If the browser does not support the files property, it will return the path of the selected file instead.
	        }
	    }
	    document.getElementById("card1UploadMsg").innerHTML = msg;
	}

// 	Flip Card 2 upload messages
	function card2UploadMsg() {
	    var fileHolder = document.getElementById("card2File");
	    var msg = "";
	    if ('files' in fileHolder) {
	        if (fileHolder.files.length == 0) {
	            msg = "Browse from computer";
	        } else {
	            var file = fileHolder.files[0];
	            if ('name' in file) {
	                msg += "File Name: " + file.name + "<br>";
	            }
	            if ('size' in file) {
	                msg += "File Size: " + file.size + " bytes <br>";
	            }
	        }
	    } else {
	        if (fileHolder.value == "") {
	            msg += "Browse from computer";
	        } else {
	            msg += "The files property is not supported by your browser!";
	            msg += "<br>The path of the selected file: " + fileHolder.value;
	            // If the browser does not support the files property, it will return the path of the selected file instead.
	        }
	    }
	    document.getElementById("card2UploadMsg").innerHTML = msg;
	}

// 	Flip Card 3 upload messages
	function card3UploadMsg() {
	    var fileHolder = document.getElementById("card3File");
	    var msg = "";
	    if ('files' in fileHolder) {
	        if (fileHolder.files.length == 0) {
	            msg = "Browse from computer";
	        } else {
	            var file = fileHolder.files[0];
	            if ('name' in file) {
	                msg += "File Name: " + file.name + "<br>";
	            }
	            if ('size' in file) {
	                msg += "File Size: " + file.size + " bytes <br>";
	            }
	        }
	    } else {
	        if (fileHolder.value == "") {
	            msg += "Browse from computer";
	        } else {
	            msg += "The files property is not supported by your browser!";
	            msg += "<br>The path of the selected file: " + fileHolder.value;
	            // If the browser does not support the files property, it will return the path of the selected file instead.
	        }
	    }
	    document.getElementById("card3UploadMsg").innerHTML = msg;
	}
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
                <a class="navbar-brand" href="#">Admin Home</a>
            </div>
            <div class="collapse navbar-collapse" id="myNavbar">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="#"><span class="glyphicon glyphicon-log-in"></span>
                            Logout</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="jumbotron">
        <div class="container text-center">
            <h2>Create Activity</h2>
            <p> Create   Activities here: <p>
        </div>
    </div>

    <select name="activityTemplate" id="activityTemplate">
        <option value="">Select The Activity...</option>
        <c:forEach items="${activityTemplates}" var="template">
			<option value="template_${template.id}">${template.templateName}</option>
		</c:forEach>
    <select>
    
    <c:forEach items="${activityTemplates}" var="template">
    <c:if test="${template.id==1}">
   <form:form action="addActivity.action" method="post" name="mcqForm" id="mcqForm" modelAttribute="activity" enctype="multipart/form-data">
	    <div id="template_${template.id}" style="display: none">
	
	        <div class="container">
	        <h2></h2>
	            <div class="form-group">
	                <label for="option1">Image Link</label>
	                <p id="videoUploadMsg">Browse from computer</p>
	                <span class="btn btn-default btn-file2">Browse<input type="file" id="videoFile" name="uploadFile" onchange="videoUploadMsg()" required></span>
	            </div>          
	
	
	            <div class="form-group">
	                <label for="comment">Question Content:</label>
	                <form:textarea name="Question" path="activityText" class="form-control" rows="5" id="commentImage" placeholder="Enter Question Contents Here." required="true"></form:textarea>
	            </div>
	            
	            <div class="form-group">
	                <label for="comment">Answer Content:</label>
	                <textarea  class="form-control" rows="5" name="idealAnswer" placeholder="Enter Answer Here." required></textarea>
	            </div>
	
	            <div class="container text-right">
					<button type="submit" class="btn btn-primary btn_lg">Add</button>
	            </div> 
	    	</div>
	        <tr><td><br/></td></tr>
	        <div class="jumbotron">
	           <footer class="container-fluid text-right">
	
	           </footer>
	        </div>
	    </div>
	    <form:input type="hidden" path="activityType.id" />
		<form:input type="hidden" path="activityTemplate.id" value="${template.id}" />
		<form:input type="hidden" path="activityContainer.activityContainerId" />
    </form:form>
    </c:if>
    <c:if test="${template.id==2}">
    <form:form action="addActivity.action" method="post" name="mcqForm" id="mcqForm" modelAttribute="activity" enctype="multipart/form-data">
	    <div id="template_${template.id}" style="display: none">
	
	        <div class="container">
	        <h2></h2>
	            <div class="form-group">
	                <label for="option1">Image Link</label>
	                <p id="imageUploadMsg">Browse from computer</p>
	                <span class="btn btn-default btn-file2">Browse<input type="file" id="imageFile" name="uploadFile" onchange="imageUploadMsg()" required></span>
	            </div>          
	
	
	            <div class="form-group">
	                <label for="comment">Question Content:</label>
	                <form:textarea name="Question" path="activityText" class="form-control" rows="5" id="commentImage" placeholder="Enter Question Contents Here." required="true"></form:textarea>
	            </div>
	            
	            <div class="form-group">
	                <label for="comment">Answer Content:</label>
	                <textarea  class="form-control" rows="5" name="idealAnswer" placeholder="Enter Answer Here." required></textarea>
	            </div>
	
	            <div class="container text-right">
					<button type="submit" class="btn btn-primary btn_lg">Add</button>
	            </div> 
	    	</div>
	        <tr><td><br/></td></tr>
	        <div class="jumbotron">
	           <footer class="container-fluid text-right">
	
	           </footer>
	        </div>
	    </div>
	    <form:input type="hidden" path="activityType.id" />
		<form:input type="hidden" path="activityTemplate.id" value="${template.id}" />
		<form:input type="hidden" path="activityContainer.activityContainerId" />
    </form:form>
    </c:if>
    <c:if test="${template.id==3}">
	<form:form action="addActivity.action" method="post" name="mcqForm" id="mcqForm" modelAttribute="activity" enctype="multipart/form-data">
		<div id="template_${template.id}" style="display: none">
	
	        <div class="container">
	        <h2></h2>
	            <div class="form-group">
	                <label for="comment">Question Content:</label>
	                <form:textarea name="Question" path="activityText" class="form-control" rows="5" id="commentMCQ" placeholder="Enter Question Contents Here."></form:textarea>
	            </div>
	            
	            <div id="mcqOptions">
	            	
	            	<div class="mcqOption" id="mcqOption_1" class="form-group">
						<input type="Checkbox" name="correctAnswer" class="chkbx" id="checkBox_1" /> 
						<input class="option form-control" type="text" name="option_1" id="option_1"  placeholder="Content for this choice" required/>
						<button class="removeOption btn btn-primary btn_lg" id="removeOption_1" type="button">Remove</button><br> 
					</div>
	            	
	            	<div class="mcqOption" id="mcqOption_2" class="form-group">
						<input type="Checkbox" name="correctAnswer" class="chkbx" id="checkBox_2" /> 
						<input class="option form-control" type="text" name="option_2" id="option_2"  placeholder="Content for this choice" required/>
						<button class="removeOption btn btn-primary btn_lg" id="removeOption_2" type="button">Remove</button><br> 
					</div>
	            	
	            	<div class="mcqOption" id="mcqOption_3" class="form-group">
						<input type="Checkbox" name="correctAnswer" class="chkbx" id="checkBox_3" /> 
						<input class="option form-control" type="text" name="option_3" id="option_3"  placeholder="Content for this choice" required/>
						<button class="removeOption btn btn-primary btn_lg" id="removeOption_3" type="button">Remove</button><br> 
					</div>
	            </div>
	    	</div>
	        <tr><td><br/></td></tr>
	        <div class="container text-left">
				<button id="mcqMoreOptions" type="button" class="btn btn-primary btn_lg">Add more options</button>
            </div> 
	        <div class="container text-right">
				<button type="submit" class="btn btn-primary btn_lg">Add</button>
            </div> 
	        <div class="jumbotron">
	           <footer class="container-fluid text-right">
	
	           </footer>
	        </div>
			<form:input type="hidden" path="activityType.id" />
			<form:input type="hidden" path="activityTemplate.id"
				value="${template.id}" />
			<form:input type="hidden" path="activityContainer.activityContainerId" />
	
	    </div>
	</form:form>
	<input type="hidden" id="mcqMaxOptions" value="3"/>    
    </c:if>
    
    <c:if test="${template.id==4}">
    	<div id="content_1" class="inv"><div class="container">
	        <h2></h2>
	        <form role="form" action=# method="post">           
	            
	            <div class="form-group">
	                <label for="comment">Information Content:</label>
	                <textarea  class="form-control" rows="5" id="comment" placeholder="Enter Information Content Here."></textarea>
	            </div>
	
	            <input type="submit" class="btn btn-primary btn_lg" value="submit"></input>
	        </form>
	    </div>
	        <tr><td><br/></td></tr>
	        <div class="jumbotron">
	           <footer class="container-fluid text-right">
	
	           </footer>
	        </div>
	    </div>
    </c:if>
    <c:if test="${template.id==5}">
    <form:form action="addActivity.action" method="post" name="mcqForm" id="mcqForm" modelAttribute="activity" enctype="multipart/form-data">
		<div id="template_${template.id}" style="display: none">
	
	        <div class="container">
	        <h2></h2>
	            <div class="form-group">
	                <label for="comment">Question Content:</label>
	                <form:textarea name="Question" path="activityText" class="form-control" rows="5" placeholder="Enter Question Contents Here."></form:textarea>
	            </div>
	            
	            <div class="form-group">
				    <h1> Card 1 </h1>
				    <label for="comment">Front Content:</label>
				    <textarea  class="form-control" rows="5" name="card1Front" placeholder="Enter Front Contents Here." required></textarea>
				    <label for="comment">Back Content:</label>
				    <textarea  class="form-control" rows="5" name="card2Back" placeholder="Enter Back Contents Here."></textarea>
				    
				    <label for="card1">Image Link</label>
	                <p id="card1UploadMsg">Browse from computer</p>
	                <span class="btn btn-default btn-file2">Browse<input type="file" id="card1File" name="card1File" onchange="card1UploadMsg()"></span>
				</div>
				<tr><td><br/></td></tr>
				<div class="form-group">
                    <h1> Card 2 </h1>
                    <label for="comment">Front Content:</label>
                    <textarea  class="form-control" rows="5" name="card3Front" placeholder="Enter Front Contents Here." required></textarea>
                    <label for="comment">Back Content:</label>
                    <textarea  class="form-control" rows="5" name="card4Back" placeholder="Enter Back Contents Here."></textarea>
                
                	<label for="card2">Image Link</label>
	                <p id="card2UploadMsg">Browse from computer</p>
	                <span class="btn btn-default btn-file2">Browse<input type="file" id="card2File" name="card2File" onchange="card2UploadMsg()"></span>
				</div>
                <tr><td><br/></td></tr>
				<div class="form-group">
                    <h1> Card 3 </h1>
                    <label for="comment">Front Content:</label>
                    <textarea  class="form-control" rows="5" name="card5Front" placeholder="Enter Front Contents Here." required></textarea>
                    <label for="comment">Back Content:</label>
                    <textarea  class="form-control" rows="5" name="card6Back"id="comment" placeholder="Enter Back Contents Here."></textarea>
                    
                	<label for="card3">Image Link</label>
	                <p id="card3UploadMsg">Browse from computer</p>
	                <span class="btn btn-default btn-file2">Browse<input type="file" id="card3File" name="card3File" onchange="card3UploadMsg()"></span>
                </div>
                <tr><td><br/></td></tr>

                <div class="container text-right">
					<button type="submit" class="btn btn-primary btn_lg">Add</button>
                </div> 
	    	</div>
	        <tr><td><br/></td></tr>
	        <div class="jumbotron">
	           <footer class="container-fluid text-right">
	
	           </footer>
	        </div>
			<form:input type="hidden" path="activityType.id" />
			<form:input type="hidden" path="activityTemplate.id"
				value="${template.id}" />
			<form:input type="hidden" path="activityContainer.activityContainerId" />
	
	    </div>
	</form:form>
    </c:if>
    </c:forEach>
    <tr><td><br/></td></tr>
</body>
</html>
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

@import
	url(http://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700)
	;

.animation {
	-webkit-transition: all 0.3s ease;
	-moz-transition: all 0.3s ease;
	-ms-transition: all 0.3s ease;
	-o-transition: all 0.3s ease;
	transition: all 0.3s ease;
}

.pageTitle sup {
	font-size: .6em;
	color: #333;
}

.well {
	padding: 3%;
	margin: 20px auto;
	border: none;
	text-align: center;
}

.well p {
	font-weight: 300;
}

.content p {
	font-weight: 300;
}

.cardContainer {
	-webkit-transition: all .3s ease;
	-moz-transition: all .3s ease;
	-ms-transition: all .3s ease;
	transition: all .3s ease;
	/*depth of the elements */
	-webkit-perspective: 800px;
	-moz-perspective: 800px;
	-o-perspective: 800px;
	perspective: 800px;
	/*border: 1px solid #ff0000;*/
	padding-left: 1%;
}

.secondRow {
	margin-top: -1.4%
}

.card {
	width: 99%;
	height: 200px;
	cursor: pointer;
	/*transition effects */
	-webkit-transition: -webkit-transform 0.6s;
	-moz-transition: -moz-transform 0.6s;
	-o-transition: -o-transform 0.6s;
	transition: transform 0.6s;
	-webkit-transform-style: preserve-3d;
	-moz-transform-style: preserve-3d;
	-o-transform-style: preserve-3d;
	transform-style: preserve-3d;
}

.card.flipped {
	-webkit-transform: rotateY(180deg);
	-moz-transform: rotateY(180deg);
	-o-transform: rotateY(180deg);
	transform: rotateY(180deg);
}

.card.flipped: {
	
}

.card .front, .card .back {
	display: block;
	height: 100%;
	width: 100%;
	line-height: 60px;
	color: white;
	text-align: center;
	font-size: 4em;
	position: absolute;
	-webkit-backface-visibility: hidden;
	-moz-backface-visibility: hidden;
	-o-backface-visibility: hidden;
	backface-visibility: hidden;
	box-shadow: 3px 5px 20px 2px rgba(0, 0, 0, 0.25);
	-webkit-box-shadow: 0 2px 10px rgba(0, 0, 0, 0.16), 0 2px 5px
		rgba(0, 0, 0, 0.26);
	box-shadow: 0 2px 10px rgba(0, 0, 0, 0.16), 0 2px 5px
		rgba(0, 0, 0, 0.26);
}

.card .back {
	width: 100%;
	padding-left: 3%;
	padding-right: 3%;
	font-size: 16px;
	text-align: left;
	line-height: 25px;
}

.card .back {
	background: #03446A;
	-webkit-transform: rotateY(180deg);
	-moz-transform: rotateY(180deg);
	-o-transform: rotateY(180deg);
	transform: rotateY(180deg);
}

/*Colors for front and back applied here*/
.cardContainer:first-child .card .front {
	background: green;
}

.cardContainer:first-child .card .back {
	background: green;
}

.cardContainer:nth-child(2) .card .front {
	background: #2aa9e0;
}

.cardContainer:nth-child(2) .card .back {
	background: #2aa9e0;
}

.cardContainer:nth-child(3) .card .front {
	background: green;
}

.cardContainer:nth-child(3) .card .back {
	background: green;
}

.cardContainer:nth-child(4) .card .front {
	background: #D05800;
}

.cardContainer:nth-child(4) .card .back {
	background: #D05800;
}

h3.cardTitle {
	line-height: 1.2em;
	margin-top: 8%;
	font-weight: 600;
}

.content h3.cardTitle {
	margin-top: 0%;
}

.content {
	padding: 4%;
	font-weight: 100;
	text-align: left;
	font-weight: bold;
}

@media all and (max-width: 1000px) {
	h3.cardTitle {
		font-weight: 500;
	}
	.content p {
		margin-top: -15%;
		line-height: 1.2em;
	}
	.card {
		height: 175px;
	}
}

@media all and (max-width : 752px) {
	.secondRow {
		margin-top: -3%;
	}
	.cardContainer:nth-child(3), .cardContainer:nth-child(4) {
		margin-top: 3%;
	}
}

@media all and (max-width : 390px) {
	.card {
		width: 100%;
		height: 200px;
	}
	.secondRow {
		margin-top: -9%;
	}
	.well {
		padding: 1%;
	}
	.cardContainer:nth-child(3), .cardContainer:nth-child(4) {
		margin-top: 5%;
	}
	h3.cardTitle {
		font-weight: 300;
	}
	.content p {
		margin-top: -20%;
		line-height: 1.2em;
	}
	.cardWrapper {
		margin-left: 4%;
	}
}
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
</style>



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
				<a class="navbar-brand" href="#">HappyBeingUs</a>
			</div>
			<div class="collapse navbar-collapse" id="myNavbar">
				<ul class="nav navbar-nav">
					<li class="active"><a href="#">1</a></li>
					<li><a href="#">2</a></li>
					<li><a href="#">3</a></li>
					<li><a href="#">4</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="#"><span class="glyphicon glyphicon-log-in"></span>
							Logout</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="jumbotron">
		<div class="container text-center">
			<h1>${c_container.containerName}</h1>
			<p>${c_act.activityText }</p>
		</div>
	</div>
<body>

	<c:if test="${c_act.activityTemplate.id==1}">


		<c:forEach items="${answers}" var="answer">
			<c:if test="${answer.orderNo==1}">
				<video width="600" controls="">
					<source src="${answer.answerText}">
					<source src="${answer.answerText}">
					<ins>Your browser does not support the video tag.</ins>
				</video>
			</c:if>
			<div>${answer.answerText}</div>
		</c:forEach>


	</c:if>
	<c:if test="${c_act.activityTemplate.id==2}">


		<c:forEach items="${answers}" var="answer">
			<c:if test="${answer.orderNo==1}">
				<div class="col-sm-6">



					<img src="${answer.answerText}"
						class="img-responsive" alt="Image not present" width="600">





				</div>
			</c:if>
			<div>${answer.answerText}</div>
		</c:forEach>

	</c:if>


	<c:if test="${c_act.activityTemplate.id==3}">

		<form:form>
			<c:forEach items="${answers}" var="answer">
				<div class="radio">
					<label><input type="radio" name="scores"
						value="${answer.orderNo}">${answer.answerText}</label>
				</div>
			</c:forEach>
			<div class="modal-footer">
				<input class="btn btn-primary" type="submit" value="submit" />

			</div>

		</form:form>
	</c:if>
	<c:if test="${c_act.activityTemplate.id==4}">


		<c:forEach items="${answers}" var="answer">
			<div>
				<label>${answer.answerText}</label>
			</div>
		</c:forEach>



	</c:if>


	<c:if test="${c_act.activityTemplate.id ==5}">

		<div class="container">

			<div class="row">
				<tr>
					<td><br /></td>
				</tr>
				<tr>
					<td><br /></td>
				</tr>
			</div>

			<!-- first Row -->
			<div class="row">

				<div class="col-lg-2"></div>
				<c:forEach items="${answers}" var="answer">
					<div class="col-lg-4  cardContainer">
						<div class="card">
							<div class="front">
								<h3 class="cardTitle">Flip me!</h3>
							</div>
							<div class="back">
								<div class="content">
									<h3 class="cardTitle">${answer.answerText}</h3>
									<br />
									<p id="happy"></p>
								</div>
							</div>
						</div>
					</div>

				</c:forEach>
				<!-- row1 -->



				<div></div>
			</div>
			<!--cardWrapper Ends-->
			<!-- container -->
	</c:if>

	<script>
		$('.card').click(function() {
			$(this).toggleClass('flipped');
		});
	</script>

	<td><a id=1 class="btn ${c_act.orderNo != 1?"
		btn-primary":"btn-primary disabled"}" role="button"> previous
			activity</a></td>
	<td><a id=2 class="btn ${c_act.orderNo!=act_max?"
		btn-primary":"btn-primary disabled"}" role="button"> next activity</a></td>
	<td><a id=3 class="btn ${c_container.orderNo !=1 ?"
		btn-primary":"btn-primary disabled"}" role="button"> previous
			container</a></td>
	<td><a id=4 class="btn ${c_container.orderNo!= con_max?"
		btn-primary":"btn-primary disabled"}" role="button"> next
			container</a></td>

	<td style="width: 25px"></td>

	<form id=form method="post" action="reload.action">
		<input type="hidden" id=actcon name=actcon />
	</form>

	<script>
		$(".btn").on("click", function() {
			$("#actcon").val(this.id);
			$("#form").submit();
		})
	</script>



	<footer class="container-fluid text-center">
		<p>Designed By Group 11 3/21/2016</p>
	</footer>

</body>
</html>