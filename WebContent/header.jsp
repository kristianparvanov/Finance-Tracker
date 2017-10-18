<%@page import="model.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <title>Header | Finance Tracker</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.7 -->
  <link rel="stylesheet" href="./static/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="./static/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="./static/ionicons.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="./static/AdminLTE.min.css">
  <!-- iCheck -->
  <link rel="stylesheet" href="./static/_all-skins.min.css">
  <!-- Google Font -->
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
</head>
<body class="hold-transition skin-blue layout-top-nav">
<div class="wrapper">

 <header class="main-header">
    <!-- Logo -->
    <a href="../../index2.html" class="logo">
      <!-- mini logo for sidebar mini 50x50 pixels -->
      <span class="logo-mini"><b>F</b>TR</span>
      <!-- logo for regular state and mobile devices -->
      <span class="logo-lg"><b>Finance</b>Tracker</span>
    </a>
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top">
      <!-- Sidebar toggle button-->
      <a href="#" class="sidebar-toggle" data-toggle="push-menu" role="button">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </a>
		
      <div class="collapse navbar-collapse pull-left">
        <ul class="nav navbar-nav">
          <!-- Messages: style can be found in dropdown.less-->
          	<li><a href="#" style="font-size: 18px;">Link</a></li>
            <li><a href="#" style="font-size: 18px;">Link</a></li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" style="font-size: 18px;">Dropdown <span class="caret"></span></a>
              <ul class="dropdown-menu" role="menu">
                <li><a href="#">Action</a></li>
                <li><a href="#">Another action</a></li>
                <li><a href="#">Something else here</a></li>
                <li class="divider"></li>
                <li><a href="#">Separated link</a></li>
                <li class="divider"></li>
                <li><a href="#">One more separated link</a></li>
              </ul>
            </li>
        </ul>
        </div>
            
        <div class="collapse navbar-collapse pull-right">
        <ul class="nav navbar-nav">
          <!-- User Account: style can be found in dropdown.less -->
          <li>
            <a href="#" style="font-size: 18px;">
            <i class="ion ion-person"></i>
              <% User u = (User) request.getSession().getAttribute("user"); %>
              <span><%= u.getFirstName() %></span>
            </a>
          </li>
          <!-- Control Sidebar Toggle Button -->
          <li>
            <a href="logout" style="font-size: 18px;"><i class="glyphicon glyphicon-log-out"></i> Log out</a>
          </li>
        </ul>
      </div>
    </nav>
  </header>
  
 <!-- <div class="content-wrapper">-->
    <!-- <div class="container" style="height: 500px">-->
     
    <!-- /.container -->
  <!-- </div>-->
  
<!-- </div>-->

<!-- jQuery 3 -->
<script src="./static/jquery.min.js"></script>
<!-- Bootstrap 3.3.7 -->
<script src="./static/bootstrap.min.js"></script>
<!-- SlimScroll -->
<script src="./static/jquery.slimscroll.min.js"></script>
<!-- FastClick -->
<script src="./static/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="./static/adminlte.min.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="./static/demo.js"></script>
</body>
</html>