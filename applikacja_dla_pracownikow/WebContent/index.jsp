<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="org.wwsis.worker.view.servlet.WebAppController"%>

<% 
	 WebAppController.getInstance().ifUserLoggedRedirectToWelcomePg(request, response);
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="css/styles.css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<title>Aplication for workers</title>
</head>
<script>

var numberOfFailded = 0;

function submit(){
	
	var userValue = $("#userInput").val();
	var passwordValue = $("#passInput").val();
	var userFieldName = "<%= WebAppController.USER_INPUT_NAME%>";
	var passwordFieldName = "<%= WebAppController.PASSWORD_INPUT_NAME %>";
	var emitJSON = {};
	emitJSON[userFieldName] = userValue;
	emitJSON[passwordFieldName] = passwordValue ;
	
	

	$.post('/login', emitJSON, 
		    function(returnedData){
		         if (returnedData === 'true') {
		        	 $(location).attr("href", "/Welcome");
		         } else {
		        	 alert("Zly login lub haslo");
		         }
		});
}

</script>
<body>
	<div class="background">
		<img class="background_pic" src="images/ny.jpg">
	</div>
	<div class="header">
		<h1>APLICATION FOR WORKERS</h1>
	</div>
	<div class="outer">
		<div class="middle">
			<div class="inner">
				<div class="login">
					<form action="/login" method="post">
						<input id="userInput" type="text" placeholder="Login" name="user">
						 <input id="passInput"
							type="password" placeholder="Password" name="password"> <br>
						<!--   <input type="submit" value="Sign In" disabled="disabled">-->
					</form>
					<button onClick="submit()">Submit</button>
					<p id="info"></p>
				</div>
			</div>
		</div>
	</div>
	<script>

	function refresh(){
		$(".outer").height($(window).height() - $(".header").outerHeight());
		  var imgProportion = $(".background_pic").height() /  $(".background_pic").width();
		  var windowsProportion = $(window).height() / $(window).width() ;
		  console.log(imgProportion+" vs "+windowsProportion);
		  if(imgProportion > windowsProportion){
		    $(".background_pic").css("max-width","100%");
		    $(".background_pic").css("max-height","none");
		  }else{
		    $(".background_pic").css("max-width","none");
		    $(".background_pic").css("max-height","100%");
		  }
	}

	refresh();
$(window).resize(refresh);

</script>

</body>
</html>
