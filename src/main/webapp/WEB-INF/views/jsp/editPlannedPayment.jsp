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
<title>Edit Planned Payment | Finance Tracker</title>
<!-- Select2 -->
<link href="<c:url value="/css/select2.min.css" />" rel="stylesheet" type="text/css">
<!-- bootstrap datepicker -->
<link href="<c:url value="/css//bootstrap-datepicker.min.css" />" rel="stylesheet" type="text/css">
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
			<h1>Edit planned payment</h1>
		</section>
		<section class="content">
			<div class="col-md-6">
		        <div class="box box-primary">
		        	 <form role="form" action="editPlannedPayment" method="post">
		        	 	<div class="box-body">
		        	 		<div class="form-group">
                  				<label>Name</label>
                  				<input type="text" id="ppname" class="form-control" placeholder="Enter planned payment name" name="name" >
                  				<c:set var="name" value="${ editPlannedPaymentName }" />
	                 			<script type="text/javascript">
	                 				var asd = '${name}';
									document.getElementById("ppname").value = asd;
								</script>
                			</div>
		        	 		<div class="form-group">
			                	<label>Type</label>
			                	<select class="form-control select2" style="width: 100%;" data-placeholder="Select a type" name="type" >
			                  		<option selected="selected"><c:out value="${ editPlannedPaymentType }"></c:out></option>
			                  		<option>EXPENCE</option>
			                  		<option>INCOME</option>
			                	</select>
			            	</div>
			        	 	<div class="form-group">
				                <label>Account</label>
				                <select class="form-control select2" style="width: 100%;" data-placeholder="Select an account" name="account">
				                  <option selected="selected"><c:out value="${ editPlannedPaymentAccount }"></c:out></option>
				                  <c:forEach items="${accounts}" var="account">
				                	  <option><c:out value="${account.name}"></c:out></option>
				                  </c:forEach>
				                </select>
				            </div>
			                 <div class="form-group">
				                <label>Category</label>
				                <select class="form-control select2" style="width: 100%;" data-placeholder="Select a category" name="category">
				                  <option selected="selected"><c:out value="${ editPlannedPaymentCategory }"></c:out></option>
				                  <c:forEach items="${categories}" var="category">
				                	  <option><c:out value="${category.name}"></c:out></option>
				                  </c:forEach>
				                </select>
				            </div>
			                <div class="form-group">
			                  <label>Amount</label>
			                  <input type="text" class="form-control" placeholder="Amount" name="amount" value="${ editPlannedPaymentAmount }">
			                </div>
			                <div class="form-group">
			                  	<label>Date</label>
	               				<div class="input-group date">
		                  			<div class="input-group-addon">
		                    			<i class="fa fa-calendar"></i>
		                  			</div>
		                  			<fmt:parseDate value="${ editPlannedPaymentDate }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                  		 			<input type="text" class="form-control pull-right" id="datepicker" value="<fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }" />" name="date">
	                			</div>
		               		</div>
			                <div class="form-group">
				                 <label>Tags</label>
			                <select id="multy" class="form-control select2" multiple="multiple" data-placeholder="Select tags" style="width: 100%;" name="tags">
				                <c:forEach items="${tags }" var="tag">
				                	<option><c:out value="${tag.name}"></c:out></option>
				                </c:forEach>
			                </select>
				                <c:set var="tags" value="${ editPlannedPaymentTags }" />
					           <script type="text/javascript">
				            	var values = '${tags}';
				            	
				            	values = values.replace(/[\[\]']+/g,'')
				            	
                				$.each(values.split(","), function(i,e){
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
	                 			<textarea id="desc" class="form-control" rows="3" placeholder="Enter planned payment description here" name="description" ></textarea>
	                 			<c:set var="description" value="${ editTPlannedPaymentDescription }" />
	                 			<script type="text/javascript">
	                 				var asd = '${description}';
									document.getElementById("desc").value = asd;
								</script>
               				</div>
	               		  </div>
	               		  <div class="box-footer">
			                <button type="submit" class="btn btn-primary">Save</button>
			                <a href="deletePlannedPayment/${plannedPaymentId}" class="btn btn-danger">Delete</a>
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