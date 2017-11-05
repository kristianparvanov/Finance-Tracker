<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" href="<c:url value='/css/daterangepicker.css'></c:url>">
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link rel="stylesheet" href="<c:url value='/css/select2.min.css'></c:url>">
		
		<script src='<c:url value='/js/moment.min.js'></c:url>'></script>
		<script src='<c:url value='/js/daterangepicker.js'></c:url>'></script>
		<title>Edit Budget | Finance Tracker</title>
		<link href="<c:url value="/img/favicon.ico" />" rel="icon" type="image/x-icon">
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
			<h1>Add budget</h1>
		</section>
		<section class="content">
			<div class="col-md-6">
		        <div class="box box-primary">
		            <form role="form" action='<c:url value='/budgets/${ budget.budgetId }/editBudget'></c:url>' method="post">
		              <div class="box-body">
			             <div class="form-group">
                  			<label>Name</label>
                 			<textarea id="bName" class="form-control" rows="1" placeholder="Enter budget name" name="name"></textarea>
                 			<c:set var="name" value="${ budget.name }" />
                 			<script type="text/javascript">
                 				var budgetName = '${ name }';
								document.getElementById("bName").value = budgetName;
							</script>
               			</div>
			            
		                <div class="form-group">
			                <label>Account</label>
			                <select class="form-control select2" style="width: 100%;" data-placeholder="Select an account" name="account">
			                  <option selected="selected"><c:out value="${ accountName }"></c:out></option>
			                  <c:forEach items="${ accounts }" var="account">
			                	  <option><c:out value="${ account.name }"></c:out></option>
			                  </c:forEach>
			                </select>
			            </div>
		                 <div class="form-group">
			                <label>Category</label>
			                <select class="form-control select2" style="width: 100%;" data-placeholder="Select a category" name="category">
			                <option selected="selected"><c:out value="${ categoryName }"></c:out></option>
			                  <c:forEach items="${ categories }" var="category">
			                	  <option><c:out value="${ category }"></c:out></option>
			                  </c:forEach>
			                </select>
			            </div>
			            <div class="form-group">
							<a href="<c:url value="/addCategory"></c:url>" type="button" class="btn btn-block btn-default" style="width: 30%;"><i class="ion ion-plus"></i> Add new category</a>
						</div>
		                <div class="form-group">
		                  <label>Amount</label>
		                  <input type="text" class="form-control" placeholder="Amount" name="amount" value="${ editBudgetAmount }">
		                </div>
		                <div class="form-group">
			                <label>Tags</label>
			                <select id="multy" class="form-control select2" multiple="multiple" data-placeholder="Select tags" style="width: 100%;" name="tags">
			                  <c:forEach items="${ tags }" var="tag">
			                	  <option><c:out value="${ tag.name }"></c:out></option>
			                  </c:forEach>
			                </select>
			                <c:set var="tags" value="${ tagNames }" />
			                <script type="text/javascript">
				           		var values="Test,Prof,Off";
				           		var values = 'Alaska'
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
			            
			              <!-- Date and time range -->
              <div class="form-group">
                <label>Date and time range:</label>

                <div class="input-group">
                  <div class="input-group-addon">
                    <i class="fa fa-clock-o"></i>
                  </div>
                  <input type="text" class="form-control pull-right" id="reservationtime" name="date">
                  <script type="text/javascript">
       				var date = '${ date }';
					document.getElementById("reservationtime").value = date;
				</script>
                </div>
                <!-- /.input group -->
              </div>
              <!-- /.form group -->
              
               <div class="box-footer">
		                <button type="submit" class="btn btn-primary">Save</button>
		                <a href='<c:url value='/budgets/${ budgetId }'></c:url>' class="btn btn-default">Cancel</a>
		              </div>
		              
	               </div>
		            </form>
	          	</div>
        	</div>
		</section>
	</div>
	<div>
		<jsp:include page="footer.jsp"></jsp:include>
	</div>
	
	<!-- Select2 -->
	<script src='<c:url value='/js/select2.full.min.js'></c:url>'></script>
	<script src='<c:url value='/js/daterangepicker.js'></c:url>'></script>
	<!-- I hate you -->
	<script type="text/javascript">
		$(function () {
			$('.select2').select2()
			$('#reservationtime').daterangepicker({ timePicker: false, timePickerIncrement: 30, format: 'MM/DD/YYYY h:mm A' })
		});
		
		
	</script>
	</body>
</html>