<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri = "http://www.springframework.org/tags/form" prefix = "f"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <title>Register | Finance Tracker</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.7 -->
  <link rel="stylesheet" href="css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="css/ionicons.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="css/AdminLTE.min.css">
  <!-- iCheck -->
  <link rel="stylesheet" href="css/blue.css">
  <!-- Google Font -->
  <link rel="stylesheet" href="css/css">
</head>
<body class="hold-transition register-page" style="background-image:  url(img/infinity3.jpg); overflow:hidden;">
<div class="register-box">
  <div class="register-logo">
    <h1 style="color: white; text-shadow: 0px 0px 15px black;"><b>Finance</b>Tracker</h1>
  </div>

  <div class="register-box-body" style="box-shadow: 0px 0px 15px black">
    <p class="login-box-msg" style="font-size: 18px">Welcome back! We missed you</p>

    <form action="<c:url value='/resetPassword'></c:url>" method="post" <%-- commandName="user" --%>>
    	<c:if test="${resetPassword!=null}">
		 <label style="color: red"><c:out value="${resetPassword}"/></label>
	  	</c:if>
      <div class="form-group has-feedback">
        <input type="text" class="form-control" placeholder="Enter your new username here" required="" name="username">
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="password" class="form-control" placeholder="Enter new password" required="" name="password">
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="password" class="form-control" placeholder="Retype new password"  name="repeatPassword" required="">
        <span class="glyphicon glyphicon-lock form-control-feedback"></span> 
      </div>
      <div class="row">
        <div class="col-xs-6">
          <button type="submit" class="btn btn-primary btn-block btn-flat">Set new password</button>
        </div>
      </div>
    </fform>
    <br>
    I am already registered. <a href="login" class="text-center">Log me in</a>
  </div>
</div>
</body></html>
</html>