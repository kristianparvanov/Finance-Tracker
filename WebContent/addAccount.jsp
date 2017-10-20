<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="model.User"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Account | Finance Tracker</title>
</head>
<body>
	<div>
		<jsp:include page="left.jsp"></jsp:include>
	</div>
	<div>
		<jsp:include page="header.jsp"></jsp:include>
	</div>
	<div class="content-wrapper">
		 <section class="content-header">
			<h1>Add account</h1>
		</section>
		<section class="content">
			<div class="col-md-6">
		        <div class="box box-primary">
		            <form role="form" action="addAccount" method="post">
		              <div class="box-body">
		                <div class="form-group">
		                  <label>Account name</label>
		                  <input type="text" class="form-control" placeholder="Name" name="name">
		                </div>
		                <div class="form-group">
		                  <label>Initial value</label>
		                  <input type="text" class="form-control" placeholder="Amount" name="amount">
		                </div>
		              </div>
		              <div class="box-footer">
		                <button type="submit" class="btn btn-primary">Save</button>
		                <a href="main.jsp" class="btn btn-default">Cancel</a>
		              </div>
		            </form>
	          	</div>
        	</div>
		</section>
	</div>
	<div>
		<jsp:include page="footer.jsp"></jsp:include>
	</div>
	
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