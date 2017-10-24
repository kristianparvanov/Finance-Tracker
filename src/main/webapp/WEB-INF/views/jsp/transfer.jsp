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
<title>Transfer | Finance Tracker</title>
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
			<h1>Transfer</h1>
		</section>
		<section class="content">
			<div class="col-md-6">
		        <div class="box box-primary">
		            <form role="form" action="transfer" method="post">
		              <div class="box-body">
		              <div class="form-group">
		                  <label>Amount</label>
		                  <input type="text" class="form-control" placeholder="Amount" name="amount">
		                </div>
		                <div class="form-group">
			                <label>From Account</label>
			                <select class="form-control select2" style="width: 100%;" data-placeholder="Select an account" name="fromAccount">
			                <option selected="selected"><c:out value="${ sessionScope.firstAccount.name }"></c:out></option>
			                  <c:forEach items="${sessionScope.accounts }" var="account">
			                	 <option><c:out value="${account.name}"></c:out></option>
			                  </c:forEach>
			                </select>
			            </div>
		                 <div class="form-group">
			                <label>To Account</label>
			                <select class="form-control select2" style="width: 100%;" data-placeholder="Select an account" name="toAccount">
			                  <c:forEach items="${sessionScope.accounts }" var="account">
			                	 <option><c:out value="${account.name}"></c:out></option>
			                  </c:forEach>
			                </select>
			            </div>
               		  </div>
               		  <div class="box-footer">
		                <button type="submit" class="btn btn-primary">Execute</button>
		                <a href="transaction?accountId=${sessionScope.accountId}" class="btn btn-default">Cancel</a>
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