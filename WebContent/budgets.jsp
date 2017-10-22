<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
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
	
		<%-- <div>
			<jsp:include page="left.jsp"></jsp:include>
		</div> --%>
	<div>
		<jsp:include page="header.jsp"></jsp:include>
	</div>
	
		<c:forEach items="${ sessionScope.budgets }" var="budget">
		
		<div class="col-md-4">
	<div class="row"> 
			<div class="info-box bg-green">
	            <span class="info-box-icon"><i class="ion ion-ios-heart-outline"></i></span>
	
	            <div class="info-box-content">
	              <span class="info-box-text"><c:out value="${ budget.key.name }"></c:out></span>
	              <span class="info-box-number"><c:out value="${ budget.key.amount }"></c:out></span>
	
	              <div class="progress">
	              <c:set var = "percent" scope = "session" value = "${ budget.value }"/>
	      		  
	                <div class="progress-bar" style="width: <c:out value = "${percent}"/>%"></div>
	              </div>
	              <span class="progress-description">
	                    <c:out value="${ percent }%"></c:out> Increase in <%-- <c:out value="${ budget.key.toDate - budget.key.fromDate }"></c:out> --%>
	                  </span>
	            </div>
	            <!-- /.info-box-content -->
	          </div>
		</div>
			
		</c:forEach>
	</div>
	</body>
</html>