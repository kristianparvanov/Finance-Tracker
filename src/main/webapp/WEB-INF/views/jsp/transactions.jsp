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
<title>Transactions | Finance Tracker</title>
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
		<jsp:include page="left.jsp"></jsp:include>
	</div>
	<div>
		<jsp:include page="header.jsp"></jsp:include>
	</div>
	<div class="content-wrapper">
		<section class="content-header">
			<h2><b><c:out value="${sessionScope.accountName}"></c:out></b></h2>
			<br>
			<h1>Current balance <c:out value="${sessionScope.balance}"></c:out></h1>
			<h1>All transactions</h1>
		</section>
		<section class="content">
			<c:if test="${empty sessionScope.transactions }">
				<h3><i class="ion ion-information-circled"></i>  No records yet</h3>
				<h4>Track your expenses and income. Start by adding a new record.</h4>
			</c:if>
			
			<div style="margin-bottom: 25px">
				<div class="row">
					<div class="col-sm-3">
						<a href="addTransaction" type="button" class="btn btn-block btn-primary btn-lg"><i class="ion ion-plus"></i> Add new record</a>
					</div>
					<div class="col-sm-3">
						<a href="main" type="button" class="btn btn-block btn-default btn-lg"><i class="ion ion-android-arrow-back"></i> Back</a>
					</div>
					<div class="col-sm-3">
						<a href="transfer?accountId=${sessionScope.accountId}" type="button" class="btn btn-block btn-default btn-lg"><i class="ion ion-arrow-swap"></i> Transfer</a>
					</div>
					<div class="col-sm-3">
						<a href="#" type="button" class="btn btn-block btn-danger btn-lg"><i class="ion ion-android-delete"></i> Delete Account</a>
					</div>
				</div>
			</div>
			
			<c:forEach items="${sessionScope.transactions }" var="transaction">
				<div>
					<a href="editTransaction?transactionId=${transaction.transactionId}">
			            <div class="info-box" style="width: auto;">
				            <div class="info-box-content">
				             <div class="row">
				              	<div class="col-sm-4">
						             <c:choose>
						              <c:when test="${transaction.type eq 'INCOME'}">
						              	<h3 style="color: green;">Amount: + <i class="ion-social-usd" style="font-size: 20px;"> </i><fmt:formatNumber value="${transaction.amount}" minFractionDigits="2"/></h3>
						              </c:when>
						              <c:when test="${transaction.type eq 'EXPENCE'}">
						              	<h3 style="color: red;">Amount: - <i class="ion-social-usd" style="font-size: 20px;"> </i><fmt:formatNumber value="${transaction.amount}" minFractionDigits="2"/></h3>
						              </c:when>
						              <c:otherwise>
		       							 <h3>Amount: <i class="ion-social-usd" style="font-size: 20px;"> </i><fmt:formatNumber value="${transaction.amount}" minFractionDigits="2"/></h3>
		    						  </c:otherwise>
		    						  </c:choose>
		    						  </div>
		    						  <div class="col-sm-4">
					              	  	 <h3>Category: <c:out value="${transaction.categoryName}"></c:out></h3>
				              	  	 </div>
				              </div>
				              <div class="row">
				              	  <div class="col-sm-4">
						              <h4>Description: <c:out value="${transaction.description }"></c:out></h4>
						          </div>
						          <div class="col-sm-4">
						              <h4>Date: <fmt:parseDate value="${transaction.date}" pattern="yyyy-MM-dd" /></h4>
					              </div>
				              </div>
				            </div>
			            </div>
		            </a>
		        </div> 
			</c:forEach>
	 	</section>
	</div>
	<div>
		<jsp:include page="footer.jsp"></jsp:include>
	</div>
	
<!-- jQuery 3 -->
<script src="js/jquery.min.js"></script>
<!-- Bootstrap 3.3.7 -->
<script src="js/bootstrap.min.js"></script>
<!-- SlimScroll -->
<script src="js/jquery.slimscroll.min.js"></script>
<!-- FastClick -->
<script src="js/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="js/adminlte.min.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="js/demo.js"></script>
</body>
</html>