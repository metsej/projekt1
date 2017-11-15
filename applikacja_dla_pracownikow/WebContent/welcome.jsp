<%@page import="org.wwsis.worker.view.servlet.InternetSession"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="org.wwsis.worker.view.servlet.WebAppController"%>
<%@ page import="org.wwsis.worker.controller.AppController"%>
<%@ page import="java.util.List"%>
<%@ page import="org.wwsis.worker.data.Worker"%>

<%
	WebAppController wContrl = WebAppController.getInstance();
	wContrl.checkIfLogged(request, response);
	
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/styles.css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="javascript/welcomePanelFunctions.js"></script>
<title>Aplikacja dla pracowników</title>
</head>
<script>

	var properties = {};
	properties["upperMargin"] = 80;
	properties["lowerMargin"] = 80;
	properties["leftMargin"] = 60;
	properties["rightMargin"] = 0;
	properties["elemName"] = "BarChart";
	properties["yAxisUnit"] = "mm";
	properties["xAxisUnit"] = "";

	$(document).ready(function() {
		$.get("/dayReport", function(data) {
			drawDayReportTable(data);
			drawChart(properties, data);

		});
	});

	$(window).resize(function() {
		$.get("/dayReport", function(data) {
			drawDayReportTable(data);
			drawChart(properties, data);

		});
	});
</script>

<body>

	<div class="header">
		<h2>Welcome</h2>
	</div>

	<div class="displayData">

		<div id="recordsDiv">
			<table id="records_table" border='1'>
			</table>
		</div>

		<div id="BarChart"></div>
	</div>

</body>

</html>
