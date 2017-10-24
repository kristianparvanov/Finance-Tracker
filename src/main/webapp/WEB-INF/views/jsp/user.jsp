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
			<h2><b>USER STUFF</b></h2>
			<br>
		</section>
		<section class="content">
			<div class="col-md-6">
		        <div class="box box-primary">
		            <form role="form" action="editTransaction" method="post">
		              <div class="box-body">
		              	<div class="form-group">
			                <label>Type</label>
			                <select class="form-control select2" style="width: 100%;" data-placeholder="Select a type" name="type" >
			                  <option selected="selected"><c:out value="${ sessionScope.editTransactionType }"></c:out></option>
			                  <option>EXPENCE</option>
			                  <option>INCOME</option>
			                </select>
			            </div>
		                <div class="form-group">
			                <label>Account</label>
			                <select class="form-control select2" style="width: 100%;" data-placeholder="Select an account" name="account">
			                <option selected="selected"><c:out value="${ sessionScope.editTransactionAccount }"></c:out></option>
			                  <c:forEach items="${sessionScope.accounts }" var="account">
			                	 <option><c:out value="${account.name}"></c:out></option>
			                  </c:forEach>
			                </select>
			            </div>
		                 <div class="form-group">
			                <label>Category</label>
			                <select class="form-control select2" style="width: 100%;" data-placeholder="Select a category" name="category">
			                <option selected="selected"><c:out value="${ sessionScope.editTransactionCategory }"></c:out></option>
			                  <c:forEach items="${sessionScope.categories }" var="category">
			                	  <option><c:out value="${category.name}"></c:out></option>
			                  </c:forEach>
			                </select>
			            </div>
		                <div class="form-group">
		                  <label>Amount</label>
		                  <input type="text" class="form-control" placeholder="Amount" name="amount" value="${ sessionScope.editTransactionAmount }">
		                </div>
		                <div class="form-group">
		                  <label>Date</label>
               				<div class="input-group date">
	                  			<div class="input-group-addon">
	                    			<i class="fa fa-calendar"></i>
	                  			</div>
                  		 		<input type="text" class="form-control pull-right" id="datepicker" value="${ sessionScope.editTransactionDate }" name="date">
                			</div>
		                </div>
		                <div class="form-group">
			                <label>Tags</label>
			                <select id="multy" class="form-control select2" multiple="multiple" data-placeholder="Select tags" style="width: 100%;" name="tags">
				                <c:forEach items="${sessionScope.tags }" var="tag">
				                	<option><c:out value="${tag.name}"></c:out></option>
				                </c:forEach>
			                </select>
			                <c:set var="tags" value="${ sessionScope.editTransactionTags }" />
				            <script type="text/javascript">
				           		var values="Test,Prof,Off";
				           		var values = 'Alaska'
				            	var values = '${tags}';
				            	
				            	values = values.replace(/[\[\]']+/g,'')
				            	
				            	alert(values);
                				$.each(values.split(","), function(i,e){
                					alert(e);
                				    $("#multy option[value='" + e + "']").prop('selected', true);
                				});
                				
                				options = document.querySelectorAll('#multy option');

                			    values.split(',').forEach(function(v) {
                			      Array.from(options).find(c => c.value == v).selected = true;
                			    });
							</script>
			            </div>
		                <div class="form-group">
                  			<label>Description</label>
                 			<textarea id="desc" class="form-control" rows="3" placeholder="Enter transaction description here" name="description" ></textarea>
                 			<c:set var="description" value="${ sessionScope.editTransactionDescription }" />
                 			<script type="text/javascript">
                 				var asd = '${description}';
								document.getElementById("desc").value = asd;
							</script>
               			</div>
               		  </div>
               		  <div class="box-footer">
		                <button type="submit" class="btn btn-primary">Save</button>
		                <a href="deleteTransaction?accountId=${sessionScope.transactionId}" class="btn btn-danger">Delete</a>
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