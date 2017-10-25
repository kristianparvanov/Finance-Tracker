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
<title>Planned Payments | Finance Tracker</title>
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
			<h1>All planned payments</h1>
		</section>
		<section class="content">
			<c:if test="${empty plannedPayments}">
				<h3><i class="ion ion-information-circled"></i>  No planned playments yet</h3>
				<h4>Plan and strategize. Start by adding a new planned playment.</h4>
			</c:if>
			
			<div style="margin-bottom: 25px">
				<div class="row">
					<div class="col-sm-3">
						<a href="addPlannedPayment" type="button" class="btn btn-block btn-primary btn-lg"><i class="ion ion-plus"></i> Add new payment</a>
					</div>
					<div class="col-sm-3">
						<a href="/FinanceTracker/main" type="button" class="btn btn-block btn-default btn-lg"><i class="ion ion-android-arrow-back"></i> Back</a>
					</div>
				</div>
			</div>
			
			<c:forEach items="${plannedPayments}" var="payment">
				<div>
					<a href="payment/${payment.plannedPaymentId}">
						<div class="info-box" style="width: auto;">
							 <div class="info-box-content">
							 	<div class="row">
							 		<div class="col-sm-4">
							 			<h3>Name: <c:out value="${payment.name}"></c:out></h3>
							 		</div>
							 		<div class="col-sm-4">
							 			<h4>Category: <c:out value="${payment.categoryName}"></c:out></h4>
							 		</div>
							 	</div>
							 	<div class="row">
				              		<div class="col-sm-4">
							            <c:choose>
											<c:when test="${payment.paymentType eq 'INCOME'}">
												<h3 style="color: green;">Amount: + <i class="ion-social-usd" style="font-size: 20px;"> </i><fmt:formatNumber value="${payment.amount}" minFractionDigits="2"/></h3>
											</c:when>
											<c:when test="${payment.paymentType eq 'EXPENCE'}">
												<h3 style="color: red;">Amount: - <i class="ion-social-usd" style="font-size: 20px;"> </i><fmt:formatNumber value="${payment.amount}" minFractionDigits="2"/></h3>
											</c:when>
											<c:otherwise>
												<h3>Amount: <i class="ion-social-usd" style="font-size: 20px;"> </i><fmt:formatNumber value="${payment.amount}" minFractionDigits="2"/></h3>
											</c:otherwise>
										</c:choose>
									</div>
									<div class="col-sm-4">
										<h4>Date: <c:out value="${payment.fromDate}"></c:out></h4>
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