<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="com.financeTracker.model.TransactionType" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User | Finance Tracker</title>
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
			<h2>Your profile, <span>${ sessionScope.user.firstName }</span></h2>
		</section>
		<section class="content">
			<div class="col-md-6">
		        <div class="box box-primary">
		            <form role="form" action="editUser" method="post">
		              <div class="box-body">
		              	<div class="form-group">
			                <label>Username</label>
               				<input type="text" id="usrname" class="form-control" name="username" >
               				<c:set var="name" value="${ username }" />
                 			<script type="text/javascript">
                 				var asd = '${name}';
								document.getElementById("usrname").value = asd;
							</script>
			            </div>
		                <div class="form-group">
			                <label>Email</label>
               				<input type="text" id="email" class="form-control" name="email" >
               				<c:set var="email" value="${ email }" />
                 			<script type="text/javascript">
                 				var asd = '${email}';
								document.getElementById("email").value = asd;
							</script>
			            </div>
		                 <div class="form-group">
			                <label>First name</label>
               				<input type="text" id="firstName" class="form-control" name="firstName" >
               				<c:set var="firstName" value="${ firstName }" />
                 			<script type="text/javascript">
                 				var asd = '${firstName}';
								document.getElementById("firstName").value = asd;
							</script>
			            </div>
		                <div class="form-group">
		                  <label>Last name</label>
               				<input type="text" id="lastName" class="form-control" name="lastName" >
               				<c:set var="lastName" value="${ lastName }" />
                 			<script type="text/javascript">
                 				var asd = '${lastName}';
								document.getElementById("lastName").value = asd;
							</script>
		                </div>
               		  </div>
               		  <div class="box-footer">
		                <button type="submit" class="btn btn-primary">Save</button>
		                <a href="main" class="btn btn-default">Cancel</a>
		              </div>
		            </form>
		            <form action="deletePlannedPayment/${sessionScope.userId}" method="post">
		            	<div class="box-footer">
    						<button type="submit" class="btn btn-danger">Delete my profile</button>
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