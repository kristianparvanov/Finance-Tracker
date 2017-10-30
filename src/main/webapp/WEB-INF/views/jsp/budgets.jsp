<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Insert title here</title>
	</head>
	<body>
		<c:if test="${ sessionScope.user == null }">
			<c:redirect url="login.jsp"></c:redirect>
		</c:if>
	
		<div>
			<jsp:include page="left.jsp"></jsp:include>
		</div>
		<div>
			<jsp:include page="header.jsp"></jsp:include>
		</div>
		<div class="content-wrapper">
			<section class="content-header">
				<h2>Budgets</h2>
				<h1>All accounts</h1>
			</section>
			<section class="content">
				<div style="margin-bottom: 25px">
					<div class="row">
						<div class="col-sm-3">
							<a href="addBudget" type="button" class="btn btn-block btn-primary btn-lg"><i class="ion ion-plus"></i> Add new budget</a>
						</div>
						<div class="col-sm-3">
							<a href="main" type="button" class="btn btn-block btn-default btn-lg"><i class="ion ion-android-arrow-back"></i> Back</a>
						</div>
					</div>
				</div>
			
				<c:forEach items="${ budgets }" var="budget">
				<div class="row"> 
					<div class="col-md-4" style="width: 50%">
						
						<div class="info-box" style="background-color: khaki" >
				            <a href="budgets/${ budget.key.budgetId }" style="color: #FFFFFF; text-decoration: none !important;">
								<span class="info-box-icon"><i class="ion ion-information-circled" style="margin-top: 20px"></i></span>
							</a>
				            <div class="info-box-content">
				              <span class="info-box-text">Budget name: <c:out value="${ budget.key.name }"></c:out></span>
				              <span class="info-box-number">Initial amount: <c:out value="${ budget.key.initialAmount }"></c:out></span>
				
				              <div class="progress" style="height: 10px; margin-left: 2px; margin-right: 2px; border-radius: 4px;">
				              <c:set var = "percent" scope = "session" value = "${ budget.value }"/>
				      		  
				                <c:if test="${ percent >= 100 }">
				      		  		<div class="progress-bar" style="height: 10px; background-color: red; width: <c:out value = "${ percent }"/>%"></div>
				      		  	</c:if>
				                <c:if test="${ percent < 100 }">
				                	<div class="progress-bar" style="height: 10px; background-color: #75FF33; width: <c:out value = "${ percent }"/>%"></div>
			                	</c:if>
				              </div>
				              <span class="progress-description">
				                    <c:out value="${ percent }%"></c:out> percent spent <%-- Increase in  <c:out value="${ budget.key.toDate - budget.key.fromDate }"></c:out> --%>
				              </span>
				            </div>
				          </div>
						</div>
					</div>
				</c:forEach>
			</section>
		</div>
	</div>
	</body>
</html>