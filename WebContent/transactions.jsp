<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.math.BigDecimal"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Transactions | Finance Tracker</title>
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
			<h2>Current balance <c:out value="${sessionScope.balance}"></c:out></h2>
			<h1>All transactions</h1>
		</section>
		<section class="content">
			<c:forEach items="${sessionScope.transactions }" var="transaction">
				<div>
		            <div class="info-box">
			            <div class="info-box-content">
			              <h3>Amount: <i class="ion-social-usd" style="font-size: 20px;"> </i><fmt:formatNumber value="${transaction.amount}" minFractionDigits="2"/></h3>
			              <h4>Type: <c:out value="${transaction.type}"></c:out></h4>
			              <h4>Description: <c:out value="${transaction.description }"></c:out></h4>
			              <h4>Category: <c:out value="${transaction.categoryName}"></c:out></h4>
			              <h4>Date: <fmt:parseDate value="${transaction.date}" pattern="yyyy-MM-dd" /></h4>
			            </div>
		            </div>
		        </div> 
			</c:forEach>
	 	</section>
	</div>
	<div>
		<jsp:include page="footer.jsp"></jsp:include>
	</div>
</body>
</html>