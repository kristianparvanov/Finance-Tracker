<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login | Finance Tracker</title>
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
  <link rel="stylesheet" href="./static/blue.css">
   <!-- Google Font -->
  <link rel="stylesheet" href="./static/css">
</head>
<body class="hold-transition login-page">
	<div class="login-box">
	  <div class="login-logo">
	    <b>Finance</b>Tracker
	  </div>
	  <div class="login-box-body">
	    <p class="login-box-msg">Sign in to start your session</p>
	
	    <form action="login" method="post">
	      <div class="form-group has-feedback">
	        <input type="text" class="form-control" placeholder="Username" name="username">
	        <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
	      </div>
	      <div class="form-group has-feedback">
	        <input type="password" class="form-control" placeholder="Password" name="password">
	        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
	      </div>
	      <div class="row">
	        <div class="col-xs-4">
	          <button type="submit" class="btn btn-primary btn-block btn-flat" >Sign In</button>
	        </div>
	      </div>
	    </form>
	    <br>
	    <a href="https://adminlte.io/themes/AdminLTE/pages/examples/login.html#">I forgot my password</a><br>
   		<a href="register.jsp" class="text-center">Register a new membership</a>
	   </div>
	 </div>
</body>
</html>