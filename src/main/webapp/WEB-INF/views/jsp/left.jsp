<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Left | Finance Tracker</title>
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
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
<body class="hold-transition skin-blue-light fixed sidebar-mini">
	<div class="wrapper">
		<!-- Left side column. contains the sidebar -->
		<aside class="main-sidebar">
			<!-- sidebar: style can be found in sidebar.less -->
			<section class="sidebar">
				<!-- sidebar menu: : style can be found in sidebar.less -->
				<ul class="sidebar-menu" data-widget="tree">
					<li class="header" style="font-size: 16px">MAIN NAVIGATION</li>
					<li>
						<a href="<c:url value="/main"></c:url>">
							<i class="fa fa-credit-card"></i> 
							<span style="font-size: 18px">Accounts</span>
						</a>
					</li>
					<li>
						<a href="<c:url value="/plannedPayments"></c:url>">
							<i class="fa fa-dollar"></i> 
							<span style="font-size: 18px">Planned Payments</span>
						</a>
					</li>
					<li>
						<a href="<c:url value="/budgets"></c:url>">
							<i class="fa fa-money"></i> 
							<span style="font-size: 18px">Budgets</span>
						</a>
					</li>
					<li class="treeview">
						<a href="#">
							<i class="fa fa-dashboard"></i> 
							<span style="font-size: 18px">???</span>
							<span class="pull-right-container"> 
								<i class="fa fa-angle-left pull-right"></i>
							</span>
						</a>
						<ul class="treeview-menu">
							<li>
								<a href="../../index.html">
									<i class="fa fa-circle-o"></i>Spravka 1
								</a>
							</li>
							<li>
								<a href="../../index2.html">
									<i class="fa fa-circle-o"></i> Spravka 2
								</a>
							</li>
						</ul>
					</li>
					<li class="treeview">
						<a href="#"> 
							<i class="fa fa-pie-chart"></i> 
							<span style="font-size: 18px">Charts</span>
							<span class="pull-right-container">
								<i class="fa fa-angle-left pull-right"></i>
							</span>
						</a>
						<ul class="treeview-menu">
							<li>
								<a href="../../index.html">
									<i class="fa fa-circle-o"></i>Chart 1
								</a>
							</li>
							<li>
								<a href="../../index2.html">
									<i class="fa fa-circle-o"></i> Chart 2
								</a>
							</li>
						</ul>
					</li>

					<li>
						<a href="#">
							<i class="fa fa-book"></i> 
							<span style="font-size: 18px">Reports</span>
						</a>
					</li>
					<li>
						<a href="<c:url value="/about"></c:url>">
							<i class="fa fa-question-circle"></i>
							<span style="font-size: 18px">About</span>
						</a>
					</li>
				</ul>
			</section>
			<!-- /.sidebar -->
		</aside>
	</div>
	<!-- jQuery 3 -->
 	<script src="<c:url value="/js/jquery.min.js" />"  type ="text/javascript"></script>
	<!-- Bootstrap 3.3.7 -->
 	<script src="<c:url value="/js/bootstrap.min.js" />"  type ="text/javascript"></script>
	<!-- SlimScroll -->
 	<script src="<c:url value="/js/jquery.slimscroll.min.js" />"  type ="text/javascript"></script>
	<!-- FastClick -->
 	<script src="<c:url value="/js/fastclick.js" />"  type ="text/javascript"></script>
	<!-- AdminLTE App -->
	 <script src="<c:url value="/js/adminlte.min.js" />"  type ="text/javascript"></script>
	<!-- AdminLTE for demo purposes -->
	<script src="<c:url value="/js/demo.js" />"  type ="text/javascript"></script>
</body>
</html>