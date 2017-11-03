<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="com.financeTracker.model.TransactionType" %>
<%@ page import="com.financeTracker.model.TransactionType" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Planned Payment | Finance Tracker</title>
<!-- Select2 -->
<link href="<c:url value="/css/select2.min.css" />" rel="stylesheet" type="text/css">
</head>
<body>
	<div>
		<jsp:include page="left.jsp"></jsp:include>
	</div>
	<div>
		<jsp:include page="header.jsp"></jsp:include>
	</div>
	<div class="content-wrapper" style="height: auto">
		<section class="content-header">
			<h1>Add planned payment</h1>
		</section>
		<section class="content">
			<div class="col-md-6">
		        <div class="box box-primary">
		        	 <form role="form" action="addPlannedPayment" method="post">
		        	 	<div class="box-body">
		        	 		<div class="form-group">
                  				<label>Name</label>
                  				<input type="text" class="form-control" placeholder="Enter planned payment name" name="name" >
                			</div>
		        	 		<div class="form-group">
			                	<label>Type</label>
			                	<select class="form-control select2" style="width: 100%;" data-placeholder="Select a type" name="type" onchange="myFunction()" id="type" >
			                		<option></option>
			                  		<option>EXPENCE</option>
			                  		<option>INCOME</option>
			                  		
			                  		 <script>
										function myFunction() {
										    var request = new XMLHttpRequest();
										    var select = document.getElementById("type");
										    var sel = select.value;
										    
										    request.onreadystatechange = function() {
										    	if (this.readyState == 4 && this.status == 200) {
													var select = document.getElementById("cat");
													var categories = JSON.parse(this.responseText);
													
													$(select).html(""); //reset child options
												    $(categories).each(function (i) { //populate child options 
												        $(select).append("<option>"+categories[i]+"</option>");
												    });
												}
										    };
										    
										    request.open("GET", "http://localhost:8080/FinanceTracker/account/getCategory/"+sel);
										    request.send();
										}
							  </script>
			                	</select>
			            	</div>
			        	 	<div class="form-group">
				                <label>Account</label>
				                <select class="form-control select2" style="width: 100%;" data-placeholder="Select an account" name="account">
				                  <c:forEach items="${accounts}" var="account">
				                	  <option><c:out value="${account.name}"></c:out></option>
				                  </c:forEach>
				                </select>
				            </div>
			                 <div class="form-group">
				                <label>Category</label>
				                <select class="form-control select2" style="width: 100%;" data-placeholder="Select a category" name="category" id="cat">
				                  <!-- <c:forEach items="${categories}" var="category">
				                	  <option><c:out value="${category.name}"></c:out></option>
				                  </c:forEach> -->
				                </select>
				            </div>
			                <div class="form-group">
			                  <label>Amount</label>
			                  <input type="text" class="form-control" placeholder="Amount" name="amount">
			                </div>
			                <div class="form-group">
			                  	<label>Date</label>
	               				<div class="input-group date">
		                  			<div class="input-group-addon">
		                    			<i class="fa fa-calendar"></i>
		                  			</div>
	                  		 		<input type="text" class="form-control pull-right" id="datepicker" data-placeholder="Enter planned date" name="date">
	                			</div>
		               		</div>
			                <div class="form-group">
				                <label>Tags</label>
				                <select class="form-control select2" multiple="multiple" data-placeholder="Select tags" style="width: 100%;" name="tags">
				                  <c:forEach items="${tags}" var="tag">
				                	  <option><c:out value="${tag.name}"></c:out></option>
				                  </c:forEach>
				                </select>
				            </div>
			                <div class="form-group">
	                  			<label>Description</label>
	                 			<textarea class="form-control" rows="3" placeholder="Enter transaction description here" name="description"></textarea>
	               			</div>
	               		  </div>
	               		  <div class="box-footer">
			                <button type="submit" class="btn btn-primary">Save</button>
			                <a href="<c:url value="/plannedPayments"></c:url>" class="btn btn-default">Cancel</a>
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
<script src="<c:url value="/js/jquery.min.js" />" type ="text/javascript"></script>
<!-- Bootstrap 3.3.7 -->
<script src="<c:url value="/js/bootstrap.min.js" />" type ="text/javascript"></script>
<!-- Select2 -->
<script src="<c:url value="/js/select2.full.min.js" />" type ="text/javascript"></script>
<!-- bootstrap datepicker -->
<script src="<c:url value="/js/bootstrap-datepicker.min.js" />" type ="text/javascript"></script>
<!-- SlimScroll -->
<script src="<c:url value="/js/jquery.slimscroll.min.js" />" type ="text/javascript"></script>
<!-- FastClick -->
<script src="<c:url value="/js/static/fastclick.js" />" type ="text/javascript"></script>
<!-- AdminLTE App -->
<script src="<c:url value="/js/static/adminlte.min.js" />" type ="text/javascript"></script>
<!-- AdminLTE for demo purposes -->
<script src="<c:url value="/js/static/demo.js" />" type ="text/javascript"></script>
<!-- I hate you -->
<script type="text/javascript">
	$(function () {
		$('.select2').select2()
		$('#datepicker').datepicker({
	    	autoclose: true
		})
	})
</script>	
</body>
</html>