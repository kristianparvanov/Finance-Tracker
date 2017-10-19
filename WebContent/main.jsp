<%@page import="java.math.BigDecimal"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="model.User"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Main | Finance Tracker</title>
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
			<h2>Current balance across all accounts: <c:out value="${sessionScope.balance}"></c:out></h2>
			<h1>All accounts</h1>
		</section>
		<section class="content">
			<div class="row">
			<c:forEach items="${sessionScope.accounts }" var="account">
				<div class="col-lg-3 col-xs-6">
					<!-- small box -->
					<c:if test="${fn:contains(account.name, 'card')}">
			            <div class="small-box bg-yellow">
	            	</c:if>
	              	<c:if test="${fn:contains(account.name, 'Cash')}">
	            		<div class="small-box bg-green">
	            	</c:if>
	            	<c:if test="${fn:contains(account.name, 'Bank')}">
	            		<div class="small-box bg-red">
	            	</c:if>
	            	<c:if test="${!fn:contains(account.name, 'card') && !fn:contains(account.name, 'Cash') && !fn:contains(account.name, 'Bank')}">
	            		<div class="small-box bg-aqua">
	            	</c:if>
			            <div class="inner">
			              <h3><i class="ion-social-usd" style="font-size: 20px;"> </i><fmt:formatNumber value="${account.amount}" minFractionDigits="2"/></h3>
			              <p><c:out value="${account.name}"></c:out></p>
			            </div>
			            <div class="icon">
			            	<c:if test="${fn:contains(account.name, 'card')}">
			            	<div style="margin-top: 10px">	<i class="ion ion-card"></i></div>
			            	</c:if>
			              	<c:if test="${fn:contains(account.name, 'Cash')}">
			            		<div style="margin-top: 10px"><i class="ion ion-cash"></i></div>
			            	</c:if>
			            	<c:if test="${fn:contains(account.name, 'Bank')}">
			            		<div style="margin-top: 10px"><i class="ion ion-social-usd"></i></div>
			            	</c:if>
			            	<c:if test="${!fn:contains(account.name, 'card') && !fn:contains(account.name, 'Cash') && !fn:contains(account.name, 'Bank')}">
			            		<div style="margin-top: 10px"><i class="ion ion-pie-graph"></i></div>
			            	</c:if>
			            </div>
			            <a href="transaction?accountId=${account.accountId}" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
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
			             <div style="margin-top: 10px"> <i class="ion ion-plus"></i></div>
			            </div>
			            <a href="#" class="small-box-footer">Get started <i class="fa fa-arrow-circle-right"></i></a>
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