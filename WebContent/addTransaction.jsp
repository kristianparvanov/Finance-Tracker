<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="model.TransactionType" %>
<%@ page import="model.TransactionType" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Transaction | Finance Tracker</title>
<!-- Select2 -->
<link rel="stylesheet" href="./static/select2.min.css">
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
			<h1>Add transaction</h1>
		</section>
		<section class="content">
			<div class="col-md-6">
		        <div class="box box-primary">
		            <form role="form" action="addTransaction" method="post">
		              <div class="box-body">
		              	<div class="form-group">
			                <label>Type</label>
			                <select class="form-control select2" style="width: 100%;" data-placeholder="Select a type" name="type" >
			                  <option>INCOME</option>
			                  <option>EXPENCE</option>
			                  <option>Alabama</option>
			                  <option>Alaska</option>
			                  <option>California</option>
			                  <option>Delaware</option>
			                  <option>Tennessee</option> 
			                  <option>Texas</option>
			                  <option>Washington</option>
			                </select>
			            </div>
		                <div class="form-group">
			                <label>Account</label>
			                <select class="form-control select2" style="width: 100%;" data-placeholder="Select an account" name="account">
			                  <option>Alabama</option>
			                  <option>Alaska</option>
			                  <option>California</option>
			                  <option>Delaware</option>
			                  <option>Tennessee</option>
			                  <option>Texas</option>
			                  <option>Washington</option>
			                </select>
			            </div>
		                 <div class="form-group">
			                <label>Category</label>
			                <select class="form-control select2" style="width: 100%;" data-placeholder="Select a category" name="category">
			                  <option>Alabama</option>
			                  <option>Alaska</option>
			                  <option>California</option>
			                  <option>Delaware</option>
			                  <option>Tennessee</option>
			                  <option>Texas</option>
			                  <option>Washington</option>
			                </select>
			            </div>
		                <div class="form-group">
		                  <label>Amount</label>
		                  <input type="text" class="form-control" placeholder="Amount" name="amount">
		                </div>
		                <div class="form-group">
			                <label>Tags</label>
			                <select class="form-control select2" multiple="multiple" data-placeholder="Select tags" style="width: 100%;" name="tags">
			                  <option>Alabama</option>
			                  <option>Alaska</option>
			                  <option>California</option>
			                  <option>Delaware</option>
			                  <option>Tennessee</option>
			                  <option>Texas</option>
			                  <option>Washington</option>
			                </select>
			            </div>
		                <div class="form-group">
                  			<label>Description</label>
                 			<textarea class="form-control" rows="3" placeholder="Enter transaction description here" name="description"></textarea>
               			</div>
               		  </div>
               		  <div class="box-footer">
		                <button type="submit" class="btn btn-primary">Save</button>
		                <a href="#" class="btn btn-danger">Delete</a>
		                <a href="main.jsp" class="btn btn-default">Cancel</a>
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
<!-- Select2 -->
<script src="./static/select2.full.min.js"></script>
<!-- SlimScroll -->
<script src="./static/jquery.slimscroll.min.js"></script>
<!-- FastClick -->
<script src="./static/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="./static/adminlte.min.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="./static/demo.js"></script>
<!-- I hate you -->
<script type="text/javascript">
	$(function () {
		$('.select2').select2()
	});
</script>
</body>
</html>