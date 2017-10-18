<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="model.User"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Finance Tracker Main</title>
</head>
<body>
	<div>
		<jsp:include page="left.jsp"></jsp:include>
	</div>
	<div>
		<jsp:include page="header.jsp"></jsp:include>
	</div>
	<br>
	<br>
	<div style="margin-left: 250px">
		<% User u = (User) session.getAttribute("user"); %>
		<h1>Current user: <%= u.getUserName() %></h1>
		<section class="content">
			<div class="row">
			<c:forEach items="${sessionScope.accounts }" var="account">
				<div class="col-lg-3 col-xs-6">
					<!-- small box -->
			          <div class="small-box bg-aqua">
			            <div class="inner">
			              <h3><c:out value="${account.amount}"></c:out></h3>
			              <p><c:out value="${account.name}"></c:out></p>
			            </div>
			            <div class="icon">
			            	<c:if test="${fn:contains(account.name, 'card')}">
			            		<i class="ion ion-card"></i>
			            	</c:if>
			              	<c:if test="${fn:contains(account.name, 'Cash')}">
			            		<i class="ion ion-cash"></i>
			            	</c:if>
			            	<c:if test="${fn:contains(account.name, 'Bank')}">
			            		<i class="ion ion-social-usd"></i>
			            	</c:if>
			            </div>
			            <a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
			          </div>
				</div>
			</c:forEach>
			
			<div class="col-lg-3 col-xs-6">
					<!-- small box -->
			          <div class="small-box bg-aqua">
			            <div class="inner">
			              <h3>Add</h3>
			              <p>Add new Account</p>
			            </div>
			            <div class="icon">
			              <i class="ion ion-plus"></i>
			            </div>
			            <a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
			          </div>
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