<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>404 | Finance Tracker</title>
<!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	 <!-- Bootstrap 3.3.7 -->
	  <link href="<c:url value="/css/bootstrap.min.css" />" rel="stylesheet" type="text/css">
	  <!-- Font Awesome -->
	  <link href="<c:url value="/css/font-awesome.min.css" />" rel="stylesheet" type="text/css">
	  <!-- Ionicons -->
	  <link href="<c:url value="/css/ionicons.min.css" />" rel="stylesheet" type="text/css">
	  <!-- Theme style -->
	  <link href="<c:url value="/css/AdminLTE.min.css" />" rel="stylesheet" type="text/css">
	  <!-- iCheck -->
	  <link href="<c:url value="/css/_all-skins.min.css" />" rel="stylesheet" type="text/css">
	<!-- Google Font -->
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
</head>
<body>
	<div>
		<section class="content">
	      <div class="error-page">
	        <h1 class="headline text-red">404</h1>
	
	        <div class="error-content">
	          <h3><i class="fa fa-warning text-red"></i> Oops! Something went wrong.</h3>
	          <h4>Either you can't type or we broke something.</h4>
			  <h4>The page you requested could not be found.</h4>
	          <p>
	            We will work on fixing that right away. Meanwhile, you may 
	            <br>
           		<h4><a href="<c:url value="/main"></c:url>"><i class="ion ion-android-arrow-back"></i> Return to the dashboard</a></h4>
           		<h4><a href="<c:url value="/"></c:url>"><i class="ion ion-person"></i> Start over</a></h4>
	          </p>
	        </div>
	      </div>
	    </section>
    </div>
    <div style="bottom: 0;position: fixed;width: 100%;">
		<jsp:include page="footer.jsp"></jsp:include>
	</div>
</body>
</html>