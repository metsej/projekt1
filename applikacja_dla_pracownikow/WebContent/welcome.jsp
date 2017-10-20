<%@page import="org.wwsis.worker.view.servlet.InternetSession"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="org.wwsis.worker.view.servlet.WebAppController"%>
<%@ page import="org.wwsis.worker.controller.AppController"%>
<%@ page import="java.util.List"%>
<%@ page import="org.wwsis.worker.data.Worker"%>

<%

WebAppController wContrl =  WebAppController.getInstance();

InternetSession currentSession = wContrl.getCurrentInternetSession(request);

	if (currentSession == null) {
		wContrl.ifUserNotLoggedRedirectToIndexPg(request, response);
	}

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/styles.css" />
<title>Aplikacja dla pracowników</title>
</head>
<body>

	<div class="header">
		<h2>
			Welcome
			<%=currentSession.getUserLogin()%>
		</h2>
	</div>

</body>
</html>