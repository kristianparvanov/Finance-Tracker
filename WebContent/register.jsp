<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <title>Register | Finance Tracker</title>
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
<body class="hold-transition register-page">
<div class="register-box">
  <div class="register-logo">
    <b>Finance</b>Tracker
  </div>

  <div class="register-box-body">
    <p class="login-box-msg">Register a new membership</p>

    <form action="register" method="post">
      <div class="form-group has-feedback">
        <input type="text" class="form-control" placeholder="Username" name="username">
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="password" class="form-control" placeholder="Password" name="password">
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="password" class="form-control" placeholder="Retype password" name="repeatPassword">
        <span class="glyphicon glyphicon-search form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="email" class="form-control" placeholder="Email" name="email">
        <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
      </div>
        <div class="form-group has-feedback">
        <input type="text" class="form-control" placeholder="First Name" name="firstName">
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
      </div>
       <div class="form-group has-feedback">
        <input type="text" class="form-control" placeholder="Last Name" name="lastName">
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
      </div>
      <div class="row">
        <div class="col-xs-4">
          <button type="submit" class="btn btn-primary btn-block btn-flat">Register</button>
        </div>
      </div>
    </form>
    <br>
    I am already registered. <a href="https://adminlte.io/themes/AdminLTE/pages/examples/login.html" class="text-center">Log me in</a>
  </div>
</div>
</body></html>
</html>